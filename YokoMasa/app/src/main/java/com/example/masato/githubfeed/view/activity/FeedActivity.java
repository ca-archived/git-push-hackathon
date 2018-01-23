package com.example.masato.githubfeed.view.activity;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.model.FeedEntry;
import com.example.masato.githubfeed.model.FeedTitle;
import com.example.masato.githubfeed.presenter.FeedPresenter;
import com.example.masato.githubfeed.view.FeedView;
import com.example.masato.githubfeed.view.adapter.FeedRecyclerViewAdapter;
import com.example.masato.githubfeed.view.adapter.FeedViewPagerAdapter;
import com.example.masato.githubfeed.view.fragment.FeedFragment;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Masato on 2018/01/19.
 */

public class FeedActivity extends AppCompatActivity implements FeedView {

    private FeedPresenter presenter;
    private ViewPager viewPager;
    private FeedViewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        presenter = new FeedPresenter(this);
        presenter.onCreate();
        viewPager = (ViewPager) findViewById(R.id.feed_view_pager);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void preparePager(Map<String, String> feedUrls) {
        adapter = new FeedViewPagerAdapter(getSupportFragmentManager());
        Set<String> keys = feedUrls.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String title = iterator.next();
            Bundle arguments = new Bundle();
            arguments.putString("title", title);
            arguments.putString("url", feedUrls.get(title));
            FeedFragment feedFragment = new FeedFragment();
            feedFragment.setArguments(arguments);
            adapter.addFragment(feedFragment);
        }
        viewPager.setAdapter(adapter);
    }

}
