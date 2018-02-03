package com.example.masato.githubfeed.view.fragment;

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
import com.example.masato.githubfeed.view.IssueListView;

/**
 * Created by Masato on 2018/02/03.
 */

public class IssueListFragment extends PaginatingListFragment<Issue> implements IssueListView {

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
    }

    private class IssueViewHolder extends PaginatingListViewHolder<Issue> {

        public AppCompatTextView title;

        public IssueViewHolder(View itemView) {
            super(itemView);
            title = (AppCompatTextView) itemView.findViewById(R.id.issue_list_element_title);
        }
    }
}
