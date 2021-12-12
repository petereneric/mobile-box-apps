package com.example.ericschumacher.bouncer.Fragments.Choice.Image;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Choice.Fragment_Choice;
import com.example.ericschumacher.bouncer.Fragments.Edit.Fragment_Edit_Model_Color;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Device;
import com.example.ericschumacher.bouncer.Objects.ModelColor;

public class Fragment_Choice_Image_ModelColor extends Fragment_Edit_Model_Color {

    // Interface
    Fragment_Device.Interface_Device iDevice;
    Fragment_Choice.Interface_Choice iChoice;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Interface
        iDevice = (Fragment_Device.Interface_Device)getActivity();
        iChoice = (Fragment_Choice.Interface_Choice)getActivity();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onClick(int position) {
        if (mAdapter.getItemViewType(position) == Constants_Intern.ITEM) {
            ModelColor oModelColor = iModel.getModel().getlModelColor().get(position);
            iChoice.returnChoice(getTag(), oModelColor);
            return;
        }
        super.onClick(position);
    }
}
