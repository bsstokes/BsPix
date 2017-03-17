package com.bsstokes.bspix;

import android.app.Application;

import com.bsstokes.bspix.initializers.StethoInitializer;
import com.squareup.leakcanary.LeakCanary;

public class BsPixApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        StethoInitializer.initialize(this);
    }
}
