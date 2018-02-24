package com.example.masato.githubfeed.view;

import com.example.masato.githubfeed.model.PullRequest;

/**
 * Created by Masato on 2018/02/08.
 */

public interface PullRequestListView extends BaseView {
    public void showPullRequest(PullRequest pr);
}
