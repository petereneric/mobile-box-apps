package com.example.ericschumacher.bouncer.Fragments.Checker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Adapter.Table.Adapter_Table;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Device;
import com.example.ericschumacher.bouncer.Fragments.Table.Fragment_Table;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Diagnose_Container;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Ann;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Urls;

import org.json.JSONException;
import org.json.JSONObject;

public class Fragment_Table_Diagnose_Menu extends Fragment_Table implements View.OnClickListener, Interface_Update {

    // Layout
    Button bNew;

    // Interface
    Fragment_Device.Interface_Device iDevice;
    Interface_Fragment_Diagnose_Container iDiagnoseContainer;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ann
        lAnn.add(new Ann(null, null, "dCreation", getString(R.string.diagnose_created_date), 1));
        lAnn.add(new Ann(null, null, "cUser", getString(R.string.user), 1));
        lAnn.add(new Ann(null, null, "cFinished", getString(R.string.state), 1));
        lAnn.add(new Ann(null, null, "cResult", getString(R.string.result), 1));
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return vLayout;
    }

    @Override
    public void setInterface() {
        // Interfaces
        iDevice = (Fragment_Device.Interface_Device) getActivity();
        iDiagnoseContainer = (Interface_Fragment_Diagnose_Container)getParentFragment();
        iFragmentTable = (Interface_Fragment_Table)getParentFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    // Layout
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        // Initiate
        bNew = vLayout.findViewById(R.id.bNew);

        // ClickListener
        bNew.setOnClickListener(this);

        // Data
        tvTitle.setText(getString(R.string.diagnose_menu));
    }

    public int getIdLayout() {
        return R.layout.fragment_table_diagnose_menu;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bNew:
                iDiagnoseContainer.newDiagnose();
                break;
        }
    }

    @Override
    public void update() {
        load();
    }

    private void load() {
        if (iDevice != null && iDevice.getDevice() != null) {
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_DIAGNOSES + iDevice.getDevice().getIdDevice(), null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    if (oJson != null) {
                        lData = oJson.getJSONArray("lDiagnoses");
                        if (lData.length() > 0) {
                        } else {
                            // handle info to activity and display new diagnose fragment
                        }
                        aTable.notifyDataSetChanged();

                    }
                }
            });
        }
    }

    @Override
    public boolean hasHeader() {
        return true;
    }
}
