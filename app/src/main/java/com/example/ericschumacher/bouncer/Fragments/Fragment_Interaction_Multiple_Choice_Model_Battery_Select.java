package com.example.ericschumacher.bouncer.Fragments;

import android.view.View;

import com.example.ericschumacher.bouncer.Objects.Model_Battery;

public class Fragment_Interaction_Multiple_Choice_Model_Battery_Select extends Fragment_Interaction_Multiple_Choice_Model_Battery {

    public void setLayout() {
        super.setLayout();
        bCommit.setVisibility(View.GONE);
    }

    public void onClick(Model_Battery oModelBattery) {
        iDeviceManager.returnDeviceBattery(oModelBattery.getoBattery());
    }


}
