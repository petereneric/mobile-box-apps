package com.example.ericschumacher.bouncer.Fragments.Fragment_Turing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Interfaces.Interface_List;
import com.example.ericschumacher.bouncer.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Fragment_List extends Fragment implements Interface_Click {

    // Layout
    View Layout;
    RecyclerView RecyclerView;

    // Data
    JSONObject oJson;

    // Adapter
    Adapter_List aList;

    // Interface
    Interface_List iList;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Layout
        setLayout(inflater, container);

        // Data
        try {
            oJson = new JSONObject(getArguments().getString(Constants_Intern.STRING_JSON));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Adapter
        try {
            aList = new Adapter_List(getActivity(), this, oJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RecyclerView.setAdapter(aList);

        // Interface
        iList = (Interface_List)getActivity();

        return Layout;
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        Layout = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView = Layout.findViewById(R.id.RecyclerView);

        RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    public void onClick(int position) {
        iList.onClick(position, getTag());
    }
}
