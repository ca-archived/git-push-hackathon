package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.model.FeedEntry;
import com.example.masato.githubfeed.model.Profile;
import com.example.masato.githubfeed.view.FeedView;
import com.example.masato.githubfeed.view.adapter.FeedRecyclerViewAdapter;

import java.util.List;
import java.util.Map;

/**
 * Created by Masato on 2018/01/19.
 */

public class FeedPresenter implements Presenter {

    private FeedView view;

    public void onCreate() {
        GitHubApi.getApi().fetchProfile(new GitHubApiCallback() {
            @Override
            public void onApiSuccess(Object object) {
                Profile profile = (Profile) object;
                view.setProfile(profile);
            }

            @Override
            public void onApiFailure(Failure failure) {
                onApiFailure(failure);
            }
        });
        GitHubApi.getApi().fetchFeedUrls(new GitHubApiCallback() {
            @Override
            public void onApiSuccess(Object object) {
                Map<String, String> feedUrls = (Map<String, String>) object;
                view.preparePager(feedUrls);
            }

            @Override
            public void onApiFailure(Failure failure) {
                onApiFailure(failure);
            }
        });
    }

    public void onApiFailure(Failure failure) {
        if (failure == Failure.INVALID_TOKEN) {
            view.navigateToLogInView();
        } else {
            view.showToast(failure.textId);
        }
    }

    public void onLogOut() {
        GitHubApi.getApi().deleteToken();
        view.navigateToLogInView();
    }

    @Override
    public void onResume() {

    }

    public FeedPresenter(FeedView feedView) {
        this.view = feedView;
    }
}
