package com.bsstokes.bspix.data.db.mappings;

import android.content.ContentValues;
import android.database.Cursor;

import com.bsstokes.bspix.data.User;

import rx.functions.Func1;

public class UsersMapping {
    public interface Table {
        String NAME = "users";
    }

    public interface Columns {
        String ID = "_id";
        String SELF = "self";
        String USER_NAME = "userName";
        String FULL_NAME = "fullName";
        String PROFILE_PICTURE = "profilePicture";
        String BIO = "bio";
        String WEBSITE = "website";
        String MEDIA_COUNT = "mediaCount";
        String FOLLOWS_COUNT = "followsCount";
        String FOLLOWED_BY_COUNT = "followedByCount";
        String PRIORITY = "priority";
    }

    public static final Func1<Cursor, User> MAPPER = new Func1<Cursor, User>() {
        @Override public User call(Cursor cursor) {
            return toUser(cursor);
        }
    };

    public static User toUser(Cursor cursor) {
        return User.builder()
                .id(Db.getString(cursor, Columns.ID))
                .self(Db.getBoolean(cursor, Columns.SELF))
                .userName(Db.getString(cursor, Columns.USER_NAME))
                .fullName(Db.getString(cursor, Columns.FULL_NAME))
                .profilePicture(Db.getString(cursor, Columns.PROFILE_PICTURE))
                .bio(Db.getString(cursor, Columns.BIO))
                .website(Db.getString(cursor, Columns.WEBSITE))
                .mediaCount(Db.getInt(cursor, Columns.MEDIA_COUNT))
                .followsCount(Db.getInt(cursor, Columns.FOLLOWS_COUNT))
                .followedByCount(Db.getInt(cursor, Columns.FOLLOWED_BY_COUNT))
                .build();
    }

    public static ContentValues toContentValues(User user) {
        final ContentValues contentValues = new ContentValues();
        contentValues.put(Columns.ID, user.id());
        contentValues.put(Columns.SELF, user.self());
        contentValues.put(Columns.USER_NAME, user.userName());
        contentValues.put(Columns.FULL_NAME, user.fullName());
        contentValues.put(Columns.PROFILE_PICTURE, user.profilePicture());
        contentValues.put(Columns.BIO, user.bio());
        contentValues.put(Columns.WEBSITE, user.website());
        contentValues.put(Columns.MEDIA_COUNT, user.mediaCount());
        contentValues.put(Columns.FOLLOWS_COUNT, user.followsCount());
        contentValues.put(Columns.FOLLOWED_BY_COUNT, user.followedByCount());
        return contentValues;
    }
}
