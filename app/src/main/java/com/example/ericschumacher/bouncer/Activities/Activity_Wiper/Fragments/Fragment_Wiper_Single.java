package com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Adapter.Pager.Adapter_Pager_Wiper_Single;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Activity_Wiper;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Wiper;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Wiper_Single;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.ModelCheck;
import com.example.ericschumacher.bouncer.Objects.ModelWipe;
import com.example.ericschumacher.bouncer.Objects.Protocol;
import com.example.ericschumacher.bouncer.Objects.ProtocolWipe;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Views.ViewPager_Eric;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class Fragment_Wiper_Single extends Fragment implements Interface_Fragment_Wiper_Single, Interface_Fragment_Wiper {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // VALUES & VARIABLES

    // Layout
    View vLayout;
    ViewPager_Eric ViewPager;
    TabLayout TabLayout;

    // Adapter
    Adapter_Pager_Wiper_Single aSingle;

    // Interfaces
    Fragment_Device.Interface_Device iDevice;
    Interface_Activity_Wiper iWiper;

    // Connection
    Volley_Connection cVolley;

    // Data
    ArrayList<Protocol> lProtocols = new ArrayList<>();
    Protocol oProtocol;

    // Fragments
    Fragment_Wiper_Protocol_Container fProtocolContainer;
    Fragment_Edit_Model_Wipe fModelWipe;


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LIFE-CYCLE

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        // Interfaces
        iDevice = (Fragment_Device.Interface_Device)getActivity();
        iWiper = (Interface_Activity_Wiper)getActivity();

        // Layout
        setLayout(inflater, container);

        return vLayout;
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PRIVATE

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(R.layout.fragment_wiper, container, false);
        ViewPager = vLayout.findViewById(R.id.ViewPager);
        TabLayout = vLayout.findViewById(R.id.TabLayout);

        // Adapter
        aSingle = new Adapter_Pager_Wiper_Single(getActivity().getSupportFragmentManager());

        // Tabs
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
        fProtocolContainer = new Fragment_Wiper_Protocol_Container();
        fProtocolContainer.setTargetFragment(Fragment_Wiper_Single.this, 0);
        aSingle.add(getString(R.string.protocol), fProtocolContainer);
        fModelWipe = new Fragment_Edit_Model_Wipe();
        fModelWipe.setTargetFragment(Fragment_Wiper_Single.this, 1);
        aSingle.add(getString(R.string.edit_model_wipes), fModelWipe);
        Fragment_Result_Wiper fResult = new Fragment_Result_Wiper();
        fResult.setTargetFragment(Fragment_Wiper_Single.this, 2);
        aSingle.add(getString(R.string.handling), fResult);
        ViewPager.setAdapter(aSingle);
        aSingle.notifyDataSetChanged();
    }

    private void loadProtocols() {
        lProtocols.clear();
        if (iDevice != null && iDevice.getDevice() != null) {
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_PROTOCOLS + iDevice.getDevice().getIdDevice(), null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    if (oJson != null) {
                        JSONArray jsonArray = oJson.getJSONArray("lProtocols");
                        for (int i = 0; i <jsonArray.length(); i++) {
                            JSONObject jsonProtocol = jsonArray.getJSONObject(i);
                            Protocol oProtocol = new Protocol(cVolley, jsonProtocol);
                            lProtocols.add(oProtocol);
                        }
                        Collections.sort(lProtocols);
                    }

                    if (aSingle != null) {
                        aSingle.update();
                    }
                }
            });
        }
    }





    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PUBLIC

    public void update() {
        oProtocol = null;
        loadProtocols();
        //loadModelChecks();
    }

    @Override
    public ArrayList<Protocol> getProtocols() {
        return lProtocols;
    }

    @Override
    public Protocol getProtocol() {
        return oProtocol;
    }

    @Override
    public void editProtocol(int position) {
        oProtocol = lProtocols.get(position);
        fProtocolContainer.showProtocol();
    }

    @Override
    public void addProtocol() {
        oProtocol = null;
        fProtocolContainer.showProtocol();
    }

    @Override
    public void showHandler() {
        ViewPager.setCurrentItem(2);
    }

    @Override
    public void showDefects() {

    }

    @Override
    public void setProtocol(Protocol protocol) {
        oProtocol = protocol;
    }

    @Override
    public ArrayList<ModelWipe> getModelWipes() {
        return iWiper.getModelWipes();
    }

    @Override
    public void changeModelWipes(boolean updateModelWipes) {

    }


}
