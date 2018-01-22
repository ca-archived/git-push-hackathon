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

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.view.adapter.FeedRecyclerViewAdapter;

/**
 * Created by Masato on 2018/01/20.
 */

public class FeedFragment extends Fragment implements FeedRecyclerViewAdapter.OnFeedRefreshedListener {

    private RecyclerView feedRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FeedRecyclerViewAdapter adapter;

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
        setListenerToAdapter();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        setListenerToAdapter();
    }

    private void setListenerToAdapter() {
        if (adapter == null) {
            Bundle bundle = getArguments();
            adapter = new FeedRecyclerViewAdapter(bundle.getString("url"), getLayoutInflater());
            adapter.setOnFeedRefreshedListener(this);
            adapter.refresh();
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
    public void onRefreshed() {
        swipeRefreshLayout.setRefreshing(false);
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
                adapter.refresh();
            }
        });
    }

}
