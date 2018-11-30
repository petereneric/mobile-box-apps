package com.example.ericschumacher.bouncer.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Juicer_Charger;
import com.example.ericschumacher.bouncer.Adapter.Pager.Adapter_Pager_ModelColorShape;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Devices;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Devices;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Juicer;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Request_Choice;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Charger;
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

public class Activity_Juicer extends AppCompatActivity implements Interface_Juicer, Interface_Devices, Interface_Request_Choice, Adapter_Juicer_Charger.Interface_Adapter_Juicer_Charger {

    // Data
    ArrayList<Charger> lCharger = new ArrayList<>();
    ArrayList<Integer> lModelColorShapeIds = new ArrayList<>();

    // Layout
    ViewPager ViewPager;
    RecyclerView rvCharger;

    // Adapter
    Adapter_Pager_ModelColorShape aLKUs;
    Adapter_Juicer_Charger aCharger;

    // Utilities
    Utility_Network uNetwork;

    // Else
    FragmentManager fManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setLayout();

        uNetwork = new Utility_Network(this);
        fManager = getSupportFragmentManager();

        // RecyclerView
        rvCharger.setLayoutManager(new GridLayoutManager(this,3, LinearLayoutManager.HORIZONTAL, false));
        aCharger = new Adapter_Juicer_Charger(Activity_Juicer.this, lCharger, Activity_Juicer.this);
        rvCharger.setAdapter(aCharger);

        ArrayList<Integer> test = new ArrayList<>();

        aLKUs = new Adapter_Pager_ModelColorShape(test, getSupportFragmentManager());
        ViewPager.setAdapter(aLKUs);

    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("Activity_Juicer", "onResume");
        uNetwork.getChargers(new Interface_VolleyCallback_ArrayList_Charger() {
            @Override
            public void onSuccess(ArrayList<Charger> list) {
                Log.i("List size", Integer.toString(list.size()));
                aCharger.updateData(list);
            }
        });
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
    public void removeIdModelColorShape(int idModelColorShape) {
        lModelColorShapeIds.remove(new Integer(idModelColorShape));
        aLKUs.updateData(lModelColorShapeIds);
    }

    @Override
    public void scan(Device device, boolean isPartOfList) {
        uNetwork.bookDeviceOutOfLKUStock(device, new Interface_VolleyCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure() {

            }
        });
        Toast.makeText(this, getString(R.string.device_written_off), Toast.LENGTH_LONG).show();
    }

    @Override
    public void delete(Device device) {
        uNetwork.deleteDevice(device, new Interface_VolleyCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure() {

            }
        });
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
    }

    @Override
    public void onAdd() {

    }

    @Override
    public void onChargerChanged(ArrayList<Charger> chargerUnselected) {
        ViewPager.setBackgroundColor(getResources().getColor(R.color.color_primary_dark));
        Log.i("Activity_Juicer", "onChargerChanged");
        uNetwork.getDevicesForJuicer(chargerUnselected, new Interface_VolleyCallback_ArrayList_ModelColorShapeIds() {
            @Override
            public void onSuccess(ArrayList<Integer> modelColorShapeIds) {
                lModelColorShapeIds = modelColorShapeIds;
                Log.i("modelColorShapeIds", Integer.toString(modelColorShapeIds.size()));
                aLKUs.updateData(lModelColorShapeIds);
                ViewPager.setBackgroundColor(getResources().getColor(R.color.color_white));
            }
            @Override
            public void onFailure() {
            }
        });

    }
}


