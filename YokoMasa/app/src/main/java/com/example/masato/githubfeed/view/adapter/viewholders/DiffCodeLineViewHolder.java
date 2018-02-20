package com.example.masato.githubfeed.view.adapter.viewholders;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.diff.DiffCodeLine;

/**
 * Created by Masato on 2018/02/19.
 */

public class DiffCodeLineViewHolder extends RecyclerView.ViewHolder {

    private AppCompatTextView code;
    private Context context;

    public void bindCodeLine(DiffCodeLine codeLine) {
        code.setText(codeLine.code);
        switch (codeLine.status) {
            case DiffCodeLine.ADDED:
                code.setBackgroundColor(context.getResources().getColor(R.color.diff_green));
                break;
            case DiffCodeLine.REMOVED:
                code.setBackgroundColor(context.getResources().getColor(R.color.diff_red));
                break;
            case DiffCodeLine.CHANGE_LINES:
                code.setBackgroundColor(context.getResources().getColor(R.color.diff_blue));
                break;
        }
    }

    public DiffCodeLineViewHolder(View itemView, Context context) {
        super(itemView);
        this.code = (AppCompatTextView) itemView.findViewById(R.id.diff_code_line);
        this.context = context;
    }
}
