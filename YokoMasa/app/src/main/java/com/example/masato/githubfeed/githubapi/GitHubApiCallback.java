package com.example.masato.githubfeed.githubapi;

/**
 * Created by Masato on 2018/01/19.
 */

public interface GitHubApiCallback {

    public void onSuccess(Object object);

    public void onError(String message);
}
