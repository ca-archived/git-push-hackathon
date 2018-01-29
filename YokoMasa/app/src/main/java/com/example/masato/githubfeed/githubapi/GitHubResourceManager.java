package com.example.masato.githubfeed.githubapi;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.masato.githubfeed.model.GitHubObjectMapper;
import com.example.masato.githubfeed.model.Profile;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.presenter.RepoPresenter;
import com.example.masato.githubfeed.util.HandyHttpURLConnection;
import com.example.masato.githubfeed.util.HttpConnectionPool;

import java.net.HttpURLConnection;
import java.util.concurrent.ExecutorService;

/**
 * Created by Masato on 2018/01/19.
 */

public class GitHubResourceManager {

    private static final String PROFILE_URL = "https://api.github.com/user";
    private static final String STARRED_URL = "https://api.github.com/user/starred";

    private HttpConnectionPool connectionPool;

    public void setToken(String token) {
        connectionPool.setToken(token);
    }

    public void getProfile(final GitHubApiCallback callback) {
        final HandyHttpURLConnection connection = connectionPool.newConnection(PROFILE_URL);
        connection.getRequestBodyString(new HandyHttpURLConnection.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int statusCode, Object content) {
                String bodyString = (String) content;
                Profile profile = GitHubObjectMapper.mapProfile(bodyString);
                setProfileIcon(profile, callback);
            }

            @Override
            public void onError(Failure failure) {
                callback.onApiFailure(failure);
            }
        });
    }

    private void setProfileIcon(final Profile profile, final GitHubApiCallback callback) {
        getBitmapFromUrl(profile.iconUrl, new GitHubApiCallback() {
            @Override
            public void onApiSuccess(Object object) {
                Bitmap icon = (Bitmap) object;
                profile.icon = icon;
                callback.onApiSuccess(profile);
            }

            @Override
            public void onApiFailure(Failure failure) {
                callback.onApiFailure(failure);
            }
        });
    }

    public void getFeedUrl(final GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection("https://api.github.com/feeds");
        connection.getRequestBodyString(new HandyHttpURLConnection.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int statusCode, Object content) {
                String bodyString = (String) content;
                Object feedUrl = GitHubObjectMapper.mapFeedUrl(bodyString);
                handleResponse(statusCode, feedUrl, callback);
            }

            @Override
            public void onError(Failure failure) {
                callback.onApiFailure(failure);
            }
        });
    }

    public void getFeedEntries(String url, int page, final GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.setHeader("Accept", "application/atom+xml");
        connection.addParams("page", Integer.toString(page));
        connection.getRequestBodyString(new HandyHttpURLConnection.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int statusCode, Object body) {
                String bodyString = (String) body;
                Object feedEntries = GitHubObjectMapper.mapFeedEntries(bodyString);
                handleResponse(statusCode, feedEntries, callback);
            }

            @Override
            public void onError(Failure failure) {
                callback.onApiFailure(failure);
            }
        });
    }

    public void getRepository(String url, final GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.getRequestBodyString(new HandyHttpURLConnection.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int statusCode, Object body) {
                Object repo = GitHubObjectMapper.mapRepository((String) body);
                handleResponse(statusCode, repo, callback);
            }

            @Override
            public void onError(Failure failure) {
                callback.onApiFailure(failure);
            }
        });
    }

    public void getReadMeHtml(Repository repository, final GitHubApiCallback callback) {
        String url = repository.baseUrl + "/contents/README.md";
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.setHeader("Accept", "application/vnd.github.html");
        connection.getRequestBodyString(new HandyHttpURLConnection.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int statusCode, Object body) {
                handleResponse(statusCode, body, callback);
            }

            @Override
            public void onError(Failure failure) {
                callback.onApiFailure(failure);
            }
        });
    }

    public void isStarredByCurrentUser(Repository repository, final GitHubApiCallback callback) {
        String url = STARRED_URL + "/" + repository.owner + "/" + repository.name;
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.getRequestBodyString(new HandyHttpURLConnection.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int statusCode, Object body) {
                handleResponse(statusCode, body, callback);
            }

            @Override
            public void onError(Failure failure) {
                callback.onApiFailure(failure);
            }
        });
    }

    public void starRepository(Repository repository, final GitHubApiCallback callback) {
        String url = STARRED_URL + "/" + repository.owner + "/" + repository.name;
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.setHeader("Content-Length", "0");
        connection.putRequestBodyString(new HandyHttpURLConnection.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int statusCode, Object body) {
                handleResponse(statusCode, body, callback);
            }

            @Override
            public void onError(Failure failure) {
                callback.onApiFailure(failure);
            }
        });
    }

    public void unStarRepository(Repository repository, final GitHubApiCallback callback) {
        String url = STARRED_URL + "/" + repository.owner + "/" + repository.name;
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.delete(new HandyHttpURLConnection.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int statusCode, Object body) {
                handleResponse(statusCode, body, callback);
            }

            @Override
            public void onError(Failure failure) {
                callback.onApiFailure(failure);
            }
        });
    }

    public void getBitmapFromUrl(String url, final GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.getRequestBodyBytes(new HandyHttpURLConnection.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int statusCode, Object body) {
                byte[] data = (byte[]) body;
                Object bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                handleResponse(statusCode, bitmap, callback);
            }

            @Override
            public void onError(Failure failure) {
                callback.onApiFailure(failure);
            }
        });
    }

    private void handleResponse(int statusCode, Object content, GitHubApiCallback callback) {
        if (isOk(statusCode)) {
            callback.onApiSuccess(content);
        } else {
            callback.onApiFailure(failureFromStatusCode(statusCode));
        }
    }

    private boolean isOk(int statusCode) {
        return 200 <= statusCode && statusCode < 300;
    }

    private Failure failureFromStatusCode(int statusCode) {
        if (400 <= statusCode && statusCode < 500) {
            return Failure.INVALID_TOKEN;
        } else if (statusCode == 500) {
            return Failure.SERVER;
        }
        return Failure.UNEXPECTED;
    }

    GitHubResourceManager(String token, ExecutorService service) {
        this.connectionPool = new HttpConnectionPool(service, token);
    }

}
