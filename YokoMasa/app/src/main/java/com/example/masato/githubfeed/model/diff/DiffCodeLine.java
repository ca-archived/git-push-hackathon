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
    public static final int CHANGE_LINES = 511;

    public String code;
    public int status;
    public static Parcelable.Creator<DiffCodeLine> CREATOR = new Parcelable.Creator<DiffCodeLine>() {
        @Override
        public DiffCodeLine createFromParcel(Parcel parcel) {
            String code = parcel.readString();
            int status = parcel.readInt();
            return new DiffCodeLine(code, status);
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

    public DiffCodeLine(String code, int status) {
        this.code = code;
        this.status = status;
    }
}
