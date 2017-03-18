package com.bsstokes.bspix.auth;

import android.support.annotation.NonNull;

import com.bsstokes.bspix.api.InstagramApi;
import com.bsstokes.bspix.api.InstagramApiHelper;

import okhttp3.HttpUrl;

class LoginController {

    public interface View {
        void loadUrl(@NonNull HttpUrl authorizeUrl);

        void finish();

        void requestSync();
    }

    @NonNull private final View view;
    @NonNull private final Account account;

    LoginController(@NonNull View view, @NonNull Account account) {
        this.view = view;
        this.account = account;
    }

    void start() {
        view.loadUrl(InstagramApi.AUTHORIZE_URL);
    }

    void onLoadPage(String url) {
        if (InstagramApiHelper.isValidRedirectUrl(url)) {
            final String accessToken = InstagramApiHelper.getAccessToken(url);
            if (null != accessToken) {
                account.logIn(accessToken);
                view.requestSync();
                view.finish();
            }
        }
    }
}
