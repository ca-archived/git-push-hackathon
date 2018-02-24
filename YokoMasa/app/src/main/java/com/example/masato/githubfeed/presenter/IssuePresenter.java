package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.Issue;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.view.IssueView;

/**
 * Created by Masato on 2018/02/14.
 */

public class IssuePresenter extends BasePresenter {

    private IssueView view;
    private String url;

    @Override
    public void tryAgain() {
        view.hideErrorView();
        view.showLoadingView();
        GitHubApi.getApi().fetchIssue(url, this::handleFetchIssueResult);
    }

    private void handleFetchIssueResult(GitHubApiResult result) {
        view.hideLoadingView();
        if (result.isSuccessful) {
            Issue issue = (Issue) result.resultObject;
            view.showIssue(issue);
            GitHubApi.getApi().fetchRepository(issue.repoUrl, this::handleFetchRepoResult);
        } else {
            view.showErrorView(result.failure, result.errorMessage);
        }
    }

    private void handleFetchRepoResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            Repository repository = (Repository) result.resultObject;
            view.showRepoInfo(repository);
        } else {
            view.showToast(result.failure.textId);
        }
    }

    public IssuePresenter(IssueView view, String url) {
        this.view = view;
        this.url = url;
        view.showLoadingView();
        GitHubApi.getApi().fetchIssue(url, this::handleFetchIssueResult);
    }
}
