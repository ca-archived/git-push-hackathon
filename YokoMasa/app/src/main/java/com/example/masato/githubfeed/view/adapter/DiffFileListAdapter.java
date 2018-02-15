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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/02/06.
 */

public class DiffFileListAdapter extends RecyclerView.Adapter {

    private static final int CODE_LINE_VIEW = 10;
    private static final int HEADER_VIEW = 17;

    private List<DiffFile> diffFiles = new ArrayList<>();
    private List<ViewInfo> viewInfos = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    public void setDiffFiles(List<DiffFile> diffFiles) {
        this.diffFiles = diffFiles;
        setUpViewInfo();
    }

    private void setUpViewInfo() {
        viewInfos.clear();
        for (int i = 0;i<diffFiles.size();i++) {
            ViewInfo viewInfo = new ViewInfo();
            viewInfo.viewType = HEADER_VIEW;
            viewInfo.fileIndex = i;
            viewInfos.add(viewInfo);
            List<DiffCodeLine> codeLines = diffFiles.get(i).codeLines;

            for (int row = 0;row<codeLines.size();row++) {
                viewInfo = new ViewInfo();
                viewInfo.viewType = CODE_LINE_VIEW;
                viewInfo.fileIndex = i;
                viewInfo.codeLineIndex = row;
                viewInfos.add(viewInfo);
            }
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        return viewInfos.get(position).viewType;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case HEADER_VIEW:
                view = inflater.inflate(R.layout.diff_header, parent, false);
                return new HeaderViewHolder(view);
            case CODE_LINE_VIEW:
                view = inflater.inflate(R.layout.diff_code_line, parent, false);
                return new CodeLineViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewInfo viewInfo = viewInfos.get(position);
        DiffFile diffFile = diffFiles.get(viewInfo.fileIndex);
        switch (viewInfo.viewType) {
            case HEADER_VIEW:
                HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
                headerViewHolder.bindDiffFile(diffFile);
                break;
            case CODE_LINE_VIEW:
                CodeLineViewHolder codeLineViewHolder = (CodeLineViewHolder) holder;
                DiffCodeLine codeLine = diffFile.codeLines.get(viewInfo.codeLineIndex);
                codeLineViewHolder.bindCodeLine(codeLine);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return viewInfos.size();
    }

    public DiffFileListAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class ViewInfo {
        int viewType;
        int fileIndex;
        int codeLineIndex;
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView title;
        AppCompatTextView addition;
        AppCompatTextView deletion;

        void bindDiffFile(DiffFile diffFile) {
            title.setText(diffFile.fileName);
            addition.setText(Integer.toString(diffFile.additions));
            deletion.setText(Integer.toString(diffFile.deletions));
        }

        HeaderViewHolder(View itemView) {
            super(itemView);
            this.title = (AppCompatTextView) itemView.findViewById(R.id.diff_header_title);
            this.addition = (AppCompatTextView) itemView.findViewById(R.id.diff_header_addition);
            this.deletion = (AppCompatTextView) itemView.findViewById(R.id.diff_header_deletion);
        }
    }

    private class CodeLineViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView code;

        void bindCodeLine(DiffCodeLine codeLine) {
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

        CodeLineViewHolder(View itemView) {
            super(itemView);
            this.code = (AppCompatTextView) itemView.findViewById(R.id.diff_code_line);
        }
    }
}
