package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
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

    Interface_Selection iSelection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Layout = inflater.inflate(R.layout.fragment_request_shape, container, false);
        setLayout();

        iSelection = (Interface_Selection)getActivity();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    void setLayout() {
        tvAcceptable = Layout.findViewById(R.id.tvAcceptable);
        tvGood = Layout.findViewById(R.id.tvGood);
        tvVeryGood = Layout.findViewById(R.id.tvVeryGood);
        tvCherry = Layout.findViewById(R.id.tvCherry);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvAcceptable:
                iSelection.setShape(Constants_Intern.SHAPE_ACCEPTABLE);
                break;
            case R.id.tvGood:
                iSelection.setShape(Constants_Intern.SHAPE_GOOD);
                break;
            case R.id.tvVeryGood:
                iSelection.setShape(Constants_Intern.SHAPE_VERY_GOOD);
                break;
            case R.id.tvCherry:
                iSelection.setShape(Constants_Intern.SHAPE_CHERRY);
                break;
        }
    }
}
