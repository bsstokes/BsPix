package com.bsstokes.bspix.data.db.migrations;

import android.database.sqlite.SQLiteDatabase;

public class Migration001_CreateUsers implements Migration {
    @Override public void onUpgrade(SQLiteDatabase database) {
        final String CREATE_SKILLS = "CREATE TABLE `users` ("
                + "`_id` VARCHAR PRIMARY KEY,"
                + "`userName` VARCHAR,"
                + "`fullName` VARCHAR,"
                + "`profilePicture` VARCHAR,"
                + "`bio` VARCHAR,"
                + "`website` VARCHAR,"
                + "`mediaCount` INTEGER,"
                + "`followsCount` INTEGER,"
                + "`followedByCount` INTEGER,"
                + "`priority` INTEGER"
                + ");";
        database.execSQL(CREATE_SKILLS);
    }
}
