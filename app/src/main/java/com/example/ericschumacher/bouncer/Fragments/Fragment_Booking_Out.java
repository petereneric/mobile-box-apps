package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ericschumacher.bouncer.Interfaces.Interface_LKU_Booker;
import com.example.ericschumacher.bouncer.Interfaces.Interface_DeviceManager;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Booking_Out extends Fragment implements View.OnClickListener {

    // vLayout
    View Layout;
    Button bBookedOut;
    Button bCancel;

    // Interface
    Interface_LKU_Booker iLKUBooker;
    Interface_DeviceManager iManager;

    // Data
    Device oDevice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // vLayout
        setLayout(inflater, container);

        // Interface
        iLKUBooker = (Interface_LKU_Booker)getActivity();
        iManager = (Interface_DeviceManager)getActivity();

        // Data
        oDevice = iManager.getDevice();

        return Layout;
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        Layout = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_stocking_book_out, container, false);

        bBookedOut = Layout.findViewById(R.id.bBookedOut);
        bCancel = Layout.findViewById(R.id.bCancel);

        bBookedOut.setOnClickListener(this);
        bCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bBookedOut:
                oDevice.setoStoragePlace(null);
                iLKUBooker.onBookedOut();
                break;
            case R.id.bCancel:
                iLKUBooker.onCancel();
                break;
        }
    }
}
