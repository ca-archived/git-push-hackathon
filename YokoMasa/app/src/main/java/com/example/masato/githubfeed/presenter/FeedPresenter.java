package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.model.FeedEntry;
import com.example.masato.githubfeed.view.FeedView;
import com.example.masato.githubfeed.view.adapter.FeedRecyclerViewAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by Masato on 2018/01/19.
 */

public class FeedPresenter implements Presenter, GitHubApiCallback {

    private FeedView view;

    public void onCreate() {
        GitHubApi.getApi().fetchFeedUrls(this);
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onApiSuccess(Object object) {
        Map<String, String> feedUrls = (Map<String, String>) object;
        view.preparePager(feedUrls);
    }

    @Override
    public void onApiFailure(Failure failure) {
        if (failure == Failure.INVALID_TOKEN) {
            view.navigateToLogInView();
        } else {
            view.showToast(failure.textId);
        }
    }

    public FeedPresenter(FeedView feedView) {
        this.view = feedView;
    }
}
