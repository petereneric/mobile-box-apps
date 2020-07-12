package com.example.ericschumacher.bouncer.Fragments.Button;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.R;

public class Fragment_Button_Columba_Print extends Fragment_Button {

    @Override
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        // Visibility
        bPrimaryOne.setVisibility(View.VISIBLE);
        bSecondaryOne.setVisibility(View.VISIBLE);

        // Text
        bPrimaryOne.setText(getString(R.string.print_shipping_label));
        bSecondaryOne.setText(getString(R.string.report_missing_stock));
    }
}
