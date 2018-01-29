package com.example.masato.githubfeed.view;

import android.graphics.Bitmap;

import com.example.masato.githubfeed.view.adapter.FeedRecyclerViewAdapter;

import java.util.Date;

/**
 * Created by Masato on 2018/01/22.
 */

public interface FeedEntryView {

    public void setDate(Date date);

    public void setTitle(String title);

    public void setThumbnail(Bitmap bitmap);

    public void setRepoUrl(String url);

    public void setOnClickListener(FeedRecyclerViewAdapter.OnClickListener listener);
}
