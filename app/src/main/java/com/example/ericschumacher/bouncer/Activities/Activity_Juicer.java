package com.example.ericschumacher.bouncer.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Juicer_Charger;
import com.example.ericschumacher.bouncer.Adapter.Pager.Adapter_Pager_ModelColorShape;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Devices;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Devices;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Juicer;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Request_Choice;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Charger;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 08.07.2018.
 */

public class Activity_Juicer extends AppCompatActivity implements Interface_Juicer, Interface_Devices, Interface_Request_Choice, Adapter_Juicer_Charger.Interface_Adapter_Juicer_Charger {

    private static final String URL_JUICER_DEVICES = "http://svp-server.com/svp-gmbh/erp/bouncer/src/api.php/juicer/devices/";

    // Data
    ArrayList<Charger> lCharger = new ArrayList<>();
    ArrayList<Integer> lModelColorShapeIds = new ArrayList<>();

    // Layout
    ViewPager ViewPager;
    RecyclerView rvCharger;

    // Adapter
    Adapter_Pager_ModelColorShape aLKUs;
    Adapter_Juicer_Charger aCharger;

    // Utilities
    Utility_Network uNetwork;

    // Connection
    Volley_Connection vConnection;

    // Else
    FragmentManager fManager;
    ArrayList<Charger> lChargerUnselected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setLayout();

        uNetwork = new Utility_Network(this);
        fManager = getSupportFragmentManager();


        // RecyclerView
        rvCharger.setLayoutManager(new GridLayoutManager(this, 3, LinearLayoutManager.HORIZONTAL, false));
        aCharger = new Adapter_Juicer_Charger(Activity_Juicer.this, lCharger, Activity_Juicer.this);
        rvCharger.setAdapter(aCharger);

        ArrayList<Integer> test = new ArrayList<>();

        aLKUs = new Adapter_Pager_ModelColorShape(test, getSupportFragmentManager());
        ViewPager.setAdapter(aLKUs);


        // Connection
        vConnection = new Volley_Connection(this);
        //simpleCheck();

