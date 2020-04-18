package com.example.ericschumacher.bouncer.Adapter.Pager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Batteries;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;

import java.util.ArrayList;

public class Adapter_Pager_Battery extends FragmentStatePagerAdapter {

    // Variables
    ArrayList<Manufacturer> lManufacturer = new ArrayList<>();
    FragmentManager fManager;

    public Adapter_Pager_Battery(FragmentManager fm, ArrayList<Manufacturer> lManufacturer) {
        super(fm);
        fManager = fm;
        this.lManufacturer = lManufacturer;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fBatteries = new Fragment_Batteries();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants_Intern.DATA_TYPE, Constants_Intern.DATA_TYPE_ID_MANUFACTURER);
        bundle.putInt(Constants_Intern.ID_MANUFACTURER, lManufacturer.get(i).getId());
        bundle.putString(Constants_Intern.SHORTCUT_MANUFACTURER, lManufacturer.get(i).getcShortcut());
        fBatteries.setArguments(bundle);
        return  fBatteries;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return lManufacturer.get(position).getName();
    }

    @Override
    public int getCount() {
        return lManufacturer.size();
    }
}
