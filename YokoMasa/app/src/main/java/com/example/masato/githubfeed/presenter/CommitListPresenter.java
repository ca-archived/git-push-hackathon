package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.BaseModel;
import com.example.masato.githubfeed.model.Comment;
import com.example.masato.githubfeed.view.PaginatingListView;

import java.util.List;

/**
 * Created by Masato on 2018/02/06.
 */

public class CommitListPresenter extends PaginatingListPresenter {

    private String url;

    @Override
    int onGetPaginatingItemViewType(BaseModel element) {
        return 0;
    }

    @Override
    public void onElementClicked(BaseModel element, int viewType) {

    }

    @Override
    protected void onFetchElement(int page) {
        GitHubApi.getApi().fetchCommitList(url, page, this::handleResult);
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
        this.url = url;
    }
}
