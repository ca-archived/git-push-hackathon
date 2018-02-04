package com.example.masato.githubfeed.view.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.Issue;
import com.example.masato.githubfeed.presenter.ImageLoadablePresenter;
import com.example.masato.githubfeed.util.DateUtil;
import com.example.masato.githubfeed.view.ImageLoadableView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Masato on 2018/02/03.
 */

public class IssueOverviewFragment extends BaseFragment implements ImageLoadableView {

    private ImageLoadablePresenter presenter;
    private CircleImageView image;
    private Issue issue;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ImageLoadablePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_issue_overview, container, false);
        issue = getArguments().getParcelable("issue");
        initViews(view, issue);
        return view;
    }

    private void initViews(View view, Issue issue) {
        initState(view, issue);
        AppCompatTextView authorName = (AppCompatTextView) view.findViewById(R.id.issue_overview_author_name);
        AppCompatTextView date = (AppCompatTextView) view.findViewById(R.id.issue_overview_date);
        AppCompatTextView title = (AppCompatTextView) view.findViewById(R.id.issue_overview_title);
        image = (CircleImageView) view.findViewById(R.id.issue_overview_image);
        WebView webView = (WebView) view.findViewById(R.id.issue_overview_comment_body);

        authorName.setText(issue.author.name);
        date.setText(DateUtil.getReadableDateForFeed(issue.createdAt, getContext()));
        title.setText(issue.name);
        webView.loadDataWithBaseURL("https://github.com", issue.bodyHtml, "text/html", "utf-8", null);
        if (issue.author.icon == null) {
            presenter.onFetchImage(issue.author.iconUrl);
        } else {
            image.setImageBitmap(issue.author.icon);
        }
    }

    private void initState(View view, Issue issue) {
        AppCompatTextView stateTextView = view.findViewById(R.id.issue_overview_state);
        if (issue.state.equals(Issue.STATE_OPEN)) {
            stateTextView.setText(R.string.issue_state_open);
            stateTextView.setBackgroundColor(getResources().getColor(R.color.issue_open));
        } else {
            stateTextView.setText(R.string.issue_state_closed);
            stateTextView.setBackgroundColor(getResources().getColor(R.color.issue_closed));
        }
    }

    @Override
    public void showImage(Bitmap bitmap) {
        issue.author.icon = bitmap;
        image.setImageBitmap(bitmap);
    }
}
