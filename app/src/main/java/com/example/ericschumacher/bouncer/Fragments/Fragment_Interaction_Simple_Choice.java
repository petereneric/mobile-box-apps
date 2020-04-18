package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_DeviceManager;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

public class Fragment_Interaction_Simple_Choice extends Fragment implements View.OnClickListener {

    // vLayout
    View Layout;
    Button bOne;
    Button bTwo;
    Button bThree;
    Button bFour;
    Button bFive;
    Button bSix;
    Button bSeven;
    Button bEight;
    Button bNine;
    TextView tvInteractionTitle;
    LinearLayout llOne;
    LinearLayout llTwo;
    LinearLayout llThree;

    // Data
    Bundle dBundle;
    String[] lNameButtons;
    String cInteractionTitle;
    String[] lTitle;
    int[] lColorIds;
    int[] lDrawableIds;

    // Interface
    Interface_DeviceManager mInterface;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Data
        dBundle = getArguments();
        lNameButtons = dBundle.getStringArray(Constants_Intern.LIST_BUTTONS);
        cInteractionTitle = dBundle.getString(Constants_Intern.INTERACTION_TITLE);
        lColorIds = dBundle.getIntArray(Constants_Intern.LIST_COLOR_IDS);
        lDrawableIds = dBundle.getIntArray(Constants_Intern.LIST_DRAWABLE_IDS);

        // vLayout
        setLayout(inflater, container);

        // Interface
        mInterface = (Interface_DeviceManager)getActivity();

        return Layout;
    }

    private void setLayout(LayoutInflater inflater, @Nullable ViewGroup container) {
        Layout = inflater.inflate(R.layout.fragment_interaction_simple_choice, container, false);

        bOne = Layout.findViewById(R.id.bOne);
        bTwo = Layout.findViewById(R.id.bTwo);
        bThree = Layout.findViewById(R.id.bThree);
        bFour = Layout.findViewById(R.id.bFour);
        bFive = Layout.findViewById(R.id.bFive);
        bSix = Layout.findViewById(R.id.bSix);
        bSeven = Layout.findViewById(R.id.bSeven);
        bEight = Layout.findViewById(R.id.bEight);
        bNine = Layout.findViewById(R.id.bNine);
        tvInteractionTitle = Layout.findViewById(R.id.tvTitle);
        llOne = Layout.findViewById(R.id.llOne);
        llTwo = Layout.findViewById(R.id.llTwo);
        llThree = Layout.findViewById(R.id.llThree);

        tvInteractionTitle.setText(cInteractionTitle);

        ArrayList<Button> aButtons = new ArrayList<>();
        aButtons.add(bOne);
        aButtons.add(bTwo);
        aButtons.add(bThree);
        aButtons.add(bFour);
        aButtons.add(bFive);
        aButtons.add(bSix);
        aButtons.add(bSeven);
        aButtons.add(bEight);
        aButtons.add(bNine);

        bOne.setOnClickListener(this);
        bTwo.setOnClickListener(this);
        bThree.setOnClickListener(this);
        bFour.setOnClickListener(this);
        bFive.setOnClickListener(this);
        bSix.setOnClickListener(this);
        bSeven.setOnClickListener(this);
        bEight.setOnClickListener(this);
        bNine.setOnClickListener(this);

        if (lNameButtons.length > 0) {
            llOne.setVisibility(View.VISIBLE);
        }
        if (lNameButtons.length > 3) {
            llTwo.setVisibility(View.VISIBLE);
        }
        if (lNameButtons.length > 6) {
            llThree.setVisibility(View.VISIBLE);
        }

        for (int i = 0; i < lNameButtons.length; i++) {
            Button button = aButtons.get(i);
            if (!lNameButtons[i].equals(Constants_Intern.GONE)) {
                button.setVisibility(View.VISIBLE);
                button.setText(lNameButtons[i]);
            }
        }

        for (int i = 0; i < lDrawableIds.length; i++) {
            Button button = aButtons.get(i);
            if (!(lDrawableIds[i] == Constants_Intern.GONE_INT)) {
                button.setBackgroundResource(lDrawableIds[i]);
            }
        }

        for (int i = 0; i < lColorIds.length; i++) {
            Button button = aButtons.get(i);
            if (!(lColorIds[i] == Constants_Intern.GONE_INT)) {
                button.setTextColor(ResourcesCompat.getColor(getContext().getResources(), (lColorIds[i]), null));
            }
        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bOne:
                mInterface.onClickInteractionButton(getTag(), Constants_Intern.BUTTON_ONE);
                break;
            case R.id.bTwo:
                mInterface.onClickInteractionButton(getTag(), Constants_Intern.BUTTON_TWO);
                break;
            case R.id.bThree:
                mInterface.onClickInteractionButton(getTag(), Constants_Intern.BUTTON_THREE);
                break;
            case R.id.bFour:
                mInterface.onClickInteractionButton(getTag(), Constants_Intern.BUTTON_FOUR);
                break;
            case R.id.bFive:
                mInterface.onClickInteractionButton(getTag(), Constants_Intern.BUTTON_FIVE);
                break;
            case R.id.bSix:
                mInterface.onClickInteractionButton(getTag(), Constants_Intern.BUTTON_SIX);
                break;
            case R.id.bSeven:
                mInterface.onClickInteractionButton(getTag(), Constants_Intern.BUTTON_SEVEN);
                break;
            case R.id.bEight:
                mInterface.onClickInteractionButton(getTag(), Constants_Intern.BUTTON_EIGHT);
                break;
            case R.id.bNine:
                mInterface.onClickInteractionButton(getTag(), Constants_Intern.BUTTON_NINE);
                break;
        }
    }
}
