package com.example.masato.githubfeed.view;

import com.example.masato.githubfeed.githubapi.Failure;

/**
 * Created by Masato on 2018/01/19.
 */

public interface LoginView {

    public void startBrowser();

    public void showLoginWaiting();

    public void showLoginSucceeded();

    public void showLoginError(Failure failure);
}
