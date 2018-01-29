package com.example.masato.githubfeed.presenter;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;

import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.model.FeedEntry;
import com.example.masato.githubfeed.util.DateUtil;
import com.example.masato.githubfeed.view.FeedEntryView;
import com.example.masato.githubfeed.view.FeedListView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.SimpleTimeZone;

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
        FeedEntry feedEntry = feedEntries.get(position);
        view.setDate(feedEntry.published);
        view.setTitle(feedEntry.title);
        view.setRepoUrl(feedEntry.repoUrl);
        if (!feedEntry.isThumbnailSet()) {
            fetchThumbnail(feedEntry, view, position);
        } else {
            view.setThumbnail(feedEntry.getThumbnail());
        }
    }

    private void fetchThumbnail(final FeedEntry feedEntry, final FeedEntryView view, final int position) {
        GitHubApi.getApi().fetchBitmap(feedEntry.thumbnailUrl, new GitHubApiCallback() {
            @Override
            public void onApiSuccess(Object object) {
                Bitmap bitmap = (Bitmap) object;
                feedEntry.setThumbnail(bitmap);
                view.setThumbnail(bitmap);
            }

            @Override
            public void onApiFailure(Failure failure) {

            }
        });
    }

    public void onRefresh() {
        refresh();
    }

    @Override
    public void onApiSuccess(Object object) {
        List<FeedEntry> feedEntries = (List<FeedEntry>) object;
        addFeedEntries(feedEntries);
    }

    @Override
    public void onApiFailure(Failure failure) {
        refreshing = false;
        feedListView.stopRefreshing();
        feedListView.showToast(failure.textId);
    }

    public void onSaveInstanceState(Bundle bundle) {
        ArrayList<FeedEntry> feedEntries = (ArrayList<FeedEntry>) this.feedEntries;
        bundle.putParcelableArrayList("feed_entries", feedEntries);
        bundle.putString("feed_url", feedUrl);
        bundle.putInt("current_page", currentPage);
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
            GitHubApi.getApi().fetchFeedList(feedUrl, currentPage+1, this);
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

    public void onItemClicked(String url) {
        feedListView.startRepoView(url);
    }

    private void refresh() {
        if (refreshing) {
            return;
        }
        GitHubApi.getApi().fetchFeedList(feedUrl, 1, this);
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

    public FeedListPresenter(FeedListView feedListView, Bundle savedState) {
        this.feedListView = feedListView;
        this.feedEntries = savedState.getParcelableArrayList("feed_entries");
        this.feedUrl = savedState.getString("feed_url");
        this.currentPage = savedState.getInt("current_page");
    }
}
