package com.example.masato.githubfeed.presenter;

import android.util.Log;

import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.FeedEntry;
import com.example.masato.githubfeed.view.FeedListView;
import com.example.masato.githubfeed.view.PaginatingListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/01/29.
 */

public class FeedListPresenter extends PaginatingListPresenter<FeedEntry> {

    private FeedListView view;
    private String url;

    @Override
    protected void onFetchElement(int page) {
        GitHubApi.getApi().fetchFeedList(this.url, page, this::handleResult);
    }

    private void handleResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            List<FeedEntry> feedEntries = (List<FeedEntry>) result.resultObject;
            onFetchedElements(feedEntries, true);
        } else {
            onFetchedElements(null, false);
            view.showToast(result.failure.textId);
        }
    }

    @Override
    public void onElementClicked(FeedEntry element) {
        view.startRepoView(element.repoUrl);
    }

    public FeedListPresenter(PaginatingListView view, String url) {
        super(view);
        this.view = (FeedListView) view;
        this.url = url;
    }
}
