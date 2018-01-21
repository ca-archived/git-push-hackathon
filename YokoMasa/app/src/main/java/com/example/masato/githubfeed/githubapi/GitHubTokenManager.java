package com.example.masato.githubfeed.githubapi;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Looper;

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

    private static String CLIENT_ID = "client_id";
    private static String CLIENT_SECRET = "client_secret";
    private static String LOGIN_URL = "https://github.com/login/oauth/access_token";
    private static String PREF_TOKEN_KEY = "token";

    private SharedPreferences preferences;
    private ExecutorService executorService;

    public void fetchToken(final String code, final GitHubApiCallback callback) {
        HandyHttpURLConnection connection = new HandyHttpURLConnection(LOGIN_URL, executorService);
        connection.setHeader("Accept", "application/json");
        connection.addParams("client_id", CLIENT_ID);
        connection.addParams("client_secret", CLIENT_SECRET);
        connection.addParams("code", code);
        connection.postRequestBodyString(new HandyHttpURLConnection.OnHttpResponseListener() {
            @Override
            public void onHttpResponse(int statusCode, Object body) {
                if (statusCode == HttpURLConnection.HTTP_CREATED
                        || statusCode == HttpURLConnection.HTTP_OK) {
                    try {
                        String token = handleResponseBody((String) body);
                        callback.onSuccess(token);
                    } catch (Exception exeption) {
                        callback.onError("Failed to create token.");
                    }
                } else {
                    callback.onError("Failed to create token.");
                }
            }

            @Override
            public void onError(String message) {
                callback.onError("Failed to create token.");
            }
        });
    }

    private String handleResponseBody(String body) throws IOException, JSONException {
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

    public boolean hasToken() {
        return getToken() != null;
    }

    public String getToken() {
        return preferences.getString(PREF_TOKEN_KEY, null);
    }

    GitHubTokenManager(SharedPreferences preferences, ExecutorService executorService){
        this.preferences = preferences;
        this.executorService = executorService;
    }

}
