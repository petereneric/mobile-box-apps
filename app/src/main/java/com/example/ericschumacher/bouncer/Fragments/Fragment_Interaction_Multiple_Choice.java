package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_DeviceManager;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import java.io.Serializable;
import java.util.ArrayList;

public class Fragment_Interaction_Multiple_Choice extends Fragment implements View.OnClickListener {

    // Data
    Bundle dBundle;
    String cInteractionTitle;
    ArrayList<Serializable> lChoice;

    // Layout
    View Layout;
    TextView tvInteractionTitle;
    Button bCommit;
    RecyclerView rvMultipleChoice;

    // Interface
    public Interface_DeviceManager iDeviceManager;

    // Else
    Volley_Connection cVolley;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Connection
        cVolley = new Volley_Connection(getActivity());

        // Interface
        iDeviceManager = (Interface_DeviceManager)getActivity();

        // Data
        getData();

        // Layout
        Layout = inflater.inflate(R.layout.fragment_interaction_multiple_choice, container, false);
        setLayout();
        return Layout;
    }



    public void setLayout() {

        // Layout
        tvInteractionTitle = Layout.findViewById(R.id.tvTitle);
        bCommit = Layout.findViewById(R.id.bCommit);
        rvMultipleChoice = Layout.findViewById(R.id.rvMultipleChoice);

        // Interface
        setInterface();

        // Data
        rvMultipleChoice.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        setAdapter();

        tvInteractionTitle.setText(cInteractionTitle);

        // ClickListener
        bCommit.setOnClickListener(this);
    }

    public void setAdapter() {
        //Adapter_Request_Choice adapter = new Adapter_Request_Choice(getActivity(), lAdditive, this);
        //rvMultipleChoice.setAdapter(adapter);
    }

    public void getData() {
        dBundle = getArguments();
        cInteractionTitle = dBundle.getString(Constants_Intern.INTERACTION_TITLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bCommit:
                onCommit();
                break;
        }
    }

    public void setInterface() {

    }

    public void onCommit() {

    }
}
