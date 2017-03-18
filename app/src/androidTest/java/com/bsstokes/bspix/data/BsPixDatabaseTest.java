package com.bsstokes.bspix.data;

import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.bsstokes.bspix.data.db.BsPixSQLiteOpenHelper;
import com.bsstokes.bspix.data.db.mappings.UsersMapping;
import com.squareup.sqlbrite.BriteDatabase;
import com.squareup.sqlbrite.SqlBrite;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import rx.Observable;
import rx.schedulers.Schedulers;

import static com.bsstokes.bspix.data.BsPixDatabase.NO_USER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

// Test my implementation of an SqlBrite-backed database. This also tests the mappings between the
// data classes and the SQLite Cursor and ContentValues.
@RunWith(AndroidJUnit4.class)
public class BsPixDatabaseTest {

    private BsPixDatabase database;

    @Before
    public void setUp() {
        final BsPixSQLiteOpenHelper openHelper = BsPixSQLiteOpenHelper.createInMemory(InstrumentationRegistry.getTargetContext());
        final SqlBrite sqlBrite = new SqlBrite.Builder().build();
        final BriteDatabase briteDatabase = sqlBrite.wrapDatabaseHelper(openHelper, Schedulers.immediate());
        database = new BsPixDatabase(briteDatabase);

        // Clear users
        briteDatabase.delete(UsersMapping.Table.NAME, "1=1");
    }

    private final User user = User.builder()
            .id("1234")
            .userName("bsstokes")
            .fullName("Brian Stokes")
            .profilePicture("http://profile.picture.com")
            .bio("I'm awesome")
            .website("http://bsstokes.com")
            .mediaCount(5)
            .followsCount(100)
            .followedByCount(1)
            .build();

    private static <T> T sync(Observable<T> observable) {
        return observable.toBlocking().first();
    }

    @Test
    public void an_empty_Users_table_returns_an_empty_list() {
        final List<User> users = sync(database.getUsers());
        assertTrue(users.isEmpty());
    }

    @Test
    public void calling_getUser_with_nonexistent_id_returns_null() {
        final User user = sync(database.getUser("no id"));
        assertEquals(NO_USER, user);
    }

    @Test
    public void can_get_a_User_by_id() {
        database.putUser(user);
        assertEquals(user, sync(database.getUser(user.id())));
    }

    @Test
    public void when_two_Users_are_inserted_separately_getUsers_returns_both() {
        final User user1 = user.toBuilder().id("1").build();
        final User user2 = user.toBuilder().id("2").build();

        database.putUser(user1);
        database.putUser(user2);

        assertThat(sync(database.getUsers()), contains(user1, user2));
    }
}
