package com.bsstokes.bspix.app.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import butterknife.OnClick;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.ViewHolder> {

    public interface OnClickListener {
        void onClickMediaItem(@NonNull String mediaId);
    }

    @NonNull private final Picasso picasso;
    @NonNull private List<Media> mediaList = new ArrayList<>();
    @NonNull private final OnClickListener onClickListener;

    public MediaAdapter(@NonNull OnClickListener onClickListener, @NonNull Picasso picasso) {
        this.onClickListener = onClickListener;
        this.picasso = picasso;
    }

    public void setMedia(List<Media> mediaList) {
        this.mediaList.clear();
        this.mediaList.addAll(mediaList);
        notifyDataSetChanged();
    }

    @Override public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.media_item, parent, false);
        return new ViewHolder(view);
    }

    @Override public void onBindViewHolder(ViewHolder holder, int position) {
        final Media media = mediaList.get(position);
        holder.bind(media);
    }

    @Override public int getItemCount() {
        return mediaList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.imageView) ImageView imageView;
        @Nullable String mediaId;

        ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(@NonNull Media media) {
            mediaId = media.id();
            picasso.load(media.standardResolutionUrl()).into(imageView);
        }

        @OnClick(R.id.imageView)
        void onClickImage() {
            if (null != mediaId) {
                onClickListener.onClickMediaItem(mediaId);
            }
        }
    }
}
