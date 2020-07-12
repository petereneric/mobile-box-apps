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
import com.example.ericschumacher.bouncer.Fragments.Display.Fragment_Display;
import com.example.ericschumacher.bouncer.Fragments.Edit.Fragment_Edit_Device_Damages;
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
    }

    @Override
    protected void onStart() {
        super.onStart();
        etSearch.requestFocus();
    }

    @Override
    public void onResume() {
        super.onResume();
        // Print
        SharedPreferences = getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0);
        if (ManagerPrinter.usePrinter(this)) {
            Log.i("ja", SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER, Constants_Intern.ID_PRINTER_ONE));
        } else {
            Log.i("nein", SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER, Constants_Intern.ID_PRINTER_ONE));
        }
        if (mPrinter == null) {
            mPrinter = new ManagerPrinter(this);
        }
        mPrinter.connect();
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

    public void reset() {
        oDevice = null;
        super.reset();

    }


    // Search

    public void onSearch() {
        final String cSearchSaved = etSearch.getText().toString();
        switch (SharedPreferences.getInt(Constants_Intern.SEARCH_DEVICE_TYPE, Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_ID_DEVICE)) {
            case Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_ID_DEVICE:
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
        return (oDevice != null) ? oDevice.getoModel() : null;
    }

    public void setModel(Model oModel) {
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
            bData.putInt(Constants_Intern.ID_MODEL, Constants_Intern.ID_UNKNOWN);
        }
        showFragment(new Fragment_Choice_Image_Color(), bData, Constants_Intern.FRAGMENT_CHOICE_IMAGE_COLOR, Constants_Intern.DONT_SHOW_KEYBOARD);
    }

    public void requestShape() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.shape));
        showFragment(new Fragment_Select_Shape(), bData, Constants_Intern.FRAGMENT_SELECT_SHAPE, Constants_Intern.DONT_SHOW_KEYBOARD);
    }

    public void requestState() {
        // Changed indirect
    }

    public void requestDamages() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.damages));
        showFragment(new Fragment_Edit_Device_Damages(), bData, Constants_Intern.FRAGMENT_EDIT_DEVICE_DAMAGES, Constants_Intern.DONT_SHOW_KEYBOARD);
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
                if (oDevice.isBatteryContained() && oDevice.getoModel().getlModelBatteries() != null && oDevice.getoModel().getlModelBatteries().size() == 1) {
                    oDevice.setoBattery(oDevice.getoModel().getoBattery());
                }
                break;
            case Constants_Intern.FRAGMENT_SELECT_BACKCOVER_CONTAINED:
                oDevice.setbBackcoverContained(tSelect == 1);
                break;
        }
        updateLayout();
        onFeatureChanged();
        removeFragment(cTag);
        base(Constants_Intern.CLOSE_KEYBOARD);
    }

    @Override
    public void returnChoice(String cTag, Object object) {
        super.returnChoice(cTag, object);
        switch (cTag) {
            case Constants_Intern.FRAGMENT_CHOICE_IMAGE_COLOR:
                oDevice.setoColor((Color) object);
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
                cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_BY_NAME + cInput, null, new Interface_VolleyResult() {
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
                            cVolley.getResponse(Request.Method.PUT, Urls.URL_PUT_MODEL_ADD+cInput, null, new Interface_VolleyResult() {
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
                            cVolley.getResponse(Request.Method.PUT, Urls.URL_PUT_BATTERY_ADD + cInput + getModel().getoManufacturer().getId(), null, new Interface_VolleyResult() {
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
                reset();
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
                oDevice.settState(Constants_Intern.STATE_DEFECT_REPAIR);
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
                        reset();
                    }
                });
                builder.create().show();
                break;
            case R.id.etSearch:
                break;
            case R.id.ivAction:
                reset();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!editable.toString().equals("")) {
            switch (SharedPreferences.getInt(Constants_Intern.SEARCH_DEVICE_TYPE, Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_ID_DEVICE)) {
                case Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_ID_DEVICE:
                    onSearch();
                    break;
                case Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_IMEI:
                    if (editable.toString().length() == 15) {
                        onSearch();
                    }
                    break;
            }
        } else {
            reset();
        }
    }
}
