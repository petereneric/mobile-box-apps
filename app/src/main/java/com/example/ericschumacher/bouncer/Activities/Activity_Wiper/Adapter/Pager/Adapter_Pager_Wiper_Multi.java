package com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Adapter.Pager;

import android.support.v4.app.FragmentManager;

import com.example.ericschumacher.bouncer.Adapter.Pager.Adapter_Pager;

public class Adapter_Pager_Wiper_Multi extends Adapter_Pager {

    public Adapter_Pager_Wiper_Multi(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void updateLayout() {
        //((Fragment_Diagnose_Container)lFragment.get(0)).updateLayout();
        //((Fragment_Edit_Model_Checks)lFragment.get(1)).updateLayout();
        //((Interface_Update)lFragment.get(2)).update();
    }
}
