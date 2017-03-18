package com.bsstokes.bspix.data.db.migrations;

import android.database.sqlite.SQLiteDatabase;

public class Migration002_CreateMedia implements Migration {
    @Override public void onUpgrade(SQLiteDatabase database) {
        final String CREATE_MEDIA = "CREATE TABLE `media` ("
                + "`_id` VARCHAR PRIMARY KEY,"
                + "`userId` VARCHAR,"
                + "`caption` VARCHAR,"
                + "`type` VARCHAR,"
                + "`tags` VARCHAR,"
                + "`location` VARCHAR,"
                + "`lowResolutionUrl` VARCHAR,"
                + "`lowResolutionWidth` INTEGER,"
                + "`lowResolutionHeight` INTEGER,"
                + "`thumbnailUrl` VARCHAR,"
                + "`thumbnailWidth` INTEGER,"
                + "`thumbnailHeight` INTEGER,"
                + "`standardResolutionUrl` VARCHAR,"
                + "`standardResolutionWidth` INTEGER,"
                + "`standardResolutionHeight` INTEGER"
                + ");";
        database.execSQL(CREATE_MEDIA);
    }
}
