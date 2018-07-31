package com.example.ericschumacher.bouncer.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ericschumacher.bouncer.Adapter.Pager.Adapter_Pager_ModelColorShape;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Devices;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Devices;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Juicer;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Devices;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_ModelColorShapeIds;
import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.Objects.Additive.Station;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 08.07.2018.
 */

public class Activity_Juicer extends AppCompatActivity implements Interface_Juicer, Interface_Devices {

    // Data
    ArrayList<Device> Devices;

    // Layout
    ViewPager ViewPager;
    RecyclerView rvCharger;

    // Adapter
    Adapter_Pager_ModelColorShape aLKUs;

    // Utilities
    Utility_Network uNetwork;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uNetwork = new Utility_Network(this);

        // RecyclerView
        rvCharger.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // muss noch eine Auswahl Activity oder ähnliches vorgeschaltet werden

        setLayout();
    }

    private void setLayout() {
        setContentView(R.layout.activity_juicer);
        ViewPager = findViewById(R.id.ViewPager);
        rvCharger = findViewById(R.id.rvCharger);
    }

    @Override
    public void returnCharger(Charger charger) {
        uNetwork.getModelColorShapeIdsForJuicerByCharger(charger, new Interface_VolleyCallback_ArrayList_ModelColorShapeIds() {
            @Override
            public void onSuccess(ArrayList<Integer> modelColorShapeIds) {
                aLKUs = new Adapter_Pager_ModelColorShape(modelColorShapeIds, getSupportFragmentManager());
            }

            @Override
            public void onFailure() {

            }
        });

    }

    @Override
    public void scan(int idDevice, boolean isPartOfList) {

    }

    @Override
    public void delete(Device device) {

    }

    @Override
    public void getDevices(Bundle data, final Interface_Fragment_Devices iCallback) {
        uNetwork.getDevicesByIdModelColorShape(data.getInt(Constants_Intern.ID_MODEL_COLOR_SHAPE), Station.getId(Constants_Intern.STATION_LKU_STOCKING), new Interface_VolleyCallback_ArrayList_Devices() {
            @Override
            public void onSuccess(ArrayList<Device> devices) {
                iCallback.setDevices(devices);
            }

            @Override
            public void onFailure() {

            }
        });
    }

}


