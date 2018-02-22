package com.example.masato.githubfeed.view;

import com.example.masato.githubfeed.model.diff.DiffFile;

import java.util.List;

/**
 * Created by Masato on 2018/02/19.
 */

public interface CommitOverviewView extends BaseView {
    public void showDiffFiles(List<DiffFile>diffFiles);
}
