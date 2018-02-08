package com.example.masato.githubfeed.presenter;

import android.os.Handler;
import android.os.Looper;

import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.navigator.Navigator;
import com.example.masato.githubfeed.view.BaseView;
import com.example.masato.githubfeed.view.MainView;

/**
 * Created by Masato on 2018/01/17.
 */

public class MainPresenter {

    private static final int ENTRANCE_LOGO_DURATION = 2000;
    private final MainView view;

    public void onResume() {
        waitASec();
    }

    private void waitASec() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            GitHubApi.getApi().checkIfTokenValid(this::navigateToFeedViewIfSucceeded);
        }, ENTRANCE_LOGO_DURATION);
    }

    private void navigateToFeedViewIfSucceeded(GitHubApiResult result) {
        if (result.isSuccessful) {
            view.showFeedListView();
        } else {
            if (result.failure == Failure.NOT_FOUND) {
                view.showLogInView();
            } else {
                view.showToast(result.failure.textId);
            }
        }
    }

    public MainPresenter(MainView view) {
        this.view = view;
    }
}
