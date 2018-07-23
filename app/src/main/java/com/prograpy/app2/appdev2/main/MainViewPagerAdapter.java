package com.prograpy.app2.appdev2.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.prograpy.app2.appdev2.chatList.ChatListFragment;
import com.prograpy.app2.appdev2.profile.MyPageFragment;
import com.prograpy.app2.appdev2.setting.SetFragment;

public class MainViewPagerAdapter extends FragmentPagerAdapter {


    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){

            case 0:
                return MatchFragment.createFragment();

            case 1:
                return MyPageFragment.createFragment();

            case 2:
                return ChatListFragment.createFragment();

            case 3:
                return SetFragment.createFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
    }



}
