package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Result_New extends Fragment implements View.OnClickListener {

    // Data
    Device oDevice;

    // Layout
    View vLayout;
    RelativeLayout rlResult;
    LinearLayout llReuse;
    LinearLayout llRepair;
    LinearLayout llRecycling;
    TextView tvReuseDevice;
    TextView tvReuseBattery;
    TextView tvReuseBackcover;
    TextView tvRepairDevice;
    TextView tvRecyclingDevice;
    TextView tvRecyclingBattery;
    TextView tvRecyclingBackcover;

    // Boolean
    boolean bReuse = false;
    boolean bRepair = false;
    boolean bRecycling = false;
    boolean bReuseDevice = false;
    boolean bReuseBattery = false;
    boolean bReuseBackcover = false;
    boolean bRepairDevice = false;
    boolean bRecyclingDevice = false;
    boolean bRecyclingBattery = false;
    boolean bRecyclingBackcover = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Data
        Bundle bArguments = getArguments();
        oDevice = (Device)bArguments.getSerializable(Constants_Intern.OBJECT_DEVICE);

        // Boolean
        setBoolean();

        // Layout
        setLayout(inflater, container);

        return vLayout;
    }

    public void setLayout(LayoutInflater inflater, @Nullable ViewGroup container) {
        vLayout = inflater.inflate(R.layout.fragment_result_new, container, false);

        rlResult = vLayout.findViewById(R.id.rlResult);
        llReuse = vLayout.findViewById(R.id.llReuse);
        llRepair = vLayout.findViewById(R.id.llRepair);
        llRecycling = vLayout.findViewById(R.id.llRecycling);
        tvReuseDevice = vLayout.findViewById(R.id.tvReuseDevice);
        tvReuseBattery = vLayout.findViewById(R.id.tvReuseBattery);
        tvReuseBackcover = vLayout.findViewById(R.id.tvReuseBackcover);
        tvRepairDevice = vLayout.findViewById(R.id.tvRepairDevice);
        tvRecyclingDevice = vLayout.findViewById(R.id.tvRecyclingDevice);
        tvRecyclingBattery = vLayout.findViewById(R.id.tvRecyclingBattery);
        tvRecyclingBackcover = vLayout.findViewById(R.id.tvRecyclingBackcover);

        rlResult.setOnClickListener(this);

        // Depending on data
        if (

    }

    public void setBoolean() {
        if (oDevice.getExploitation() == Constants_Intern.EXPLOITATION_RECYCLING) {
            bRecycling = true;

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlResult:

                break;
        }
    }
}
