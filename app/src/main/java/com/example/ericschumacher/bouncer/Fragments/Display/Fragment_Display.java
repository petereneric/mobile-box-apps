package com.example.ericschumacher.bouncer.Fragments.Display;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Density;

public class Fragment_Display extends Fragment implements View.OnClickListener {

    // Layout
    int kLayout = R.layout.fragment_display;
    View vLayout;
    RelativeLayout rlMain;
    TextView tvText;

    // Data
    public Bundle bData;
    String cTitle;
    String cText;

    // Interface
    Interface_Display iDisplay;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Arguments
        bData = getArguments();
        cText = bData.getString(Constants_Intern.TEXT);

        // Interface
        iDisplay = (Interface_Display)getActivity();
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
        vLayout = inflater.inflate(kLayout, container, false);
        rlMain = vLayout.findViewById(R.id.rlMain);
        tvText = vLayout.findViewById(R.id.tvText);

        // Fill with arguments
        tvText.setText(cText);

        // Background
        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.RECTANGLE);
        shape.setCornerRadii(new float[] {Utility_Density.getDp(getActivity(), 16),Utility_Density.getDp(getActivity(), 16), Utility_Density.getDp(getActivity(), 16), Utility_Density.getDp(getActivity(), 16), Utility_Density.getDp(getActivity(), 16), Utility_Density.getDp(getActivity(), 16), Utility_Density.getDp(getActivity(), 16), Utility_Density.getDp(getActivity(), 16)});
        //shape.setColor(ContextCompat.getColor(Context, R.color.color_green));
        shape.setStroke(1, ResourcesCompat.getColor(getActivity().getResources(), R.color.color_grey_secondary, null));
        tvText.setBackground(shape);

        // ClickListener
        rlMain.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlMain:
                iDisplay.returnDisplay(getTag());
        }
    }

    public interface Interface_Display {
        void returnDisplay(String cTag);
    }
}
