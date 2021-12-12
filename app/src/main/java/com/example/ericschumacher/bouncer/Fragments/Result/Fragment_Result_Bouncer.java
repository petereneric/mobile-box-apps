package com.example.ericschumacher.bouncer.Fragments.Result;

import android.util.Log;
import android.view.View;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Backcover;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Result_Bouncer extends Fragment_Result {

    // Interface
    Interface_Backcover iBackcover;

    @Override
    public void setInterface() {
        super.setInterface();
        iBackcover = (Interface_Backcover)getActivity();
    }

    @Override
    public void setScreen() {
        super.setScreen();
        // Depending on data
        if (oDevice.gettState() == Constants_Intern.STATE_MODEL_UNKNOWN) {
            llUnknown.setVisibility(View.VISIBLE);
            llUnknownObjects.setVisibility(View.VISIBLE);
            tvUnknownModel.setVisibility(View.VISIBLE);
        } else {
            if (oDevice.getoModel() != null) {
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
                    case  Constants_Intern.DEFAULT_EXPLOITATION_TBD:
                        switch (oDevice.gettState()) {
                            case Constants_Intern.STATE_INTACT_REUSE:
                            case Constants_Intern.STATE_DEFECT_REUSE:
                                llReuse.setVisibility(View.VISIBLE);
                                if (oDevice.getoStation().getId() == Constants_Intern.STATION_TBD || oDevice.getoModel().gettDefaultExploitation() == Constants_Intern.DEFAULT_EXPLOITATION_TBD) {
                                    tvReuse.setText(getString(R.string.station_market_check));
                                } else {
                                    tvReuse.setText(getString(R.string.reuse_station_lku_stock));
                                }
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
                                    llRecyclingObjects.setVisibility(View.VISIBLE);
                                    tvRecyclingBattery.setVisibility(View.VISIBLE);
                                    tvRecyclingDevice.setVisibility(View.VISIBLE);
                                }
                                if (oDevice.getoModel().isBackcoverRemovable() && oDevice.isBackcoverContained()) {
                                    if (iBackcover.getBackcover() != null && iBackcover.getBackcover().gettState() == Constants_Intern.STATE_RECYCLING) {
                                        llRecyclingObjects.setVisibility(View.VISIBLE);
                                        tvRecyclingBackcover.setVisibility(View.VISIBLE);
                                        tvRecyclingDevice.setVisibility(View.VISIBLE);
                                    } else {
                                        llRecyclingObjects.setVisibility(View.VISIBLE);
                                        llReuseObjects.setVisibility(View.VISIBLE);
                                        llReuse.setVisibility(View.VISIBLE);
                                        tvReuseBackcover.setVisibility(View.VISIBLE);
                                        tvRecyclingDevice.setVisibility(View.VISIBLE);
                                    }

                                }
                                break;
                            case Constants_Intern.STATE_DEFECT_REPAIR:
                                llRepair.setVisibility(View.VISIBLE);
                                if (oDevice.getoModel().isBatteryRemovable() && oDevice.isBatteryContained() && oDevice.getoBattery().getlStock() < 2) {
                                    llDefectRepairObjects.setVisibility(View.VISIBLE);
                                    tvDefectRepairDevice.setVisibility(View.VISIBLE);
                                    llReuseObjects.setVisibility(View.VISIBLE);
                                    llReuse.setVisibility(View.VISIBLE);
                                    tvReuseBattery.setVisibility(View.VISIBLE);
                                }
                                if (oDevice.getoModel().isBatteryRemovable() && oDevice.isBatteryContained() && oDevice.getoBattery().getlStock() >= 2) {
                                    llRecycling.setVisibility(View.VISIBLE);
                                    llRecyclingObjects.setVisibility(View.VISIBLE);
                                    tvRecyclingBattery.setVisibility(View.VISIBLE);
                                    llDefectRepairObjects.setVisibility(View.VISIBLE);
                                    tvDefectRepairDevice.setVisibility(View.VISIBLE);
                                }
                                if (oDevice.getoModel().isBackcoverRemovable() && oDevice.isBackcoverContained()) {
                                    llReuseObjects.setVisibility(View.VISIBLE);
                                    llReuse.setVisibility(View.VISIBLE);
                                    tvReuseBackcover.setVisibility(View.VISIBLE);
                                    llDefectRepairObjects.setVisibility(View.VISIBLE);
                                    tvDefectRepairDevice.setVisibility(View.VISIBLE);
                                }
                                //tvDefectRepairDevice.setVisibility(View.VISIBLE);
                                break;
                        }
                        break;
                }
            } else {
                switch (oDevice.gettState()) {
                    case Constants_Intern.STATE_RECYCLING:
                        llRecycling.setVisibility(View.VISIBLE);
                        break;
                }
            }

        }
    }
}
