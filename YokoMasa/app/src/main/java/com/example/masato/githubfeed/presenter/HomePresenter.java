package com.example.masato.githubfeed.presenter;

import android.graphics.Bitmap;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.Profile;
import com.example.masato.githubfeed.view.HomeView;

/**
 * Created by Masato on 2018/01/19.
 */

public class HomePresenter {

    private HomeView view;
    private Profile profile;

    public void onLogOutSelected() {
        GitHubApi.getApi().deleteToken();
        view.showLogInView();
    }

    public void onGlobalFeedSelected() {
        view.closeDrawer();
        view.showGlobalFeed();
    }

    private void handleFetchProfileResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            profile = (Profile) result.resultObject;
            GitHubApi.getApi().fetchBitmap(profile.iconUrl, this::handleFetchProfileIconResult);
        }
    }

    private void handleFetchProfileIconResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            Bitmap bitmap = (Bitmap) result.resultObject;
            profile.icon = bitmap;
            view.setUpContent(profile);
            view.hideLoadingView();
        }
    }

    public HomePresenter(HomeView feedView) {
        this.view = feedView;
        GitHubApi.getApi().fetchProfile(this::handleFetchProfileResult);
    }
}
