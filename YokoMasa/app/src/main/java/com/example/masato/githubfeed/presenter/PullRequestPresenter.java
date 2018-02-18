package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.PullRequest;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.view.PullRequestView;

/**
 * Created by Masato on 2018/02/14.
 */

public class PullRequestPresenter {

    private PullRequestView view;

    private void handleResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            PullRequest pr = (PullRequest) result.resultObject;
            view.showPullRequest(pr);
            view.showRepoInfo(pr.repository);
        } else {
            view.showToast(result.failure.textId);
        }
    }

    public PullRequestPresenter(PullRequestView view, String url) {
        this.view = view;
        GitHubApi.getApi().fetchPullRequest(url, this::handleResult);
    }
}
