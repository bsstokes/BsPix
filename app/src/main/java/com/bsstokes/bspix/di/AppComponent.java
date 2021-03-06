package com.bsstokes.bspix.di;

import com.bsstokes.bspix.app.BsPixApplication;
import com.bsstokes.bspix.app.home.HomeActivity;
import com.bsstokes.bspix.app.home.follows.FollowsFragment;
import com.bsstokes.bspix.app.home.liked_media.LikedMediaFragment;
import com.bsstokes.bspix.app.home.my_media.MyMediaFragment;
import com.bsstokes.bspix.app.main.MainActivity;
import com.bsstokes.bspix.app.media_item.MediaItemActivity;
import com.bsstokes.bspix.app.user.UserActivity;
import com.bsstokes.bspix.auth.LoginActivity;
import com.bsstokes.bspix.sync.SyncService;

public interface AppComponent {
    // Application
    void inject(BsPixApplication application);

    // Activities
    void inject(HomeActivity activity);

    void inject(LoginActivity activity);

    void inject(MainActivity activity);

    void inject(MediaItemActivity activity);

    void inject(UserActivity activity);

    // Fragment
    void inject(FollowsFragment fragment);

    void inject(LikedMediaFragment fragment);

    void inject(MyMediaFragment fragment);

    // Services
    void inject(SyncService service);
}
