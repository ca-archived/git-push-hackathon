package com.example.masato.githubfeed.view.fragment;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.githubapi.GitHubUrls;
import com.example.masato.githubfeed.model.BaseModel;
import com.example.masato.githubfeed.model.Comment;
import com.example.masato.githubfeed.presenter.CommentListPresenter;
import com.example.masato.githubfeed.presenter.PaginatingListPresenter;
import com.example.masato.githubfeed.util.DateUtil;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Masato on 2018/02/03.
 */

public class CommentListFragment extends PaginatingListFragment {

    @Override
    protected PaginatingListPresenter onCreatePresenter() {
        return new CommentListPresenter(this, getArguments().getString("url"));
    }

    @Override
    protected PaginatingListViewHolder onCreatePaginatingViewHolder(ViewGroup parent, int viewType) {
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
    protected void onBindViewHolder(PaginatingListViewHolder holder, BaseModel element, int viewType) {
        CommentViewHolder commentViewHolder = (CommentViewHolder) holder;
        Comment comment = (Comment) element;
        commentViewHolder.bindComment(comment);
    }

    private class CommentViewHolder extends PaginatingListViewHolder {

        AppCompatTextView authorName;
        AppCompatTextView date;
        CircleImageView image;
        WebView commentBody;
        private Comment comment;

        void bindComment(Comment comment) {
            this.comment = comment;
            authorName.setText(comment.author.name);
            date.setText(DateUtil.getReadableDateForFeed(comment.createdAt, getContext()));
            commentBody.loadDataWithBaseURL(GitHubUrls.BASE_HTML_URL, comment.bodyHtml, "text/html", "utf-8", null);
            Picasso.with(getContext()).load(comment.author.iconUrl).into(image);
        }

        CommentViewHolder(View itemView) {
            super(itemView);
            authorName = (AppCompatTextView) itemView.findViewById(R.id.comment_author_name);
            date = (AppCompatTextView) itemView.findViewById(R.id.comment_date);
            image = (CircleImageView) itemView.findViewById(R.id.comment_image);
            commentBody = (WebView) itemView.findViewById(R.id.comment_body);
        }
    }
}
