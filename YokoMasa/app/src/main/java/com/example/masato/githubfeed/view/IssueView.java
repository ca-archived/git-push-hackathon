package com.example.masato.githubfeed.view;

import com.example.masato.githubfeed.model.Issue;
import com.example.masato.githubfeed.model.Repository;

/**
 * Created by Masato on 2018/02/14.
 */

public interface IssueView extends BaseView {
    public void showIssue(Issue issue);

    public void showRepoInfo(Repository repository);
}
