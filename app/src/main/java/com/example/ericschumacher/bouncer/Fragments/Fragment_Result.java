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
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;

/**
 * Created by Eric Schumacher on 21.05.2018.
 */

public class Fragment_Result extends Fragment implements View.OnClickListener {

    // Layout
    View Layout;
    TextView tvResult;

    // Data
    Device oModel;

    // Interface
    Interface_Selection iSelection;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Layout = inflater.inflate(R.layout.fragment_result, container, false);

        // Layout
        setLayout();

        // Data
        oModel = getArguments().getParcelable(Constants_Intern.OBJECT_MODEL);
        tvResult.setText(oModel.getExploitationForScreen(getActivity()));

        // Interface
        iSelection = (Interface_Selection) getActivity();

        return Layout;
    }

    // Layout
    private void setLayout() {
        tvResult = Layout.findViewById(R.id.tvResult);

        tvResult.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvResult:
                iSelection.reset();
                break;
        }
    }
}
