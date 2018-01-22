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

    public FeedPresenter(FeedView feedView) {
        this.view = feedView;
    }
}
