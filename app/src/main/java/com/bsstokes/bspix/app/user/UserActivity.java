package com.bsstokes.bspix.app.user;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bsstokes.bspix.R;
import com.bsstokes.bspix.app.BsPixApplication;
import com.bsstokes.bspix.data.BsPixDatabase;
import com.bsstokes.bspix.util.StringUtils;
import com.facebook.stetho.common.StringUtil;

import javax.inject.Inject;

public class UserActivity extends AppCompatActivity implements UserController.View {

    @NonNull public static Intent createIntent(@NonNull Context context, @NonNull String userId) {
        final Intent intent = new Intent(context, UserActivity.class);
        intent.putExtra(EXTRA_USER_ID, userId);
        return intent;
    }

    private static final String EXTRA_USER_ID = "EXTRA_USER_ID";

    @Inject BsPixDatabase bsPixDatabase;

    private UserController userController;
    @Nullable private String userId;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_activity);

        BsPixApplication.getBsPixApplication(this).getAppComponent().inject(this);

        userController = new UserController(this, bsPixDatabase);

        if (null != getIntent()) {
            userId = getIntent().getStringExtra(EXTRA_USER_ID);
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
}
