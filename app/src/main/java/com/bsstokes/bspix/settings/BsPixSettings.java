package com.bsstokes.bspix.settings;

import android.support.annotation.Nullable;

public interface BsPixSettings {
    String getAccessToken();

    void setAccessToken(@Nullable String accessToken);
}
