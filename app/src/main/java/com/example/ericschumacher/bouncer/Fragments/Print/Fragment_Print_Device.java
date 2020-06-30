package com.example.ericschumacher.bouncer.Fragments.Print;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Activities.Manager.Activity_Device;

public class Fragment_Print_Device extends Fragment_Print implements View.OnClickListener {

    // Activity
    Activity_Device activityDevice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Layout
        vLayout = super.onCreateView(inflater, container, savedInstanceState);

        // Activity
        activityDevice = (Activity_Device)getActivity();

        return vLayout;
    }



    @Override
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        // Visibility
        tvPrintDevice.setVisibility(View.VISIBLE);
    }
}
