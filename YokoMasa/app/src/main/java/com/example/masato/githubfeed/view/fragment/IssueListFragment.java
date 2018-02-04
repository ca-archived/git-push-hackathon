package com.example.masato.githubfeed.view.fragment;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.Issue;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.presenter.IssueListPresenter;
import com.example.masato.githubfeed.presenter.PaginatingListPresenter;
import com.example.masato.githubfeed.util.DateUtil;
import com.example.masato.githubfeed.view.IssueListView;
import com.example.masato.githubfeed.view.activity.IssueActivity;

/**
 * Created by Masato on 2018/02/03.
 */

public class IssueListFragment extends PaginatingListFragment<Issue> implements IssueListView {

    @Override
    public void navigateToIssueView(Issue issue) {
        Intent intent = new Intent(getContext(), IssueActivity.class);
        intent.putExtra("issue", issue);
        startActivity(intent);
    }

    @Override
    protected PaginatingListPresenter<Issue> onCreatePresenter() {
        String url = getArguments().getString("url");
        Repository repository = getArguments().getParcelable("repository");
        if (repository != null) {
            return new IssueListPresenter(this, repository);
        } else {
            return new IssueListPresenter(this, url);
        }
    }

    @Override
    protected PaginatingListViewHolder<Issue> onCreatePaginatingViewHolder(ViewGroup parent) {
        View view = getLayoutInflater().inflate(R.layout.issue_list_element, parent, false);
        return new IssueViewHolder(view);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {

    }

    @Override
    protected void onBindViewHolder(PaginatingListViewHolder holder, Issue element) {
        IssueViewHolder viewHolder = (IssueViewHolder) holder;
        viewHolder.title.setText(element.name);
        viewHolder.date.setText(DateUtil.getReadableDateForFeed(element.createdAt, getContext()));
        viewHolder.comments.setText(Integer.toString(element.comments));
    }

    private class IssueViewHolder extends PaginatingListViewHolder<Issue> {

        public AppCompatTextView date;
        public AppCompatTextView title;
        public AppCompatTextView comments;

        public IssueViewHolder(View itemView) {
            super(itemView);
            title = (AppCompatTextView) itemView.findViewById(R.id.issue_list_element_title);
            date = (AppCompatTextView) itemView.findViewById(R.id.issue_list_element_date);
            comments = (AppCompatTextView) itemView.findViewById(R.id.issue_list_element_comment);
        }
    }
}
