package com.bsstokes.bspix.di;

import com.bsstokes.bspix.BsPixApplication;
import com.bsstokes.bspix.auth.LoginActivity;
import com.bsstokes.bspix.home.HomeActivity;
import com.bsstokes.bspix.main.MainActivity;

public interface AppComponent {
    // Application
    void inject(BsPixApplication application);

    // Activities
    void inject(HomeActivity activity);

    void inject(LoginActivity activity);

    void inject(MainActivity activity);
}
