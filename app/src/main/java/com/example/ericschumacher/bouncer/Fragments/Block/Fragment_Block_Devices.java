package com.example.ericschumacher.bouncer.Fragments.Block;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Devices;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Block_Devices extends Fragment {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // VALUES & VARIABLES

    // Layout
    private View vLayout;
    private TextView tvTitle;
    private RecyclerView rvData;

    // Adapter
    Adapter_Block_Devices mAdapter;

    // Interfaces
    private Interface_Devices iDevices;


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LIFE-CYCLE

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        // Interfaces
        iDevices = (Interface_Devices)getActivity();

        // Layout
        setLayout(inflater, container);


        return vLayout;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PRIVATE

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(R.layout.fragment_block_devices, container, false);

        // Initiate
        tvTitle = (TextView)vLayout.findViewById(R.id.tvTitle);
        rvData = (RecyclerView)vLayout.findViewById(R.id.rvData);

        tvTitle.setText(getString(R.string.devices));

        // RecyclerView
        rvData.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        mAdapter = new Adapter_Block_Devices(getActivity(), iDevices);
        rvData.setAdapter(mAdapter);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PUBLIC

    public void update() {
        mAdapter.notifyDataSetChanged();
    }
}
