package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Color;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Manager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Model;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Additive;
import com.example.ericschumacher.bouncer.Objects.Additive.Additive;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

import java.util.ArrayList;

public class Fragment_Device extends Fragment implements View.OnClickListener, Interface_Device {

    // Layout
    View Layout;
    TableRow trLKU;
    TableRow trDestination;
    TableRow trStation;
    TableRow trColor;
    TableRow trShape;

    TextView tvLKU;
    TextView tvDestination;
    TextView tvStation;
    TextView tvColor;
    TextView tvShape;

    // Fragment
    FragmentManager fManager;
    private final static String FRAGMENT_MODEL = "FRAGMENT_MODEL";

    // Interface
    Interface_Model iModel;
    Interface_Manager iManager;

    Utility_Network uNetwork;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Layout = inflater.inflate(R.layout.fragment_device, container, false);
        setLayout();

        fManager = getActivity().getSupportFragmentManager();
        Fragment_Model fModel = new Fragment_Model();
        fManager.beginTransaction().replace(R.id.flModel, fModel, FRAGMENT_MODEL).commit();
        iManager = (Interface_Manager)getActivity();
        uNetwork = new Utility_Network(getActivity());

        return Layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        iModel = (Interface_Model)fManager.findFragmentByTag(FRAGMENT_MODEL);
    }

    private void setLayout() {
        trLKU = Layout.findViewById(R.id.trLKU);
        trDestination = Layout.findViewById(R.id.trDestination);
        trStation = Layout.findViewById(R.id.trStation);
        trColor = Layout.findViewById(R.id.trColor);
        trShape = Layout.findViewById(R.id.trShape);

        tvLKU = Layout.findViewById(R.id.tvLKU);
        tvDestination = Layout.findViewById(R.id.tvDestination);
        tvStation = Layout.findViewById(R.id.tvStation);
        tvColor = Layout.findViewById(R.id.tvColor);
        tvShape = Layout.findViewById(R.id.tvShape);

        trLKU.setOnClickListener(this);
        trDestination.setOnClickListener(this);
        trStation.setOnClickListener(this);
        trColor.setOnClickListener(this);
        trShape.setOnClickListener(this);
    }

    public void updateUI(Device device) {

        if (device.getLKU() > Constants_Intern.STATION_UNKNOWN_INT) {
            tvLKU.setText(Integer.toString(device.getLKU()));
        } else {
            Log.i("LKU ID", Integer.toString(device.getLKU()));
            tvLKU.setText(Constants_Intern.UNKOWN);
        }
        if (device.getVariationColor() != null) {
            tvColor.setText(device.getVariationColor().getName());
        } else {
            tvColor.setText(Constants_Intern.UNKOWN);
        }
        if (device.getVariationShape() != null) {
            tvShape.setText(device.getVariationShape().getName());
        } else {
            tvShape.setText(Constants_Intern.UNKOWN);
        }
        if (device.getDestination() > Constants_Intern.INT_UNKNOWN) {
            tvDestination.setText(Integer.toString(device.getDestination()));
        } else {
            tvDestination.setText(Constants_Intern.UNKOWN);
        }
        if (device.getStation().getId() > Constants_Intern.INT_UNKNOWN) {
            tvStation.setText(Integer.toString(device.getStation().getId()));
        } else {
            tvStation.setText(Constants_Intern.UNKOWN);
        }
        ((Fragment_Model)fManager.findFragmentByTag(FRAGMENT_MODEL)).updateUI(device);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trLKU:
                iManager.onClickLKU();
                //requestLKU();
                break;
            case R.id.trDestination:
                requestDestination();
                break;
            case R.id.trStation:
                requestStation();
                break;
            case R.id.trColor:
                requestColor(iManager.getDevice());
                break;
            case R.id.trShape:
                requestShape();
                break;
        }
    }

    @Override
    public void requestLKU() {
        //Toast.makeText(getActivity(), "Noch checken", Toast.LENGTH_LONG).show();
        /*
        if (iManager.getDevice().getIdModel() > 0) {
            uNetwork.connectWithLku(iManager.getDevice(), new Interface_VolleyCallback_Int() {
                @Override
                public void onSuccess(int i) {
                    iManager.getDevice().setLKU(i);
                    iManager.updateUI();
                }

                @Override
                public void onFailure() {
                    Toast.makeText(getActivity(), getString(R.string.toast_model_has_lku), Toast.LENGTH_LONG).show();
                }
            });
        }
        */
    }

    @Override
    public void requestDestination() {

    }

    @Override
    public void requestStation() {

    }

    @Override
    public void requestShape() {
        Fragment_Request_Shape f = new Fragment_Request_Shape();
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, f, Constants_Intern.FRAGMENT_REQUEST_SHAPE).commit();
    }

    @Override
    public void requestCondition() {
        Fragment_Request_Condition f = new Fragment_Request_Condition();
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, f, Constants_Intern.FRAGMENT_REQUEST_CONDITION).commit();
    }

    @Override
    public void requestColor(Device device) {
        uNetwork.getColors(device, new Interface_VolleyCallback_ArrayList_Additive() {
            @Override
            public void onSuccess(ArrayList<Additive> list) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constants_Intern.LIST_ADDITIVE, list);
                Fragment_Request_Color f = new Fragment_Request_Color();
                f.setArguments(bundle);
                fManager.beginTransaction().replace(R.id.flFragmentInteraction, f, Constants_Intern.FRAGMENT_REQUEST_COLOR).commit();
            }
        });
    }

    @Override
    public void requestName() {
        iModel.requestName();
    }

    @Override
    public void requestManufacturer() {
        iModel.requestManufacturer();
    }

    @Override
    public void requestCharger(Device device) {
        iModel.requestCharger(device);
    }

    @Override
    public void requestBattery(Device device) {
        iModel.requestBattery(device);
    }

    @Override
    public void requestDefaultExploitation(Model model) {
        iModel.requestDefaultExploitation(model);
    }
}
