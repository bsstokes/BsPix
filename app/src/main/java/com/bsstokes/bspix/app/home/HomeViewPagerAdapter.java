package com.bsstokes.bspix.app.home;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bsstokes.bspix.app.home.my_media.MyMediaFragment;

public class HomeViewPagerAdapter extends FragmentStatePagerAdapter {

    public HomeViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override public CharSequence getPageTitle(int position) {
        switch (position) {
            case (0):
                return "My Photos";
            default:
                return null;
        }
    }

    @Override public Fragment getItem(int position) {
        switch (position) {
            case (0):
                return new MyMediaFragment();
        }
        return null;
    }

    @Override public int getCount() {
        return 1;
    }
}
