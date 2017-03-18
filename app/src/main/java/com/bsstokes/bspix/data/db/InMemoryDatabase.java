package com.bsstokes.bspix.data.db;

import android.support.annotation.NonNull;

import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.data.User;

import java.util.HashMap;
import java.util.Map;

public class InMemoryDatabase implements BsPixDatabase {
    private final Map<String, User> users = new HashMap<>();

    @Override synchronized public void putUser(@NonNull String id, @NonNull User user) {
        users.put(id, user);
    }

    @NonNull synchronized @Override public User getUser(@NonNull String id) {
        final User user = users.get(id);
        if (null == user) {
            return NO_USER;
        } else {
            return user;
        }
    }
}
