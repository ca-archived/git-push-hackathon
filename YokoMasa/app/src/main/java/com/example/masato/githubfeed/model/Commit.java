package com.example.masato.githubfeed.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Masato on 2018/02/06.
 */

public class Commit extends BaseModel {

    public String comment;
    public String sha;
    public String url;
    public String htmlUrl;
    public Profile committer;
    public Profile author;
    public Date committerDate;
    public Date authorDate;
    public Repository repository;
    public static Parcelable.Creator<Commit> CREATOR = new Parcelable.Creator<Commit>() {
        @Override
        public Commit createFromParcel(Parcel parcel) {
            Commit commit = new Commit();
            commit.comment = parcel.readString();
            commit.sha = parcel.readString();
            commit.url = parcel.readString();
            commit.htmlUrl = parcel.readString();
            commit.committer = parcel.readParcelable(getClass().getClassLoader());
            commit.author = parcel.readParcelable(getClass().getClassLoader());
            commit.committerDate = (Date) parcel.readSerializable();
            commit.authorDate = (Date) parcel.readSerializable();
            commit.repository = parcel.readParcelable(getClass().getClassLoader());
            return commit;
        }

        @Override
        public Commit[] newArray(int i) {
            return new Commit[i];
        }
    };

    public String getShortenedSha() {
        return sha.substring(0, 6);
    }

    public String getShortenedComment() {
        return comment.split("\n")[0];
    }

    public String getRepoUrl() {
        if (url == null) {
            return "";
        }
        return url.split("/commits")[0];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(comment);
        parcel.writeString(sha);
        parcel.writeString(url);
        parcel.writeString(htmlUrl);
        parcel.writeParcelable(committer, 0);
        parcel.writeParcelable(author, 0);
        parcel.writeSerializable(committerDate);
        parcel.writeSerializable(authorDate);
        parcel.writeParcelable(repository, 0);
    }
}
