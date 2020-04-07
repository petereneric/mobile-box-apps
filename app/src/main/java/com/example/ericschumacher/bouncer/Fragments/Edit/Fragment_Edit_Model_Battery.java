package com.example.ericschumacher.bouncer.Fragments.Edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Edit_Model_Battery;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Model_New_New;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Request_Name;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.Objects.Model_Battery;

import java.util.ArrayList;

public class Fragment_Edit_Model_Battery extends Fragment_Edit implements Interface_Request_Name {

    // Adapter
    Adapter_Edit_Model_Battery aModelBatteries;

    // Data
    ArrayList<Model_Battery> lModelBatteries;
    int kModel;
    int tMode;

    // Interface
    Interface_Model_New_New iModel;
    Interface_Edit_Model_Battery iEditModelBattery;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Interface
        iModel = (Interface_Model_New_New)getActivity();
        iEditModelBattery = (Interface_Edit_Model_Battery)getActivity();

        // Data
        tMode = bData.getInt(Constants_Intern.TYPE_MODE);
        lModelBatteries = iModel.getModel().getlModelBatteries();

        // Adapter
        aModelBatteries = new Adapter_Edit_Model_Battery(getActivity(), lModelBatteries, this, tMode);
        rvList.setAdapter(aModelBatteries);

        return vLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        lModelBatteries = iModel.getModel().getlModelBatteries();
        aModelBatteries.notifyDataSetChanged();
    }

    @Override
    public void onCommit() {
        iEditModelBattery.returnEditModelBattery(getTag());
    }

    public void addBattery(Battery oBattery) {
        lModelBatteries.add(new Model_Battery(getActivity(), iModel.getModel().getkModel(), oBattery, 0));
        iModel.getModel().update();
        aModelBatteries.notifyDataSetChanged();
    }

    public void deleteModelBattery(Model_Battery oModelBattery) {
        iModel.getModel().getlModelBatteries().remove(oModelBattery);
        iModel.getModel().update();
        aModelBatteries.notifyDataSetChanged();
    }

    public void showFragmentInputBattery() {
        iModel.addModelBattery();
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
        iModel.getModel().update();
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
        return iModel.getModel();
    }

    public interface Interface_Edit_Model_Battery {
        void returnEditModelBattery(String cTag);
    }
}
