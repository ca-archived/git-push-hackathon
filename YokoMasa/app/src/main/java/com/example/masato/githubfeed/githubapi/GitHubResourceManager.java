package com.example.masato.githubfeed.githubapi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.masato.githubfeed.model.GitHubObjectMapper;
import com.example.masato.githubfeed.model.Profile;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.http.HandyHttpURLConnection;
import com.example.masato.githubfeed.http.HttpConnectionPool;
import com.example.masato.githubfeed.util.GitHubApiUtil;

import java.util.concurrent.ExecutorService;

/**
 * Created by Masato on 2018/01/19.
 */

public class GitHubResourceManager {

    private static final String BASE_URL = "https://api.github.com";
    private static final String PROFILE_URL = BASE_URL + "/user";
    private static final String STARRED_URL = PROFILE_URL + "/starred";
    private static final String REPOSITORY_URL = BASE_URL + "/repos";

    private HttpConnectionPool connectionPool;

    public void setToken(String token) {
        connectionPool.setToken(token);
    }

    public void getProfile(final GitHubApiCallback callback) {
        final HandyHttpURLConnection connection = connectionPool.newConnection(PROFILE_URL);
        connection.get(result -> {
            Profile profile = GitHubObjectMapper.mapProfile(result.getBodyString());
            setProfileIcon(profile, callback);
        });
    }

    private void setProfileIcon(final Profile profile, final GitHubApiCallback callback) {
        getBitmapFromUrl(profile.iconUrl, result -> {
            if (result.isSuccessful) {
                Bitmap icon = (Bitmap) result.resultObject;
                profile.icon = icon;
                result.resultObject = profile;
                callback.onApiResult(result);
            } else {
                callback.onApiResult(result);
            }
        });
    }

    public void getFeedUrl(final GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection("https://api.github.com/feeds");
        connection.get(result -> {
            GitHubApiUtil.handleResult(result, callback, successfulResult -> {
                return GitHubObjectMapper.mapFeedUrl(result.getBodyString());
            });
        });
    }

    public void getFeedEntries(String url, int page, final GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.setHeader("Accept", "application/atom+xml");
        connection.addParams("page", Integer.toString(page));
        connection.get(result -> {
            GitHubApiUtil.handleResult(result, callback, successfulResult -> {
                return GitHubObjectMapper.mapFeedEntries(successfulResult.getBodyString());
            });
        });
    }

    public void getRepository(String url, final GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.get(result -> {
            GitHubApiUtil.handleResult(result, callback, successfulResult -> {
                return GitHubObjectMapper.mapRepository(successfulResult.getBodyString());
            });
        });
    }

    public void getReadMeHtml(Repository repository, final GitHubApiCallback callback) {
        String url = repository.baseUrl + "/contents/README.md";
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.setHeader("Accept", "application/vnd.github.html");
        connection.get(result -> {
            GitHubApiUtil.handleResult(result, callback, successfulResult -> {
                return successfulResult.getBodyString();
            });
        });
    }

    public void isStarredByCurrentUser(Repository repository, final GitHubApiCallback callback) {
        String url = STARRED_URL + "/" + repository.owner + "/" + repository.name;
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.get(result -> {
            GitHubApiUtil.handleResult(result, callback, null);
        });
    }

    public void starRepository(Repository repository, final GitHubApiCallback callback) {
        String url = STARRED_URL + "/" + repository.owner + "/" + repository.name;
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.setHeader("Content-Length", "0");
        connection.put(result -> {
            GitHubApiUtil.handleResult(result, callback, null);
        });
    }

    public void unStarRepository(Repository repository, final GitHubApiCallback callback) {
        String url = STARRED_URL + "/" + repository.owner + "/" + repository.name;
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.delete(result -> {
            GitHubApiUtil.handleResult(result, callback, null);
        });
    }

    public void isSubscribedByCurrentUser(Repository repository, final GitHubApiCallback callback) {
        String url = REPOSITORY_URL + "/" + repository.owner + "/" + repository.name + "/subscription";
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.get(result -> {
            GitHubApiUtil. handleResult(result, callback, null);
        });
    }

    public void subscribeRepository(Repository repository, final GitHubApiCallback callback) {
        String url = REPOSITORY_URL + "/" + repository.owner + "/" + repository.name + "/subscription";
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.addParams("subscribed", "true");
        connection.put(result -> {
            GitHubApiUtil.handleResult(result, callback, null);
        });
    }

    public void unSubscribeRepository(Repository repository, final GitHubApiCallback callback) {
        String url = REPOSITORY_URL + "/" + repository.owner + "/" + repository.name + "/subscription";
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.delete(result -> {
            GitHubApiUtil.handleResult(result, callback, null);
        });
    }

    public void getBitmapFromUrl(String url, final GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.get(result -> {
            GitHubApiUtil.handleResult(result, callback, successfulResult -> {
                byte[] bodyBytes = successfulResult.bodyBytes;
                return BitmapFactory.decodeByteArray(bodyBytes, 0, bodyBytes.length);
            });
        });
    }

    GitHubResourceManager(String token, ExecutorService service) {
        this.connectionPool = new HttpConnectionPool(service, token);
    }

}
