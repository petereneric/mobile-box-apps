package com.example.ericschumacher.bouncer.Fragments.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Activities.Tools.Activity_Wiper_Procedure;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List_Wipeprocedure;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Wipeprocedure;
import com.example.ericschumacher.bouncer.Objects.Wipeprocedure;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

public class Fragment_List_Wipeprocedure extends Fragment {

    // VALUES & VARIABLES

    // Layout
    View vLayout;
    TextView tvTitle;
    RecyclerView rvData;

    // Data
    ArrayList<Wipeprocedure> lWipeprocedure = new ArrayList<>();

    // Interface
    Interface_Wipeprocedure iWipeprocedure;

    // Adapter
    Adapter_List_Wipeprocedure mAdapter;

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LIFE-CYCLE


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Interface
        iWipeprocedure = (Interface_Wipeprocedure)getActivity();

        // Layout
        setLayout(inflater, container);

        // Data
        update(iWipeprocedure.getlWipeprocedures());

        return vLayout;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PRIVATE

    // Layout
    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(R.layout.fragment_list_new, container, false);

        tvTitle = vLayout.findViewById(R.id.tvTitle);
        rvData = vLayout.findViewById(R.id.rvData);

        mAdapter = new Adapter_List_Wipeprocedure(getActivity(), lWipeprocedure, new Interface_Click() {
            @Override
            public void onClick(int position) {
                ((Activity_Wiper_Procedure)getActivity()).setWipeprocedure(lWipeprocedure.get(position));
            }
        });
        rvData.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvData.setAdapter(mAdapter);
    }

    // Update
    private void updateLayout() {
        if (lWipeprocedure.size() > 0) {
            tvTitle.setText(getString(R.string.wipeprocedures));
        } else {
            tvTitle.setText(getString(R.string.no_matching_results));
        }
    }

    public void update(ArrayList<Wipeprocedure> lWipeprocedure) {
        this.lWipeprocedure = lWipeprocedure;
        mAdapter.update(lWipeprocedure);
        updateLayout();
    }
}
