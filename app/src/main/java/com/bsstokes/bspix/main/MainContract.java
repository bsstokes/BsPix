package com.bsstokes.bspix.main;

interface MainContract {
    interface View {
        void launchLoginActivity();

        void launchHomeActivity();
    }

    interface Controller {
        void start();

        void stop();
    }
}
