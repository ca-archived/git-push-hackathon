package com.example.masato.githubfeed.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.Issue;
import com.example.masato.githubfeed.view.adapter.FragmentListPagerAdapter;
import com.example.masato.githubfeed.view.fragment.CommentListFragment;
import com.example.masato.githubfeed.view.fragment.IssueOverviewFragment;

/**
 * Created by Masato on 2018/02/03.
 */

public class IssueActivity extends ViewPagerActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpContent(getIntent().getParcelableExtra("issue"));
    }

    public void setUpContent(Issue issue) {
        getSupportActionBar().setTitle(R.string.issue_title);
        Bundle bundle = new Bundle();
        bundle.putParcelable("issue", issue);
        bundle.putString("name", getString(R.string.tab_overview));
        IssueOverviewFragment issueOverviewFragment = new IssueOverviewFragment();
        issueOverviewFragment.setArguments(bundle);
        addFragment(issueOverviewFragment);

        bundle = new Bundle();
        bundle.putString("url", issue.commentsUrl);
        bundle.putString("name", getString(R.string.tab_comments));
        CommentListFragment commentListFragment = new CommentListFragment();
        commentListFragment.setArguments(bundle);
        addFragment(commentListFragment);
    }
}