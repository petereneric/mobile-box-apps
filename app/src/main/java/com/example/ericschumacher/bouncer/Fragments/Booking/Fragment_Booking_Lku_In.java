package com.example.ericschumacher.bouncer.Fragments.Booking;

import android.content.SharedPreferences;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Density;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

public class Fragment_Booking_Lku_In extends Fragment_Booking implements View.OnClickListener {

    // vLayout
    int kLayout = R.layout.fragment_booking_lku_in;
    TextView tvStationFrom;
    TextView tvStorageTo;
    TextView tvStorageNumber;
    View vDividerStorageNumber;
    Button bCommit;
    Button bSpaceFull;
    TextView tvStockSide;
    TextView tvStockCapacity;
    TextView tvOtherSide;
    LinearLayout llBooking;

    // SharedPreferences
    SharedPreferences SharedPreferences;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // SharedPreferences
        SharedPreferences = getActivity().getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0);

        // vLayout
        setLayout(inflater, container);
        updateLayout();

        return vLayout;
    }



    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        // Initiate
        tvStationFrom = vLayout.findViewById(R.id.tvStationFrom);
        tvStorageTo = vLayout.findViewById(R.id.tvStorageTo);
        tvStorageNumber = vLayout.findViewById(R.id.tvStorageNumber);
        vDividerStorageNumber = vLayout.findViewById(R.id.vDividerStorageNumber);
        bCommit = vLayout.findViewById(R.id.bCommit);
        bSpaceFull = vLayout.findViewById(R.id.bSpaceFull);
        tvStockSide = vLayout.findViewById(R.id.tvStockSide);
        tvStockCapacity = vLayout.findViewById(R.id.tvStockCapacity);
        tvOtherSide = vLayout.findViewById(R.id.tvOtherSide);
        llBooking = vLayout.findViewById(R.id.llBooking);

        // Data
        tvStationFrom.setText(oDevice.getoStation().getName());
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_INFORMATION_STOCK_PRIME_HIGHEST_LKU, null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (Volley_Connection.successfulResponse(oJson)) tvStockCapacity.setText(Integer.toString(oJson.getInt(Constants_Extern.HIGHEST_LKU)));
            }
        });


        // OnClickListener
        tvStorageTo.setOnClickListener(this);
        bCommit.setOnClickListener(this);
        bSpaceFull.setOnClickListener(this);
        tvStockSide.setOnClickListener(this);
        tvStockCapacity.setOnClickListener(this);
        tvOtherSide.setOnClickListener(this);
    }

    public void updateLayout() {
        tvStockSide.setText((SharedPreferences.getInt(Constants_Intern.STOCK_SIDE, Constants_Intern.STOCK_SIDE_FRONT) == Constants_Intern.STOCK_SIDE_FRONT) ? getString(R.string.frontside) : getString(R.string.backside));

        // StockPrime Capacity
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_INFORMATION_STOCK_PRIME_IS_FULL, null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (Volley_Connection.successfulResponse(oJson)) {
                    int kColor;
                    if (oJson.getInt(Constants_Extern.IS_FULL) == 0) {
                        kColor = R.color.color_grey;
                    } else {
                        kColor = R.color.color_red;
                    }
                    // TextColor
                    tvStockCapacity.setTextColor(ResourcesCompat.getColor(getContext().getResources(), kColor, null));

                    // Background
                    GradientDrawable shape = new GradientDrawable();
                    shape.setShape(GradientDrawable.RECTANGLE);
                    shape.setCornerRadii(new float[] {Utility_Density.getDp(getActivity(), 16),Utility_Density.getDp(getActivity(), 16), Utility_Density.getDp(getActivity(), 16), Utility_Density.getDp(getActivity(), 16), Utility_Density.getDp(getActivity(), 16), Utility_Density.getDp(getActivity(), 16), Utility_Density.getDp(getActivity(), 16), Utility_Density.getDp(getActivity(), 16)});
                    shape.setStroke(1, ResourcesCompat.getColor(getActivity().getResources(), kColor, null));
                    tvStockCapacity.setBackground(shape);
                }
            }
        });
    }

    @Override
    public int getkLayout() {
        return kLayout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvStorageTo:

                break;
            case R.id.bCommit:

                break;
            case R.id.bSpaceFull:

                break;
            case R.id.tvStockSide:

                break;
            case R.id.tvStockCapacity:

                break;
            case R.id.tvOtherSide:
                SharedPreferences.edit().putInt(Constants_Intern.STOCK_SIDE, (SharedPreferences.getInt(Constants_Intern.STOCK_SIDE, Constants_Intern.STOCK_SIDE_FRONT) == Constants_Intern.STOCK_SIDE_FRONT) ? Constants_Intern.STOCK_SIDE_BACK : Constants_Intern.STOCK_SIDE_FRONT).commit();
                updateLayout();
                break;
        }
    }
}
