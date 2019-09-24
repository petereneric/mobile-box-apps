package com.example.ericschumacher.bouncer.Fragments.Fragment_Request;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Interfaces.Interface_DeviceManager;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Input_LKU_Booking extends Fragment_Input_Simple implements View.OnClickListener {

    private boolean isBooked;
    private Interface_DeviceManager iManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Layout = super.onCreateView(inflater, container, savedInstanceState);
        Log.i("Fragment_LKU_Booking", "onCreateView");
        iManager = (Interface_DeviceManager)getActivity();

        //isBooked = (getArguments().getInt(Constants_Intern.Stat) == Constants_Intern.STATION_LKU_STOCKING_INT);
        if (!isBooked) {
            tvSimple.setText(getString(R.string.book));
        } else {
            tvSimple.setText(getString(R.string.book_out));
        }


        return Layout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSimple:
                if (isBooked) {
                    iManager.bookDeviceOutOfLKUStock();
                } else {
                    iManager.bookDeviceIntoLKUStock();
                }
        }
    }
}
