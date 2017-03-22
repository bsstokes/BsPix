package com.bsstokes.bspix.data.db.migrations;

import android.database.sqlite.SQLiteDatabase;

public class Migration003_CreateLikedMedia implements Migration {
    @Override public void onUpgrade(SQLiteDatabase database) {
        final String CREATE_LIKED_MEDIA = "CREATE TABLE `liked_media` ("
                + "`_id` VARCHAR PRIMARY KEY"
                + ");";
        database.execSQL(CREATE_LIKED_MEDIA);

    }
}
