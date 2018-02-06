package com.example.masato.githubfeed.presenter;

import android.os.Parcelable;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.Comment;
import com.example.masato.githubfeed.view.PaginatingListView;

import java.util.List;

/**
 * Created by Masato on 2018/02/03.
 */

public class CommentListPresenter extends PaginatingListPresenter {

    private String commentsUrl;

    @Override
    int onGetPaginatingItemViewType(Parcelable parcelable) {
        return 0;
    }

    @Override
    public void onElementClicked(Parcelable element, int viewType) {

    }

    @Override
    protected void onFetchElement(int page) {
        GitHubApi.getApi().fetchCommentList(commentsUrl, page, this::handleResult);
    }

    private void handleResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            List<Parcelable> commentList = (List<Parcelable>) result.resultObject;
            onFetchedElements(commentList, true);
        } else {
            onFetchedElements(null, false);
        }
    }

    public CommentListPresenter(PaginatingListView view, String commentsUrl) {
        super(view);
        this.commentsUrl = commentsUrl;
    }
}
