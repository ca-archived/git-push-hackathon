package com.example.masato.githubfeed.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Masato on 2018/02/01.
 */

public class Comment implements Parcelable {

    public String bodyHtml;
    public Date createdAt;
    public Profile author;
    public Parcelable.Creator<Comment> CREATOR = new Parcelable.Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel parcel) {
            Comment comment = new Comment();
            comment.bodyHtml = parcel.readString();
            comment.createdAt = (Date) parcel.readSerializable();
            comment.author = parcel.readParcelable(getClass().getClassLoader());
            return comment;
        }

        @Override
        public Comment[] newArray(int i) {
            return new Comment[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bodyHtml);
        parcel.writeSerializable(createdAt);
        parcel.writeParcelable(author, 0);
    }

}
