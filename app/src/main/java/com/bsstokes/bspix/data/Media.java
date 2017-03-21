package com.bsstokes.bspix.data;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class Media {
    @Nullable public abstract String id();

    @Nullable public abstract String userId();

    @Nullable public abstract String userName();

    @Nullable public abstract String caption();

    @Nullable public abstract String type();

    @Nullable public abstract String tags();

    @Nullable public abstract String location();

    @Nullable public abstract String lowResolutionUrl();

    public abstract int lowResolutionWidth();

    public abstract int lowResolutionHeight();

    @Nullable public abstract String thumbnailUrl();

    public abstract int thumbnailWidth();

    public abstract int thumbnailHeight();

    @Nullable public abstract String standardResolutionUrl();

    public abstract int standardResolutionWidth();

    public abstract int standardResolutionHeight();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_Media.Builder()
                .lowResolutionWidth(0)
                .lowResolutionHeight(0)
                .thumbnailWidth(0)
                .thumbnailHeight(0)
                .standardResolutionWidth(0)
                .standardResolutionHeight(0);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(String id);

        public abstract Builder userId(String userId);

        public abstract Builder userName(String userName);

        public abstract Builder caption(String caption);

        public abstract Builder type(String type);

        public abstract Builder tags(String tags);

        public abstract Builder location(String location);

        public abstract Builder lowResolutionUrl(String lowResolutionUrl);

        public abstract Builder lowResolutionWidth(int lowResolutionWidth);

        public abstract Builder lowResolutionHeight(int lowResolutionHeight);

        public abstract Builder thumbnailUrl(String thumbnailUrl);

        public abstract Builder thumbnailWidth(int thumbnailWidth);

        public abstract Builder thumbnailHeight(int thumbnailHeight);

        public abstract Builder standardResolutionUrl(String standardResolutionUrl);

        public abstract Builder standardResolutionWidth(int standardResolutionWidth);

        public abstract Builder standardResolutionHeight(int standardResolutionHeight);

        public abstract Media build();
    }
}
