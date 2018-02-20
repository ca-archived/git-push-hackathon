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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.model.Commit;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.model.diff.DiffFile;
import com.example.masato.githubfeed.navigator.Navigator;
import com.example.masato.githubfeed.presenter.CommitPresenter;
import com.example.masato.githubfeed.util.DateUtil;
import com.example.masato.githubfeed.view.CommitView;
import com.example.masato.githubfeed.view.fragment.CommitOverviewFragment;
import com.example.masato.githubfeed.view.fragment.DiffFileListFragment;
import com.example.masato.githubfeed.view.fragment.FragmentFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/02/06.
 */

public class CommitActivity extends BaseActivity implements CommitView {

    private CommitPresenter presenter;
    private Commit commit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit);
        commit = getIntent().getParcelableExtra("commit");

        Toolbar toolbar = (Toolbar) findViewById(R.id.commit_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        String actionBarTitle = getString(R.string.commit_action_bar_title) + commit.getShortenedSha();
        getSupportActionBar().setTitle(actionBarTitle);

        presenter = new CommitPresenter(this, commit);

        if (savedInstanceState == null) {
            doSafeFTTransaction(() -> {
                CommitOverviewFragment fragment = FragmentFactory.createCommitOverviewFragment(commit, "");
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.commit_overview, fragment);
                ft.commit();
            });
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
                Navigator.navigateToExternalBrowser(this, commit.htmlUrl);
                break;
            case R.id.menu_id_open_repo:
                Navigator.navigateToRepoView(this, commit.getRepoUrl());
                break;
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
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
