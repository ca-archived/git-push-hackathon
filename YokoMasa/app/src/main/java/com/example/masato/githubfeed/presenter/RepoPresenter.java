package com.example.masato.githubfeed.presenter;

import android.util.Log;

import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.view.RepoView;

/**
 * Created by Masato on 2018/01/27.
 */

public class RepoPresenter {

    RepoView view;
    String repoUrl;

    public void onCreate() {
        GitHubApi.getApi().fetchRepository(repoUrl, new GitHubApiCallback() {
            @Override
            public void onApiSuccess(Object object) {
                Repository repository = (Repository) object;
                view.showRepo(repository);
            }

            @Override
            public void onApiFailure(Failure failure) {

            }
        });
    }

    public RepoPresenter(RepoView view, String url) {
        this.view = view;
        this.repoUrl = url;
    }
}
