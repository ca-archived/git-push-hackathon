package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.diff.DiffFile;
import com.example.masato.githubfeed.view.DiffFileListView;

import java.util.List;

/**
 * Created by Masato on 2018/02/08.
 */

public class DiffFileListPresenter {

    private DiffFileListView view;

    private void handleResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            List<DiffFile> diffFiles = (List<DiffFile>) result.resultObject;
            view.showDiffFiles(diffFiles);
        }
    }

    public DiffFileListPresenter(DiffFileListView view, String url) {
        this.view = view;
        GitHubApi.getApi().fetchDiffFileList(url, this::handleResult);
    }
}
