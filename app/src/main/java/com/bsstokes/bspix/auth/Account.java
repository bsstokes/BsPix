package com.bsstokes.bspix.auth;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bsstokes.bspix.settings.BsPixSettings;

public class Account {

    @NonNull
    private final BsPixSettings bsPixSettings;

    public Account(@NonNull BsPixSettings bsPixSettings) {
        this.bsPixSettings = bsPixSettings;
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
    }

    @Nullable public String getAccessToken() {
        return bsPixSettings.getAccessToken();
    }
}
