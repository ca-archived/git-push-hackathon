package com.example.masato.githubfeed.view.adapter.viewholders;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.diff.DiffFile;

/**
 * Created by Masato on 2018/02/19.
 */

public class DiffFileHeaderViewHolder extends RecyclerView.ViewHolder {

    private AppCompatTextView title;
    private AppCompatTextView addition;
    private AppCompatTextView deletion;

    public void bindDiffFile(DiffFile diffFile) {
        title.setText(diffFile.fileName);
        addition.setText(Integer.toString(diffFile.additions));
        deletion.setText(Integer.toString(diffFile.deletions));
    }

    public DiffFileHeaderViewHolder(View itemView) {
        super(itemView);
        this.title = (AppCompatTextView) itemView.findViewById(R.id.diff_header_title);
        this.addition = (AppCompatTextView) itemView.findViewById(R.id.diff_header_addition);
        this.deletion = (AppCompatTextView) itemView.findViewById(R.id.diff_header_deletion);
    }
}
