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
import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.view.adapter.FragmentListPagerAdapter;
import com.example.masato.githubfeed.view.fragment.BaseFragment;

/**
 * Created by Masato on 2018/02/03.
 */

public abstract class ViewPagerActivity extends BaseActivity {

    private ViewPager viewPager;
    private TabLayout tabLayout;
    private FragmentListPagerAdapter adapter;
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

    public void showLoadingView() {
        showLoadingFragment(R.id.general_recyclerView_mother);
    }

    public void hideLoadingView() {
        removeLoadingFragment();
    }

    public void showErrorView(Failure failure, String errorMessage) {
        showErrorFragment(R.id.general_view_pager_mother, failure, errorMessage);
    }

    public void hideErrorView() {
        removeErrorFragment();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("page", viewPager.getCurrentItem());
    }

    protected void restorePage() {
        setPage(storedPage);
    }

    protected void setPage(int page) {
        viewPager.setCurrentItem(page);
    }

    protected void addFragment(BaseFragment fragment) {
        doSafeFTTransaction(() -> {
            adapter.addFragment(fragment);
            restorePage();
        });
    }

    protected void addFragment(BaseFragment fragment, int position) {
        doSafeFTTransaction(() -> {
            adapter.addFragment(fragment, position);
            restorePage();
        });
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
