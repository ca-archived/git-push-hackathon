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

    private void handleDiffFileResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            ArrayList<DiffFile> diffFiles = (ArrayList<DiffFile>) result.resultObject;
            view.showDiffFileList(diffFiles);
        }
    }

    private void handleRepoResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            Repository repository = (Repository) result.resultObject;
            view.showRepoInfo(repository);
        }
    }

    private void handleAuthorIconResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            Bitmap icon = (Bitmap) result.resultObject;
            view.showAuthorIcon(icon);
        }
    }

    private void handleCommitterIconResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            Bitmap icon = (Bitmap) result.resultObject;
            view.showCommitterIcon(icon);
        }
    }

    private void initContent(Commit commit) {
        GitHubApi.getApi().fetchCommitDiffFileList(commit, this::handleDiffFileResult);
        if (commit.repository != null) {
            view.showRepoInfo(commit.repository);
        } else {
            GitHubApi.getApi().fetchRepository(commit.getRepoUrl(), this::handleRepoResult);
        }

        if (commit.committer != null) {
            if (commit.committer.icon == null) {
                GitHubApi.getApi().fetchBitmap(commit.committer.iconUrl, this::handleCommitterIconResult);
            }
        }

        if (commit.author != null) {
            if (commit.author.icon == null) {
                GitHubApi.getApi().fetchBitmap(commit.author.iconUrl, this::handleAuthorIconResult);
            }
        }
    }

    public CommitPresenter(CommitView view, Commit commit) {
        this.view = view;
        initContent(commit);
    }
}
