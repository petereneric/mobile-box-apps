package com.example.ericschumacher.bouncer.Fragments.Select;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Select_Shape extends Fragment_Select {

    @Override
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        // Visibility
        llOne.setVisibility(View.VISIBLE);
        bOne.setVisibility(View.VISIBLE);
        bTwo.setVisibility(View.VISIBLE);
        llTwo.setVisibility(View.VISIBLE);
        bFour.setVisibility(View.VISIBLE);
        bFive.setVisibility(View.VISIBLE);
        llThree.setVisibility(View.VISIBLE);
        bSeven.setVisibility(View.VISIBLE);

        // Text
        setText(bOne, R.string.shape_cherry);
        setText(bTwo, R.string.shape_very_good);
        setText(bFour, R.string.shape_good);
        setText(bFive, R.string.shape_acceptable);
        setText(bSeven, R.string.broken);

        // TextColor
        setTextColor(bOne, R.color.color_shape_cherry);
        setTextColor(bTwo, R.color.color_shape_very_good);
        setTextColor(bFour, R.color.color_shape_good);
        setTextColor(bFive, R.color.color_shape_acceptable);
        setTextColor(bSeven, R.color.color_shape_broken);

        // Background
        setBackground(bOne, R.color.color_shape_cherry);
        setBackground(bTwo, R.color.color_shape_very_good);
        setBackground(bFour, R.color.color_shape_good);
        setBackground(bFive, R.color.color_shape_acceptable);
        setBackground(bSeven, R.color.color_shape_broken);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bOne:
                iSelect.returnSelect(getTag(), Constants_Intern.SHAPE_CHERRY);
                break;
            case R.id.bTwo:
                iSelect.returnSelect(getTag(), Constants_Intern.SHAPE_VERY_GOOD);
                break;
            case R.id.bFour:
                iSelect.returnSelect(getTag(), Constants_Intern.SHAPE_GOOD);
                break;
            case R.id.bFive:
                iSelect.returnSelect(getTag(), Constants_Intern.SHAPE_ACCEPTABLE);
                break;
            case R.id.bSeven:
                iSelect.returnSelect(getTag(), Constants_Intern.SHAPE_BROKEN);
                break;
        }
    }
}
