package com.example.masato.githubfeed.view.fragment;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.masato.githubfeed.R;
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

public abstract class PaginatingListFragment<T extends Parcelable> extends BaseFragment
        implements PaginatingListView, PaginatingListAdapter.PaginatingListListAdapterListener {

    private PaginatingListPresenter<T> presenter;
    private RecyclerView elementRecyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private PaginatingListAdapter adapter;
    private int scrollX, scrollY;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            presenter = onCreatePresenter();
            presenter.refresh();
        } else {
            scrollX = savedInstanceState.getInt("scroll_x");
            scrollY = savedInstanceState.getInt("scroll_y");
            int currentPage = savedInstanceState.getInt("current_page");
            ArrayList<T> elements = savedInstanceState.getParcelableArrayList("elements");
            presenter = onCreatePresenter();
            presenter.setCurrentPage(currentPage);
            presenter.setElementList(elements);
        }
    }

    protected abstract PaginatingListPresenter<T> onCreatePresenter();

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
    final public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent) {
        return onCreatePaginatingViewHolder(parent);
    }

    protected abstract PaginatingListViewHolder<T> onCreatePaginatingViewHolder(ViewGroup parent);

    @Override
    final public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        T element = presenter.getItem(position);
        PaginatingListViewHolder viewHolder = (PaginatingListViewHolder) holder;
        onBindViewHolder(viewHolder, element);
        viewHolder.notifyWhenClicked(element, new OnElementClickListener<T>() {
            @Override
            public void onClick(T element) {
                presenter.onElementClicked(element);
            }
        });
    }

    protected abstract void onBindViewHolder(PaginatingListViewHolder holder, T element);

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

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("scroll_x", elementRecyclerView.getScrollX());
        outState.putInt("scroll_y", elementRecyclerView.getScrollY());
        outState.putInt("current_page", presenter.getCurrentPage());
        outState.putParcelableArrayList("elements", presenter.getElementList());
    }

    private void initViews(View view) {
        adapter = new PaginatingListAdapter(getLayoutInflater(), this);
        elementRecyclerView = (RecyclerView) view.findViewById(R.id.feed_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        elementRecyclerView.setLayoutManager(layoutManager);
        elementRecyclerView.setAdapter(adapter);
        elementRecyclerView.setScrollX(scrollX);
        elementRecyclerView.setScrollY(scrollY);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.feed_swipe_refresh_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refresh();
            }
        });
    }

    abstract class PaginatingListViewHolder<T extends Parcelable> extends RecyclerView.ViewHolder {

        View itemView;

        void notifyWhenClicked(final T element, final OnElementClickListener<T> listener) {
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

    private interface OnElementClickListener<T> {
        void onClick(T element);
    }

}
