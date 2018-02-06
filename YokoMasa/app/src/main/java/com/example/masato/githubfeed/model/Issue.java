package com.example.masato.githubfeed.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Masato on 2018/02/01.
 */

public class Issue extends BaseModel {

    public static final String STATE_OPEN = "open";
    public static final String STATED_CLOSED = "closed";

    public String name;
    public String bodyHtml;
    public String state;
    public Profile author;
    public Repository repository;
    public String commentsUrl;
    public int comments;
    public static Parcelable.Creator<Issue> CREATOR = new Parcelable.Creator<Issue>() {
        @Override
        public Issue createFromParcel(Parcel parcel) {
            Issue issue = new Issue();
            issue.name = parcel.readString();
            issue.bodyHtml = parcel.readString();
            issue.state = parcel.readString();
            issue.createdAt = (Date) parcel.readSerializable();
            issue.author = parcel.readParcelable(getClass().getClassLoader());
            issue.repository = parcel.readParcelable(getClass().getClassLoader());
            issue.commentsUrl = parcel.readString();
            issue.comments = parcel.readInt();
            return issue;
        }

        @Override
        public Issue[] newArray(int i) {
            return new Issue[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(bodyHtml);
        parcel.writeString(state);
        parcel.writeSerializable(createdAt);
        parcel.writeParcelable(author, 0);
        parcel.writeParcelable(repository, 0);
        parcel.writeString(commentsUrl);
        parcel.writeInt(comments);
    }
}