package com.example.masato.githubfeed.view.activity;

import android.media.Image;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.navigator.Navigator;
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

    private Repository repository;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        } else {
            String url = getIntent().getStringExtra("url");
            new RepoPresenter(this, url);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_menu2, menu);
        return true;
    }


    private void restoreState(Bundle savedInstanceState) {
        Repository repository = savedInstanceState.getParcelable("repository");
        if (repository != null) {
            setUpContent(repository);
        } else {
            String url = getIntent().getStringExtra("url");
            new RepoPresenter(this, url);
        }
    }

    @Override
    public void setUpContent(Repository repository) {
        this.repository = repository;
        getSupportActionBar().setTitle(repository.name);
        getSupportActionBar().setSubtitle(repository.owner);

        RepoOverviewFragment overviewFragment =
                FragmentFactory.createRepoOverviewFragment(repository, getString(R.string.tab_overview));
        addFragment(overviewFragment);

        CommitListFragment commitListFragment =
                FragmentFactory.createCommitListFragment(repository, getString(R.string.tab_commits));
        addFragment(commitListFragment);

        IssueListFragment issueListFragment =
                FragmentFactory.createIssueListFragment(repository, getString(R.string.tab_issues));
        addFragment(issueListFragment);

        PullRequestListFragment pullRequestListFragment =
                FragmentFactory.createPullRequestListFragment(repository, getString(R.string.tab_pull_requests));
        addFragment(pullRequestListFragment);
        restorePage();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menu_id_open_in_browser:
                Navigator.navigateToExternalBrowser(this, repository.htmlUrl);
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable("repository", repository);
    }
}
