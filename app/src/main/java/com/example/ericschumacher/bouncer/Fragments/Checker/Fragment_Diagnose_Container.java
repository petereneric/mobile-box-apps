package com.example.ericschumacher.bouncer.Fragments.Checker;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Device;
import com.example.ericschumacher.bouncer.Fragments.Table.Fragment_Table;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Checker;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Diagnose_Container;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Diagnose;
import com.example.ericschumacher.bouncer.Objects.ModelCheck;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Diagnose_Container extends Fragment implements Interface_Update, Interface_Fragment_Diagnose_Container, Fragment_Table.Interface_Fragment_Table, Interface_Fragment_Checker {

    // Layout
    public View vLayout;
    FrameLayout flContainer;

    // FragmentManager
    FragmentManager fManager;

    // Connection
    Volley_Connection cVolley;

    // Interfaces
    Fragment_Device.Interface_Device iDevice;
    Interface_Fragment_Checker iChecker;
    Interface_Update iUpdateDiagnoseMenu;
    Interface_Update iUpdateDiagnose;

    // Fragments
    Fragment_List_Diagnose_Menu fMenu;
    Fragment_List_Diagnose fDiagnose;

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
        iChecker = (Interface_Fragment_Checker)getTargetFragment();

        // Connection
        cVolley = new Volley_Connection(getActivity());

        // FragmentManager
        fManager = getChildFragmentManager();

        // Fragments

        fMenu = new Fragment_List_Diagnose_Menu();
        fDiagnose = new Fragment_List_Diagnose();
        //addFragment(fMenu,"FRAGMENT_DIAGNOSE_MENU");
        //addFragment(fDiagnose,"FRAGMENT_DIAGNOSE");
        if (iChecker.getDiagnoses().size() == 0 || (DateUtils.isToday(iChecker.getDiagnoses().get(0).getdCreation().getTime()) && !iChecker.getDiagnoses().get(0).isbFinished())) {
            showDiagnose();
        } else {
            showMenu();
        }
        Log.i("CRRREATION", "test");

        // Interfaces
        iUpdateDiagnoseMenu = (Interface_Update)fMenu;
        iUpdateDiagnose = (Interface_Update)fDiagnose;



        Log.i("CRRREATION", "test2");
        setLayout(inflater, container);
        return vLayout;
    }


    // Layout

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(R.layout.fragment_diagnose_container, container, false);
        flContainer = vLayout.findViewById(R.id.flContainer);
    }


    @Override
    public void update() {
        if (fManager != null) {
            if (iUpdateDiagnoseMenu != null) {
                iUpdateDiagnoseMenu.update();
            }
            if (iUpdateDiagnose != null) {
                iUpdateDiagnose.update();
            }
        }
    }

    public void updateLayout() {
        if (fManager != null) {
            if (iUpdateDiagnoseMenu != null) {
                iUpdateDiagnoseMenu.update();
            }
            if (fDiagnose != null) {
                fDiagnose.updateLayout();
            }
        }
    }

    @Override
    public void newDiagnose() {

    }

    private void replaceFragment(Fragment fragment, String tag) {
        if (fManager != null) {
            /*
            for(int i = 0; i < fManager.getBackStackEntryCount(); ++i) {
                fManager.popBackStack();
            }
             */

            FragmentTransaction fTransaction = fManager.beginTransaction();
            fTransaction.addToBackStack(null);
            fTransaction.replace(R.id.flContainer, fragment, tag).commit();
        }
    }

    private void addFragment(Fragment fragment, String tag) {
        if (fManager != null) {
            FragmentTransaction fTransaction = fManager.beginTransaction();
            fTransaction.addToBackStack(null);
            fTransaction.add(R.id.flContainer, fragment, tag).commit();
        }
    }

    @Override
    public void returnTable(String cTag, JSONObject oJson) {
        try {
            int kDiagnose = oJson.getInt("id");
            Fragment_List_Diagnose fDiagnose = new Fragment_List_Diagnose();
            Bundle data = new Bundle();
            data.putInt("kDiagnose", kDiagnose);
            fDiagnose.setArguments(data);
            replaceFragment(fDiagnose, "FRAGMENT_DIAGNOSE");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Diagnose> getDiagnoses() {
        if (iChecker != null) {
            return iChecker.getDiagnoses();
        } else {
            Log.i("Hier liegt es", iDevice.getDevice().getIdDevice()+"");
            return new ArrayList<Diagnose>();
        }

    }

    @Override
    public ArrayList<ModelCheck> getModelChecks() {
        return iChecker.getModelChecks();
    }

    @Override
    public void addDiagnose() {
        iChecker.addDiagnose();
    }

    @Override
    public void editDiagnose(int position) {
        iChecker.editDiagnose(position);
    }

    @Override
    public Diagnose getDiagnose() {
        return iChecker.getDiagnose();
    }

    @Override
    public void setDiagnose(Diagnose diagnose) {
        iChecker.setDiagnose(diagnose);
    }

    @Override
    public void deleteDiagnose(Diagnose diagnose) {
        iChecker.deleteDiagnose(diagnose);
    }

    @Override
    public void pauseDiagnose() {
        iChecker.pauseDiagnose();
    }

    @Override
    public void showHandler() {
        iChecker.showHandler();
    }

    @Override
    public void showTab(Integer position) {
        iChecker.showTab(position);
    }

    @Override
    public void diagnoseChange() {
        iChecker.diagnoseChange();
    }

    @Override
    public void changeModelChecks() {
        changeModelChecks();
    }

    public void showDiagnose() {
        Log.i("Call", "showDiagnose");
        replaceFragment(fDiagnose, "FRAGMENT_DIAGNOSE");
        if (fDiagnose != null) {
            fDiagnose.update();
        }
    }

    public void showMenu() {
        Log.i("Call", "showMenu");
        replaceFragment(fMenu, "FRAGMENT_DIAGNOSE_MENU");
    }

    public Fragment_List_Diagnose getFragmentDiagnose() {
        return fDiagnose;
    }



    public void removeFragments() {
        if (fManager != null) {
            for(int i = 0; i < fManager.getBackStackEntryCount(); ++i) {
                fManager.popBackStack();
            }
            /*
            FragmentTransaction fTransaction = fManager.beginTransaction();
            fTransaction.remove(fManager.findFragmentByTag("FRAGMENT_DIAGNOSE")).commit();
            FragmentTransaction fTransaction2 = fManager.beginTransaction();
            fTransaction2.remove(fManager.findFragmentByTag("FRAGMENT_DIAGNOSE_MENU")).commit();

             */
        }
    }
}

