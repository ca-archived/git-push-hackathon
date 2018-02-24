package com.example.masato.githubfeed.presenter;

import android.util.Log;

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

public class CommitOverviewPresenter extends BasePresenter {

    private CommitOverviewView view;
    private Commit commit;

    @Override
    public void tryAgain() {
        view.hideErrorView();
        view.showLoadingView();
        GitHubApi.getApi().fetchCommitDiffFileList(commit, this::handleDiffFileListResult);
    }

    private void handleDiffFileListResult(GitHubApiResult result) {
        view.hideLoadingView();
        if (result.isSuccessful) {
            List<DiffFile> diffFiles = (List<DiffFile>) result.resultObject;
            view.showDiffFiles(diffFiles);
            Log.i("gh_feed", "diff success");
        } else {
            view.showErrorView(result.failure, result.errorMessage);
            Log.i("gh_feed", "diff failure");
        }
    }

    public CommitOverviewPresenter(CommitOverviewView view, Commit commit) {
        this.view = view;
        this.commit = commit;
        view.showLoadingView();
        GitHubApi.getApi().fetchCommitDiffFileList(commit, this::handleDiffFileListResult);
    }
}
