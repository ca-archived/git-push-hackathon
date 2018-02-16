package com.example.masato.githubfeed.view.activity;

import android.app.Activity;
import android.content.res.Configuration;
import android.graphics.Bitmap;
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
import com.example.masato.githubfeed.githubapi.GitHubUrls;
import com.example.masato.githubfeed.model.Profile;
import com.example.masato.githubfeed.navigator.Navigator;
import com.example.masato.githubfeed.presenter.HomePresenter;
import com.example.masato.githubfeed.view.HomeView;
import com.example.masato.githubfeed.view.fragment.EventListFragment;
import com.example.masato.githubfeed.view.fragment.FragmentFactory;
import com.example.masato.githubfeed.view.fragment.LoadingFragment;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Masato on 2018/01/19.
 */

public class HomeActivity extends AppCompatActivity implements HomeView, AdapterView.OnItemClickListener {

    private HomePresenter presenter;
    private ActionBarDrawerToggle mActionBarToggle;
    private DrawerLayout drawerLayout;
    private LoadingFragment loadingFragment;
    private boolean firstTimeBoot;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        firstTimeBoot = savedInstanceState == null;
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
        presenter = new HomePresenter(this);
        if (firstTimeBoot) {
            showLoadingView();
        }
    }

    public void showLoadingView() {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        loadingFragment = new LoadingFragment();
        ft.add(R.id.feed_mother, loadingFragment);
        ft.commit();
    }

    @Override
    public void hideLoadingView() {
        if (loadingFragment == null) {
            return;
        }
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.remove(loadingFragment);
        ft.commit();
        loadingFragment = null;
    }

    @Override
    public void setUpContent(Profile profile) {
        if (firstTimeBoot) {
            setUpFragment(profile);
            setUpDrawerContent(profile);
        }
    }

    private void setUpDrawerContent(Profile profile) {
        CircleImageView imageView = (CircleImageView) findViewById(R.id.feed_nav_menu_icon);
        AppCompatTextView name = (AppCompatTextView) findViewById(R.id.feed_nav_menu_name);
        imageView.setImageBitmap(profile.icon);
        name.setText(profile.name);
    }

    private void setUpFragment(Profile profile) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        EventListFragment eventListFragment =
                FragmentFactory.createEventListFragment(GitHubUrls.getEventUrl(profile), "");
        ft.add(R.id.feed_mother, eventListFragment);
        ft.commit();
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
    public void showLogInView() {
        Navigator.navigateToLogInView(this);
    }

    @Override
    public void showGlobalFeed() {
        Navigator.navigateToGlobalFeedView(this);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showToast(int stringId) {
        Toast.makeText(this, stringId, Toast.LENGTH_LONG).show();
    }

    private class MDrawerToggle extends ActionBarDrawerToggle {
        public MDrawerToggle(Activity activity, DrawerLayout drawerLayout, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
            super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
        }
    }

}
