package com.example.masato.githubfeed.model.diff;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.masato.githubfeed.model.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/02/06.
 */

public class DiffFile extends BaseModel {

    public List<DiffCodeLine> codeLines = new ArrayList<>();
    public String fileName;
    public int additions;
    public int deletions;
    public static Parcelable.Creator<DiffFile> CREATOR = new Parcelable.Creator<DiffFile>() {
        @Override
        public DiffFile createFromParcel(Parcel parcel) {
            DiffFile diffFile = new DiffFile();
            parcel.readList(diffFile.codeLines, getClass().getClassLoader());
            diffFile.fileName = parcel.readString();
            diffFile.additions = parcel.readInt();
            diffFile.deletions = parcel.readInt();
            return diffFile;
        }

        @Override
        public DiffFile[] newArray(int i) {
            return new DiffFile[0];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(codeLines);
        parcel.writeString(fileName);
        parcel.writeInt(additions);
        parcel.writeInt(deletions);
    }
}
