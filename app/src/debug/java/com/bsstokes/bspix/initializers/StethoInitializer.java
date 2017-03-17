package com.bsstokes.bspix.initializers;

import android.content.Context;
import android.support.annotation.NonNull;

import com.facebook.stetho.Stetho;

public class StethoInitializer {
    public static void initialize(final @NonNull Context context) {
        Stetho.initializeWithDefaults(context);
    }
}
