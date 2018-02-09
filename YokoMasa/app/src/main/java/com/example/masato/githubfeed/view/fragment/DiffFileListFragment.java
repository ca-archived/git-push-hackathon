package com.example.masato.githubfeed.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.BaseModel;
import com.example.masato.githubfeed.model.diff.DiffFile;
import com.example.masato.githubfeed.presenter.DiffFileListPresenter;
import com.example.masato.githubfeed.presenter.PaginatingListPresenter;
import com.example.masato.githubfeed.view.DiffFileListView;
import com.example.masato.githubfeed.view.adapter.DiffAdapter;
import com.example.masato.githubfeed.view.adapter.DiffFileListAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * Created by Masato on 2018/02/06.
 */

public class DiffFileListFragment extends BaseFragment implements DiffFileListView {

    private RecyclerView recyclerView;
    private DiffFileListAdapter adapter;
    private LoadingFragment loadingFragment;
    private ArrayList<DiffFile> diffFiles;
    private boolean isLoading;
    private boolean isViewCreated;
    private boolean fragmentUnControllable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_diff_file_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        showLoadingFragment();

        recyclerView = (RecyclerView) view.findViewById(R.id.diff_file_list_recyclerView);
        adapter = new DiffFileListAdapter(getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);

        if (savedInstanceState == null) {
            setUpContent();
        } else {
            restoreContent(savedInstanceState);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        fragmentUnControllable = true;
        outState.putParcelableArrayList("diff_files", diffFiles);
        if (isViewCreated) {
            outState.putInt("scroll_x", recyclerView.getScrollX());
            outState.putInt("scroll_y", recyclerView.getScrollY());
        }
    }

    private void setUpContent() {
        ArrayList<DiffFile> diffFiles = getArguments().getParcelableArrayList("diff_files");
        String url = getArguments().getString("url");
        if (diffFiles != null) {
            showDiffFiles(diffFiles);
        } else {
            new DiffFileListPresenter(this, url);
        }
    }

    private void restoreContent(Bundle savedInstanceState) {
        ArrayList<DiffFile> diffFiles = savedInstanceState.getParcelableArrayList("diff_files");
        showDiffFiles(diffFiles);
        int scrollX = savedInstanceState.getInt("scroll_x");
        int scrollY = savedInstanceState.getInt("scroll_y");
        recyclerView.setScrollY(scrollX);
        recyclerView.setScrollY(scrollY);
    }

    private void showLoadingFragment() {
        if (fragmentUnControllable) {
            return;
        }
        loadingFragment = new LoadingFragment();
        FragmentTransaction ft = getChildFragmentManager().beginTransaction();
        ft.add(R.id.diff_file_list_mother, loadingFragment);
        ft.commit();
        isLoading = true;
    }

    private void removeLoadingFragment() {
        if (fragmentUnControllable) {
            return;
        }
        if (isLoading) {
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.remove(loadingFragment);
            ft.commit();
            isLoading = false;
        }
    }

    @Override
    public void showDiffFiles(ArrayList<DiffFile> diffFiles) {
        this.diffFiles = diffFiles;
        adapter.setDiffFiles(diffFiles);
        recyclerView.addOnChildAttachStateChangeListener(new RecyclerView.OnChildAttachStateChangeListener() {
            @Override
            public void onChildViewAttachedToWindow(View view) {
                removeLoadingFragment();
            }

            @Override
            public void onChildViewDetachedFromWindow(View view) {

            }
        });
    }
}
