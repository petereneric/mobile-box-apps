package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Devices;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Devices;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Devices;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

import java.util.ArrayList;

public class Fragment_Devices extends Fragment implements Interface_Fragment_Devices {

    // Layout
    View Layout;
    RecyclerView rvDevices;
    EditText etScan;

    // Data
    ArrayList<Device> lDevices = new ArrayList<>();

    // Interface
    Interface_Devices iDevices;

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
        iDevices = (Interface_Devices) getActivity();

        // Utility
        uNetwork = new Utility_Network(getActivity());

        // EditText
        etScan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!charSequence.toString().equals("")) {
                    int idDevice = Integer.parseInt(charSequence.toString());
                    Device device = null;
                    boolean isPartOfList = false;
                    for (Device d : lDevices) {
                        if (d.getIdDevice() == idDevice) {
                            isPartOfList = true;
                            device = d;
                            iDevices.scan(device, isPartOfList);
                            break;
                        }
                    }
                    etScan.setText("");
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            iDevices.getDevices(getArguments(), Fragment_Devices.this);
                        }
                    }, 500);

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // RecyclerView
        rvDevices.setLayoutManager(new LinearLayoutManager(getActivity()));
        aDevices = new Adapter_Devices(getActivity(), lDevices, Fragment_Devices.this);
        rvDevices.setAdapter(aDevices);

        // Data
        iDevices.getDevices(getArguments(), this);

        return Layout;
    }

    private void setLayout(LayoutInflater inflater, ViewGroup containter) {
        Layout = inflater.inflate(R.layout.fragment_devices, containter, false);
        rvDevices = Layout.findViewById(R.id.rvLkuDevices);
        etScan = Layout.findViewById(R.id.etScan);
    }

    @Override
    public void delete(Device device) {
        iDevices.delete(device);
    }

    @Override
    public void setDevices(ArrayList<Device> devices) {
        if (devices.size() == 0) {
            //onDestroy();
        }
        Log.i("Works", "so far");
        lDevices = devices;
        aDevices.updateList(lDevices);
    }
}
