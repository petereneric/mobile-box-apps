package com.example.ericschumacher.bouncer.Activities;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

public class Activity_Lifter_LkuStock extends Activity_Device_New {



    // Layout

    public void setLayout() {
        super.setLayout();

        // Toolbar
        getSupportActionBar().setTitle(getString(R.string.activity_lifter_stock_lku));
    }



    // Update

    public void updateLayout(boolean bCloseKeyboard) {
        super.updateLayout();

        // Show no fragment while loading
        if (getSupportFragmentManager().findFragmentByTag(Constants_Intern.FRAGMENT_BOOKING_IN_STOCK_PRIME) != null) {
            removeFragment(Constants_Intern.FRAGMENT_BOOKING_IN_STOCK_PRIME);
        }
        if (getSupportFragmentManager().findFragmentByTag(Constants_Intern.FRAGMENT_BOOKING_OUT_STOCK_PRIME) != null) {
            removeFragment(Constants_Intern.FRAGMENT_BOOKING_OUT_STOCK_PRIME);
        }

        if (oDevice != null && oDevice.getoStation().getId() == Constants_Intern.STATION_PRIME_STOCK) {
            showFragmentBookingOutStockPrime(bCloseKeyboard);
        } else {
            showFragmentBookingInStockPrime(bCloseKeyboard);
        }
    }



    // Base & Reset

    public void base() {
        updateLayout(Constants_Intern.CLOSE_KEYBOARD);
    }

    public void reset() {
        oDevice = null;
        oModel = null;
        updateLayout();
        updateKeyboardSearch(null);
        if (!etSearch.getText().toString().equals("")) {
            etSearch.setText("");
        }
    }



    // Search

    @Override
    public void returnFromSearch() {
        updateLayout(Constants_Intern.DONT_CLOSE_KEYBOARD);
    }



    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



    // Return

    @Override
    public void returnBooking(String cTag) {
        removeFragment(cTag);
        reset();
    }
}
