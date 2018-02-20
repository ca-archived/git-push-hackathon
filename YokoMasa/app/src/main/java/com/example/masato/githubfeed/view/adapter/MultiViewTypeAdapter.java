package com.example.masato.githubfeed.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/02/19.
 */

public abstract class MultiViewTypeAdapter extends RecyclerView.Adapter {

    private List<ViewInfo> viewInfoList = new ArrayList<>();

    public void addViewInfo(Object info, int viewType) {
        viewInfoList.add(new ViewInfo(info, viewType));
    }

    public void addViewInfo(Object info, int viewType, int position) {
        viewInfoList.add(position, new ViewInfo(info, viewType));
    }

    public void clearViewInfo() {
        viewInfoList.clear();
    }

    @Override
    final public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindViewHolder(holder, viewInfoList.get(position));
    }

    protected abstract void onBindViewHolder(RecyclerView.ViewHolder holder, ViewInfo viewInfo);

    @Override
    public int getItemCount() {
        return viewInfoList.size();
    }

    @Override
    final public int getItemViewType(int position) {
        return viewInfoList.get(position).viewType;
    }

    class ViewInfo {
        public int viewType;
        public Object info;

        ViewInfo(Object info, int viewType) {
            this.info = info;
            this.viewType = viewType;
        }
    }
}
