package com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Adapter.Pager.Adapter_Pager_Wiper_Multi;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Activity_Wiper;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Wiper;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Wiper_Multi;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.ModelWipe;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_DateTime;
import com.example.ericschumacher.bouncer.Views.ViewPager_Eric;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import java.util.ArrayList;

public class Fragment_Wiper_Multi extends Fragment implements Interface_Fragment_Wiper, Interface_Fragment_Wiper_Multi {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // VALUES & VARIABLES

    // Constants
    public static final int MODE_NO_NEGATIVE_PROTOCOLS = 0;
    public static final int MODE_ALL_POSITIVE_PROTOCOLS = 1;
    public static final int MODE_NEGATIVE_PROTOCOLS = 2;

    // Layout
    View vLayout;
    ViewPager_Eric ViewPager;
    android.support.design.widget.TabLayout TabLayout;

    // Adapter
    Adapter_Pager_Wiper_Multi aMulti;

    // Interfaces
    Fragment_Device.Interface_Device iDevice;
    Interface_Activity_Wiper iWiperMain;

    // Connection
    Volley_Connection cVolley;

    // Data
    ArrayList<ModelWipe> lModelWipes = new ArrayList<>();

    // Fragments
    Fragment_Wiper_Protocol_Multi fProtocol;
    Fragment_Edit_Model_Wipe fModelWipe;

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LIFE-CYCLE

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        // Interfaces
        iDevice = (Fragment_Device.Interface_Device)getActivity();
        iWiperMain = (Interface_Activity_Wiper)getActivity();

        // Layout
        setLayout(inflater, container);
        base();

        return vLayout;
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(R.layout.fragment_wiper, container, false);
        ViewPager = vLayout.findViewById(R.id.ViewPager);
        TabLayout = vLayout.findViewById(R.id.TabLayout);

        // Adapter
        aMulti = new Adapter_Pager_Wiper_Multi(getActivity().getSupportFragmentManager());

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
        fProtocol = new Fragment_Wiper_Protocol_Multi();
        fProtocol.setTargetFragment(Fragment_Wiper_Multi.this, 0);
        aMulti.add(getString(R.string.protocol), fProtocol);
        fModelWipe = new Fragment_Edit_Model_Wipe();
        fModelWipe.setTargetFragment(Fragment_Wiper_Multi.this, 1);
        aMulti.add(getString(R.string.edit_model_wipes), fModelWipe);
        Fragment_Result_Wiper fResult = new Fragment_Result_Wiper();
        fResult.setTargetFragment(Fragment_Wiper_Multi.this, 2);
        aMulti.add(getString(R.string.handling), fResult);
        ViewPager.setAdapter(aMulti);
        aMulti.notifyDataSetChanged();
    }

    private int getMode() {
        Boolean positiveProtocols = null;
        for (int i = 0; i < iWiperMain.getSelectedDevices().size(); i++) {
            Device device = iWiperMain.getSelectedDevices().get(i);
            if (device.getlProtocols().size() > 0) {
                Boolean positiveProtocol = null;
                if (DateUtils.isToday(device.getlProtocols().get(0).getdCreation().getTime())) {
                    positiveProtocol = device.getlProtocols().get(0).isbPassed();
                    if (!positiveProtocol) {
                        positiveProtocols = false;
                        break;
                    } else {
                        if (i == 0 && positiveProtocol) {
                            positiveProtocols = true;
                        }
                    }
                }

            } else {
                positiveProtocols = null;
            }
        }
        if (positiveProtocols != null) {
            if (positiveProtocols) {
                return MODE_ALL_POSITIVE_PROTOCOLS;
            } else {
                return MODE_NEGATIVE_PROTOCOLS;
            }
        } else {
            return MODE_NO_NEGATIVE_PROTOCOLS;
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PUBLIC

    public void update() {
        aMulti.update();
    }

    public void base() {
        switch (getMode()) {
            case MODE_ALL_POSITIVE_PROTOCOLS:
                ViewPager.setCurrentItem(2);
                break;
            case MODE_NO_NEGATIVE_PROTOCOLS:
                if (iWiperMain.getSelectedModel().getlModelWipes().size() == 0) {
                    ViewPager.setCurrentItem(1);
                } else {
                    ViewPager.setCurrentItem(0);
                }
                break;
            case MODE_NEGATIVE_PROTOCOLS:
                // Show Devices
                break;
        }
    }

    @Override
    public ArrayList<ModelWipe> getModelWipes() {
        return iWiperMain.getModelWipes();
    }

    @Override
    public void changeModelWipes(boolean updateModelWipes) {

    }

    @Override
    public void finishAll() {

    }
}
