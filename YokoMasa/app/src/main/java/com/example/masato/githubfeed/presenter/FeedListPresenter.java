package com.example.masato.githubfeed.presenter;

import android.util.Log;

import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.model.FeedEntry;
import com.example.masato.githubfeed.view.FeedListView;
import com.example.masato.githubfeed.view.PaginatingListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/01/29.
 */

public class FeedListPresenter extends PaginatingListPresenter<FeedEntry> {

    private static final int NOTIFICATION_THRESHOLD = 15;
    private FeedListView view;
    private String url;

    public void setView(FeedListView view) {
        this.view = view;
    }

    @Override
    protected void onFetchElement(int page) {
        GitHubApi.getApi().fetchFeedList(this.url, page, new GitHubApiCallback() {
            @Override
            public void onApiSuccess(Object object) {
                List<FeedEntry> feedEntries = (List<FeedEntry>) object;
                onFetchedElements(feedEntries, true);
            }

            @Override
            public void onApiFailure(Failure failure) {
                onFetchedElements(null, false);
                view.showToast(failure.textId);
            }
        });
    }

    @Override
    public void onElementClicked(FeedEntry element) {
        view.startRepoView(element.repoUrl);
    }

    public FeedListPresenter(PaginatingListView view, String url) {
        super(view, NOTIFICATION_THRESHOLD);
        this.url = url;
    }
}
