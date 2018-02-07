package com.example.masato.githubfeed.view.fragment;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.BaseModel;
import com.example.masato.githubfeed.model.Commit;
import com.example.masato.githubfeed.presenter.CommitListPresenter;
import com.example.masato.githubfeed.presenter.PaginatingListPresenter;
import com.example.masato.githubfeed.util.DateUtil;

/**
 * Created by Masato on 2018/02/06.
 */

public class CommitListFragment extends PaginatingListFragment {

    @Override
    protected PaginatingListPresenter onCreatePresenter() {
        String url = getArguments().getString("url");
        return new CommitListPresenter(this, url);
    }

    @Override
    protected PaginatingListViewHolder onCreatePaginatingViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.commit_list_element, parent, false);
        return new CommitViewHolder(view);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {

    }

    @Override
    protected void onBindViewHolder(PaginatingListViewHolder holder, BaseModel element, int viewType) {
        CommitViewHolder commitViewHolder = (CommitViewHolder) holder;
        Commit commit = (Commit) element;
        commitViewHolder.bindCommit(commit);
    }

    private class CommitViewHolder extends PaginatingListViewHolder {

        AppCompatTextView date;
        AppCompatTextView comment;
        AppCompatTextView sha;

        void bindCommit(Commit commit) {
            date.setText(DateUtil.getReadableDateForFeed(commit.createdAt, getContext()));
            comment.setText(commit.comment);
            sha.setText(commit.sha.substring(0, 6));
        }

        public CommitViewHolder(View itemView) {
            super(itemView);
            date = (AppCompatTextView) itemView.findViewById(R.id.commit_list_element_date);
            comment = (AppCompatTextView) itemView.findViewById(R.id.commit_list_element_comment);
            sha = (AppCompatTextView) itemView.findViewById(R.id.commit_list_element_sha);
        }
    }
}
