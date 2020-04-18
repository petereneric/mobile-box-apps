package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List_Battery;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog.Fragment_Dialog_Battery_Storage_Level;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Batteries extends Fragment implements Interface_Update {

    View lFragment;
    RecyclerView rvBatteries;
    Adapter_List_Battery aBattery;

    // Data
    private int tData;
    private Integer kManufacturer = null;
    private String cNamePart = null;
    private String cShortcut = null;
    ArrayList<Battery> lBatteries = new ArrayList<>();

    // Interface

    // Connection
    Volley_Connection cVolley;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // vLayout
        lFragment = inflater.inflate(R.layout.fragment_batteries, container, false);

        // rvList
        rvBatteries = lFragment.findViewById(R.id.rvBatteries);
        rvBatteries.setLayoutManager(new LinearLayoutManager(getActivity()));
        aBattery = new Adapter_List_Battery(getActivity(), lBatteries, this);
        rvBatteries.setAdapter(aBattery);

        // Data
        tData = getArguments().getInt(Constants_Intern.DATA_TYPE, 0);
        if (tData == Constants_Intern.DATA_TYPE_ID_MANUFACTURER) {
            kManufacturer = getArguments().getInt(Constants_Intern.ID_MANUFACTURER);
        }

        cVolley = new Volley_Connection(getActivity());
        update();

        return lFragment;
    }

    public void requestStorageLevel(int position) {
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment_Dialog_Battery_Storage_Level fStorageLevel = new Fragment_Dialog_Battery_Storage_Level();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants_Intern.POSITION_BATTERY, position);
        bundle.putInt(Constants_Intern.STORAGE_LEVEL, lBatteries.get(position).getlStock());
        fStorageLevel.setArguments(bundle);
        fStorageLevel.setTargetFragment(Fragment_Batteries.this, 0);
        fStorageLevel.show(ft, Constants_Intern.FRAGMENT_DIALOG_BATTERY_STORAGE_LEVEL);
    }

    public void updateStorageLevel(int position, int lStorage) {
        int oldLevelStock = lBatteries.get(position).getlStock();
        lBatteries.get(position).setlStock(lStorage);
        if (oldLevelStock == Constants_Intern.NO_STORAGE) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    update();
                }
            }, 500);

        } else {
            aBattery.notifyDataSetChanged();
        }
    }

    @Override
    public void update() {
        lBatteries.clear();
        if (tData == Constants_Intern.DATA_TYPE_NAME_PART && cNamePart != null) {
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_BATTERIES_BY_NAME_PART+cNamePart, null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    if (oJson.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                        lBatteries.clear();
                        JSONArray jsonArray = oJson.getJSONArray(Constants_Extern.LIST_BATTERIES);
                        for (int i = 0; i<jsonArray.length();i++) {
                            Battery oBattery = new Battery(jsonArray.getJSONObject(i), getActivity());
                            lBatteries.add(oBattery);
                        }
                        aBattery.notifyDataSetChanged();
                        //aListBattery = new Adapter_List_Battery(Activity_Battery.this, batteries);
                        //rvSearch.setAdapter(aListBattery);
                    }
                }
            });
        }
        if (tData == Constants_Intern.DATA_TYPE_ID_MANUFACTURER) {
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_BATTERIES_BY_MANUFACTURER + Integer.toString(kManufacturer), null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    if (oJson.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                        JSONArray jsonArray = oJson.getJSONArray(Constants_Extern.LIST_BATTERIES);
                        for (int i = 0; i<jsonArray.length(); i++) {
                            Battery oBattery = new Battery(jsonArray.getJSONObject(i), getActivity());
                            lBatteries.add(oBattery);
                        }
                    }
                    aBattery.notifyDataSetChanged();
                }
            });
        }

    }

    public void updateNamePart(String cNamePart) {
        this.cNamePart = cNamePart;
        update();
    }

    public void clearSearch() {
        cNamePart = null;
        lBatteries.clear();
    }

    public void delete(int position) {
        cVolley.execute(Request.Method.DELETE, Urls.URL_DELETE_BATTERY+Integer.toString(lBatteries.get(position).getId()), null);
        lBatteries.remove(position);
        aBattery.notifyDataSetChanged();
    }

    public void editName(int position, String cName) {
        lBatteries.get(position).setName(cName);
        aBattery.notifyDataSetChanged();
    }
}
