package com.example.masato.githubfeed.view;

import com.example.masato.githubfeed.githubapi.Failure;

/**
 * Created by Masato on 2018/01/23.
 */

public interface BaseView {
    public void showToast(String text);

    public void showToast(int stringId);

    public void showErrorView(Failure failure, String message);

    public void hideErrorView();

    public void showLoadingView();

    public void hideLoadingView();
}
