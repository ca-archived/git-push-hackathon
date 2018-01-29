package com.example.masato.githubfeed.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.presenter.RepoPresenter;
import com.example.masato.githubfeed.view.RepoView;

/**
 * Created by Masato on 2018/01/27.
 */

public class RepoActivity extends AppCompatActivity implements RepoView {

    private RepoPresenter presenter;
    private AppCompatTextView title;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.repo_tool_bar);
        setSupportActionBar(toolbar);
        title = (AppCompatTextView) findViewById(R.id.repo_title);
        presenter = new RepoPresenter(this, getIntent().getStringExtra("url"));
        presenter.onCreate();
    }

    @Override
    public void showRepo(Repository repository) {
        String test = "name: " + repository.fullName +
                " stars: " + repository.stars +
                " watches: " + repository.watches +
                " forks: " + repository.forks;
        title.setText(test);
    }

}
