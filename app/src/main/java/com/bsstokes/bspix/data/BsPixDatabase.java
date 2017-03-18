package com.bsstokes.bspix.data;

import android.support.annotation.NonNull;

import com.bsstokes.bspix.data.db.mappings.MediaMapping;
import com.bsstokes.bspix.data.db.mappings.UsersMapping;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.List;

import rx.Observable;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;

public class BsPixDatabase {

    @NonNull private final BriteDatabase briteDatabase;

    public BsPixDatabase(@NonNull BriteDatabase briteDatabase) {
        this.briteDatabase = briteDatabase;
    }

    public void putUser(@NonNull User user) {
        briteDatabase.insert(UsersMapping.Table.NAME, UsersMapping.toContentValues(user), CONFLICT_REPLACE);
    }

    public Observable<List<User>> getUsers() {
        final String query = "SELECT * FROM " + UsersMapping.Table.NAME;
        return briteDatabase.createQuery(UsersMapping.Table.NAME, query)
                .mapToList(UsersMapping.MAPPER);
    }

    public Observable<User> getSelf() {
        final String query = ""
                + "SELECT *"
                + " FROM " + UsersMapping.Table.NAME
                + " WHERE " + UsersMapping.Columns.SELF + "=1"
                + " LIMIT 1";
        return briteDatabase.createQuery(UsersMapping.Table.NAME, query)
                .mapToOneOrDefault(UsersMapping.MAPPER, NO_USER);
    }

    public Observable<User> getUser(String userId) {
        final String query = ""
                + "SELECT *"
                + " FROM " + UsersMapping.Table.NAME
                + " WHERE " + UsersMapping.Columns.ID + "=?"
                + " LIMIT 1";
        return briteDatabase.createQuery(UsersMapping.Table.NAME, query, userId)
                .mapToOneOrDefault(UsersMapping.MAPPER, NO_USER);
    }

    public void putMedia(@NonNull Media media) {
        briteDatabase.insert(MediaMapping.Table.NAME, MediaMapping.toContentValues(media), CONFLICT_REPLACE);
    }

    public Observable<Media> getMedia(String mediaId) {
        final String query = ""
                + "SELECT *"
                + " FROM " + MediaMapping.Table.NAME
                + " WHERE " + MediaMapping.Columns.ID + "=?"
                + " LIMIT 1";
        return briteDatabase.createQuery(MediaMapping.Table.NAME, query, mediaId)
                .mapToOneOrDefault(MediaMapping.MAPPER, NO_MEDIA);
    }

    public Observable<List<Media>> getMediaForUser(String userId) {
        final String query = ""
                + "SELECT *"
                + " FROM " + MediaMapping.Table.NAME
                + " WHERE " + MediaMapping.Columns.USER_ID + "=?";
        return briteDatabase.createQuery(MediaMapping.Table.NAME, query, userId)
                .mapToList(MediaMapping.MAPPER);

    }

    public static final User NO_USER = User.builder().build();
    public static final Media NO_MEDIA = Media.builder().build();
}
