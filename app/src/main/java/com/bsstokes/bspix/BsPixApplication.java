package com.bsstokes.bspix;

import android.app.Application;

import com.bsstokes.bspix.initializers.StethoInitializer;

public class BsPixApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        StethoInitializer.initialize(this);
    }
}
