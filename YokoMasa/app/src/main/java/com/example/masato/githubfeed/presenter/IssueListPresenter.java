package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.BaseModel;
import com.example.masato.githubfeed.model.Issue;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.view.IssueListView;
import com.example.masato.githubfeed.view.PaginatingListView;

import java.util.ArrayList;

/**
 * Created by Masato on 2018/02/03.
 */

public class IssueListPresenter extends PaginatingListPresenter {

    private IssueListView view;
    private String url;
    private Repository repository;

    @Override
    int onGetPaginatingItemViewType(BaseModel element) {
        return 0;
    }

    @Override
    public void onElementClicked(BaseModel element, int viewType) {
        Issue issue = (Issue) element;
        view.navigateToIssueView(issue);
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
            ArrayList<BaseModel> issues = (ArrayList<BaseModel>) result.resultObject;
            onFetchedElements(issues, true);
        } else {
            onFetchedElements(null, false);
        }
    }

    public IssueListPresenter(PaginatingListView view, String url) {
        super(view);
        this.view = (IssueListView) view;
        this.url = url;
    }

    public IssueListPresenter(PaginatingListView view, Repository repository) {
        super(view);
        this.view = (IssueListView) view;
        this.repository = repository;
    }
}
