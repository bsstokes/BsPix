package com.bsstokes.bspix.app.main;

interface MainContract {
    interface View {
        void launchLoginActivity();

        void launchHomeActivity();
    }

    interface Controller {
        void start();
    }
}
