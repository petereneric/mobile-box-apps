package com.example.ericschumacher.bouncer.Fragments.Select;

import android.graphics.drawable.GradientDrawable;
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
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Density;

public class Fragment_Select extends Fragment implements View.OnClickListener {

    // Layout
    View vLayout;
    TextView tvTitle;

    LinearLayout llOne;
    LinearLayout llTwo;
    LinearLayout llThree;

    Button bOne;
    Button bTwo;
    Button bThree;
    Button bFour;
    Button bFive;
    Button bSix;
    Button bSeven;
    Button bEight;
    Button bNine;


    // Data
    public Bundle bData;
    String cTitle;

    // Interface
    public Fragment_Select.Interface_Select iSelect;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Arguments
        bData = getArguments();
        cTitle = bData.getString(Constants_Intern.TITLE);

        // Interface
        iSelect = (Fragment_Select.Interface_Select)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Layout
        setLayout(inflater, container);

        return vLayout;
    }

    // Layout
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        // Initiate
        vLayout = inflater.inflate(R.layout.fragment_select, container, false);
        tvTitle = vLayout.findViewById(R.id.tvTitle);
        bOne = vLayout.findViewById(R.id.bOne);
        bTwo = vLayout.findViewById(R.id.bTwo);
        bThree = vLayout.findViewById(R.id.bThree);
        bFour = vLayout.findViewById(R.id.bFour);
        bFive = vLayout.findViewById(R.id.bFive);
        bSix = vLayout.findViewById(R.id.bSix);
        bSeven = vLayout.findViewById(R.id.bSeven);
        bEight = vLayout.findViewById(R.id.bEight);
        bNine = vLayout.findViewById(R.id.bNine);
        llOne = vLayout.findViewById(R.id.llOne);
        llTwo = vLayout.findViewById(R.id.llTwo);
        llThree = vLayout.findViewById(R.id.llThree);

        // OnClickListener
        bOne.setOnClickListener(this);
        bTwo.setOnClickListener(this);
        bThree.setOnClickListener(this);
        bFour.setOnClickListener(this);
        bFive.setOnClickListener(this);
        bSix.setOnClickListener(this);
        bSeven.setOnClickListener(this);
        bEight.setOnClickListener(this);
        bNine.setOnClickListener(this);

        // Fill with arguments
        tvTitle.setText(cTitle);

    }

    // Class methods
    public void setTextColor(Button button, int kColor) {
        button.setTextColor(ResourcesCompat.getColor(getContext().getResources(), kColor, null));
    }

    public void setBackground(Button button, int kColor) {
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[] {Utility_Density.getDp(getActivity(), 16),Utility_Density.getDp(getActivity(), 16), Utility_Density.getDp(getActivity(), 16), Utility_Density.getDp(getActivity(), 16), Utility_Density.getDp(getActivity(), 16), Utility_Density.getDp(getActivity(), 16), Utility_Density.getDp(getActivity(), 16), Utility_Density.getDp(getActivity(), 16)});
        //shape.setColor(ContextCompat.getColor(Context, R.color.color_green));
        shape.setStroke(1, ResourcesCompat.getColor(getActivity().getResources(), kColor, null));
        button.setBackground(shape);
    }

    public void setText(Button button, int kText) {
        button.setText(getString(kText));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bOne:
                iSelect.returnSelect(getTag(), Constants_Intern.BUTTON_ONE);
                break;
            case R.id.bTwo:
                iSelect.returnSelect(getTag(), Constants_Intern.BUTTON_TWO);
                break;
            case R.id.bThree:
                iSelect.returnSelect(getTag(), Constants_Intern.BUTTON_THREE);
                break;
            case R.id.bFour:
                iSelect.returnSelect(getTag(), Constants_Intern.BUTTON_FOUR);
                break;
            case R.id.bFive:
                iSelect.returnSelect(getTag(), Constants_Intern.BUTTON_FIVE);
                break;
            case R.id.bSix:
                iSelect.returnSelect(getTag(), Constants_Intern.BUTTON_SIX);
                break;
            case R.id.bSeven:
                iSelect.returnSelect(getTag(), Constants_Intern.BUTTON_SEVEN);
                break;
            case R.id.bEight:
                iSelect.returnSelect(getTag(), Constants_Intern.BUTTON_EIGHT);
                break;
            case R.id.bNine:
                iSelect.returnSelect(getTag(), Constants_Intern.BUTTON_NINE);
                break;
        }
    }

    public interface Interface_Select {
        public void returnSelect(String cTag, int tSelect);
    }
}

