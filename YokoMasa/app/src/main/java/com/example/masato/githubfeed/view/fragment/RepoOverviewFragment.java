package com.example.masato.githubfeed.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.githubapi.GitHubUrls;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.presenter.RepoOverviewPresenter;
import com.example.masato.githubfeed.view.RepoOverviewView;

/**
 * Created by Masato on 2018/02/02.
 */

public class RepoOverviewFragment extends BaseFragment implements RepoOverviewView, View.OnClickListener {

    private RepoOverviewPresenter presenter;
    private ScrollView scrollView;
    private AppCompatTextView starCount;
    private AppCompatTextView watchCount;
    private AppCompatTextView forkCount;
    private ImageView star;
    private ImageView watch;
    private WebView readmeWebView;
    private int savedScrollX, savedScrollY;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Repository repository = getArguments().getParcelable("repository");
        presenter = new RepoOverviewPresenter(this, repository);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_repo_overview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState != null) {
            restoreState(savedInstanceState);
        }
        initViews(view);
        presenter.onViewCreated();
    }

    private void initViews(View view) {
        LinearLayout starLayout = (LinearLayout) view.findViewById(R.id.repo_star_layout);
        LinearLayout watchLayout = (LinearLayout) view.findViewById(R.id.repo_watch_layout);
        starLayout.setOnClickListener(this);
        watchLayout.setOnClickListener(this);
        star = (ImageView) view.findViewById(R.id.repo_star_image);
        watch = (ImageView) view.findViewById(R.id.repo_watch_image);
        starCount = (AppCompatTextView) view.findViewById(R.id.repo_star);
        watchCount = (AppCompatTextView) view.findViewById(R.id.repo_watch);
        forkCount = (AppCompatTextView) view.findViewById(R.id.repo_fork);
        readmeWebView = (WebView) view.findViewById(R.id.repo_readme_web_view);
        readmeWebView.loadData(getString(R.string.repo_loading_readme), "text/html", "utf-8");
        scrollView = (ScrollView) view.findViewById(R.id.repo_scroll_view);
    }

    private void restoreState(Bundle savedInstanceState) {
        savedScrollX = savedInstanceState.getInt("scroll_x");
        savedScrollY = savedInstanceState.getInt("scroll_y");
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.repo_star_layout) {
            presenter.onStarPressed();
        } else {
            presenter.onSubscribePressed();
        }
    }

    @Override
    public void showLoadingView() {
        showLoadingFragment(R.id.repo_mother);
    }

    @Override
    public void hideLoadingView() {
        removeLoadingFragment();
    }

    @Override
    public void onTryAgain() {
        presenter.tryAgain();
    }

    @Override
    public void showErrorView(Failure failure, String message) {
        showErrorFragment(R.id.repo_mother, failure, message);
    }

    @Override
    public void hideErrorView() {
        removeErrorFragment();
    }

    @Override
    public void showOverview(Repository repository) {
        starCount.setText(Integer.toString(repository.stars));
        watchCount.setText(Integer.toString(repository.watches));
        forkCount.setText(Integer.toString(repository.forks));
    }

    @Override
    public void showReadMe(String readMeHtml) {
        readmeWebView.loadDataWithBaseURL(GitHubUrls.BASE_HTML_URL, readMeHtml, "text/html", "utf-8", null);
        readmeWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress == 100) {
                    scrollView.setScrollX(savedScrollX);
                    scrollView.setScrollY(savedScrollY);
                }
            }
        });
    }

    @Override
    public void showNoReadMe() {
        readmeWebView.loadData(getString(R.string.repo_no_readme), "text/html", "utf-8");
    }

    @Override
    public void setStarActivated(boolean activated) {
        if (activated) {
            star.setImageResource(R.drawable.star_active);
        } else {
            star.setImageResource(R.drawable.star);
        }
    }

    @Override
    public void setWatchActivated(boolean activated) {
        if (activated) {
            watch.setImageResource(R.drawable.watch_active);
        } else {
            watch.setImageResource(R.drawable.watch);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("scroll_x", scrollView.getScrollX());
        outState.putInt("scroll_y", scrollView.getScrollY());
    }
}
