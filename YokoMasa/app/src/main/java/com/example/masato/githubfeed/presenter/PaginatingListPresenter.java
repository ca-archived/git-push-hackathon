package com.example.masato.githubfeed.presenter;

import android.os.Parcelable;
import android.util.Log;

import com.example.masato.githubfeed.githubapi.GitHubApiCallback;
import com.example.masato.githubfeed.view.PaginatingListView;
import static com.example.masato.githubfeed.view.PaginatingListView.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/01/29.
 */

public abstract class PaginatingListPresenter<T extends Parcelable> {

    private PaginatingListView view;
    private ArrayList<T> elementList = new ArrayList<>();
    private int currentPage = 1;
    private int notificationThreshold;
    private boolean feedMaxedOut = false;
    private boolean refreshing = false;
    private boolean fetching = false;

    public int getCurrentPage() {
        return currentPage;
    }

    public ArrayList<T> getElementList() {
        return elementList;
    }

    public T getItem(int position) {
        return elementList.get(position);
    }

    public int onGetItemCount() {
        return getItemCount();
    }

    public int onGetItemViewType(int position) {
        if (feedMaxedOut) {
            if (elementList.size() == 0) {
                return NOTHING_TO_SHOW_VIEW;
            }
            return ELEMENT_VIEW;
        }
        if (position == getItemCount() - 1) {
            return LOADING_VIEW;
        }
        return ELEMENT_VIEW;
    }

    public abstract void onElementClicked(T element);

    protected void onFetchedElements(List<T> elements, boolean fetchSucceeded) {
        if (fetchSucceeded) {
            addElements(elements);
        } else {
            view.stopRefreshing();
            refreshing = false;
            fetching = false;
        }
    }

    public void addElements(List<T> elements) {
        if (refreshing) {
            this.elementList.clear();
            this.elementList.addAll(elements);
            view.stopRefreshing();
            refreshing = false;
        } else {
            if (elements.size() == 0) {
                feedMaxedOut = true;
            } else {
                this.elementList.addAll(elements);
            }
        }
        view.updateAdapter();
        fetching = false;
    }

    public void refresh() {
        if (refreshing) {
            return;
        }
        currentPage = 1;
        onFetchElement(1);
        refreshing = true;
        fetching = true;
    }

    public void fetchElementIfNeeded(int position) {
        if (fetching) {
            return;
        }
        int remaining = getItemCount() - position;
        if (remaining < notificationThreshold && !feedMaxedOut) {
            fetching = true;
            onFetchElement(currentPage + 1);
            currentPage++;
        }
    }

    protected abstract void onFetchElement(int page);

    private int getItemCount() {
        if (feedMaxedOut) {
            if (elementList.size() == 0) {
                return 1;
            }
            return elementList.size();
        }
        return elementList.size() + 1;
    }

    public PaginatingListPresenter(PaginatingListView view, int notificationThreshold, ArrayList<T> elements, int currentPage) {
        this.view = view;
        this.notificationThreshold = notificationThreshold;
        this.elementList = elements;
        this.currentPage = currentPage;
    }

    public PaginatingListPresenter(PaginatingListView view, int notificationThreshold) {
        this.view = view;
        this.notificationThreshold = notificationThreshold;
    }
}
