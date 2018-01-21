package com.example.masato.githubfeed.view;

/**
 * Created by Masato on 2018/01/19.
 */

public interface LoginView {

    public void startBrowser();

    public void showLoginWaiting();

    public void showLoginSucceeded();

    public void showLoginError(String message);
}
