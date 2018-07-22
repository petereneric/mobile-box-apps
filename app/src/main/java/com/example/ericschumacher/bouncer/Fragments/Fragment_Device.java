package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ericschumacher.bouncer.Activities.Activity_Bouncer;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Choice;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Manager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Model;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Additive;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Int;
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
        tvLKU.setText(Integer.toString(device.getLKU()));
        tvDestination.setText(Integer.toString(device.getDestination()));
        tvStation.setText(Integer.toString(device.getStation()));
        if (device.getVariationColor() != null) {
            tvColor.setText(device.getVariationColor().getName());
        }
        if (device.getVariationShape()!= null) {
            tvShape.setText(device.getVariationShape().getName());
        }
        ((Fragment_Model)fManager.findFragmentByTag(FRAGMENT_MODEL)).updateUI(device);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trLKU:
                requestLKU();
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
        if (iManager.getDevice().getId() > 0) {
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
        fManager.beginTransaction().replace(R.id.flFragmentRequest, f, Constants_Intern.FRAGMENT_REQUEST).commit();
    }

    @Override
    public void requestColor(Device device) {
        uNetwork.getColors(device, new Interface_VolleyCallback_ArrayList_Additive() {
            @Override
            public void onSuccess(ArrayList<Additive> list) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constants_Intern.LIST_ADDITIVE, list);
                Fragment_Request_Choice f = new Fragment_Request_Choice();
                f.setArguments(bundle);
                fManager.beginTransaction().replace(R.id.flFragmentRequest, f, Constants_Intern.FRAGMENT_REQUEST).commit();
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
