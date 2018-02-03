package com.example.masato.githubfeed.presenter;

import android.util.Log;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.view.RepoView;

/**
 * Created by Masato on 2018/01/27.
 */

public class RepoPresenter {

    private RepoView view;
    private String repoUrl;
    private Repository repository;
    private boolean isStarred;
    private boolean isSubscribed;

    private void handleFetchRepositoryResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            Repository repository = (Repository) result.resultObject;
            RepoPresenter.this.repository = repository;
            view.setUpContent(repository);
        }
    }

    public RepoPresenter(RepoView view, String url) {
        this.view = view;
        this.repoUrl = url;
        GitHubApi.getApi().fetchRepository(repoUrl, this::handleFetchRepositoryResult);
    }

    public RepoPresenter(RepoView view, Repository repository) {
        this.view = view;
        this.repository = repository;
        view.setUpContent(repository);
    }
}
