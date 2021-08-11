package com.example.ericschumacher.bouncer.Fragments.Checker;

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
import com.example.ericschumacher.bouncer.Adapter.Pager.Adapter_Pager;
import com.example.ericschumacher.bouncer.Adapter.Pager.Adapter_Pager_Checker;
import com.example.ericschumacher.bouncer.Fragments.Edit.Fragment_Edit_Model_Checks;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Device;
import com.example.ericschumacher.bouncer.Fragments.Result.Fragment_Result_Checker;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Checker;
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

    // Adapter
    Adapter_Pager_Checker aChecker;

    // Interfaces
    Fragment_Device.Interface_Device iDevice;

    // Connection
    Volley_Connection cVolley;

    // Data
    ArrayList<ModelCheck> lModelChecks = new ArrayList<>();
    ArrayList<Diagnose> lDiagnoses;
    Diagnose oDiagnose;

    // Fragments
    Fragment_Diagnose_Container fDiagnoseContainer;
    Fragment_Edit_Model_Checks fModelChecks;

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

        // Connection
        cVolley = new Volley_Connection(getActivity());

        // Data
        loadDiagnoses();

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

        /*
        FragmentManager fm = getActivity().getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
         */

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

    public void loadModelChecks() {
        // Load
        if (iDevice != null && iDevice.getDevice().getoModel() != null) {
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
                }
            });
        }
    }

    public void update() {
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
        diagnose.delete();
        lDiagnoses.remove(diagnose);
        fDiagnoseContainer.showMenu();
        aChecker.updateDiagnose();

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
        aChecker.updateDiagnose();
    }

    @Override
    public void changeModelChecks() {
        fDiagnoseContainer.getFragmentDiagnose().updateLayout();
        fModelChecks.refresh(true);
    }

    public void sortDiagnoses() {
        Collections.sort(lDiagnoses);
    }

    public void removeFragments() {
        if (fDiagnoseContainer != null) fDiagnoseContainer.removeFragments();
        if (aChecker != null) aChecker.remove();
    }

    public void reset() {
        showTab(0);
    }
}
