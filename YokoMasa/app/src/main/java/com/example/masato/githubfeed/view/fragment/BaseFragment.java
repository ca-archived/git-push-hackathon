package com.example.masato.githubfeed.view.fragment;

import android.support.v4.app.Fragment;
import android.widget.Toast;

/**
 * Created by Masato on 2018/02/03.
 */

public abstract class BaseFragment extends Fragment {

    public void showToast(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }

    public void showToast(int stringId) {
        Toast.makeText(getContext(), stringId, Toast.LENGTH_LONG).show();
    }

    public abstract String getName();
}
