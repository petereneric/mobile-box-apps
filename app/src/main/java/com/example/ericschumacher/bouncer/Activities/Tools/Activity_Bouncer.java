package com.example.ericschumacher.bouncer.Activities.Tools;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Activities.Manager.Activity_Device;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Choice.Image.Fragment_Choice_Image_Manufacturer;
import com.example.ericschumacher.bouncer.Fragments.Choice.Image.Fragment_Choice_Image_Model;
import com.example.ericschumacher.bouncer.Fragments.Result.Fragment_Result;
import com.example.ericschumacher.bouncer.Fragments.Record.Fragment_Record;
import com.example.ericschumacher.bouncer.Fragments.Record.Fragment_Record_Bouncer;
import com.example.ericschumacher.bouncer.Fragments.Result.Fragment_Result_Bouncer;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Additive.Shape;
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

import java.util.List;

public class Activity_Bouncer extends Activity_Device implements Fragment_Record.Interface_Fragment_Record, Fragment_Result.Interface_Fragment_Result {

    // Data
    Record oRecord;

    // Fragments
    Fragment_Record_Bouncer fRecord;


    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    // Fragments

    public void initiateFragments() {
        super.initiateFragments();
        fDevice.lMenu.setVisibility(View.GONE);
        fRecord = (Fragment_Record_Bouncer) fManager.findFragmentById(R.id.fRecord);

        // Visibility
        fDevice.showAll(false);
        fModel.showAll(false);
    }

