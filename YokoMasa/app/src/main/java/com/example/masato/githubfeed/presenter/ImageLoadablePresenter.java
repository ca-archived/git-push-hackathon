package com.example.masato.githubfeed.presenter;

import android.graphics.Bitmap;

import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubApiResult;
import com.example.masato.githubfeed.view.ImageLoadableView;

/**
 * Created by Masato on 2018/02/03.
 */

public class ImageLoadablePresenter {

    private ImageLoadableView view;

    public void onFetchImage(String url) {
        GitHubApi.getApi().fetchBitmap(url, this::handleResult);
    }

    private void handleResult(GitHubApiResult result) {
        if (result.isSuccessful) {
            Bitmap bitmap = (Bitmap) result.resultObject;
            view.showImage(bitmap);
        }
    }

    public ImageLoadablePresenter(ImageLoadableView view) {
        this.view = view;
    }
}
