package com.example.ericschumacher.bouncer.Fragments.Select;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Select_Class extends Fragment_Select {

    @Override
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        // Visibility
        llOne.setVisibility(View.VISIBLE);
        bOne.setVisibility(View.VISIBLE);
        bTwo.setVisibility(View.VISIBLE);

        // Text
        setText(bOne, R.string.automatic);
        setText(bTwo, R.string.class_five_blancco);

        // TextColor
        setTextColor(bOne, R.color.color_text_secondary);
        setTextColor(bTwo, R.color.color_red);

        // Background
        setBackground(bOne, R.color.color_text_secondary);
        setBackground(bTwo, R.color.color_red);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bOne:
                iSelect.returnSelect(getTag(), Constants_Intern.TYPE_CLASS_AUTOMATIC);
                break;
            case R.id.bTwo:
                iSelect.returnSelect(getTag(), Constants_Intern.TYPE_CLASS_V);
                break;
        }
    }
}
