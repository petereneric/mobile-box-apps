package com.example.ericschumacher.bouncer.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Activities.Parent.Activity_Device;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Booking_Out;
import com.example.ericschumacher.bouncer.Fragments.Fragment_LKU_Booking;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Input_Simple;
import com.example.ericschumacher.bouncer.Interfaces.Interface_LKU_Booker;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONObject;

public class Activity_LKU_Booker extends Activity_Device implements Interface_LKU_Booker {

    Volley_Connection vConnection;

    private boolean bScanReady;

    // Connection
    private final static String URL_GET_DEVICE = "http://svp-server.com/svp-gmbh/erp/bouncer/src/api.php/device/new/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        vConnection = new Volley_Connection(this);
        bScanReady = true;
    }

    @Override
    public void onScan(String text) {

        if (!text.equals("") && !text.matches("")) {

            if (bScanReady) {
                vConnection.getResponse(Request.Method.GET, URL_GET_DEVICE + Integer.parseInt(text), null, new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) {
                        Log.i("Result | Device:", oJson.toString());
                        oDevice = new Device(oJson, Activity_LKU_Booker.this);
                        try {
                            if (Integer.parseInt(etScan.getText().toString()) != oDevice.getIdDevice()) {
                                onScan(etScan.getText().toString());
                                return;
                            }
                            updateUI();
                            showBookingFragment();
                            Log.i("StationObject:", oDevice.getoStation().getJson().toString());
                        } catch (NumberFormatException e) {
                            totalReset();
                            Toast.makeText(Activity_LKU_Booker.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }

                        // Log.i("StoragePlace:", oDevice.getoStoragePlace().getJson().toString());
                    }
                });
            } else {
                Toast.makeText(this, getString(R.string.error_forgot_to_book), Toast.LENGTH_LONG).show();
                try {

                } catch (java.lang.NullPointerException e) {
                    e.printStackTrace();
                    onDestroy();
                }
            }

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivClearScan:
                Log.i("Jooo", "j");
                totalReset();
                break;
        }
    }

    @Override
    public void onBookedIn() {
        totalReset();
    }

    @Override
    public void onFull() {

    }

    @Override
    public void onCancel() {
        totalReset();
    }

    @Override
    public void onBookedOut() {
        totalReset();
    }

    public void updateUI() {
        super.updateUI();
    }

    @Override
    public void totalReset() {
        super.totalReset();
        bScanReady = true;
        fragmentInteraction(new Fragment_Input_Simple(), null, "FRAGMENT_SIMPLE");
    }

    @Override
    public void returnColor(Color color) {
        super.returnColor(color);
        showBookingFragment();
    }

    private void showBookingFragment() {
        if ((oDevice.getoStation().getId() == Constants_Intern.STATION_PRIME_STOCK || oDevice.getoStation().getId() == Constants_Intern.STATION_EXCESS_STOCK) && oDevice.getoStoragePlace() != null) {
            Fragment_Booking_Out fragment = new Fragment_Booking_Out();
            fManager.beginTransaction().replace(R.id.flFragmentInteraction, fragment, Constants_Intern.FRAGMENT_LKU_BOOKING).commit();
        } else {
            Fragment_LKU_Booking fLKUBookingInto = new Fragment_LKU_Booking();
            fManager.beginTransaction().replace(R.id.flFragmentInteraction, fLKUBookingInto, Constants_Intern.FRAGMENT_LKU_BOOKING_INTO).commit();
        }
    }

}
