package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Interfaces.Interface_LKU_Booker;
import com.example.ericschumacher.bouncer.Interfaces.Interface_DeviceManager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.StoragePlace;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONObject;

public class Fragment_LKU_Booking extends Fragment implements View.OnClickListener {

    private final static String urlSuggestedLkuStock = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/suggestion/storage_place/";
    private final static String urlFull = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/lku/full/";

    // Layout
    View Layout;
    TextView tvSuggestedLKU;
    Button bBooked;
    Button bFull;
    Button bCancel;

    // Interface
    Interface_DeviceManager iManager;
    Interface_LKU_Booker iLKUBooker;

    // Objects
    Device oDevice;
    StoragePlace oStoragePlaceSuggested;

    // Connection
    Volley_Connection vConnection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        setLayout(inflater, container);

        // Interface
        iManager = (Interface_DeviceManager)getActivity();
        iLKUBooker = (Interface_LKU_Booker)getActivity();

        // Connection
        vConnection = new Volley_Connection(getActivity());

        // Objects
        oDevice = iManager.getDevice();

        suggestLku();

        return Layout;
    }

    private void setLayout(LayoutInflater inflater, @Nullable ViewGroup container) {
        Layout = inflater.inflate(R.layout.fragment_stocking_book_into, container, false);
        tvSuggestedLKU = Layout.findViewById(R.id.tvSuggestedLku);
        bBooked = Layout.findViewById(R.id.bBooked);
        bFull = Layout.findViewById(R.id.bSpaceFull);
        bCancel = Layout.findViewById(R.id.bCancel);

        tvSuggestedLKU.setOnClickListener(this);
        bBooked.setOnClickListener(this);
        bFull.setOnClickListener(this);
        bCancel.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bBooked:
                    Log.i("Passeirt nich", "ne");
                    oDevice.setoStoragePlace(new StoragePlace(oStoragePlaceSuggested.getkStock(), oStoragePlaceSuggested.getkLku()));
                    iLKUBooker.onBookedIn();
                break;
            case R.id.bSpaceFull:
                vConnection.execute(Request.Method.PUT, urlFull+Integer.toString(oStoragePlaceSuggested.getkStock())+"/"+oStoragePlaceSuggested.getkLku()+"/"+oDevice.getIdDevice(), null);
                suggestLku();
                break;
            case R.id.bCancel:
                iLKUBooker.onCancel();
                break;
        }
    }

    private void suggestLku() {
        // Show suggested LkuStock
        vConnection.getResponse( Request.Method.GET, urlSuggestedLkuStock+Integer.toString(oDevice.getIdDevice()), null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) {
                Log.i("Response Suggested", oJson.toString());
                oStoragePlaceSuggested = new StoragePlace(oJson);
                tvSuggestedLKU.setText("Suggested: "+oStoragePlaceSuggested.getcStock()+" Lku: "+oStoragePlaceSuggested.getkLku());
            }
        });
    }
}
