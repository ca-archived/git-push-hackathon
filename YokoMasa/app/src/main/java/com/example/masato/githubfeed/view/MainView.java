package com.example.masato.githubfeed.view;

import com.example.masato.githubfeed.model.Profile;

/**
 * Created by Masato on 2018/02/07.
 */

public interface MainView {
    public void showLogInView();

    public void showHomeView();

    public void showToast(int stringId);

    public void showTryAgainDialog();
}
