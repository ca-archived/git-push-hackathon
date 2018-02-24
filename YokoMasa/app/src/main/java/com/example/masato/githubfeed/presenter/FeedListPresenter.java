package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.BaseModel;
import com.example.masato.githubfeed.model.FeedEntry;
import com.example.masato.githubfeed.navigator.Navigator;
import com.example.masato.githubfeed.view.FeedListView;
import com.example.masato.githubfeed.view.PaginatingListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/01/29.
 */

public class FeedListPresenter extends PaginatingListPresenter {

    private FeedListView view;
    private String url;

    @Override
    protected void onFetchElement(int page) {
        GitHubApi.getApi().fetchFeedList(this.url, page, this::handleResult);
    }

    @Override
    int onGetPaginatingItemViewType(BaseModel element) {
        return 0;
    }

    private void handleResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            List<BaseModel> feedEntries = (List<BaseModel>) result.resultObject;
            onFetchSucceeded(feedEntries);
        } else {
            onFetchFailed(result.failure, result.errorMessage);
            view.showErrorView(result.failure, result.errorMessage);
        }
    }

    @Override
    public void onElementClicked(BaseModel element, int viewType) {
        FeedEntry feedEntry = (FeedEntry) element;
        view.showRepo(feedEntry.repoUrl);
    }

    public FeedListPresenter(PaginatingListView view, String url) {
        super(view);
        this.view = (FeedListView) view;
        this.url = url;
    }
}
