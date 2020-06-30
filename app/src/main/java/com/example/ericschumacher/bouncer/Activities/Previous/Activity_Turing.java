package com.example.ericschumacher.bouncer.Activities.Previous;

import  android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Adapter.Pager.Adapter_Pager_Turing;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Table;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Turing.Fragment_List;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_List;
import com.example.ericschumacher.bouncer.Interfaces.Interface_List;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Ann;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Views.ViewPager_Eric;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

public class Activity_Turing extends AppCompatActivity implements Interface_List {

    // Layout
    android.support.design.widget.TabLayout TabLayout;
    android.support.v7.widget.Toolbar Toolbar;
    ViewPager_Eric ViewPager;

    Adapter_Pager_Turing aTuring;

    // Tags
    private final static String FRAGMENT_ERROR_MODEL = "FRAGMENT_ERROR_MODEL";
    private final static String FRAGMENT_ERROR_COLOR = "FRAGMENT_ERROR_COLOR";


    // Connection
    private final static String urlSynchronization = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/check/synchronization";
    private final static String urlDeleteModel = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/model/";
    private final static String urlDeleteModelColor = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/model_color/";
    private final static String urlFlush = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/rpd/flush/";
    Volley_Connection vConnection;

    // Data
    private Handler mHandler;
    JSONObject oJson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connection
        vConnection = new Volley_Connection(this);

        // Layout
        setLayout();

