package com.example.ericschumacher.bouncer.Adapter.Pager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Devices;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Devices;

public class Adapter_Pager_LKUs extends FragmentPagerAdapter {

    private int[] Lkus;

    public Adapter_Pager_LKUs(int[] lkus, FragmentManager fm) {
        super(fm);
        Lkus = lkus;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment_Devices fDevices = new Fragment_Devices();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants_Intern.LKU, Lkus[position]);
        fDevices.setArguments(bundle);
        return fDevices;
    }

    @Override
    public int getCount() {
        return Lkus.length;
    }
}
