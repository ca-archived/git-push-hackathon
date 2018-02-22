package com.example.masato.githubfeed.view.activity;

import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.model.Profile;
import com.example.masato.githubfeed.navigator.Navigator;
import com.example.masato.githubfeed.presenter.MainPresenter;
import com.example.masato.githubfeed.view.BaseView;
import com.example.masato.githubfeed.view.MainView;
import com.example.masato.githubfeed.view.fragment.TryAgainDialogFragment;

public class MainActivity extends AppCompatActivity implements MainView, TryAgainDialogFragment.TryAgainListener {

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
    public void onTryAgain() {
        presenter.tryAgain();
    }

    @Override
    public void showTryAgainDialog() {
        DialogFragment dialogFragment = new TryAgainDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "");
    }

    @Override
    public void showLogInView() {
        Navigator.navigateToLogInView(this);
    }

    @Override
    public void showHomeView() {
        Navigator.navigateToHomeView(this);
    }

    @Override
    public void showToast(int stringId) {
        Toast.makeText(this, stringId, Toast.LENGTH_LONG).show();
    }

}
