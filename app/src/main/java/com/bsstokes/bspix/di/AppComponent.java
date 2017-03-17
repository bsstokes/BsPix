package com.bsstokes.bspix.di;

import com.bsstokes.bspix.BsPixApplication;
import com.bsstokes.bspix.main.MainActivity;

public interface AppComponent {
    // Application
    void inject(BsPixApplication application);

    // Activities
    void inject(MainActivity activity);
}
