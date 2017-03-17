package com.bsstokes.bspix;

import android.app.Application;

import com.bsstokes.bspix.di.AppComponent;
import com.bsstokes.bspix.di.ComponentFactory;
import com.bsstokes.bspix.initializers.StethoInitializer;
import com.crashlytics.android.Crashlytics;
import com.squareup.leakcanary.LeakCanary;

import io.fabric.sdk.android.Fabric;

public class BsPixApplication extends Application {

    private AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);

        Fabric.with(this, new Crashlytics());

        appComponent = ComponentFactory.create(this);

        StethoInitializer.initialize(this);
    }
}
