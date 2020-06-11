package com.example.ericschumacher.bouncer.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Device_New;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Model_New;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Record_New_New;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Result_New;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Station;
import com.example.ericschumacher.bouncer.Objects.Collection.Record;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

public class Activity_Bouncer_New extends Activity_Device_New implements Fragment_Record_New_New.Interface_Fragment_Record {

    // Data
    Record oRecord;

    // Fragments
    Fragment_Record_New_New fRecord;

    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    // Lifecycle-Methods

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    // Fragments

    public void initiateFragments() {
        fModel = (Fragment_Model_New) fManager.findFragmentById(R.id.fModel);
        fDevice = (Fragment_Device_New) fManager.findFragmentById(R.id.fDevice);
        fRecord = (Fragment_Record_New_New) fManager.findFragmentById(R.id.fRecord);
    }

    public void removeFragments() {

    }

    // Layout

    public void setLayout() {
        super.setLayout();

        // Toolbar
        getSupportActionBar().setTitle(getString(R.string.bouncer));
    }

    public int getIdLayout() {
        return R.layout.activity_bouncer_new;
    }

    // Update

    public void updateLayout() {
        if (oRecord != null) {

            // Fragments
            fManager.beginTransaction().show(fRecord).commit();
            fRecord.updateLayout();
            if (oModel == null || oDevice == null) {
                getSupportFragmentManager().beginTransaction().hide(fModel).commit();
                getSupportFragmentManager().beginTransaction().hide(fDevice).commit();

            } else {
                getSupportFragmentManager().beginTransaction().show(fModel).commit();
                getSupportFragmentManager().beginTransaction().show(fDevice).commit();
                fModel.updateLayout();
                fDevice.updateLayout();
            }

            // Input
            tvSearchType.setText(getString(R.string.imei));
            etSearch.setHint(getString(R.string.enter_scan_imei));
            etSearch.setRawInputType(InputType.TYPE_CLASS_NUMBER);
            etSearch.setInputType(InputType.TYPE_CLASS_NUMBER);
            etSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
            if (etSearch.getText().length() > 0) {
                ivSearchClear.setImageResource(R.drawable.ic_clear_24dp);
            } else {
                ivSearchClear.setImageResource(R.drawable.ic_unknown_white_24dp);
            }

        } else {
            // Fragments
            getSupportFragmentManager().beginTransaction().hide(fModel).commit();
            getSupportFragmentManager().beginTransaction().hide(fDevice).commit();
            getSupportFragmentManager().beginTransaction().hide(fRecord).commit();

            // Input
            tvSearchType.setText(getString(R.string.id_record));
            etSearch.setHint(getString(R.string.enter_scan_id_record));
            etSearch.setRawInputType(InputType.TYPE_CLASS_TEXT);
            etSearch.setInputType(InputType.TYPE_CLASS_TEXT);
            etSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
            //etSearch.setBackgroundResource(android.R.color.transparent);
            if (etSearch.getText().length() > 0) {
                ivSearchClear.setImageResource(R.drawable.ic_clear_24dp);
            } else {
                ivSearchClear.setImageResource(R.drawable.ic_beach_access_white_24dp);
            }
        }
    }

    // Base & Reset