    public void removeFragments() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment == null || fragment.getTag().equals(Constants_Intern.FRAGMENT_MODEL) || fragment.getTag().equals(Constants_Intern.FRAGMENT_DEVICE) || fragment.getTag().equals(Constants_Intern.FRAGMENT_RECORD)) {
                continue;
            }
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }


    // Layout

    public void setLayout() {
        super.setLayout();

        // Toolbar
        getSupportActionBar().setTitle(getString(R.string.bouncer));
    }

    public int getIdLayout() {
        return R.layout.activity_bouncer;
    }


    // Update

    public void updateLayout() {
        if (oRecord != null) {
            // Fragments
            fManager.beginTransaction().show(fRecord).commit();
            fRecord.updateLayout();
            if (getModel() == null || oDevice == null) {
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
            tvSearchType.setText(getString(R.string.id_box));
            etSearch.setHint(getString(R.string.enter_scan_id_box));
            etSearch.setRawInputType(InputType.TYPE_CLASS_TEXT);
            etSearch.setInputType(InputType.TYPE_CLASS_TEXT);
            etSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
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
                if (oDevice.gettState() == Constants_Intern.STATE_MODEL_UNKNOWN) {
                    if (oDevice.getoShape() != null) {
                        if (oDevice.getoManufacturer() != null) {
                            if (oDevice.gettPhone() != null) {
                                if (oDevice.getoColor() != null) {
                                    Bundle bundle = new Bundle();
                                    bundle.putString(Constants_Intern.TITLE, getString(R.string.model));
                                    bundle.putInt(Constants_Intern.ID_MANUFACTURER, oDevice.getoManufacturer().getId());
                                    bundle.putInt(Constants_Intern.ID_COLOR, oDevice.getoColor().getId());
                                    showFragment(new Fragment_Choice_Image_Model(), bundle, Constants_Intern.FRAGMENT_CHOICE_IMAGE_MODEL, Constants_Intern.CLOSE_KEYBOARD);
                                } else {
                                    requestColor();
                                }
                            } else {
                                requestPhoneType();
                            }
                        } else {
                            requestDeviceManufacturer();
                        }
                    } else {
                        requestShape();
                    }
                } else {
                    if (oDevice.getoModel() == null) {
                        if (oDevice.gettState() == Constants_Intern.STATE_RECYCLING) {
                            showResult();
                        } else {
                            requestModelName();
                        }
                    } else {
                        if (oDevice.getoModel().gettPhone() == null) {
                            requestPhoneType();
                        } else {
                            if (oDevice.getoModel().isBatteryRemovable() == null) {
                                requestBatteryRemovable();
                            } else {
                                if (oDevice.getoModel().isBackcoverRemovable() == null) {
                                    requestBackcoverRemovable();
                                } else {
                                    if (oDevice.getoModel().gettDefaultExploitation() == null || oDevice.getoModel().gettDefaultExploitation() == 0) {
                                        if (oDevice.getoModel().getoManufacturer() == null) {
                                            requestManufacturer();
                                        } else {
                                            if (oDevice.getoModel().getoManufacturer().gettDefaultExploitation() == Constants_Intern.DEFAULT_EXPLOITATION_RECYCLING) {
                                                oDevice.getoModel().settDefaultExploitation(Constants_Intern.DEFAULT_EXPLOITATION_RECYCLING);
                                            } else {
                                                oDevice.getoModel().settDefaultExploitation(Constants_Intern.DEFAULT_EXPLOITATION_TBD);
                                                base(null);
                                            }
                                        }
                                    } else {
                                        if (oDevice.getoModel().gettDefaultExploitation() == Constants_Intern.EXPLOITATION_RECYCLING || oDevice.gettState() == Constants_Intern.STATE_RECYCLING) {
                                            oDevice.settState(Constants_Intern.STATE_RECYCLING);
                                            if (oDevice.getoModel().gettDefaultExploitation() != Constants_Intern.EXPLOITATION_RECYCLING && oDevice.getoModel().isBatteryRemovable() && oDevice.isBatteryContained() == null) {
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
                                        } else {
                                            if (oDevice.getoModel().gettDefaultExploitation() == Constants_Intern.EXPLOITATION_INTACT_REUSE || oDevice.getoModel().gettDefaultExploitation() == Constants_Intern.EXPLOITATION_DEFECT_REUSE || oDevice.getoModel().gettDefaultExploitation() == Constants_Intern.DEFAULT_EXPLOITATION_TBD) {
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
        }
    }

    public void resetRecord() {
        oDevice = null;
        oRecord = null;
        resetLayout();

    }

    public void resetDevice() {
        oDevice = null;
        resetLayout();
    }

    public void resetLayout() {
        removeFragments();
        etSearch.setText("");
        setKeyboard(Constants_Intern.SHOW_KEYBOARD);
    }


    // Search

    public void onSearch() {

        if (oRecord != null) {
            final String cSearchSaved = etSearch.getText().toString();
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_DEVICE_BY_IMEI + etSearch.getText().toString(), null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    if (Volley_Connection.successfulResponse(oJson)) {
                        // Device exists
                        oDevice = new Device(oJson.getJSONObject(Constants_Extern.OBJECT_DEVICE), Activity_Bouncer.this);
                        oDevice.setkRecord(oRecord.getId());
                        base(Constants_Intern.CLOSE_KEYBOARD);
                    } else {
                        // Device does not exist
                        oDevice = new Device(Activity_Bouncer.this, etSearch.getText().toString());
                        oDevice.setkRecord(oRecord.getId());
                        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_BY_TAC + etSearch.getText().toString().substring(0, 8), null, new Interface_VolleyResult() {
                            @Override
                            public void onResult(JSONObject oJson) {
                                if (cSearchSaved.equals(etSearch.getText().toString())) {
                                    if (Volley_Connection.successfulResponse(oJson)) {
                                        // Model found
                                        try {
                                            oDevice.setoModel(new Model(Activity_Bouncer.this, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL)));
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
            final String cSearchSaved = etSearch.getText().toString();
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_BOX_BY_ID + etSearch.getText().toString().substring(0, etSearch.getText().toString().length() - 1), null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    if (cSearchSaved.equals(etSearch.getText().toString())) {
                        if (Volley_Connection.successfulResponse(oJson)) {
                            // Box found
                            if (!oJson.getJSONObject(Constants_Extern.OBJECT_BOX).isNull(Constants_Extern.OBJECT_RECORD)) {
                                // Record found
                                oRecord = new Record(Activity_Bouncer.this, oJson.getJSONObject(Constants_Extern.OBJECT_BOX).getJSONObject(Constants_Extern.OBJECT_RECORD));
                            } else {
                                // Record not found
                                Utility_Toast.show(Activity_Bouncer.this, R.string.no_record_connected_to_box);
                            }
                        } else {
                            // Box not found
                            Utility_Toast.show(Activity_Bouncer.this, R.string.id_unknown);
                        }
                        Log.i("TEEXT?: ", etSearch.getText().toString());
                        resetLayout();
                        base(null);
                    }
                }
            });
        }
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    // Get

    @Override
    public Record getRecord() {
        return oRecord;
    }


    // Result

    public void showResult() {
        switch (oDevice.gettState()) {
            case Constants_Intern.STATE_DEFECT_REPAIR:
            case Constants_Intern.STATE_DEFECT_REUSE:
                oDevice.setoStation(new Station(Constants_Intern.STATION_PRE_STOCK));
                cVolley.getResponse(Request.Method.PUT, Urls.URL_ADD_DEVICE, oDevice.getJson(), new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) {
                        Log.i("Raus damit", oJson.toString());
                        try {
                            if (oJson.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                                oDevice = new Device(oJson.getJSONObject(Constants_Extern.OBJECT_DEVICE), Activity_Bouncer.this);
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(Constants_Intern.OBJECT_DEVICE, oDevice);
                                showFragment(new Fragment_Result_Bouncer(), bundle, Constants_Intern.FRAGMENT_BOUNCER_RESULT, false);
                                mPrinter.printDeviceId(oDevice);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            case Constants_Intern.STATE_INTACT_REUSE:
                oDevice.setoStation(new Station(Constants_Intern.STATION_PRE_STOCK));
                cVolley.getResponse(Request.Method.PUT, Urls.URL_ADD_DEVICE, oDevice.getJson(), new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) {
                        Log.i("Raus damit", oJson.toString());
                        try {
                            if (oJson.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                                oDevice = new Device(oJson.getJSONObject(Constants_Extern.OBJECT_DEVICE), Activity_Bouncer.this);
                                if (oDevice.getnFutureStock() != null && oDevice.getnFutureStock() == 0)
                                    oDevice.setoStation(new Station(Constants_Intern.STATION_CHECK_ONE));
                                Bundle bundle = new Bundle();
                                bundle.putSerializable(Constants_Intern.OBJECT_DEVICE, oDevice);
                                showFragment(new Fragment_Result_Bouncer(), bundle, Constants_Intern.FRAGMENT_BOUNCER_RESULT, false);
                                mPrinter.printDeviceId(oDevice);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                break;
            default:
                Bundle bundle = new Bundle();
                bundle.putSerializable(Constants_Intern.OBJECT_DEVICE, oDevice);
                showFragment(new Fragment_Result_Bouncer(), bundle, Constants_Intern.FRAGMENT_BOUNCER_RESULT, false);
        }
    }

    // Request

    public void requestDeviceManufacturer() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.manufacturer));
        showFragment(new Fragment_Choice_Image_Manufacturer(), bData, Constants_Intern.FRAGMENT_CHOICE_IMAGE_DEVICE_MANUFACTURER, Constants_Intern.DONT_SHOW_KEYBOARD);
    }


    // Return

    @Override
    public void returnSelect(String cTag, int tSelect) {
        if (oDevice.gettState() == Constants_Intern.STATE_MODEL_UNKNOWN && cTag == Constants_Intern.FRAGMENT_SELECT_SHAPE) {
            oDevice.setoShape(new Shape(tSelect));
            if (tSelect == Constants_Intern.SHAPE_BROKEN) {
                oDevice.settState(Constants_Intern.STATE_RECYCLING);
            }
            updateLayout();
            removeFragment(cTag);
            base(Constants_Intern.CLOSE_KEYBOARD);
            return;
        }
        if (oDevice.gettState() == Constants_Intern.STATE_MODEL_UNKNOWN && cTag == Constants_Intern.FRAGMENT_SELECT_PHONE_TYPE) {
            Log.i(oDevice.getoManufacturer().getName() +" "+oDevice.getoManufacturer().gettDefaultExploitation(), tSelect+"");
            oDevice.settPhone(tSelect);
            if (tSelect == Constants_Intern.TYPE_PHONE_HANDY && oDevice.getoManufacturer() != null && oDevice.getoManufacturer().gettDefaultExploitation() == 3) {
                Log.i("why", "hy");
                oDevice.settState(Constants_Intern.STATE_RECYCLING);
            }
            updateLayout();
            removeFragment(cTag);
            base(Constants_Intern.CLOSE_KEYBOARD);
            return;
        }
        super.returnSelect(cTag, tSelect);
    }

    @Override
    public void returnChoice(String cTag, Object object) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_CHOICE_IMAGE_DEVICE_MANUFACTURER:
                Manufacturer oManufacturer = (Manufacturer) object;
                if (oManufacturer.gettDefaultExploitation() == Constants_Intern.DEFAULT_EXPLOITATION_RECYCLING) {
                    oDevice.settState(Constants_Intern.STATE_RECYCLING);
                } else {
                    oDevice.setoManufacturer((Manufacturer) object);
                }
                break;
            case Constants_Intern.FRAGMENT_CHOICE_IMAGE_MODEL:
                setModel((Model) object);
                oDevice.settState(Constants_Intern.STATE_UNKNOWN);
                break;
        }
        super.returnChoice(cTag, object);

    }

    @Override
    public void returnMenu(int tAction, String cTag) {
        switch (tAction) {
            case Constants_Intern.TYPE_ACTION_MENU_PAUSE:
                switch (cTag) {
                    case Constants_Intern.FRAGMENT_RECORD:
                        resetRecord();
                        break;
                }
                break;
            case Constants_Intern.TYPE_ACTION_MENU_DONE:
                switch (cTag) {
                    case Constants_Intern.FRAGMENT_RECORD:
                        cVolley.execute(Request.Method.PUT, Urls.URL_RECORD_FINISH + oRecord.getId(), null);
                        resetRecord();
                        break;
                }
                break;
        }
    }

    public void returnResult(final String cTag) {
        switch (oDevice.gettState()) {
            case Constants_Intern.STATE_UNKNOWN:
                if (oDevice.getoModel().gettDefaultExploitation() == Constants_Intern.EXPLOITATION_UNKNOWN) {
                    oRecord.incrementRecycling();
                }
                break;
            case Constants_Intern.STATE_RECYCLING:
                oRecord.incrementRecycling();
                if (oDevice.getoModel() != null && oDevice.getoModel().gettDefaultExploitation() != Constants_Intern.EXPLOITATION_RECYCLING && oDevice.getoModel().isBatteryRemovable() && oDevice.isBatteryContained() && oDevice.getoBattery().getlStock() < 2) {
                    mPrinter.printBattery(oDevice.getoModel().getoBattery());
                }
                break;
            case Constants_Intern.STATE_MODEL_UNKNOWN:
            case Constants_Intern.STATE_DEFECT_REPAIR:
            case Constants_Intern.STATE_DEFECT_REUSE:
                oRecord.incrementRecycling();
                break;
            case Constants_Intern.STATE_INTACT_REUSE:
                oRecord.incrementReuse();
                break;
        }
        removeFragment(cTag);
        resetDevice();
        updateLayout();
    }


    // Unknown

    @Override
    public void unknownInput(String cTag) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_INPUT_MODEL:
                Log.i("jooo", "jo");
                oDevice.settState(Constants_Intern.STATE_MODEL_UNKNOWN);
                break;
            case Constants_Intern.FRAGMENT_INPUT_DPS:
                break;
            case Constants_Intern.FRAGMENT_INPUT_BATTERY:
        }
        removeFragment(cTag);
        base(Constants_Intern.CLOSE_KEYBOARD);
    }

    @Override
    public void unknownChoice(String cTag) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_CHOICE_IMAGE_MODEL:
                showResult();
        }
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


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
                        oDevice.setkRecord(oRecord.getId());
                        base(Constants_Intern.CLOSE_KEYBOARD);
                    } else {
                        resetDevice();
                    }
                } else {
                    if (etSearch.getText().toString().equals("")) {
                        oRecord = Record.createFreeRecord();
                        resetLayout();
                        base(null);
                    } else {
                        resetLayout();
                    }
                }
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
                if (editable.toString().length() > 1 && editable.toString().substring(editable.toString().length() - 1).equals("e")) {
                    onSearch();
                }
            }
        } else {
            //reset();
        }
    }
}
