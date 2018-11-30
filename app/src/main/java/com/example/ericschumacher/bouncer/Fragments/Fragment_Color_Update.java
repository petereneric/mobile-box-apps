package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Color_Update extends Fragment_Color_Add {

    // Objects
    Color oColor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       Layout =  super.onCreateView(inflater, container, savedInstanceState);

       oColor = (Color)getArguments().getSerializable(Constants_Intern.OBJECT_COLOR);
       if (oColor.getManufacturer() != null) etManufacturer.setText(oColor.getManufacturer().getName());
       if (oColor.getModel() != null) etModelName.setText(oColor.getModel().getName());
       etColorName.setText(oColor.getName());
       etHex.setText(oColor.getHexCode());

       return Layout;

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivDeleteManufacturer:
                oColor.setManufacturer(null);
                break;
            case R.id.ivDeleteModelName:
                oColor.setModel(null);
                break;
            case R.id.bCommit:
                iManager.updateColor(oColor);
                break;
        }
    }
}
