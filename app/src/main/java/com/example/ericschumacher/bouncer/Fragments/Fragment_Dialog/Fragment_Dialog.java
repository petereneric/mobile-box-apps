package com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Dialog extends DialogFragment implements View.OnClickListener {

    // Layout
    View vLayout;
    TextView tvHeader;
    TextView tvContent;
    TextView tvButtonOne;
    TextView tvButtonTwo;
    ImageView ivButtonOne;
    ImageView ivButtonTwo;
    RelativeLayout rlButtons;
    RelativeLayout rlButtonOne;
    RelativeLayout rlButtonTwo;

    // Interface
    Interface_Fragment_Dialog iFragmentDialog;

    // Data
    int nPosition;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        // Interface
        iFragmentDialog = (Interface_Fragment_Dialog)getTargetFragment();

        // Data
        if (getArguments() != null) nPosition = getArguments().getInt("nPosition", 0);

        // Layout
        setLayout(inflater, container);

        return vLayout;
    }

    // Layout
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        // Initiate
        vLayout = inflater.inflate(R.layout.fragment_dialog, container, false);
        tvHeader = vLayout.findViewById(R.id.tvHeader);
        tvContent = vLayout.findViewById(R.id.tvContent);
        tvButtonOne = vLayout.findViewById(R.id.tvButtonOne);
        tvButtonTwo = vLayout.findViewById(R.id.tvButtonTwo);
        ivButtonOne = vLayout.findViewById(R.id.ivButtonOne);
        ivButtonTwo = vLayout.findViewById(R.id.ivButtonTwo);
        rlButtons = vLayout.findViewById(R.id.rlButtons);
        rlButtonOne = vLayout.findViewById(R.id.rlButtonOne);
        rlButtonTwo = vLayout.findViewById(R.id.rlButtonTwo);

        // ClickListener
        rlButtonOne.setOnClickListener(this);
        rlButtonTwo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rlButtonOne:
                clickButtonOne();
                dismiss();
                break;
            case R.id.rlButtonTwo:
                clickButtonTwo();
                dismiss();
                break;
        }
    }

    public void clickButtonOne() {
        iFragmentDialog.click(getTag(), Constants_Intern.BUTTON_ONE, nPosition);
    }

    public void clickButtonTwo() {
        iFragmentDialog.click(getTag(), Constants_Intern.BUTTON_TWO, nPosition);
    }

    public interface Interface_Fragment_Dialog {
        public void click(String cTag, int typeButton, int position);
    }

    public void setHeightContent(int height) {
        tvContent.getLayoutParams().height = height;
    }
}
