package com.example.masato.githubfeed.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

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
    private boolean fragmentUnControllable;

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
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        fragmentUnControllable = true;
    }

    protected void addFragment(BaseFragment fragment) {
        if (fragmentUnControllable) {
            return;
        }
        adapter.addFragment(fragment);
    }

    protected void addFragment(BaseFragment fragment, int position) {
        if (fragmentUnControllable) {
            return;
        }
        adapter.addFragment(fragment, position);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
