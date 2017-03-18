package com.bsstokes.bspix.app.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.bsstokes.bspix.BsPixApplication;
import com.bsstokes.bspix.auth.Account;
import com.bsstokes.bspix.auth.LoginActivity;
import com.bsstokes.bspix.app.home.HomeActivity;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements MainContract.View {

    @Inject Account account;
    private MainController mainController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BsPixApplication.getBsPixApplication(this).getAppComponent().inject(this);

        mainController = new MainController(this, account);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mainController.start();
    }

    @Override
    public void launchLoginActivity() {
        startActivity(LoginActivity.createIntent(this));
    }

    @Override
    public void launchHomeActivity() {
        startActivity(HomeActivity.createIntent(this));
    }
}
