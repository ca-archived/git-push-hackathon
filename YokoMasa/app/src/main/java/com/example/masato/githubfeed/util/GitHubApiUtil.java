package com.example.masato.githubfeed.util;

import android.util.Log;

import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.githubapi.GitHubResourceManager;
import com.example.masato.githubfeed.http.ConnectionResult;
import com.example.masato.githubfeed.http.ResultBodyConverter;

/**
 * Created by Masato on 2018/01/31.
 */

public class GitHubApiUtil {

    public static void handleResult(ConnectionResult result, GitHubApiCallback callback, ResultBodyConverter converter) {
        GitHubApiResult gitHubApiResult = new GitHubApiResult();
        if (isOk(result)) {
            if (converter != null) {
                gitHubApiResult.resultObject = converter.convert(result);
            }
            gitHubApiResult.header = result.header;
            gitHubApiResult.isSuccessful = true;
        } else {
            Log.e("gh_feed", "error stream: " + result.getBodyString());
            gitHubApiResult.isSuccessful = false;
            setFailureToResult(result, gitHubApiResult);
        }
        callback.onApiResult(gitHubApiResult);
    }

    private static GitHubApiResult setFailureToResult(ConnectionResult result, GitHubApiResult gitHubApiResult) {
        if (result.isConnectionSuccessful) {
            gitHubApiResult.failure = failureFromStatusCode(result.responseCode);
        } else {
            gitHubApiResult.failure = Failure.INTERNET;
        }
        return gitHubApiResult;
    }

    public static boolean isOk(ConnectionResult result) {
        if (result.isConnectionSuccessful) {
            return 200 <= result.responseCode && result.responseCode < 400;
        } else {
            return false;
        }
    }

    public static Failure failureFromStatusCode(int statusCode) {
        switch (statusCode) {
            case 403:
                return Failure.INVALID_TOKEN;
            case 404:
                return Failure.NOT_FOUND;
            case 500:
                return Failure.UNEXPECTED;
            default:
                return Failure.UNEXPECTED;
        }
    }

    private GitHubApiUtil() {}
}
