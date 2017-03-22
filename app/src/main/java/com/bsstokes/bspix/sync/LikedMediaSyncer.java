package com.bsstokes.bspix.sync;

import android.support.annotation.NonNull;

import com.bsstokes.bspix.api.InstagramApi;
import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.data.Media;

import java.util.ArrayList;
import java.util.List;

public class LikedMediaSyncer {
    @NonNull private final BsPixDatabase bsPixDatabase;

    public LikedMediaSyncer(@NonNull BsPixDatabase bsPixDatabase) {
        this.bsPixDatabase = bsPixDatabase;
    }

    public void sync(@NonNull List<InstagramApi.Media> apiLikedMediaList) {
        final ArrayList<Media> likedMediaList = new ArrayList<>();
        for (final InstagramApi.Media apiMedia : apiLikedMediaList) {
            final Media media = MediaSyncer.convert(apiMedia);
            likedMediaList.add(media);
        }

        bsPixDatabase.putLikedMedia(likedMediaList);
    }
}
