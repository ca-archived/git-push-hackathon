package com.example.masato.githubfeed.view;

import android.graphics.Bitmap;

import com.example.masato.githubfeed.model.FeedEntry;
import com.example.masato.githubfeed.model.Profile;

import java.util.List;
import java.util.Map;

/**
 * Created by Masato on 2018/01/19.
 */

public interface HomeView extends BaseView{

    public void hideLoadingView();

    public void closeDrawer();

    public void setUpContent(Profile profile);

    public void showLogInView();

    public void showGlobalFeed();

}
