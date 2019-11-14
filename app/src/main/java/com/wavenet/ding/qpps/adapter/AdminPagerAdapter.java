package com.wavenet.ding.qpps.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by zoubeiwen on 2018/8/6.
 */

public class AdminPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public AdminPagerAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {

        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    /**
     * 如果不是自定义标签布局，需要重写该方法
     */
//        @Nullable
//        @Override
//        public CharSequence getPageTitle(int position) {
//            return sitelrighttitle[position];
//        }
}
