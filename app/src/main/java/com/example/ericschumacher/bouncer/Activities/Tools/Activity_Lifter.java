package com.example.ericschumacher.bouncer.Activities.Tools;

import android.view.View;

import com.example.ericschumacher.bouncer.Activities.Manager.Activity_Device;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

public class Activity_Lifter extends Activity_Device {





    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



    // Fragments

    public void initiateFragments() {
        super.initiateFragments();
        fDevice.lMenu.setVisibility(View.GONE);
    }

    public void removeFragments() {
        super.removeFragments();
        if (getSupportFragmentManager().findFragmentByTag(Constants_Intern.FRAGMENT_BOOKING_IN_STOCK_PRIME) != null) {
            removeFragment(Constants_Intern.FRAGMENT_BOOKING_IN_STOCK_PRIME);
        }
        if (getSupportFragmentManager().findFragmentByTag(Constants_Intern.FRAGMENT_BOOKING_OUT_STOCK_PRIME) != null) {
            removeFragment(Constants_Intern.FRAGMENT_BOOKING_OUT_STOCK_PRIME);
        }
    }

    // Layout

    public void setLayout() {
        super.setLayout();

        // Toolbar
        getSupportActionBar().setTitle(getString(R.string.activity_lifter_stock_lku));
    }

    public int getIdLayout() {
        return R.layout.activity_lifter;
    }

    // Update

    public void updateLayout() {
        super.updateLayout();

        // Show no fragment while loading
        if (getSupportFragmentManager().findFragmentByTag(Constants_Intern.FRAGMENT_BOOKING_IN_STOCK_PRIME) != null) {
            removeFragment(Constants_Intern.FRAGMENT_BOOKING_IN_STOCK_PRIME);
        }
        if (getSupportFragmentManager().findFragmentByTag(Constants_Intern.FRAGMENT_BOOKING_OUT_STOCK_PRIME) != null) {
            removeFragment(Constants_Intern.FRAGMENT_BOOKING_OUT_STOCK_PRIME);
        }

        if (oDevice != null && (oDevice.getoStation().getId() == Constants_Intern.STATION_PRIME_STOCK || oDevice.getoStation().getId() == Constants_Intern.STATION_EXCESS_STOCK)) {
            showFragmentBookingOutStockPrime(null);
        }
        if (oDevice != null && oDevice.getoStation().getId() != Constants_Intern.STATION_PRIME_STOCK && oDevice.getoStation().getId() != Constants_Intern.STATION_EXCESS_STOCK){
            showFragmentBookingInStockPrime(null);
        }
    }



    // Base & Reset

    public void base(Boolean bKeyboard) {
        updateLayout();
    }



    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



    // Return

    @Override
    public void returnBooking(String cTag) {
        removeFragment(cTag);
        reset();
    }

    // ClickListener & TextWatcher

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
        }
    }
}
