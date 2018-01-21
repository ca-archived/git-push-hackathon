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

public class FeedActivity extends AppCompatActivity implements FeedFragment.FeedFetchRequestListener,
        ViewPager.OnPageChangeListener, FeedView {

    private FeedPresenter presenter;
    private ViewPager viewPager;
    private PagerTitleStrip pagerTitleStrip;
    private FeedViewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        presenter = new FeedPresenter(this);
        viewPager = (ViewPager) findViewById(R.id.feed_view_pager);
        pagerTitleStrip = (PagerTitleStrip) findViewById(R.id.feed_pager_title_strip);
        preparePager();
    }

    private void preparePager() {
        adapter = new FeedViewPagerAdapter(getSupportFragmentManager());
        Map<String, String> urls = GitHubApi.getApi().getFeedUrls();
        Set<String> keys = urls.keySet();
        Iterator<String> iterator = keys.iterator();
        while (iterator.hasNext()) {
            String identifier = iterator.next();
            String title = FeedTitle.getTitleFromIdentifier(identifier, this);
            String url = urls.get(identifier);
            Bundle arguments = new Bundle();
            arguments.putString("title", title);
            arguments.putString("url", url);
            FeedFragment feedFragment = new FeedFragment();
            feedFragment.setArguments(arguments);
            adapter.addFragment(feedFragment);
        }
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int fragmentNumber) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onFeedFetchRequest(int fragmentNumber, String url, int page) {
        presenter.onFeedFetchRequested(fragmentNumber, url, page);
    }

    @Override
    public void addFeedEntry(int fragmentNumber, List<FeedEntry> feedEntries) {
        adapter.addFeedEntries(fragmentNumber, feedEntries);
    }

    @Override
    public void disableRefreshing(int fragmentNumber) {
        adapter.disableRefreshing(fragmentNumber);
    }

}
