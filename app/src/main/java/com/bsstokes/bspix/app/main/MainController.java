package com.bsstokes.bspix.app.main;

import android.support.annotation.NonNull;

import com.bsstokes.bspix.auth.Account;

class MainController implements MainContract.Controller {

    @NonNull private final MainContract.View view;
    @NonNull private final Account account;

    MainController(@NonNull MainContract.View view, @NonNull Account account) {
        this.view = view;
        this.account = account;
    }

    @Override
    public void start() {
        if (account.isLoggedIn()) {
            view.launchHomeActivity();
        } else {
            view.launchLoginActivity();
        }
    }
}
