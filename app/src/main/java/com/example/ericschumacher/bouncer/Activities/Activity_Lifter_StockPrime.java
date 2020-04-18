package com.example.ericschumacher.bouncer.Activities;

import android.os.Bundle;
import android.text.Editable;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Display.Fragment_Display;
import com.example.ericschumacher.bouncer.R;

public class Activity_Lifter_StockPrime extends Activity_Device_New {

    // Search
    @Override
    public void onSearchFinished() {
        jobFinished();
    }

    // Interaction
    public void jobFinished() {
        if (oDevice.getoStation().getId() == Constants_Intern.STATION_PRIME_STOCK) {

        } else {
            showFragmentBookingInStockPrime();
        }
    }

    public void reset() {
        oDevice = null;
        oModel = null;
        updateLayout();
        updateKeyboardSearch(null);
        if (!etSearch.getText().toString().equals("")) {
            etSearch.setText("");
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants_Intern.TEXT, getString(R.string.lift_new_device));
        showFragment(new Fragment_Display(), bundle, Constants_Intern.FRAGMENT_DISPLAY_LIFT_NEW_DEVICE);
        updateKeyboardSearch(false);
    }

    @Override
    public void returnDisplay(String cTag) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_DISPLAY_STOCK_PRIME_FULL:
                reset();
        }
    }

    @Override
    public void returnBooking(String cTag) {
        updateLayout();
        removeFragment(cTag);
        reset();
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!editable.toString().equals("")) {
            switch (SharedPreferences.getInt(Constants_Intern.SEARCH_DEVICE_TYPE, 2)) {
                case Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_ID_DEVICE:
                    // Do nothing
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
