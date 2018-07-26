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

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Choice;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Name;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Battery;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Manager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Model;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Additive;
import com.example.ericschumacher.bouncer.Objects.Additive.Additive;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

import java.util.ArrayList;

public class Fragment_Model extends Fragment implements View.OnClickListener, Interface_Model {

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
    Interface_Manager iManager;


    // Ohters
    Utility_Network uNetwork;
    FragmentManager fManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Layout = inflater.inflate(R.layout.fragment_model, container, false);
        setLayout();

        fManager = getActivity().getSupportFragmentManager();
        uNetwork = new Utility_Network(getActivity());

        iManager = (Interface_Manager)getActivity();


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
        if (model.getName() != null){
            tvName.setText(model.getName());
        } else {
            tvName.setText(Constants_Intern.UNKOWN);
        }
        if (model.getManufacturer() != null) {
            tvManufacturer.setText(model.getManufacturer().getName());
        }else {
            tvManufacturer.setText(Constants_Intern.UNKOWN);
        }
        if (model.getCharger() != null) {
            tvCharger.setText(model.getCharger().getName());
        }else {
            tvCharger.setText(Constants_Intern.UNKOWN);
        }
        if (model.getBattery() != null) {
            tvBattery.setText(model.getBattery().getName());
        }else {
            tvBattery.setText(Constants_Intern.UNKOWN);
        }
        tvDefaultExploitation.setText(model.getExploitationForScreen(getActivity()));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trName:
                requestName();
                break;
            case R.id.trManufacturer:
                requestManufacturer();
                break;
            case R.id.trCharger:
                requestCharger(iManager.getModel());
                break;
            case R.id.trBattery:
                requestBattery(iManager.getModel());
                break;
            case R.id.trDefaultExploitation:
                requestDefaultExploitation(iManager.getModel());
                break;
        }
    }

    @Override
    public void requestName() {
        Fragment_Request_Name f = new Fragment_Request_Name();
        fManager.beginTransaction().replace(R.id.flFragmentRequest, f, Constants_Intern.FRAGMENT_REQUEST).commit();
    }

    public void requestManufacturer() {
        uNetwork.getManufactures(new Interface_VolleyCallback_ArrayList_Additive() {
            @Override
            public void onSuccess(ArrayList<Additive> list) {
                Bundle b = new Bundle();
                b.putParcelableArrayList(Constants_Intern.LIST_ADDITIVE, list);
                startFragmentChoice(b);
            }
        });
    }

    @Override
    public void requestCharger(Model model) {
        uNetwork.getChargers(model, new Interface_VolleyCallback_ArrayList_Additive() {
            @Override
            public void onSuccess(ArrayList<Additive> list) {
                Bundle b = new Bundle();
                b.putParcelableArrayList(Constants_Intern.LIST_ADDITIVE, list);
                startFragmentChoice(b);
            }
        });
    }

    @Override
    public void requestBattery(Model model) {
        Fragment_Request_Battery fragment = new Fragment_Request_Battery();
        Bundle b = new Bundle();
        b.putInt(Constants_Intern.SELECTION_ID_MODEL, model.getIdModel());
        fragment.setArguments(b);
        fManager.beginTransaction().replace(R.id.flFragmentRequest, fragment, Constants_Intern.FRAGMENT_REQUEST).commit();
    }

    @Override
    public void requestDefaultExploitation(Model model) {
        Fragment_Request_Exploitation f = new Fragment_Request_Exploitation();
        Bundle b = new Bundle();
        b.putInt(Constants_Intern.SELECTION_ID_MODEL, model.getIdModel());
        f.setArguments(b);
        fManager.beginTransaction().replace(R.id.flFragmentRequest, f, Constants_Intern.FRAGMENT_REQUEST).commit();
    }

    private void startFragmentChoice(Bundle bundle) {
        Fragment_Request_Choice f = new Fragment_Request_Choice();
        f.setArguments(bundle);
        fManager.beginTransaction().replace(R.id.flFragmentRequest, f, Constants_Intern.FRAGMENT_REQUEST).commit();
    }
}
