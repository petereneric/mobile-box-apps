package com.example.ericschumacher.bouncer.Adapter.Pager;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.util.Log;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Devices;

import java.util.ArrayList;

public class Adapter_Pager_ModelColorShape extends FragmentStatePagerAdapter {

    private ArrayList<Integer> Lkus;
    private int baseId = 17;

    public Adapter_Pager_ModelColorShape(ArrayList<Integer> lkus, FragmentManager fm) {
        super(fm);
        Lkus = lkus;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment_Devices fDevices = new Fragment_Devices();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants_Intern.ID_MODEL_COLOR_SHAPE, Lkus.get(position));
        fDevices.setArguments(bundle);
        return fDevices;
    }

    @Override
    public int getCount() {
        Log.i("size, ids", Integer.toString(Lkus.size()));
        return Lkus.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    public void deleteFragment(ArrayList<Integer> list) {
        Log.i("Jo", "Delete");
        Lkus = list;
        notifyDataSetChanged();
    }

    public void updateData(ArrayList<Integer> list) {
        Lkus = list;
        notifyDataSetChanged();
    }
}
