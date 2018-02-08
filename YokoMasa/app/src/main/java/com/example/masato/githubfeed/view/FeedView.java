package com.example.masato.githubfeed.view;

import com.example.masato.githubfeed.model.FeedEntry;
import com.example.masato.githubfeed.model.Profile;

import java.util.List;
import java.util.Map;

/**
 * Created by Masato on 2018/01/19.
 */

public interface FeedView extends BaseView{

    public void setProfile(Profile profile);

    public void closeDrawer();

    public void startFeedFragment(String feedUrl);

    public void showLogInView();

    public void showGlobalFeed();

}
