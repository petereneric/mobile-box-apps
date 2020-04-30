package com.example.ericschumacher.bouncer.Activities;

import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

public class Activity_Lifter_LkuStock extends Activity_Device_New {

    // Layout
    FloatingActionButton fabReset;



    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------



    // Fragments

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
        fabReset = findViewById(R.id.fabReset);

        // Toolbar
        getSupportActionBar().setTitle(getString(R.string.activity_lifter_stock_lku));

        // OnClickListener & TextWatcher
        fabReset.setOnClickListener(this);
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

    public void base() {
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
            case R.id.fabReset:
                reset();
        }
    }
}
