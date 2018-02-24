package com.example.masato.githubfeed.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.githubapi.GitHubUrls;
import com.example.masato.githubfeed.model.Issue;
import com.example.masato.githubfeed.model.PullRequest;
import com.example.masato.githubfeed.util.DateUtil;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Masato on 2018/02/09.
 */

public class PullRequestOverviewFragment extends BaseFragment {

    private PullRequest pr;

    @Override
    public void onTryAgain() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_pull_request_overview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        pr = getArguments().getParcelable("pull_request");
        initViews(view);
    }

    private void initViews(View view) {
        initState(view);
        AppCompatTextView authorName = (AppCompatTextView) view.findViewById(R.id.pull_request_overview_author_name);
        AppCompatTextView date = (AppCompatTextView) view.findViewById(R.id.pull_request_overview_date);
        AppCompatTextView title = (AppCompatTextView) view.findViewById(R.id.pull_request_overview_title);
        CircleImageView image = (CircleImageView) view.findViewById(R.id.pull_request_overview_image);
        WebView webView = (WebView) view.findViewById(R.id.pull_request_overview_comment_body);

        authorName.setText(pr.author.name);
        date.setText(DateUtil.getReadableDateForFeed(pr.createdAt, getContext()));
        title.setText(pr.name);
        webView.loadDataWithBaseURL(GitHubUrls.BASE_HTML_URL, pr.bodyHtml, "text/html", "utf-8", null);
        Picasso.with(getContext()).load(pr.author.iconUrl).into(image);
    }

    private void initState(View view) {
        AppCompatTextView stateTextView = view.findViewById(R.id.pull_request_overview_state);
        if (pr.state.equals(Issue.STATE_OPEN)) {
            stateTextView.setText(R.string.issue_state_open);
            stateTextView.setBackgroundColor(getResources().getColor(R.color.issue_open));
        } else {
            stateTextView.setText(R.string.issue_state_closed);
            stateTextView.setBackgroundColor(getResources().getColor(R.color.issue_closed));
        }
    }

}
