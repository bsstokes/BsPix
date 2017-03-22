package com.bsstokes.bspix.data;

import android.database.Cursor;
import android.support.annotation.NonNull;

import com.bsstokes.bspix.data.db.mappings.LikedMediaMapping;
import com.bsstokes.bspix.data.db.mappings.MediaMapping;
import com.bsstokes.bspix.data.db.mappings.UsersMapping;
import com.squareup.sqlbrite.BriteDatabase;

import java.util.Arrays;
import java.util.List;

import rx.Observable;
import rx.functions.Func1;

import static android.database.sqlite.SQLiteDatabase.CONFLICT_REPLACE;

public class BsPixDatabase {

    @NonNull private final BriteDatabase briteDatabase;

    public BsPixDatabase(@NonNull BriteDatabase briteDatabase) {
        this.briteDatabase = briteDatabase;
    }

    public void putUser(@NonNull User user) {
        briteDatabase.insert(UsersMapping.Table.NAME, UsersMapping.toContentValues(user), CONFLICT_REPLACE);
    }

    public void putUsers(@NonNull User... users) {
        putUsers(Arrays.asList(users));
    }

    public void putUsers(@NonNull List<User> users) {
        final BriteDatabase.Transaction transaction = briteDatabase.newTransaction();
        try {
            for (final User user : users) {
                briteDatabase.insert(UsersMapping.Table.NAME, UsersMapping.toContentValues(user), CONFLICT_REPLACE);
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
    }

    public Observable<List<User>> getUsers() {
        final String query = "SELECT * FROM " + UsersMapping.Table.NAME;
        return briteDatabase.createQuery(UsersMapping.Table.NAME, query)
                .mapToList(UsersMapping.MAPPER);
    }

    public Observable<List<User>> getFollows() {
        final String query = ""
                + "SELECT *"
                + " FROM " + UsersMapping.Table.NAME
                + " WHERE " + UsersMapping.Columns.SELF + "=0";
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

    public void putMedia(@NonNull Media... mediaList) {
        putMedia(Arrays.asList(mediaList));
    }

    public void putMedia(@NonNull List<Media> mediaList) {
        final BriteDatabase.Transaction transaction = briteDatabase.newTransaction();
        try {
            for (final Media media : mediaList) {
                briteDatabase.insert(MediaMapping.Table.NAME, MediaMapping.toContentValues(media), CONFLICT_REPLACE);
            }
            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
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

    public Observable<List<Media>> getMediaForSelf() {
        final String usersSelfField = UsersMapping.Table.NAME + "." + UsersMapping.Columns.SELF;
        final String usersIdField = UsersMapping.Table.NAME + "." + UsersMapping.Columns.ID;
        final String mediaAllFields = MediaMapping.Table.NAME + ".*";
        final String mediaUserIdField = MediaMapping.Table.NAME + "." + MediaMapping.Columns.USER_ID;

        final String query = ""
                + "SELECT " + mediaAllFields
                + " FROM " + MediaMapping.Table.NAME
                + " JOIN " + UsersMapping.Table.NAME
                + " WHERE " + usersSelfField + "=1"
                + " AND " + usersIdField + "=" + mediaUserIdField;
        return briteDatabase.createQuery(MediaMapping.Table.NAME, query)
                .mapToList(MediaMapping.MAPPER);
    }

    public Observable<List<Media>> getMediaForUser(String userId) {
        final String query = ""
                + "SELECT *"
                + " FROM " + MediaMapping.Table.NAME
                + " WHERE " + MediaMapping.Columns.USER_ID + "=?";
        return briteDatabase.createQuery(MediaMapping.Table.NAME, query, userId)
                .mapToList(MediaMapping.MAPPER);

    }

    public Observable<List<Media>> getLikedMedia() {
        final String mediaAllFields = MediaMapping.Table.NAME + ".*";
        final String mediaId = MediaMapping.Table.NAME + "." + MediaMapping.Columns.ID;
        final String likedMediaId = LikedMediaMapping.Table.NAME + "." + LikedMediaMapping.Columns.ID;

        final List<String> tables = Arrays.asList(MediaMapping.Table.NAME, LikedMediaMapping.Table.NAME);

        final String query = ""
                + "SELECT " + mediaAllFields
                + " FROM " + MediaMapping.Table.NAME
                + " JOIN " + LikedMediaMapping.Table.NAME
                + " WHERE " + mediaId + "=" + likedMediaId;
        return briteDatabase.createQuery(tables, query)
                .mapToList(MediaMapping.MAPPER);
    }

    public Observable<Boolean> isLikedMedia(@NonNull String mediaId) {
        final List<String> tables = Arrays.asList(MediaMapping.Table.NAME, LikedMediaMapping.Table.NAME);
        final String query = ""
                + "SELECT *"
                + " FROM " + LikedMediaMapping.Table.NAME
                + " WHERE " + LikedMediaMapping.Columns.ID + "=?";
        return briteDatabase.createQuery(tables, query, mediaId)
                .mapToOneOrDefault(new Func1<Cursor, Boolean>() {
                    @Override public Boolean call(Cursor cursor) {
                        // If there is an entry at all, return true
                        return true;
                    }
                }, Boolean.FALSE);
    }

    public void putLikedMedia(@NonNull Media... mediaList) {
        putLikedMedia(Arrays.asList(mediaList));
    }

    public void putLikedMedia(@NonNull List<Media> mediaList) {
        final BriteDatabase.Transaction transaction = briteDatabase.newTransaction();
        try {
            briteDatabase.delete(LikedMediaMapping.Table.NAME, "1=1");

            for (final Media media : mediaList) {
                // Insert media into table
                briteDatabase.insert(MediaMapping.Table.NAME, MediaMapping.toContentValues(media), CONFLICT_REPLACE);
                // Insert entry into liked media table
                briteDatabase.insert(LikedMediaMapping.Table.NAME, LikedMediaMapping.toContentValues(media.id()), CONFLICT_REPLACE);
            }

            transaction.markSuccessful();
        } finally {
            transaction.end();
        }
    }

    public static final User NO_USER = User.builder().build();
    public static final Media NO_MEDIA = Media.builder().build();
}
