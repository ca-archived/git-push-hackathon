package com.example.masato.githubfeed.view.fragment;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.BaseModel;
import com.example.masato.githubfeed.model.PullRequest;
import com.example.masato.githubfeed.model.Repository;
import com.example.masato.githubfeed.navigator.Navigator;
import com.example.masato.githubfeed.presenter.PaginatingListPresenter;
import com.example.masato.githubfeed.presenter.PullRequestListPresenter;
import com.example.masato.githubfeed.util.DateUtil;
import com.example.masato.githubfeed.view.PullRequestListView;

/**
 * Created by Masato on 2018/02/08.
 */

public class PullRequestListFragment extends PaginatingListFragment implements PullRequestListView {

    @Override
    public void showPullRequest(PullRequest pr) {
        Navigator.navigateToPullRequestView(getContext(), pr);
    }

    @Override
    protected PaginatingListPresenter onCreatePresenter() {
        Repository repository = getArguments().getParcelable("repository");
        return new PullRequestListPresenter(this, repository);
    }

    @Override
    protected PaginatingListViewHolder onCreatePaginatingViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.pull_request_list_element, parent, false);
        return new PullRequestViewHolder(view);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {

    }

    @Override
    protected void onBindViewHolder(PaginatingListViewHolder holder, BaseModel element, int viewType) {
        PullRequest pr = (PullRequest) element;
        PullRequestViewHolder viewHolder = (PullRequestViewHolder) holder;
        viewHolder.bindPullRequest(pr);
    }

    private class PullRequestViewHolder extends PaginatingListViewHolder {

        AppCompatTextView date;
        AppCompatTextView number;
        AppCompatTextView title;

        void bindPullRequest(PullRequest pr) {
            date.setText(DateUtil.getReadableDateForFeed(pr.createdAt, getContext()));
            number.setText(getString(R.string.hash_tag) + pr.number);
            title.setText(pr.name);
        }

        public PullRequestViewHolder(View itemView) {
            super(itemView);
            date = (AppCompatTextView) itemView.findViewById(R.id.pr_list_element_date);
            number = (AppCompatTextView) itemView.findViewById(R.id.pr_list_element_number);
            title = (AppCompatTextView) itemView.findViewById(R.id.pr_list_element_title);
        }
    }
}
