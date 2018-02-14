package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.PullRequest;
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
        }
    }

    public PullRequestPresenter(PullRequestView view, PullRequest pr) {
        this.view = view;
        if (pr.bodyHtml.equals("")) {
            GitHubApi.getApi().fetchPullRequest(pr.url, this::handleResult);
        } else {
            view.showPullRequest(pr);
        }
    }
}
