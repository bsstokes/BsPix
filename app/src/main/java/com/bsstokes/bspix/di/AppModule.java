package com.bsstokes.bspix.di;

import com.bsstokes.bspix.BsPixApplication;
import com.bsstokes.bspix.auth.Account;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class AppModule {

    private final BsPixApplication application;

    AppModule(BsPixApplication application) {
        this.application = application;
    }

    @Provides
    @Singleton
    BsPixApplication provideApplication() {
        return application;
    }

    @Provides
    @Singleton
    Account provideAccount() {
        return new Account();
    }
}
