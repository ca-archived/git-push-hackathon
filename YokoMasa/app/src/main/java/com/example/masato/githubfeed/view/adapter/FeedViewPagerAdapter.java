package com.example.masato.githubfeed.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;

import com.example.masato.githubfeed.model.FeedEntry;
import com.example.masato.githubfeed.view.fragment.FeedFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/01/20.
 */

public class FeedViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<FeedFragment> feedFragments = new ArrayList<>();

    public void addFragment(FeedFragment feedFragment) {
        feedFragments.add(feedFragment);
    }

    @Override
    public Fragment getItem(int position) {
        return feedFragments.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return feedFragments.get(position).getTitle();
    }

    @Override
    public int getCount() {
        return feedFragments.size();
    }

    public FeedViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }
}
