package com.bsstokes.bspix;

import android.app.Activity;
import android.app.Application;
import android.support.annotation.NonNull;

import com.bsstokes.bspix.di.AppComponent;
import com.bsstokes.bspix.di.ComponentFactory;
import com.bsstokes.bspix.initializers.StethoInitializer;
import com.crashlytics.android.Crashlytics;
import com.squareup.leakcanary.LeakCanary;

import io.fabric.sdk.android.Fabric;

public class BsPixApplication extends Application {

    private AppComponent appComponent;

    @NonNull
    public static BsPixApplication getBsPixApplication(@NonNull Activity activity) {
        return (BsPixApplication) activity.getApplication();
    }

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

    @NonNull
    public AppComponent getAppComponent() {
        return appComponent;
    }
}