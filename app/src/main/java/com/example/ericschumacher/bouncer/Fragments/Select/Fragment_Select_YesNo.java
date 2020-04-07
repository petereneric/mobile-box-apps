package com.example.ericschumacher.bouncer.Fragments.Select;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Select_YesNo extends Fragment_Select {

    // Manual
    // 1. Fragment needs to be created
    // 2. Activity needs to implement Interface_Select
    // 3. Listen in Activity on returnSelect for right fragmentTag and right button

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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bOne:
                iSelect.returnSelect(getTag(), Constants_Intern.NO);
                break;
            case R.id.bTwo:
                iSelect.returnSelect(getTag(), Constants_Intern.YES);
                break;
        }
    }
}
