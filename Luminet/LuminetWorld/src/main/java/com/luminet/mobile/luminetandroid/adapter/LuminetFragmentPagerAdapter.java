package com.luminet.mobile.luminetandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.luminet.mobile.luminetandroid.screenViewsFragments.ConnectFeedsFragment;
import com.luminet.mobile.luminetandroid.screenViewsFragments.LumiWorldFragment;
import com.luminet.mobile.luminetandroid.screenViewsFragments.UserProfileFragment;

/**
 * Created by chris on 2017/10/17.
 */

public class LuminetFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] = new String[] { "CONNECT", "LUMINET WORLD", "PROFILE" };

    public LuminetFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {

            // Open FragmentTab1.java
            case 0:
                return ConnectFeedsFragment.newInstane(position);

            // Open FragmentTab2.java
            case 1:
                return LumiWorldFragment.newInstane(position);

            // Open FragmentTab3.java
            case 2:
                return UserProfileFragment.newInstane(position);
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
