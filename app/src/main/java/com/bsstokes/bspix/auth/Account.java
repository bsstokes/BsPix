package com.bsstokes.bspix.auth;

import com.bsstokes.bspix.settings.BsPixSettings;

public class Account {

    private final BsPixSettings bsPixSettings;

    public Account(BsPixSettings bsPixSettings) {
        this.bsPixSettings = bsPixSettings;
    }

    public boolean isLoggedIn() {
        return null != bsPixSettings.getAccessToken();
    }
}
