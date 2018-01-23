package com.example.masato.githubfeed.presenter;

import android.os.Handler;
import android.os.Looper;

import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.view.MainView;

/**
 * Created by Masato on 2018/01/17.
 */

public class MainPresenter implements Presenter, GitHubApiCallback {

    private final MainView view;

    @Override
    public void onResume() {
        waitASec();
    }

    private void waitASec() {
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                view.initGitHubApi();
                GitHubApi.getApi().fetchProfile(MainPresenter.this);
            }
        }, 2000);
    }

    @Override
    public void onApiSuccess(Object object) {
        view.navigateToFeedView();
    }

    @Override
    public void onApiFailure(Failure failure) {
        if (failure == Failure.INVALID_TOKEN) {
            view.navigateToLogInView();
        } else {
            view.showToast(failure.textId);
        }
    }

    public MainPresenter(MainView view) {
        this.view = view;
    }
}
