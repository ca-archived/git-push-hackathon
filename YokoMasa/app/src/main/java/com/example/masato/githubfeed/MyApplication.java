package com.example.masato.githubfeed;

import android.app.Application;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.navigator.Navigator;

/**
 * Created by Masato on 2018/02/07.
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        GitHubApi.init(this);
    }
}
