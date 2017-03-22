package com.bsstokes.bspix.app.media_item;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bsstokes.bspix.api.InstagramApi;
import com.bsstokes.bspix.api.unwrap.UnwrapInstagramMeta;
import com.bsstokes.bspix.api.unwrap.UnwrapResponse;
import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.rx.BaseObserver;

import retrofit2.Response;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

class LikeAction {

    @NonNull private final BsPixDatabase bsPixDatabase;
    @NonNull private final InstagramApi instagramApi;
    @NonNull private final CompositeSubscription subscriptions = new CompositeSubscription();

    LikeAction(@NonNull BsPixDatabase bsPixDatabase, @NonNull InstagramApi instagramApi) {
        this.bsPixDatabase = bsPixDatabase;
        this.instagramApi = instagramApi;
    }

    void like(@Nullable String mediaItemId) {
        if (null != mediaItemId) {
            like(mediaItemId, true, instagramApi.postLike(mediaItemId));
        }
    }

    void unlike(@Nullable String mediaItemId) {
        if (null != mediaItemId) {
            like(mediaItemId, false, instagramApi.deleteLike(mediaItemId));
        }
    }

    private void like(
            @NonNull final String mediaItemId,
            final boolean like,
            @NonNull final Observable<Response<InstagramApi.InstagramResponse<InstagramApi.Ignore>>> observable) {

        final Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .map(new UnwrapResponse<InstagramApi.InstagramResponse<InstagramApi.Ignore>>())
                .map(new UnwrapInstagramMeta<InstagramApi.Ignore>())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<InstagramApi.Meta>() {
                    @Override public void onNext(InstagramApi.Meta meta) {
                        onSuccess(mediaItemId, like);
                    }
                });
        subscriptions.add(subscription);
    }

    private void onSuccess(@NonNull String mediaItemId, boolean liked) {
        if (liked) {
            bsPixDatabase.setMediaAsLiked(mediaItemId);
        } else {
            bsPixDatabase.setMediaAsNotLiked(mediaItemId);
        }
    }
}
