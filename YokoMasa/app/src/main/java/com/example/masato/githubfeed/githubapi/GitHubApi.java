package com.example.masato.githubfeed.githubapi;

import android.content.SharedPreferences;
import android.content.res.Resources;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Masato on 2018/01/19.
 */

public class GitHubApi {

    public static final String OAUTH_URL = "https://github.com/login/oauth/authorize";
    public static final String GLOBAL_FEED_URL = "https://github.com/timeline";
    private GitHubTokenManager tokenManager;
    private GitHubResourceManager resourceManager;
    private ExecutorService executorService;

    private static GitHubApi api;

    public static void init(SharedPreferences preferences, Resources resources) {
        api = new GitHubApi(preferences, resources);
    }

    public static GitHubApi getApi() {
        if (api == null) {
            throw new RuntimeException("init() must be called before accessing to the api");
        }
        return api;
    }

    public void fetchProfile(GitHubApiCallback callback) {
        resourceManager.getProfile(callback);
    }

    public void fetchFeedList(String url, int page, GitHubApiCallback callback) {
        resourceManager.getFeedEntries(url, page, callback);
    }

    public void fetchFeedUrl(GitHubApiCallback callback) {
        resourceManager.getFeedUrl(callback);
    }

    public void fetchBitmap(String url, GitHubApiCallback callback) {
        resourceManager.getBitmapFromUrl(url, callback);
    }

    public void deleteToken() {
        tokenManager.deleteToken();
    }

    public void requestToken(String code, final GitHubApiCallback callback) {
        tokenManager.fetchToken(code, new GitHubApiCallback() {
            @Override
            public void onApiSuccess(Object object) {
                String token = (String) object;
                resourceManager.setToken(token);
                callback.onApiSuccess(null);
            }

            @Override
            public void onApiFailure(Failure failure) {
                callback.onApiFailure(failure);
            }
        });
    }

    private GitHubApi(SharedPreferences preferences, Resources resources) {
        this.executorService = Executors.newFixedThreadPool(3);
        this.tokenManager = new GitHubTokenManager(resources, preferences, executorService);
        this.resourceManager = new GitHubResourceManager(tokenManager.getToken(), executorService);
    }

    public interface GitHubApiTokenCheckCallback {
        public void onFinishChecking(boolean havingToken);
    }
}
