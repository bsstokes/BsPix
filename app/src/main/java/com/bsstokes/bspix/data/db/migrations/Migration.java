package com.bsstokes.bspix.data.db.migrations;

import android.database.sqlite.SQLiteDatabase;

public interface Migration {
    void onUpgrade(SQLiteDatabase database);
}
