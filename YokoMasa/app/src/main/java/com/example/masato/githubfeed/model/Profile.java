package com.example.masato.githubfeed.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Masato on 2018/01/19.
 */

public class Profile extends BaseModel {

    public String name;
    public String iconUrl;
    public Bitmap icon;
    public static Parcelable.Creator<Profile> CREATOR = new Parcelable.Creator<Profile>() {
        @Override
        public Profile createFromParcel(Parcel parcel) {
            Profile profile = new Profile();
            profile.name = parcel.readString();
            profile.iconUrl = parcel.readString();
            profile.icon = parcel.readParcelable(getClass().getClassLoader());
            return profile;
        }

        @Override
        public Profile[] newArray(int i) {
            return new Profile[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name);
        parcel.writeString(iconUrl);
        parcel.writeParcelable(icon, 0);
    }
}
