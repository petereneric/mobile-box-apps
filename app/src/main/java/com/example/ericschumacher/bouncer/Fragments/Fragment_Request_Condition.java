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
import com.example.ericschumacher.bouncer.R;

/**
 * Created by Eric Schumacher on 17.06.2018.
 */

public class Fragment_Request_Condition extends Fragment implements View.OnClickListener {

    View Layout;
    TextView tvBroken;
    TextView tvOk;

    Interface_Manager iManager;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Layout = inflater.inflate(R.layout.fragment_request_condition, container, false);

        setLayout();

        iManager = (Interface_Manager)getActivity();

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
                iManager.returnCondition(Constants_Intern.CONDITION_BROKEN);
                break;
            case R.id.tvOk:
                iManager.returnCondition(Constants_Intern.CONDITION_OK);
                break;
        }
    }

}
