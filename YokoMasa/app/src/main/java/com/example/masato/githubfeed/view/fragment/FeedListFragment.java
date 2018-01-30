package com.example.masato.githubfeed.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.FeedEntry;
import com.example.masato.githubfeed.presenter.FeedEntryPresenter;
import com.example.masato.githubfeed.presenter.PaginatingListPresenter;
import com.example.masato.githubfeed.presenter.FeedListPresenter;
import com.example.masato.githubfeed.util.DateUtil;
import com.example.masato.githubfeed.view.FeedEntryView;
import com.example.masato.githubfeed.view.FeedListView;
import com.example.masato.githubfeed.view.activity.RepoActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Masato on 2018/01/29.
 */

public class FeedListFragment extends PaginatingListFragment<FeedEntry> implements FeedListView {

    @Override
    protected PaginatingListPresenter<FeedEntry> onCreatePresenter() {
        String url = getArguments().getString("url");
        FeedListPresenter presenter =  new FeedListPresenter(this, url);
        presenter.setView(this);
        return presenter;
    }

    @Override
    protected PaginatingListViewHolder onCreatePaginatingViewHolder(ViewGroup parent) {
        View view = getLayoutInflater().inflate(R.layout.feed_entry, parent, false);
        return new FeedEntryViewViewHolder(view, getContext());
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (holder instanceof FeedEntryViewViewHolder) {
            FeedEntryViewViewHolder viewHolder = (FeedEntryViewViewHolder) holder;
            viewHolder.thumbnail.setImageBitmap(null);
        }
    }

    @Override
    protected void onBindViewHolder(PaginatingListViewHolder holder, FeedEntry element) {
        FeedEntryViewViewHolder viewHolder = (FeedEntryViewViewHolder) holder;
        viewHolder.bindFeedEntry(element);
    }

    @Override
    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showToast(int stringId) {
        Toast.makeText(getContext(), stringId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void startRepoView(String url) {
        Intent intent = new Intent(getContext(), RepoActivity.class);
        intent.putExtra("url", url);
        startActivity(intent);
    }

    private class FeedEntryViewViewHolder extends PaginatingListViewHolder<FeedEntry> implements FeedEntryView {

        private FeedEntryPresenter presenter;
        AppCompatTextView date;
        AppCompatTextView title;
        CircleImageView thumbnail;
        Context context;

        public void bindFeedEntry(FeedEntry feedEntry) {
            this.date.setText(DateUtil.getReadableDateForFeed(feedEntry.published, context));
            this.title.setText(feedEntry.title);
            if (feedEntry.isThumbnailSet()) {
                this.thumbnail.setImageBitmap(feedEntry.thumbnail);
            } else {
                presenter.fetchThumbnail(feedEntry);
            }
        }

        @Override
        public void setThumbnail(Bitmap bitmap) {
            this.thumbnail.setImageBitmap(bitmap);
        }

        FeedEntryViewViewHolder(View itemView, Context context) {
            super(itemView);
            presenter = new FeedEntryPresenter(this);
            date = (AppCompatTextView) itemView.findViewById(R.id.feed_entry_date);
            title = (AppCompatTextView) itemView.findViewById(R.id.feed_entry_title);
            thumbnail = (CircleImageView) itemView.findViewById(R.id.feed_entry_image);
            this.context = context;
        }
    }
}
