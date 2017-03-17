package com.bsstokes.bspix.home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.bsstokes.bspix.BsPixApplication;
import com.bsstokes.bspix.R;
import com.bsstokes.bspix.auth.Account;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class HomeActivity extends AppCompatActivity {

    @Inject Account account;

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

    @OnClick(R.id.log_out_button)
    void onClickLogOutButton() {
        account.logOut();
        finish();
    }
}
