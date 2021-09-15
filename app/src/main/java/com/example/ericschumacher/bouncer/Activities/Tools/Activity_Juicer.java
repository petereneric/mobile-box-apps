package com.example.ericschumacher.bouncer.Activities.Tools;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Activities.Activity_Authentication;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Display.Fragment_Display;
import com.example.ericschumacher.bouncer.Fragments.List.Fragment_List_Juicer;
import com.example.ericschumacher.bouncer.Fragments.Loading.Fragment_Loading;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Layout;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Activity_Juicer extends Activity_Authentication implements View.OnClickListener, Fragment_Display.Interface_Display {

    // Layout
    Toolbar vToolbar;
    View vLayoutStock;
    View vLayoutLoadingStation;
    TextView tvLayoutStockTitle;
    TextView tvLayoutLoadingStationTitle;
    TextView tvStockPrime;
    TextView tvStockExcess;
    TextView tvLoadingStationOne;
    TextView tvLoadingStationTwo;
    TextView tvLoadingStationThree;
    TextView tvLoadingStationFour;

    // Connection
    Volley_Connection cVolley;

    // List
    ArrayList<Integer> lModelColorShapes;

    // SharedPreferences
    android.content.SharedPreferences SharedPreferences;


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    // Lifecycle-Methods

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SharedPreferences
        SharedPreferences = getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0);

        // List
        lModelColorShapes = new ArrayList<>();

        // Layout
        setLayout();
        updateLayout();

        // Connection
        cVolley = new Volley_Connection(this);
    }



    @Override
    protected void onStart() {
        super.onStart();
        load();
    }

    // Fragments

    public void showFragment(Fragment fragment, Bundle bData, String cTag) {
        fragment.setArguments(bData);
        getSupportFragmentManager().beginTransaction().replace(R.id.flInteraction, fragment, cTag).commit();
    }


    // Layout

    public void setLayout() {
        setContentView(getIdLayout());
        vToolbar = findViewById(R.id.vToolbar);
        vLayoutStock = findViewById(R.id.lStock);
        vLayoutLoadingStation = findViewById(R.id.lLoadingStation);
        tvLayoutStockTitle = vLayoutStock.findViewById(R.id.tvTitle);
        tvLayoutLoadingStationTitle = vLayoutLoadingStation.findViewById(R.id.tvTitle);
        tvStockPrime = findViewById(R.id.tvStockPrime);
        tvStockExcess = findViewById(R.id.tvStockExcess);
        tvLoadingStationOne = findViewById(R.id.tvLoadingStationOne);
        tvLoadingStationTwo = findViewById(R.id.tvLoadingStationTwo);
        tvLoadingStationThree = findViewById(R.id.tvLoadingStationThree);
        tvLoadingStationFour = findViewById(R.id.tvLoadingStationFour);


        // Title
        tvLayoutStockTitle.setText(getString(R.string.stock));
        tvLayoutLoadingStationTitle.setText(getString(R.string.loadingstation));

        // Toolbar
        setSupportActionBar(vToolbar);
        getSupportActionBar().setTitle(getString(R.string.juicer));
        vToolbar.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.color_primary, null));
        vToolbar.setTitleTextColor(ResourcesCompat.getColor(getResources(), R.color.color_white, null));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // OnClickListener & TextWatcher
        tvStockPrime.setOnClickListener(this);
        tvStockExcess.setOnClickListener(this);
        tvLoadingStationOne.setOnClickListener(this);
        tvLoadingStationTwo.setOnClickListener(this);
        tvLoadingStationThree.setOnClickListener(this);
        tvLoadingStationFour.setOnClickListener(this);
    }

    public int getIdLayout() {
        return R.layout.activity_juicer_new;
    }


    // Update

    public void updateLayout() {
        // RoundedCorners
        Utility_Layout.setRoundedCorners(this, tvStockPrime, SharedPreferences.getBoolean(Constants_Intern.JUICER_STOCK_PRIME_SELECTED, true) ? R.color.color_primary : R.color.color_grey_secondary);
        Utility_Layout.setRoundedCorners(this, tvStockExcess, SharedPreferences.getBoolean(Constants_Intern.JUICER_STOCK_EXCESS_SELECTED, false) ? R.color.color_primary : R.color.color_grey_secondary);
        Utility_Layout.setRoundedCorners(this, tvLoadingStationOne, SharedPreferences.getBoolean(Constants_Intern.JUICER_LOADING_STATION_ONE_SELECTED, true) ? R.color.color_primary : R.color.color_grey_secondary);
        Utility_Layout.setRoundedCorners(this, tvLoadingStationTwo, SharedPreferences.getBoolean(Constants_Intern.JUICER_LOADING_STATION_TWO_SELECTED, true) ? R.color.color_primary : R.color.color_grey_secondary);
        Utility_Layout.setRoundedCorners(this, tvLoadingStationThree, SharedPreferences.getBoolean(Constants_Intern.JUICER_LOADING_STATION_THREE_SELECTED, true) ? R.color.color_primary : R.color.color_grey_secondary);
        Utility_Layout.setRoundedCorners(this, tvLoadingStationFour, SharedPreferences.getBoolean(Constants_Intern.JUICER_LOADING_STATION_FOUR_SELECTED, true) ? R.color.color_primary : R.color.color_grey_secondary);

        // TextColor
        Utility_Layout.setTextColor(this, tvStockPrime, SharedPreferences.getBoolean(Constants_Intern.JUICER_STOCK_PRIME_SELECTED, true) ? R.color.color_primary : R.color.color_grey_secondary);
        Utility_Layout.setTextColor(this, tvStockExcess, SharedPreferences.getBoolean(Constants_Intern.JUICER_STOCK_EXCESS_SELECTED, false) ? R.color.color_primary : R.color.color_grey_secondary);
        Utility_Layout.setTextColor(this, tvLoadingStationOne, SharedPreferences.getBoolean(Constants_Intern.JUICER_LOADING_STATION_ONE_SELECTED, true) ? R.color.color_primary : R.color.color_grey_secondary);
        Utility_Layout.setTextColor(this, tvLoadingStationTwo, SharedPreferences.getBoolean(Constants_Intern.JUICER_LOADING_STATION_TWO_SELECTED, true) ? R.color.color_primary : R.color.color_grey_secondary);
        Utility_Layout.setTextColor(this, tvLoadingStationThree, SharedPreferences.getBoolean(Constants_Intern.JUICER_LOADING_STATION_THREE_SELECTED, true) ? R.color.color_primary : R.color.color_grey_secondary);
        Utility_Layout.setTextColor(this, tvLoadingStationFour, SharedPreferences.getBoolean(Constants_Intern.JUICER_LOADING_STATION_FOUR_SELECTED, true) ? R.color.color_primary : R.color.color_grey_secondary);

        // Text
        tvStockPrime.setText(SharedPreferences.getBoolean(Constants_Intern.JUICER_STOCK_PRIME_SELECTED, true) ? getString(R.string.stock_prime_short)+" ("+lModelColorShapes.size()+")" : getString(R.string.stock_prime_short));
        tvStockExcess.setText(SharedPreferences.getBoolean(Constants_Intern.JUICER_STOCK_EXCESS_SELECTED, false) ? getString(R.string.stock_excess_short)+" ("+lModelColorShapes.size()+")" : getString(R.string.stock_excess_short));
    }

    // Load
    public void load() {
        // Load
        showFragment(new Fragment_Loading(), null, Constants_Intern.FRAGMENT_LOADING);

        // Data
        lModelColorShapes.clear();
        updateLayout();
        cVolley.getResponse(Request.Method.POST, Urls.URL_POST_ID_MODEL_COLOR_SHAPE_FOR_JUICER, getJson(), new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (Volley_Connection.successfulResponse(oJson)) {
                    JSONArray jsonArray = oJson.getJSONArray(Constants_Extern.LIST_ID_MODEL_COLOR_SHAPE);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        lModelColorShapes.add(jsonObject.getInt(Constants_Extern.ID_MODEL_COLOR_SHAPE));
                    }
                }
                returnFromLoading(Volley_Connection.successfulResponse(oJson));
            }
        });
    }

    public void returnFromLoading(boolean bSuccessful) {
        updateLayout();
        if (bSuccessful) {
            showFragment(new Fragment_List_Juicer(), null, Constants_Intern.FRAGMENT_LIST_JUICER);
        } else {
            Bundle bData = new Bundle();
            bData.putString(Constants_Intern.TEXT, getString(R.string.this_stock_is_empty));
            showFragment(new Fragment_Display(), bData, Constants_Intern.FRAGMENT_DISPLAY_STOCK_EMPTY);
        }
    }

    // Json

    private JSONObject getJson() {
        int tStock = SharedPreferences.getBoolean(Constants_Intern.JUICER_STOCK_PRIME_SELECTED, true) ? Constants_Intern.STATION_PRIME_STOCK : Constants_Intern.STATION_EXCESS_STOCK;
        ArrayList<Integer> lLoadingStations = new ArrayList<>();
        if (!SharedPreferences.getBoolean(Constants_Intern.JUICER_LOADING_STATION_ONE_SELECTED, true)) lLoadingStations.add(1);
        if (!SharedPreferences.getBoolean(Constants_Intern.JUICER_LOADING_STATION_TWO_SELECTED, true)) lLoadingStations.add(2);
        if (!SharedPreferences.getBoolean(Constants_Intern.JUICER_LOADING_STATION_THREE_SELECTED, true)) lLoadingStations.add(3);
        if (!SharedPreferences.getBoolean(Constants_Intern.JUICER_LOADING_STATION_FOUR_SELECTED, true)) lLoadingStations.add(4);

        String cLoadingStations = "";
        if (lLoadingStations.size() > 0 ) {
            for (int i = 0; i < lLoadingStations.size(); i++) {
                int loadingStation = lLoadingStations.get(i);
                if (i == 0) {
                    cLoadingStations = cLoadingStations + "{\""+i+"\" : "+loadingStation;
                } else {
                    cLoadingStations = cLoadingStations + ", \""+i+"\" : "+loadingStation;
                }
            }
            cLoadingStations = cLoadingStations+"}";
        } else {
            cLoadingStations = "null";
        }



        String jsonString = "{\"TYPE_STOCK\": "+tStock+", \"LIST_LOADINGSTATIONS\" :"+cLoadingStations+"}";

        Log.i("json_text: ", jsonString);
        try {
            JSONObject json = new JSONObject(jsonString);
            return json;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // ModelColorShape

    public Integer getIdModelColorShape() {
        if (lModelColorShapes.size() > 0) {
            return lModelColorShapes.get(0);
        } else {
            load();
            return null;
        }
    }

    public void removeIdModelColorShape (Integer kModelColorShape) {
        lModelColorShapes.remove(kModelColorShape);
        updateLayout();
    }



    public int getStock() {
        return SharedPreferences.getBoolean(Constants_Intern.JUICER_STOCK_PRIME_SELECTED, true) ? Constants_Intern.STATION_PRIME_STOCK : Constants_Intern.STATION_EXCESS_STOCK;
    }


    // Return

    @Override
    public void returnDisplay(String cTag) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_DISPLAY_STOCK_EMPTY:
                load();
        }
    }

    @Override
    public void onClick(View view) {
        cVolley.stopRequests();
        switch (view.getId()) {
            case R.id.tvStockPrime:
                SharedPreferences.edit().putBoolean(Constants_Intern.JUICER_STOCK_PRIME_SELECTED, (!SharedPreferences.getBoolean(Constants_Intern.JUICER_STOCK_PRIME_SELECTED, true))).commit();
                SharedPreferences.edit().putBoolean(Constants_Intern.JUICER_STOCK_EXCESS_SELECTED, (!SharedPreferences.getBoolean(Constants_Intern.JUICER_STOCK_PRIME_SELECTED, false))).commit();
                break;
            case R.id.tvStockExcess:
                SharedPreferences.edit().putBoolean(Constants_Intern.JUICER_STOCK_EXCESS_SELECTED, (!SharedPreferences.getBoolean(Constants_Intern.JUICER_STOCK_EXCESS_SELECTED, false))).commit();
                SharedPreferences.edit().putBoolean(Constants_Intern.JUICER_STOCK_PRIME_SELECTED, (!SharedPreferences.getBoolean(Constants_Intern.JUICER_STOCK_EXCESS_SELECTED, true))).commit();
                break;
            case R.id.tvLoadingStationOne:
                SharedPreferences.edit().putBoolean(Constants_Intern.JUICER_LOADING_STATION_ONE_SELECTED, (!SharedPreferences.getBoolean(Constants_Intern.JUICER_LOADING_STATION_ONE_SELECTED, true))).commit();
                break;
            case R.id.tvLoadingStationTwo:
                SharedPreferences.edit().putBoolean(Constants_Intern.JUICER_LOADING_STATION_TWO_SELECTED, (!SharedPreferences.getBoolean(Constants_Intern.JUICER_LOADING_STATION_TWO_SELECTED, true))).commit();
                break;
            case R.id.tvLoadingStationThree:
                SharedPreferences.edit().putBoolean(Constants_Intern.JUICER_LOADING_STATION_THREE_SELECTED, (!SharedPreferences.getBoolean(Constants_Intern.JUICER_LOADING_STATION_THREE_SELECTED, true))).commit();
                break;
            case R.id.tvLoadingStationFour:
                SharedPreferences.edit().putBoolean(Constants_Intern.JUICER_LOADING_STATION_FOUR_SELECTED, (!SharedPreferences.getBoolean(Constants_Intern.JUICER_LOADING_STATION_FOUR_SELECTED, true))).commit();
                break;
        }
        updateLayout();
        load();
    }
}
