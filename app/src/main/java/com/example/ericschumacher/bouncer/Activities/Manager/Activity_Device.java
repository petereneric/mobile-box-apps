package com.example.ericschumacher.bouncer.Activities.Manager;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.ericschumacher.bouncer.Fragments.Booking.Fragment_Booking;
import com.example.ericschumacher.bouncer.Fragments.Booking.Fragment_Booking_In_Lku_Stock;
import com.example.ericschumacher.bouncer.Fragments.Booking.Fragment_Booking_Out_Lku_Stock;
import com.example.ericschumacher.bouncer.Fragments.Choice.Fragment_Choice_DeviceBattery;
import com.example.ericschumacher.bouncer.Fragments.Choice.Image.Fragment_Choice_Image_Color;
import com.example.ericschumacher.bouncer.Fragments.Choice.Image.Fragment_Choice_Image_ModelColor;
import com.example.ericschumacher.bouncer.Fragments.Display.Fragment_Display;
import com.example.ericschumacher.bouncer.Fragments.Edit.Fragment_Edit_Device_Damages;
import com.example.ericschumacher.bouncer.Fragments.Edit.Fragment_Edit_Device_Damages_New;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Device;
import com.example.ericschumacher.bouncer.Fragments.Input.Fragment_Input_Model;
import com.example.ericschumacher.bouncer.Fragments.Input.Fragment_Input_StockPrimeCapacity;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Object;
import com.example.ericschumacher.bouncer.Fragments.Select.Fragment_Select_Shape;
import com.example.ericschumacher.bouncer.Fragments.Select.Fragment_Select_YesNo;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;
import com.example.ericschumacher.bouncer.Objects.Additive.Shape;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.Objects.ModelColor;
import com.example.ericschumacher.bouncer.Objects.Model_Battery;
import com.example.ericschumacher.bouncer.Objects.Object_Device_Damage;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;
import com.example.ericschumacher.bouncer.Zebra.ManagerPrinter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Activity_Device extends Activity_Model implements Fragment_Edit_Device_Damages.Interface_Edit_Device_Damages, Fragment_Booking.Interface_Booking, Fragment_Device.Interface_Device, Fragment_Object.Interface_Fragment_Object_Menu {

    // Print
    public ManagerPrinter mPrinter;

    // Data
    public Device oDevice;

    // Fragments
    public Fragment_Device fDevice;


    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    // Lifecycle-Methods

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Print
        mPrinter = new ManagerPrinter(this);
        //mPrinter.connect();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Utility_Toast.showString(this, getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0).getString(Constants_Intern.SELECTED_PRINTER_IP, "none"));
        etSearch.requestFocus();
        mPrinter.connect();
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    @Override
    protected void onPause() {
        super.onPause();
        if (mPrinter != null) mPrinter.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPrinter != null) mPrinter.disconnect();
    }


    // Fragments

    public void initiateFragments() {
        super.initiateFragments();
        fDevice = (Fragment_Device) fManager.findFragmentById(R.id.fDevice);

        // Visibility
        fDevice.showAll(true);
    }

    public void showFragmentBookingInStockPrime(Boolean bKeyboard) {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.booking));
        showFragment(new Fragment_Booking_In_Lku_Stock(), bData, Constants_Intern.FRAGMENT_BOOKING_IN_STOCK_PRIME, bKeyboard);

    }

    public void showFragmentBookingOutStockPrime(Boolean bKeyboard) {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.booking));
        showFragment(new Fragment_Booking_Out_Lku_Stock(), bData, Constants_Intern.FRAGMENT_BOOKING_OUT_STOCK_PRIME, bKeyboard);
    }

    public void removeFragments() {
        super.removeFragments();
        getSupportFragmentManager().beginTransaction().hide(fDevice).commit();
        if (fManager.findFragmentByTag(Constants_Intern.FRAGMENT_EDIT_DEVICE_DAMAGES) != null) {
            removeFragment(Constants_Intern.FRAGMENT_EDIT_DEVICE_DAMAGES);
        }
    }


    // Layout

    public void setLayout() {
        super.setLayout();

        // Toolbar
        getSupportActionBar().setTitle(getString(R.string.device_manager));
    }

    public int getIdLayout() {
        return R.layout.activity_device_new;
    }


    // Update

    public void updateLayout() {
        // Fragments
        if (getModel() != null) {
            getSupportFragmentManager().beginTransaction().show(fModel).commit();
            fModel.updateLayout();
        }
        if (oDevice != null) {
            getSupportFragmentManager().beginTransaction().show(fDevice).commit();
            fDevice.updateLayout();
        }

        // TextViewSearch & EditTextSearch
        switch (SharedPreferences.getInt(Constants_Intern.SEARCH_DEVICE_TYPE, Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_ID_DEVICE)) {
            case Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_ID_DEVICE:
                tvSearchType.setText(getString(R.string.id_device));
                etSearch.setHint(getString(R.string.enter_scan_id_device));
                etSearch.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                etSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                etSearch.setSingleLine(true);
                break;
            case Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_IMEI:
                tvSearchType.setText(getString(R.string.imei));
                etSearch.setHint(getString(R.string.enter_scan_imei));
                etSearch.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                etSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
                etSearch.setSingleLine(true);
                break;
        }
    }


    // Base & Reset

    @Override
    public void base(Boolean bKeyboard) {
        updateLayout();
    }

    @Override
    public void reset() {
        oDevice = null;
        super.reset();
    }


    // Search

    public void onSearch() {
        final String cSearchSaved = etSearch.getText().toString();
        switch (SharedPreferences.getInt(Constants_Intern.SEARCH_DEVICE_TYPE, Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_ID_DEVICE)) {
            case Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_ID_DEVICE:
                if (cSearchSaved.length() < 15) {
                    cVolley.getResponse(Request.Method.GET, Urls.URL_GET_DEVICE_BY_ID + etSearch.getText().toString(), null, new Interface_VolleyResult() {
                        @Override
                        public void onResult(JSONObject oJson) {
                            if (cSearchSaved.equals(etSearch.getText().toString())) {
                                if (Volley_Connection.successfulResponse(oJson)) {
                                    try {
                                        oDevice = new Device(oJson.getJSONObject(Constants_Extern.OBJECT_DEVICE), Activity_Device.this);
                                        oModel = oDevice.getoModel();
                                        returnFromSearch();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Utility_Toast.show(Activity_Device.this, R.string.id_unknown);
                                    removeFragments();
                                }
                            }
                        }
                    });
                    break;
                }
            case Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_IMEI:
                cVolley.getResponse(Request.Method.GET, Urls.URL_GET_DEVICE_BY_IMEI + etSearch.getText().toString(), null, new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) {
                        if (cSearchSaved.equals(etSearch.getText().toString())) {
                            if (Volley_Connection.successfulResponse(oJson)) {
                                try {
                                    oDevice = new Device(oJson.getJSONObject(Constants_Extern.OBJECT_DEVICE), Activity_Device.this);
                                    oModel = oDevice.getoModel();
                                    returnFromSearch();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Utility_Toast.show(Activity_Device.this, R.string.imei_unknown);
                                removeFragments();
                            }
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void returnFromSearch() {
        super.returnFromSearch();
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    // Get

    public Device getDevice() {
        return oDevice;
    }

    public Model getModel() {
        if (oDevice != null) {
            return oDevice.getoModel();
        } else {
            return (oModel != null) ?  oModel: null;
        }

    }

    public void setModel(Model oModel) {
        super.setModel(oModel);
        if (oDevice != null) oDevice.setoModel(oModel);
    }


    // Edit

    public void requestModelName() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.interaction_title_request_name_model));
        showFragment(new Fragment_Input_Model(), bData, Constants_Intern.FRAGMENT_INPUT_MODEL, Constants_Intern.DONT_SHOW_KEYBOARD);
    }

    public void requestColor() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.color));
        if (oDevice.getoModel() != null) {
            bData.putInt(Constants_Intern.ID_MODEL, oDevice.getoModel().getkModel());
        } else {
            requestModelName();
        }
        showFragment(new Fragment_Choice_Image_ModelColor(), bData, Constants_Intern.FRAGMENT_CHOICE_IMAGE_MODEL_COLOR, Constants_Intern.DONT_SHOW_KEYBOARD);
    }

    public void requestShape() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.shape));
        showFragment(new Fragment_Select_Shape(), bData, Constants_Intern.FRAGMENT_SELECT_SHAPE, Constants_Intern.DONT_SHOW_KEYBOARD);
    }

    public void requestState() {
        // Changed indirect
    }

    @Override
    public void printDevice() {
        if (oDevice != null) mPrinter.printDeviceId(oDevice);
    }

    @Override
    public void printDeviceBattery() {
        if (oDevice != null && oDevice.getoBattery() != null) mPrinter.printBattery(oDevice.getoBattery());
    }

    public void requestDamages() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.damages));
        showFragment(new Fragment_Edit_Device_Damages_New(), bData, Constants_Intern.FRAGMENT_EDIT_DEVICE_DAMAGES, Constants_Intern.DONT_SHOW_KEYBOARD);
    }

    public void requestStation() {
        // Changed indirect
    }

    public void requestLku() {
        // Changed indirect
    }

    public void requestBatteryContained() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.battery_contained));
        showFragment(new Fragment_Select_YesNo(), bData, Constants_Intern.FRAGMENT_SELECT_BATTERY_CONTAINED, Constants_Intern.DONT_SHOW_KEYBOARD);
    }

    public void requestDeviceBattery() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.device_battery));
        showFragment(new Fragment_Choice_DeviceBattery(), bData, Constants_Intern.FRAGMENT_CHOICE_DEVICE_BATTERY, Constants_Intern.DONT_SHOW_KEYBOARD);
    }

    public void requestBackcoverContained() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.backcover_contained));
        showFragment(new Fragment_Select_YesNo(), bData, Constants_Intern.FRAGMENT_SELECT_BACKCOVER_CONTAINED, Constants_Intern.DONT_SHOW_KEYBOARD);
    }

    public void requestStockPrimeCapacity() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.stock_prime_capacity));
        showFragment(new Fragment_Input_StockPrimeCapacity(), bData, Constants_Intern.FRAGMENT_INPUT_STOCK_PRIME_CAPACITY, Constants_Intern.DONT_SHOW_KEYBOARD);
    }


    // Return

    @Override
    public void returnSelect(String cTag, int tSelect) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_SELECT_PHONE_TYPE:
                getModel().settPhone(tSelect);
                break;
            case Constants_Intern.FRAGMENT_SELECT_EXPLOITATION:
                getModel().settDefaultExploitation(tSelect);
                break;
            case Constants_Intern.FRAGMENT_SELECT_BATTERY_REMOVABLE:
                getModel().setBatteryRemovable(tSelect == 1);
                break;
            case Constants_Intern.FRAGMENT_SELECT_BACKCOVER_REMOVABLE:
                getModel().setBackcoverRemovable(tSelect == 1);
                break;
            case Constants_Intern.FRAGMENT_SELECT_SHAPE:
                oDevice.setoShape(new Shape(tSelect));
                if (tSelect == Constants_Intern.SHAPE_BROKEN) {
                    Log.i("Size Damages: ", oDevice.getoModel().getlModelDamages().size()+"");
                    if (oDevice.getoModel().getlModelDamages().size() > 0) {
                        requestDamages();
                        return;
                    } else {
                        oDevice.settState(Constants_Intern.STATE_RECYCLING);
                    }
                } else {
                    oDevice.settState(Constants_Intern.STATE_INTACT_REUSE);
                }
                break;
            case Constants_Intern.FRAGMENT_SELECT_BATTERY_CONTAINED:
                oDevice.setBatteryContained(tSelect == 1);
                if (tSelect == 1 && getModel() != null && getModel().getlModelBatteries().size() > 1) {
                    requestDeviceBattery();
                }
                break;
            case Constants_Intern.FRAGMENT_SELECT_BACKCOVER_CONTAINED:
                oDevice.setbBackcoverContained(tSelect == 1);
                break;
        }
        updateLayout();
        removeFragment(cTag);
        base(Constants_Intern.CLOSE_KEYBOARD);
        onFeatureChanged();
    }

    @Override
    public void returnChoice(String cTag, Object object) {
        super.returnChoice(cTag, object);
        switch (cTag) {
            case Constants_Intern.FRAGMENT_CHOICE_IMAGE_MODEL_COLOR:
                oDevice.setoModelColor((ModelColor) object);
                //oDevice.settState(Constants_Intern.STATE_UNKNOWN);
                onFeatureChanged();
                break;
            case Constants_Intern.FRAGMENT_CHOICE_DEVICE_BATTERY:
                oDevice.setoBattery(((Model_Battery)object).getoBattery());
                break;
        }
        updateLayout();
        removeFragment(cTag);
        base(Constants_Intern.CLOSE_KEYBOARD);
    }

    public void returnInput(final String cTag, final String cInput) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_INPUT_STOCK_PRIME_CAPACITY:
                cVolley.execute(Request.Method.PUT, Urls.URL_PUT_STOCK_PRIME_HIGHEST_LKU + cInput, null);
                removeFragment(cTag);
                showFragmentBookingInStockPrime(Constants_Intern.CLOSE_KEYBOARD);
                break;
            case Constants_Intern.FRAGMENT_INPUT_MODEL:
                JSONObject oJson= new JSONObject();
                try {
                    oJson.put(Constants_Intern.NAME_MODEL, cInput);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cVolley.getResponse(Request.Method.POST, Urls.URL_POST_MODEL_BY_NAME, oJson, new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) {
                        if (Volley_Connection.successfulResponse(oJson)) {
                            try {
                                oDevice.setoModel(new Model(Activity_Device.this, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL)));
                                if (!oDevice.getTAC().equals("00000000"))getModel().connectTac(oDevice.getTAC());
                                removeFragment(cTag);
                                base(Constants_Intern.CLOSE_KEYBOARD);
                                onFeatureChanged();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            JSONObject json = new JSONObject();
                            try {
                                json.put("cName", cInput);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            cVolley.getResponse(Request.Method.PUT, Urls.URL_PUT_MODEL_ADD, json, new Interface_VolleyResult() {
                                @Override
                                public void onResult(JSONObject oJson) throws JSONException {
                                    if (Volley_Connection.successfulResponse(oJson)) {
                                        oDevice.setoModel(new Model(Activity_Device.this, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL)));
                                        if (!oDevice.getTAC().equals("00000000"))getModel().connectTac(oDevice.getTAC());
                                        removeFragment(cTag);
                                        base(Constants_Intern.CLOSE_KEYBOARD);
                                        onFeatureChanged();
                                    }
                                }
                            });
                        }

                    }
                });
                return;
            case Constants_Intern.FRAGMENT_INPUT_BATTERY:
                cVolley.getResponse(Request.Method.GET, Urls.URL_GET_BATTERY_BY_NAME + cInput, null, new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) throws JSONException {
                        if (Volley_Connection.successfulResponse(oJson)) {
                            final Battery oBattery = new Battery(oJson.getJSONObject(Constants_Extern.OBJECT_BATTERY), Activity_Device.this);
                            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_BATTERY_CONNECT_MODEL + oBattery.getId() + "/" + getModel().getkModel(), null, new Interface_VolleyResult() {
                                @Override
                                public void onResult(JSONObject oJson) throws JSONException {
                                    getModel().addModelBattery(new Model_Battery(Activity_Device.this, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL_BATTERY)));
                                    updateLayout();
                                    removeFragment(cTag);
                                    if (getModel().getlModelBatteries().size() > 1) {
                                        requestBattery();
                                    } else {
                                        oDevice.setoBattery(oBattery);
                                        base(Constants_Intern.CLOSE_KEYBOARD);
                                    }
                                }
                            });
                        } else {
                            cVolley.getResponse(Request.Method.PUT, Urls.URL_PUT_BATTERY_ADD + cInput + "/" + getModel().getoManufacturer().getId(), null, new Interface_VolleyResult() {
                                @Override
                                public void onResult(JSONObject oJson) throws JSONException {
                                    if (Volley_Connection.successfulResponse(oJson)) {
                                        final Battery oBattery = new Battery(oJson.getJSONObject(Constants_Extern.OBJECT_BATTERY), Activity_Device.this);
                                        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_BATTERY_CONNECT_MODEL + oBattery.getId() + "/" + getModel().getkModel(), null, new Interface_VolleyResult() {
                                            @Override
                                            public void onResult(JSONObject oJson) throws JSONException {
                                                getModel().addModelBattery(new Model_Battery(Activity_Device.this, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL_BATTERY)));
                                                updateLayout();
                                                removeFragment(cTag);
                                                if (getModel().getlModelBatteries().size() > 1) {
                                                    requestBattery();
                                                } else {
                                                    oDevice.setoBattery(oBattery);
                                                    base(Constants_Intern.CLOSE_KEYBOARD);
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
                return;
        }
        super.returnInput(cTag, cInput);
    }

    @Override
    public void returnDisplay(String cTag) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_DISPLAY_STOCK_PRIME_FULL:
                base(Constants_Intern.CLOSE_KEYBOARD);
            case Constants_Intern.FRAGMENT_DISPLAY_ARTICLE_NOT_FOUND:
                hardReset();
        }
    }

    @Override
    public void returnBooking(String cTag) {
        removeFragment(cTag);
        base(Constants_Intern.CLOSE_KEYBOARD);
    }

    @Override
    public void returnEditDeviceDamages(int tAction, String cTag) {
        switch (tAction) {
            case Constants_Intern.TYPE_ACTION_DEVICE_DAMAGES_COMMIT:
                if (oDevice.getlDeviceDamages().size() > 0) {
                    Integer tRepairstate = null;
                    for (Object_Device_Damage deviceDamage : oDevice.getlDeviceDamages()) {
                        if (deviceDamage.gettStatus() == 3) {
                            tRepairstate = 3;
                            break;
                        }
                        if (tRepairstate == null && deviceDamage.gettStatus() == 2) {
                            tRepairstate = 2;
                        }
                        if (deviceDamage.gettStatus() == 1) {
                            tRepairstate = 1;
                        }
                    }

                    switch (tRepairstate) {
                        case 1:
                            if (oDevice.getlDeviceDamages().size() == 1 && oDevice.getlDeviceDamages().get(0).getoModelDamage().getoDamage().getkDamage() == 10) {
                                oDevice.settState(Constants_Intern.STATE_DEFECT_REUSE);
                            } else {
                                oDevice.settState(Constants_Intern.STATE_DEFECT_REPAIR);
                            }
                            break;
                        case 2:
                            oDevice.settState(Constants_Intern.STATE_INTACT_REUSE);
                            break;
                        case 3:
                            oDevice.settState(Constants_Intern.STATE_RECYCLING);
                            break;
                    }
                } else {
                    oDevice.settState(Constants_Intern.STATE_INTACT_REUSE);
                }
                break;
            case Constants_Intern.TYPE_ACTION_DEVICE_DAMAGES_OVERBROKEN:
            case Constants_Intern.TYPE_ACTION_DEVICE_DAMAGES_OTHER_DAMAGES:
                oDevice.setlDeviceDamages(new ArrayList<Object_Device_Damage>());
                oDevice.settState(Constants_Intern.STATE_RECYCLING);
                break;
        }
        removeFragment(cTag);
        base(Constants_Intern.CLOSE_KEYBOARD);
    }

    @Override
    public void returnMenu(int tAction, String cTag) {
        switch (tAction) {
            case Constants_Intern.TYPE_ACTION_MENU_PRINT:
                mPrinter.printDeviceId(oDevice);
                break;
        }
    }


    // Unknown
    @Override
    public void unknownInput(String cTag) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_INPUT_MODEL:
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
        }
        removeFragment(cTag);
        base(Constants_Intern.CLOSE_KEYBOARD);
    }

    // Error

    @Override
    public void errorBooking(String cTag, String cError) {
        if (cTag != null) {
            switch (cTag) {
                case Constants_Intern.FRAGMENT_BOOKING_IN_STOCK_PRIME:
                    switch (cError) {
                        case Constants_Intern.TYPE_ERROR_STOCK_PRIME_FULL:
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants_Intern.TEXT, getString(R.string.stock_prime_full));
                            showFragment(new Fragment_Display(), bundle, Constants_Intern.FRAGMENT_DISPLAY_STOCK_PRIME_FULL, Constants_Intern.DONT_SHOW_KEYBOARD);
                            break;
                        case Constants_Intern.ERROR_BOOKING_SPACE_FULL:
                        case Constants_Intern.ERROR_BOOKING_STOCK_EXCESS_LKU_FULL:
                            updateLayout();
                            break;
                    }
                    break;
            }
        }
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    // ClickListener & TextWatcher

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSearchType:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                String[] lChoice = {getString(R.string.id_device), getString(R.string.imei)};
                builder.setItems(lChoice, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                SharedPreferences.edit().putInt(Constants_Intern.SEARCH_DEVICE_TYPE, Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_ID_DEVICE).commit();
                                break;
                            case 1:
                                SharedPreferences.edit().putInt(Constants_Intern.SEARCH_DEVICE_TYPE, Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_IMEI).commit();
                                break;
                        }
                        hardReset();
                    }
                });
                builder.create().show();
                break;
            case R.id.etSearch:
                break;
            case R.id.ivAction:
                hardReset();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        reset();
        if (!editable.toString().equals("")) {
            switch (SharedPreferences.getInt(Constants_Intern.SEARCH_DEVICE_TYPE, Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_ID_DEVICE)) {
                case Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_ID_DEVICE:
                    if (editable.toString().length() < 15) {
                        onSearch();
                        break;
                    }
                case Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_IMEI:
                    if (editable.toString().length() == 15) {
                        onSearch();
                    }
                    break;
            }
        }
    }

    public Integer getSearchId() {
        if (etSearch.getText().subSequence(etSearch.getText().length()-1, etSearch.getText().length()).toString().equals("e")) {
            return Integer.parseInt(etSearch.getText().subSequence(0, etSearch.getText().length()-1).toString());
        } else {
            return null;
        }
    }
}
