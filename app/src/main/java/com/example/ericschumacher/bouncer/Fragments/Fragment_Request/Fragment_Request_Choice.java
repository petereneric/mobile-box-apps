package com.example.ericschumacher.bouncer.Fragments.Fragment_Request;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Request_Choice;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Manager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Request_Choice;
import com.example.ericschumacher.bouncer.Objects.Additive.Additive;
import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Additive.Variation_Color;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

import java.util.ArrayList;

/**
 * Created by Eric on 25.05.2018.
 */

public class Fragment_Request_Choice extends Fragment implements Interface_Request_Choice {

    // Layout
    View Layout;
    RecyclerView RecyclerView;

    // Variables
    Utility_Network uNetwork;

    // Interface
    Interface_Manager iManager;

    // Data
    ArrayList<Additive> lAdditive;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Layout
        Layout = inflater.inflate(R.layout.fragment_request_choice, container, false);
        setLayout();

        // Interface
        iManager = (Interface_Manager) getActivity();

        Bundle arg = getArguments();
        lAdditive = new ArrayList<>();
        lAdditive = arg.getParcelableArrayList(Constants_Intern.LIST_ADDITIVE);
        Adapter_Request_Choice adapter = new Adapter_Request_Choice(getActivity(), lAdditive, this);
        RecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        RecyclerView.setAdapter(adapter);

        return Layout;
    }

    // Layout
    private void setLayout() {
        RecyclerView = Layout.findViewById(R.id.recycler_view);
    }

    @Override
    public void onChoice(int position) {
        Additive additive = lAdditive.get(position);
        if (additive instanceof Manufacturer) iManager.returnManufacturer((Manufacturer) lAdditive.get(position));
        if (additive instanceof Charger) iManager.returnCharger((Charger) lAdditive.get(position));
        if (additive instanceof Variation_Color)  iManager.returnColor((Variation_Color) lAdditive.get(position));
    }
}
