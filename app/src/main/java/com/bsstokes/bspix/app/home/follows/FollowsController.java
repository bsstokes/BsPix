package com.bsstokes.bspix.app.home.follows;

import android.support.annotation.NonNull;

import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.data.User;
import com.bsstokes.bspix.rx.BaseObserver;

import java.util.List;

import rx.Scheduler;
import rx.subscriptions.CompositeSubscription;

class FollowsController {

    interface View {
        void setFollows(@NonNull List<User> follows);

        void navigateToUser(@NonNull String userId);
    }

    @NonNull private final View view;
    @NonNull private final BsPixDatabase bsPixDatabase;
    @NonNull private final CompositeSubscription subscriptions = new CompositeSubscription();

    FollowsController(@NonNull View view, @NonNull BsPixDatabase bsPixDatabase) {
        this.view = view;
        this.bsPixDatabase = bsPixDatabase;
    }

    void load(@NonNull Scheduler scheduler) {
        bsPixDatabase.getFollows()
                .observeOn(scheduler)
                .subscribe(new BaseObserver<List<User>>() {
                    @Override public void onNext(List<User> follows) {
                        view.setFollows(follows);
                    }
                });
    }

    void unload() {
        subscriptions.clear();
    }

    void onClickUser(@NonNull String userId) {
        view.navigateToUser(userId);
    }
}
