package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.BaseModel;
import com.example.masato.githubfeed.model.Comment;
import com.example.masato.githubfeed.view.PaginatingListView;

import java.util.List;

/**
 * Created by Masato on 2018/02/03.
 */

public class CommentListPresenter extends PaginatingListPresenter {

    private String commentsUrl;
    private PaginatingListView view;

    @Override
    int onGetPaginatingItemViewType(BaseModel element) {
        return 0;
    }

    @Override
    public void onElementClicked(BaseModel element, int viewType) {

    }

    @Override
    protected void onFetchElement(int page) {
        GitHubApi.getApi().fetchCommentList(commentsUrl, page, this::handleResult);
    }

    private void handleResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            List<BaseModel> commentList = (List<BaseModel>) result.resultObject;
            onFetchSucceeded(commentList);
        } else {
            onFetchFailed(result.failure, result.errorMessage);
        }
    }

    public CommentListPresenter(PaginatingListView view, String commentsUrl) {
        super(view);
        this.view = view;
        this.commentsUrl = commentsUrl;
    }
}
