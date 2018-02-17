package com.example.masato.githubfeed.view.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/02/03.
 */

public class BaseFragment extends Fragment {

    private List<Runnable> FTQueue = new ArrayList<>();
    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean FTSafe;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FTSafe = true;
        execQueuedTransactions();
    }

    private void execQueuedTransactions() {
        for (Runnable runnable : FTQueue) {
            handler.post(runnable);
        }
        FTQueue.clear();
    }

    protected void doSafeFTTransaction(Runnable runnable) {
        if (FTSafe) {
            handler.post(runnable);
        } else {
            FTQueue.add(runnable);
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
