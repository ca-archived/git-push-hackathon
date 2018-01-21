package com.example.masato.githubfeed.view.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.FeedEntry;
import com.example.masato.githubfeed.presenter.FeedPresenter;
import com.example.masato.githubfeed.view.FeedView;
import com.example.masato.githubfeed.view.adapter.FeedRecyclerViewAdapter;

import java.util.List;

/**
 * Created by Masato on 2018/01/20.
 */

public class FeedFragment extends Fragment implements FeedRecyclerViewAdapter.FetchRequestListener{

    private RecyclerView feedRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private FeedRecyclerViewAdapter adapter;
    private FeedFetchRequestListener listener;


    public void addFeedEntries(List<FeedEntry> feedEntries) {
        adapter.addFeedEntries(feedEntries);
    }

    public void disableRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public String getTitle() {
        Bundle bundle = getArguments();
        return bundle.getString("title");
    }

    public int getFragmentNumber() {
        Bundle bundle = getArguments();
        return bundle.getInt("number");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        listener = (FeedFetchRequestListener) context;
        setListenerToAdapter();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        listener = (FeedFetchRequestListener) activity;
        setListenerToAdapter();
    }

    private void setListenerToAdapter() {
        if (adapter == null) {
            Log.i("gh_info", "refresh " + Integer.toString(getFragmentNumber()));
            Bundle bundle = getArguments();
            adapter = new FeedRecyclerViewAdapter(bundle.getString("url"), getLayoutInflater(), this);
            adapter.refreshEntries();
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
                //Log.i("gh_info", "pulled down");
                adapter.refreshEntries();
            }
        });
    }

    @Override
    public void onFeedFetchRequested(String url, int page) {
        //Log.i("gh_info", "fragment onFeedFetchRequested", new Throwable());
        Log.i("gh_info", Thread.currentThread().getName());
        listener.onFeedFetchRequest(getFragmentNumber(), url, page);
    }

    public interface FeedFetchRequestListener {
        public void onFeedFetchRequest(int fragmentNumber, String url, int page);
    }

}
