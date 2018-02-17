package com.example.masato.githubfeed.view.activity;

import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.view.fragment.EventListFragment;
import com.example.masato.githubfeed.view.fragment.FeedListFragment;
import com.example.masato.githubfeed.view.fragment.FragmentFactory;

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
            EventListFragment eventListFragment =
                    FragmentFactory.createEventListFragment("https://api.github.com/events", "");
            ft.add(R.id.global_feed_mother, eventListFragment);
            ft.commit();
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
