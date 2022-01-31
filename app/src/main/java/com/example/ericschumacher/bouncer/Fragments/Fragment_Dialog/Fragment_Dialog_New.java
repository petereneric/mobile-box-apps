package com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Dialog_New extends DialogFragment implements View.OnClickListener {

    // Layout
    View vLayout;
    TextView tvTitle;
    TextView tvSubtitle;
    TextView tvContent;
    View vDivider;
    ConstraintLayout clButtonsText;
    ConstraintLayout clButtons;
    ConstraintLayout clText;
    ImageButton ibNeutral;
    ImageButton ibPositive;
    ImageButton ibNegative;
    TextView tvNeutral;
    TextView tvPositive;
    TextView tvNegative;

    // Interface
    Interface_Dialog_New iDialog;

    // Data
    Integer nPosition = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Interface
        iDialog = (Interface_Dialog_New)getTargetFragment();

        // Data
        if (getArguments() != null) nPosition = getArguments().getInt("nPosition", 0);

        // Layout
        setLayout(inflater, container);

        return vLayout;
    }

    // Layout
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        // Initiate
        vLayout = inflater.inflate(R.layout.fragment_dialog_new, container, false);
        tvTitle = vLayout.findViewById(R.id.tvTitle);
        tvSubtitle = vLayout.findViewById(R.id.tvSubtitle);
        tvContent = vLayout.findViewById(R.id.tvContent);
        vDivider = vLayout.findViewById(R.id.tvContent);
        clButtonsText = vLayout.findViewById(R.id.clButtonsText);
        clButtons = vLayout.findViewById(R.id.clButtons);
        clText = vLayout.findViewById(R.id.clText);
        ibNeutral = vLayout.findViewById(R.id.ibNeutral);
        ibPositive = vLayout.findViewById(R.id.ibPositive);
        ibNegative = vLayout.findViewById(R.id.ibNegative);
        tvNeutral = vLayout.findViewById(R.id.tvNeutral);
        tvPositive = vLayout.findViewById(R.id.tvPositive);
        tvNegative = vLayout.findViewById(R.id.tvNegative);

        // ClickListener
        ibNeutral.setOnClickListener(this);
        ibPositive.setOnClickListener(this);
        ibNegative.setOnClickListener(this);
        tvNeutral.setOnClickListener(this);
        tvPositive.setOnClickListener(this);
        tvNegative.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibNeutral:
            case R.id.tvNeutral:
                iDialog.returnDialog(getTag(), Constants_Intern.TYPE_ACTION_NEUTRAL, nPosition);
                dismiss();
                break;
            case R.id.ibPositive:
            case R.id.tvPositive:
                iDialog.returnDialog(getTag(), Constants_Intern.TYPE_ACTION_POSITIVE, nPosition);
                dismiss();
                break;
            case R.id.ibNegative:
            case R.id.tvNegative:
                iDialog.returnDialog(getTag(), Constants_Intern.TYPE_ACTION_NEGATIVE, nPosition);
                dismiss();
                break;
        }
    }

    public interface Interface_Dialog_New {
        public void returnDialog(String fTag, int tAction, Integer nPosition);
    }
}
