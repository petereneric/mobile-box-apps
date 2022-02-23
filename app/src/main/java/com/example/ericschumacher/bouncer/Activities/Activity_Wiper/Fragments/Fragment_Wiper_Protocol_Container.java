package com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Fragments;

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

import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Wiper_Single;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

public class Fragment_Wiper_Protocol_Container extends Fragment implements Interface_Update {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // VALUES & VARIABLES

    // Layout
    private View vLayout;
    FrameLayout flContainer;

    // FragmentManager
    FragmentManager fManager;

    // Connection
    Volley_Connection cVolley;

    // Interfaces
    Fragment_Device.Interface_Device iDevice;
    Interface_Fragment_Wiper_Single iWiper;
    Interface_Update iUpdateProtocol;
    Interface_Update iUpdateProtocolMenu;

    // Fragments
    Fragment_Wiper_Protocol_Single fProtocol;
    Fragment_Wiper_Protocol_Menu fProtocolMenu;


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LIFECYCLE

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        // Interfaces
        iWiper = (Interface_Fragment_Wiper_Single)getTargetFragment();

        // Connection
        cVolley = new Volley_Connection(getActivity());

        // FragmentManager
        fManager = getChildFragmentManager();

        // Fragments
        fProtocol = new Fragment_Wiper_Protocol_Single();
        fProtocolMenu = new Fragment_Wiper_Protocol_Menu();
        if (iWiper.getProtocols().size() == 0 || (DateUtils.isToday(iWiper.getProtocols().get(0).getdCreation().getTime()))) {
            showProtocol();
        } else {
            showProtocolMenu();
        }
        Log.i("CRRREATION", "test");

        // Interfaces
        iUpdateProtocol = (Interface_Update)fProtocol;
        iUpdateProtocolMenu = (Interface_Update)fProtocolMenu;



        Log.i("CRRREATION", "test2");
        setLayout(inflater, container);

        return vLayout;
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PRIVATE

    // Layout
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(R.layout.fragment_container, container, false);
        flContainer = vLayout.findViewById(R.id.flContainer);
    }

    // Fragments
    private void replaceFragment(Fragment fragment, String tag) {
        if (fManager != null) {
            FragmentTransaction fTransaction = fManager.beginTransaction();
            fTransaction.addToBackStack(null);
            fTransaction.replace(R.id.flContainer, fragment, tag).commit();
        }
    }

    public void showProtocol() {
        Log.i("Call", "showDiagnose");
        replaceFragment(((Fragment)fProtocol), "FRAGMENT_PROTOCOL");
        if (fProtocol != null) {
            fProtocol.update();
        }
    }

    public void showProtocolMenu() {
        Log.i("Call", "showMenu");
        replaceFragment(fProtocolMenu, "FRAGMENT_PROTOCOL_MENU");
    }

    public void removeFragments() {
        if (fManager != null) {
            for(int i = 0; i < fManager.getBackStackEntryCount(); ++i) {
                fManager.popBackStack();
            }
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PUBLIC

    @Override
    public void update() {
        if (fManager != null) {
            if (iUpdateProtocol != null) {
                iUpdateProtocol.update();
            }
            if (iUpdateProtocolMenu != null) {
                iUpdateProtocolMenu.update();
            }
        }
    }

    public Fragment_Wiper_Protocol_Single getFragmentProtocol() {
        return fProtocol;
    }
}
