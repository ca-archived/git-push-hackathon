package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.model.FeedEntry;
import com.example.masato.githubfeed.view.FeedView;
import com.example.masato.githubfeed.view.adapter.FeedRecyclerViewAdapter;

import java.util.List;

/**
 * Created by Masato on 2018/01/19.
 */

public class FeedPresenter implements Presenter {

    private FeedView view;

    @Override
    public void onResume() {

    }

    public void onFeedFetchRequested(final int fragmentNumber, String url, int page) {
        GitHubApi.getApi().getFeedList(url, page, new GitHubApiCallback() {
            @Override
            public void onSuccess(Object object) {
                List<FeedEntry> feedEntries = (List<FeedEntry>) object;
                view.disableRefreshing(fragmentNumber);
                view.addFeedEntry(fragmentNumber, feedEntries);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    public void fetchFeed(int fragmentNumber, String url, int page) {

    }

    public FeedPresenter(FeedView feedView) {
        this.view = feedView;
    }
}
