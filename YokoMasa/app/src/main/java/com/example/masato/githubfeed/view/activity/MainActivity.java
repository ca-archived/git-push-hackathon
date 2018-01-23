package com.example.masato.githubfeed.view.activity;

import android.content.Context;
import android.content.Intent;
import android.support.customtabs.CustomTabsCallback;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.presenter.MainPresenter;
import com.example.masato.githubfeed.presenter.Presenter;
import com.example.masato.githubfeed.view.MainView;

import java.util.Iterator;
import java.util.Set;

public class MainActivity extends AppCompatActivity implements MainView {

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);
    }

    @Override
    public void initGitHubApi() {
        GitHubApi.init(getSharedPreferences("token", Context.MODE_PRIVATE), getResources());
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void navigateToLogInView() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void navigateToFeedView() {
        Intent intent = new Intent(this, FeedActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
