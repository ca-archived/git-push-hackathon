package com.example.masato.githubfeed.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Masato on 2018/02/01.
 */

public class Issue implements Parcelable {

    public String name;
    public String bodyHtml;
    public Date createdAt;
    public Profile author;
    public Repository repository;
    public String commentsUrl;
    public static Parcelable.Creator<Issue> CREATOR = new Parcelable.Creator<Issue>() {
        @Override
        public Issue createFromParcel(Parcel parcel) {
            Issue issue = new Issue();
            issue.name = parcel.readString();
            issue.bodyHtml = parcel.readString();
            issue.createdAt = (Date) parcel.readSerializable();
            issue.author = parcel.readParcelable(getClass().getClassLoader());
            issue.repository = parcel.readParcelable(getClass().getClassLoader());
            issue.commentsUrl = parcel.readString();
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
        parcel.writeSerializable(createdAt);
        parcel.writeParcelable(author, 0);
        parcel.writeParcelable(repository, 0);
        parcel.writeString(commentsUrl);
    }
}
