package com.example.masato.githubfeed.githubapi;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.util.Base64;
import android.util.Log;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.http.HandyHttpURLConnection;
import com.example.masato.githubfeed.util.GitHubApiUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.concurrent.ExecutorService;

/**
 * Created by Masato on 2018/01/19.
 */

public class GitHubTokenManager {

    private static String LOGIN_URL = "https://github.com/login/oauth/access_token";
    private static String AUTH_CHECK_URL = "https://api.github.com/applications";
    private static String PREF_TOKEN_KEY = "token";

    private Resources resources;
    private SharedPreferences preferences;
    private ExecutorService executorService;

    public void checkIfTokenValid(final GitHubApiCallback callback) {
        String clientId = resources.getString(R.string.client_id);
        String clientSecret = resources.getString(R.string.client_secret);
        String rawAuthString = clientId + ":" + clientSecret;
        String authString = "Basic " + Base64.encodeToString(rawAuthString.getBytes(), Base64.NO_WRAP);

        String url = AUTH_CHECK_URL + "/" + clientId + "/tokens/" + getToken();
        HandyHttpURLConnection connection = new HandyHttpURLConnection(url, executorService);
        connection.setHeader("Authorization", authString);
        connection.get(result -> {
            GitHubApiUtil.handleResult(result, callback, null);
        });
    }

    public void fetchToken(final String code, final GitHubApiCallback callback) {
        HandyHttpURLConnection connection = new HandyHttpURLConnection(LOGIN_URL, executorService);
        String clientId = resources.getString(R.string.client_id);
        String clientSecret = resources.getString(R.string.client_secret);

        connection.setHeader("Accept", "application/json");
        connection.addParams("client_id", clientId);
        connection.addParams("client_secret", clientSecret);
        connection.addParams("code", code);
        connection.post(result -> {
            GitHubApiUtil.handleResult(result, callback, successfulResult -> {
                return extractToken(successfulResult.getBodyString());
            });
        });
    }

    private String extractToken(String body) {
        try {
            JSONObject jsonObject = new JSONObject(body);
            String token = jsonObject.getString("access_token");
            saveToken(token);
            return token;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
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
