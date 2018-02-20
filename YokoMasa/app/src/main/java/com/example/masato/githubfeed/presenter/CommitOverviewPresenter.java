package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.Commit;
import com.example.masato.githubfeed.model.diff.DiffCodeLine;
import com.example.masato.githubfeed.model.diff.DiffFile;
import com.example.masato.githubfeed.view.CommitOverviewView;

import java.util.List;

/**
 * Created by Masato on 2018/02/19.
 */

public class CommitOverviewPresenter {

    private CommitOverviewView view;

    private void handleDiffFileListResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            List<DiffFile> diffFiles = (List<DiffFile>) result.resultObject;
            view.showDiffFiles(diffFiles);
        }
    }

    public CommitOverviewPresenter(CommitOverviewView view, Commit commit) {
        this.view = view;
        GitHubApi.getApi().fetchCommitDiffFileList(commit, this::handleDiffFileListResult);
    }
}
