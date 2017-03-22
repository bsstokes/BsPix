package com.bsstokes.bspix.di;

import com.bsstokes.bspix.app.BsPixApplication;
import com.bsstokes.bspix.auth.Account;
import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.settings.BsPixSettings;
import com.bsstokes.bspix.settings.SharedPreferenceSettings;
import com.squareup.picasso.Picasso;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class AppModule {

    private final BsPixApplication application;

    AppModule(BsPixApplication application) {
        this.application = application;
    }

    @Provides @Singleton BsPixApplication provideApplication() {
        return application;
    }

    @Provides @Singleton BsPixSettings provideBsPixSettings() {
        return new SharedPreferenceSettings(application.getApplicationContext());
    }

    @Provides @Singleton
    Account provideAccount(BsPixSettings bsPixSettings, BsPixDatabase bsPixDatabase) {
        return new Account(bsPixSettings, bsPixDatabase);
    }

    @Provides @Singleton Picasso providePicasso() {
        return Picasso.with(application);
    }
}
