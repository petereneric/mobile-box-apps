package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Model;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

public class Fragment_Model extends Fragment implements View.OnClickListener {

    // Layout
    View Layout;
    TableRow trName;
    TableRow trManufacturer;
    TableRow trCharger;
    TableRow trBattery;
    TableRow trDefaultExploitation;

    TextView tvName;
    TextView tvManufacturer;
    TextView tvCharger;
    TextView tvBattery;
    TextView tvDefaultExploitation;

    // Interface
    Interface_Model iModel;

    // Ohters
    Utility_Network uNetwork;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Layout = inflater.inflate(R.layout.fragment_model, container, false);
        setLayout();

        uNetwork = new Utility_Network(getActivity());


        return Layout;
    }

    private void setLayout() {
        trName = Layout.findViewById(R.id.trName);
        trManufacturer = Layout.findViewById(R.id.trManufacturer);
        trCharger = Layout.findViewById(R.id.trCharger);
        trBattery = Layout.findViewById(R.id.trBattery);
        trDefaultExploitation = Layout.findViewById(R.id.trDefaultExploitation);

        tvName = Layout.findViewById(R.id.tvName);
        tvManufacturer = Layout.findViewById(R.id.tvManufacturer);
        tvCharger = Layout.findViewById(R.id.tvCharger);
        tvBattery = Layout.findViewById(R.id.tvBattery);
        tvDefaultExploitation = Layout.findViewById(R.id.tvDefaultExploitation);

        trName.setOnClickListener(this);
        trManufacturer.setOnClickListener(this);
        trCharger.setOnClickListener(this);
        trBattery.setOnClickListener(this);
        trDefaultExploitation.setOnClickListener(this);
    }

    public void updateUI(Model model) {
        tvName.setText(model.getName());
        if (model.getManufacturer() != null) {
            tvManufacturer.setText(model.getManufacturer().getName());
        }
        if (model.getCharger() != null) {
            tvCharger.setText(model.getCharger().getName());
        }
        if (model.getBattery() != null) {
            tvBattery.setText(model.getBattery().getName());
        }
        tvDefaultExploitation.setText(model.getExploitation());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trName:
                iModel.requestName();
                break;
            case R.id.trManufacturer:

                break;
            case R.id.trCharger:

                break;
            case R.id.trBattery:

                break;
            case R.id.trDefaultExploitation:

                break;
        }
    }
}
