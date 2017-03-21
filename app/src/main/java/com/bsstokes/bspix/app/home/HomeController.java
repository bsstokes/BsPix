package com.bsstokes.bspix.app.home;

import android.support.annotation.NonNull;

import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.data.User;
import com.bsstokes.bspix.rx.BaseObserver;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.subscriptions.CompositeSubscription;

class HomeController {

    public interface View {
        void loadProfilePicture(String profilePictureUrl);

        void setName(String name);

        void setBio(String bio);

        void setWebsite(String website);

        void setCounts(int posts, int followers, int following);
    }

    @NonNull private final View view;
    @NonNull private final BsPixDatabase bsPixDatabase;
    @NonNull private final CompositeSubscription subscriptions = new CompositeSubscription();

    HomeController(@NonNull View view, @NonNull BsPixDatabase bsPixDatabase) {
        this.view = view;
        this.bsPixDatabase = bsPixDatabase;
    }

    void loadSelf() {
        load(bsPixDatabase.getSelf());
    }

    private void load(Observable<User> userObservable) {
        final Subscription subscription = userObservable
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<User>() {
                    @Override public void onNext(User user) {
                        view.loadProfilePicture(user.profilePicture());
                        view.setName(user.fullName());
                        view.setBio(user.bio());
                        view.setWebsite(user.website());
                        view.setCounts(user.mediaCount(), user.followedByCount(), user.followsCount());
                    }
                });
        subscriptions.add(subscription);
    }

    void unload() {
        subscriptions.clear();
    }
}
