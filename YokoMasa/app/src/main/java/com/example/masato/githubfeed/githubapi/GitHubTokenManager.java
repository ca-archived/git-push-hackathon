package com.example.masato.githubfeed.githubapi;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.util.HandyHttpURLConnection;
import com.example.masato.githubfeed.util.HttpConnectionPool;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.ExecutorService;

/**
 * Created by Masato on 2018/01/19.
 */

public class GitHubTokenManager {

    private static String LOGIN_URL = "https://github.com/login/oauth/access_token";
    private static String PREF_TOKEN_KEY = "token";

    private Resources resources;
    private SharedPreferences preferences;
    private ExecutorService executorService;

    public void fetchToken(final String code, final GitHubApiCallback callback) {
        HandyHttpURLConnection connection = new HandyHttpURLConnection(LOGIN_URL, executorService);
        String clientId = resources.getString(R.string.client_id);
        String clientSecret = resources.getString(R.string.client_secret);

        connection.setHeader("Accept", "application/json");
        connection.addParams("client_id", clientId);
        connection.addParams("client_secret", clientSecret);
        connection.addParams("code", code);
        connection.postRequestBodyString(new HandyHttpURLConnection.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int statusCode, Object body) {
                if (200 <= statusCode && statusCode < 300) {
                    try {
                        String token = extractToken((String) body);
                        callback.onApiSuccess(token);
                    } catch (Exception exception) {
                        callback.onApiFailure(Failure.CREATING_TOKEN);
                    }
                } else {
                    callback.onApiFailure(Failure.CREATING_TOKEN);
                }
            }

            @Override
            public void onError(Failure failure) {
                callback.onApiFailure(failure);
            }
        });
    }

    private String extractToken(String body) throws IOException, JSONException {
        JSONObject jsonObject = new JSONObject(body);
        String token = jsonObject.getString("access_token");
        saveToken(token);
        return token;
    }

    private void saveToken(String token) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREF_TOKEN_KEY, token);
        editor.apply();
    }

    public void deleteToken() {
        saveToken("");
    }

    public boolean hasToken() {
        return getToken() != null;
    }

    public String getToken() {
        Log.i("gh_feed",  preferences.getString(PREF_TOKEN_KEY, ""));
        return preferences.getString(PREF_TOKEN_KEY, "");
    }

    GitHubTokenManager(Resources resources, SharedPreferences sharedPreferences, ExecutorService executorService){
        this.preferences = sharedPreferences;
        this.resources = resources;
        this.executorService = executorService;
    }

}
