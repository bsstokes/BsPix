package com.bsstokes.bspix.sync;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.bsstokes.bspix.api.InstagramApi;
import com.bsstokes.bspix.api.UnwrapInstagramResponse;
import com.bsstokes.bspix.api.UnwrapResponse;
import com.bsstokes.bspix.app.BsPixApplication;
import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.rx.BaseObserver;

import java.util.List;

import javax.inject.Inject;

import rx.schedulers.Schedulers;

public class SyncService extends IntentService {

    private static final String ACTION_SYNC_SELF = "com.bsstokes.bspix.sync.action.SYNC_SELF";

    @Inject BsPixDatabase bsPixDatabase;
    @Inject InstagramApi instagramApi;

    public SyncService() {
        super("SyncService");
    }

    public static void startActionSyncSelf(Context context) {
        Intent intent = new Intent(context, SyncService.class);
        intent.setAction(ACTION_SYNC_SELF);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        BsPixApplication.getBsPixApplication(this).getAppComponent().inject(this);

        if (null != intent) {
            final String action = intent.getAction();
            if (ACTION_SYNC_SELF.equals(action)) {
                handleActionSyncSelf();
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
}
