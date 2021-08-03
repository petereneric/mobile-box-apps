package com.example.ericschumacher.bouncer.Adapter.Pager;

import android.support.v4.app.FragmentManager;

public class Adapter_Pager_Checker extends Adapter_Pager {

    public Adapter_Pager_Checker(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }
}
