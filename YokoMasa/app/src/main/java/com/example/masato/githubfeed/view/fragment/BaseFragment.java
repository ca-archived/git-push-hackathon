package com.example.masato.githubfeed.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.Toast;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.githubapi.Failure;
import com.example.masato.githubfeed.view.fragment.transaction.FTTask;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Masato on 2018/02/03.
 */

public class BaseFragment extends Fragment implements ErrorFragment.TryAgainListener {

    private List<FTTask> FTQueue = new ArrayList<>();
    private LoadingFragment loadingFragment;
    private ErrorFragment errorFragment;
    private boolean FTSafe;

    @Override
    public void onResume() {
        super.onResume();
        FTSafe = true;
        execQueuedTransactions();
    }

    @Override
    public void tryAgain() {

    }

    public void showErrorFragment(int motherId, Failure failure, String errorMessage) {
        doSafeFTTransaction(() -> {
            errorFragment = FragmentFactory.createErrorFragment(failure, errorMessage);
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.add(motherId, errorFragment);
            ft.commit();
        });
    }

    public void removeErrorFragment() {
        doSafeFTTransaction(() -> {
            if (errorFragment == null) {
                return;
            }
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.remove(errorFragment);
            ft.commit();
            errorFragment = null;
        });
    }

    public void showLoadingFragment(int motherId) {
        doSafeFTTransaction(() -> {
            loadingFragment = new LoadingFragment();
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.add(motherId, loadingFragment);
            ft.commit();
        });
    }

    public void removeLoadingFragment() {
        doSafeFTTransaction(() -> {
            if (loadingFragment == null) {
                return;
            }
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.setCustomAnimations(R.anim.fade_animation, R.anim.fade_animation);
            ft.remove(loadingFragment);
            ft.commit();
            loadingFragment = null;
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

    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }

    public void showToast(int stringId) {
        Toast.makeText(getContext(), stringId, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPause() {
        super.onPause();
        FTSafe = false;
    }

    public String getName() {
        return getArguments().getString("name");
    };
}
