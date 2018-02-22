package com.example.masato.githubfeed.view.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.PullRequest;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.navigator.Navigator;
import com.example.masato.githubfeed.presenter.PullRequestPresenter;
import com.example.masato.githubfeed.view.PullRequestView;
import com.example.masato.githubfeed.view.fragment.CommentListFragment;
import com.example.masato.githubfeed.view.fragment.CommitListFragment;
import com.example.masato.githubfeed.view.fragment.DiffFileListFragment;
import com.example.masato.githubfeed.view.fragment.FragmentFactory;
import com.example.masato.githubfeed.view.fragment.PullRequestOverviewFragment;

/**
 * Created by Masato on 2018/02/08.
 */

public class PullRequestActivity extends ViewPagerActivity implements PullRequestView {

    private PullRequest pr;
    private PullRequestPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        } else {
            String url = getIntent().getStringExtra("url");
            presenter = new PullRequestPresenter(this, url);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.activity_menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.menu_id_open_in_browser:
                Navigator.navigateToExternalBrowser(this, pr.htmlUrl);
                break;
            case R.id.menu_id_open_repo:
                Navigator.navigateToRepoView(this, pr.repository.baseUrl);
                break;
        }
        return true;
    }

    private void restoreState(Bundle savedInstanceState) {
        PullRequest pr = savedInstanceState.getParcelable("pr");
        if (pr != null) {
            showPullRequest(pr);
        } else {
            String url = getIntent().getStringExtra("url");
            presenter = new PullRequestPresenter(this, url);
        }
    }

    @Override
    public void showPullRequest(PullRequest pr) {
        this.pr = pr;
        setUpActionBar(pr);

        PullRequestOverviewFragment pullRequestOverviewFragment =
                FragmentFactory.createPullRequestOverviewFragment(pr, getString(R.string.tab_overview));
        addFragment(pullRequestOverviewFragment);

        CommentListFragment commentListFragment =
                FragmentFactory.createCommentListFragment(pr.commentsUrl, getString(R.string.tab_comments));
        addFragment(commentListFragment);

        CommitListFragment commitListFragment =
                FragmentFactory.createCommitListFragment(pr.commitsUrl, getString(R.string.tab_commits));
        addFragment(commitListFragment);

        DiffFileListFragment diffFileListFragment =
                FragmentFactory.createDiffFileListFragment(pr.diffUrl, getString(R.string.tab_file_changed));
        addFragment(diffFileListFragment);
    }


    @Override
    public void onTryAgain() {

    }

    @Override
    public void showRepoInfo(Repository repository) {
        getSupportActionBar().setSubtitle(repository.fullName);
    }

    private void setUpActionBar(PullRequest pr) {
        StringBuilder builder = new StringBuilder();
        builder.append(getString(R.string.tab_pull_requests));
        builder.append(" ");
        builder.append(getString(R.string.hash_tag));
        builder.append(pr.number);
        getSupportActionBar().setTitle(builder.toString());
    }

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putParcelable("pr", pr);
    }
}
