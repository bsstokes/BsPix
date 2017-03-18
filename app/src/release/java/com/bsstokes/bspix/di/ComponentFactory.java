package com.bsstokes.bspix.di;

import com.bsstokes.bspix.app.BsPixApplication;

public class ComponentFactory {
    public static AppComponent create(BsPixApplication application) {
        return DaggerReleaseAppComponent.builder()
                .appModule(new AppModule(application))
                .releaseApiModule(new ReleaseApiModule())
                .build();
    }
}
