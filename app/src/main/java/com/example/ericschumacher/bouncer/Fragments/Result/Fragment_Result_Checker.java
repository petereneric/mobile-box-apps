package com.example.ericschumacher.bouncer.Fragments.Result;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Checker;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;
import com.example.ericschumacher.bouncer.Objects.Object_Device_Damage;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Result_Checker extends Fragment_Result implements Interface_Update {

    // Interfaces
    Interface_Fragment_Checker iChecker;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout(LayoutInflater inflater, @Nullable ViewGroup container) {
        super.setLayout(inflater, container);

        tvInteractionTitle.setVisibility(View.VISIBLE);
        tvInteractionTitle.setText(getString(R.string.handler));
    }

    @Override
    public void setScreen() {
        // Check if there is diagnose that can be shown
        if (iChecker != null) {
            Log.i("hier lk", "kk");
            if (iChecker.getDiagnoses().size() == 0) {
                // No diagnoses
                Log.i("diagnose", "nlskdjf");
                llInformation.setVisibility(View.VISIBLE);
                tvInformation.setText(getString(R.string.no_diagnoses_created_yet));

            } else {
                if (iChecker.getDiagnoses().get(0).isbFinished()) {
                    // Last diagnose is finished
                    Log.i("diagnose finished", "nlskdjf");
                    llInformation.setVisibility(View.GONE);

                    // Show result
                    if (iChecker.getDiagnoses().get(0).isbPassed()) {
                        // Diagnose passed
                        if (iDevice.getDevice().isBackcoverContained()) {
                            // Backcover contained
                            if (iDevice.getDevice().getlDeviceDamages().size() > 0) {
                                boolean bDust = false;
                                for (Object_Device_Damage oDeviceDamage : iDevice.getDevice().getlDeviceDamages()) {
                                    if (oDeviceDamage.getoModelDamage().getId() == 10) {
                                        bDust = true;
                                    }
                                }
                                if (!bDust) {
                                    // No dust
                                    llReuse.setVisibility(View.VISIBLE);
                                    tvReuse.setVisibility(View.GONE);
                                    llReuseObjects.setVisibility(View.VISIBLE);
                                    tvReuseDevice.setVisibility(View.VISIBLE);
                                    if (iDevice.getDevice().isBatteryContained()) {
                                        // Battery contained
                                        tvReuseDevice.setText(getString(R.string.load_with_battery));
                                    } else {
                                        // Battery not contained
                                        tvReuseDevice.setText(getString(R.string.load_without_battery));
                                    }
                                } else {
                                    // With dust
                                    llDefectRepair.setVisibility(View.VISIBLE);
                                    llDefectRepairObjects.setVisibility(View.VISIBLE);
                                    tvDefectRepairDevice.setVisibility(View.VISIBLE);
                                    tvDefectRepairDevice.setText(getString(R.string.remove_dust));
                                }
                            }
                        } else {
                            // Backcover not contained
                            llDefectRepair.setVisibility(View.VISIBLE);
                            llDefectRepairObjects.setVisibility(View.VISIBLE);
                            tvDefectRepairDevice.setVisibility(View.VISIBLE);
                            tvDefectRepairDevice.setText(getString(R.string.attach_backcover));
                        }
                    } else {
                        // Diagnose failed
                        if (iDevice.getDevice().getoModel().getlModelDamages().size() >= 2) {
                            // Repair possible
                            llDefectRepair.setVisibility(View.VISIBLE);
                            if (iDevice.getDevice().isBatteryContained()) {
                                // Battery contained
                                tvDefectRepairDevice.setVisibility(View.VISIBLE);
                                if (iDevice.getDevice().getoBattery().getlStock() < 2) {
                                    // Battery reuse
                                    llReuse.setVisibility(View.VISIBLE);
                                    llReuseObjects.setVisibility(View.VISIBLE);
                                    tvReuseBattery.setVisibility(View.VISIBLE);
                                } else {
                                    // Battery recycling
                                    llRecycling.setVisibility(View.VISIBLE);
                                    llRecyclingObjects.setVisibility(View.VISIBLE);
                                    tvRecyclingBattery.setVisibility(View.VISIBLE);
                                }
                            } else {
                                // Battery not contained
                                // --
                            }

                        } else {
                            // Repair not possible
                            llRecycling.setVisibility(View.VISIBLE);
                            if (iDevice.getDevice().isBackcoverContained()) {
                                // Backcover contained
                                if (iDevice.getDevice().getoShape().getId() == 4) {
                                    // Backcover recycling
                                    if (iDevice.getDevice().isBatteryContained()) {
                                        // Battery contained
                                        if (iDevice.getDevice().getoBattery().getlStock() < 2) {
                                            // Battery reuse
                                            llRecyclingObjects.setVisibility(View.VISIBLE);
                                            tvRecyclingDevice.setVisibility(View.VISIBLE);
                                            tvRecyclingBackcover.setVisibility(View.VISIBLE);
                                            llReuse.setVisibility(View.VISIBLE);
                                            llReuseObjects.setVisibility(View.VISIBLE);
                                            tvReuseBattery.setVisibility(View.VISIBLE);
                                        } else {
                                            // Battery recycling
                                            // --
                                        }
                                    } else {
                                        // Battery not contained
                                        // --
                                    }
                                } else {
                                    // Backcover reuse
                                    llRecyclingObjects.setVisibility(View.VISIBLE);
                                    tvRecyclingDevice.setVisibility(View.VISIBLE);
                                    llReuse.setVisibility(View.VISIBLE);
                                    llReuseObjects.setVisibility(View.VISIBLE);
                                    tvReuseBackcover.setVisibility(View.VISIBLE);
                                    if (iDevice.getDevice().isBatteryContained()) {
                                        // Battery contained
                                        if (iDevice.getDevice().getoBattery().getlStock() < 2) {
                                            // Battery reuse
                                            tvReuseBattery.setVisibility(View.VISIBLE);
                                        } else {
                                            // Battery recycling
                                            tvReuseBattery.setVisibility(View.VISIBLE);
                                        }
                                    } else {
                                        // Battery not contained
                                        // --
                                    }
                                }

                            } else {
                                // Backcover not contained
                                if (iDevice.getDevice().isBatteryContained()) {
                                    // Battery contained
                                    if (iDevice.getDevice().getoBattery().getlStock() < 2) {
                                        // Battery reuse
                                        llRecyclingObjects.setVisibility(View.VISIBLE);
                                        tvRecyclingDevice.setVisibility(View.VISIBLE);
                                        llReuse.setVisibility(View.VISIBLE);
                                        llReuseObjects.setVisibility(View.VISIBLE);
                                        tvReuseBattery.setVisibility(View.VISIBLE);
                                    } else {
                                        // Battery recycling
                                        // --
                                    }

                                } else {
                                    // Battery not contained
                                    // --
                                }
                            }
                        }
                    }

                } else {
                    // Last diagnose not finished
                    Log.i("diagnose not finished", "nlskdjf");
                    llInformation.setVisibility(View.VISIBLE);
                    tvInformation.setText(getString(R.string.please_finish_diagnose));
                }
            }
        }
    }

    @Override
    public void update() {
        setScreen();
    }

    @Override
    public void setInterface() {
        super.setInterface();
        iChecker = (Interface_Fragment_Checker)getTargetFragment();
    }

    @Override
    public void click() {
        if (iChecker.getDiagnoses().size() > 0 && iChecker.getDiagnoses().get(0).isbFinished()) {
            iResult.returnResult(getTag());
        } else {
            iChecker.showTab(0);
        }
    }
}
