package com.example.masato.githubfeed.view.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.masato.githubfeed.R;
import com.example.masato.githubfeed.model.Profile;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Masato on 2018/01/23.
 */

public class ProfileFragment extends Fragment {

    private Profile profile;

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        setContent(view);
        return view;
    }

    private void setContent(View view) {
        AppCompatTextView textView = (AppCompatTextView) view.findViewById(R.id.profile_name);
        CircleImageView imageView = (CircleImageView) view.findViewById(R.id.profile_image);
        textView.setText(profile.name);
        imageView.setImageBitmap(profile.icon);
    }
}
