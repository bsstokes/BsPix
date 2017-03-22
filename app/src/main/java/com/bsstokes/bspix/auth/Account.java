package com.bsstokes.bspix.auth;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.settings.BsPixSettings;

public class Account {

    @NonNull private final BsPixSettings bsPixSettings;
    @NonNull private final BsPixDatabase bsPixDatabase;

    public Account(@NonNull BsPixSettings bsPixSettings, @NonNull BsPixDatabase bsPixDatabase) {
        this.bsPixSettings = bsPixSettings;
        this.bsPixDatabase = bsPixDatabase;
    }

    public boolean isLoggedIn() {
        return null != bsPixSettings.getAccessToken();
    }

    void logIn(@NonNull String accessToken) {
        bsPixSettings.setAccessToken(accessToken);
    }

    public void logOut() {
        bsPixSettings.setAccessToken(null);
        bsPixSettings.clearCookies();

        bsPixDatabase.deleteAllLikedMedia();
        bsPixDatabase.deleteAllMedia();
        bsPixDatabase.deleteAllUsers();
    }

    @Nullable public String getAccessToken() {
        return bsPixSettings.getAccessToken();
    }
}
