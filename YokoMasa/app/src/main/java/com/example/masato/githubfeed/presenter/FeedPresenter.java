package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.Profile;
import com.example.masato.githubfeed.view.FeedView;

/**
 * Created by Masato on 2018/01/19.
 */

public class FeedPresenter implements Presenter {

    private FeedView view;

    public void onCreate() {
        GitHubApi.getApi().fetchProfile(this::setProfileIfSucceeded);
        GitHubApi.getApi().fetchFeedUrl(this::startFeedFragmentIfSucceeded);
    }

    private void setProfileIfSucceeded(GitHubApiResult result) {
        if (result.isSuccessful) {
            Profile profile = (Profile) result.resultObject;
            view.setProfile(profile);
        } else {
            onApiFailure(result);
        }
    }

    private void startFeedFragmentIfSucceeded(GitHubApiResult result) {
        if (result.isSuccessful) {
            String feedUrl = (String) result.resultObject;
            view.startFeedFragment(feedUrl);
        } else {
            onApiFailure(result);
        }
    }

    private void onApiFailure(GitHubApiResult result) {
        if (result.failure == Failure.INVALID_TOKEN) {
            view.navigateToLogInView();
        } else {
            view.showToast(result.failure.textId);
        }
    }

    public void onLogOutSelected() {
        GitHubApi.getApi().deleteToken();
        view.navigateToLogInView();
    }

    public void onGlobalFeedSelected() {
        view.closeDrawer();
        view.navigateToGlobalFeedView();
    }

    @Override
    public void onResume() {

    }

    public FeedPresenter(FeedView feedView) {
        this.view = feedView;
    }
}
