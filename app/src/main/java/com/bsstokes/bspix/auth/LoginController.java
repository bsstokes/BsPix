package com.bsstokes.bspix.auth;

import android.support.annotation.NonNull;

import com.bsstokes.bspix.api.InstagramApi;

import okhttp3.HttpUrl;

class LoginController {

    public interface View {
        void loadUrl(@NonNull HttpUrl authorizeUrl);
    }

    private final View view;

    LoginController(View view) {
        this.view = view;
    }

    void start() {
        view.loadUrl(InstagramApi.AUTHORIZE_URL);
    }
}
