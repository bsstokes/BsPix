package com.bsstokes.bspix.app.media_item;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.data.Media;
import com.bsstokes.bspix.rx.BaseObserver;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

class MediaItemController {

    interface View {
        void loadPhoto(@NonNull String photoUrl);

        void setUserName(@Nullable String userName);

        void setCaption(@Nullable String caption);

        void setLocation(@Nullable String location);

        void logErrorAndFinish(@NonNull String message);
    }

    @NonNull private final View view;
    @NonNull private final BsPixDatabase bsPixDatabase;
    @NonNull private final CompositeSubscription subscriptions = new CompositeSubscription();

    MediaItemController(@NonNull View view, @NonNull BsPixDatabase bsPixDatabase) {
        this.view = view;
        this.bsPixDatabase = bsPixDatabase;
    }

    void load(@Nullable String mediaItemId) {
        if (null == mediaItemId) {
            view.logErrorAndFinish("No media item ID given");
            return;
        }

        final Subscription subscription = bsPixDatabase.getMedia(mediaItemId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Media>() {
                    @Override public void onNext(Media media) {
                        onLoad(media);
                    }
                });
        subscriptions.add(subscription);
    }

    void unload() {
        subscriptions.clear();
    }

    private void onLoad(@NonNull Media media) {
        final String photoUrl = media.standardResolutionUrl();
        if (null != photoUrl) {
            view.loadPhoto(photoUrl);
        }
        view.setUserName(media.userName());
        view.setCaption(media.caption());
        view.setLocation(media.location());
    }
}
