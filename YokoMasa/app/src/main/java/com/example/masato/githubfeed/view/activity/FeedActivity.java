package com.example.masato.githubfeed.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.Profile;
import com.example.masato.githubfeed.presenter.FeedPresenter;
import com.example.masato.githubfeed.view.FeedView;
import com.example.masato.githubfeed.view.fragment.FeedFragment;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Masato on 2018/01/19.
 */

public class FeedActivity extends AppCompatActivity implements FeedView, AdapterView.OnItemClickListener {

    private FeedPresenter presenter;
    private ActionBarDrawerToggle mActionBarToggle;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Toolbar toolbar = (Toolbar) findViewById(R.id.feed_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        ListView listView = (ListView) findViewById(R.id.feed_nav_menu_list);
        listView.setAdapter(ArrayAdapter.createFromResource(this, R.array.nav_menu_array, R.layout.feed_nav_menu_list_element));
        listView.setOnItemClickListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.feed_drawer_layout);
        mActionBarToggle = new MDrawerToggle(this, drawerLayout, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(mActionBarToggle);
        presenter = new FeedPresenter(this);
        if (savedInstanceState == null) {
            presenter.onCreate(true);
        } else {
            presenter.onCreate(false);
        }
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mActionBarToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mActionBarToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0:
                presenter.onLogOutSelected();
                break;
            case 1:
                presenter.onGlobalFeedSelected();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mActionBarToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void closeDrawer() {
        drawerLayout.closeDrawers();
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
    public void navigateToGlobalFeedView() {
        Intent intent = new Intent(this, GlobalFeedActivity.class);
        startActivity(intent);
    }

    @Override
    public void startFeedFragment(String feedUrl) {
        Bundle arguments = new Bundle();
        arguments.putString("url", feedUrl);
        FeedFragment feedFragment = new FeedFragment();
        feedFragment.setArguments(arguments);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.feed_mother, feedFragment);
        ft.commit();
    }

    private class MDrawerToggle extends ActionBarDrawerToggle {
        public MDrawerToggle(Activity activity, DrawerLayout drawerLayout, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
        }
    }

}
