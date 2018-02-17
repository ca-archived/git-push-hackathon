package com.example.masato.githubfeed.view.activity;

import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/02/17.
 */

public class BaseActivity extends AppCompatActivity {

    private List<Runnable> FTQueue = new ArrayList<>();
    private Handler handler = new Handler(Looper.getMainLooper());
    private boolean FTSafe;

    @Override
    protected void onResume() {
        super.onResume();
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

    @Override
    protected void onPause() {
        super.onPause();
        FTSafe = false;
    }
}
