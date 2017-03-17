package com.bsstokes.bspix.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.bsstokes.bspix.BsPixApplication;
import com.bsstokes.bspix.R;

import javax.inject.Inject;

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
        BsPixApplication.getBsPixApplication(this).getAppComponent().inject(this);

        findViewById(R.id.log_in_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account.logIn("access-token");
                finish();
            }
        });
    }
}
