package com.example.masato.githubfeed.presenter;

import android.util.Log;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.view.LoginView;

/**
 * Created by Masato on 2018/01/19.
 */

public class LoginPresenter implements Presenter, GitHubApiCallback {

    private LoginView view;

    @Override
    public void onResume() {

    }

    public void onLoginButtonPressed() {
        view.startBrowser();
    }

    @Override
    public void onSuccess(Object object) {
        GitHubApi.getApi().checkToken(new GitHubApiCallback() {
            @Override
            public void onSuccess(Object object) {
                view.showLoginSucceeded();
            }

            @Override
            public void onError(String message) {
                view.showLoginError(message);
            }
        });
    }

    @Override
    public void onError(String message) {
        view.showLoginError(message);
        Log.i("info", message);
    }

    public void onCodeFetched(String code) {
        view.showLoginWaiting();
        GitHubApi.getApi().fetchToken(code, this);
    }

    public LoginPresenter(LoginView view) {
        this.view = view;
    }
}
