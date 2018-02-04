package com.example.masato.githubfeed.view.activity;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.presenter.RepoPresenter;
import com.example.masato.githubfeed.view.RepoView;
import com.example.masato.githubfeed.view.adapter.FragmentListPagerAdapter;
import com.example.masato.githubfeed.view.fragment.BaseFragment;
import com.example.masato.githubfeed.view.fragment.IssueListFragment;
import com.example.masato.githubfeed.view.fragment.RepoOverviewFragment;

/**
 * Created by Masato on 2018/01/27.
 */

public class RepoActivity extends AppCompatActivity implements RepoView {

    private RepoPresenter presenter;
    private ViewPager viewPager;
    private TabLayout tabLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.general_view_pager_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.general_view_pager_tool_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.general_view_pager_view_pager);
        tabLayout = (TabLayout) findViewById(R.id.general_view_pager_tab_layout);
        setUpPresenter();
    }

    private void setUpPresenter() {
        String url = getIntent().getStringExtra("url");
        Repository repository = getIntent().getParcelableExtra("repository");
        if (repository != null) {
            presenter = new RepoPresenter(this, repository);
        } else {
            presenter = new RepoPresenter(this, url);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void setUpContent(Repository repository) {
        setUpActionbarTitle(repository);
        FragmentListPagerAdapter pagerAdapter = new FragmentListPagerAdapter(getSupportFragmentManager());

        Bundle bundle = new Bundle();
        bundle.putParcelable("repository", repository);
        bundle.putString("name", getString(R.string.tab_overview));
        RepoOverviewFragment overviewFragment = new RepoOverviewFragment();
        overviewFragment.setArguments(bundle);
        pagerAdapter.addFragment(overviewFragment);

        bundle = new Bundle();
        bundle.putParcelable("repository", repository);
        bundle.putString("name", getString(R.string.tab_issues));
        IssueListFragment issueListFragment = new IssueListFragment();
        issueListFragment.setArguments(bundle);
        pagerAdapter.addFragment(issueListFragment);

        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setUpActionbarTitle(Repository repository) {
        getSupportActionBar().setTitle(repository.name);
        getSupportActionBar().setSubtitle(repository.owner);
    }
}
