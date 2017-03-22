package com.bsstokes.bspix.app.user;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.data.User;
import com.bsstokes.bspix.rx.BaseObserver;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

class UserController {

    interface View {
        void setFullName(@Nullable String fullName);

        void setUserName(@Nullable String userName);

        void loadProfilePicture(@Nullable String profilePictureUrl);

        void finish();
    }

    @NonNull private final View view;
    @NonNull private final BsPixDatabase bsPixDatabase;
    @NonNull private final CompositeSubscription subscriptions = new CompositeSubscription();

    UserController(@NonNull View view, @NonNull BsPixDatabase bsPixDatabase) {
        this.view = view;
        this.bsPixDatabase = bsPixDatabase;
    }

    void load(@NonNull final String userId) {
        final Subscription getUser = bsPixDatabase.getUser(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<User>() {
                    @Override public void onNext(User user) {
                        if (null == user) {
                            onFail();
                        } else {
                            onLoad(user);
                        }
                    }
                });
        subscriptions.add(getUser);
    }

    void unload() {
        subscriptions.clear();
    }

    private void onLoad(@NonNull User user) {
        view.loadProfilePicture(user.profilePicture());
        view.setFullName(user.fullName());
        view.setUserName(user.userName());
    }

    private void onFail() {
        view.finish();
    }
}
