package com.example.ericschumacher.bouncer.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.view.View;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Booking.Fragment_Booking;
import com.example.ericschumacher.bouncer.Fragments.Booking.Fragment_Booking_In_Lku_Stock;
import com.example.ericschumacher.bouncer.Fragments.Choice.Fragment_Choice_DeviceBattery;
import com.example.ericschumacher.bouncer.Fragments.Choice.Image.Fragment_Choice_Image_Color;
import com.example.ericschumacher.bouncer.Fragments.Display.Fragment_Display;
import com.example.ericschumacher.bouncer.Fragments.Edit.Fragment_Edit_Device_Damages;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Device_New;
import com.example.ericschumacher.bouncer.Fragments.Input.Fragment_Input_StockPrimeCapacity;
import com.example.ericschumacher.bouncer.Fragments.Select.Fragment_Select_Shape;
import com.example.ericschumacher.bouncer.Fragments.Select.Fragment_Select_YesNo;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;
import com.example.ericschumacher.bouncer.Objects.Additive.Shape;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.Objects.Object_Device_Damage;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Activity_Device_New extends Activity_Model implements Fragment_Edit_Device_Damages.Interface_Edit_Device_Damages, Fragment_Booking.Interface_Booking {

    // Object
    Device oDevice;

    // Interaction
    Fragment_Device_New fDevice;

    @Override
    protected void onStart() {
        super.onStart();
        etSearch.requestFocus();
    }

    public void initiateFragments() {
        super.initiateFragments();
        fDevice = (Fragment_Device_New) fManager.findFragmentById(R.id.fDevice);
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

    public void updateLayout() {
        // Fragments
        fModel.updateLayout();
        fDevice.updateLayout();

        // TextViewSearch & EditTextSearch
        switch (SharedPreferences.getInt(Constants_Intern.SEARCH_DEVICE_TYPE, 0)) {
            case Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_ID_DEVICE:
                tvSearchType.setText(getString(R.string.id_device));
                etSearch.setHint(getString(R.string.enter_scan_id_device));
                etSearch.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                etSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                break;
            case Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_IMEI:
                tvSearchType.setText(getString(R.string.imei));
                etSearch.setHint(getString(R.string.enter_scan_imei));
                etSearch.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                etSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
                break;
        }
    }

    // Interaction
    public void jobFinished() {
        Bundle bundle = new Bundle();
        bundle.putString(Constants_Intern.TEXT, getString(R.string.edit_new_device));
        showFragment(new Fragment_Display(), bundle, Constants_Intern.FRAGMENT_DISPLAY_EDIT_NEW_DEVICE);
        updateKeyboardSearch(false);
    }

    public void reset() {
        oDevice = null;
        oModel = null;
        updateLayout();
        updateKeyboardSearch(null);
        if (!etSearch.getText().toString().equals("")) {
            etSearch.setText("");
        }
        if (fManager.findFragmentByTag(Constants_Intern.FRAGMENT_DISPLAY_EDIT_NEW_MODEL) != null) {
            removeFragment(Constants_Intern.FRAGMENT_DISPLAY_EDIT_NEW_MODEL);
        }
    }

    // Search
    public void onSearch() {
        final String cSearchSaved = etSearch.getText().toString();
        switch (SharedPreferences.getInt(Constants_Intern.SEARCH_DEVICE_TYPE, 0)) {
            case Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_ID_DEVICE:
                cVolley.getResponse(Request.Method.GET, Urls.URL_GET_DEVICE_BY_ID + etSearch.getText().toString(), null, new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) {
                        if (cSearchSaved.equals(etSearch.getText().toString())) {
                            if (Volley_Connection.successfulResponse(oJson)) {
                                try {
                                    oDevice = new Device( oJson.getJSONObject(Constants_Extern.OBJECT_DEVICE), Activity_Device_New.this);
                                    oModel = oDevice.getoModel();
                                    updateLayout();
                                    onSearchFinished();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Utility_Toast.show(Activity_Device_New.this, R.string.id_unknown);
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
                                    oDevice = new Device( oJson.getJSONObject(Constants_Extern.OBJECT_DEVICE), Activity_Device_New.this);
                                    oModel = oDevice.getoModel();
                                    updateLayout();
                                    onSearchFinished();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Utility_Toast.show(Activity_Device_New.this, R.string.imei_unknown);
                            }
                        }

                    }
                });
                break;
        }
    }

    // Get
    public Device getDevice() {
        return oDevice;
    }

    public Model getModel() {
        return (oDevice != null) ? oDevice.getoModel() : null;
    }

    // Show
    public void showFragmentBookingInStockPrime() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.booking));
        showFragment(new Fragment_Booking_In_Lku_Stock(), bData, Constants_Intern.FRAGMENT_BOOKING_IN_STOCK_PRIME);
    }

    public void showFragmentBookingOutStockPrime() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.stock_prime_booking_in));
        showFragment(new Fragment_Booking_In_Lku_Stock(), bData, Constants_Intern.FRAGMENT_BOOKING_IN_STOCK_PRIME);
    }

    // Edit
    public void onClickColor() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.color));
        bData.putInt(Constants_Intern.ID_MODEL, oDevice.getoModel().getkModel());
        showFragment(new Fragment_Choice_Image_Color(), bData, Constants_Intern.FRAGMENT_CHOICE_IMAGE_COLOR);
    }

    public void onClickShape() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.shape));
        showFragment(new Fragment_Select_Shape(), bData, Constants_Intern.FRAGMENT_SELECT_SHAPE);
    }

    public void onClickState() {
        // Changed indirect
    }

    public void onClickDamages() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.damages));
        showFragment(new Fragment_Edit_Device_Damages(), bData, Constants_Intern.FRAGMENT_EDIT_DEVICE_DAMAGES);
    }

    public void onClickStation() {
        // Changed indirect
    }

    public void onClickLku() {
        // Changed indirect
    }

    public void onClickBatteryContained() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.battery_contained));
        showFragment(new Fragment_Select_YesNo(), bData, Constants_Intern.FRAGMENT_SELECT_BATTERY_CONTAINED);
    }

    public void onClickDeviceBattery() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.device_battery));
        showFragment(new Fragment_Choice_DeviceBattery(), bData, Constants_Intern.FRAGMENT_CHOICE_DEVICE_BATTERY);
    }

    public void onClickBackcoverContained() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.backcover_contained));
        showFragment(new Fragment_Select_YesNo(), bData, Constants_Intern.FRAGMENT_SELECT_BACKCOVER_CONTAINED);
    }

    public void onClickStockPrimeCapacity() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.stock_prime_capacity));
        showFragment(new Fragment_Input_StockPrimeCapacity(), bData, Constants_Intern.FRAGMENT_INPUT_STOCK_PRIME_CAPACITY);
    }

    // Return
    @Override
    public void returnSelect(String cTag, int tSelect) {
        super.returnSelect(cTag, tSelect);
        switch (cTag) {
            case Constants_Intern.FRAGMENT_SELECT_SHAPE:
                oDevice.setoShape(new Shape(tSelect));
                if (oDevice.getoModel().getlModelDamages().size() > 0) {
                    onClickDamages();
                } else {
                    oDevice.settState(Constants_Intern.STATE_RECYCLING);
                }
                break;
            case Constants_Intern.FRAGMENT_SELECT_BATTERY_CONTAINED:
                oDevice.setBatteryContained(tSelect == 1);
                break;
            case Constants_Intern.FRAGMENT_SELECT_BACKCOVER_CONTAINED:
                oDevice.setbBackcoverContained(tSelect == 1);
                break;
        }
        updateLayout();
        removeFragment(cTag);
        jobFinished();
    }

    @Override
    public void returnChoice(String cTag, Object object) {
        super.returnChoice(cTag, object);
        switch (cTag) {
            case Constants_Intern.FRAGMENT_CHOICE_IMAGE_COLOR:
                oDevice.setoColor((Color)object);
                break;
            case Constants_Intern.FRAGMENT_CHOICE_DEVICE_BATTERY:
                oDevice.setoBattery((Battery)object);
                break;
        }
        updateLayout();
        removeFragment(cTag);
        jobFinished();
    }

    public void returnInput(final String cTag, final String cInput) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_INPUT_STOCK_PRIME_CAPACITY:
                cVolley.execute(Request.Method.PUT, Urls.URL_PUT_STOCK_PRIME_HIGHEST_LKU+cInput, null);
                removeFragment(cTag);
                showFragmentBookingInStockPrime();
                break;
        }
    }

    @Override
    public void returnDisplay(String cTag) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_DISPLAY_EDIT_NEW_DEVICE:
                reset();
            case Constants_Intern.FRAGMENT_DISPLAY_STOCK_PRIME_FULL:
                jobFinished();
        }
    }

    @Override
    public void returnBooking(String cTag) {
        updateLayout();
        removeFragment(cTag);
        jobFinished();
    }

    @Override
    public void returnEditDeviceDamages_DeviceDamages(String cTag) {
        oDevice.settState(Constants_Intern.STATE_DEFECT_REPAIR);
        removeFragment(cTag);
        jobFinished();
    }

    @Override
    public void returnEditDeviceDamages_Overbroken(String cTag) {
        oDevice.setlDeviceDamages(new ArrayList<Object_Device_Damage>());
        oDevice.settState(Constants_Intern.STATE_RECYCLING);
        removeFragment(cTag);
        jobFinished();
    }

    @Override
    public void returnEditDeviceDamages_OtherDamages(String cTag) {
        returnEditDeviceDamages_Overbroken(cTag);
    }

    @Override
    public void errorBooking(String cTag, String cError) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_BOOKING_IN_STOCK_PRIME:
                switch (cError) {
                    case Constants_Intern.TYPE_ERROR_STOCK_PRIME_FULL:
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants_Intern.TEXT, getString(R.string.stock_prime_full));
                        showFragment(new Fragment_Display(), bundle, Constants_Intern.FRAGMENT_DISPLAY_STOCK_PRIME_FULL);
                        break;
                }
                break;
        }
    }

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
                                SharedPreferences.edit().putInt(Constants_Intern.SEARCH_MODEL_TYPE, Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_IMEI).commit();
                                break;
                        }
                        reset();
                    }
                });
                builder.create().show();
                break;
            case R.id.etSearch:
                if (!etSearch.getText().toString().equals("")) {
                    if (!bSearchSelected) {
                        etSearch.selectAll();
                    }
                    bSearchSelected = !bSearchSelected;
                }
                break;
            case R.id.ivSearch:
                final String cSearchSaved = etSearch.getText().toString();
                switch (SharedPreferences.getInt(Constants_Intern.SEARCH_DEVICE_TYPE, 0)) {
                    case Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_ID_DEVICE:
                        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_DEVICE_BY_ID + etSearch.getText().toString(), null, new Interface_VolleyResult() {
                            @Override
                            public void onResult(JSONObject oJson) {
                                if (cSearchSaved.equals(etSearch.getText().toString())) {
                                    if (Volley_Connection.successfulResponse(oJson)) {
                                        try {
                                            oDevice = new Device( oJson.getJSONObject(Constants_Extern.OBJECT_DEVICE), Activity_Device_New.this);
                                            oModel = oDevice.getoModel();
                                            updateLayout();
                                            jobFinished();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        Utility_Toast.show(Activity_Device_New.this, R.string.id_unknown);
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
                                            oDevice = new Device( oJson.getJSONObject(Constants_Extern.OBJECT_DEVICE), Activity_Device_New.this);
                                            oModel = oDevice.getoModel();
                                            updateLayout();
                                            jobFinished();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        Utility_Toast.show(Activity_Device_New.this, R.string.imei_unknown);
                                    }
                                }

                            }
                        });
                        break;
                }
                break;
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!editable.toString().equals("")) {
            switch (SharedPreferences.getInt(Constants_Intern.SEARCH_DEVICE_TYPE, 2)) {
                case Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_ID_DEVICE:
                    onSearch();
                    break;
                case Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_IMEI:
                    if (editable.toString().length()==15) {
                        onSearch();
                    }
                    break;
            }
        } else {
            reset();
        }
    }
}
