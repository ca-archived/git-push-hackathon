package com.example.masato.githubfeed.view.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.presenter.FeedListPresenter;
import com.example.masato.githubfeed.util.DateUtil;
import com.example.masato.githubfeed.view.FeedEntryView;

import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Masato on 2018/01/19.
 */

public class FeedRecyclerViewAdapter extends RecyclerView.Adapter {

    private FeedListPresenter presenter;
    private LayoutInflater inflater;
    private Context context;

    @Override
    public int getItemViewType(int position) {
        return presenter.onGetItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == FeedListPresenter.FEED_ENTRY_VIEW) {
            view = inflater.inflate(R.layout.feed_entry, parent, false);
            viewHolder =  new FeedEntryViewViewHolder(view, context);
        } else if (viewType == FeedListPresenter.LOADING_VIEW){
            view = inflater.inflate(R.layout.feed_loading, parent, false);
            viewHolder =  new LoadingViewHolder(view);
        } else if (viewType == FeedListPresenter.NOTHING_TO_SHOW_VIEW) {
            view = inflater.inflate(R.layout.feed_nothing_to_show, parent, false);
            viewHolder =  new NothingToShowViewHolder(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == FeedListPresenter.FEED_ENTRY_VIEW) {
            FeedEntryView feedEntryView = (FeedEntryView) holder;
            presenter.onBindFeedEntryView(feedEntryView, position);
            feedEntryView.setOnClickListener(new OnClickListener() {
                @Override
                public void OnClick(String url) {
                    presenter.onItemClicked(url);
                }
            });
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

    public FeedRecyclerViewAdapter(FeedListPresenter presenter, LayoutInflater inflater, Context context) {
        this.inflater = inflater;
        this.presenter = presenter;
        this.context = context;
    }

    private class FeedEntryViewViewHolder extends RecyclerView.ViewHolder implements FeedEntryView{

        AppCompatTextView date;
        AppCompatTextView title;
        CircleImageView thumbnail;
        OnClickListener listener;
        String repoUrl;
        Context context;

        @Override
        public void setRepoUrl(String url) {
            this.repoUrl = url;
        }

        @Override
        public void setOnClickListener(OnClickListener listener) {
            this.listener = listener;
        }

        @Override
        public void setDate(Date date) {
            this.date.setText(DateUtil.getReadableDateForFeed(date, context));
        }

        @Override
        public void setTitle(String title) {
            this.title.setText(title);
        }

        @Override
        public void setThumbnail(Bitmap bitmap) {
            this.thumbnail.setImageBitmap(bitmap);
        }

        FeedEntryViewViewHolder(View itemView, Context context) {
            super(itemView);
            date = (AppCompatTextView) itemView.findViewById(R.id.feed_entry_date);
            title = (AppCompatTextView) itemView.findViewById(R.id.feed_entry_title);
            thumbnail = (CircleImageView) itemView.findViewById(R.id.feed_entry_image);
            this.context = context;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.OnClick(repoUrl);
                    }
                }
            });
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

    public interface OnClickListener {
        public void OnClick(String url);
    }

}
