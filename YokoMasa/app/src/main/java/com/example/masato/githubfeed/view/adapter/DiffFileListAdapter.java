package com.example.masato.githubfeed.view.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.diff.DiffFile;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/02/06.
 */

public class DiffFileListAdapter extends RecyclerView.Adapter {

    private List<DiffFile> diffFiles = new ArrayList<>();
    private Context context;
    private LayoutInflater inflater;

    public void addDiffFiles(List<DiffFile> diffFiles) {
        this.diffFiles.addAll(diffFiles);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.diff, parent, false);
        return new DiffFileViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        DiffFile diffFile = diffFiles.get(position);
        DiffFileViewHolder diffFileViewHolder = (DiffFileViewHolder) holder;
        diffFileViewHolder.bindDiffFile(diffFile);
    }

    @Override
    public int getItemCount() {
        return diffFiles.size();
    }

    public DiffFileListAdapter(Context context) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    private class DiffFileViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        AppCompatTextView fileName;
        AppCompatTextView addition;
        AppCompatTextView deletion;
        DiffAdapter diffAdapter;
        boolean diffEverLoaded;

        void bindDiffFile(DiffFile diffFile) {
            fileName.setText(diffFile.fileName);
            addition.setText("+" + diffFile.additions);
            deletion.setText("-" + diffFile.deletions);
            if (!diffEverLoaded) {
                diffAdapter.setDiffFile(diffFile);
                diffEverLoaded = true;
            }
        }

        private void setUpRecyclerView() {
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
            diffAdapter = new DiffAdapter(context);
            recyclerView.setAdapter(diffAdapter);
            recyclerView.setLayoutManager(layoutManager);
        }

        public DiffFileViewHolder(View itemView) {
            super(itemView);
            recyclerView = (RecyclerView) itemView.findViewById(R.id.diff_recycler_view);
            fileName = (AppCompatTextView) itemView.findViewById(R.id.diff_file_name);
            addition = (AppCompatTextView) itemView.findViewById(R.id.diff_addition);
            deletion = (AppCompatTextView) itemView.findViewById(R.id.diff_deletion);
            setUpRecyclerView();
        }
    }
}
