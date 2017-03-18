package com.bsstokes.bspix.app.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bsstokes.bspix.app.BsPixApplication;
import com.bsstokes.bspix.R;
import com.bsstokes.bspix.api.InstagramApi;
import com.bsstokes.bspix.api.UnwrapInstagramResponse;
import com.bsstokes.bspix.api.UnwrapResponse;
import com.bsstokes.bspix.auth.Account;
import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.data.User;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observer;
import rx.schedulers.Schedulers;

public class HomeActivity extends AppCompatActivity {

    @Inject Account account;
    @Inject BsPixDatabase bsPixDatabase;
    @Inject InstagramApi instagramApi;

    @NonNull
    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, HomeActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);
        ButterKnife.bind(this);
        BsPixApplication.getBsPixApplication(this).getAppComponent().inject(this);
    }

    @Override protected void onResume() {
        super.onResume();

        bsPixDatabase.getUser("4833062266")
                .subscribe(new Observer<User>() {
                    @Override public void onCompleted() {

                    }

                    @Override public void onError(Throwable e) {

                    }

                    @Override public void onNext(User user) {
                        Log.d("getUser", "user=" + user);
                    }
                });


        instagramApi.getSelf()
                .subscribeOn(Schedulers.io())
                .map(new UnwrapResponse<InstagramApi.InstagramResponse<InstagramApi.User>>())
                .map(new UnwrapInstagramResponse<InstagramApi.User>())
                .subscribe(new Observer<InstagramApi.User>() {
                    @Override public void onCompleted() {

                    }

                    @Override public void onError(Throwable e) {

                    }

                    @Override public void onNext(InstagramApi.User user) {
                        final User dbUser = User.builder()
                                .id(user.id)
                                .userName(user.username)
                                .fullName(user.full_name)
                                .bio(user.bio)
                                .website(user.website)
                                .mediaCount(user.counts.media)
                                .followsCount(user.counts.follows)
                                .followedByCount(user.counts.followed_by)
                                .build();

                        bsPixDatabase.putUser(dbUser);
                    }
                });
    }

    @OnClick(R.id.log_out_button)
    void onClickLogOutButton() {
        account.logOut();
        finish();
    }
}
