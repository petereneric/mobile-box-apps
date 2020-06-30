package com.example.ericschumacher.bouncer.Fragments.Booking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Activities.Manager.Activity_Device;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

public class Fragment_Booking extends Fragment implements View.OnClickListener {

    // Context
    android.content.Context Context;

    // Data
    Bundle bData;
    String cTitle;

    // vLayout
    public int kLayout;
    View vLayout;
    TextView tvTitle;
    TextView tvStationFrom;
    TextView tvStockTo;
    TextView tvStockNumber;
    View vDividerStorageNumber;
    Button bCommit;
    LinearLayout llBooking;

    // Connection
    Volley_Connection cVolley;

    // Interface
    Interface_Booking iBooking;

    // Instances
    Activity_Device activityDevice;
    Device oDevice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Context
        Context = getActivity();

        // Instances
        activityDevice = (Activity_Device)getActivity();
        oDevice = activityDevice.getDevice();

        // Connection
        cVolley = new Volley_Connection(getActivity());

        // Data
        bData = getArguments();
        cTitle = bData.getString(Constants_Intern.TITLE);

        // vLayout
        setLayout(inflater, container);
        update();

        // Interface
        iBooking = (Interface_Booking)getActivity();

        return vLayout;
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        // vLayout
        vLayout = inflater.inflate(getkLayout(), container, false);
        tvTitle = vLayout.findViewById(R.id.tvTitle);
        tvStationFrom = vLayout.findViewById(R.id.tvStationFrom);
        tvStockTo = vLayout.findViewById(R.id.tvStockTo);
        tvStockNumber = vLayout.findViewById(R.id.tvStorageNumber);
        vDividerStorageNumber = vLayout.findViewById(R.id.vDividerStorageNumber);
        bCommit = vLayout.findViewById(R.id.bCommit);
        llBooking = vLayout.findViewById(R.id.llBooking);

        // Data
        tvTitle.setText(cTitle);
        tvStationFrom.setText(oDevice.getoStation().getName());

        // OnClickListener
        bCommit.setOnClickListener(this);
    }

    public void update() {

    }

    public int getkLayout() {
        return kLayout;
    }

    public interface Interface_Booking {
        void returnBooking(String cTag);
        void errorBooking(String cTag, String cError);
    }


    // ClickListener
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bCommit:
                onCommit();
                break;
        }
    }

    public void onCommit() {

    }
}
