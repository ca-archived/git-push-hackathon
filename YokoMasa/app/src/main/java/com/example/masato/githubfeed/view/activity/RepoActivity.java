package com.example.masato.githubfeed.view.activity;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.presenter.RepoPresenter;
import com.example.masato.githubfeed.view.RepoView;
import com.example.masato.githubfeed.view.adapter.FragmentListPagerAdapter;
import com.example.masato.githubfeed.view.fragment.BaseFragment;
import com.example.masato.githubfeed.view.fragment.CommitListFragment;
import com.example.masato.githubfeed.view.fragment.FragmentFactory;
import com.example.masato.githubfeed.view.fragment.IssueListFragment;
import com.example.masato.githubfeed.view.fragment.PullRequestListFragment;
import com.example.masato.githubfeed.view.fragment.RepoOverviewFragment;

/**
 * Created by Masato on 2018/01/27.
 */

public class RepoActivity extends ViewPagerActivity implements RepoView {

    private RepoPresenter presenter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpPresenterIfNeeded();
    }

    private void setUpPresenterIfNeeded() {
        String url = getIntent().getStringExtra("url");
        Repository repository = getIntent().getParcelableExtra("repository");
        if (repository != null) {
            setUpContent(repository);
        } else {
            presenter = new RepoPresenter(this, url);
        }
    }

    @Override
    public void setUpContent(Repository repository) {
        getSupportActionBar().setTitle(repository.name);
        getSupportActionBar().setSubtitle(repository.owner);

        RepoOverviewFragment overviewFragment =
                FragmentFactory.createRepoOverviewFragment(repository, getString(R.string.tab_overview));
        addFragment(overviewFragment);

        IssueListFragment issueListFragment =
                FragmentFactory.createIssueListFragment(repository, getString(R.string.tab_issues));
        addFragment(issueListFragment);

        CommitListFragment commitListFragment =
                FragmentFactory.createCommitListFragment(repository, getString(R.string.tab_commits));
        addFragment(commitListFragment);

        PullRequestListFragment pullRequestListFragment =
                FragmentFactory.createPullRequestListFragment(repository, getString(R.string.tab_pull_requests));
        addFragment(pullRequestListFragment);
    }
}
