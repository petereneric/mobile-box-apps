package com.example.ericschumacher.bouncer.Fragments.Booking;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Additive.Station;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Booking_Out_Lku_Stock extends Fragment_Booking {

    // Layout
    int kLayout = R.layout.fragment_booking_lku_out;

    // Data
    Station oStationTo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

       oStationTo = new Station(bData.getInt(Constants_Intern.STATION_ID));

       return vLayout;
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);
        // Data
        tvStationFrom.setText(new Station(Constants_Intern.STATION_PRIME_STOCK).getName());
        if (oStationTo != null) {
            tvStockTo.setText(oStationTo.getName());
        } else {
            tvStockTo.setText(new Station(Constants_Intern.STATION_UNKNOWN).getName());
        }

        // Visibility
        vDividerStorageNumber.setVisibility(View.GONE);
        tvStockNumber.setVisibility(View.GONE);
    }

    @Override
    public int getkLayout() {
        return kLayout;
    }

    // ClickListener
    public void onCommit() {
        oDevice.setoStoragePlace(null);
        iBooking.returnBooking(getTag());
    }
}
