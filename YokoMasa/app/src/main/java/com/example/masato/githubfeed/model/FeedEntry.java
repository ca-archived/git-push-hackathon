package com.example.masato.githubfeed.model;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcel;
import android.os.Parcelable;

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
    public Date published;
    private Bitmap thumbnail;

    public static Parcelable.Creator<FeedEntry> CREATOR = new Parcelable.Creator<FeedEntry>() {
        @Override
        public FeedEntry createFromParcel(Parcel parcel) {
            FeedEntry feedEntry = new FeedEntry();
            feedEntry.title = parcel.readString();
            feedEntry.name = parcel.readString();
            feedEntry.thumbnailUrl = parcel.readString();
            feedEntry.published = (Date) parcel.readSerializable();
            int bitmapByteArrayLength = parcel.readInt();
            if (bitmapByteArrayLength != 0) {
                byte[] bitmapByteArray = new byte[bitmapByteArrayLength];
                parcel.readByteArray(bitmapByteArray);
                feedEntry.thumbnail = BitmapFactory.decodeByteArray(bitmapByteArray, 0, bitmapByteArrayLength);
            }
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
        parcel.writeSerializable(published);
        byte[] bitmapByteArray = thumbnailToByteArray();
        if (bitmapByteArray != null) {
            parcel.writeInt(bitmapByteArray.length);
            parcel.writeByteArray(bitmapByteArray);
        }
    }

    private byte[] thumbnailToByteArray() {
        if (thumbnail != null) {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            thumbnail.compress(Bitmap.CompressFormat.PNG, 100, baos);
            return baos.toByteArray();
        }
        return null;
    }

}
