package com.example.ericschumacher.bouncer.Fragments.Fragment_Request;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.Adapter_Request_Choice;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Manufactures;
import com.example.ericschumacher.bouncer.Objects.Object_Manufacturer;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

import java.util.ArrayList;

/**
 * Created by Eric on 25.05.2018.
 */

public class Fragment_Request_Choice_Manufacturer extends Fragment {

    // Layout
    View Layout;
    RecyclerView RecyclerView;

    // Variables
    Utility_Network uNetwork;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Layout
        Layout = inflater.inflate(R.layout.fragment_request_choice, container, false);
        setLayout();
        Log.i("Fragment: ", "Maufactures");

        // Variables
        uNetwork = new Utility_Network(getActivity());

        // RecyclerView
        uNetwork.getManufactures(new Interface_VolleyCallback_ArrayList_Manufactures() {
            @Override
            public void onSuccess(ArrayList<Object_Manufacturer> list) {
                Log.i("Fragment: ", "Maufactures"+" "+list.size());
                Adapter_Request_Choice adapter = new Adapter_Request_Choice(getActivity(),list,(Interface_Selection)getActivity());
                RecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
                RecyclerView.setAdapter(adapter);
            }
        });


        return Layout;
    }

    // Layout
    private void setLayout() {
        RecyclerView = Layout.findViewById(R.id.recycler_view);
    }
}
