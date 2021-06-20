package com.example.ericschumacher.bouncer.Fragments.Edit;

import android.app.DownloadManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Adapter.Adapter_List_ModelChecks;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Model_New_New;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Check;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.Objects.ModelCheck;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Edit_Model_Checks extends Fragment_Edit implements Interface_Update, Adapter_List.Interface_Adapter_List {

    // Interface
    Interface_Model_New_New iModel;

    // Data
    ArrayList<ModelCheck> lModelChecks = new ArrayList<>();

    // Adapter
    Adapter_List_ModelChecks aList;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        // Interface
        iModel = (Interface_Model_New_New)getActivity();

        super.onCreateView(inflater, container, savedInstanceState);
        return vLayout;
    }

    @Override
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        aList = new Adapter_List_ModelChecks(getActivity(), this, lModelChecks);
        rvList.setAdapter(aList);
    }

    private void loadData() {
        // Data
        if (iModel != null && iModel.getModel() != null) {
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_CHECKS + iModel.getModel().getkModel(), null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    if (oJson != null) {
                        JSONArray aJson = oJson.getJSONArray("lModelChecks");
                        for (int i = 0; i < aJson.length(); i++) {
                            JSONObject jsonModelCheck = aJson.getJSONObject(i);
                            ModelCheck oModelCheck = new ModelCheck(jsonModelCheck, getActivity());
                            lModelChecks.add(oModelCheck);
                        }
                        aList.notifyDataSetChanged();
                    }
                }
            });
        }
    }

    @Override
    public void update() {
        loadData();
    }

    @Override
    public int getItemCount() {
        return lModelChecks.size()+1;
    }

    @Override
    public void clickList(int position) {

    }

    @Override
    public int getItemViewType(int position) {
        return position < lModelChecks.size() ? Constants_Intern.ITEM : Constants_Intern.ADD;
    }
}
