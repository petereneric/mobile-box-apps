package com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Adapter.List.Adapter_List_Protocol_Multi;
import com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Adapter.List.Adapter_List_Protocol_Single;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Activity_Wiper;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Wiper;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Wiper_Multi;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Wiper_Single;
import com.example.ericschumacher.bouncer.Interfaces.Interface_JWT;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_DateTime;

import java.util.ArrayList;

public class Fragment_Wiper_Protocol_Multi extends Fragment implements Interface_Update, View.OnClickListener, Adapter_List.Interface_Adapter_List {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // VALUES & VARIABLES

    // Layout
    TextView tvTitle;
    TextView tvSubtitle;
    RecyclerView rvList;
    View vLayout;
    ImageView ivHeaderLeft;
    ImageView ivHeaderRight;

    // Interface
    Interface_Fragment_Wiper iWiper;
    Interface_Fragment_Wiper_Multi iWiperMulti;
    Interface_JWT iJWT;
    Interface_Activity_Wiper iWiperMain;

    // Adapter
    Adapter_List_Protocol_Multi aProtocol;

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LIFE-CYCLE

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Interface
        iWiper = (Interface_Fragment_Wiper)getParentFragment();
        iWiperMulti = (Interface_Fragment_Wiper_Multi) getParentFragment();
        iWiperMain = (Interface_Activity_Wiper)getActivity();
        iJWT = (Interface_JWT)getActivity();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return vLayout;
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PRIVATE

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        // Layout
        tvTitle = vLayout.findViewById(R.id.tvTitle);
        tvTitle.setText(getActivity().getString(R.string.protocol));
        tvSubtitle = vLayout.findViewById(R.id.tvSubtitle);
        rvList = vLayout.findViewById(R.id.rvList);
        ivHeaderLeft = vLayout.findViewById(R.id.ivHeaderLeft);
        ivHeaderRight = vLayout.findViewById(R.id.ivHeaderRight);

        // ClickListener
        ivHeaderLeft.setOnClickListener(this);
        ivHeaderRight.setOnClickListener(this);

        // Header
        tvSubtitle.setVisibility(View.VISIBLE);

        // RecyclerView
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Data
        aProtocol = new Adapter_List_Protocol_Multi(getActivity(), this, iWiperMain != null ? iWiperMain.getSelectedModel().getlModelWipes() : new ArrayList<>());
        rvList.setAdapter(aProtocol);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public int getItemCount() {
        return iWiper.getModelWipes().size()+2;
    }

    @Override
    public void clickList(int position) {
        if (getItemViewType(position) == Constants_Intern.FINISH) {
            iWiperMulti.finishAll();
        }
        if (getItemViewType(position) == Constants_Intern.ERROR) {
            // toast
        }
    }

    @Override
    public boolean longClickList(int position) {
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < iWiper.getModelWipes().size()) {
            return Constants_Intern.ITEM;
        }
        if (position == iWiper.getModelWipes().size()) {
            return Constants_Intern.FINISH;
        }
        if (position == iWiper.getModelWipes().size()+1) {
            return Constants_Intern.ERROR;
        }
        return 0;
    }

    @Override
    public void update() {
        aProtocol.updateData(iWiperMain != null ? iWiperMain.getSelectedModel().getlModelWipes() : new ArrayList<>());
    }
}
