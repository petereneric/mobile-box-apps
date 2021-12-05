package com.example.ericschumacher.bouncer.Fragments.Object;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Device extends Fragment_Object implements View.OnClickListener {

    // Header
    TextView tvSubtitle;
    ImageView ivHeaderLeft;
    ImageView ivHeaderRight;

    TableRow trColor;
    TableRow trShape;
    TableRow trState;
    TableRow trDamages;
    TableRow trStation;
    TableRow trLKU;
    TableRow trBatteryContained;
    TableRow trBattery;
    TableRow trBackcoverContained;

    TextView tvLKU;
    TextView tvStation;
    TextView tvColor;
    TextView tvShape;
    TextView tvBatteryContained;
    TextView tvBattery;
    TextView tvBackcoverContained;
    TextView tvDamages;
    TextView tvState;
    public View lMenu;

    // Visibility
    boolean bShowAll;

    // Interface
    Interface_Device iFragmentDevice;

    // Object
    Device oDevice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Interface
        iFragmentDevice = (Interface_Device)getActivity();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        // Initiate
        tvTitle = vLayout.findViewById(R.id.tvTitle);
        tvSubtitle = vLayout.findViewById(R.id.tvSubtitle);
        ivHeaderLeft = vLayout.findViewById(R.id.ivHeaderLeft);
        ivHeaderRight = vLayout.findViewById(R.id.ivHeaderRight);
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

        lMenu = vLayout.findViewById(R.id.lMenu);

        // Data
        tvTitle.setText(getString(R.string.device));

        // Visibility
        ivAdd.setVisibility(View.GONE);
        ivPause.setVisibility(View.GONE);
        ivClear.setVisibility(View.GONE);
        ivDelete.setVisibility(View.GONE);
        ivDone.setVisibility(View.GONE);

        // OnClickListener
        trLKU.setOnClickListener(this);
        trStation.setOnClickListener(this);
        trColor.setOnClickListener(this);
        trShape.setOnClickListener(this);
        trBatteryContained.setOnClickListener(this);
        trBattery.setOnClickListener(this);
        trBackcoverContained.setOnClickListener(this);
        trDamages.setOnClickListener(this);
        ivHeaderLeft.setOnClickListener(this);
        ivHeaderRight.setOnClickListener(this);

        ivHeaderRight.setVisibility(View.VISIBLE);
        ivHeaderRight.setColorFilter(getActivity().getResources().getColor(R.color.color_divider));
        bShowAll = false;
    }

    public int getIdLayout() {
        return R.layout.fragment_device;
    }

    public void updateLayout() {
        oDevice = iFragmentDevice.getDevice();

        if (oDevice != null) {
            if (oDevice.getoModelColor() != null) {
                tvColor.setText(oDevice.getoModelColor().getoColor().getcNameDE());
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
                trLKU.setVisibility(View.VISIBLE);
                tvLKU.setText(Integer.toString(oDevice.getoStoragePlace().getkLku())+" ("+oDevice.getoStoragePlace().getnPosition()+")");
            } else {
                trLKU.setVisibility(View.GONE);
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
            if (oDevice.isBatteryContained() != null && oDevice.getoBattery() != null) {
                tvBattery.setText(oDevice.getoBattery().getName());
            } else {
                tvBattery.setText(getString(R.string.unknown));
            }
            if (oDevice.isBackcoverContained() != null) {
                tvBackcoverContained.setText(oDevice.isBackcoverContained() ? getString(R.string.yes) : getString(R.string.no));
            } else {
                tvBackcoverContained.setText(getString(R.string.unknown));
            }
        }

        // Clap-In & -Out
        updateVisibility();
    }

    public void updateVisibility() {
        int bVisibility;
        if (bShowAll) {
            bVisibility = View.VISIBLE;
            ivHeaderRight.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_expand_more));

        } else {
            bVisibility = View.GONE;
            ivHeaderRight.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_expand_less));
        }
        setVisibility(bVisibility);

    }

    public void setVisibility(int bVisibility) {
        trState.setVisibility(bVisibility);
        trDamages.setVisibility(bVisibility);
        trStation.setVisibility(bVisibility);
        trBattery.setVisibility(bVisibility);
        trBackcoverContained.setVisibility(bVisibility);
    }

    public void showAll(boolean bShowAll) {
        this.bShowAll = bShowAll;
        updateVisibility();
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.trLKU:
                iFragmentDevice.requestLku();
                //requestLKU();
                break;
            case R.id.trStation:
                iFragmentDevice.requestStation();
                break;
            case R.id.trColor:
                iFragmentDevice.requestColor();
                break;
            case R.id.trShape:
                iFragmentDevice.requestShape();
                break;
            case R.id.trBatteryContained:
                iFragmentDevice.requestBatteryContained();
                break;
            case R.id.trBattery:
                iFragmentDevice.requestDeviceBattery();
                break;
            case R.id.trBackcoverContained:
                iFragmentDevice.requestBackcoverContained();
                break;
            case R.id.trDamages:
                iFragmentDevice.requestDamages();
                break;
            case R.id.trState:
                iFragmentDevice.requestState();
                break;
            case R.id.ivHeaderRight:
                bShowAll = !bShowAll;
                updateLayout();
                break;
        }
    }

    public interface Interface_Device {
        Device getDevice();
        void requestLku();
        void requestStation();
        void requestColor();
        void requestShape();
        void requestBatteryContained();
        void requestDeviceBattery();
        void requestBackcoverContained();
        void requestDamages();
        void requestState();
        void printDevice();
        void printDeviceBattery();
    }
}
