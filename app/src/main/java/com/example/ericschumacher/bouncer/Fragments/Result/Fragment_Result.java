package com.example.ericschumacher.bouncer.Fragments.Result;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Activities.Tools.Activity_Bouncer;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Device;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Result extends Fragment implements View.OnClickListener {

    // Data
    Device oDevice;

    // vLayout
    View vLayout;
    TextView tvInteractionTitle;
    TextView tvInteractionType;
    RelativeLayout rlResult;
    LinearLayout llReuse;
    LinearLayout llDefectRepair;
    LinearLayout llDefectReuse;
    LinearLayout llRecycling;
    LinearLayout llUnknown;
    LinearLayout llReuseObjects;
    LinearLayout llRecyclingObjects;
    LinearLayout llDefectRepairObjects;
    LinearLayout llDefectReuseObjects;
    LinearLayout llUnknownObjects;
    LinearLayout llInformation;
    TextView tvReuse;
    TextView tvReuseDevice;
    TextView tvReuseBattery;
    TextView tvReuseBackcover;
    TextView tvDefectRepairDevice;
    TextView tvDefectReuseDevice;
    TextView tvRecyclingDevice;
    TextView tvRecyclingBattery;
    TextView tvRecyclingBackcover;
    TextView tvUnknownModel;
    TextView tvUnknownExploitation;
    TextView tvInformation;

    // Interface
    Interface_Fragment_Result iResult;
    Fragment_Device.Interface_Device iDevice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Interface
        setInterface();

        // Data
        oDevice = iDevice.getDevice();

        // vLayout
        setLayout(inflater, container);
        setScreen();

        return vLayout;
    }

    public void setLayout(LayoutInflater inflater, @Nullable ViewGroup container) {
        vLayout = inflater.inflate(R.layout.fragment_result_new, container, false);

        rlResult = vLayout.findViewById(R.id.rlResult);
        tvInteractionTitle = vLayout.findViewById(R.id.tvTitle);
        tvInteractionType = vLayout.findViewById(R.id.tvTitle);
        llReuse = vLayout.findViewById(R.id.llReuse);
        llDefectRepair = vLayout.findViewById(R.id.llRepair);
        llDefectReuse = vLayout.findViewById(R.id.llDefectReuse);
        llRecycling = vLayout.findViewById(R.id.llRecycling);
        llUnknown = vLayout.findViewById(R.id.llUnknown);
        llReuseObjects = vLayout.findViewById(R.id.llReuseObjects);
        llRecyclingObjects = vLayout.findViewById(R.id.llRecyclingObjects);
        llDefectRepairObjects = vLayout.findViewById(R.id.llDefectRepairObjects);
        llDefectReuseObjects = vLayout.findViewById(R.id.llDefectReuseObjects);
        llUnknownObjects = vLayout.findViewById(R.id.llUnknownObjects);
        llInformation = vLayout.findViewById(R.id.llInformation);
        tvReuse = vLayout.findViewById(R.id.tvReuse);
        tvReuseDevice = vLayout.findViewById(R.id.tvReuseDevice);
        tvReuseBattery = vLayout.findViewById(R.id.tvReuseBattery);
        tvReuseBackcover = vLayout.findViewById(R.id.tvReuseBackcover);
        tvDefectRepairDevice = vLayout.findViewById(R.id.tvRepairDevice);
        tvDefectReuseDevice = vLayout.findViewById(R.id.tvDefectReuseDevice);
        tvRecyclingDevice = vLayout.findViewById(R.id.tvRecyclingDevice);
        tvRecyclingBattery = vLayout.findViewById(R.id.tvRecyclingBattery);
        tvRecyclingBackcover = vLayout.findViewById(R.id.tvRecyclingBackcover);
        tvUnknownModel = vLayout.findViewById(R.id.tvUnknownModel);
        tvUnknownExploitation = vLayout.findViewById(R.id.tvUnknownExploitation);
        tvInformation = vLayout.findViewById(R.id.tvInformation);

        rlResult.setOnClickListener(this);

        tvInteractionType.setText(getString(R.string.result));
        tvInteractionTitle.setVisibility(View.GONE);


    }

    public void setScreen() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlResult:
                click();
                break;
        }
    }

    public void setInterface() {
        iDevice = (Fragment_Device.Interface_Device)getActivity();
        iResult = (Interface_Fragment_Result)getActivity();
    }

    public interface Interface_Fragment_Result {
        public void returnResult(String cTag);
    }

    public void click() {
        iResult.returnResult(getTag());
    }
}
