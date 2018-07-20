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

import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Device extends Fragment implements View.OnClickListener {

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Layout = inflater.inflate(R.layout.fragment_model, container, false);
        setLayout();

        fManager = getActivity().getSupportFragmentManager();
        Fragment_Model fModel = new Fragment_Model();
        fManager.beginTransaction().replace(R.id.flModel, fModel, FRAGMENT_MODEL).commit();

        return Layout;
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
        tvLKU.setText(device.getLKU());
        tvDestination.setText(device.getDestination());
        tvStation.setText(device.getStation());
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

    }
}
