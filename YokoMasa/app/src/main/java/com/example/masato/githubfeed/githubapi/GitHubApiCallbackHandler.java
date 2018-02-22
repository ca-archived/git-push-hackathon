package com.example.masato.githubfeed.githubapi;

import android.util.Log;

import com.example.masato.githubfeed.http.ConnectionResult;
import com.example.masato.githubfeed.http.ResultBodyConverter;
import com.example.masato.githubfeed.model.GitHubObjectMapper;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
            gitHubApiResult.header = result.header;
            if (converter != null) {
                gitHubApiResult.resultObject = converter.convert(result);
                if (gitHubApiResult.resultObject != null) {
                    gitHubApiResult.isSuccessful = true;
                } else {
                    gitHubApiResult.isSuccessful = false;
                    gitHubApiResult.failure = Failure.MAP;
                    logHeader(result);
                }
            } else {
                gitHubApiResult.isSuccessful = true;
            }
        } else {
            if (result.responseCode != 404) {
                logHeader(result);
            }
            gitHubApiResult.isSuccessful = false;
            setErrorInfoToGitHubApiResult(result, gitHubApiResult);
        }
        callback.onApiResult(gitHubApiResult);
    }

    private static void logHeader(ConnectionResult result) {
        if (result.header == null) {
            return;
        }
        Log.e("gh_feed", "status code: " + result.responseCode);
        Log.e("gh_feed", "error header.");

        boolean isOctet = false;
        Set<String> keys = result.header.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            StringBuilder stringBuilder = new StringBuilder();
            for (String val : result.header.get(key)) {
                if (key != null & val != null) {
                    if (key.equals("Content-Type") && val.equals("application/octet-stream")) {
                        isOctet = true;
                    }
                }
                stringBuilder.append(val);
                stringBuilder.append(", ");
            }
            Log.e("gh_feed", "key: " + key + " val: " + stringBuilder.toString());
        }

        if (isOctet) {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0;i<result.bodyBytes.length;i++) {
                byte b = result.bodyBytes[i];
                stringBuilder.append(b);
                stringBuilder.append(",");
            }
            Log.e("gh_feed", "error bytes: " + stringBuilder.toString());
        } else {
            Log.e("gh_feed", "error stream: " + result.getBodyString());
        }
    }

    private static void setErrorInfoToGitHubApiResult(ConnectionResult result, GitHubApiResult gitHubApiResult) {
        if (result.isConnectionSuccessful) {
            gitHubApiResult.failure = failureFromStatusCode(result.responseCode);
            gitHubApiResult.errorMessage = GitHubObjectMapper.mapErrorMessage(result.getBodyString());
        } else {
            gitHubApiResult.failure = Failure.INTERNET;
        }
    }

    private static boolean isOk(ConnectionResult result) {
        if (result.isConnectionSuccessful) {
            return 200 <= result.responseCode && result.responseCode < 300;
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
                return Failure.SERVER;
            default:
                return Failure.UNEXPECTED;
        }
    }

    private GitHubApiCallbackHandler() {}
}
