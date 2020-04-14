package com.example.ericschumacher.bouncer.Fragments.Booking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Activities.Activity_Device_New;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

public class Fragment_Booking extends Fragment {

    // Data
    Bundle bData;
    String cTitle;

    // vLayout
    public int kLayout;
    View vLayout;
    TextView tvTitle;

    // Connection
    Volley_Connection cVolley;

    // Interface
    Interface_Booking iBooking;

    // Instances
    Activity_Device_New activityDevice;
    Device oDevice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Instances
        activityDevice = (Activity_Device_New)getActivity();
        oDevice = activityDevice.getDevice();

        // Connection
        cVolley = new Volley_Connection(getActivity());

        // Data
        bData = getArguments();
        cTitle = bData.getString(Constants_Intern.TITLE);

        // vLayout
        setLayout(inflater, container);

        // Interface
        iBooking = (Interface_Booking)getActivity();

        return vLayout;
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        // vLayout
        vLayout = inflater.inflate(getkLayout(), container, false);
        tvTitle = vLayout.findViewById(R.id.tvTitle);

        // Data
        tvTitle.setText(cTitle);
    }

    public int getkLayout() {
        return kLayout;
    }

    public interface Interface_Booking {
        void returnBooking(String cTag);
    }
}
