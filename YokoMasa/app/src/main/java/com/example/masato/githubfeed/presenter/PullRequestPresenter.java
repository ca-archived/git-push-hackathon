package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.PullRequest;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.view.PullRequestView;

/**
 * Created by Masato on 2018/02/14.
 */

public class PullRequestPresenter extends BasePresenter {

    private PullRequestView view;
    private String url;

    @Override
    public void tryAgain() {
        view.showLoadingView();
        view.hideErrorView();
        GitHubApi.getApi().fetchPullRequest(url, this::handleResult);
    }

    private void handleResult(GitHubApiResult result) {
        view.hideLoadingView();
        if (result.isSuccessful) {
            PullRequest pr = (PullRequest) result.resultObject;
            view.showPullRequest(pr);
            view.showRepoInfo(pr.repository);
        } else {
            view.showErrorView(result.failure, result.errorMessage);
        }
    }

    public PullRequestPresenter(PullRequestView view, String url) {
        this.view = view;
        this.url = url;
        view.showLoadingView();
        GitHubApi.getApi().fetchPullRequest(url, this::handleResult);
    }
}
