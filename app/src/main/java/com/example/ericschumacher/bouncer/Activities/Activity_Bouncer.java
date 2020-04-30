package com.example.ericschumacher.bouncer.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Activities.Parent.Activity_Device;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Overview.Fragment_Overview_Selection;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Record.Fragment_Record_Existing;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Record.Fragment_Record_Menu;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Record.Fragment_Record_New;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Result;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Result_New;
import com.example.ericschumacher.bouncer.Interfaces.Interface_DeviceManager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Dialog;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Overview_Selection;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Int;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;
import com.example.ericschumacher.bouncer.Objects.Additive.Shape;
import com.example.ericschumacher.bouncer.Objects.Additive.Station;
import com.example.ericschumacher.bouncer.Objects.Collection.Record;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.Objects.Unit_Backcover;
import com.example.ericschumacher.bouncer.Objects.Unit_Battery;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

public class Activity_Bouncer extends Activity_Device implements Interface_Selection, View.OnClickListener, Interface_DeviceManager, Interface_Dialog {

    // Interfaces
    Interface_Overview_Selection iOverviewSelection;

    // Objects
    Record oRecord;

    // Units
    Unit_Battery uBattery;
    Unit_Backcover uBackcover;

    // Connection
    Volley_Connection vConnection;
    private final static String URL_ADD_DEVICE = "http://svp-server.com/svp-gmbh/erp/bouncer/src/api.php/device/add/2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connection
        vConnection = new Volley_Connection(this);

        // Objects
        oDevice = new Device();
        uBattery = null;
        uBackcover = null;

        oRecord = null;

