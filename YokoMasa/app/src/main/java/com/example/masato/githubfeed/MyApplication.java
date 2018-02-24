package com.example.masato.githubfeed;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.model.event.EventMapper;
import com.example.masato.githubfeed.navigator.Navigator;
import com.google.android.gms.security.ProviderInstaller;

/**
 * Created by Masato on 2018/02/07.
 */

public class MyApplication extends Application implements ProviderInstaller.ProviderInstallListener {

    @Override
    public void onCreate() {
        super.onCreate();
        GitHubApi.init(this);
        EventMapper.init(this);

        // Android 4.4 以下で発生するSSLのバグ対応
        // https://stackoverflow.com/questions/29916962/javax-net-ssl-sslhandshakeexception-javax-net-ssl-sslprotocolexception-ssl-han
        ProviderInstaller.installIfNeededAsync(this, this);
    }

    @Override
    public void onProviderInstalled() {
        Log.i("gh_feed", "provider installed.");
    }

    @Override
    public void onProviderInstallFailed(int i, Intent intent) {
        Log.i("gh_feed", "provider installation failed.");
    }
}
