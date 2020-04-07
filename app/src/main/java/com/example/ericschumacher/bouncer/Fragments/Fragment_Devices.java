package com.example.ericschumacher.bouncer.Fragments;

import android.content.Context;
import android.os.Bundle;
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
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Devices;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Devices;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Juicer;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Keyboard;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

import java.util.ArrayList;

public class Fragment_Devices extends Fragment implements Interface_Fragment_Devices {

    // vLayout
    View Layout;
    RecyclerView rvDevices;
    EditText etScan;

    // Data
    ArrayList<Device> lDevices = new ArrayList<>();

    // Interface
    Interface_Devices iDevices;
    Interface_Juicer iJuicer;

    // Adapter
    Adapter_Devices aDevices;

    // Utility
    Utility_Network uNetwork;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // vLayout
        setLayout(inflater, container);

        // Interface
        iDevices = (Interface_Devices) getActivity();
        iJuicer = (Interface_Juicer)getActivity();

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
                            lDevices.remove(d);
                            iDevices.scan(device, isPartOfList);
                            etScan.setText("");
                            aDevices.updateList(lDevices);
                            if (lDevices.size() == 0) {
                                Log.i("leeer", "jo");
                                iJuicer.removeIdModelColorShape(getArguments().getInt(Constants_Intern.ID_MODEL_COLOR_SHAPE));
                            } else {

                            }
                            break;
                        }
                    }


                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        etScan.requestFocus();
        Utility_Keyboard.hideKeyboardFrom(getContext(), etScan);

        // rvList
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
        } else {
            Log.i("Works", "so far");
            lDevices = devices;
            aDevices.updateList(lDevices);
        }
        etScan.requestFocus();
        Context context = getContext();
        if (context != null && etScan != null) {
            Utility_Keyboard.hideKeyboardFrom(getContext(), etScan);
        }
    }
}
