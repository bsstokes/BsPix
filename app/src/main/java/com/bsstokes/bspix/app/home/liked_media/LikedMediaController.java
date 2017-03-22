package com.bsstokes.bspix.app.home.liked_media;

import android.support.annotation.NonNull;

import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.data.Media;
import com.bsstokes.bspix.rx.BaseObserver;

import java.util.List;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

class LikedMediaController {

    interface View {
        void launchMediaItem(String mediaItemId);

        void setMedia(@NonNull List<Media> mediaList);
    }

    @NonNull private final View view;
    @NonNull private final BsPixDatabase bsPixDatabase;
    @NonNull private final CompositeSubscription subscriptions = new CompositeSubscription();

    LikedMediaController(@NonNull View view, @NonNull BsPixDatabase bsPixDatabase) {
        this.view = view;
        this.bsPixDatabase = bsPixDatabase;
    }

    void load() {
        loadMedia(bsPixDatabase.getLikedMedia());
    }

    private void loadMedia(Observable<List<Media>> mediaListObservable) {
        final Subscription subscription = mediaListObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<List<Media>>() {
                    @Override public void onNext(List<Media> mediaList) {
                        view.setMedia(mediaList);
                    }
                });
        subscriptions.add(subscription);
    }

    void unload() {
        subscriptions.clear();
    }

    void onClickMediaItem(@NonNull String mediaItemId) {
        view.launchMediaItem(mediaItemId);
    }
}
