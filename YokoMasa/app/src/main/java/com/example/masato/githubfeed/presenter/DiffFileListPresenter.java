package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.diff.DiffFile;
import com.example.masato.githubfeed.view.DiffFileListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/02/08.
 */

public class DiffFileListPresenter extends BasePresenter {

    private DiffFileListView view;
    private String url;

    @Override
    public void tryAgain() {
        view.hideErrorView();
        view.showLoadingView();
        GitHubApi.getApi().fetchDiffFileList(url, this::handleResult);
    }

    private void handleResult(GitHubApiResult result) {
        view.removeLoadingView();
        if (result.isSuccessful) {
            ArrayList<DiffFile> diffFiles = (ArrayList<DiffFile>) result.resultObject;
            view.showDiffFiles(diffFiles);
        } else {
            view.showErrorView(result.failure, result.errorMessage);
        }
    }

    public DiffFileListPresenter(DiffFileListView view, String url) {
        this.view = view;
        this.url = url;
        view.showLoadingView();
        GitHubApi.getApi().fetchDiffFileList(url, this::handleResult);
    }
}
