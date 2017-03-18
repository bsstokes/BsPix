package com.bsstokes.bspix.app.home;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bsstokes.bspix.R;
import com.bsstokes.bspix.data.Media;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {

    @NonNull private final Picasso picasso;
    @NonNull private List<Media> mediaList = new ArrayList<>();

    public MediaAdapter(@NonNull Picasso picasso) {
        this.picasso = picasso;
    }

    public void setMedia(List<Media> mediaList) {
        this.mediaList.clear();
        this.mediaList = new ArrayList<>(mediaList);
        notifyDataSetChanged();
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_item, parent, false);
        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        final Media media = mediaList.get(position);
        picasso.load(media.standardResolutionUrl()).into(holder.imageView);
    }

    @Override public int getItemCount() {
        return mediaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView) ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
