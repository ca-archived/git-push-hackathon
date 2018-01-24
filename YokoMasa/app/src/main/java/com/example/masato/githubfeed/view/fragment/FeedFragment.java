package com.example.masato.githubfeed.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.presenter.FeedListPresenter;
import com.example.masato.githubfeed.view.FeedListView;
import com.example.masato.githubfeed.view.adapter.FeedRecyclerViewAdapter;

/**
 * Created by Masato on 2018/01/20.
 */

public class FeedFragment extends Fragment implements FeedListView {

    private FeedListPresenter presenter;
    private RecyclerView feedRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FeedRecyclerViewAdapter adapter;
    private boolean initialized = false;

    public void disableRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public String getTitle() {
        Bundle bundle = getArguments();
        return bundle.getString("title");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        init();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        init();
    }

    private void init() {
        if (!initialized) {
            Bundle bundle = getArguments();
            presenter = new FeedListPresenter(this, bundle.getString("url"));
            adapter = new FeedRecyclerViewAdapter(presenter, getLayoutInflater());
            initialized = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        findViews(view);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();;
    }

    @Override
    public void showToast(int stringId) {
        Toast.makeText(getContext(), stringId, Toast.LENGTH_LONG).show();;
    }

    @Override
    public void stopRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void updateAdapter() {
        adapter.notifyDataSetChanged();
    }

    private void findViews(View view) {
        feedRecyclerView = (RecyclerView) view.findViewById(R.id.feed_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        feedRecyclerView.setLayoutManager(layoutManager);
        feedRecyclerView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.feed_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.onRefresh();
            }
        });
    }

}
