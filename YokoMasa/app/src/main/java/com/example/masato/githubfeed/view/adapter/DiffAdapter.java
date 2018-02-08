package com.example.masato.githubfeed.view.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.diff.DiffCodeLine;
import com.example.masato.githubfeed.model.diff.DiffFile;

/**
 * Created by Masato on 2018/02/06.
 */

public class DiffAdapter extends RecyclerView.Adapter {

    private DiffFile diffFile;
    private Context context;
    private LayoutInflater inflater;

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.diff_code_line, parent, false);
        return new DiffViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DiffCodeLine codeLine = diffFile.codeLines.get(position);
        DiffViewHolder diffViewHolder = (DiffViewHolder) holder;
        diffViewHolder.bindCodeLine(codeLine);
    }

    @Override
    public int getItemCount() {
        return diffFile.codeLines.size();
    }

    public DiffAdapter(DiffFile diffFile, Context context) {
        this.diffFile = diffFile;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class DiffViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView codeLineView;

        public void bindCodeLine(DiffCodeLine codeLine) {
            codeLineView.setText(codeLine.code);
            if (codeLine.status == DiffCodeLine.REMOVED) {
                codeLineView.setBackgroundColor(context.getResources().getColor(R.color.diff_red));
            } else if (codeLine.status == DiffCodeLine.ADDED) {
                codeLineView.setBackgroundColor(context.getResources().getColor(R.color.diff_green));
            } else if (codeLine.status == DiffCodeLine.CHANGE_LINES) {
                codeLineView.setBackgroundColor(context.getResources().getColor(R.color.diff_blue));
            }
        }

        public DiffViewHolder(View itemView) {
            super(itemView);
            this.codeLineView = (AppCompatTextView) itemView.findViewById(R.id.diff_code_line);
        }
    }
}
