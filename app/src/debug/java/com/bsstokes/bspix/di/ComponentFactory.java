package com.bsstokes.bspix.di;

import android.support.annotation.NonNull;

import com.bsstokes.bspix.app.BsPixApplication;

public class ComponentFactory {
    public static AppComponent create(@NonNull BsPixApplication application) {
        return DaggerDebugAppComponent.builder()
                .appModule(new AppModule(application))
                .debugApiModule(new DebugApiModule())
                .build();
    }
}
