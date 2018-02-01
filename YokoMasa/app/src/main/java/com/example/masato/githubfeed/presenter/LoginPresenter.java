package com.example.masato.githubfeed.presenter;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.Profile;
import com.example.masato.githubfeed.view.LoginView;

/**
 * Created by Masato on 2018/01/19.
 */

public class LoginPresenter implements Presenter {

    private LoginView view;

    @Override
    public void onResume() {

    }

    public void onLoginButtonPressed() {
        view.startBrowser();
    }

    public void onCodeFetched(String code) {
        view.showLoginWaiting();
        view.disableLogInButton();
        GitHubApi.getApi().requestToken(code, this::execSuccessFlowIfSucceeded);
    }

    private void execSuccessFlowIfSucceeded(GitHubApiResult result) {
        if (result.isSuccessful) {
            execSuccessFlow();
        } else {
            view.enableLogInButton();
            view.showLoginError(result.failure);
        }
    }

    private void execSuccessFlow() {
        GitHubApi.getApi().fetchProfile(this::navigateToFeedViewIfSucceeded);
    }

    private void navigateToFeedViewIfSucceeded(GitHubApiResult result) {
        if (result.isSuccessful) {
            Profile profile = (Profile) result.resultObject;
            navigateToFeedView(profile);
        } else {
            view.enableLogInButton();
            view.showLoginError(result.failure);
        }
    }

    private void navigateToFeedView(Profile profile) {
        view.showProfile(profile);
        new Handler(Looper.getMainLooper()).postDelayed(view::navigateToFeedView, 2000);
    }

    public LoginPresenter(LoginView view) {
        this.view = view;
    }
}
