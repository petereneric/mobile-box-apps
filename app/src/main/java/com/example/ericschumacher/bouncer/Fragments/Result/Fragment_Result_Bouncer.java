package com.example.ericschumacher.bouncer.Fragments.Result;

import android.util.Log;
import android.view.View;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Result_Bouncer extends Fragment_Result {

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
                                llReuse.setVisibility(View.VISIBLE);
                                if (oDevice.getoStation().getId() == Constants_Intern.STATION_CHECK_ONE) {
                                    tvReuse.setText(getString(R.string.reuse_station_check_one));
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
