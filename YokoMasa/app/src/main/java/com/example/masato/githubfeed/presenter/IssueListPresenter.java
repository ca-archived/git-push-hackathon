package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.Issue;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.view.IssueListView;
import com.example.masato.githubfeed.view.PaginatingListView;

import java.util.ArrayList;

/**
 * Created by Masato on 2018/02/03.
 */

public class IssueListPresenter extends PaginatingListPresenter<Issue> {

    private static final int FETCH_THRESHOLD = 15;
    private IssueListView view;
    private String url;
    private Repository repository;

    @Override
    public void onElementClicked(Issue element) {

    }

    @Override
    protected void onFetchElement(int page) {
        if (repository != null) {
            GitHubApi.getApi().fetchIssueList(repository, page, this::handleApiResult);
        } else {
            GitHubApi.getApi().fetchIssueList(url, page, this::handleApiResult);
        }
    }

    private void handleApiResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            ArrayList<Issue> issues = (ArrayList<Issue>) result.resultObject;
            onFetchedElements(issues, true);
        } else {
            onFetchedElements(null, false);
        }
    }

    public IssueListPresenter(PaginatingListView view, String url) {
        super(view, FETCH_THRESHOLD);
        this.view = (IssueListView) view;
        this.url = url;
    }

    public IssueListPresenter(PaginatingListView view, Repository repository) {
        super(view, FETCH_THRESHOLD);
        this.view = (IssueListView) view;
        this.repository = repository;
    }
}
