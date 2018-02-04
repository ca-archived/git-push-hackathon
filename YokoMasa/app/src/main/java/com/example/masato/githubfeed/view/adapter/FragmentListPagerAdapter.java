package com.example.masato.githubfeed.view.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.example.masato.githubfeed.view.fragment.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/02/02.
 */

public class FragmentListPagerAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> fragmentList = new ArrayList<>();

    public void addFragment(BaseFragment fragment) {
        fragmentList.add(fragment);
        notifyDataSetChanged();
    }

    public void addFragment(BaseFragment fragment, int position) {
        fragmentList.add(position, fragment);
        notifyDataSetChanged();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentList.get(position).getName();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public FragmentListPagerAdapter(FragmentManager fm) {
        super(fm);
    }
}
