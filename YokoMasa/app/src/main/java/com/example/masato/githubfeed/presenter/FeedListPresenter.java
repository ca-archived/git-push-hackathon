package com.example.masato.githubfeed.presenter;

import android.graphics.Bitmap;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.model.FeedEntry;
import com.example.masato.githubfeed.view.FeedEntryView;
import com.example.masato.githubfeed.view.FeedListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/01/22.
 */

public class FeedListPresenter implements Presenter, GitHubApiCallback {

    public static final int FEED_ENTRY_VIEW = 1;
    public static final int LOADING_VIEW = 2;
    public static final int NOTHING_TO_SHOW_VIEW = 3;
    private static final int PREFETCH_THRESHOLD = 15;

    private String feedUrl;
    private FeedListView feedListView;
    private List<FeedEntry> feedEntries = new ArrayList<>();
    private int currentPage = 1;
    private boolean feedMaxedOut = false;
    private boolean refreshing = false;
    private boolean fetching = false;

    public int onGetItemCount() {
        return getItemCount();
    }

    public int onGetItemViewType(int position) {
        if (feedMaxedOut) {
            if (feedEntries.size() == 0) {
                return NOTHING_TO_SHOW_VIEW;
            }
            return FEED_ENTRY_VIEW;
        }
        if (position == getItemCount() - 1) {
            return LOADING_VIEW;
        }
        return FEED_ENTRY_VIEW;
    }

    public void onBindFeedEntryView(final FeedEntryView view, int position) {
        fetchFeedEntriesIfNeeded(position);
        final FeedEntry feedEntry = feedEntries.get(position);
        view.setTitle(feedEntry.title);
        if (feedEntry.thumbnail == null) {
            GitHubApi.getApi().getBitmap(feedEntry.thumbnailUrl, new GitHubApiCallback() {
                @Override
                public void onSuccess(Object object) {
                    Bitmap bitmap = (Bitmap) object;
                    feedEntry.thumbnail = bitmap;
                    view.setThumbnail(bitmap);
                }

                @Override
                public void onError(String message) {

                }
            });
        } else {
            view.setThumbnail(feedEntry.thumbnail);
        }
    }

    public void onRefresh() {
        refresh();
    }

    @Override
    public void onSuccess(Object object) {
        List<FeedEntry> feedEntries = (List<FeedEntry>) object;
        addFeedEntries(feedEntries);
    }

    @Override
    public void onError(String message) {

    }

    private int getItemCount() {
        if (feedMaxedOut) {
            if (feedEntries.size() == 0) {
                return 1;
            }
            return feedEntries.size();
        }
        return feedEntries.size() + 1;
    }

    private void fetchFeedEntriesIfNeeded(int position) {
        if (fetching) {
            return;
        }
        int remaining = getItemCount() - position;
        if (remaining < PREFETCH_THRESHOLD && !feedMaxedOut) {
            GitHubApi.getApi().getFeedList(feedUrl, currentPage+1, this);
            fetching = true;
            currentPage++;
        }
    }

    private void addFeedEntries(List<FeedEntry> feedEntries) {
        if (refreshing) {
            this.feedEntries.clear();
            this.feedEntries.addAll(feedEntries);
            feedListView.stopRefreshing();
            refreshing = false;
        } else {
            if (feedEntries.size() == 0) {
                feedMaxedOut = true;
            } else {
                this.feedEntries.addAll(feedEntries);
            }
        }
        feedListView.updateAdapter();
        fetching = false;
    }

    private void refresh() {
        if (refreshing || fetching) {
            return;
        }
        GitHubApi.getApi().getFeedList(feedUrl, 1, this);
        refreshing = true;
        fetching = true;
    }

    @Override
    public void onResume() {

    }

    public FeedListPresenter(FeedListView feedListView, String feedUrl) {
        this.feedListView = feedListView;
        this.feedUrl = feedUrl;
        refresh();
    }
}
