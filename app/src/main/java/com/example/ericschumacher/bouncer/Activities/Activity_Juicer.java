package com.example.ericschumacher.bouncer.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.ericschumacher.bouncer.Adapter.Pager.Adapter_Pager_LKUs;
import com.example.ericschumacher.bouncer.Interfaces.Interface_LKU;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 08.07.2018.
 */

public class Activity_Juicer extends AppCompatActivity implements Interface_LKU {

    // Layout
    ViewPager ViewPager;

    // Adapter
    Adapter_Pager_LKUs aLKUs;

    // Utilities
    Utility_Network uNetwork;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uNetwork = new Utility_Network(this);

        aLKUs = new Adapter_Pager_LKUs(this, uNetwork.getJuicerDevicesByCharger();getSupportFragmentManager())
        // muss noch eine Auswahl Activity oder ähnliches vorgeschaltet werden

        setLayout();


    }

    private void setLayout() {
        setContentView(R.layout.activity_juicer);
        ViewPager = findViewById(R.id.ViewPager);
    }

    @Override
    public ArrayList<Device> getDevices(int lku) {
        return null;
    }

    @Override
    public void removeDevice(Device device) {

    }
}
