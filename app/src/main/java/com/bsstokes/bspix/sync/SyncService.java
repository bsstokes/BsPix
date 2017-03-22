package com.bsstokes.bspix.sync;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.bsstokes.bspix.api.InstagramApi;
import com.bsstokes.bspix.api.unwrap.UnwrapInstagramResponse;
import com.bsstokes.bspix.api.unwrap.UnwrapResponse;
import com.bsstokes.bspix.app.BsPixApplication;
import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.rx.BaseObserver;

import java.util.List;

import javax.inject.Inject;

import rx.schedulers.Schedulers;

public class SyncService extends IntentService {

    private static final String ACTION_SYNC_SELF = "com.bsstokes.bspix.sync.action.SYNC_SELF";
    private static final String ACTION_SYNC_USER = "com.bsstokes.bspix.sync.action.SYNC_USER";
    private static final String EXTRA_USER_ID = "USER_ID";

    private static final String TAG = "SyncService";

    @Inject BsPixDatabase bsPixDatabase;
    @Inject InstagramApi instagramApi;

    public SyncService() {
        super("SyncService");
    }

    public static void startActionSyncSelf(@NonNull Context context) {
        final Intent intent = createIntent(context, ACTION_SYNC_SELF);
        context.startService(intent);
    }

    public static void startActionSyncUser(@NonNull Context context, @NonNull String userId) {
        final Intent intent = createIntent(context, ACTION_SYNC_USER);
        intent.putExtra(EXTRA_USER_ID, userId);
        context.startService(intent);
    }

    @NonNull private static Intent createIntent(@NonNull Context context, @NonNull String action) {
        final Intent intent = new Intent(context, SyncService.class);
        intent.setAction(action);
        return intent;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        BsPixApplication.getBsPixApplication(this).getAppComponent().inject(this);

        if (null != intent) {
            final String action = intent.getAction();
            if (ACTION_SYNC_SELF.equals(action)) {
                handleActionSyncSelf();
            } else if (ACTION_SYNC_USER.equals(action)) {
                final String userId = intent.getStringExtra(EXTRA_USER_ID);
                handleActionSyncUser(userId);
            }
        }
    }

    private void handleActionSyncSelf() {
        final UserSyncer userSyncer = new UserSyncer(bsPixDatabase);
        instagramApi.getSelf()
                .observeOn(Schedulers.immediate())
                .subscribeOn(Schedulers.immediate())
                .map(new UnwrapResponse<InstagramApi.InstagramResponse<InstagramApi.User>>())
                .map(new UnwrapInstagramResponse<InstagramApi.User>())
                .subscribe(new BaseObserver<InstagramApi.User>() {
                    @Override public void onNext(InstagramApi.User user) {
                        userSyncer.sync(user, true);
                    }
                });

        final MediaSyncer mediaSyncer = new MediaSyncer(bsPixDatabase);
        instagramApi.getRecentMedia()
                .observeOn(Schedulers.immediate())
                .subscribeOn(Schedulers.immediate())
                .map(new UnwrapResponse<InstagramApi.InstagramResponse<List<InstagramApi.Media>>>())
                .map(new UnwrapInstagramResponse<List<InstagramApi.Media>>())
                .subscribe(new BaseObserver<List<InstagramApi.Media>>() {
                    @Override public void onNext(List<InstagramApi.Media> mediaList) {
                        mediaSyncer.sync(mediaList);
                    }
                });

        final FollowsSyncer followsSyncer = new FollowsSyncer(bsPixDatabase);
        instagramApi.getFollows()
                .observeOn(Schedulers.immediate())
                .subscribeOn(Schedulers.immediate())
                .map(new UnwrapResponse<InstagramApi.InstagramResponse<List<InstagramApi.FollowedUser>>>())
                .map(new UnwrapInstagramResponse<List<InstagramApi.FollowedUser>>())
                .subscribe(new BaseObserver<List<InstagramApi.FollowedUser>>() {
                    @Override public void onNext(List<InstagramApi.FollowedUser> followedUsers) {
                        followsSyncer.sync(followedUsers);
                    }
                });

        final LikedMediaSyncer likedMediaSyncer = new LikedMediaSyncer(bsPixDatabase);
        instagramApi.getLikedMedia()
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .map(new UnwrapResponse<InstagramApi.InstagramResponse<List<InstagramApi.Media>>>())
                .map(new UnwrapInstagramResponse<List<InstagramApi.Media>>())
                .subscribe(new BaseObserver<List<InstagramApi.Media>>() {
                    @Override public void onNext(List<InstagramApi.Media> likedMediaList) {
                        likedMediaSyncer.sync(likedMediaList);
                    }
                });
    }

    private void handleActionSyncUser(@Nullable final String userId) {
        if (null == userId) {
            Log.e(TAG, "handleActionSyncUser: No userId given");
            return;
        }

        final MediaSyncer mediaSyncer = new MediaSyncer(bsPixDatabase);
        instagramApi.getUserMedia(userId)
                .subscribeOn(Schedulers.immediate())
                .observeOn(Schedulers.immediate())
                .map(new UnwrapResponse<InstagramApi.InstagramResponse<List<InstagramApi.Media>>>())
                .map(new UnwrapInstagramResponse<List<InstagramApi.Media>>())
                .subscribe(new BaseObserver<List<InstagramApi.Media>>() {
                    @Override public void onNext(List<InstagramApi.Media> mediaList) {
                        mediaSyncer.sync(mediaList);
                    }
                });
    }
}
