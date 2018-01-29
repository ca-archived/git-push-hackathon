package com.example.masato.githubfeed.view.activity;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * Created by Masato on 2018/01/27.
 */

public class RepoActivity extends AppCompatActivity implements RepoView, View.OnClickListener {

    private RepoPresenter presenter;
    private ImageView star;
    private ImageView watch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repo);
        LinearLayout starLayout = (LinearLayout) findViewById(R.id.repo_star_layout);
        LinearLayout watchLayout = (LinearLayout) findViewById(R.id.repo_watch_layout);
        starLayout.setOnClickListener(this);
        watchLayout.setOnClickListener(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.repo_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        star = (ImageView) findViewById(R.id.repo_star_image);
        watch = (ImageView) findViewById(R.id.repo_watch_image);
        presenter = new RepoPresenter(this, getIntent().getStringExtra("url"));
        presenter.onCreate();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.repo_star_layout) {
            presenter.onStarPressed();
        } else {
            presenter.onSubscribePressed();
        }
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showToast(int stringId) {
        Toast.makeText(this, stringId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showRepo(Repository repository) {
        AppCompatTextView star = (AppCompatTextView) findViewById(R.id.repo_star);
        AppCompatTextView watch = (AppCompatTextView) findViewById(R.id.repo_watch);
        AppCompatTextView fork = (AppCompatTextView) findViewById(R.id.repo_fork);
        star.setText(Integer.toString(repository.stars));
        watch.setText(Integer.toString(repository.watches));
        fork.setText(Integer.toString(repository.forks));
        getSupportActionBar().setTitle(repository.name);
        getSupportActionBar().setSubtitle(repository.owner);
    }

    @Override
    public void showReadMe(String readMeHtml) {
        WebView webView = (WebView) findViewById(R.id.repo_readme_web_view);
        webView.loadDataWithBaseURL("https://github.com", readMeHtml, "text/html", "utf-8", null);
    }

    @Override
    public void setStarActivated(boolean activated) {
        if (activated) {
            star.setImageResource(R.drawable.star_active);
        } else {
            star.setImageResource(R.drawable.star);
        }
    }

    @Override
    public void setWatchActivated(boolean activated) {
        if (activated) {
            watch.setImageResource(R.drawable.watch_active);
        } else {
            watch.setImageResource(R.drawable.watch);
        }
    }
}
