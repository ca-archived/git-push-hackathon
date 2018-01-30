package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.model.Profile;
import com.example.masato.githubfeed.view.FeedView;

/**
 * Created by Masato on 2018/01/19.
 */

public class FeedPresenter implements Presenter {

    private FeedView view;

    public void onCreate(boolean shouldStartFeedFragment) {
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
        if (!shouldStartFeedFragment) {
            return;
        }
        GitHubApi.getApi().fetchFeedUrl(new GitHubApiCallback() {
            @Override
            public void onApiSuccess(Object object) {
                String feedUrl = (String) object;
                view.startFeedFragment(feedUrl);
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
