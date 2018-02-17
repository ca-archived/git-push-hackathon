package com.example.masato.githubfeed.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.view.adapter.FragmentListPagerAdapter;
import com.example.masato.githubfeed.view.fragment.BaseFragment;

/**
 * Created by Masato on 2018/02/03.
 */

public class ViewPagerActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FragmentListPagerAdapter adapter;
    private boolean FTProhibited;
    private int storedPage;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_view_pager_layout);
        viewPager = (ViewPager) findViewById(R.id.general_view_pager_view_pager);
        adapter = new FragmentListPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        tabLayout = (TabLayout) findViewById(R.id.general_view_pager_tab_layout);
        tabLayout.setupWithViewPager(viewPager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.general_view_pager_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        if (savedInstanceState != null) {
            storedPage = savedInstanceState.getInt("page");
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        FTProhibited = false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        FTProhibited = true;
        outState.putInt("page", viewPager.getCurrentItem());
    }

    protected void restorePage() {
        setPage(storedPage);
    }

    protected void setPage(int page) {
        viewPager.setCurrentItem(page);
    }

    protected void addFragment(BaseFragment fragment) {
        if (FTProhibited) {
            return;
        }
        adapter.addFragment(fragment);
    }

    protected void addFragment(BaseFragment fragment, int position) {
        if (FTProhibited) {
            return;
        }
        adapter.addFragment(fragment, position);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return false;
    }

}
