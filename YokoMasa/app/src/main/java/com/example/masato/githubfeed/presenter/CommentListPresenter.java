package com.example.masato.githubfeed.presenter;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.model.Comment;
import com.example.masato.githubfeed.view.PaginatingListView;

import java.util.List;

/**
 * Created by Masato on 2018/02/03.
 */

public class CommentListPresenter extends PaginatingListPresenter<Comment> {

    private String commentsUrl;

    @Override
    public void onElementClicked(Comment element) {

    }

    @Override
    protected void onFetchElement(int page) {
        GitHubApi.getApi().fetchCommentList(commentsUrl, page, this::handleResult);
    }

    private void handleResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            List<Comment> commentList = (List<Comment>) result.resultObject;
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
