package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
import com.example.ericschumacher.bouncer.Objects.Object_Model;
import com.example.ericschumacher.bouncer.R;

/**
 * Created by Eric Schumacher on 17.06.2018.
 */

public class Fragment_Request_Condition extends Fragment implements View.OnClickListener {

    View Layout;
    TextView tvBroken;
    TextView tvOk;

    Object_Model oModel;
    Interface_Selection iSelection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Layout = inflater.inflate(R.layout.fragment_request_condition, container, false);

        setLayout();

        oModel = getArguments().getParcelable(Constants_Intern.OBJECT_MODEL);
        iSelection = (Interface_Selection)getActivity();

        return Layout;
    }

    void setLayout() {
        tvBroken = Layout.findViewById(R.id.tvBroken);
        tvOk = Layout.findViewById(R.id.tvOk);

        tvBroken.setOnClickListener(this);
        tvOk.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvBroken:
                iSelection.setCondition(Constants_Intern.CONDITION_BROKEN);
                break;
            case R.id.tvOk:
                iSelection.setCondition(Constants_Intern.CONDITION_OK);
                break;
        }
    }

}
