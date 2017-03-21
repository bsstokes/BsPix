package com.bsstokes.bspix.app.home.follows;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bsstokes.bspix.R;

public class FollowsFragment extends Fragment {

    @NonNull public static FollowsFragment create() {
        return new FollowsFragment();
    }

    public FollowsFragment() {
        // Do nothing
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.home_follows_fragment, container, false);
        return view;
    }
}
