package com.example.masato.githubfeed.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.model.FeedEntry;
import com.example.masato.githubfeed.model.FeedTitle;
import com.example.masato.githubfeed.model.Profile;
import com.example.masato.githubfeed.presenter.FeedPresenter;
import com.example.masato.githubfeed.view.FeedView;
import com.example.masato.githubfeed.view.adapter.FeedRecyclerViewAdapter;
import com.example.masato.githubfeed.view.adapter.FeedViewPagerAdapter;
import com.example.masato.githubfeed.view.fragment.FeedFragment;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Masato on 2018/01/19.
 */

public class FeedActivity extends AppCompatActivity implements FeedView, AdapterView.OnItemClickListener {

    private FeedPresenter presenter;
    private ViewPager viewPager;
    private FeedViewPagerAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.feed_tool_bar);
        setSupportActionBar(toolbar);
        ListView listView = (ListView) findViewById(R.id.feed_nav_menu_list);
        listView.setAdapter(ArrayAdapter.createFromResource(this, R.array.nav_menu_array, R.layout.feed_nav_menu_list_element));
        listView.setOnItemClickListener(this);
        presenter = new FeedPresenter(this);
        presenter.onCreate();
        viewPager = (ViewPager) findViewById(R.id.feed_view_pager);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        presenter.onLogOut();
    }

    @Override
    public void setProfile(Profile profile) {
        AppCompatTextView textView = (AppCompatTextView) findViewById(R.id.feed_nav_menu_name);
        CircleImageView circleImageView = (CircleImageView) findViewById(R.id.feed_nav_menu_icon);
        textView.setText(profile.name);
        circleImageView.setImageBitmap(profile.icon);
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
    public void navigateToLogInView() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
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
