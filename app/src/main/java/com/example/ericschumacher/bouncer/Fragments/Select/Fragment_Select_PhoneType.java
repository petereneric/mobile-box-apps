package com.example.ericschumacher.bouncer.Fragments.Select;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Select_PhoneType extends Fragment_Select {

    @Override
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        // Visibility
        llOne.setVisibility(View.VISIBLE);
        bOne.setVisibility(View.VISIBLE);
        bTwo.setVisibility(View.VISIBLE);

        // Text
        setText(bOne, R.string.handy);
        setText(bTwo, R.string.smartphone);

        // TextColor
        setTextColor(bOne, R.color.color_green);
        setTextColor(bTwo, R.color.color_orange);

        // Background
        setBackground(bOne, R.color.color_green);
        setBackground(bTwo, R.color.color_orange);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bOne:
                iSelect.returnSelect(getTag(), Constants_Intern.TYPE_PHONE_HANDY);
                break;
            case R.id.bTwo:
                iSelect.returnSelect(getTag(), Constants_Intern.TYPE_PHONE_SMARTPHONE);
                break;
        }
    }
}
