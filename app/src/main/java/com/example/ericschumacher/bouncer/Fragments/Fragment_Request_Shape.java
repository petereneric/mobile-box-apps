package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Manager;
import com.example.ericschumacher.bouncer.Objects.Additive.Variation_Shape;
import com.example.ericschumacher.bouncer.R;

/**
 * Created by Eric on 13.06.2018.
 */

public class Fragment_Request_Shape extends Fragment implements View.OnClickListener {

    View Layout;
    TextView tvAcceptable;
    TextView tvGood;
    TextView tvVeryGood;
    TextView tvCherry;

    Interface_Manager iManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Layout = inflater.inflate(R.layout.fragment_request_shape, container, false);
        setLayout();

        iManager = (Interface_Manager) getActivity();

        return Layout;
    }

    void setLayout() {
        tvAcceptable = Layout.findViewById(R.id.tvAcceptable);
        tvGood = Layout.findViewById(R.id.tvGood);
        tvVeryGood = Layout.findViewById(R.id.tvVeryGood);
        tvCherry = Layout.findViewById(R.id.tvCherry);

        tvAcceptable.setOnClickListener(this);
        tvGood.setOnClickListener(this);
        tvVeryGood.setOnClickListener(this);
        tvCherry.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAcceptable:
                iManager.returnShape(new Variation_Shape(Constants_Intern.SHAPE_ACCEPTABLE, getString(R.string.shape_acceptable)));
                break;
            case R.id.tvGood:
                iManager.returnShape(new Variation_Shape(Constants_Intern.SHAPE_GOOD, getString(R.string.shape_good)));
                break;
            case R.id.tvVeryGood:
                iManager.returnShape(new Variation_Shape(Constants_Intern.SHAPE_VERY_GOOD, getString(R.string.shape_very_good)));
                break;
            case R.id.tvCherry:
                iManager.returnShape(new Variation_Shape(Constants_Intern.SHAPE_CHERRY, getString(R.string.shape_cherry)));
                break;
        }
    }
}
