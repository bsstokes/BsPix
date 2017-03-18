package com.bsstokes.bspix.data;

import android.support.annotation.NonNull;

public interface BsPixDatabase {
    void putUser(@NonNull String id, @NonNull User user);

    @NonNull User getUser(@NonNull String id);

    User NO_USER = User.builder()
            .build();
}
