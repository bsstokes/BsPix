package com.bsstokes.bspix.sync;

import android.support.annotation.NonNull;

import com.bsstokes.bspix.api.InstagramApi;
import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.data.Media;
import com.bsstokes.bspix.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

class MediaSyncer {
    @NonNull private final BsPixDatabase bsPixDatabase;

    MediaSyncer(@NonNull BsPixDatabase bsPixDatabase) {
        this.bsPixDatabase = bsPixDatabase;
    }

    public void sync(List<InstagramApi.Media> apiMediaList) {
        final ArrayList<Media> mediaList = new ArrayList<>();
        for (final InstagramApi.Media apiMedia : apiMediaList) {
            final Media media = convert(apiMedia);
            mediaList.add(media);
        }

        bsPixDatabase.putMedia(mediaList);
    }

    private Media convert(InstagramApi.Media apiMedia) {
        final String tags = (null == apiMedia.tags) ? "" : StringUtils.join(", ", apiMedia.tags);
        final String location = (null == apiMedia.location) ? "" : apiMedia.location.name;

        return Media.builder()
                .id(apiMedia.id)
                .userId(apiMedia.user.id)
                .userName(apiMedia.user.username)
                .caption(apiMedia.caption.text)
                .type(apiMedia.type)
                .tags(tags)
                .location(location)
                .lowResolutionUrl(apiMedia.images.low_resolution.url)
                .lowResolutionWidth(apiMedia.images.low_resolution.width)
                .lowResolutionHeight(apiMedia.images.low_resolution.height)
                .thumbnailUrl(apiMedia.images.thumbnail.url)
                .thumbnailWidth(apiMedia.images.thumbnail.width)
                .thumbnailHeight(apiMedia.images.thumbnail.height)
                .standardResolutionUrl(apiMedia.images.standard_resolution.url)
                .standardResolutionWidth(apiMedia.images.standard_resolution.width)
                .standardResolutionHeight(apiMedia.images.standard_resolution.height)
                .build();
    }
}
