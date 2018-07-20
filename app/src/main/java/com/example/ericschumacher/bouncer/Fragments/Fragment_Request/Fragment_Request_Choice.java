package com.example.ericschumacher.bouncer.Fragments.Fragment_Request;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.Adapter_Request_Choice;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
import com.example.ericschumacher.bouncer.Objects.Additive.Additive;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

import java.util.ArrayList;

/**
 * Created by Eric on 25.05.2018.
 */

public class Fragment_Request_Choice extends Fragment {

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

        Bundle arg = getArguments();
        ArrayList<Additive> list = new ArrayList<>();
        list = arg.getParcelableArrayList(Constants_Intern.LIST_ADDITIVE);
        Adapter_Request_Choice adapter = new Adapter_Request_Choice(getActivity(), list, (Interface_Selection) getActivity());
        RecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        RecyclerView.setAdapter(adapter);

        return Layout;
    }

    // Layout
    private void setLayout() {
        RecyclerView = Layout.findViewById(R.id.recycler_view);
    }
}