        lChargerUnselected = new ArrayList<>();
        uNetwork.getChargers(new Interface_VolleyCallback_ArrayList_Charger() {
            @Override
            public void onSuccess(ArrayList<Charger> list) {
                Log.i("List size", Integer.toString(list.size()));
                aCharger.updateData(list);
                onChargerChanged(lChargerUnselected);
            }
        });
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

    }

    @Override
    protected void onResume() {
        super.onResume();

        Log.i("Activity_Juicer", "onResume");
    }

    private void setLayout() {
        setContentView(R.layout.activity_juicer);
        ViewPager = findViewById(R.id.ViewPager);
        rvCharger = findViewById(R.id.rvCharger);
    }

    @Override
    public void returnCharger(Charger charger) {

    }

    @Override
    public void removeIdModelColorShape(int idModelColorShape) {

        lModelColorShapeIds.remove(new Integer(idModelColorShape));
        if (lModelColorShapeIds.size() == 0 || false) {
            onChargerChanged(lChargerUnselected);
        } else {
            aLKUs = new Adapter_Pager_ModelColorShape(lModelColorShapeIds, getSupportFragmentManager());
            ViewPager.setAdapter(aLKUs);
        }
        //aLKUs.updateData(lModelColorShapeIds);
    }

    @Override
    public void scan(Device device, boolean isPartOfList) {
        device.setoStoragePlace(null);
        Toast.makeText(this, getString(R.string.device_written_off), Toast.LENGTH_LONG).show();
    }

    @Override
    public void delete(Device device) {
        device.setoStoragePlace(null);
    }

    @Override
    public void getDevices(Bundle data, final Interface_Fragment_Devices iCallback) {
        vConnection.getResponse(Request.Method.GET, URL_JUICER_DEVICES + Integer.toString(data.getInt(Constants_Intern.ID_MODEL_COLOR_SHAPE)), null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) {
                try {
                    if (oJson.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                        JSONArray jsonArray = oJson.getJSONArray(Constants_Extern.DEVICES);
                        ArrayList<Device> lDevices = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Device oDevice = new Device(jsonArray.getJSONObject(i), Activity_Juicer.this);
                            lDevices.add(oDevice);
                        }
                        iCallback.setDevices(lDevices);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        /*uNetwork.getDevicesByIdModelColorShape(data.getInt(Constants_Intern.ID_MODEL_COLOR_SHAPE), Station.getId(Constants_Intern.STATION_LKU_STOCKING), new Interface_VolleyCallback_ArrayList_Devices() {
            @Override
            public void onSuccess(ArrayList<Device> devices) {
                iCallback.setDevices(devices);
            }

            @Override
            public void onFailure() {

            }
        });*/
    }

    @Override
    public void onChoice(int position) {
    }

    @Override
    public void onAdd() {

    }

    @Override
    public void onChargerChanged(ArrayList<Charger> chargerUnselected) {
        ViewPager.setBackgroundColor(getResources().getColor(R.color.color_primary_dark));
        Log.i("Activity_Juicer", "onChargerChanged");
        ArrayList<Charger> list = new ArrayList<>();
        for (Charger Charger2 : chargerUnselected) {
            list.add(new Charger(Charger2.getId(), Charger2.getName()));
        }
        lChargerUnselected = list;
        JSONObject jsonBody = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        String test = "{";
        for (int i = 0; i < list.size(); i++) {
            Charger charger = list.get(i);
            if (i != 0 || (i != 0 && i == list.size() - 1)) {
                test = test + ",";
            }
            test = test + "\"" + Integer.toString(i) + "\"" + " : " + Integer.toString(charger.getId());
            JSONObject json = new JSONObject();
            try {
                json.put(Integer.toString(i), Integer.toString(charger.getId()));
                jsonArray.put(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //jsonBody.put(Integer.toString(i), Integer.toString(charger.getId()));

        }
        JSONArray jsonArrayObjectModelColorShapeIds = new JSONArray();
        for (int i = 0; i < lModelColorShapeIds.size(); i++) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put(Integer.toString(i), Integer.toString(lModelColorShapeIds.get(i)));
                jsonArrayObjectModelColorShapeIds.put(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        test = test + "}";
        Log.i("Charger not selected", test);
        JSONObject json = null;
        try {
            json = new JSONObject(test);
            jsonBody.put(Constants_Extern.LIST_CHOICE_CHARGER, jsonArray);
            jsonBody.put(Constants_Extern.LIST_MODEL_COLOR_SHAPE_IDS, jsonArrayObjectModelColorShapeIds);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("CHEEECK", jsonBody.toString());

        //vConnection.getResponseWithString(Request.Method.PUT, "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/ids/juicer", jsonBody);

        vConnection.getResponse(Request.Method.PUT, "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/ids/juicer", json, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                JSONArray jsonArray = oJson.getJSONArray(Constants_Extern.IDS);
                lModelColorShapeIds = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    Log.i("Looping", "Array");
                    JSONObject json = jsonArray.getJSONObject(i);
                    lModelColorShapeIds.add(new Integer(json.getInt(Constants_Extern.ID)));
                }
                ViewPager.setBackgroundColor(getResources().getColor(R.color.color_white));
                aLKUs = new Adapter_Pager_ModelColorShape(lModelColorShapeIds, getSupportFragmentManager());
                ViewPager.setAdapter(aLKUs);
            }
        });

        /*
        uNetwork.getDevicesForJuicer(chargerUnselected, new Interface_VolleyCallback_ArrayList_ModelColorShapeIds() {
            @Override
            public void onSuccess(ArrayList<Integer> modelColorShapeIds) {
                lModelColorShapeIds = modelColorShapeIds;
                Log.i("modelColorShapeIds", Integer.toString(modelColorShapeIds.size()));
                ViewPager.setBackgroundColor(getResources().getColor(R.color.color_white));
                aLKUs = new Adapter_Pager_ModelColorShape(lModelColorShapeIds, getSupportFragmentManager());
                ViewPager.setAdapter(aLKUs);
            }
            @Override
            public void onFailure() {
            }
        });
        */

    }


    private void simpleCheck() {
        /*
        vConnection.getResponseWithString(Request.Method.PUT, "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/ids/juicer", "{\"0\" : 2}", new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                Log.i("RESSULLT", oJson.toString());
                JSONArray jsonArray = oJson.getJSONArray(Constants_Extern.IDS);
                ArrayList<Integer> modelColorShapeIds = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    Log.i("Looping", "Array");
                    JSONObject json = jsonArray.getJSONObject(i);
                    modelColorShapeIds.add(new Integer (json.getInt(Constants_Extern.ID)));
                }
                //ViewPager.setBackgroundColor(getResources().getColor(R.color.color_white));
                //aLKUs = new Adapter_Pager_ModelColorShape(modelColorShapeIds, getSupportFragmentManager());
                //ViewPager.setAdapter(aLKUs);
            }
        });
        */
    }
}


