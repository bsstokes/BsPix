package com.bsstokes.bspix.sync;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.bsstokes.bspix.api.InstagramApi;
import com.bsstokes.bspix.api.UnwrapInstagramResponse;
import com.bsstokes.bspix.api.UnwrapResponse;
import com.bsstokes.bspix.app.BsPixApplication;
import com.bsstokes.bspix.data.BsPixDatabase;
import com.crashlytics.android.Crashlytics;

import javax.inject.Inject;

import rx.Observer;
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
                .observeOn(Schedulers.immediate())
                .subscribeOn(Schedulers.immediate())
                .map(new UnwrapResponse<InstagramApi.InstagramResponse<InstagramApi.User>>())
                .map(new UnwrapInstagramResponse<InstagramApi.User>())
                .subscribe(new Observer<InstagramApi.User>() {
                    @Override public void onCompleted() {

                    }

                    @Override public void onError(Throwable e) {
                        Crashlytics.logException(e);
                    }

                    @Override public void onNext(InstagramApi.User user) {
                        final UserSyncer userSyncer = new UserSyncer(bsPixDatabase);
                        userSyncer.sync(user);
                    }
                });
    }
}
