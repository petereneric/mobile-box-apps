package com.example.ericschumacher.bouncer.Adapter.Pager;

import android.os.Bundle;
import android.support.v4.app.BundleCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;

import java.util.ArrayList;

public class Adapter_Pager extends FragmentPagerAdapter {

    ArrayList<Fragment> lFragment = new ArrayList<>();
    ArrayList<String> lTitle = new ArrayList<>();

    public Adapter_Pager(FragmentManager fm) {
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
        Bundle bundle = new Bundle();
        bundle.putString(Constants_Intern.TITLE, title);
        fragment.setArguments(bundle);
        lFragment.add(fragment);
        lTitle.add(title);
    }
    
    public void update() {
        for (Fragment fragment : lFragment) {
            ((Interface_Update)fragment).update();
        }
    }

}
