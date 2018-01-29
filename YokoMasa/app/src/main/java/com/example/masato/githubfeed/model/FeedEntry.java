package com.example.masato.githubfeed.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * Created by Masato on 2018/01/19.
 */

public class FeedEntry implements Parcelable {
    public String title;
    public String name;
    public String thumbnailUrl;
    public String repoUrl;
    public Date published;
    private Bitmap thumbnail;

    public static Parcelable.Creator<FeedEntry> CREATOR = new Parcelable.Creator<FeedEntry>() {
        @Override
        public FeedEntry createFromParcel(Parcel parcel) {
            FeedEntry feedEntry = new FeedEntry();
            feedEntry.title = parcel.readString();
            feedEntry.name = parcel.readString();
            feedEntry.thumbnailUrl = parcel.readString();
            feedEntry.repoUrl = parcel.readString();
            feedEntry.published = (Date) parcel.readSerializable();
            feedEntry.thumbnail = parcel.readParcelable(getClass().getClassLoader());
            return feedEntry;
        }

        @Override
        public FeedEntry[] newArray(int i) {
            return new FeedEntry[i];
        }
    };

    public void setThumbnail(Bitmap bitmap) {
        this.thumbnail = bitmap;
    }

    public Bitmap getThumbnail() {
        return thumbnail;
    }

    public boolean isThumbnailSet() {
        return thumbnail != null;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(name);
        parcel.writeString(thumbnailUrl);
        parcel.writeString(repoUrl);
        parcel.writeSerializable(published);
        parcel.writeParcelable(thumbnail, 0);
    }

    public void setApiRepoUrlFromEventUrl(String eventUrl) {
        Log.i("gh_feed", eventUrl);
        if (!eventUrl.startsWith("https://github.com/")) {
            repoUrl = "";
            return;
        }
        int firstSlashIndex = eventUrl.indexOf("/", 19);
        if (firstSlashIndex == -1) {
            repoUrl = "";
            return;
        }
        String authorName = eventUrl.substring(19, firstSlashIndex);
        int secondSlashIndex = eventUrl.indexOf("/", firstSlashIndex + 1);
        if (secondSlashIndex == -1) {
            secondSlashIndex = eventUrl.length();
        }
        String repoName = eventUrl.substring(firstSlashIndex + 1, secondSlashIndex);
        repoUrl =  "https://api.github.com/repos/" + authorName + "/" + repoName;
    }

}
