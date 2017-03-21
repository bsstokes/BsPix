package com.bsstokes.bspix.app.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bsstokes.bspix.app.home.follows.FollowsFragment;
import com.bsstokes.bspix.app.home.my_media.MyMediaFragment;

class HomeViewPagerAdapter extends FragmentStatePagerAdapter {

    HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override public CharSequence getPageTitle(int position) {
        switch (position) {
            case (0):
                return "My Photos";
            case (1):
                return "Follows";
            default:
                return null;
        }
    }

    @Override public Fragment getItem(int position) {
        switch (position) {
            case (0):
                return MyMediaFragment.create();
            case (1):
                return FollowsFragment.create();
            default:
                return null;
        }
    }

    @Override public int getCount() {
        return 2;
    }
}
