package com.example.masato.githubfeed.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.example.masato.githubfeed.view.fragment.transaction.FTTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/02/03.
 */

public class BaseFragment extends Fragment {

    private List<FTTask> FTQueue = new ArrayList<>();
    private boolean FTSafe;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FTSafe = true;
        execQueuedTransactions();
    }

    private void execQueuedTransactions() {
        for (FTTask ftTask : FTQueue) {
            ftTask.execute();
        }
        FTQueue.clear();
    }

    protected void doSafeFTTransaction(FTTask ftTask) {
        if (FTSafe) {
            ftTask.execute();
        } else {
            FTQueue.add(ftTask);
        }
    }

    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }

    public void showToast(int stringId) {
        Toast.makeText(getContext(), stringId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        FTSafe = false;
    }

    public String getName() {
        return getArguments().getString("name");
    };
}
