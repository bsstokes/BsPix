package com.bsstokes.bspix.di;

import com.bsstokes.bspix.BsPixApplication;
import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.data.db.BsPixSQLiteOpenHelper;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import rx.schedulers.Schedulers;

@Module
class DbModule {
    @Provides @Singleton BsPixDatabase provideDatabase(BriteDatabase briteDatabase) {
        return new BsPixDatabase(briteDatabase);
    }

    @Provides @Singleton BriteDatabase provideBriteDatabase(BsPixSQLiteOpenHelper openHelper) {
        final SqlBrite sqlBrite = new SqlBrite.Builder().build();
        return sqlBrite.wrapDatabaseHelper(openHelper, Schedulers.io());
    }

    @Provides @Singleton
    BsPixSQLiteOpenHelper provideBsPixSQLiteOpenHelper(BsPixApplication application) {
        return BsPixSQLiteOpenHelper.create(application.getApplicationContext());
    }
}
