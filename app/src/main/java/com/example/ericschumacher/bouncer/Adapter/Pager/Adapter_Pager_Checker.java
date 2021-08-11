package com.example.ericschumacher.bouncer.Adapter.Pager;

import android.support.v4.app.FragmentManager;

import com.example.ericschumacher.bouncer.Fragments.Checker.Fragment_Diagnose_Container;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;

public class Adapter_Pager_Checker extends Adapter_Pager {

    public Adapter_Pager_Checker(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void updateDiagnose() {
        ((Fragment_Diagnose_Container)lFragment.get(0)).updateLayout();
        ((Interface_Update)lFragment.get(2)).update();
    }
}