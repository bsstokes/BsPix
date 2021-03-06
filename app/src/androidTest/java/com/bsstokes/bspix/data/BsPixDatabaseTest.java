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

import static com.bsstokes.bspix.data.BsPixDatabase.NO_MEDIA;
import static com.bsstokes.bspix.data.BsPixDatabase.NO_USER;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assume.assumeFalse;
import static org.junit.Assume.assumeThat;
import static org.junit.Assume.assumeTrue;

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
            .self(true)
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

        database.putUsers(user1, user2);

        assertThat(sync(database.getUsers()), contains(user1, user2));
    }

    @Test
    public void calling_getSelf_on_empty_database_returns_NO_USER() {
        assertEquals(NO_USER, sync(database.getSelf()));
    }

    @Test
    public void calling_getSelf_when_there_is_no_self_returns_NO_USER() {
        database.putUser(user.toBuilder().self(false).build());
        assertEquals(NO_USER, sync(database.getSelf()));
    }

    @Test
    public void calling_getSelf_only_returns_self() {
        final User user1NotSelf = user.toBuilder().id("1").self(false).build();
        final User user2Self = user.toBuilder().id("2").self(true).build();

        database.putUsers(user1NotSelf, user2Self);

        final User foundUser = sync(database.getSelf());
        assertEquals(user2Self, foundUser);
    }

    @Test
    public void getFollows_only_returns_non_self_users() {
        final User self1 = user.toBuilder().id("1").self(true).build();
        final User follow2 = user.toBuilder().id("2").self(false).build();
        final User follow3 = user.toBuilder().id("3").self(false).build();

        database.putUsers(self1, follow2, follow3);

        final List<User> follows = sync(database.getFollows());
        assertThat(follows, hasSize(2));
        assertThat(follows, containsInAnyOrder(follow2, follow3));
    }

    private final Media media = Media.builder()
            .id("456")
            .userId("789")
            .userName("media user name")
            .caption("My caption")
            .type("image")
            .tags("tag1,tag2")
            .location("My location")
            .lowResolutionUrl("http://low-resolution-url.com/")
            .lowResolutionWidth(100)
            .lowResolutionHeight(200)
            .thumbnailUrl("http://thumbnail-url.com/")
            .thumbnailWidth(50)
            .thumbnailHeight(100)
            .standardResolutionUrl("http://standard-resolution-url.com/")
            .standardResolutionWidth(400)
            .standardResolutionHeight(800)
            .build();

    @Test
    public void an_empty_Media_table_returns_an_empty_list() {
        final List<Media> mediaList = sync(database.getMediaForUser(media.userId()));
        assertTrue(mediaList.isEmpty());
    }

    @Test
    public void getMediaForUser_returns_only_Media_for_userId() {
        final Media myMedia1 = media.toBuilder().id("1").userId("1").build();
        final Media myMedia2 = media.toBuilder().id("2").userId("1").build();
        final Media notMyMedia3 = media.toBuilder().id("3").userId("2").build();

        database.putMedia(myMedia1, myMedia2, notMyMedia3);

        final List<Media> mediaList = sync(database.getMediaForUser("1"));
        assertEquals(2, mediaList.size());
        assertThat(mediaList, contains(myMedia1, myMedia2));
    }

    @Test
    public void getMedia_returns_correct_Media() {
        final Media myMedia1 = media.toBuilder().id("1").userId("1").build();
        final Media myMedia2 = media.toBuilder().id("2").userId("1").build();

        database.putMedia(myMedia1, myMedia2);

        final Media foundMedia = sync(database.getMedia("2"));
        assertEquals(myMedia2, foundMedia);
    }

    @Test
    public void when_no_Media_getMedia_returns_NO_MEDIA() {
        assertEquals(NO_MEDIA, sync(database.getMedia("1")));
    }

    @Test
    public void getMedia_returns_NO_MEDIA_when_not_found() {
        database.putMedia(media.toBuilder().id("2").build());
        assertEquals(NO_MEDIA, sync(database.getMedia("1")));
    }

    @Test
    public void getLikedMedia_returns_empty_list_when_no_likes() {
        assertTrue(sync(database.getLikedMedia()).isEmpty());
    }

    @Test
    public void getLikedMedia_returns_only_liked_media() {
        final Media media1Liked = media.toBuilder().id("1").build();
        final Media media2Liked = media.toBuilder().id("2").build();
        database.putLikedMedia(media1Liked, media2Liked);

        final Media media3NotLiked = media.toBuilder().id("3").build();
        database.putMedia(media3NotLiked);

        final List<Media> likedMediaList = sync(database.getLikedMedia());
        assertThat(likedMediaList, hasSize(2));
        assertThat(likedMediaList, containsInAnyOrder(media1Liked, media2Liked));
    }

    @Test
    public void putLikedMedia_resets_liked_media_when_called_again() {
        final Media media1Liked = media.toBuilder().id("1").build();
        final Media media2Liked = media.toBuilder().id("2").build();
        database.putLikedMedia(media1Liked, media2Liked);

        final List<Media> firstLikedMediaList = sync(database.getLikedMedia());
        assertThat(firstLikedMediaList, hasSize(2));
        assertThat(firstLikedMediaList, containsInAnyOrder(media1Liked, media2Liked));

        final Media media3Liked = media.toBuilder().id("3").build();
        final Media media4Liked = media.toBuilder().id("4").build();
        database.putLikedMedia(media3Liked, media4Liked);

        final List<Media> secondLikedMediaList = sync(database.getLikedMedia());
        assertThat(secondLikedMediaList, hasSize(2));
        assertThat(secondLikedMediaList, containsInAnyOrder(media3Liked, media4Liked));
    }

    @Test
    public void isLikedMedia_returns_false_if_the_Media_is_not_liked() {
        final String HATED_ID = "1";
        final Media hatedMedia = media.toBuilder().id(HATED_ID).build();
        database.putMedia(hatedMedia);
        assertFalse(sync(database.isLikedMedia(HATED_ID)));
    }

    @Test
    public void isLikedMedia_returns_true_if_the_Media_is_not_liked() {
        final String LIKED_ID = "3";
        final Media likedMedia = media.toBuilder().id(LIKED_ID).build();
        database.putLikedMedia(likedMedia);
        assertTrue(sync(database.isLikedMedia(LIKED_ID)));
    }

    @Test
    public void can_set_not_liked_media_as_liked() {
        final String MEDIA_ID = "123";
        final Media newMedia = media.toBuilder().id(MEDIA_ID).build();

        database.putMedia(newMedia);
        assumeFalse(sync(database.isLikedMedia(MEDIA_ID)));

        database.setMediaAsLiked(MEDIA_ID);
        assertTrue(sync(database.isLikedMedia(MEDIA_ID)));
    }

    @Test
    public void can_set_liked_media_as_not_liked() {
        final String MEDIA_ID = "321";
        final Media newMedia = media.toBuilder().id(MEDIA_ID).build();

        database.putLikedMedia(newMedia);
        assumeTrue(sync(database.isLikedMedia(MEDIA_ID)));

        database.setMediaAsNotLiked(MEDIA_ID);
        assertFalse(sync(database.isLikedMedia(MEDIA_ID)));
    }

    @Test
    public void deleteAllUsers_deletes_all_users() {
        final User user1 = user.toBuilder().id("1").self(true).build();
        final User user2 = user.toBuilder().id("2").self(false).build();
        database.putUsers(user1, user2);

        assumeThat(sync(database.getUsers()), containsInAnyOrder(user1, user2));

        database.deleteAllUsers();
        assertThat(sync(database.getUsers()), hasSize(0));
    }

    @Test
    public void deleteAllMedia_deletes_all_media() {
        final Media media1 = media.toBuilder().id("1").build();
        final Media media2 = media.toBuilder().id("2").build();

        database.putMedia(media1, media2);
        assumeThat(sync(database.getAllMedia()), containsInAnyOrder(media1, media2));

        database.deleteAllMedia();
        assertThat(sync(database.getAllMedia()), hasSize(0));
    }

    @Test
    public void deleteAllLikedMedia_deletes_all_liked_media() {
        final Media likedMedia = media.toBuilder().id("1").build();

        database.putLikedMedia(likedMedia);
        assumeThat(sync(database.getLikedMedia()), containsInAnyOrder(likedMedia));

        database.deleteAllLikedMedia();
        assertThat(sync(database.getLikedMedia()), hasSize(0));
    }
}
