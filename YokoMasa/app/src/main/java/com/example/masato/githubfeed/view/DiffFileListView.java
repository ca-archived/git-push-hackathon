package com.example.masato.githubfeed.view;

import com.example.masato.githubfeed.model.diff.DiffFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/02/08.
 */

public interface DiffFileListView extends BaseView {
    public void showDiffFiles(ArrayList<DiffFile> diffFiles);

    public void removeLoadingView();
}
