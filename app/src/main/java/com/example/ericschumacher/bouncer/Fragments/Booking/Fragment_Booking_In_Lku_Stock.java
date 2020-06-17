package com.example.ericschumacher.bouncer.Fragments.Booking;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
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
import com.example.ericschumacher.bouncer.Objects.StoragePlace;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Density;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

public class Fragment_Booking_In_Lku_Stock extends Fragment_Booking implements View.OnClickListener {

    // Layout
    int kLayout = R.layout.fragment_booking_lku_in;
    Button bSpaceFull;
    LinearLayout llFooter;
    TextView tvStockCapacity;


    // Data
    StoragePlace oStoragePlaceSuggested;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return vLayout;
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);
        // Initiate
        bSpaceFull = vLayout.findViewById(R.id.bSpaceFull);
        llFooter = vLayout.findViewById(R.id.llFooter);
        tvStockCapacity = vLayout.findViewById(R.id.tvStockCapacity);

        // OnClickListener
        bSpaceFull.setOnClickListener(this);
        tvStockCapacity.setOnClickListener(this);
    }

    public void update() {

        // Lku
        suggestLku();

        // StockPrime Capacity
        llFooter.setVisibility(View.GONE);
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_INFORMATION_STOCK_PRIME_INFORMATION, null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (Volley_Connection.successfulResponse(oJson)) {
                    // Data
                    int highestLku = oJson.getInt(Constants_Extern.HIGHEST_LKU);
                    double loadFactor = oJson.getDouble(Constants_Extern.STOCK_PRIME_LOAD_FACTOR);

                    int kColor = R.color.color_grey;
                    if (loadFactor <= 0.7) {
                        kColor = R.color.color_grey;
                    }
                    if (loadFactor > 0.7 && loadFactor <= 0.9) {
                        kColor = R.color.color_grey;
                    }
                    if (loadFactor > 0.9) {
                        kColor = R.color.color_red;
                    }

                    // Text
                    tvStockCapacity.setText(Integer.toString(highestLku)+" | "+Math.round(loadFactor*100)+" %");

                    // TextColor
                    Context context = getContext();
                    if (context != null) {
                        tvStockCapacity.setTextColor(ResourcesCompat.getColor(getContext().getResources(), kColor, null));
                    }

                    // Background
                    GradientDrawable shape = new GradientDrawable();
                    shape.setShape(GradientDrawable.RECTANGLE);
                    shape.setCornerRadii(new float[] {Utility_Density.getDp(Context, 16),Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16), Utility_Density.getDp(Context, 16)});
                    shape.setStroke(Utility_Density.getDp(Context, 1), ResourcesCompat.getColor(Context.getResources(), kColor, null));
                    tvStockCapacity.setBackground(shape);

                    // Visibility
                    llFooter.setVisibility(View.GONE);
                }
            }
        });
    }

    public void suggestLku() {
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_STOCK_PRIME_LKU_SUGGESTION + oDevice.getIdDevice(), null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (Volley_Connection.successfulResponse(oJson)) {
                    llBooking.setVisibility(View.VISIBLE);
                    StoragePlace oStoragePlaceSuggestedNew = new StoragePlace(oJson.getJSONObject(Constants_Extern.OBJECT_STORAGEPLACE));
                    if (oStoragePlaceSuggested == null || (oStoragePlaceSuggestedNew.getkStock() != oStoragePlaceSuggested.getkStock() && oStoragePlaceSuggestedNew.getkLku() != oStoragePlaceSuggested.getkLku())) {
                        Log.i("suggestLku", "success");
                        oStoragePlaceSuggested = oStoragePlaceSuggestedNew;
                        tvStockTo.setText(oStoragePlaceSuggested.getcStock());
                        tvStockNumber.setText(Integer.toString(oStoragePlaceSuggested.getkLku()));
                    }
                } else {
                    llBooking.setVisibility(View.GONE);
                    iBooking.errorBooking(getTag(), Constants_Intern.TYPE_ERROR_STOCK_PRIME_FULL);
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
        super.onClick(view);
        Log.i("onClick", "clicked");
        switch (view.getId()) {
            case R.id.bSpaceFull:
                oDevice.getoModel().setnDps(oStoragePlaceSuggested.getnPosition()-1);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        iBooking.errorBooking(getTag(), Constants_Intern.ERROR_BOOKING_SPACE_FULL);
                    }
                }, 1000);

                break;
            case R.id.tvStockSide:
                // not used
                break;
            case R.id.tvStockCapacity:
                activityDevice.requestStockPrimeCapacity();
                break;
            case R.id.tvOtherSide:
                // not used yet
                break;
            case R.id.llTo:
                // not used
                break;
        }
    }

    public void onCommit() {
        oDevice.setoStoragePlace(new StoragePlace(oStoragePlaceSuggested.getkStock(), oStoragePlaceSuggested.getkLku()));
        iBooking.returnBooking(getTag());
    }
}
