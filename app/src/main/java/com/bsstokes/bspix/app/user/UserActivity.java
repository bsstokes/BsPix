package com.bsstokes.bspix.app.user;

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
import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.sync.SyncService;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserActivity extends AppCompatActivity implements UserController.View {

    @NonNull public static Intent createIntent(@NonNull Context context, @NonNull String userId) {
        final Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }

    private static final String EXTRA_USER_ID = "EXTRA_USER_ID";

    @BindView(R.id.profile_picture_image_view) ImageView profilePictureImageView;
    @BindView(R.id.full_name_text_view) TextView fullNameTextView;
    @BindView(R.id.user_name_text_view) TextView userNameTextView;

    @Inject BsPixDatabase bsPixDatabase;
    @Inject Picasso picasso;

    private UserController userController;
    @Nullable private String userId;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);
        ButterKnife.bind(this);

        BsPixApplication.getBsPixApplication(this).getAppComponent().inject(this);

        userController = new UserController(this, bsPixDatabase);

        if (null != getIntent()) {
            userId = getIntent().getStringExtra(EXTRA_USER_ID);
        }

        if (null != userId) {
            SyncService.startActionSyncUser(this, userId);
        }
    }

    @Override protected void onResume() {
        super.onResume();

        if (null == userId) {
            finish();
            return;
        }

        userController.load(userId);
    }

    @Override protected void onPause() {
        super.onPause();
        userController.unload();
    }

    @Override public void setFullName(@Nullable String fullName) {
        fullNameTextView.setText(fullName);
    }

    @Override public void setUserName(@Nullable String userName) {
        userNameTextView.setText(userName);
    }

    @Override public void loadProfilePicture(@Nullable String profilePictureUrl) {
        if (null == profilePictureUrl) {
            profilePictureImageView.setImageDrawable(null);
        } else {
            picasso.load(profilePictureUrl).into(profilePictureImageView);
        }
    }
}
