package com.example.ericschumacher.bouncer.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Request_Choice;
import com.example.ericschumacher.bouncer.Adapter.Pager.Adapter_Pager_ModelColorShape;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Devices;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Devices;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Juicer;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Request_Choice;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Additive;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Devices;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_ModelColorShapeIds;
import com.example.ericschumacher.bouncer.Objects.Additive.Additive;
import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.Objects.Additive.Station;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 08.07.2018.
 */

public class Activity_Juicer extends AppCompatActivity implements Interface_Juicer, Interface_Devices, Interface_Request_Choice {

    // Data
    ArrayList<Device> Devices;
    ArrayList<Additive> lAdditive = new ArrayList<>();

    // Layout
    ViewPager ViewPager;
    RecyclerView rvCharger;

    // Adapter
    Adapter_Pager_ModelColorShape aLKUs;

    // Utilities
    Utility_Network uNetwork;

    // Else
    FragmentManager fManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        uNetwork = new Utility_Network(this);
        fManager = getSupportFragmentManager();

        // RecyclerView
        rvCharger.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        uNetwork.getChargers(new Interface_VolleyCallback_ArrayList_Additive() {
            @Override
            public void onSuccess(ArrayList<Additive> list) {
                lAdditive = list;
                Adapter_Request_Choice adapter = new Adapter_Request_Choice(Activity_Juicer.this, lAdditive, Activity_Juicer.this);
            }
        });

        setLayout();
    }

    private void setLayout() {
        setContentView(R.layout.activity_juicer);
        ViewPager = findViewById(R.id.ViewPager);
        rvCharger = findViewById(R.id.rvCharger);
    }

    @Override
    public void returnCharger(Charger charger) {

    }

    @Override
    public void scan(int idDevice, boolean isPartOfList) {

    }

    @Override
    public void delete(Device device) {
        device.getStation().setId(Constants_Intern.CURRENT_STATION_UNKNOWN);
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

    @Override
    public void onChoice(int position) {
        Charger charger = (Charger)lAdditive.get(position);
        uNetwork.getDevicesForJuicer(charger, new Interface_VolleyCallback_ArrayList_ModelColorShapeIds() {
            @Override
            public void onSuccess(ArrayList<Integer> modelColorShapeIds) {
                aLKUs = new Adapter_Pager_ModelColorShape(modelColorShapeIds, getSupportFragmentManager());
            }

            @Override
            public void onFailure() {

            }
        });
    }
}


