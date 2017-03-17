package com.bsstokes.bspix.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bsstokes.bspix.BsPixApplication;
import com.bsstokes.bspix.R;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @Inject Account account;

    @NonNull
    public static Intent createIntent(@NonNull Context context) {
        return new Intent(context, LoginActivity.class);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        BsPixApplication.getBsPixApplication(this).getAppComponent().inject(this);
    }

    @OnClick(R.id.log_in_button)
    void onClickLogInButton() {
        account.logIn("access-token");
        finish();
    }
}