        // Layout
        Fragment_Record_Menu fRecordMenu = new Fragment_Record_Menu();
        fManager.beginTransaction().add(R.id.flFragmentOverview, fRecordMenu, Constants_Intern.FRAGMENT_RECORD_MENU).commit();

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Keyboard
        closeKeyboard(etScan);
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
        if (oRecord != null) {
            oDevice.setkRecord(oRecord.getId());
            if (text.length() == 15) {
                oDevice.setIMEI(text);

                if (text.equals(Constants_Intern.UNKNOWN_IMEI)) {
                    bounce();
                } else {
                    cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_BY_TAC + oDevice.getTAC(), null, new Interface_VolleyResult() {
                        @Override
                        public void onResult(JSONObject oJson) throws JSONException {
                            if (oJson.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS))
                                oDevice.setoModel(new Model(Activity_Bouncer.this, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL)));
                            bounce();
                        }
                    });
                    /*
                    uNetwork.getModelByTac(oDevice, new Interface_VolleyCallback_FailureResponse() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure(JSONObject json) {
                            try {
                                if (json.getString(Constants_Extern.DETAILS).equals(Constants_Extern.IMEI_EXISTS_ALREADY)) {
                                    Toast.makeText(Activity_Bouncer.this, getString(R.string.bouncer_imei_exists), Toast.LENGTH_LONG).show();
                                    resetDevice();
                                } else {
                                    bounce();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    */
                }
                closeKeyboard(etScan);
            }
        } else {
            etScan.setText("");
            Toast.makeText(this, "please select collector", Toast.LENGTH_LONG).show();
        }
    }

    // Fragments
    @Override
    public void showResult() {
        /*
        Fragment_Interaction_Simple_Choice fResult = new Fragment_Interaction_Simple_Choice();
        Bundle bundle = new Bundle();
        bundle.putString(Constants_Intern.TITLE_MAIN, getString(R.string.result));
        String[] lTitle;
        String[] lButtons;
        if (oDevice.getCondition() == Constants_Intern.CONDITION_BROKEN ) {
            if (oDevice.getoModel().getoBattery() != null) {
                if (oDevice.getoModel().getoBattery().gettState() == Constants_Intern.EXPLOITATION_RECYCLING) {
                    lTitle = new String[1];
                    lTitle[0] = getString(R.string.recycling);
                    lButtons = new String[2];
                }
            }

        }
        */


        Fragment_Result_New fResult = new Fragment_Result_New();
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants_Intern.OBJECT_DEVICE, oDevice);
        //bundle.putSerializable(Constants_Intern.UNIT_BATTERY, uBattery);
        //bundle.putSerializable(Constants_Intern.UNIT_BACKCOVER, uBackcover);

        fResult.setArguments(bundle);
        //fManager.beginTransaction().replace(R.id.flFragmentInteraction, fResult, Constants_Intern.FRAGMENT_REQUEST_BATTERY_REMOVABLE).commit();

        /*
        String[] lTitle;
        String[] lButtons;
        int[] lColorIds;
        if (oDevice.getoModel().gettDefaultExploitation() == Constants_Intern.EXPLOITATION_RECYCLING || oDevice.getCondition() == Constants_Intern.CONDITION_BROKEN) {
            if (oDevice.getoModel().isBatteryRemovable()) {
                if (oDevice.isBatteryContained()) {
                    if (oDevice.getoModel().getoBattery().getExploitation() == Constants_Intern.EXPLOITATION_RECYCLING) {
                        lTitle = new String[1];
                        lTitle[0] = getString(R.string.recycling);

                        lButtons = new String[2];
                        lButtons[0] = getString(R.string.device);
                        lButtons[1] = getString(R.string.battery);

                        lColorIds = new int[2];
                        lColorIds[0] = R.color.color_recycling;
                        lColorIds[1] = R.color.color_recycling;
                    } else {
                        lTitle = new String[2];
                        lTitle[0] = getString(R.string.reuse);
                        lTitle[1] = getString(R.string.recycling);

                        lButtons = new String[2];
                        lButtons[0] = getString(R.string.battery);
                        lButtons[1] = Constants_Intern.GONE;
                        lButtons[2] = Constants_Intern.GONE;
                        lButtons[3] = getString(R.string.device);

                        lColorIds = new int[2];
                        lColorIds[0] = R.color.color_reuse;
                        lColorIds[1] = R.color.color_white;
                        lColorIds[1] = R.color.color_white;
                        lColorIds[1] = R.color.color_recycling;
                    }
                } else {

                }

            } else {

            }


        }
        /*
        String[] lTitle = new String[1];
        lTitle[0] = getString(R.string.request_battery_removable_title);
        bundle.putStringArray(Constants_Intern.LIST_TITLE, lTitle);
        String[] lButtons = new String[2];
        lButtons[0] = getString(R.string.yes);
        lButtons[1] = getString(R.string.no);
        bundle.putStringArray(Constants_Intern.LIST_BUTTONS, lButtons);
        int[] lColorIds = {R.color.color_choice_positive, R.color.color_choice_negative};
        bundle.putIntArray(Constants_Intern.LIST_COLOR_IDS, lColorIds);
        fResult.setArguments(bundle);
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, fResult, Constants_Intern.FRAGMENT_REQUEST_BATTERY_REMOVABLE).commit();
        */


        Fragment_Result f = new Fragment_Result();
        Bundle b = new Bundle();
        b.putSerializable(Constants_Intern.OBJECT_MODEL, oDevice);
        f.setArguments(b);
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, fResult, Constants_Intern.FRAGMENT_REQUEST).commit();
    }

    // Logic of Bouncer
    public void bounce() {
        updateUI();
        if (oDevice.gettState() == Constants_Intern.STATE_MODEL_UNKNOWN) {
            showResult();
        } else {
            if (oDevice.getoModel().getkModel() == Constants_Intern.ID_UNKNOWN) {
                iDevice.requestName();
            } else {
                if (oDevice.getoModel().gettPhone() == null) {
                    iDevice.requestTypePhone();
                } else {
                    if (oDevice.getoModel().gettDefaultExploitation() == Constants_Intern.EXPLOITATION_RECYCLING || oDevice.gettState() == Constants_Intern.STATE_RECYCLING) {
                        oDevice.settState(Constants_Intern.STATE_RECYCLING);
                        if (oDevice.getoModel().isBatteryRemovable() == null && oDevice.getoModel().gettDefaultExploitation() != Constants_Intern.EXPLOITATION_RECYCLING) {
                            iDevice.requestBatteryRemovable(oDevice);
                        } else {
                            if (oDevice.getoModel().isBackcoverRemovable() == null && oDevice.getoModel().gettDefaultExploitation() != Constants_Intern.EXPLOITATION_RECYCLING) {
                                iDevice.requestBackcoverRemovable(oDevice);
                            } else {
                                if (oDevice.getoModel().gettDefaultExploitation() != Constants_Intern.EXPLOITATION_RECYCLING && oDevice.getoModel().isBatteryRemovable() && oDevice.isBatteryContained() == null) {
                                    iDevice.requestBatteryContained();
                                } else {
                                    if (oDevice.getoModel().gettDefaultExploitation() != Constants_Intern.EXPLOITATION_RECYCLING && oDevice.getoModel().isBatteryRemovable() && oDevice.isBatteryContained() == true && oDevice.getoModel().getoBattery() == null) {
                                        iDevice.requestBattery(oDevice);
                                    } else {
                                        if (oDevice.getoModel().gettDefaultExploitation() != Constants_Intern.EXPLOITATION_RECYCLING && oDevice.getoModel().isBatteryRemovable() && oDevice.isBatteryContained() == true && oDevice.getoModel().getlModelBatteries().size()>1 && oDevice.getoBattery() == null) {
                                            requestDeviceBattery();
                                        } else {
                                            if (oDevice.getoModel().gettDefaultExploitation() != Constants_Intern.EXPLOITATION_RECYCLING && oDevice.getoModel().isBackcoverRemovable() && oDevice.isBackcoverContained() == null) {
                                                iDevice.requestBackcoverContained();
                                            } else {
                                                showResult();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        if (oDevice.getoModel().gettDefaultExploitation() == Constants_Intern.EXPLOITATION_NULL) {
                            showResult();
                        } else {
                            if (oDevice.getoModel().gettDefaultExploitation() == Constants_Intern.EXPLOITATION_INTACT_REUSE || oDevice.getoModel().gettDefaultExploitation() == Constants_Intern.EXPLOITATION_DEFECT_REUSE) {
                                if (oDevice.getoModel().getoManufacturer() == null) {
                                    iDevice.requestManufacturer();
                                } else {
                                    if (oDevice.getoModel().getoCharger() == null) {
                                        iDevice.requestCharger(oDevice);
                                    } else {
                                        if (oDevice.getoModel().isBatteryRemovable() == null) {
                                            iDevice.requestBatteryRemovable(oDevice);
                                        } else {
                                            if (oDevice.getoModel().isBackcoverRemovable() == null) {
                                                iDevice.requestBackcoverRemovable(oDevice);
                                            } else {
                                                if (oDevice.getoModel().isBatteryRemovable() && (oDevice.isBatteryContained() != null && oDevice.isBatteryContained() == true) && oDevice.getoModel().getoBattery() == null) {
                                                    iDevice.requestBattery(oDevice);
                                                } else {
                                                    if (oDevice.getCondition() == Constants_Intern.CONDITION_UNKNOWN && false) { // not used anymore
                                                        iDevice.requestCondition();
                                                    } else {
                                                        if (oDevice.getoShape() == null) {
                                                            iDevice.requestShape();
                                                        } else {
                                                            if (oDevice.getoColor() == null) {
                                                                iDevice.requestColor(oDevice);
                                                            } else {
                                                                if (oDevice.getoColor().getkModelColor() == 0 && mSharedPreferences.getBoolean(Constants_Intern.USE_CAMERA_MODEL_COLOR, false) && oDevice.getoColor().getkModelColor() != Constants_Intern.TAKE_NO_PICTURE) {
                                                                    takePictures(IMAGE_FRONT);
                                                                } else {
                                                                    if (oDevice.isBatteryContained() == null) {
                                                                        iDevice.requestBatteryContained();
                                                                    } else {
                                                                        if (oDevice.getoModel().isBatteryRemovable() && oDevice.isBatteryContained() && oDevice.getoBattery() == null) {
                                                                            requestDeviceBattery();
                                                                        } else {
                                                                            if (oDevice.isBackcoverContained() == null) { // Backcover
                                                                                iDevice.requestBackcoverContained();
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
        switch (oDevice.gettState()) {
            case Constants_Intern.STATE_UNKNOWN:
                if (oDevice.getoModel().gettDefaultExploitation() == Constants_Intern.EXPLOITATION_UNKNOWN) {
                    cVolley.execute(Request.Method.PUT, Urls.URL_INCREMENT_RECORD_RECYCLING + oRecord.getId(), null);
                    oRecord.incrementRecycling();
                    updateUI();
                    resetDevice();
                }
                break;
            case Constants_Intern.STATE_MODEL_UNKNOWN:
                oRecord.incrementRecycling();
                updateUI();
                resetDevice();
                break;
            case Constants_Intern.STATE_RECYCLING:
                cVolley.execute(Request.Method.PUT, Urls.URL_INCREMENT_RECORD_RECYCLING + oRecord.getId(), null);
                oRecord.incrementRecycling();
                if (oDevice.getoModel().gettDefaultExploitation() != Constants_Intern.EXPLOITATION_RECYCLING && oDevice.getoModel().isBatteryRemovable() && oDevice.isBatteryContained() && oDevice.getoBattery().getlStock() < 2) {
                    // Print Label for Battery
                    //Toast.makeText(this, getString(R.string.label_print_battery), Toast.LENGTH_LONG).show();
                    mPrinter.printBattery(oDevice.getoModel().getoBattery());
                    updateUI();
                    resetDevice();
                } else {
                    if (oDevice.getoModel().gettDefaultExploitation() != Constants_Intern.EXPLOITATION_RECYCLING && oDevice.getoModel().isBatteryRemovable() && oDevice.isBatteryContained() && oDevice.getoBattery().getlStock() > 1) {
                        //Toast.makeText(this, getString(R.string.no_label_print_battery), Toast.LENGTH_LONG).show();
                    }
                    updateUI();
                    resetDevice();
                }


                break;
            case Constants_Intern.STATE_DEFECT_REPAIR:
            case Constants_Intern.STATE_DEFECT_REUSE:
                cVolley.execute(Request.Method.PUT, Urls.URL_INCREMENT_RECORD_RECYCLING + oRecord.getId(), null);
                oRecord.incrementRecycling();
                oDevice.setoStation(new Station(Constants_Intern.STATION_PRE_STOCK));
                vConnection.getResponse(Request.Method.PUT, URL_ADD_DEVICE, oDevice.getJson(), new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) {
                        Log.i("Raus damit", oJson.toString());
                        try {
                            if (oJson.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                                oDevice.setIdDevice(oJson.getInt(Constants_Extern.ID_DEVICE));
                                //Toast.makeText(Activity_Bouncer.this, getString(R.string.label_print_device), Toast.LENGTH_LONG).show();
                                mPrinter.printDeviceId(oDevice);
                                resetDevice();
                                updateUI();
                            } else {
                                //Toast.makeText(Activity_Bouncer.this, "IMEI exists", Toast.LENGTH_LONG).show();
                                resetDevice();
                                updateUI();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case Constants_Intern.STATE_INTACT_REUSE:
                cVolley.execute(Request.Method.PUT, Urls.URL_INCREMENT_RECORD_REUSE + oRecord.getId(), null);
                oRecord.incrementReuse();
                oDevice.setoStation(new Station(Constants_Intern.STATION_PRE_STOCK));
                vConnection.getResponse(Request.Method.PUT, URL_ADD_DEVICE, oDevice.getJson(), new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) {
                        Log.i("Raus damit", oJson.toString());
                        try {
                            if (oJson.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                                oDevice.setIdDevice(oJson.getInt(Constants_Extern.ID_DEVICE));
                                mPrinter.printDeviceId(oDevice);
                                resetDevice();
                                updateUI();
                            } else {
                                //Toast.makeText(Activity_Bouncer.this, "IMEI exists", Toast.LENGTH_LONG).show();
                                resetDevice();
                                updateUI();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;

        }
        /*
        switch (oDevice.getoModel().gettDefaultExploitation()) {
            case Constants_Intern.EXPLOITATION_RECYCLING:

                uNetwork.recordRecycling(oRecord.getId(), new Interface_VolleyCallback() {
                    @Override
                    public void onSuccess() {
                        oRecord.incrementRecycling();
                        updateUI();
                        resetDevice();
                        Log.i("REEECyling:", Integer.toString(oRecord.getnRecycling()));

                    }

                    @Override
                    public void onFailure() {

                    }
                });
                break;
            case Constants_Intern.EXPLOITATION_INTACT_REUSE:

                uNetwork.recordReuse(oRecord.getId(), new Interface_VolleyCallback() {
                    @Override
                    public void onSuccess() {
                        oRecord.incrementReuse();
                        oDevice.setoStation(new Station(Constants_Intern.STATION_PRE_STOCK));

                        vConnection.getResponse(Request.Method.PUT, URL_ADD_DEVICE, oDevice.getJson(), new Interface_VolleyResult() {
                            @Override
                            public void onResult(JSONObject oJson) {
                                Log.i("Raus damit", oJson.toString());
                                try {
                                    if (oJson.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                                        oDevice.setIdDevice(oJson.getInt(Constants_Extern.ID_DEVICE));
                                        mPrinter.printDeviceId(oDevice);
                                        resetDevice();
                                        updateUI();
                                    } else {
                                        Toast.makeText(Activity_Bouncer.this, "IMEI exists", Toast.LENGTH_LONG).show();
                                        resetDevice();
                                        updateUI();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                        //addDevice();

                    }

                    @Override
                    public void onFailure() {

                    }
                });

                break;
        }

         */
/*        Toast.makeText(Activity_Bouncer.this, "works so far", Toast.LENGTH_LONG).show();
        resetDevice();
        updateUI();*/
    }

    public void prepareBounce() {
        //getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants_Intern.OBJECT_RECORD, oRecord);
        fragmentOverview(new Fragment_Overview_Selection(), bundle, Constants_Intern.FRAGMENT_RECORD);
        Toast.makeText(this, "ready to bounce", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showFragmentRecordNew() {
        fragmentOverview(new Fragment_Record_New(), null, Constants_Intern.FRAGMENT_RECORD_NEW);
    }

    @Override
    public void showFragmentRecordExisting() {
        fragmentOverview(new Fragment_Record_Existing(), null, Constants_Intern.FRAGMENT_RECORD_EXISTING);
    }

    @Override
    public void showFragmentRecordMenu() {
        fragmentOverview(new Fragment_Record_Menu(), null, Constants_Intern.FRAGMENT_RECORD_MENU);
    }


    @Override
    public void setRecord(Record record) {
        oRecord = new Record(record.getId(), record.getdLastUpdate(), record.getnRecycling(), record.getnReuse(), record.getnDevices(), record.getcCollectorName());

        //oRecord = record;
        prepareBounce();
    }

    @Override
    public void pauseRecord() {
        showFragmentRecordMenu();
    }

    @Override
    public void finishRecord() {
        uNetwork.recordSelected(oRecord.getId(), new Interface_VolleyCallback() {
            @Override
            public void onSuccess() {
                totalReset();
                oRecord = null;
                showFragmentRecordMenu();
            }

            @Override
            public void onFailure() {

            }
        });

    }

    @Override
    public void deleteRecord() {

    }

    @Override
    public int getCountReuse() {
        return oRecord.getnReuse();
    }

    @Override
    public int getCountRecycling() {
        return oRecord.getnRecycling();
    }

    @Override
    public String getNameCollector() {
        return oRecord.getcCollectorName();
    }

    // Specific methods
    public void addDevice() {
        if (oDevice.getIdDevice() == Constants_Intern.ID_UNKNOWN) {
            uNetwork.addDevice(oDevice, oRecord, new Interface_VolleyCallback_Int() {
                @Override
                public void onSuccess(int i) {
                    //oDevice.setIdDevice(i);
                    mPrinter.printDeviceId(oDevice);
                    resetDevice();
                    updateUI();
                    /*
                    uNetwork.assignLku(oDevice, new Interface_VolleyCallback_Int() {
                        @Override
                        public void onSuccess(int i) {
                            oDevice.setLKU(i);
                            Toast.makeText(Activity_Device.this, "LKU: " + Integer.toString(i), Toast.LENGTH_LONG).show();
                            if (usePrinter) mPrinter.printDeviceId(oDevice);
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
    public void returnColor(Color color) {
        super.returnColor(color);
        Log.i("da", "da");
        bounce();
    }

    @Override
    public void returnCondition(int condition) {
        super.returnCondition(condition);
        bounce();
    }

    @Override
    public void fragmentColorUpdate() {

    }

    @Override
    public void returnShape(Shape shape) {
        super.returnShape(shape);
        bounce();
    }

    @Override
    public void returnDefaultExploitation(int exploitation) {
        super.returnDefaultExploitation(exploitation);
        bounce();
    }

    // Handled Returns
    @Override
    public void handledReturnModel() {
        bounce();
    }

    @Override
    public void handledReturnBattery() {
        super.handledReturnBattery();
        bounce();
    }

    @Override
    public void handledReturnManufacturer() {
        bounce();
    }

    @Override
    public void handledReturnTypePhone() {
        bounce();
    }

    public void handledReturnDefaultExploitation() {
        if (oDevice.getoModel().gettDefaultExploitation() == Constants_Intern.EXPLOITATION_UNKNOWN) {
            oRecord.incrementRecycling();
            resetDevice();

        }
        bounce();
    }

    public void handledReturnDeviceBattery() {
        super.handledReturnDeviceBattery();
        bounce();
    }

    @Override
    public void continueWithRoutine() {
        bounce();
    }

    @Override
    public void handledReturnAddColor() {
        bounce();
    }

    @Override
    public void handledReturnModelBatteries() {
        super.handledReturnModelBatteries();
        bounce();
    }

    @Override
    public void handledReturnCharger() {
        bounce();
    }

    @Override
    public void handledPicturesTaken() {
        bounce();
    }

    @Override
    public void handledReturnBatteryContained() {
        super.handledReturnBatteryContained();
        bounce();
    }

    @Override
    public void handledReturnBatteryRemovable() {
        bounce();
    }

    @Override
    public void handledReturnBackcoverRemovable() {
        bounce();
    }

    @Override
    public void handledReturnShape() {
        bounce();
    }

    @Override
    public void handledReturnBackcoverContained() {
        super.handledReturnBackcoverContained();
        bounce();
    }

    @Override
    public void handledReturnDeviceDamages() {
        super.handledReturnDeviceDamages();
        bounce();
    }


    // Reset
    @Override
    public void totalReset() {
        super.totalReset();
    }

    // UI
    @Override
    public void updateUI() {
        super.updateUI();
        iOverviewSelection = (Interface_Overview_Selection) fManager.findFragmentByTag(Constants_Intern.FRAGMENT_RECORD);

        iOverviewSelection.updateUI();
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.ivHelp:
                etScan.setText(Constants_Intern.UNKNOWN_IMEI);
                Log.i("onAdapterClick", "ivHelp");
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
