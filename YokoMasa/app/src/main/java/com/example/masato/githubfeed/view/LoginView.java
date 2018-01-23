package com.example.masato.githubfeed.view;

import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.model.Profile;

/**
 * Created by Masato on 2018/01/19.
 */

public interface LoginView extends BaseView{

    public void startBrowser();

    public void showLoginWaiting();

    public void navigateToFeedView();

    public void showLoginError(Failure failure);

    public void showProfile(Profile profile);

}
