package com.example.masato.githubfeed.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.githubapi.Failure;

/**
 * Created by Masato on 2018/02/22.
 */

public class ErrorFragment extends Fragment implements View.OnClickListener {

    private TryAgainListener listener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_error, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AppCompatButton button = (AppCompatButton) view.findViewById(R.id.error_try_again_button);
        button.setOnClickListener(this);
        Failure failure = (Failure) getArguments().getSerializable("failure");
        String messageString = getArguments().getString("message");
        AppCompatTextView overview = (AppCompatTextView) view.findViewById(R.id.error_overview);
        AppCompatTextView message = (AppCompatTextView) view.findViewById(R.id.error_message);
        overview.setText(failure.textId);
        message.setText(messageString);

    }

    @Override
    public void onClick(View view) {
        if (listener != null) {
            listener.onTryAgain();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Fragment fragment = getParentFragment();
        if (fragment instanceof TryAgainListener) {
            this.listener = (TryAgainListener) fragment;
        }
    }

    public interface TryAgainListener {
        public void onTryAgain();
    }
}
