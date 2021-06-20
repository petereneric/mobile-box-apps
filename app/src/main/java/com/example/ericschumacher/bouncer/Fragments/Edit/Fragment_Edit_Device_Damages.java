package com.example.ericschumacher.bouncer.Fragments.Edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Multiple_Choice_Device_Damages;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Object_Device_Damage;
import com.example.ericschumacher.bouncer.Objects.Object_Model_Damage;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Edit_Device_Damages extends Fragment_Edit {

    // Adapter
    Adapter_Multiple_Choice_Device_Damages aModelDamages;

    // Data
    String strModelDamages;
    String strDeviceDamages;
    ArrayList<Object_Device_Damage> lDeviceDamages = new ArrayList<>();
    ArrayList<Object_Model_Damage> lModelDamages = new ArrayList<>();
    int kDevice;

    // Interface
    Fragment_Device.Interface_Device iDevice;
    Interface_Edit_Device_Damages iEditDeviceDamages;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Interface
        iDevice = (Fragment_Device.Interface_Device)getActivity();
        iEditDeviceDamages = (Interface_Edit_Device_Damages) getActivity();

        // Data
        kDevice = iDevice.getDevice().getIdDevice();

        lDeviceDamages = iDevice.getDevice().getlDeviceDamages();

        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_DAMAGE + iDevice.getDevice().getoModel().getkModel(), null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (Volley_Connection.successfulResponse(oJson)) {
                    // Damages found
                    JSONArray aJson = oJson.getJSONArray(Constants_Extern.LIST_DAMAGES);
                    for (int i = 0; i < aJson.length(); i++) {
                        JSONObject oJsonModelDamage = aJson.getJSONObject(i);
                        Object_Model_Damage oModelDamage = new Object_Model_Damage(getActivity(), oJsonModelDamage);
                        lModelDamages.add(oModelDamage);
                    }
                    for (Object_Device_Damage oDeviceDamage : lDeviceDamages) {
                        for (Object_Model_Damage oModelDamage : new ArrayList<Object_Model_Damage>(lModelDamages)) {
                            if (oDeviceDamage.getoModelDamage().equals(oModelDamage)) {
                                lModelDamages.remove(oModelDamage);
                            }
                        }
                    }
                    // Adapter
                    aModelDamages = new Adapter_Multiple_Choice_Device_Damages(getActivity(), lDeviceDamages, lModelDamages, kDevice, Fragment_Edit_Device_Damages.this);
                    rvList.setAdapter(aModelDamages);
                }
            }
        });



        return vLayout;
    }

    @Override
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);
        rvList.setLayoutManager(new GridLayoutManager(getActivity(), 3));

        // Visibility
        bCommit.setVisibility(View.VISIBLE);
    }


    @Override
    public void onCommit() {
        iEditDeviceDamages.returnEditDeviceDamages(Constants_Intern.TYPE_ACTION_DEVICE_DAMAGES_COMMIT, getTag());
    }

    public void clickOtherDamages() {
        iEditDeviceDamages.returnEditDeviceDamages(Constants_Intern.TYPE_ACTION_DEVICE_DAMAGES_OTHER_DAMAGES, getTag());
    }

    public void clickOverbroken() {
        iEditDeviceDamages.returnEditDeviceDamages(Constants_Intern.TYPE_ACTION_DEVICE_DAMAGES_OVERBROKEN, getTag());
    }

    public interface Interface_Edit_Device_Damages {
        void returnEditDeviceDamages(int tActionDeviceDamages, String cTag);
    }

}


