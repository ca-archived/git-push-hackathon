package com.example.masato.githubfeed.view.adapter;

import android.animation.ObjectAnimator;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.presenter.FeedListPresenter;
import com.example.masato.githubfeed.view.FeedEntryView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Masato on 2018/01/19.
 */

public class FeedRecyclerViewAdapter extends RecyclerView.Adapter {

    private FeedListPresenter presenter;
    private LayoutInflater inflater;

    @Override
    public int getItemViewType(int position) {
        return presenter.onGetItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        if (viewType == FeedListPresenter.FEED_ENTRY_VIEW) {
            view = inflater.inflate(R.layout.feed_entry, parent, false);
            return new FeedEntryViewViewHolder(view);
        } else if (viewType == FeedListPresenter.LOADING_VIEW){
            view = inflater.inflate(R.layout.feed_loading, parent, false);
            return new LoadingViewHolder(view);
        } else if (viewType == FeedListPresenter.NOTHING_TO_SHOW_VIEW) {
            view = inflater.inflate(R.layout.feed_nothing_to_show, parent, false);
            return new NothingToShowViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == FeedListPresenter.FEED_ENTRY_VIEW) {
            FeedEntryView feedEntryView = (FeedEntryView) holder;
            presenter.onBindFeedEntryView(feedEntryView, position);
        }
    }

    @Override
    public int getItemCount() {
        return presenter.onGetItemCount();
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        super.onViewRecycled(holder);
        if (holder instanceof FeedEntryViewViewHolder) {
            FeedEntryViewViewHolder viewHolder = (FeedEntryViewViewHolder) holder;
            viewHolder.thumbnail.setImageBitmap(null);
        }
    }

    public FeedRecyclerViewAdapter(FeedListPresenter presenter, LayoutInflater inflater) {
        this.inflater = inflater;
        this.presenter = presenter;
    }

    private class FeedEntryViewViewHolder extends RecyclerView.ViewHolder implements FeedEntryView{

        AppCompatTextView title;
        CircleImageView thumbnail;

        @Override
        public void setTitle(String title) {
            this.title.setText(title);
        }

        @Override
        public void setThumbnail(Bitmap bitmap) {
            this.thumbnail.setImageBitmap(bitmap);
        }

        FeedEntryViewViewHolder(View itemView) {
            super(itemView);
            title = (AppCompatTextView) itemView.findViewById(R.id.feed_entry_title);
            thumbnail = (CircleImageView) itemView.findViewById(R.id.feed_entry_image);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class NothingToShowViewHolder extends RecyclerView.ViewHolder {
        NothingToShowViewHolder(View itemView) {
            super(itemView);
        }
    }

}
