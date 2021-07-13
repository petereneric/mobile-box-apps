package com.example.ericschumacher.bouncer.Fragments.Others;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.ericschumacher.bouncer.Fragments.List.Fragment_List_Diagnose;
import com.example.ericschumacher.bouncer.Fragments.Table.Fragment_Table_Diagnose_Menu;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Diagnose_Container;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Diagnose_Container extends Fragment implements Interface_Update, Interface_Fragment_Diagnose_Container {

    // Layout
    public View vLayout;
    FrameLayout flContainer;

    // FragmentManager
    FragmentManager fManager;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        setLayout(inflater, container);

        // FragmentManager
        fManager = getChildFragmentManager();

        // DiagnoseMenu
        Fragment_Table_Diagnose_Menu fDiagnoseMenu = new Fragment_Table_Diagnose_Menu();
        replaceFragment(fDiagnoseMenu);

        return vLayout;
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(R.layout.fragment_diagnose_container, container, false);
        flContainer = vLayout.findViewById(R.id.flContainer);
    }


    @Override
    public void update() {
        if (fManager != null) {
            Interface_Update iUpdate = (Interface_Update) getChildFragmentManager().findFragmentByTag("FRAGMENT_DIAGNOSE");
            if (iUpdate != null) iUpdate.update();
        }
    }

    @Override
    public void newDiagnose() {
        if (fManager != null) {
            Fragment_List_Diagnose fDiagnose = new Fragment_List_Diagnose();
            replaceFragment(fDiagnose);
        }
    }

    private void replaceFragment(Fragment fragment) {
        FragmentTransaction fTransaction = fManager.beginTransaction();
        fTransaction.addToBackStack(null);
        fTransaction.replace(R.id.flContainer, fragment, "FRAGMENT_DIAGNOSE").commit();
    }

}

