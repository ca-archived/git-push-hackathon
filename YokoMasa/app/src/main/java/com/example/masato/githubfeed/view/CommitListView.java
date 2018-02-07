package com.example.masato.githubfeed.view;

import android.content.Context;

import com.example.masato.githubfeed.model.Commit;

/**
 * Created by Masato on 2018/02/06.
 */

public interface CommitListView {
    public void navigateToCommitView(Commit commit);
}
