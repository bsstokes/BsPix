package com.bsstokes.bspix.data.db.mappings;

import android.content.ContentValues;

public class LikedMediaMapping {
    public interface Table {
        String NAME = "liked_media";
    }

    public interface Columns {
        String ID = "_id";
    }

    public static ContentValues toContentValues(String id) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(LikedMediaMapping.Columns.ID, id);
        return contentValues;
    }
}
