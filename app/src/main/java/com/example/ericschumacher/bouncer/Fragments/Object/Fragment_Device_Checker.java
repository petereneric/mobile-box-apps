package com.example.ericschumacher.bouncer.Fragments.Object;

import android.util.Log;

import com.example.ericschumacher.bouncer.R;

public class Fragment_Device_Checker extends Fragment_Device {

    @Override
    public int getIdLayout() {
        Log.i("Called", "hier jemand");
        return R.layout.fragment_device_checker;
    }

    @Override
    public void setVisibility(int bVisibility) {
        trShape.setVisibility(bVisibility);
        trState.setVisibility(bVisibility);
        trColor.setVisibility(bVisibility);
        trStation.setVisibility(bVisibility);
        trBattery.setVisibility(bVisibility);
    }
}
