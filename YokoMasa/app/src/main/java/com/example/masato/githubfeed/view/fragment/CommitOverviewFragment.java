package com.example.masato.githubfeed.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.model.Commit;
import com.example.masato.githubfeed.model.diff.DiffFile;
import com.example.masato.githubfeed.presenter.CommitOverviewPresenter;
import com.example.masato.githubfeed.view.CommitOverviewView;
import com.example.masato.githubfeed.view.adapter.CommitOverviewAdapter;

import java.util.List;

/**
 * Created by Masato on 2018/02/19.
 */

public class CommitOverviewFragment extends BaseFragment implements CommitOverviewView {

    private CommitOverviewAdapter adapter;
    private CommitOverviewPresenter presenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        Commit commit = getArguments().getParcelable("commit");
        this.adapter = new CommitOverviewAdapter(commit, getContext());
        presenter = new CommitOverviewPresenter(this, commit);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.general_recycler_view_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.general_recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void showLoadingView() {
        showLoadingFragment(R.id.general_recyclerView_mother);
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
        showErrorFragment(R.id.general_recyclerView_mother, failure, message);
    }

    @Override
    public void hideErrorView() {
        removeErrorFragment();
    }

    @Override
    public void showDiffFiles(List<DiffFile> diffFiles) {
        adapter.setDiffFiles(diffFiles);
    }
}
