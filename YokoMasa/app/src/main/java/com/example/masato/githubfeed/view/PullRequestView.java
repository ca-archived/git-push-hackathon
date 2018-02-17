package com.example.masato.githubfeed.view;

import com.example.masato.githubfeed.model.PullRequest;
import com.example.masato.githubfeed.model.Repository;

/**
 * Created by Masato on 2018/02/14.
 */

public interface PullRequestView {
    public void showPullRequest(PullRequest pr);

    public void showRepoInfo(Repository repository);
}
