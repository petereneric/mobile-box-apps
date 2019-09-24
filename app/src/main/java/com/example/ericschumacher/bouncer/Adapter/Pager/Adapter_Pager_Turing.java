package com.example.ericschumacher.bouncer.Adapter.Pager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

public class Adapter_Pager_Turing extends FragmentPagerAdapter {

    ArrayList<Fragment> lFragment = new ArrayList<>();
    ArrayList<String> lTitle = new ArrayList<>();

    public Adapter_Pager_Turing(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return lFragment.get(position);
    }

    @Override
    public int getCount() {
        return lFragment.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return lTitle.get(position);
    }

    public void add(String title, Fragment fragment) {
        lFragment.add(fragment);
        lTitle.add(title);
    }

}
