package com.example.masato.githubfeed.githubapi;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.masato.githubfeed.model.FeedEntry;
import com.example.masato.githubfeed.model.GitHubObjectMapper;
import com.example.masato.githubfeed.util.HandyHttpURLConnection;
import com.example.masato.githubfeed.util.HttpConnectionPool;

import java.net.HttpURLConnection;
import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * Created by Masato on 2018/01/19.
 */

public class GitHubResourceManager {

    private static final String GLOBAL_FEED_URL = "https://github.com/timeline";
    private static final String BASE_URL = "https://api.github.com";
    private static final String PROFILE_URL = BASE_URL + "/user";
    private static final String FEEDS_URL = BASE_URL + "/feeds";

    private HttpConnectionPool connectionPool;

    public void getProfile(final GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection(PROFILE_URL);
        connection.getRequestBodyString(new HandyHttpURLConnection.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int statusCode, Object body) {
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    callback.onSuccess(body);
                } else {
                    callback.onError("http error");
                }
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
    }

    public void getFeedUrls(final GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection("https://api.github.com/feeds");
        //connection.setHeader("Accept", "application/atom+xml");
        connection.getRequestBodyString(new HandyHttpURLConnection.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int statusCode, Object body) {
                String bodyString = (String) body;
                Log.i("gh_info", bodyString);
                callback.onSuccess(GitHubObjectMapper.extractFeedUrls(bodyString));
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
                Log.i("gh_info", message);
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
                if (statusCode == HttpURLConnection.HTTP_OK) {
                    String bodyString = (String) body;
                    List<FeedEntry> feedEntries = GitHubObjectMapper.generateFeedEntries(bodyString);
                    callback.onSuccess(feedEntries);
                } else {
                    callback.onError("http error");
                }
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
    }

    public void getBitmapFromUrl(String url, final GitHubApiCallback callback) {
        HandyHttpURLConnection connection = connectionPool.newConnection(url);
        connection.getRequestBodyBytes(new HandyHttpURLConnection.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int statusCode, Object body) {
                byte[] data = (byte[]) body;
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                callback.onSuccess(bitmap);
            }

            @Override
            public void onError(String message) {
                callback.onError(message);
            }
        });
    }

    GitHubResourceManager(ExecutorService service, String token) {
        this.connectionPool = new HttpConnectionPool(service, token);
    }

}
