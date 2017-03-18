package com.bsstokes.bspix.app.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bsstokes.bspix.R;
import com.bsstokes.bspix.app.BsPixApplication;
import com.bsstokes.bspix.auth.Account;
import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.data.User;
import com.bsstokes.bspix.rx.BaseObserver;
import com.bsstokes.bspix.sync.SyncService;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.android.schedulers.AndroidSchedulers;

public class HomeActivity extends AppCompatActivity {

    @BindView(R.id.profilePictureImageView) ImageView profilePictureImageView;
    @BindView(R.id.nameTextView) TextView nameTextView;
    @BindView(R.id.bioTextView) TextView bioTextView;
    @BindView(R.id.websiteTextView) TextView websiteTextView;

    @Inject Account account;
    @Inject BsPixDatabase bsPixDatabase;
    @Inject Picasso picasso;

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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<User>() {
                    @Override public void onNext(User user) {
                        picasso.load(user.profilePicture()).into(profilePictureImageView);
                        nameTextView.setText(user.fullName());
                        bioTextView.setText(user.bio());
                        websiteTextView.setText(user.website());
                    }
                });

        SyncService.startActionSyncSelf(this);
    }
}
