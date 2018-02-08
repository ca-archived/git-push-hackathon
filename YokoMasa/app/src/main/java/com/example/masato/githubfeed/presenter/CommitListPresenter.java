package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.BaseModel;
import com.example.masato.githubfeed.model.Commit;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.navigator.Navigator;
import com.example.masato.githubfeed.view.PaginatingListView;
import com.example.masato.githubfeed.view.fragment.CommitListView;

import java.util.List;

/**
 * Created by Masato on 2018/02/06.
 */

public class CommitListPresenter extends PaginatingListPresenter {

    private String url;
    private Repository repository;
    private CommitListView view;

    @Override
    int onGetPaginatingItemViewType(BaseModel element) {
        return 0;
    }

    @Override
    public void onElementClicked(BaseModel element, int viewType) {
        Commit commit = (Commit) element;
        commit.repository = repository;
        view.showCommit(commit);
    }

    @Override
    protected void onFetchElement(int page) {
        if (repository != null) {
            GitHubApi.getApi().fetchCommitList(repository, page, this::handleResult);
        } else {
            GitHubApi.getApi().fetchCommitList(url, page, this::handleResult);
        }
    }

    private void handleResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            List<BaseModel> commits = (List<BaseModel>) result.resultObject;
            onFetchedElements(commits, true);
        } else {
            onFetchedElements(null, false);
        }
    }

    public CommitListPresenter(PaginatingListView view, String url) {
        super(view);
        this.view = (CommitListView) view;
        this.url = url;
        setFetchThreshold(25);

    }

    public CommitListPresenter(PaginatingListView view, Repository repository) {
        super(view);
        this.view = (CommitListView) view;
        this.repository = repository;
        setFetchThreshold(25);
    }
}
