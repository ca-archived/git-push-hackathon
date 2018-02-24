package com.example.masato.githubfeed.view;

import com.example.masato.githubfeed.model.FeedEntry;

/**
 * Created by Masato on 2018/01/22.
 */

public interface FeedListView extends BaseView {

    public void stopRefreshing();

    public void updateAdapter();

    public void showRepo(String repoUrl);

}
