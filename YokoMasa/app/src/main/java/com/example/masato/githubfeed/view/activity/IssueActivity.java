package com.example.masato.githubfeed.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.Issue;
import com.example.masato.githubfeed.presenter.IssuePresenter;
import com.example.masato.githubfeed.view.IssueView;
import com.example.masato.githubfeed.view.adapter.FragmentListPagerAdapter;
import com.example.masato.githubfeed.view.fragment.CommentListFragment;
import com.example.masato.githubfeed.view.fragment.FragmentFactory;
import com.example.masato.githubfeed.view.fragment.IssueOverviewFragment;

/**
 * Created by Masato on 2018/02/03.
 */

public class IssueActivity extends ViewPagerActivity implements IssueView {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String url = getIntent().getStringExtra("url");
        new IssuePresenter(this, url);
    }

    @Override
    public void showIssue(Issue issue) {
        getSupportActionBar().setTitle(R.string.issue_title);
        IssueOverviewFragment issueOverviewFragment =
                FragmentFactory.createIssueOverviewFragment(issue, getString(R.string.tab_overview));
        addFragment(issueOverviewFragment);

        CommentListFragment commentListFragment =
                FragmentFactory.createCommentListFragment(issue.commentsUrl, getString(R.string.tab_comments));
        addFragment(commentListFragment);
    }

}
