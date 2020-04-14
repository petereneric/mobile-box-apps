package com.example.ericschumacher.bouncer.Fragments.Choice;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Fragments.Edit.Fragment_Edit_Model_Battery;
import com.example.ericschumacher.bouncer.Objects.Model_Battery;

public class Fragment_Choice_DeviceBattery extends Fragment_Edit_Model_Battery {

    // Interface
    public Fragment_Choice.Interface_Choice iChoice;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Interface
        iChoice = (Fragment_Choice.Interface_Choice)getActivity();
    }

    public void setLayout(LayoutInflater inflater, ViewGroup viewGroup) {
        super.setLayout(inflater, viewGroup);
        bCommit.setVisibility(View.GONE);
    }

    public void onClick(Model_Battery oModelBattery) {
        iChoice.returnChoice(getTag(), oModelBattery);
    }
}
