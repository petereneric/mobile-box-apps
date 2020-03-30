package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Multiple_Choice_Model_Battery;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Name_Battery;
import com.example.ericschumacher.bouncer.Interfaces.Interface_ModelBatteryManager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Request_Name;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.Objects.Model_Battery;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

public class Fragment_Interaction_Multiple_Choice_Model_Battery extends Fragment_Interaction_Multiple_Choice implements Interface_Request_Name {

    // Adapter
    Adapter_Multiple_Choice_Model_Battery aModelBatteries;

    // Data
    ArrayList<Model_Battery> lModelBatteries;
    int kModel;
    int tMode;

    // Interface
    Interface_ModelBatteryManager iModelBatteryManager;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Super
        super.onCreateView(inflater, container, savedInstanceState);

        return Layout;
    }

    @Override
    public void setAdapter() {
        aModelBatteries = new Adapter_Multiple_Choice_Model_Battery(getActivity(), lModelBatteries, this, tMode);
        rvMultipleChoice.setAdapter(aModelBatteries);
    }

    @Override
    public void getData() {
        super.getData();
        kModel = dBundle.getInt(Constants_Intern.ID_MODEL);
        tMode = dBundle.getInt(Constants_Intern.TYPE_MODE);
        lModelBatteries = iDeviceManager.getDevice().getoModel().getlModelBatteries();

        /*
        cVolley.getResponse(Request.Method.PUT, Urls.URL_GET_MODEL_BATTERY_BY_MODEL + Integer.toString(kModel), null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (Volley_Connection.successfulResponse(oJson)) {
                    JSONArray jsonArray = oJson.getJSONArray(Constants_Extern.LIST_MODEL_BATTERIES);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        Model_Battery oModelBattery = new Model_Battery(getActivity(), jsonArray.getJSONObject(i));
                        lModelBatteries.add(oModelBattery);
                    }
                }
                aModelBatteries.notifyDataSetChanged();
            }
        });
        */
    }

    @Override
    public void setInterface() {
        iModelBatteryManager = (Interface_ModelBatteryManager) getActivity();
    }

    @Override
    public void onCommit() {
        //iDeviceManager.returnModelBatteries(lModelBatteries);
        iDeviceManager.continueWithRoutine();
    }

    public void addBattery(Battery oBattery) {
        lModelBatteries.add(new Model_Battery(getActivity(), iDeviceManager.getModel().getkModel(), oBattery, 0));
        iDeviceManager.getDevice().getoModel().update();
        aModelBatteries.notifyDataSetChanged();
    }

    public void deleteModelBattery(Model_Battery oModelBattery) {
        lModelBatteries.remove(oModelBattery);
        iDeviceManager.getDevice().getoModel().update();
        aModelBatteries.notifyDataSetChanged();
    }

    public void showFragmentRequestNameBattery() {
        Fragment_Request_Name_Battery fragment = new Fragment_Request_Name_Battery();
        Bundle b = new Bundle();
        b.putInt(Constants_Intern.TYPE_INTERFACE, Constants_Intern.TYPE_INTERFACE_FROM_FRAGMENT);
        b.putString(Constants_Intern.INTERACTION_TITLE, getString(R.string.request_name_battery));
        fragment.setArguments(b);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.flFragmentInteraction, fragment, Constants_Intern.FRAGMENT_REQUEST_NAME_BATTERY_IN_EDIT_MODE).commit();
    }

    public void onClick(Model_Battery oModelBattery) {
        oModelBattery.changeStatus();
        if (oModelBattery.gettStatus() == Constants_Intern.MODEL_BATTERY_STATUS_PRIME) {
            for (Model_Battery modelBattery : lModelBatteries) {
                if (modelBattery.getkId() != oModelBattery.getkId() && modelBattery.gettStatus() == Constants_Intern.MODEL_BATTERY_STATUS_PRIME) {
                    modelBattery.settStatus(Constants_Intern.MODEL_BATTERY_STATUS_NORMAL );
                }
            }
        }
        Log.i("Hombre", "here");
        aModelBatteries.notifyDataSetChanged();
    }

    @Override
    public void returnRequestNameResult(String tFragment, Object object) {
        addBattery((Battery)object);
    }

    @Override
    public void returnRequestNameUnknown(int tObject) {

    }

    @Override
    public Model getModel() {
        return iDeviceManager.getDevice().getoModel();
    }
}
