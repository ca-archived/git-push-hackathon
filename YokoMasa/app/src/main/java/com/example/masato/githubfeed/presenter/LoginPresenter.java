package com.example.masato.githubfeed.presenter;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.model.Profile;
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
    public void onApiSuccess(Object object) {
        GitHubApi.getApi().fetchProfile(new GitHubApiCallback() {
            @Override
            public void onApiSuccess(Object object) {
                Profile profile = (Profile) object;
                execSuccessFlow(profile);
            }

            @Override
            public void onApiFailure(Failure failure) {
                view.showLoginError(failure);
            }
        });
    }

    @Override
    public void onApiFailure(Failure failure) {
        view.showLoginError(failure);
    }

    private void execSuccessFlow(Profile profile) {
        view.showProfile(profile);
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                view.navigateToFeedView();
            }
        }, 2000);
    }

    public void onCodeFetched(String code) {
        view.showLoginWaiting();
        GitHubApi.getApi().requestToken(code, this);
    }

    public LoginPresenter(LoginView view) {
        this.view = view;
    }
}
