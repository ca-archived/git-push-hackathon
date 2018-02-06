package com.example.masato.githubfeed.presenter;

import android.os.Parcel;
import android.os.Parcelable;
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

public class FeedListPresenter extends PaginatingListPresenter {

    private FeedListView view;
    private String url;

    @Override
    protected void onFetchElement(int page) {
        GitHubApi.getApi().fetchFeedList(this.url, page, this::handleResult);
    }

    @Override
    int onGetPaginatingItemViewType(Parcelable parcelable) {
        return 0;
    }

    private void handleResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            List<Parcelable> feedEntries = (List<Parcelable>) result.resultObject;
            onFetchedElements(feedEntries, true);
        } else {
            onFetchedElements(null, false);
            view.showToast(result.failure.textId);
        }
    }

    @Override
    public void onElementClicked(Parcelable element, int viewType) {
        FeedEntry feedEntry = (FeedEntry) element;
        view.startRepoView(feedEntry.repoUrl);
    }

    public FeedListPresenter(PaginatingListView view, String url) {
        super(view);
        this.view = (FeedListView) view;
        this.url = url;
    }
}
