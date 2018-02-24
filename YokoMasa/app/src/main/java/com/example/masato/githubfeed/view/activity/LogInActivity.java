package com.example.masato.githubfeed.view.activity;

import android.content.ComponentName;
import android.content.pm.PackageManager;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsIntent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.githubapi.GitHubUrls;
import com.example.masato.githubfeed.model.Profile;
import com.example.masato.githubfeed.navigator.Navigator;
import com.example.masato.githubfeed.presenter.LoginPresenter;
import com.example.masato.githubfeed.view.LoginView;
import com.example.masato.githubfeed.view.fragment.ProfileFragment;

/**
 * Created by Masato on 2018/01/17.
 */

public class LogInActivity extends AppCompatActivity implements View.OnClickListener, LoginView {

    private static final String CHROME_PACKAGE_NAME = "com.android.chrome";

    private LoginPresenter presenter;
    private AppCompatTextView loginDescription;
    private AppCompatButton loginButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter = new LoginPresenter(this);
        loginButton = (AppCompatButton) findViewById(R.id.login_button);
        loginButton.setOnClickListener(this);
        loginDescription = (AppCompatTextView) findViewById(R.id.login_description);
        if (isChromeInstalled()) {
            warmUpChrome();
        }
    }

    @Override
    public void onClick(View view) {
        presenter.onLoginButtonPressed();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri uri = intent.getData();
        if (uri != null) {
            presenter.onCodeFetched(uri.getQueryParameter("code"));
        }
    }

    @Override
    public void showToast(int stringId) {
        Toast.makeText(this, stringId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void disableLogInButton() {
        loginButton.setEnabled(false);
    }

    @Override
    public void enableLogInButton() {
        loginButton.setEnabled(true);
    }

    @Override
    public void showLoginWaiting() {
        loginDescription.setText(R.string.login_waiting);
    }

    @Override
    public void showProfile(Profile profile) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("profile", profile);
        ProfileFragment profileFragment = new ProfileFragment();
        profileFragment.setArguments(bundle);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.setCustomAnimations(R.anim.profile_fragment_animation, R.anim.profile_fragment_animation);
        transaction.add(R.id.login_mother, profileFragment, null);
        transaction.commit();
    }

    @Override
    public void showLoginError(Failure failure) {
        loginDescription.setText(failure.textId);
    }

    @Override
    public void showHomeView() {
        Navigator.navigateToHomeView(this);
    }

    @Override
    public void startBrowser() {
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        builder.setToolbarColor(getResources().getColor(R.color.colorPrimaryDark));
        CustomTabsIntent customTabsIntent = builder.build();
        if (isChromeInstalled()) {
            customTabsIntent.intent.setPackage(CHROME_PACKAGE_NAME);
        }
        String oauthUrl = GitHubUrls.getLoginUrl(getString(R.string.client_id));
        customTabsIntent.launchUrl(this, Uri.parse(oauthUrl));
    }

    private void warmUpChrome() {
        CustomTabsClient.bindCustomTabsService(this, CHROME_PACKAGE_NAME, new CustomTabsServiceConnection() {
            @Override
            public void onCustomTabsServiceConnected(ComponentName name, CustomTabsClient client) {
                client.warmup(0);
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {

            }
        });
    }

    private boolean isChromeInstalled() {
        try {
            getPackageManager().getPackageInfo(CHROME_PACKAGE_NAME, PackageManager.GET_SERVICES);
        } catch (PackageManager.NameNotFoundException nnfe) {
            return false;
        }
        return true;
    }
}
