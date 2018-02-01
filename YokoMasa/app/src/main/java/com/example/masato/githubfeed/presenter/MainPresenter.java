package com.example.masato.githubfeed.presenter;

import android.os.Handler;
import android.os.Looper;
import android.util.Base64;

import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.view.MainView;

/**
 * Created by Masato on 2018/01/17.
 */

public class MainPresenter implements Presenter{

    private final MainView view;

    @Override
    public void onResume() {
        view.initGitHubApi();
        waitASec();
    }

    private void waitASec() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            GitHubApi.getApi().checkIfTokenValid(this::navigateToFeedViewIfSucceeded);
        }, 2000);
    }

    private void navigateToFeedViewIfSucceeded(GitHubApiResult result) {
        if (result.isSuccessful) {
            view.navigateToFeedView();
        } else {
            if (result.failure == Failure.NOT_FOUND) {
                view.navigateToLogInView();
            } else {
                view.showToast(result.failure.textId);
            }
        }
    }

    public MainPresenter(MainView view) {
        this.view = view;
    }
}
