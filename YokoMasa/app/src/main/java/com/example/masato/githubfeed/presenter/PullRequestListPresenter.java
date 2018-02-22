package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.BaseModel;
import com.example.masato.githubfeed.model.PullRequest;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.view.PaginatingListView;
import com.example.masato.githubfeed.view.PullRequestListView;

import java.util.List;

/**
 * Created by Masato on 2018/02/08.
 */

public class PullRequestListPresenter extends PaginatingListPresenter {

    private Repository repository;
    private PullRequestListView view;

    @Override
    int onGetPaginatingItemViewType(BaseModel element) {
        return 0;
    }

    @Override
    public void onElementClicked(BaseModel element, int viewType) {
        PullRequest pr = (PullRequest) element;
        view.showPullRequest(pr);
    }

    @Override
    protected void onFetchElement(int page) {
        GitHubApi.getApi().fetchPullRequestList(repository, page, this::handleResult);
    }

    private void handleResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            List<BaseModel> prList = (List<BaseModel>) result.resultObject;
            onFetchedElements(prList, true);
        } else {
            onFetchedElements(null, false);
            view.showErrorView(result.failure, result.errorMessage);
        }
    }

    public PullRequestListPresenter(PaginatingListView view, Repository repository) {
        super(view);
        this.repository = repository;
        this.view = (PullRequestListView) view;
    }
}
