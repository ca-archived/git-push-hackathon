package com.example.masato.githubfeed.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.presenter.RepoOverviewPresenter;
import com.example.masato.githubfeed.view.RepoOverviewView;

/**
 * Created by Masato on 2018/02/02.
 */

public class RepoOverviewFragment extends BaseFragment implements RepoOverviewView, View.OnClickListener {

    private RepoOverviewPresenter presenter;
    private AppCompatTextView starCount;
    private AppCompatTextView watchCount;
    private AppCompatTextView forkCount;
    private ImageView star;
    private ImageView watch;
    private WebView readmeWebView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Repository repository = getArguments().getParcelable("repository");
        presenter = new RepoOverviewPresenter(this, repository);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_repo_overview, container, false);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        readmeWebView = (WebView) view.findViewById(R.id.repo_readme_web_view);
        starCount = (AppCompatTextView) view.findViewById(R.id.repo_star);
        watchCount = (AppCompatTextView) view.findViewById(R.id.repo_watch);
        forkCount = (AppCompatTextView) view.findViewById(R.id.repo_fork);
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
    public void showOverview(Repository repository) {
        starCount.setText(Integer.toString(repository.stars));
        watchCount.setText(Integer.toString(repository.watches));
        forkCount.setText(Integer.toString(repository.forks));
    }

    @Override
    public void showReadMe(String readMeHtml) {
        readmeWebView.loadDataWithBaseURL("https://github.com", readMeHtml, "text/html", "utf-8", null);
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
}
