package com.example.masato.githubfeed.view;

import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.model.diff.DiffFile;

import java.util.ArrayList;

/**
 * Created by Masato on 2018/02/07.
 */

public interface CommitView {
    public void showDiffFileList(ArrayList<DiffFile> diffFiles);

    public void showRepoInfo(Repository repository);
}
