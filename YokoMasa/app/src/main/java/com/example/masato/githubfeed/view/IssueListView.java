package com.example.masato.githubfeed.view;

import com.example.masato.githubfeed.model.Issue;

/**
 * Created by Masato on 2018/02/03.
 */

public interface IssueListView extends BaseView {
    public void navigateToIssueView(Issue issue);
}
