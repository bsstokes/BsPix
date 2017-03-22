package com.bsstokes.bspix.app.user;

import android.support.annotation.NonNull;

import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.data.User;
import com.bsstokes.bspix.rx.BaseObserver;

import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

class UserController {

    interface View {

    }

    @NonNull private final View view;
    @NonNull private final BsPixDatabase bsPixDatabase;
    @NonNull private final CompositeSubscription subscriptions = new CompositeSubscription();

    UserController(@NonNull View view, @NonNull BsPixDatabase bsPixDatabase) {
        this.view = view;
        this.bsPixDatabase = bsPixDatabase;
    }

    void load(@NonNull String userId) {
        bsPixDatabase.getUser(userId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<User>() {
                    @Override public void onNext(User user) {
                        onLoad(user);
                    }
                });
    }

    void unload() {
        subscriptions.clear();
    }

    private void onLoad(User user) {

    }
}
