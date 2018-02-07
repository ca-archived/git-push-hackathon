package com.example.masato.githubfeed.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Masato on 2018/02/06.
 */

public class Commit extends BaseModel {

    public String comment;
    public String sha;
    public String url;
    public Profile committer;
    public static Parcelable.Creator<Commit> CREATOR = new Parcelable.Creator<Commit>() {
        @Override
        public Commit createFromParcel(Parcel parcel) {
            Commit commit = new Commit();
            commit.comment = parcel.readString();
            commit.sha = parcel.readString();
            commit.url = parcel.readString();
            commit.committer = parcel.readParcelable(getClass().getClassLoader());
            return commit;
        }

        @Override
        public Commit[] newArray(int i) {
            return new Commit[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(comment);
        parcel.writeString(sha);
        parcel.writeString(url);
        parcel.writeParcelable(committer, 0);
    }
}
