package com.example.masato.githubfeed.view;

/**
 * Created by Masato on 2018/01/22.
 */

public interface FeedListView extends BaseView {

    public void stopRefreshing();

    public void updateAdapter();

    public void startRepoView(String url);
}
