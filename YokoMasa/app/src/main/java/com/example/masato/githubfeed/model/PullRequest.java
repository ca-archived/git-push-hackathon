package com.example.masato.githubfeed.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;

/**
 * Created by Masato on 2018/02/08.
 */

public class PullRequest extends BaseModel{

    public static final String STATE_OPEN = "open";
    public static final String STATE_CLOSED = "closed";
    public static final String STATE_MERGED = "merged";

    public String name;
    public String bodyHtml;
    public String state;
    public Profile author;
    public Repository repository;
    public String commitsUrl;
    public String diffUrl;
    public String commentsUrl;
    public int number;
    public static Parcelable.Creator<PullRequest> CREATOR = new Parcelable.Creator<PullRequest>() {
        @Override
        public PullRequest createFromParcel(Parcel parcel) {
            PullRequest pr = new PullRequest();
            pr.name = parcel.readString();
            pr.bodyHtml = parcel.readString();
            pr.state = parcel.readString();
            pr.createdAt = (Date) parcel.readSerializable();
            pr.author = parcel.readParcelable(getClass().getClassLoader());
            pr.repository = parcel.readParcelable(getClass().getClassLoader());
            pr.commitsUrl = parcel.readString();
            pr.diffUrl = parcel.readString();
            pr.commentsUrl = parcel.readString();
            pr.number = parcel.readInt();
            return pr;
        }

        @Override
        public PullRequest[] newArray(int i) {
            return new PullRequest[i];
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
        parcel.writeString(commitsUrl);
        parcel.writeString(diffUrl);
        parcel.writeString(commentsUrl);
        parcel.writeInt(number);
    }
}
