package com.example.masato.githubfeed.model.diff;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.masato.githubfeed.model.BaseModel;

/**
 * Created by Masato on 2018/02/06.
 */

public class DiffCodeLine extends BaseModel {

    public static final int NORMAL = 391;
    public static final int ADDED = 226;
    public static final int REMOVED = 367;

    public String code;
    public int status;
    public static Parcelable.Creator<DiffCodeLine> CREATOR = new Parcelable.Creator<DiffCodeLine>() {
        @Override
        public DiffCodeLine createFromParcel(Parcel parcel) {
            return new DiffCodeLine(parcel.readString());
        }

        @Override
        public DiffCodeLine[] newArray(int i) {
            return new DiffCodeLine[i];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(code);
    }

    private void setStatus() {
        if (code.startsWith("+")) {
            status = ADDED;
        } else if (code.startsWith("-")) {
            status = REMOVED;
        } else {
            status = NORMAL;
        }
    }

    public DiffCodeLine(String code) {
        this.code = code;
        setStatus();
    }
}
