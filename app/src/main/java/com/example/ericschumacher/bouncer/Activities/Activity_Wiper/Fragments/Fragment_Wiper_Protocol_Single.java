package com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Fragments;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Adapter.List.Adapter_List_Protocol_Single;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List_Diagnose;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog.Fragment_Dialog_Checker_CodeLock;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog.Fragment_Dialog_Checker_Software;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Adapter_List;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Checker;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Wiper;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Wiper_Single;
import com.example.ericschumacher.bouncer.Interfaces.Interface_JWT;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Manager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Diagnose;
import com.example.ericschumacher.bouncer.Objects.DiagnoseCheck;
import com.example.ericschumacher.bouncer.Objects.Object_Model_Damage;
import com.example.ericschumacher.bouncer.Objects.Protocol;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_DateTime;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Wiper_Protocol_Single extends Fragment implements Adapter_List.Interface_Adapter_List, Interface_Update, View.OnClickListener {

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
    Fragment_Device.Interface_Device iDevice;
    Interface_Fragment_Wiper iWiper;
    Interface_Fragment_Wiper_Single iWiperSingle;
    Interface_JWT iJWT;

    // Adapter
    Adapter_List_Protocol_Single aProtocol;

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LIFE-CYCLE

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Interface
        iDevice = (Fragment_Device.Interface_Device)getActivity();
        iWiper = (Interface_Fragment_Wiper)getParentFragment();
        iWiperSingle = (Interface_Fragment_Wiper_Single)getParentFragment();
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
        setAdapter();

        // Update
        updateLayout();
    }

    public void setAdapter() {
        aProtocol = new Adapter_List_Protocol_Single(getActivity(), this, iWiper != null && iWiper.getModelWipes() != null ? iWiper.getModelWipes() : new ArrayList<>(), iWiperSingle != null && iWiperSingle.getProtocol() != null ? iWiperSingle.getProtocol().getlProtocolWipes() : new ArrayList<>());
        rvList.setAdapter(aProtocol);
    }

    public void updateLayout() {
        if (iWiper != null && iWiperSingle != null) {
            aProtocol.updateData(iWiper.getModelWipes(), iWiperSingle.getProtocol() != null ? iWiperSingle.getProtocol().getlProtocolWipes() : new ArrayList<>());
            if (iWiperSingle.getProtocol() != null) {
                tvSubtitle.setVisibility(View.VISIBLE);
                tvSubtitle.setText(Utility_DateTime.dateToString(iWiperSingle.getProtocol().getdCreation()) + " | " + iWiperSingle.getProtocol().getcUser());
            } else {
                tvSubtitle.setVisibility(View.GONE);
                ivHeaderRight.setVisibility(View.GONE);
            }
        }
    }



    @Override
    public int getItemCount() {
        return iWiper.getModelWipes().size() + 2;
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
    public void clickList(int position) {
        if (getItemViewType(position) == Constants_Intern.FINISH) {
            if (iWiperSingle.getProtocol() != null) {
                iWiperSingle.showHandler();
            } else {
                newProtocol(true);
            }
        }
        if (getItemViewType(position) == Constants_Intern.ERROR) {
            newProtocol(false);
        }
    }

    private void newProtocol (boolean bPassed) {
        Protocol.create(new Volley_Connection(getActivity()), iDevice.getDevice().getIdDevice(), iJWT.getJWT().getkUser(), bPassed, new Protocol.Interface_Create() {
            @Override
            public void created(Protocol protocol) {
                iWiperSingle.setProtocol(protocol);
                if (bPassed) {
                    iWiperSingle.showHandler();
                } else {
                    iWiperSingle.showDefects();
                }

            }
        });
    }

    @Override
    public boolean longClickList(int position) {
        return false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivHeaderLeft:
                break;
            case R.id.ivHeaderRight:
                break;
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PUBLIC

    @Override
    public void update() {
    }
}
