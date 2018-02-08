package com.example.masato.githubfeed.view.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.navigator.Navigator;
import com.example.masato.githubfeed.presenter.MainPresenter;
import com.example.masato.githubfeed.view.BaseView;
import com.example.masato.githubfeed.view.MainView;

public class MainActivity extends AppCompatActivity implements MainView {

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter = new MainPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void showLogInView() {
        Navigator.navigateToLogInView(this);
    }

    @Override
    public void showFeedListView() {
        Navigator.navigateToFeedView(this);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showToast(int stringId) {
        Toast.makeText(this, stringId, Toast.LENGTH_LONG).show();
    }

}
