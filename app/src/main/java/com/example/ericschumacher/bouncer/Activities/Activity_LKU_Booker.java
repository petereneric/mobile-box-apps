package com.example.ericschumacher.bouncer.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.ericschumacher.bouncer.Activities.Parent.Activity_Device;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Input_LKU_Booking;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Device;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;

public class Activity_LKU_Booker extends Activity_Device {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onScan(String text) {
        uNetwork.getDevice(Integer.parseInt(text), new Interface_VolleyCallback_Device() {
            @Override
            public void onSuccess(Device device) {
                closeKeyboard(etScan);
                Log.i("XE", "SS");
                oDevice = device;
                updateUI();
                Fragment_Input_LKU_Booking fragment = new Fragment_Input_LKU_Booking();
                Bundle bundle = new Bundle();
                bundle.putInt(Constants_Intern.ID_STATION, oDevice.getStation().getId());
                fragment.setArguments(bundle);
                fManager.beginTransaction().replace(R.id.flFragmentInteraction, fragment, Constants_Intern.FRAGMENT_LKU_BOOKING).commit();
            }

            @Override
            public void onFailure() {
                resetDevice();
            }
        });
    }


}
