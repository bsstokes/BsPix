package com.bsstokes.bspix.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.webkit.CookieManager;

public class SharedPreferenceSettings implements BsPixSettings {

    @NonNull private final Context context;

    private static final String PREFERENCES_NAME = "BsPix";
    private static final String KEY_ACCESS_TOKEN = "access_token";

    public SharedPreferenceSettings(@NonNull Context context) {
        this.context = context;
    }

    @Override
    public String getAccessToken() {
        return getSharedPreferences().getString(KEY_ACCESS_TOKEN, null);
    }

    @Override
    public void setAccessToken(@Nullable String accessToken) {
        getSharedPreferences().edit()
                .putString(KEY_ACCESS_TOKEN, accessToken)
                .apply();
    }

    @NonNull
    private SharedPreferences getSharedPreferences() {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public void clearCookies() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().removeAllCookies(null);
        } else {
            //noinspection deprecation
            CookieManager.getInstance().removeAllCookie();
        }
    }
}
