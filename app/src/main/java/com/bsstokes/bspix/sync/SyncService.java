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
        instagramApi.getSelf()
                .map(new UnwrapResponse<InstagramApi.InstagramResponse<InstagramApi.User>>())
                .map(new UnwrapInstagramResponse<InstagramApi.User>())
                .observeOn(Schedulers.immediate())
                .subscribeOn(Schedulers.immediate())
                .subscribe(new BaseObserver<InstagramApi.User>() {
                    @Override public void onNext(InstagramApi.User user) {
                        final UserSyncer userSyncer = new UserSyncer(bsPixDatabase);
                        userSyncer.sync(user, true);
                    }
                });

        instagramApi.getRecentMedia()
                .map(new UnwrapResponse<InstagramApi.InstagramResponse<List<InstagramApi.Media>>>())
                .map(new UnwrapInstagramResponse<List<InstagramApi.Media>>())
                .observeOn(Schedulers.immediate())
                .subscribeOn(Schedulers.immediate())
                .subscribe(new BaseObserver<List<InstagramApi.Media>>() {
                    @Override public void onNext(List<InstagramApi.Media> mediaList) {
                        final MediaSyncer mediaSyncer = new MediaSyncer(bsPixDatabase);
                        mediaSyncer.sync(mediaList);
                    }
                });
    }
}
