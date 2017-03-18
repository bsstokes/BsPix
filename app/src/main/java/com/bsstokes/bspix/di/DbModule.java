package com.bsstokes.bspix.di;

import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.data.db.InMemoryDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
class DbModule {
    @Provides @Singleton BsPixDatabase provideDatabase() {
        return new InMemoryDatabase();
    }
}
