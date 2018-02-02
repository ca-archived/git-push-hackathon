package com.example.masato.githubfeed.githubapi;

import android.util.Log;

import com.example.masato.githubfeed.http.ConnectionResult;
import com.example.masato.githubfeed.http.ResultBodyConverter;

/**
 * Created by Masato on 2018/01/31.
 *
 * 外部ではhttpの処理はしてほしくないため、ConnectionResultをもっと扱いやすいGitHubApiResultに変換
 * してcallbackに通知する。http通信自体の失敗、または通信成功してもレスポンスコード400~500の場合ははまとめて
 * Failureにして、listenerにはとりあえず失敗した事実と原因を伝える。
 *
 */

class GitHubApiCallbackHandler {

    static void handleResult(ConnectionResult result, GitHubApiCallback callback, ResultBodyConverter converter) {
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
            setFailureToGitHubApiResult(result, gitHubApiResult);
        }
        callback.onApiResult(gitHubApiResult);
    }

    private static void setFailureToGitHubApiResult(ConnectionResult result, GitHubApiResult gitHubApiResult) {
        if (result.isConnectionSuccessful) {
            gitHubApiResult.failure = failureFromStatusCode(result.responseCode);
        } else {
            gitHubApiResult.failure = Failure.INTERNET;
        }
    }

    private static boolean isOk(ConnectionResult result) {
        if (result.isConnectionSuccessful) {
            return 200 <= result.responseCode && result.responseCode < 400;
        } else {
            return false;
        }
    }

    private static Failure failureFromStatusCode(int statusCode) {
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

    private GitHubApiCallbackHandler() {}
}
