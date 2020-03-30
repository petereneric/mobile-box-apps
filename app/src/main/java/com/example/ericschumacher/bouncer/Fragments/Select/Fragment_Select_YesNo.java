package com.example.ericschumacher.bouncer.Fragments.Select;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.R;

public class Fragment_Select_YesNo extends Fragment_Select {

    @Override
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        // Visibility
        llOne.setVisibility(View.VISIBLE);
        bOne.setVisibility(View.VISIBLE);
        bTwo.setVisibility(View.VISIBLE);

        // Text
        setText(bOne, R.string.no);
        setText(bTwo, R.string.yes);

        // TextColor
        setTextColor(bOne, R.color.color_no);
        setTextColor(bTwo, R.color.color_yes);

        // Background
        setBackground(bOne, R.color.color_no);
        setBackground(bTwo, R.color.color_yes);
    }
}
