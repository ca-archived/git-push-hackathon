package com.example.masato.githubfeed.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Masato on 2018/01/27.
 */

public class Repository extends BaseModel {

    public String fullName;
    public String name;
    public String owner;
    public String baseUrl;
    public String htmlUrl;
    public int stars;
    public int watches;
    public int forks;
    public static Parcelable.Creator<Repository> CREATOR = new Parcelable.Creator<Repository>() {
        @Override
        public Repository createFromParcel(Parcel parcel) {
            Repository repo = new Repository();
            repo.fullName = parcel.readString();
            repo.name = parcel.readString();
            repo.owner = parcel.readString();
            repo.baseUrl = parcel.readString();
            repo.htmlUrl = parcel.readString();
            repo.stars = parcel.readInt();
            repo.watches = parcel.readInt();
            repo.forks = parcel.readInt();
            return repo;
        }

        @Override
        public Repository[] newArray(int i) {
            return new Repository[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(fullName);
        parcel.writeString(name);
        parcel.writeString(owner);
        parcel.writeString(baseUrl);
        parcel.writeString(htmlUrl);
        parcel.writeInt(stars);
        parcel.writeInt(watches);
        parcel.writeInt(forks);
    }
}
