package com.bsstokes.bspix.data.db.mappings;

import android.content.ContentValues;
import android.database.Cursor;

import com.bsstokes.bspix.data.Media;

import rx.functions.Func1;

public class MediaMapping {
    public interface Table {
        String NAME = "media";
    }

    public interface Columns {
        String ID = "_id";
        String USER_ID = "userId";
        String USER_NAME = "userName";
        String CAPTION = "caption";
        String TYPE = "type";
        String TAGS = "tags";
        String LOCATION = "location";
        String LOW_RESOLUTION_URL = "lowResolutionUrl";
        String LOW_RESOLUTION_WIDTH = "lowResolutionWidth";
        String LOW_RESOLUTION_HEIGHT = "lowResolutionHeight";
        String THUMBNAIL_URL = "thumbnailUrl";
        String THUMBNAIL_WIDTH = "thumbnailWidth";
        String THUMBNAIL_HEIGHT = "thumbnailHeight";
        String STANDARD_URL = "standardResolutionUrl";
        String STANDARD_WIDTH = "standardResolutionWidth";
        String STANDARD_HEIGHT = "standardResolutionHeight";
    }

    public static final Func1<Cursor, Media> MAPPER = new Func1<Cursor, Media>() {
        @Override public Media call(Cursor cursor) {
            return toMedia(cursor);
        }
    };

    public static Media toMedia(Cursor cursor) {
        return Media.builder()
                .id(Db.getString(cursor, Columns.ID))
                .userId(Db.getString(cursor, Columns.USER_ID))
                .userName(Db.getString(cursor, Columns.USER_NAME))
                .caption(Db.getString(cursor, Columns.CAPTION))
                .type(Db.getString(cursor, Columns.TYPE))
                .tags(Db.getString(cursor, Columns.TAGS))
                .location(Db.getString(cursor, Columns.LOCATION))
                .lowResolutionUrl(Db.getString(cursor, Columns.LOW_RESOLUTION_URL))
                .lowResolutionWidth(Db.getInt(cursor, Columns.LOW_RESOLUTION_WIDTH))
                .lowResolutionHeight(Db.getInt(cursor, Columns.LOW_RESOLUTION_HEIGHT))
                .thumbnailUrl(Db.getString(cursor, Columns.THUMBNAIL_URL))
                .thumbnailWidth(Db.getInt(cursor, Columns.THUMBNAIL_WIDTH))
                .thumbnailHeight(Db.getInt(cursor, Columns.THUMBNAIL_HEIGHT))
                .standardResolutionUrl(Db.getString(cursor, Columns.STANDARD_URL))
                .standardResolutionWidth(Db.getInt(cursor, Columns.STANDARD_WIDTH))
                .standardResolutionHeight(Db.getInt(cursor, Columns.STANDARD_HEIGHT))
                .build();
    }

    public static ContentValues toContentValues(Media media) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(Columns.ID, media.id());
        contentValues.put(Columns.USER_ID, media.userId());
        contentValues.put(Columns.USER_NAME, media.userName());
        contentValues.put(Columns.CAPTION, media.caption());
        contentValues.put(Columns.TYPE, media.type());
        contentValues.put(Columns.TAGS, media.tags());
        contentValues.put(Columns.LOCATION, media.location());
        contentValues.put(Columns.LOW_RESOLUTION_URL, media.lowResolutionUrl());
        contentValues.put(Columns.LOW_RESOLUTION_WIDTH, media.lowResolutionWidth());
        contentValues.put(Columns.LOW_RESOLUTION_HEIGHT, media.lowResolutionHeight());
        contentValues.put(Columns.THUMBNAIL_URL, media.thumbnailUrl());
        contentValues.put(Columns.THUMBNAIL_WIDTH, media.thumbnailWidth());
        contentValues.put(Columns.THUMBNAIL_HEIGHT, media.thumbnailHeight());
        contentValues.put(Columns.STANDARD_URL, media.standardResolutionUrl());
        contentValues.put(Columns.STANDARD_WIDTH, media.standardResolutionWidth());
        contentValues.put(Columns.STANDARD_HEIGHT, media.standardResolutionHeight());
        return contentValues;
    }
}
