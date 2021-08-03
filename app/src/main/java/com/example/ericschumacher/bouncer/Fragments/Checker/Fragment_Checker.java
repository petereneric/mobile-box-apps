package com.example.ericschumacher.bouncer.Fragments.Checker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Adapter.Pager.Adapter_Pager;
import com.example.ericschumacher.bouncer.Adapter.Pager.Adapter_Pager_Checker;
import com.example.ericschumacher.bouncer.Fragments.Edit.Fragment_Edit_Model_Checks;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Device;
import com.example.ericschumacher.bouncer.Fragments.Result.Fragment_Result_Checker;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Checker;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Diagnose;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Views.ViewPager_Eric;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class Fragment_Checker extends Fragment implements Interface_Fragment_Checker {

    // Layout
    View vLayout;
    ViewPager_Eric ViewPager;
    TabLayout vTabLayout;

    // Adapter
    Adapter_Pager_Checker aChecker;

    // Interfaces
    Fragment_Device.Interface_Device iDevice;

    // Connection
    Volley_Connection cVolley;

    // Data
    ArrayList<Diagnose> lDiagnoses;
    Diagnose oDiagnose;

    // Fragments
    Fragment_Diagnose_Container fDiagnoseContainer;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Interfaces
        iDevice = (Fragment_Device.Interface_Device)getActivity();

        // Connection
        cVolley = new Volley_Connection(getActivity());

        // Data
        loadDiagnoses();
    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        // Layout
        setLayout(inflater, container);

        return vLayout;
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(R.layout.fragment_checker, container, false);
        ViewPager = vLayout.findViewById(R.id.ViewPager);
        vTabLayout = vLayout.findViewById(R.id.vTabLayout);

        // Adapter
        aChecker = new Adapter_Pager_Checker(getActivity().getSupportFragmentManager());

        // Tabs
        vTabLayout.setupWithViewPager(ViewPager);
        vTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                ViewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        fDiagnoseContainer = new Fragment_Diagnose_Container();
        fDiagnoseContainer.setTargetFragment(Fragment_Checker.this, 0);
        aChecker.add(getString(R.string.diagnose), fDiagnoseContainer);
        aChecker.add(getString(R.string.edit_checks), new Fragment_Edit_Model_Checks());
        Fragment_Result_Checker fResult = new Fragment_Result_Checker();
        fResult.setTargetFragment(Fragment_Checker.this, 2);
        aChecker.add(getString(R.string.handling), fResult);
        ViewPager.setAdapter(aChecker);
    }

    // Data

    private void loadDiagnoses() {
        lDiagnoses = new ArrayList<>();
        if (iDevice != null && iDevice.getDevice() != null) {
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_DIAGNOSES + iDevice.getDevice().getIdDevice(), null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    if (oJson != null) {
                        Log.i("Strange:", ""+iDevice.getDevice().getIdDevice());
                        JSONArray jsonArray = oJson.getJSONArray("lDiagnoses");
                        for (int i = 0; i <jsonArray.length(); i++) {
                            JSONObject jsonDiagnose = jsonArray.getJSONObject(i);
                            Diagnose oDiagnose = new Diagnose(getActivity(), jsonDiagnose);
                            lDiagnoses.add(oDiagnose);
                        }
                        sortDiagnoses();
                    }
                    if (aChecker != null) {
                        aChecker.update();
                    }
                }
            });
        }
    }

    public void update() {
        loadDiagnoses();
    }

    public void reset() {

    }

    @Override
    public ArrayList<Diagnose> getDiagnoses() {
        return lDiagnoses;
    }

    @Override
    public void addDiagnose() {
        JSONObject oJson = new JSONObject();
        try {
            oJson.put("kDevice", iDevice.getDevice().getIdDevice());
            oJson.put("kUser", 1);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cVolley.getResponse(Request.Method.PUT, Urls.URL_CREATE_DIAGNOSE, oJson, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (oJson != null) {
                    Diagnose oDiagnose = new Diagnose(getActivity(), oJson);
                    lDiagnoses.add(oDiagnose);
                    Fragment_Checker.this.oDiagnose = oDiagnose;
                    fDiagnoseContainer.showDiagnose();
                }
            }
        });
    }

    @Override
    public void editDiagnose(int position) {
        Diagnose diagnose = lDiagnoses.get(position);
        oDiagnose = diagnose;
        fDiagnoseContainer.showDiagnose();
    }

    @Override
    public Diagnose getDiagnose() {
        return oDiagnose;
    }

    @Override
    public void deleteDiagnose() {
        cVolley.execute(Request.Method.DELETE, Urls.URL_DELETE_DIAGNOSE+oDiagnose.getId(), null);
        lDiagnoses.remove(oDiagnose);
        oDiagnose = null;
        fDiagnoseContainer.showMenu();
    }

    @Override
    public void pauseDiagnose() {
        fDiagnoseContainer.showMenu();
    }

    @Override
    public void showHandler() {
        ViewPager.setCurrentItem(2);
    }

    @Override
    public void showTab(int position) {
        ViewPager.setCurrentItem(position);
    }

    public void sortDiagnoses() {
        Collections.sort(lDiagnoses);
    }
}
