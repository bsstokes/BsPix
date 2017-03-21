package com.bsstokes.bspix.app.home.my_media;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bsstokes.bspix.R;
import com.bsstokes.bspix.app.BsPixApplication;
import com.bsstokes.bspix.app.home.MediaAdapter;
import com.bsstokes.bspix.app.media_item.MediaItemActivity;
import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.data.Media;
import com.squareup.picasso.Picasso;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MyMediaFragment extends Fragment implements MediaAdapter.OnClickListener, MyMediaController.View {

    @BindView(R.id.mediaRecyclerView) RecyclerView mediaRecyclerView;

    @Inject BsPixDatabase bsPixDatabase;
    @Inject Picasso picasso;

    private Unbinder unbinder;
    private MediaAdapter mediaAdapter;
    private MyMediaController myMediaController;

    public MyMediaFragment() {
        // Default constructor
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        BsPixApplication.getBsPixApplication(getActivity()).getAppComponent().inject(this);

        final View view = inflater.inflate(R.layout.home_my_media_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);

        mediaRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        mediaAdapter = new MediaAdapter(this, picasso);
        mediaRecyclerView.setAdapter(mediaAdapter);

        myMediaController = new MyMediaController(this, bsPixDatabase);
        myMediaController.load();

        return view;
    }

    @Override public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        myMediaController.unload();
    }

    @Override
    public void setMedia(@NonNull List<Media> mediaList) {
        mediaAdapter.setMedia(mediaList);
    }

    @Override public void onClickMediaItem(@NonNull String mediaItemId) {
        myMediaController.onClickMediaItem(mediaItemId);
    }

    @Override public void launchMediaItem(String mediaItemId) {
        startActivity(MediaItemActivity.createIntent(getActivity(), mediaItemId));
    }
}
