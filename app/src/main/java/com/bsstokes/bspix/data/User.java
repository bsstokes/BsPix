package com.bsstokes.bspix.data;

import android.support.annotation.Nullable;

import com.google.auto.value.AutoValue;

@AutoValue
public abstract class User {

    @Nullable public abstract String id();

    public abstract boolean self();

    @Nullable public abstract String userName();

    @Nullable public abstract String fullName();

    @Nullable public abstract String profilePicture();

    @Nullable public abstract String bio();

    @Nullable public abstract String website();

    public abstract int mediaCount();

    public abstract int followsCount();

    public abstract int followedByCount();

    public abstract Builder toBuilder();

    public static Builder builder() {
        return new AutoValue_User.Builder()
                .self(false)
                .mediaCount(0)
                .followsCount(0)
                .followedByCount(0);
    }

    @AutoValue.Builder
    public abstract static class Builder {
        public abstract Builder id(String id);

        public abstract Builder self(boolean self);

        public abstract Builder userName(String userName);

        public abstract Builder fullName(String fullName);

        public abstract Builder profilePicture(String profilePicture);

        public abstract Builder bio(String bio);

        public abstract Builder website(String website);

        public abstract Builder mediaCount(int mediaCount);

        public abstract Builder followsCount(int followsCount);

        public abstract Builder followedByCount(int followedByCount);

        public abstract User build();
    }
}
