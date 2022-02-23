package com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Adapter.Pager;

import android.support.v4.app.FragmentManager;

import com.example.ericschumacher.bouncer.Adapter.Pager.Adapter_Pager;
import com.example.ericschumacher.bouncer.Fragments.Checker.Fragment_Diagnose_Container;
import com.example.ericschumacher.bouncer.Fragments.Edit.Fragment_Edit_Model_Checks;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;

public class Adapter_Pager_Wiper_Single extends Adapter_Pager {

    public Adapter_Pager_Wiper_Single(FragmentManager fm) {
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
