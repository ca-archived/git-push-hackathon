package com.example.masato.githubfeed.view.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
    private boolean FTProhibited;

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

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onRestoreInstanceState(savedInstanceState, persistentState);
        FTProhibited = false;
    }

    private void showAuthor(Commit commit) {
        if (commit.author == null) {
            return;
        }
        if (commit.author.icon != null) {
            showAuthorIcon(commit.author.icon);
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
        if (commit.committer.icon != null) {
            showCommitterIcon(commit.committer.icon);
        }
        AppCompatTextView name = (AppCompatTextView) findViewById(R.id.commit_committer_name);
        AppCompatTextView date = (AppCompatTextView) findViewById(R.id.commit_committer_date);
        name.setText(commit.committer.name);
        date.setText(DateUtil.getReadableDateForFeed(commit.committerDate, this));
        ViewGroup author = (ViewGroup) findViewById(R.id.commit_committer);
        author.setVisibility(View.VISIBLE);
    }

    @Override
    public void showAuthorIcon(Bitmap icon) {
        ImageView imageView = (ImageView) findViewById(R.id.commit_author_icon);
        imageView.setImageBitmap(icon);
    }

    @Override
    public void showCommitterIcon(Bitmap icon) {
        ImageView imageView = (ImageView) findViewById(R.id.commit_committer_icon);
        imageView.setImageBitmap(icon);
    }

    @Override
    public void showDiffFileList(ArrayList<DiffFile> diffFiles) {
        if (FTProhibited) {
            return;
        }
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

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FTProhibited = true;
    }
}
