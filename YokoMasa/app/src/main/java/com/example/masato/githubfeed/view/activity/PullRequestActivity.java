package com.example.masato.githubfeed.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.masato.githubfeed.model.PullRequest;

/**
 * Created by Masato on 2018/02/08.
 */

public class PullRequestActivity extends ViewPagerActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PullRequest pullRequest = getIntent().getParcelableExtra("pull_request");
    }

}
