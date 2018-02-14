package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.Issue;
import com.example.masato.githubfeed.view.IssueView;

/**
 * Created by Masato on 2018/02/14.
 */

public class IssuePresenter {

    private IssueView view;

    private void handleResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            Issue issue = (Issue) result.resultObject;
            view.showIssue(issue);
        }
    }

    public IssuePresenter(IssueView view, Issue issue) {
        this.view = view;
        if (issue.bodyHtml.equals("")) {
            GitHubApi.getApi().fetchIssue(issue.url, this::handleResult);
        } else {
            view.showIssue(issue);
        }
    }
}
