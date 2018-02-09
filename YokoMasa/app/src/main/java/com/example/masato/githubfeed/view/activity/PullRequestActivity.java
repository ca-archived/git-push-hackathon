package com.example.masato.githubfeed.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.PullRequest;
import com.example.masato.githubfeed.view.fragment.CommentListFragment;
import com.example.masato.githubfeed.view.fragment.CommitListFragment;
import com.example.masato.githubfeed.view.fragment.DiffFileListFragment;
import com.example.masato.githubfeed.view.fragment.FragmentFactory;
import com.example.masato.githubfeed.view.fragment.PullRequestOverviewFragment;

/**
 * Created by Masato on 2018/02/08.
 */

public class PullRequestActivity extends ViewPagerActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PullRequest pullRequest = getIntent().getParcelableExtra("pull_request");
        setUpContent(pullRequest);
    }

    private void setUpContent(PullRequest pr) {
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

    private void setUpActionBar(PullRequest pr) {
        StringBuilder builder = new StringBuilder();
        builder.append(getString(R.string.tab_pull_requests));
        builder.append(" ");
        builder.append(getString(R.string.hash_tag));
        builder.append(pr.number);
        getSupportActionBar().setTitle(builder.toString());
    }

}
