package com.bsstokes.bspix.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import com.bsstokes.bspix.data.db.migrations.Migration;
import com.bsstokes.bspix.data.db.migrations.Migration001_CreateUsers;
import com.bsstokes.bspix.data.db.migrations.Migrator;

public class BsPixSQLiteOpenHelper extends SQLiteOpenHelper {

    public static BsPixSQLiteOpenHelper create(@NonNull Context context) {
        return new BsPixSQLiteOpenHelper(context, DATABASE_NAME);
    }

    public static BsPixSQLiteOpenHelper createInMemory(@NonNull Context context) {
        return new BsPixSQLiteOpenHelper(context, IN_MEMORY_DATABASE_NAME);
    }

    private static final String DATABASE_NAME = "bspix.db";
    private static final String IN_MEMORY_DATABASE_NAME = null;
    private static final SQLiteDatabase.CursorFactory NULL_CURSOR_FACTORY = null;

    private static final Migrator MIGRATOR = new Migrator(new Migration[]{
            new Migration001_CreateUsers()
    });

    private BsPixSQLiteOpenHelper(@NonNull Context context, String databaseName) {
        super(context, databaseName, NULL_CURSOR_FACTORY, MIGRATOR.getVersion());
    }

    @Override public void onCreate(SQLiteDatabase db) {
        MIGRATOR.migrate(db, 0, MIGRATOR.getVersion());
    }

    @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        MIGRATOR.migrate(db, oldVersion, newVersion);
    }
}
