package com.example.masato.githubfeed.view;

import com.example.masato.githubfeed.model.Issue;

/**
 * Created by Masato on 2018/02/07.
 */

public interface IssueListView extends BaseView {
    public void showIssue(Issue issue);
}
