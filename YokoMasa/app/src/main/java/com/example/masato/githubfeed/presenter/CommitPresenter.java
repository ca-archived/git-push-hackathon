package com.example.masato.githubfeed.presenter;

import android.graphics.Bitmap;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.Commit;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.model.diff.DiffFile;
import com.example.masato.githubfeed.view.CommitView;

import java.util.ArrayList;

/**
 * Created by Masato on 2018/02/07.
 */

public class CommitPresenter {

    private CommitView view;

    private void handleRepoResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            Repository repository = (Repository) result.resultObject;
            view.showRepoInfo(repository);
        } else {
            view.showToast(result.failure.textId);
        }
    }

    private void initContent(Commit commit) {
        if (commit.repository != null) {
            view.showRepoInfo(commit.repository);
        } else {
            GitHubApi.getApi().fetchRepository(commit.getRepoUrl(), this::handleRepoResult);
        }
    }

    public CommitPresenter(CommitView view, Commit commit) {
        this.view = view;
        initContent(commit);
    }
}
