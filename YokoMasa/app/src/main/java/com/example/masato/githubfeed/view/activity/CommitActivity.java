package com.example.masato.githubfeed.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.model.Commit;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.model.diff.DiffFile;
import com.example.masato.githubfeed.presenter.CommitPresenter;
import com.example.masato.githubfeed.util.DateUtil;
import com.example.masato.githubfeed.view.CommitView;
import com.example.masato.githubfeed.view.fragment.DiffFileListFragment;
import com.example.masato.githubfeed.view.fragment.FragmentFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/02/06.
 */

public class CommitActivity extends AppCompatActivity implements CommitView {

    private CommitPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit);
        Commit commit = getIntent().getParcelableExtra("commit");

        AppCompatTextView commitMessage = (AppCompatTextView) findViewById(R.id.commit_message);
        commitMessage.setText(commit.getShortenedComment());

        showAuthor(commit);
        showCommitter(commit);

        Toolbar toolbar = (Toolbar) findViewById(R.id.commit_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String actionBarTitle = getString(R.string.commit_action_bar_title) + commit.getShortenedSha();
        getSupportActionBar().setTitle(actionBarTitle);

        presenter = new CommitPresenter(this, commit);
    }

    private void showAuthor(Commit commit) {
        if (commit.author == null) {
            return;
        }
        AppCompatTextView name = (AppCompatTextView) findViewById(R.id.commit_author_name);
        AppCompatTextView date = (AppCompatTextView) findViewById(R.id.commit_author_date);
        name.setText(commit.author.name);
        date.setText(DateUtil.getReadableDateForFeed(commit.authorDate, this));
        ViewGroup author = (ViewGroup) findViewById(R.id.commit_author);
        author.setVisibility(View.VISIBLE);
    }

    private void showCommitter(Commit commit) {
        if (commit.committer == null) {
            return;
        }
        AppCompatTextView name = (AppCompatTextView) findViewById(R.id.commit_committer_name);
        AppCompatTextView date = (AppCompatTextView) findViewById(R.id.commit_committer_date);
        name.setText(commit.committer.name);
        date.setText(DateUtil.getReadableDateForFeed(commit.committerDate, this));
        ViewGroup author = (ViewGroup) findViewById(R.id.commit_committer);
        author.setVisibility(View.VISIBLE);
    }

    @Override
    public void showDiffFileList(ArrayList<DiffFile> diffFiles) {
        DiffFileListFragment diffFileListFragment = FragmentFactory.createDiffFileListFragment(diffFiles, "");
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.commit_diff_file_list_mother, diffFileListFragment);
        ft.commit();
    }

    @Override
    public void showRepoInfo(Repository repository) {
        getSupportActionBar().setSubtitle(repository.fullName);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
