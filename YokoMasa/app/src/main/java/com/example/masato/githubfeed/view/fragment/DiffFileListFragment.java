package com.example.masato.githubfeed.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.BaseModel;
import com.example.masato.githubfeed.model.diff.DiffFile;
import com.example.masato.githubfeed.presenter.PaginatingListPresenter;
import com.example.masato.githubfeed.view.adapter.DiffAdapter;
import com.example.masato.githubfeed.view.adapter.DiffFileListAdapter;

import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * Created by Masato on 2018/02/06.
 */

public class DiffFileListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.general_recycler_view_layout, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        List<DiffFile> diffFiles = getArguments().getParcelableArrayList("diff_files");
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.general_recyclerView);
        DiffFileListAdapter diffFileListAdapter = new DiffFileListAdapter(diffFiles, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(diffFileListAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

}
