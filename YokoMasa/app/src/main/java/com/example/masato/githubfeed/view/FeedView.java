package com.example.masato.githubfeed.view;

import com.example.masato.githubfeed.model.FeedEntry;

import java.util.List;

/**
 * Created by Masato on 2018/01/19.
 */

public interface FeedView {
    public void addFeedEntry(int fragmentNumber, List<FeedEntry> feedEntries);

    public void disableRefreshing(int fragmentNumber);
}
