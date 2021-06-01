package com.example.ericschumacher.bouncer.Activities.Tools;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List_Battery;
import com.example.ericschumacher.bouncer.Adapter.Pager.Adapter_Pager_Battery;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Others.Fragment_Batteries;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Battery;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Views.ViewPager_Eric;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Activity_Battery extends AppCompatActivity implements View.OnClickListener, Interface_Battery {

    // Layout
    ImageView ivClear;
    EditText etSearch;
    FrameLayout flSearch;
    TabLayout TabLayout;
    ViewPager_Eric ViewPager;
    //FloatingActionButton fabMerge;

    // Adapter
    Adapter_List_Battery aListBattery;
    Adapter_Pager_Battery aPagerBattery;

    // Connection
    Volley_Connection cVolley;

    // Else
    ArrayList<Battery> lBatteries = new ArrayList<>();
    ArrayList<Manufacturer> lManufacturer = new ArrayList<>();
    private Handler mHandler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connection
        mHandler = new Handler(Looper.getMainLooper());
        cVolley = new Volley_Connection(this);

        // Layout
        setLayout();
    }

    private void setLayout() {
        setContentView(R.layout.activity_battery);

        ivClear = findViewById(R.id.ivAction);
        etSearch = findViewById(R.id.etSearch);
        flSearch = findViewById(R.id.flSearch);
        TabLayout = findViewById(R.id.vTabLayout);
        ViewPager = findViewById(R.id.ViewPager);
        //fabMerge = findViewById(R.id.fabMerge);
        ViewPager.setSwipeable(false);

        ivClear.setOnClickListener(this);
        //fabMerge.setOnClickListener(this);

        // Fragment
        Fragment_Batteries fBatteries = new Fragment_Batteries();
        Bundle bData = new Bundle();
        bData.putInt(Constants_Intern.DATA_TYPE, Constants_Intern.DATA_TYPE_NAME_PART);
        fBatteries.setArguments(bData);
        getSupportFragmentManager().beginTransaction().replace(R.id.flSearch, fBatteries, Constants_Intern.FRAGMENT_BATTERIES_SEARCH_RESULTS).commit();

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) {
                    ((Fragment_Batteries)getSupportFragmentManager().findFragmentByTag(Constants_Intern.FRAGMENT_BATTERIES_SEARCH_RESULTS)).updateNamePart(editable.toString());
                }
                else {
                    ((Fragment_Batteries)getSupportFragmentManager().findFragmentByTag(Constants_Intern.FRAGMENT_BATTERIES_SEARCH_RESULTS)).clearSearch();
                }
            }
        });

        // TabLayout 6 ViewPager
        TabLayout.setupWithViewPager(ViewPager);
        TabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MANUFACTURER_ALL, null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                    JSONArray jsonArray = oJson.getJSONArray(Constants_Extern.LIST_MANUFACTURER);
                    for (int i = 0; i<jsonArray.length(); i++) {
                        Manufacturer oManufacturer = new Manufacturer(jsonArray.getJSONObject(i));
                        lManufacturer.add(oManufacturer);
                    }
                aPagerBattery = new Adapter_Pager_Battery(getSupportFragmentManager(), lManufacturer);
                ViewPager.setAdapter(aPagerBattery);
            }
        });



    }

    private void reset() {
        lBatteries.clear();
        aListBattery.notifyDataSetChanged();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivAction:
                etSearch.setText("");
                break;
                /*
            case R.id.fabMerge:
                cVolley.execute(Request.Method.POST, Urls.URL_MERGE_BATTERY, null);
                Intent intent = getIntent();
                finish();
                startActivity(intent);
                break;
                */
        }
    }

    @Override
    public void getBatteries(int kManufacturer) {
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_BATTERIES_BY_MANUFACTURER + Integer.toString(kManufacturer), null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                ArrayList<Battery> lBatteries = new ArrayList<>();
                if (oJson.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                    JSONArray jsonArray = oJson.getJSONArray(Constants_Extern.LIST_BATTERIES);
                    for (int i = 0; i<jsonArray.length(); i++) {
                        Battery oBattery = new Battery(jsonArray.getJSONObject(i), Activity_Battery.this);
                        lBatteries.add(oBattery);
                    }

                }
            }
        });
    }

    @Override
    public void deleteBattery(Battery oBattery) {
        cVolley.execute(Request.Method.DELETE, Urls.URL_DELETE_BATTERY+Integer.toString(oBattery.getId()), null);
    }




}