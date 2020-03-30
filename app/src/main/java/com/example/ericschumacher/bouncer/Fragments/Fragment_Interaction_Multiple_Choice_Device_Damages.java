package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Multiple_Choice_Device_Damages;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_DeviceManager;
import com.example.ericschumacher.bouncer.Objects.Object_Device_Damage;
import com.example.ericschumacher.bouncer.Objects.Object_Model_Damage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Interaction_Multiple_Choice_Device_Damages extends Fragment_Interaction_Multiple_Choice {

    // Adapter
    Adapter_Multiple_Choice_Device_Damages aModelDamages;

    // Data
    String strModelDamages;
    String strDeviceDamages;
    ArrayList<Object_Device_Damage> lDeviceDamages = new ArrayList<>();
    ArrayList<Object_Model_Damage> lModelDamages = new ArrayList<>();
    int kDevice;

    // Interface
    Interface_DeviceManager iDeviceManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return Layout;
    }



    @Override
    public void setAdapter() {
        aModelDamages = new Adapter_Multiple_Choice_Device_Damages(getActivity(), lDeviceDamages, lModelDamages, kDevice, this);
        rvMultipleChoice.setAdapter(aModelDamages);
    }

    @Override
    public void setLayout() {
        super.setLayout();
        rvMultipleChoice.setLayoutManager(new GridLayoutManager(getActivity(), 3));

    }

    @Override
    public void getData() {
        super.getData();
        kDevice = dBundle.getInt(Constants_Intern.ID_DEVICE);
        strModelDamages = dBundle.getString(Constants_Intern.STRING_MODEL_DAMAGES);
        strDeviceDamages = dBundle.getString(Constants_Intern.STRING_DEVICE_DAMAGES);
        Log.i("Pre2 Model Damages: ", strModelDamages);
        Log.i("Pre2 Device Damages: ", strDeviceDamages);
        try {
            JSONArray aJson = new JSONArray(strModelDamages);
            for (int i = 0; i < aJson.length(); i++) {
                JSONObject oJsonModelDamage = aJson.getJSONObject(i);
                Object_Model_Damage oModelDamage = new Object_Model_Damage(getActivity(), oJsonModelDamage);
                lModelDamages.add(oModelDamage);
            }

            JSONArray aJsonDeviceDamages = new JSONArray(strDeviceDamages);
            for (int i = 0; i < aJsonDeviceDamages.length(); i++) {
                JSONObject oJsonDeviceDamage = aJsonDeviceDamages.getJSONObject(i);
                Object_Device_Damage oDeviceDamage = new Object_Device_Damage(getActivity(), oJsonDeviceDamage);
                lDeviceDamages.add(oDeviceDamage);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.i("Size Model Damages: ", ""+lModelDamages.size()+" "+"Size Device Damages: "+lDeviceDamages.size());
    }

    @Override
    public void setInterface() {
        iDeviceManager = (Interface_DeviceManager)getActivity();
    }

    @Override
    public void onCommit() {
        iDeviceManager.returnDamages(aModelDamages.getlDeviceDamages());
    }

    public void clickOtherDamages() {
        iDeviceManager.onClickInteractionButton(Constants_Intern.FRAGMENT_MULTIPLE_CHOICE_DEVICE_DAMAGES, Constants_Intern.BUTTON_OTHER_DAMAGES);
    }

    public void clickOverbroken() {
        iDeviceManager.onClickInteractionButton(Constants_Intern.FRAGMENT_MULTIPLE_CHOICE_DEVICE_DAMAGES, Constants_Intern.BUTTON_OVERBROKEN);
    }

}


