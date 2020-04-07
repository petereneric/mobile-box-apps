package com.example.ericschumacher.bouncer.Fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Fragments.Edit.Fragment_Edit_Model_Battery;
import com.example.ericschumacher.bouncer.Objects.Model_Battery;

public class Fragment_Edit_Model_Battery_Select extends Fragment_Edit_Model_Battery {

    public void setLayout(LayoutInflater inflater, ViewGroup viewGroup) {
        super.setLayout(inflater, viewGroup);
        bCommit.setVisibility(View.GONE);
    }

    public void onClick(Model_Battery oModelBattery) {
        //ßiDeviceManager.returnDeviceBattery(oModelBattery.getoBattery());
    }


}
