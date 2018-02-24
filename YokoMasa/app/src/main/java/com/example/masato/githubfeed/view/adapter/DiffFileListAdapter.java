package com.example.masato.githubfeed.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.diff.DiffCodeLine;
import com.example.masato.githubfeed.model.diff.DiffFile;
import com.example.masato.githubfeed.view.adapter.viewholders.DiffCodeLineViewHolder;
import com.example.masato.githubfeed.view.adapter.viewholders.DiffFileHeaderViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/02/06.
 */

public class DiffFileListAdapter extends MultiViewTypeAdapter {

    private static final int CODE_LINE_VIEW = 10;
    private static final int HEADER_VIEW = 17;

    private Context context;
    private LayoutInflater inflater;

    public void setDiffFiles(List<DiffFile> diffFiles) {
        clearViewInfo();
        for (DiffFile diffFile : diffFiles) {
            addViewInfo(diffFile, HEADER_VIEW);
            for (DiffCodeLine diffCodeLine : diffFile.codeLines) {
                addViewInfo(diffCodeLine, CODE_LINE_VIEW);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    protected void onBindViewHolder(RecyclerView.ViewHolder holder, MultiViewTypeAdapter.ViewInfo viewInfo) {
        switch (viewInfo.viewType) {
            case HEADER_VIEW:
                DiffFileHeaderViewHolder headerViewHolder = (DiffFileHeaderViewHolder) holder;
                DiffFile diffFile = (DiffFile) viewInfo.info;
                headerViewHolder.bindDiffFile(diffFile);
                break;
            case CODE_LINE_VIEW:
                DiffCodeLineViewHolder codeLineViewHolder = (DiffCodeLineViewHolder) holder;
                DiffCodeLine diffCodeLine = (DiffCodeLine) viewInfo.info;
                codeLineViewHolder.bindCodeLine(diffCodeLine);
                break;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case HEADER_VIEW:
                view = inflater.inflate(R.layout.diff_header, parent, false);
                return new DiffFileHeaderViewHolder(view);
            case CODE_LINE_VIEW:
                view = inflater.inflate(R.layout.diff_code_line, parent, false);
                return new DiffCodeLineViewHolder(view, context);
            default:
                return null;
        }
    }

    public DiffFileListAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

}
