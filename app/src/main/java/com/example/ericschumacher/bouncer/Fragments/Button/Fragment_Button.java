package com.example.ericschumacher.bouncer.Fragments.Button;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Button extends Fragment implements View.OnClickListener {

    // Layout
    View vLayout;
    Button bPrimaryOne;
    Button bPrimaryTwo;
    Button bSecondaryOne;
    Button bSecondaryTwo;

    // Interface
    Interface_Fragment_Button iButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Layout
        setLayout(inflater, container);

        // Interface
        iButton = (Interface_Fragment_Button)getActivity();

        return vLayout;
    }

    // Layout
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        // Initiate
        vLayout = inflater.inflate(R.layout.fragment_input, container, false);
        bPrimaryOne = vLayout.findViewById(R.id.bPrimaryOne);
        bPrimaryTwo = vLayout.findViewById(R.id.bPrimaryTwo);
        bSecondaryOne = vLayout.findViewById(R.id.bSecondaryOne);
        bSecondaryTwo = vLayout.findViewById(R.id.bSecondaryTwo);

        // OnClickListener & TextWatcher
        bPrimaryOne.setOnClickListener(this);
        bPrimaryTwo.setOnClickListener(this);
        bSecondaryOne.setOnClickListener(this);
        bSecondaryTwo.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bPrimaryOne:
                iButton.returnButton(getTag(), Constants_Intern.TYPE_ACTION_FRAGMENT_BUTTON_PRIMARY_ONE);
                break;
            case R.id.bPrimaryTwo:
                iButton.returnButton(getTag(), Constants_Intern.TYPE_ACTION_FRAGMENT_BUTTON_PRIMARY_TWO);
                break;
            case R.id.bSecondaryOne:
                iButton.returnButton(getTag(), Constants_Intern.TYPE_ACTION_FRAGMENT_BUTTON_SECONDARY_ONE);
                break;
            case R.id.bSecondaryTwo:
                iButton.returnButton(getTag(), Constants_Intern.TYPE_ACTION_FRAGMENT_BUTTON_SECONDARY_TWO);
                break;
        }
    }

    public interface Interface_Fragment_Button {
        void returnButton(String cTag, int tAction);
    }
}
