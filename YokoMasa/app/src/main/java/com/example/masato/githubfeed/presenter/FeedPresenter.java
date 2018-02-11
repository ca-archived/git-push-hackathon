package com.example.masato.githubfeed.presenter;

import android.util.Log;

import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.Profile;
import com.example.masato.githubfeed.model.event.Event;
import com.example.masato.githubfeed.navigator.Navigator;
import com.example.masato.githubfeed.view.FeedView;

import java.util.List;

/**
 * Created by Masato on 2018/01/19.
 */

public class FeedPresenter {

    private FeedView view;

    public void onCreate() {
        GitHubApi.getApi().fetchProfile(this::setProfileIfSucceeded);
        GitHubApi.getApi().fetchFeedUrl(this::startFeedFragmentIfSucceeded);
        test();
    }

    private void test() {
        GitHubApi.getApi().fetchEventList(result -> {
            if (result.isSuccessful) {
                List<Event> events = (List<Event>) result.resultObject;
                Log.i("gh_feed", events.get(0).content);
            }
        });
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
            view.showLogInView();
        } else {
            view.showToast(result.failure.textId);
        }
    }

    public void onLogOutSelected() {
        GitHubApi.getApi().deleteToken();
        view.showLogInView();
    }

    public void onGlobalFeedSelected() {
        view.closeDrawer();
        view.showGlobalFeed();
    }

    public FeedPresenter(FeedView feedView) {
        this.view = feedView;
    }
}
