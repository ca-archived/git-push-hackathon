package com.example.masato.githubfeed.model.event;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.masato.githubfeed.model.BaseModel;

/**
 * Created by Masato on 2018/02/09.
 */

public class Event extends BaseModel {

    public String content;
    public String repoName;
    public String type;
    public String actorName;
    public String actorIconUrl;
    public Bitmap actorIcon;
    public String repoUrl;
    public Action action;
    public String triggerUrl;
    public static Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel parcel) {
            Event event = new Event();
            event.content = parcel.readString();
            event.repoName = parcel.readString();
            event.type = parcel.readString();
            event.actorName = parcel.readString();
            event.actorIconUrl = parcel.readString();
            event.actorIcon = parcel.readParcelable(getClass().getClassLoader());
            event.action = (Action) parcel.readSerializable();
            event.triggerUrl = parcel.readString();
            return event;
        }

        @Override
        public Event[] newArray(int i) {
            return new Event[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(content);
        parcel.writeString(repoName);
        parcel.writeString(type);
        parcel.writeString(actorName);
        parcel.writeString(actorIconUrl);
        parcel.writeParcelable(actorIcon, 0);
        parcel.writeString(repoUrl);
        parcel.writeSerializable(action);
        parcel.writeString(triggerUrl);
    }

    public enum Action {
        REPO_VIEW,
        ISSUE_VIEW,
        PR_VIEW
    }

}
