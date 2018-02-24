package com.example.masato.githubfeed.view;

import android.graphics.Bitmap;

import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.model.diff.DiffFile;

import java.util.ArrayList;

/**
 * Created by Masato on 2018/02/07.
 */

public interface CommitView extends BaseView {
    public void showRepoInfo(Repository repository);
}
