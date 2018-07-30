package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Devices;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Devices;
import com.example.ericschumacher.bouncer.Interfaces.Interface_LKU;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Devices;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

import java.util.ArrayList;

public class Fragment_Devices extends Fragment implements Interface_Devices {

    // Layout
    View Layout;
    RecyclerView rvDevices;

    // Interface
    Interface_LKU iLKU;

    // Adapter
    Adapter_Devices aDevices;

    // Utility
    Utility_Network uNetwork;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Layout
        setLayout(inflater, container);

        // Interface
        iLKU = (Interface_LKU)getActivity();

        // Utility
        uNetwork = new Utility_Network(getActivity());

        // RecyclerView
        rvDevices.setLayoutManager(new LinearLayoutManager(getActivity()));
        uNetwork.getDevicesByIdModelColorShape(getArguments().getInt(Constants_Intern.ID_MODEL_COLOR_SHAPE), new Interface_VolleyCallback_ArrayList_Devices() {
            @Override
            public void onSuccess(ArrayList<Device> devices) {
                aDevices = new Adapter_Devices(getActivity(), devices, Fragment_Devices.this);
                rvDevices.setAdapter(aDevices);
            }

            @Override
            public void onFailure() {

            }
        });


        return Layout;
    }

    private void setLayout(LayoutInflater inflater, ViewGroup containter) {
        Layout = inflater.inflate(R.layout.fragment_devices,containter, false);
        rvDevices = Layout.findViewById(R.id.rvLkuDevices);
    }

    @Override
    public void remove(Device device) {
        iLKU.removeDevice(device);
    }
}
