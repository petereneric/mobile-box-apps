package com.example.ericschumacher.bouncer.Fragments;

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

import com.example.ericschumacher.bouncer.Activities.Activity_Bouncer_New;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Unit_Backcover;
import com.example.ericschumacher.bouncer.Objects.Unit_Battery;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Result_New extends Fragment implements View.OnClickListener {

    // Data
    Device oDevice;
    Unit_Battery uBattery;
    Unit_Backcover uBackcover;

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

    // Interface
    Activity_Bouncer_New aBouncer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Data
        Bundle bArguments = getArguments();
        oDevice = (Device) bArguments.getSerializable(Constants_Intern.OBJECT_DEVICE);
        uBattery = (Unit_Battery) bArguments.getSerializable(Constants_Intern.UNIT_BATTERY);
        uBackcover = (Unit_Backcover) bArguments.getSerializable(Constants_Intern.UNIT_BACKCOVER);

        // vLayout
        setLayout(inflater, container);

        // Interface
        aBouncer = (Activity_Bouncer_New)getActivity();

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

        rlResult.setOnClickListener(this);

        tvInteractionType.setText(getString(R.string.result));
        tvInteractionTitle.setVisibility(View.GONE);

        // Depending on data
        Log.i("Jo", Integer.toString(oDevice.getoModel().gettDefaultExploitation()));
        switch (oDevice.getoModel().gettDefaultExploitation()) {
            case Constants_Intern.EXPLOITATION_NULL:
                switch (oDevice.gettState()) {
                    case Constants_Intern.STATE_UNKNOWN:
                        llUnknown.setVisibility(View.VISIBLE);
                        llUnknownObjects.setVisibility(View.VISIBLE);
                        tvUnknownExploitation.setVisibility(View.VISIBLE);
                        break;
                    case Constants_Intern.STATE_MODEL_UNKNOWN:
                        llUnknown.setVisibility(View.VISIBLE);
                        llUnknownObjects.setVisibility(View.VISIBLE);
                        tvUnknownModel.setVisibility(View.VISIBLE);
                }
                break;
            case Constants_Intern.EXPLOITATION_RECYCLING:
                Log.i("Jo", "Joo");
                llRecycling.setVisibility(View.VISIBLE);
                break;
            case Constants_Intern.EXPLOITATION_INTACT_REUSE:
            case  Constants_Intern.EXPLOITATION_DEFECT_REUSE:
                switch (oDevice.gettState()) {
                    case Constants_Intern.STATE_INTACT_REUSE:
                        llReuse.setVisibility(View.VISIBLE);
                        //tvReuseDevice.setVisibility(View.VISIBLE);
                        if (oDevice.getoModel().isBatteryRemovable() && oDevice.isBatteryContained()) {
                            //tvReuseBattery.setVisibility(View.VISIBLE);
                        }
                        if (oDevice.getoModel().isBatteryRemovable() && oDevice.isBatteryContained() && oDevice.getoBattery() != null && oDevice.getoModel().getModelBatteryByBattery(oDevice.getoBattery()).gettStatus() == 2) {
                            llRecycling.setVisibility(View.VISIBLE);
                            llRecyclingObjects.setVisibility(View.VISIBLE);
                            tvRecyclingBattery.setVisibility(View.VISIBLE);
                            llReuseObjects.setVisibility(View.VISIBLE);
                            tvReuseDevice.setVisibility(View.VISIBLE);
                        }
                        break;
                    case Constants_Intern.STATE_RECYCLING:
                        llRecycling.setVisibility(View.VISIBLE);
                        if (oDevice.getoModel().isBatteryRemovable() && oDevice.isBatteryContained() && oDevice.getoBattery().getlStock() < 2) {
                            llRecyclingObjects.setVisibility(View.VISIBLE);
                            llReuseObjects.setVisibility(View.VISIBLE);
                            llReuse.setVisibility(View.VISIBLE);
                            tvReuseBattery.setVisibility(View.VISIBLE);
                            tvRecyclingDevice.setVisibility(View.VISIBLE);
                        }
                        if (oDevice.getoModel().isBatteryRemovable() && oDevice.isBatteryContained() && oDevice.getoBattery().getlStock() >= 2) {
                            tvRecyclingBattery.setVisibility(View.VISIBLE);
                            tvRecyclingDevice.setVisibility(View.VISIBLE);
                        }
                        if (oDevice.getoModel().isBackcoverRemovable() && oDevice.isBackcoverContained()) {
                            llRecyclingObjects.setVisibility(View.VISIBLE);
                            llReuseObjects.setVisibility(View.VISIBLE);
                            llReuse.setVisibility(View.VISIBLE);
                            tvReuseBackcover.setVisibility(View.VISIBLE);
                            tvRecyclingDevice.setVisibility(View.VISIBLE);
                        }
                        break;
                    case Constants_Intern.STATE_DEFECT_REPAIR:
                        llDefectRepair.setVisibility(View.VISIBLE);
                        //tvDefectRepairDevice.setVisibility(View.VISIBLE);
                        break;
                    case Constants_Intern.STATE_DEFECT_REUSE:
                        llDefectReuse.setVisibility(View.VISIBLE);
                        //tvDefectReuseDevice.setVisibility(View.VISIBLE);
                        break;
                }
//                if (oDevice.gettState() == Constants_Intern.STATE_INTACT_REUSE) {
//                    tvReuseDevice.setVisibility(View.VISIBLE);
//                    if (oDevice.isBatteryContained()) {
//                        tvReuseBattery.setVisibility(View.VISIBLE);
//                    }
//                }
                break;

        }
//        switch (oDevice.gettState()) {
//            case Constants_Intern.STATE_RECYCLING:
//                llRecycling.setVisibility(View.VISIBLE);
//                tvRecyclingDevice.setVisibility(View.VISIBLE);
//                tvRecyclingBattery.setVisibility(View.VISIBLE);
//                tvRecyclingBackcover.setVisibility(View.VISIBLE);
//                break;
//            case Constants_Intern.STATE_INTACT_REUSE:
//                llReuse.setVisibility(View.VISIBLE);
//                tvReuseDevice.setVisibility(View.VISIBLE);
//                if (oDevice.isBatteryContained() && uBattery != null && uBattery.isbBatteryIntact() == false) {
//                    tvRecyclingBattery.setVisibility(View.VISIBLE);
//                }
//                if (oDevice.isBackcoverContained() && uBackcover != null && uBackcover.getkShape() == 5) {
//                    tvRecyclingBackcover.setVisibility(View.VISIBLE);
//                }
//                break;
//            case Constants_Intern.STATE_DEFECT_REPAIR:
//                llDefectRepair.setVisibility(View.VISIBLE);
//                tvDefectRepairDevice.setVisibility(View.VISIBLE);
//                if (oDevice.isBatteryContained() && uBattery != null && uBattery.isbBatteryIntact() == false) {
//                    tvRecyclingBattery.setVisibility(View.VISIBLE);
//                }
//                if (oDevice.isBackcoverContained() && uBackcover != null && uBackcover.getkShape() == 5) {
//                    tvRecyclingBackcover.setVisibility(View.VISIBLE);
//                }
//                break;
//            case Constants_Intern.STATE_DEFECT_REUSE:
//                llDefectReuse.setVisibility(View.VISIBLE);
//                tvDefectReuseDevice.setVisibility(View.VISIBLE);
//                if (oDevice.isBatteryContained() && uBattery != null && uBattery.isbBatteryIntact() == false) {
//                    tvRecyclingBattery.setVisibility(View.VISIBLE);
//                }
//                if (oDevice.isBackcoverContained() && uBackcover != null && uBackcover.getkShape() == 5) {
//                    tvRecyclingBackcover.setVisibility(View.VISIBLE);
//                }
//                break;
//        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlResult:
                aBouncer.returnResult(getTag());
                break;
        }
    }
}
