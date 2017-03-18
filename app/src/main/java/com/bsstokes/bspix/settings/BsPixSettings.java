package com.bsstokes.bspix.settings;

import android.support.annotation.Nullable;

public interface BsPixSettings {
    @Nullable
    String getAccessToken();

    void setAccessToken(@Nullable String accessToken);

    void clearCookies();
}
