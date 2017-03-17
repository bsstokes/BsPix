package com.bsstokes.bspix.api;

import android.support.annotation.Nullable;

import okhttp3.HttpUrl;

public class InstagramApiHelper {

    public static boolean isValidRedirectUrl(@Nullable String url) {
        if (null == url) {
            return false;
        }

        return url.startsWith(InstagramApi.REDIRECT_URL.toString());
    }

    @Nullable
    public static String getAccessToken(@Nullable String url) {
        if (null == url) {
            return null;
        }

        final HttpUrl httpUrl = HttpUrl.parse(url);
        if (null == httpUrl) {
            return null;
        }

        final String fragment = httpUrl.fragment();
        if (null == fragment) {
            return null;
        }

        final String[] fragmentValues = fragment.split("=");
        if (2 > fragmentValues.length) {
            return null;
        }

        if ("access_token".equals(fragmentValues[0])) {
            return fragmentValues[1];
        }

        return null;
    }
}
