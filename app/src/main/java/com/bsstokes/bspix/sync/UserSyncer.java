package com.bsstokes.bspix.sync;

import android.support.annotation.NonNull;

import com.bsstokes.bspix.api.InstagramApi;
import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.data.User;

public class UserSyncer {

    @NonNull private final BsPixDatabase bsPixDatabase;

    public UserSyncer(@NonNull BsPixDatabase bsPixDatabase) {
        this.bsPixDatabase = bsPixDatabase;
    }

    public void sync(InstagramApi.User apiUser) {
        final User user = convert(apiUser);
        bsPixDatabase.putUser(user);
    }

    public User convert(InstagramApi.User apiUser) {
        int mediaCount = 0;
        int followsCount = 0;
        int followedByCount = 0;
        if (null != apiUser.counts) {
            mediaCount = apiUser.counts.media;
            followsCount = apiUser.counts.follows;
            followedByCount = apiUser.counts.followed_by;
        }

        return User.builder()
                .id(apiUser.id)
                .userName(apiUser.username)
                .fullName(apiUser.full_name)
                .profilePicture(apiUser.profile_picture)
                .bio(apiUser.bio)
                .website(apiUser.website)
                .mediaCount(mediaCount)
                .followsCount(followsCount)
                .followedByCount(followedByCount)
                .build();
    }
}
