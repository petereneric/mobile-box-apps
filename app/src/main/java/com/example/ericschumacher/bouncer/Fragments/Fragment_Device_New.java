package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Activities.Activity_Device_New;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Device_New extends Fragment implements View.OnClickListener {

    // vLayout
    View vLayout;

    TableRow trColor;
    TableRow trShape;
    TableRow trState;
    TableRow trDamages;
    TableRow trStation;
    TableRow trLKU;
    TableRow trBatteryContained;
    TableRow trBattery;
    TableRow trBackcoverContained;

    TextView tvTitle;
    TextView tvLKU;
    TextView tvStation;
    TextView tvColor;
    TextView tvShape;
    TextView tvBatteryContained;
    TextView tvBattery;
    TextView tvBackcoverContained;
    TextView tvDamages;
    TextView tvState;

    // Instances
    Activity_Device_New activityDevice;
    Device oDevice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Instances
        activityDevice = (Activity_Device_New)getActivity();
        oDevice = activityDevice.getDevice();

        // Layout
        setLayout(inflater, container);



        return vLayout;
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(R.layout.fragment_device_new, container, false);

        tvTitle = vLayout.findViewById(R.id.tvTitle);
        trLKU = vLayout.findViewById(R.id.trLKU);
        trStation = vLayout.findViewById(R.id.trStation);
        trColor = vLayout.findViewById(R.id.trColor);
        trShape = vLayout.findViewById(R.id.trShape);
        trBatteryContained = vLayout.findViewById(R.id.trBatteryContained);
        trBattery = vLayout.findViewById(R.id.trBattery);
        trBackcoverContained = vLayout.findViewById(R.id.trBackcoverContained);
        trDamages = vLayout.findViewById(R.id.trDamages);
        trState = vLayout.findViewById(R.id.trState);

        tvLKU = vLayout.findViewById(R.id.tvLKU);
        tvStation = vLayout.findViewById(R.id.tvStation);
        tvColor = vLayout.findViewById(R.id.tvColor);
        tvShape = vLayout.findViewById(R.id.tvShape);
        tvBatteryContained = vLayout.findViewById(R.id.tvBatteryContained);
        tvBattery = vLayout.findViewById(R.id.tvBattery);
        tvBackcoverContained = vLayout.findViewById(R.id.tvBackcoverContained);
        tvDamages = vLayout.findViewById(R.id.tvDamages);
        tvState = vLayout.findViewById(R.id.tvState);

        tvTitle.setText(getString(R.string.device));

        trLKU.setOnClickListener(this);
        trStation.setOnClickListener(this);
        trColor.setOnClickListener(this);
        trShape.setOnClickListener(this);
        trBatteryContained.setOnClickListener(this);
        trBattery.setOnClickListener(this);
        trBackcoverContained.setOnClickListener(this);
        trDamages.setOnClickListener(this);
    }

    public void updateLayout() {
        oDevice = activityDevice.getDevice();

        setClickable(oDevice != null);

        if (oDevice != null) {
            if (oDevice.getoColor() != null) {
                tvColor.setText(oDevice.getoColor().getcNameDE());
            } else {
                tvColor.setText(getString(R.string.unknown));
            }
            if (oDevice.getoShape() != null) {
                tvShape.setText(oDevice.getoShape().getName());
            } else {
                tvShape.setText(getString(R.string.unknown));
            }
            if (oDevice.gettState() != null) {
                tvState.setText(oDevice.getStateName());
            } else {
                tvState.setText(getString(R.string.unknown));
            }
            if (true) {
                tvDamages.setVisibility(View.VISIBLE);
                tvDamages.setText(Integer.toString(oDevice.getlDeviceDamages().size()));
            } else {
                tvDamages.setText(getString(R.string.none));
            }
            if (oDevice.getoStation() != null) {
                tvStation.setText(oDevice.getoStation().getName());
            } else {
                tvStation.setText(getString(R.string.unknown));
            }
            if (oDevice.getoStoragePlace() != null) {
                tvLKU.setText(Integer.toString(oDevice.getoStoragePlace().getkLku()));
                tvLKU.setVisibility(View.VISIBLE);
            } else {
                tvLKU.setText(getString(R.string.unknown));
                tvLKU.setVisibility(View.GONE);
            }
            if (oDevice.isBatteryContained() != null) {
                tvBatteryContained.setText(oDevice.isBatteryContained() ? getString(R.string.yes) : getString(R.string.yes));
            } else {
                tvBatteryContained.setText(getString(R.string.unknown));
            }
            if (oDevice.isBatteryContained() != null) {
                tvBatteryContained.setText(oDevice.isBatteryContained() ? getString(R.string.yes) : getString(R.string.no));
            } else {
                tvBatteryContained.setText(getString(R.string.unknown));
            }
            if (oDevice.isBackcoverContained() != null) {
                tvBackcoverContained.setText(oDevice.isBackcoverContained() ? getString(R.string.yes) : getString(R.string.no));
            } else {
                tvBackcoverContained.setText(getString(R.string.unknown));
            }
        } else {
            tvColor.setText(getString(R.string.unknown));
            tvShape.setText(getString(R.string.unknown));
            tvState.setText(getString(R.string.unknown));
            tvBattery.setText(getString(R.string.unknown));
            tvDamages.setText(getString(R.string.unknown));
            tvStation.setText(getString(R.string.unknown));
            tvLKU.setText(getString(R.string.unknown));
            tvBatteryContained.setText(getString(R.string.unknown));
            tvBatteryContained.setText(getString(R.string.unknown));
            tvBackcoverContained.setText(getString(R.string.unknown));
        }
    }

    private void setClickable (boolean clickable) {
        trLKU.setClickable(false);
        trStation.setClickable(false);
        trColor.setClickable(clickable);
        trShape.setClickable(clickable);
        trBatteryContained.setClickable(clickable);
        trBattery.setClickable(clickable);
        trBackcoverContained.setClickable(clickable);
        trDamages.setClickable(clickable);
        trState.setClickable(false);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trLKU:
                activityDevice.onClickLku();
                //requestLKU();
                break;
            case R.id.trStation:
                activityDevice.onClickStation();
                break;
            case R.id.trColor:
                activityDevice.onClickColor();
                break;
            case R.id.trShape:
                activityDevice.onClickShape();
                break;
            case R.id.trBatteryContained:
                activityDevice.onClickBatteryContained();
                break;
            case R.id.trBattery:
                activityDevice.onClickDeviceBattery();
                break;
            case R.id.trBackcoverContained:
                activityDevice.onClickBackcoverContained();
                break;
            case R.id.trDamages:
                activityDevice.onClickDamages();
                break;
            case R.id.trState:
                activityDevice.onClickState();
                break;
        }
    }
}