        // Data
        mHandler = new Handler(Looper.getMainLooper());
        loadData();
    }

    private void setLayout() {
        setContentView(R.layout.activity_turing);
        TabLayout = findViewById(R.id.TabLayout);
        Toolbar = findViewById(R.id.Toolbar);
        ViewPager = findViewById(R.id.ViewPager);
        ViewPager.setSwipeable(false);

        // Tabs
        TabLayout.addTab(TabLayout.newTab().setText(getString(R.string.tab_turing_one)));
        TabLayout.addTab(TabLayout.newTab().setText(getString(R.string.tab_turing_two)));
        TabLayout.addTab(TabLayout.newTab().setText(getString(R.string.tab_turing_three)));
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

        // Toolbar
        setSupportActionBar(Toolbar);
        getSupportActionBar().setTitle(getString(R.string.activity_name_turing));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


    }

    @Override
    public void onClick(JSONObject oJson, String tagFragment) {
        // edit
        switch (tagFragment) {
            case FRAGMENT_ERROR_MODEL:

                break;
            case FRAGMENT_ERROR_COLOR:

                break;
        }

    }

    @Override
    public void onSwipeLeft(final int position, JSONObject oJson, String tagFragment, final Interface_Fragment_List iFragmentList) throws JSONException {
        // rpd = 1000
        switch (tagFragment) {
            case FRAGMENT_ERROR_MODEL:
                Log.i("TEEEST: ", oJson.getString(Constants_Intern.ID_MODEL_COLOR));
                vConnection.getResponse(Request.Method.PUT, urlFlush + oJson.getString(Constants_Intern.ID_MODEL_COLOR), null, new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) {
                        Log.i("REESult: ", oJson.toString());
                        iFragmentList.remove(position);
                    }
                });
                break;
            case FRAGMENT_ERROR_COLOR:
                Log.i("TEEEST: ", oJson.getString(Constants_Intern.ID_MODEL_COLOR));
                vConnection.getResponse(Request.Method.PUT, urlFlush + oJson.getString(Constants_Intern.ID_MODEL_COLOR), null, new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) {
                        Log.i("REESult: ", oJson.toString());
                        iFragmentList.remove(position);
                    }
                });
                break;
        }
    }

    @Override
    public void onSwipeRight(final int position, JSONObject oJson, String tagFragment, final Interface_Fragment_List iFragmentList) throws JSONException {
        // delete
        switch (tagFragment) {
            case FRAGMENT_ERROR_MODEL:
                JSONObject jsonObject = oJson.getJSONObject(Constants_Intern.OBJECT_MODEL);
                vConnection.getResponse(Request.Method.DELETE, urlDeleteModel + jsonObject.getString(Constants_Intern.ID_MODEL), null, new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) {
                        iFragmentList.remove(position);
                    }
                });
                break;
            case FRAGMENT_ERROR_COLOR:
                vConnection.getResponse(Request.Method.DELETE, urlDeleteModelColor + oJson.getString(Constants_Intern.ID_MODEL_COLOR), null, new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) {
                        iFragmentList.remove(position);
                    }
                });
                break;
        }
    }

    @Override
    public JSONArray getData(String tagFragment) {
        Log.i("tagFragment", tagFragment);
        try {
            if (tagFragment == FRAGMENT_ERROR_MODEL) {
                //Log.i("FragmentModel", oJson.toString());
                return oJson.getJSONArray(Constants_Intern.ERROR_MODEL);
            } else {
                Log.i("FragmentModelColor", "works");
                return oJson.getJSONArray(Constants_Intern.ERROR_COLOR);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ArrayList<Ann> getAnn(String tagFragment) {
        if (tagFragment == FRAGMENT_ERROR_MODEL) {
            ArrayList<Ann> lAnn = new ArrayList<>();
            lAnn.add(new Ann(null, Constants_Intern.OBJECT_MODEL, Constants_Intern.ID_MODEL, getString(R.string.id), 1));
            lAnn.add(new Ann(Constants_Intern.OBJECT_MODEL,Constants_Intern.OBJECT_MANUFACTURER, Constants_Intern.NAME_MANUFACTURER, getString(R.string.manufacturer), 4));
            lAnn.add(new Ann(null, Constants_Intern.OBJECT_MODEL, Constants_Intern.NAME_MODEL, getString(R.string.model), 4));

            return lAnn;
        } else {
            ArrayList<Ann> lAnn = new ArrayList<>();
            lAnn.add(new Ann(null, null,Constants_Intern.ID_MODEL_COLOR, getString(R.string.id), 1));
            lAnn.add(new Ann(Constants_Intern.OBJECT_MODEL,Constants_Intern.OBJECT_MANUFACTURER, Constants_Intern.NAME_MANUFACTURER, getString(R.string.manufacturer), 4));
            lAnn.add(new Ann(null, Constants_Intern.OBJECT_MODEL, Constants_Intern.NAME_MODEL, getString(R.string.model), 4));
            lAnn.add(new Ann(null, Constants_Intern.OBJECT_COLOR, Constants_Intern.NAME_COLOR, getString(R.string.model), 2));
            return lAnn;
        }
    }

    @Override
    public void setSwipeViewLeft(TextView tvBackground) {
        tvBackground.setText(getString(R.string.flush));
        tvBackground.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.color_flush, null));
        tvBackground.setGravity(Gravity.CENTER_VERTICAL|Gravity.RIGHT);
    }

    @Override
    public void setSwipeViewRight(TextView tvBackground) {
        tvBackground.setText(getString(R.string.delete));
        tvBackground.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.color_delete, null));
        tvBackground.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
    }

    private void loadData() {
        OkHttpClient client = new OkHttpClient.Builder().connectTimeout(6000, TimeUnit.SECONDS).writeTimeout(10, TimeUnit.SECONDS).readTimeout(6000, TimeUnit.SECONDS).retryOnConnectionFailure(false).build();
        final okhttp3.Request request = new okhttp3.Request.Builder().url(urlSynchronization).get().build();
        Call call = client.newCall(request);
        Log.i("Jaaa", "ja");
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("Errror", "");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                try {
                    String r = response.body().string();
                    largeLog("RESULTT", r);
                    //Log.i("RESULTT", response.body().string());
                    oJson = new JSONObject(r);
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        // Set Adapter

                        aTuring = new Adapter_Pager_Turing(getSupportFragmentManager());
                        aTuring.add(getString(R.string.tab_turing_one), new Fragment_Table());
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants_Intern.TAG, Constants_Intern.FRAGMENT_ERROR_MODEL);
                        Fragment_List fListOne = new Fragment_List();
                        fListOne.setArguments(bundle);
                        aTuring.add(getString(R.string.tab_turing_two), fListOne);
                        Bundle bundleTwo = new Bundle();
                        bundleTwo.putString(Constants_Intern.TAG, Constants_Intern.FRAGMENT_ERROR_COLOR);
                        Fragment_List fListTwo = new Fragment_List();
                        fListTwo.setArguments(bundleTwo);
                        aTuring.add(getString(R.string.tab_turing_three), fListTwo);
                        ViewPager.setAdapter(aTuring);
                    }
                });
            }
        });
    }

    public static void largeLog(String tag, String content) {
        if (content.length() > 4000) {
            Log.d(tag, content.substring(0, 4000));
            largeLog(tag, content.substring(4000));
        } else {
            Log.d(tag, content);
        }
    }

    private void setColorStatusBar() {
        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(this,R.color.color_primary_dark_turing));
        }
    }
}
