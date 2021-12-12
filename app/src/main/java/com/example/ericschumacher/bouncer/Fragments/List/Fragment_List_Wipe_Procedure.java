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

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List_Wipe_Procedure;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click_List;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Wipeprocedure;
import com.example.ericschumacher.bouncer.Objects.Wipe;
import com.example.ericschumacher.bouncer.Objects.Wipe_Procedure;
import com.example.ericschumacher.bouncer.R;

public class Fragment_List_Wipe_Procedure extends Fragment implements Interface_Click_List {

    // VALUES & VARIABLES

    // Layout
    View vLayout;
    TextView tvTitle;
    RecyclerView rvData;

    // Interfaces
    Interface_Wipeprocedure iWipeprocedure;

    // Adapter
    Adapter_List_Wipe_Procedure mAdapter;

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LIFE-CYCLE


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Interfaces
        iWipeprocedure = (Interface_Wipeprocedure)getActivity();
        // Layout
        setLayout(inflater, container);

        return vLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PRIVATE

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(R.layout.fragment_list_new, container, false);

        // Initiate
        tvTitle = vLayout.findViewById(R.id.tvTitle);
        rvData = vLayout.findViewById(R.id.rvData);

        // Data
        tvTitle.setText(getString(R.string.wipe_procedures));

        // Adapter
        mAdapter = new Adapter_List_Wipe_Procedure(getActivity(), iWipeprocedure.getWipeprocedure().getlWipeProcedure(), this);

        // RecyclerView
        rvData.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvData.setAdapter(mAdapter);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PUBLIC

    public void update() {
        mAdapter.update(iWipeprocedure.getWipeprocedure().getlWipeProcedure());
    }

    @Override
    public void onClickList(int position, int type) {
        // Item
        if (type == Constants_Intern.ITEM) {

        }

        // Add
        if (type == Constants_Intern.ADD) {

        }
    }
}
