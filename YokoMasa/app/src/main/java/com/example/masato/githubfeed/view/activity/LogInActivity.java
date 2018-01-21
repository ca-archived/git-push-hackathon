package com.example.masato.githubfeed.view.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.presenter.LoginPresenter;
import com.example.masato.githubfeed.presenter.Presenter;
import com.example.masato.githubfeed.view.LoginView;
import com.example.masato.githubfeed.view.adapter.FeedRecyclerViewAdapter;

/**
 * Created by Masato on 2018/01/17.
 */

public class LogInActivity extends AppCompatActivity implements View.OnClickListener, LoginView {

    private LoginPresenter presenter;
    private AppCompatTextView loginDescription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(this);
        AppCompatButton loginButton = (AppCompatButton) findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
        loginDescription = (AppCompatTextView) findViewById(R.id.login_description);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onClick(View view) {
        presenter.loginButtonPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri uri = intent.getData();
        if (uri == null) {
            presenter.onCodeFetched(null);
        }
        presenter.onCodeFetched(uri.getQueryParameter("code"));
    }

    @Override
    public void startBrowser() {
        String url = "https://github.com/login/oauth/authorize?client_id=de861078b4636caf8d1f";
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(url));
    }

    @Override
    public void showLoginWaiting() {
        loginDescription.setText(R.string.login_waiting);
    }

    @Override
    public void showLoginSucceeded() {
        Intent intent = new Intent(this, FeedActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void showLoginError(String message) {
        loginDescription.setText(message);
    }
}
