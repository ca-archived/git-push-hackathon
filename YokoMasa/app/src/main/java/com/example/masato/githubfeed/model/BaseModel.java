package com.example.masato.githubfeed.model;

import android.os.Parcelable;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by Masato on 2018/02/05.
 */

public abstract class BaseModel implements Comparable<BaseModel>, Parcelable {

    public Date createdAt;

    @Override
    public int compareTo(@NonNull BaseModel baseModel) {
        if (this.createdAt == null || baseModel.createdAt == null) {
            return 0;
        }
        return createdAt.compareTo(baseModel.createdAt);
    }
}
