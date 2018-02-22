package com.example.masato.githubfeed.view.activity;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.view.fragment.ErrorFragment;
import com.example.masato.githubfeed.view.fragment.FragmentFactory;
import com.example.masato.githubfeed.view.fragment.transaction.FTTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/02/17.
 */

public abstract class BaseActivity extends AppCompatActivity {

    private List<FTTask> FTQueue = new ArrayList<>();
    private boolean FTSafe;
    private ErrorFragment errorFragment;

    @Override
    protected void onResume() {
        super.onResume();
        FTSafe = true;
        execQueuedTransactions();
    }

    public void showErrorFragment(int motherId, Failure failure, String errorMessage) {
        doSafeFTTransaction(() -> {
            errorFragment = FragmentFactory.createErrorFragment(failure, errorMessage);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(motherId, errorFragment);
            ft.commit();
        });
    }

    public void removeErrorFragment() {
        doSafeFTTransaction(() -> {
            if (errorFragment == null) {
                return;
            }
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.remove(errorFragment);
            ft.commit();
            errorFragment = null;
        });
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

    public void showToast(int stringId) {
        Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
    }

    public void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        FTSafe = false;
    }
}
