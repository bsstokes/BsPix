package com.bsstokes.bspix.sync;

import android.support.annotation.NonNull;

import com.bsstokes.bspix.api.InstagramApi;
import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.data.User;

import java.util.ArrayList;
import java.util.List;

class FollowsSyncer {

    @NonNull private final BsPixDatabase bsPixDatabase;

    FollowsSyncer(@NonNull BsPixDatabase bsPixDatabase) {
        this.bsPixDatabase = bsPixDatabase;
    }

    public void sync(@NonNull List<InstagramApi.FollowedUser> followedUsers) {
        final List<User> users = new ArrayList<>(followedUsers.size());
        for (final InstagramApi.FollowedUser followedUser : followedUsers) {
            users.add(convert(followedUser));
        }

        bsPixDatabase.putUsers(users);
    }

    private User convert(InstagramApi.FollowedUser followedUser) {
        return User.builder()
                .id(followedUser.id)
                .userName(followedUser.username)
                .fullName(followedUser.full_name)
                .profilePicture(followedUser.profile_picture)
                .build();
    }
}
