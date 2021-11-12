package com.example.ericschumacher.bouncer.Fragments.Checker;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Activities.Tools.Activity_Checker;
import com.example.ericschumacher.bouncer.Adapter.Pager.Adapter_Pager;
import com.example.ericschumacher.bouncer.Adapter.Pager.Adapter_Pager_Checker;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Edit.Fragment_Edit_Model_Checks;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Device;
import com.example.ericschumacher.bouncer.Fragments.Result.Fragment_Result_Checker;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Checker;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Manager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Diagnose;
import com.example.ericschumacher.bouncer.Objects.ModelCheck;
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
    boolean bCreated = false;

    // Adapter
    Adapter_Pager_Checker aChecker;

    // Interfaces
    Fragment_Device.Interface_Device iDevice;
    Interface_Manager iManager;

    // Connection
    Volley_Connection cVolley;

    // Data
    ArrayList<ModelCheck> lModelChecks = new ArrayList<>();
    ArrayList<Diagnose> lDiagnoses = new ArrayList<>();
    Diagnose oDiagnose;

    // Fragments
    Fragment_Diagnose_Container fDiagnoseContainer;
    Fragment_Edit_Model_Checks fModelChecks;

    // Log
    private final String lTitle = "FRAGMENT_CHECKER";

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        // Interfaces
        iDevice = (Fragment_Device.Interface_Device)getActivity();
        iManager = (Interface_Manager)getActivity();

        // Connection
        cVolley = new Volley_Connection(getActivity());

        // Data
        update();

        // Layout
        Log.i("onCreateView", "Fragment_Checker");
        setLayout(inflater, container);

        return vLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Show Diagnose if there is no diagnose yet or there is one from the same day that is not finished
        if (lDiagnoses.size() > 0 && (DateUtils.isToday(lDiagnoses.get(0).getdLastUpdate().getTime()) && !lDiagnoses.get(0).isbFinished())) {
            Log.i("Wird ", "angezeigt2");
            oDiagnose = lDiagnoses.get(0);
            fDiagnoseContainer.showDiagnose();
        }
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
        fModelChecks = new Fragment_Edit_Model_Checks();
        fModelChecks.setTargetFragment(Fragment_Checker.this, 1);
        aChecker.add(getString(R.string.edit_checks), fModelChecks);
        Fragment_Result_Checker fResult = new Fragment_Result_Checker();
        fResult.setTargetFragment(Fragment_Checker.this, 2);
        aChecker.add(getString(R.string.handling), fResult);
        ViewPager.setAdapter(aChecker);
        aChecker.notifyDataSetChanged();
    }

    // Data

    private void loadDiagnoses() {
        lDiagnoses.clear();
        if (iDevice != null && iDevice.getDevice() != null) {
            Log.i(lTitle, "loadDiagnoses: "+iDevice.getDevice().getIdDevice());
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_DIAGNOSES + iDevice.getDevice().getIdDevice(), null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {

                    lDiagnoses.clear();
                    if (oJson != null) {
                        Log.i("Strange:", ""+iDevice.getDevice().getIdDevice());
                        JSONArray jsonArray = oJson.getJSONArray("lDiagnoses");
                        for (int i = 0; i <jsonArray.length(); i++) {
                            JSONObject jsonDiagnose = jsonArray.getJSONObject(i);
                            Diagnose oDiagnose = new Diagnose(getActivity(), jsonDiagnose);
                            lDiagnoses.add(oDiagnose);
                        }
                        // Sort
                        sortDiagnoses();

                        // Show Diagnose if there is no diagnose yet or there is one from the same day that is not finished
                        if (lDiagnoses.size() == 0 || (DateUtils.isToday(lDiagnoses.get(0).getdCreation().getTime()) && !lDiagnoses.get(0).isbFinished())) {
                            Log.i("Wird ", "angezeigt");
                            if (lDiagnoses.size() > 0) {
                                oDiagnose = lDiagnoses.get(0);
                            }
                            //fDiagnoseContainer.showDiagnose();
                        } else {
                            //fDiagnoseContainer.showMenu();
                        }
                    }
                    if (aChecker != null) {
                        aChecker.update();
                    }
                }
            });
        }
    }

    @Override
    public void loadModelChecks() {
        // Load
        Log.i("whattt", "the fuck");
        if (iDevice != null && iDevice.getDevice().getoModel() != null) {
            Log.i("heeee", "jooo");
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_CHECKS + iDevice.getDevice().getoModel().getkModel(), null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    lModelChecks.clear();
                    if (oJson != null) {
                        JSONArray aJson = oJson.getJSONArray("lModelChecks");
                        for (int i = 0; i < aJson.length(); i++) {
                            JSONObject jsonModelCheck = aJson.getJSONObject(i);
                            ModelCheck oModelCheck = new ModelCheck(jsonModelCheck, getActivity());
                            lModelChecks.add(oModelCheck);
                        }
                    }
                    ModelCheck.sortByPosition(lModelChecks);
                    ModelCheck.sortByLogic(lModelChecks);
                    ModelCheck.updatePosition(lModelChecks);

                    fModelChecks.refresh(false);
                    fDiagnoseContainer.updateLayout();

                    if (!bCreated) {
                        bCreated = true;
                        if (lModelChecks.size() == 0) {
                            showTab(1);
                        } else {
                            showTab(0);
                        }
                    }
                }
            });
        }
    }

    @Override
    public void diagnoseFinished() {
        showHandler();
        if ((iDevice.getDevice().gettState() == Constants_Intern.STATE_RECYCLING || iDevice.getDevice().gettState() == Constants_Intern.STATE_DEFECT_REPAIR) && iDevice.getDevice().getoBattery() != null && iDevice.getDevice().getoBattery().getlStock() < 2) {
            Log.i("Print", "Battery");
            iDevice.printDeviceBattery();
        } else {
            Log.i("Don't Print", "Battery");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public void update() {
        Log.i(lTitle, "update()");
        oDiagnose = null;
        loadDiagnoses();
        loadModelChecks();
    }

    @Override
    public ArrayList<Diagnose> getDiagnoses() {
        return lDiagnoses;
    }

    @Override
    public ArrayList<ModelCheck> getModelChecks() {
        return lModelChecks;
    }

    @Override
    public void addDiagnose() {
        oDiagnose = null;
        fDiagnoseContainer.showDiagnose();
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
    public void setDiagnose(Diagnose diagnose) {
        oDiagnose = diagnose;
    }

    @Override
    public void deleteDiagnose(Diagnose diagnose) {
        if (lDiagnoses.indexOf(diagnose) == 0) {
            setDiagnose(null);
        }
        diagnose.delete(lModelChecks);
        lDiagnoses.remove(diagnose);
        fDiagnoseContainer.showMenu();
        diagnoseChange();

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
    public void showTab(Integer position) {
        if (ViewPager != null) {
            if (position == null) {
                ViewPager.setCurrentItem(0);
                fDiagnoseContainer.showMenu();
            } else {
                ViewPager.setCurrentItem(position);
            }
        }
    }

    @Override
    public void diagnoseChange() {
        sortDiagnoses();
        aChecker.updateLayout();
    }

    @Override
    public void changeModelChecks(boolean updateModelChecks) {
        fDiagnoseContainer.getFragmentDiagnose().updateLayout();
        if (updateModelChecks) fModelChecks.refresh(true);
    }

    public void sortDiagnoses() {
        Collections.sort(lDiagnoses);
    }

    public void removeFragments() {
        if (fDiagnoseContainer != null) fDiagnoseContainer.removeFragments();
        if (aChecker != null) aChecker.remove();
    }

    public void reset() {
        bCreated = false;
        showTab(0);
    }
}
