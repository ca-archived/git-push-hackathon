package com.example.masato.githubfeed.view.adapter;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.model.FeedEntry;
import com.example.masato.githubfeed.util.HandyHttpURLConnection;
import com.example.masato.githubfeed.util.HttpConnectionPool;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Masato on 2018/01/19.
 */

public class FeedRecyclerViewAdapter extends RecyclerView.Adapter {

    private static final int FEED_ENTRY_VIEW = 1;
    private static final int LOADING_VIEW = 2;
    private static final int NOTHING_TO_SHOW_VIEW = 3;

    private List<FeedEntry> feedEntries = new ArrayList<>();
    private FetchRequestListener listener;
    private LayoutInflater inflater;
    private String feedUrl;
    private int currentPage = 1;
    private boolean feedFetching = false;
    private boolean feedMaxedOut = false;
    private boolean refreshing = false;

    public void addFeedEntries(List<FeedEntry> feedEntries) {
        if (refreshing) {
            this.feedEntries.clear();
            this.feedEntries.addAll(feedEntries);
            refreshing = false;
        } else {
            if (feedEntries.size() == 0) {
                feedMaxedOut = true;
            } else {
                this.feedEntries.addAll(feedEntries);
            }
        }
        feedFetching = false;
        notifyDataSetChanged();
    }

    public void refreshEntries() {
        if (!refreshing && !feedFetching) {
            refreshing = true;
            feedFetching = true;
            listener.onFeedFetchRequested(feedUrl, 1);
            currentPage = 1;
            feedMaxedOut = false;
        }
    }

    @Override
    public int getItemViewType(int position) {
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

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == FEED_ENTRY_VIEW) {
            view = inflater.inflate(R.layout.feed_entry, parent, false);
            return new FeedEntryViewViewHolder(view);
        } else if (viewType == LOADING_VIEW){
            view = inflater.inflate(R.layout.feed_loading, parent, false);
            return new LoadingViewHolder(view);
        } else if (viewType == NOTHING_TO_SHOW_VIEW) {
            view = inflater.inflate(R.layout.feed_nothing_to_show, parent, false);
            return new NothingToShowViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        fetchFeedIfNeeded(position);
        if (getItemViewType(position) == FEED_ENTRY_VIEW) {
            FeedEntryViewViewHolder viewHolder = (FeedEntryViewViewHolder) holder;
            bindFeedEntryView(viewHolder, position);
        }
    }

    private void bindFeedEntryView(FeedEntryViewViewHolder viewHolder, int position) {
        FeedEntry feedEntry = feedEntries.get(position);
        if (feedEntry.thumbnail == null) {
            loadThumbnail(feedEntry, viewHolder);
        } else {
            viewHolder.thumbnail.setImageBitmap(feedEntry.thumbnail);
        }
        viewHolder.title.setText(feedEntry.title);
    }

    @Override
    public int getItemCount() {
        if (feedMaxedOut) {
            if (feedEntries.size() == 0) {
                return 1;
            }
            return feedEntries.size();
        }
        return feedEntries.size() + 1;
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof FeedEntryViewViewHolder) {
            FeedEntryViewViewHolder viewHolder = (FeedEntryViewViewHolder) holder;
            viewHolder.thumbnail.setImageBitmap(null);
        }
    }

    private void loadThumbnail(final FeedEntry feedEntry, final FeedEntryViewViewHolder viewHolder) {
        GitHubApi.getApi().getBitmap(feedEntry.thumbnailUrl, new GitHubApiCallback() {
            @Override
            public void onSuccess(Object object) {
                feedEntry.thumbnail = (Bitmap) object;
                viewHolder.thumbnail.setImageBitmap(feedEntry.thumbnail);
            }

            @Override
            public void onError(String message) {

            }
        });
    }

    private void fetchFeedIfNeeded(int position) {
        if (feedFetching || refreshing) {
            return;
        }
        int remaining = getItemCount() - position;
        if (remaining < 10 && !feedMaxedOut) {
            listener.onFeedFetchRequested(feedUrl, currentPage + 1);
            feedFetching = true;
            currentPage++;
        }
    }

    public FeedRecyclerViewAdapter(String feedUrl, LayoutInflater inflater, FetchRequestListener listener) {
        this.inflater = inflater;
        this.listener = listener;
        this.feedUrl = feedUrl;
    }

    private class FeedEntryViewViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView title;
        ImageView thumbnail;

        FeedEntryViewViewHolder(View itemView) {
            super(itemView);
            title = (AppCompatTextView) itemView.findViewById(R.id.feed_entry_title);
            thumbnail = (ImageView) itemView.findViewById(R.id.feed_entry_image);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class NothingToShowViewHolder extends RecyclerView.ViewHolder {
        public NothingToShowViewHolder(View itemView) {
            super(itemView);
        }
    }

    public interface FetchRequestListener {
        public void onFeedFetchRequested(String url, int page);
    }
}
