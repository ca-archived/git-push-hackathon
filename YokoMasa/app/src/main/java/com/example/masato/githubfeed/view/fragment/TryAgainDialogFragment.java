package com.example.masato.githubfeed.view.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.example.masato.githubfeed.R;

/**
 * Created by Masato on 2018/02/22.
 */

public class TryAgainDialogFragment extends DialogFragment {

    private TryAgainListener listener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof TryAgainListener) {
            this.listener = (TryAgainListener) context;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof TryAgainListener) {
            this.listener = (TryAgainListener) activity;
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        AlertDialog alertDialog = builder.setMessage(R.string.failure_internet)
                .setPositiveButton(R.string.error_try_again, (dialog, id) -> {
                    Fragment fragment = getParentFragment();
                    if (fragment instanceof TryAgainListener) {
                        listener = (TryAgainListener) fragment;
                    }
                    if (listener != null) {
                        listener.onTryAgain();
                    }
                })
                .create();
        return alertDialog;
    }

    public interface TryAgainListener {
        public void onTryAgain();
    }
}
