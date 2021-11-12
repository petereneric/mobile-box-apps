package com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

public class Fragment_Dialog_Info_Checker extends Fragment_Dialog {

    // Connection
    Volley_Connection cVolley;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        vLayout = super.onCreateView(inflater, container, savedInstanceState);

        // Connection
        cVolley = new Volley_Connection(getActivity());

        // Load
        loadData();

        return vLayout;
    }

    @Override
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        tvHeader.setText(getString(R.string.current_state));
        tvContent.setTextSize(30);
        tvButtonOne.setText(getString(R.string.ok));
        rlButtonTwo.setVisibility(View.GONE);
    }

    private void loadData() {
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_STATISTIC_CHECKER, null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                tvContent.setText(oJson.getString("sDiagnoses"));
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlButtonOne:
                dismiss();
                break;
        }
    }
}
