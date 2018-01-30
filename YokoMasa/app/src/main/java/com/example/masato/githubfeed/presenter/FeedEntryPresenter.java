package com.example.masato.githubfeed.presenter;

import android.graphics.Bitmap;

import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.model.FeedEntry;
import com.example.masato.githubfeed.view.FeedEntryView;

/**
 * Created by Masato on 2018/01/30.
 */

public class FeedEntryPresenter {

    private FeedEntryView view;

    public void fetchThumbnail(final FeedEntry feedEntry) {
        GitHubApi.getApi().fetchBitmap(feedEntry.thumbnailUrl, new GitHubApiCallback() {
            @Override
            public void onApiSuccess(Object object) {
                Bitmap thumbnail = (Bitmap) object;
                feedEntry.thumbnail = thumbnail;
                view.setThumbnail(thumbnail);
            }

            @Override
            public void onApiFailure(Failure failure) {

            }
        });
    }

    public FeedEntryPresenter(FeedEntryView view) {
        this.view = view;
    }
}
