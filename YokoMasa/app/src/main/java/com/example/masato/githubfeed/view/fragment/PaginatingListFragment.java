package com.example.masato.githubfeed.view.fragment;

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
import com.example.masato.githubfeed.model.BaseModel;
import com.example.masato.githubfeed.presenter.PaginatingListPresenter;
import com.example.masato.githubfeed.view.PaginatingListView;
import com.example.masato.githubfeed.view.adapter.PaginatingListAdapter;

import java.util.ArrayList;

/**
 *
 * Created by Masato on 2018/01/29.
 *
 * このフラグメントはツイッターのように、あるスクロール地点まで行くと次のページのものを取ってくるという機能をもつ
 * フラグメントです。画面を下に引っ張って更新する機能も備えます。
 * このクラスのサブクラスはビューホルダーに関する処理を実装する必要があります。
 * また、ビジネスロジックの処理はonCreatePresenter()で渡されるPaginatingListPresenterによって行われます。
 *
 */

public abstract class PaginatingListFragment extends BaseFragment
        implements PaginatingListView, PaginatingListAdapter.PaginatingListListAdapterListener {

    private PaginatingListPresenter presenter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PaginatingListAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        presenter = onCreatePresenter();
        presenter.refresh();

    }

    protected abstract PaginatingListPresenter onCreatePresenter();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
    }

    @Override
    public int onGetItemViewType(int position) {
        return presenter.onGetItemViewType(position);
    }

    @Override
    final public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreatePaginatingViewHolder(parent, viewType);
    }

    @Override
    public RecyclerView.ViewHolder onCreateLoadingViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public RecyclerView.ViewHolder onCreateNothingToShowViewHolder(View parent) {
        return null;
    }

    protected abstract PaginatingListViewHolder onCreatePaginatingViewHolder(ViewGroup parent, int viewType);

    @Override
    final public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        BaseModel element = presenter.getItem(position);
        int viewType = presenter.onGetItemViewType(position);
        PaginatingListViewHolder viewHolder = (PaginatingListViewHolder) holder;
        onBindViewHolder(viewHolder, element, viewType);
        viewHolder.notifyWhenClicked(element, new OnElementClickListener() {
            @Override
            public void onClick(BaseModel element) {
                presenter.onElementClicked(element, viewType);
            }
        });
    }

    protected abstract void onBindViewHolder(PaginatingListViewHolder holder, BaseModel element, int viewType);

    @Override
    final public void onFetchIfNeeded(int position) {
        presenter.fetchElementIfNeeded(position);
    }

    @Override
    public int onGetItemCount() {
        return presenter.onGetItemCount();
    }

    @Override
    public void updateAdapter() {
        adapter.notifyDataSetChanged();
    }

    @Override
    public void stopRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
    }

    public void setSwipeRefreshEnabled(boolean enabled) {
        swipeRefreshLayout.setEnabled(enabled);
    }

    private void initViews(View view) {
        adapter = new PaginatingListAdapter(getLayoutInflater(), this);
        RecyclerView elementRecyclerView = (RecyclerView) view.findViewById(R.id.feed_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        elementRecyclerView.setLayoutManager(layoutManager);
        elementRecyclerView.setAdapter(adapter);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.feed_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
    }

    abstract class PaginatingListViewHolder extends RecyclerView.ViewHolder {

        View itemView;

        void notifyWhenClicked(final BaseModel element, final OnElementClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onClick(element);
                }
            });
        }

        PaginatingListViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
        }
    }

    private interface OnElementClickListener {
        void onClick(BaseModel element);
    }

}
