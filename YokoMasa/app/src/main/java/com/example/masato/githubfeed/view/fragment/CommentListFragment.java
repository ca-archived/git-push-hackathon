package com.example.masato.githubfeed.view.fragment;

import android.graphics.Bitmap;
import android.os.Parcelable;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.Comment;
import com.example.masato.githubfeed.model.Profile;
import com.example.masato.githubfeed.presenter.CommentListPresenter;
import com.example.masato.githubfeed.presenter.ImageLoadablePresenter;
import com.example.masato.githubfeed.presenter.PaginatingListPresenter;
import com.example.masato.githubfeed.util.DateUtil;
import com.example.masato.githubfeed.view.ImageLoadableView;
import com.example.masato.githubfeed.view.CommentListView;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Masato on 2018/02/03.
 */

public class CommentListFragment extends PaginatingListFragment<Comment> {

    @Override
    protected PaginatingListPresenter<Comment> onCreatePresenter() {
        return new CommentListPresenter(this, getArguments().getString("url"));
    }

    @Override
    protected PaginatingListViewHolder<Comment> onCreatePaginatingViewHolder(ViewGroup parent) {
        View view = getLayoutInflater().inflate(R.layout.comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onViewRecycled(RecyclerView.ViewHolder holder) {
        if (holder instanceof CommentViewHolder) {
            CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
            commentViewHolder.image.setImageBitmap(null);
        }
    }

    @Override
    protected void onBindViewHolder(PaginatingListViewHolder holder, Comment element) {
        CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
        commentViewHolder.bindComment(element);
    }

    private class CommentViewHolder extends PaginatingListViewHolder<Comment> implements ImageLoadableView {

        AppCompatTextView authorName;
        AppCompatTextView date;
        CircleImageView image;
        WebView commentBody;
        private Comment comment;
        private ImageLoadablePresenter presenter;

        void bindComment(Comment comment) {
            this.comment = comment;
            authorName.setText(comment.author.name);
            date.setText(DateUtil.getReadableDateForFeed(comment.createdAt, getContext()));
            commentBody.loadDataWithBaseURL("https://github.com", comment.bodyHtml, "text/html", "utf-8", null);
            setProfileImage(comment.author);
        }

        private void setProfileImage(Profile profile) {
            if (profile.icon == null) {
                presenter.onFetchImage(profile.iconUrl);
            } else {
                image.setImageBitmap(profile.icon);
            }
        }

        @Override
        public void showImage(Bitmap bitmap) {
            image.setImageBitmap(bitmap);
            comment.author.icon = bitmap;
        }

        CommentViewHolder(View itemView) {
            super(itemView);
            authorName = (AppCompatTextView) itemView.findViewById(R.id.comment_author_name);
            date = (AppCompatTextView) itemView.findViewById(R.id.comment_date);
            image = (CircleImageView) itemView.findViewById(R.id.comment_image);
            commentBody = (WebView) itemView.findViewById(R.id.comment_body);
            presenter = new ImageLoadablePresenter(this);
        }
    }
}
