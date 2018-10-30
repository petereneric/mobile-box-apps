package com.example.ericschumacher.bouncer.Activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.example.ericschumacher.bouncer.Activities.Parent.Activity_Device;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Overview.Fragment_Overview_Selection;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Result;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Dialog;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Manager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Overview_Selection;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Int;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.Objects.Additive.Station;
import com.example.ericschumacher.bouncer.Objects.Additive.Variation_Color;
import com.example.ericschumacher.bouncer.Objects.Additive.Variation_Shape;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;

public class Activity_Bouncer extends Activity_Device implements Interface_Selection, View.OnClickListener, Interface_Manager, Interface_Dialog {

    // Interfaces
    Interface_Overview_Selection iOverviewSelection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Objects
        oDevice = new Device();

        // Layout
        Fragment fOverview = new Fragment_Overview_Selection();
        fManager.beginTransaction().add(R.id.flFragmentOverview, fOverview, Constants_Intern.FRAGMENT_OVERVIEW).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        iOverviewSelection = (Interface_Overview_Selection) fManager.findFragmentByTag(Constants_Intern.FRAGMENT_OVERVIEW);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        //updateUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onScan(String text) {
        if (text.length() == 15) {
            oDevice.setIMEI(text);

            if (text.equals(Constants_Intern.UNKNOWN_IMEI)) {
                bounce();

            } else {
                uNetwork.getModelByTac(oDevice, new Interface_VolleyCallback() {
                    @Override
                    public void onSuccess() {
                        bounce();
                    }
                    @Override
                    public void onFailure() {
                        bounce();
                    }
                });
            }
            closeKeyboard(etScan);
        }
    }

    // Fragments
    @Override
    public void showResult() {
        Fragment_Result f = new Fragment_Result();
        Bundle b = new Bundle();
        b.putParcelable(Constants_Intern.OBJECT_MODEL, oDevice);
        f.setArguments(b);
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, f, Constants_Intern.FRAGMENT_REQUEST).commit();
    }

    // Logic of Bouncer
    public void bounce() {
        updateUI();
        if (oDevice.getName() == null) {
            iDevice.requestName();
        } else {
            if (oDevice.getExploitation() == Constants_Intern.EXPLOITATION_RECYCLING) {
                oDevice.setDestination(Constants_Intern.EXPLOITATION_RECYCLING);
                showResult();
            } else {
                if (oDevice.getExploitation() == Constants_Intern.EXPLOITATION_NULL) {
                    iDevice.requestDefaultExploitation(oDevice);
                } else {
                    if (oDevice.getExploitation() == Constants_Intern.EXPLOITATION_REUSE) {
                        if (oDevice.getManufacturer() == null) {
                            iDevice.requestManufacturer();
                        } else {
                            if (oDevice.getCharger() == null) {
                                iDevice.requestCharger(oDevice);
                            } else {
                                if (oDevice.getBattery() == null) {
                                    iDevice.requestBattery(oDevice);
                                } else {
                                    if (oDevice.getCondition() == Constants_Intern.CONDITION_UNKNOWN) {
                                        iDevice.requestCondition();
                                    } else {
                                        if (oDevice.getVariationShape() == null) {
                                            iDevice.requestShape();
                                        } else {
                                            if (oDevice.getVariationColor() == null) {
                                                iDevice.requestColor(oDevice);
                                            } else {
                                                showResult();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


            }
        }
    }

    @Override
    public void afterBounce() {
        switch (oDevice.getExploitation()) {
            case Constants_Intern.EXPLOITATION_RECYCLING:
                iOverviewSelection.incrementCounterRecycling();
                resetDevice();
                break;
            case Constants_Intern.EXPLOITATION_REUSE:
                iOverviewSelection.incrementCounterReuse();
                oDevice.setStation(new Station(Constants_Intern.STATION_PRESORT_INT));
                //addDevice();
                if (usePrinter) mPrinter.printDevice(oDevice);
                resetDevice();
                break;
        }
        updateUI();
    }

    // Specific methods
    public void addDevice() {
        if (oDevice.getIdDevice() == Constants_Intern.ID_UNKNOWN) {
            uNetwork.addDevice(oDevice, new Interface_VolleyCallback_Int() {
                @Override
                public void onSuccess(int i) {
                    oDevice.setIdDevice(i);
                    if (usePrinter) mPrinter.printDevice(oDevice);
                    resetDevice();
                    /*
                    uNetwork.assignLku(oDevice, new Interface_VolleyCallback_Int() {
                        @Override
                        public void onSuccess(int i) {
                            oDevice.setLKU(i);
                            Toast.makeText(Activity_Device.this, "LKU: " + Integer.toString(i), Toast.LENGTH_LONG).show();
                            if (usePrinter) mPrinter.printDevice(oDevice);
                            resetDevice();
                        }

                        @Override
                        public void onFailure() {

                        }
                    });
                    */
                }

                @Override
                public void onFailure() {

                }
            });
        } else {
            updateDevice();
        }
    }

    // Return Methods

    @Override
    public void returnColor(Variation_Color color) {
        super.returnColor(color);
        bounce();
    }

    @Override
    public void returnCondition(int condition) {
        super.returnCondition(condition);
        bounce();
    }

    @Override
    public void returnShape(Variation_Shape shape) {
        super.returnShape(shape);
        bounce();
    }

    @Override
    public void returnDefaultExploitation(int exploitation) {
        super.returnDefaultExploitation(exploitation);
        bounce();
    }

    @Override
    public void unknownBattery() {
        oDevice.setBattery(new Battery(Constants_Intern.ID_UNKNOWN, Constants_Intern.UNKNOWN_NAME));
        bounce();
    }

    // Handled Returns
    @Override
    public void handledReturnModel() {
        bounce();
    }

    @Override
    public void handledReturnBattery() {
        bounce();
    }

    @Override
    public void handledReturnManufacturer() {
        bounce();
    }

    public void handledReturnDefaultExploitation() {
        bounce();
    }

    @Override
    public void handledReturnCharger() {
        bounce();
    }

    // Reset
    @Override
    public void totalReset() {
        totalReset();
        iOverviewSelection.reset();
    }

    // UI
    @Override
    public void updateUI() {
        super.updateUI();
        iOverviewSelection.updateUI();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ivHelp:
                etScan.setText(Constants_Intern.UNKNOWN_IMEI);
                Log.i("onClick", "ivHelp");
                break;
        }
    }


    @Override
    public void onYes(int type) {
        switch (type) {
            case Constants_Intern.TYPE_FRAGMENT_DIALOG_TOTAL_RESET:
                totalReset();
        }
    }

    @Override
    public void onNo() {
        bounce();
    }
}
