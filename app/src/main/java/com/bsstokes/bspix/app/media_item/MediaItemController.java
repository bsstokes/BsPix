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

        void setIsLiked(boolean isLiked);
    }

    @NonNull private final View view;
    @NonNull private final BsPixDatabase bsPixDatabase;
    @NonNull private final LikeAction likeAction;
    @NonNull private final CompositeSubscription subscriptions = new CompositeSubscription();

    private boolean isLikedMedia = false;

    MediaItemController(@NonNull View view, @NonNull BsPixDatabase bsPixDatabase, @NonNull LikeAction likeAction) {
        this.view = view;
        this.bsPixDatabase = bsPixDatabase;
        this.likeAction = likeAction;
    }

    void load(@Nullable String mediaItemId) {
        if (null == mediaItemId) {
            view.logErrorAndFinish("No media item ID given");
            return;
        }

        final Subscription getMedia = bsPixDatabase.getMedia(mediaItemId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Media>() {
                    @Override public void onNext(Media media) {
                        onLoad(media);
                    }
                });
        subscriptions.add(getMedia);

        final Subscription isLikedMedia = bsPixDatabase.isLikedMedia(mediaItemId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<Boolean>() {
                    @Override public void onNext(Boolean isLiked) {
                        onLoadIsLikedMedia(isLiked);
                    }
                });
        subscriptions.add(isLikedMedia);
    }

    void unload() {
        subscriptions.clear();
    }

    void onClickLikeButton(@Nullable String mediaItemId) {
        if (isLikedMedia) {
            likeAction.unlike(mediaItemId);
        } else {
            likeAction.like(mediaItemId);
        }
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

    private void onLoadIsLikedMedia(boolean isLiked) {
        this.isLikedMedia = isLiked;
        view.setIsLiked(isLiked);
    }
}
