package com.bsstokes.bspix.app.home.follows;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bsstokes.bspix.R;
import com.bsstokes.bspix.app.BsPixApplication;
import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.data.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FollowsFragment extends Fragment implements FollowsController.View, FollowsAdapter.OnClickListener {

    @NonNull public static FollowsFragment create() {
        return new FollowsFragment();
    }

    @BindView(R.id.follows_recycler_view) RecyclerView followsRecyclerView;

    @Inject BsPixDatabase bsPixDatabase;
    @Inject Picasso picasso;

    private FollowsController followsController;
    private FollowsAdapter followsAdapter;
    private Unbinder unbinder;

    public FollowsFragment() {
        // Do nothing
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BsPixApplication.getBsPixApplication(getActivity()).getAppComponent().inject(this);

        final View view = inflater.inflate(R.layout.home_follows_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        followsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        followsAdapter = new FollowsAdapter(this, picasso);
        followsRecyclerView.setAdapter(followsAdapter);

        followsController = new FollowsController(this, bsPixDatabase);
        followsController.load();

        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        followsController.unload();
        unbinder.unbind();
    }

    @Override public void setFollows(@NonNull List<User> follows) {
        followsAdapter.setFollows(follows);
    }
}
