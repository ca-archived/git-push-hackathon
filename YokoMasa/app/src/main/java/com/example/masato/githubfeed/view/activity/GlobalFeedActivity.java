package com.example.masato.githubfeed.view.activity;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.view.fragment.FeedFragment;

/**
 * Created by Masato on 2018/01/27.
 */

public class GlobalFeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_global_feed);

        Toolbar toolbar = (Toolbar) findViewById(R.id.global_feed_tool_bar);
        toolbar.setTitle(R.string.feed_timeline);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            Bundle bundle = new Bundle();
            bundle.putString("url", GitHubApi.GLOBAL_FEED_URL);
            FeedFragment ttFragment = new FeedFragment();
            ttFragment.setArguments(bundle);
            ft.add(R.id.global_feed_mother, ttFragment);
            ft.commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
