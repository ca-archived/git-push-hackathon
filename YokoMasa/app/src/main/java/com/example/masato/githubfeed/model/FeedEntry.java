package com.example.masato.githubfeed.model;

import android.graphics.Bitmap;

/**
 * Created by Masato on 2018/01/19.
 */

public class FeedEntry {
    public String title;
    public String name;
    public String thumbnailUrl;
    public Bitmap thumbnail;

    public boolean isThumbnailSet() { return thumbnail != null; }
}