    public void base(Boolean bKeyboard) {
        updateLayout();
        if (oRecord != null) {
            if (oDevice != null) {
                if (oDevice.getoModel() == null) {
                    requestModelName();
                } else {
                    if (oDevice.getoModel().gettPhone() == null) {
                        requestPhoneType();
                    } else {
                        if (oDevice.getoModel().gettDefaultExploitation() == Constants_Intern.EXPLOITATION_RECYCLING || oDevice.gettState() == Constants_Intern.STATE_RECYCLING) {
                            oDevice.settState(Constants_Intern.STATE_RECYCLING);
                            if (oDevice.getoModel().isBatteryRemovable() == null && oDevice.getoModel().gettDefaultExploitation() != Constants_Intern.EXPLOITATION_RECYCLING) {
                                requestBatteryRemovable();
                            } else {
                                if (oDevice.getoModel().isBackcoverRemovable() == null && oDevice.getoModel().gettDefaultExploitation() != Constants_Intern.EXPLOITATION_RECYCLING) {
                                    requestBackcoverRemovable();
                                } else {
                                    if (oDevice.getoModel().gettDefaultExploitation() != Constants_Intern.EXPLOITATION_RECYCLING && oDevice.getoModel().isBatteryRemovable() && oDevice.isBatteryContained() == null) {
                                        Log.i("Battery", "Contained2");
                                        requestBatteryContained();
                                    } else {
                                        if (oDevice.getoModel().gettDefaultExploitation() != Constants_Intern.EXPLOITATION_RECYCLING && oDevice.getoModel().isBatteryRemovable() && oDevice.isBatteryContained() == true && oDevice.getoModel().getoBattery() == null) {
                                            requestBattery();
                                        } else {
                                            if (oDevice.getoModel().gettDefaultExploitation() != Constants_Intern.EXPLOITATION_RECYCLING && oDevice.getoModel().isBatteryRemovable() && oDevice.isBatteryContained() == true && oDevice.getoModel().getlModelBatteries().size() > 1 && oDevice.getoBattery() == null) {
                                                requestDeviceBattery();
                                            } else {
                                                if (oDevice.getoModel().gettDefaultExploitation() != Constants_Intern.EXPLOITATION_RECYCLING && oDevice.getoModel().isBackcoverRemovable() && oDevice.isBackcoverContained() == null) {
                                                    requestBackcoverContained();
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
                                        requestManufacturer();
                                    } else {
                                        if (oDevice.getoModel().getoCharger() == null) {
                                            requestCharger();
                                        } else {
                                            if (oDevice.getoModel().isBatteryRemovable() == null) {
                                                requestBatteryRemovable();
                                            } else {
                                                if (oDevice.getoModel().isBackcoverRemovable() == null) {
                                                    requestBackcoverRemovable();
                                                } else {
                                                    if (oDevice.getoModel().isBatteryRemovable() && (oDevice.isBatteryContained() != null && oDevice.isBatteryContained() == true) && oDevice.getoModel().getoBattery() == null) {
                                                        requestBattery();
                                                    } else {
                                                        if (oDevice.getoShape() == null) {
                                                            requestShape();
                                                        } else {
                                                            if (oDevice.getoColor() == null) {
                                                                Log.i("Color", "null");
                                                                requestColor();
                                                            } else {
                                                                Log.i("Color: ", oDevice.getoColor().getcNameDE());
                                                                if (false && oDevice.getoColor().getkModelColor() == 0 && oDevice.getoColor().getkModelColor() != Constants_Intern.TAKE_NO_PICTURE) { // mSharedPreferences.getBoolean(Constants_Intern.USE_CAMERA_MODEL_COLOR, false) &&
                                                                    //takePictures(IMAGE_FRONT);
                                                                } else {
                                                                    if (oDevice.isBatteryContained() == null) {
                                                                        Log.i("Battery", "Contained");
                                                                        requestBatteryContained();
                                                                    } else {
                                                                        if (oDevice.getoModel().isBatteryRemovable() && oDevice.isBatteryContained() && oDevice.getoBattery() == null) {
                                                                            requestDeviceBattery();
                                                                        } else {
                                                                            if (oDevice.isBackcoverContained() == null) { // Backcover
                                                                                requestBackcoverContained();
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

    public void resetRecord() {
        oRecord = null;
        getSupportFragmentManager().beginTransaction().hide(fRecord).commit();
        resetLayout();
    }

    public void resetDevice() {
        oDevice = null;
        fModel = (Fragment_Model_New) fManager.findFragmentById(R.id.fModel);
        fDevice = (Fragment_Device_New) fManager.findFragmentById(R.id.fDevice);
        resetLayout();
    }

    public void resetLayout() {
        etSearch.setText("");
        setKeyboard(Constants_Intern.SHOW_KEYBOARD);
    }

    public void reset() {

    }

    // Result

    public void showResult() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants_Intern.OBJECT_DEVICE, oDevice);
        showFragment( new Fragment_Result_New(), bundle, Constants_Intern.FRAGMENT_BOUNCER_RESULT, false);
    }

    // AfterBounce

    public void afterBounce() {

    }




    // Search

    public void onSearch() {
        final String cSearchSaved = etSearch.getText().toString();
        if (oRecord != null) {
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_DEVICE_BY_IMEI + etSearch.getText().toString(), null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    if (Volley_Connection.successfulResponse(oJson)) {
                        // Device exists
                        oDevice = new Device(oJson.getJSONObject(Constants_Extern.OBJECT_DEVICE), Activity_Bouncer_New.this);
                        base(Constants_Intern.CLOSE_KEYBOARD);
                    } else {
                        // Device does not exist
                        oDevice = new Device(Activity_Bouncer_New.this, etSearch.getText().toString());
                        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_BY_TAC+ etSearch.getText().toString().substring(0, 8), null, new Interface_VolleyResult() {
                            @Override
                            public void onResult(JSONObject oJson) {
                                if (cSearchSaved.equals(etSearch.getText().toString())) {
                                    if (Volley_Connection.successfulResponse(oJson)) {
                                        // Model found
                                        try {
                                            oDevice.setoModel(new Model(Activity_Bouncer_New.this, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL)));
                                            base(Constants_Intern.CLOSE_KEYBOARD);
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        // Model not found
                                        base(Constants_Intern.CLOSE_KEYBOARD);
                                    }
                                }
                            }
                        });
                    }
                }
            });

        } else {
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_RECORD_BY_ID + etSearch.getText().toString(), null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    if (cSearchSaved.equals(etSearch.getText().toString())) {
                        if (Volley_Connection.successfulResponse(oJson)) {
                            oRecord = new Record(Activity_Bouncer_New.this, oJson.getJSONObject(Constants_Extern.OBJECT_RECORD));
                            resetLayout();
                            base(null);
                        } else {
                            Utility_Toast.showString(Activity_Bouncer_New.this, oJson.getString(Constants_Extern.DETAILS));
                            resetLayout();
                            base(Constants_Intern.SHOW_KEYBOARD);
                        }
                    }
                }
            });

        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        updateLayout();
        if (!editable.toString().equals("")) {
            if (oRecord != null) {
                if (editable.toString().length() == 15) {
                    onSearch();
                }
            } else {
                if (editable.toString().substring(editable.toString().length()-1).equals("E")) {
                    etSearch.setText(editable.toString().substring(0, editable.toString().length()-1));
                    onSearch();
                }
            }
        } else {
            reset();
        }
    }

    @Override
    public Record getRecord() {
        return oRecord;
    }


    // Return

    @Override
    public void returnRecord(String cTag, String cAction) {
        switch (cAction) {
            case Constants_Intern.ACTION_FRAGMENT_RECORD_PAUSE:
                resetRecord();
                break;
            case Constants_Intern.ACTION_FRAGMENT_RECORD_FINISH:
                cVolley.getResponse(Request.Method.PUT, Urls.URL_RECORD_FINISH, null, new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) throws JSONException {
                        if (Volley_Connection.successfulResponse(oJson)) {
                            resetRecord();
                        }
                    }
                });
        }
    }

    public void returnResult(String cTag) {
        switch (oDevice.gettState()) {
            case Constants_Intern.STATE_UNKNOWN:
                if (oDevice.getoModel().gettDefaultExploitation() == Constants_Intern.EXPLOITATION_UNKNOWN) {
                    cVolley.execute(Request.Method.PUT, Urls.URL_INCREMENT_RECORD_RECYCLING + oRecord.getId(), null);
                    oRecord.incrementRecycling();
                }
                break;
            case Constants_Intern.STATE_MODEL_UNKNOWN:
                cVolley.execute(Request.Method.PUT, Urls.URL_INCREMENT_RECORD_RECYCLING + oRecord.getId(), null);
                oRecord.incrementRecycling();
                break;
            case Constants_Intern.STATE_RECYCLING:
                cVolley.execute(Request.Method.PUT, Urls.URL_INCREMENT_RECORD_RECYCLING + oRecord.getId(), null);
                oRecord.incrementRecycling();
                if (oDevice.getoModel().gettDefaultExploitation() != Constants_Intern.EXPLOITATION_RECYCLING && oDevice.getoModel().isBatteryRemovable() && oDevice.isBatteryContained() && oDevice.getoBattery().getlStock() < 2) {
                    // Print Label for Battery
                    //Toast.makeText(this, getString(R.string.label_print_battery), Toast.LENGTH_LONG).show();
                    mPrinter.printBattery(oDevice.getoModel().getoBattery());
                } else {
                    if (oDevice.getoModel().gettDefaultExploitation() != Constants_Intern.EXPLOITATION_RECYCLING && oDevice.getoModel().isBatteryRemovable() && oDevice.isBatteryContained() && oDevice.getoBattery().getlStock() > 1) {
                        //Toast.makeText(this, getString(R.string.no_label_print_battery), Toast.LENGTH_LONG).show();
                    }
                }


                break;
            case Constants_Intern.STATE_DEFECT_REPAIR:
            case Constants_Intern.STATE_DEFECT_REUSE:
                cVolley.execute(Request.Method.PUT, Urls.URL_INCREMENT_RECORD_RECYCLING + oRecord.getId(), null);
                oRecord.incrementRecycling();
                oDevice.setoStation(new Station(Constants_Intern.STATION_PRE_STOCK));
                cVolley.getResponse(Request.Method.PUT, Urls.URL_ADD_DEVICE, oDevice.getJson(), new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) {
                        Log.i("Raus damit", oJson.toString());
                        try {
                            if (oJson.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                                oDevice.setIdDevice(oJson.getInt(Constants_Extern.ID_DEVICE));
                                //Toast.makeText(Activity_Bouncer.this, getString(R.string.label_print_device), Toast.LENGTH_LONG).show();
                                mPrinter.printDeviceId(oDevice);
                            } else {
                                //Toast.makeText(Activity_Bouncer.this, "IMEI exists", Toast.LENGTH_LONG).show();
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
                cVolley.getResponse(Request.Method.PUT, Urls.URL_ADD_DEVICE, oDevice.getJson(), new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) {
                        Log.i("Raus damit", oJson.toString());
                        try {
                            if (oJson.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                                oDevice.setIdDevice(oJson.getInt(Constants_Extern.ID_DEVICE));
                                mPrinter.printDeviceId(oDevice);
                            } else {
                                //Toast.makeText(Activity_Bouncer.this, "IMEI exists", Toast.LENGTH_LONG).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
        }
        removeFragment(cTag);
        resetDevice();
        updateLayout();
    }

    // ClickListener & TextWatcher

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSearchType:
            case R.id.etSearch:
                break;
            case R.id.ivAction:
                if (oRecord != null) {
                    if (etSearch.getText().toString().equals("")) {
                        oDevice = new Device(this, Constants_Intern.UNKNOWN_IMEI);
                        base(Constants_Intern.CLOSE_KEYBOARD);
                    } else {
                        resetDevice();
                    }
                } else {
                    if (etSearch.getText().toString().equals("")) {
                        // Free Record
                    } else {
                        resetLayout();
                    }
                }
        }
    }
}
