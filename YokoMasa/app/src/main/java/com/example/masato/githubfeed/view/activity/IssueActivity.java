package com.example.masato.githubfeed.view.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.Issue;
import com.example.masato.githubfeed.navigator.Navigator;
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

    private Issue issue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
           restoreState(savedInstanceState);
        } else {
            String url = getIntent().getStringExtra("url");
            new IssuePresenter(this, url);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_menu1, menu);
        return true;
    }


    private void restoreState(Bundle savedInstanceState) {
        Issue issue = savedInstanceState.getParcelable("issue");
        if (issue != null) {
            showIssue(issue);
        } else {
            String url = getIntent().getStringExtra("url");
            new IssuePresenter(this, url);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menu_id_open_in_browser:
                Navigator.navigateToExternalBrowser(this, issue.htmlUrl);
                break;
            case R.id.menu_id_open_repo:
                Navigator.navigateToRepoView(this, issue.repoUrl);
                break;
        }
        return true;
    }

    @Override
    public void showIssue(Issue issue) {
        this.issue = issue;
        getSupportActionBar().setTitle(R.string.issue_title);
        IssueOverviewFragment issueOverviewFragment =
                FragmentFactory.createIssueOverviewFragment(issue, getString(R.string.tab_overview));
        addFragment(issueOverviewFragment);

        CommentListFragment commentListFragment =
                FragmentFactory.createCommentListFragment(issue.commentsUrl, getString(R.string.tab_comments));
        addFragment(commentListFragment);
        restorePage();
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable("issue", issue);
    }
}
