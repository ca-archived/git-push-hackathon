package com.example.masato.githubfeed.githubapi;

import android.content.SharedPreferences;
import android.util.Log;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Masato on 2018/01/19.
 */

public class GitHubApi {

    private GitHubTokenManager tokenManager;
    private GitHubResourceManager resourceManager;
    private ExecutorService executorService;
    private Map<String, String> feedUrls;

    private static GitHubApi api;

    public static void init(SharedPreferences preferences, GitHubApiCallback callback) {
        api = new GitHubApi(preferences, callback);
    }

    public static GitHubApi getApi() {
        if (api == null) {
            throw new RuntimeException("init() must be called before accessing to the api");
        }
        return api;
    }

    public void getFeedList(String url, int page, GitHubApiCallback callback) {
        resourceManager.getFeedEntries(url, page, callback);
    }

    public Map<String, String> getFeedUrls() {
        return feedUrls;
    }

    public void getBitmap(String url, GitHubApiCallback callback) {
        resourceManager.getBitmapFromUrl(url, callback);
    }

    public void fetchToken(String code, final GitHubApiCallback callback) {
        tokenManager.fetchToken(code, new GitHubApiCallback() {
            @Override
            public void onSuccess(Object object) {
                String token = (String) object;
                resourceManager = new GitHubResourceManager(executorService, token);
                callback.onSuccess(null);
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
    }

    public void checkToken(final GitHubApiCallback callback) {
        if (!tokenManager.hasToken()) {
            callback.onError("not logged in yet");
        } else {
            Log.i("gh_info", tokenManager.getToken());
            resourceManager = new GitHubResourceManager(executorService, tokenManager.getToken());
            resourceManager.getFeedUrls(new GitHubApiCallback() {
                @Override
                public void onSuccess(Object resource) {
                    feedUrls = (Map<String, String>) resource;
                    callback.onSuccess(resource);
                }

                @Override
                public void onError(String message) {
                    callback.onError(message);
                }
            });
        }
    }

    private GitHubApi(SharedPreferences preferences, GitHubApiCallback callback) {
        this.executorService = Executors.newFixedThreadPool(4);
        this.tokenManager = new GitHubTokenManager(preferences, executorService);
        checkToken(callback);
    }
}
