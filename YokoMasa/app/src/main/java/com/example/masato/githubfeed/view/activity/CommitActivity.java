package com.example.masato.githubfeed.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.githubapi.GitHubApi;
import com.example.masato.githubfeed.model.Commit;
import com.example.masato.githubfeed.model.diff.DiffFile;
import com.example.masato.githubfeed.view.fragment.DiffFileListFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/02/06.
 */

public class CommitActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commit);
        Commit commit = getIntent().getParcelableExtra("commit");
        Log.i("gh_feed", "commit activity onCreate: " + commit.sha);
        if (savedInstanceState == null) {
            GitHubApi.getApi().fetchCommitDiffFileList(commit, result -> {
                Log.i("gh_feed", "fetch diff list result");
                if (result.isSuccessful) {
                    ArrayList<DiffFile> diffFileList = (ArrayList<DiffFile>) result.resultObject;
                    Log.i("gh_feed", "fetch diff list successful. list size: " + diffFileList.size());
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("diff_files", diffFileList);
                    DiffFileListFragment diffFileListFragment = new DiffFileListFragment();
                    diffFileListFragment.setArguments(bundle);
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.add(R.id.commit_mother, diffFileListFragment);
                    ft.commit();
                }
            });
        }
    }
}
