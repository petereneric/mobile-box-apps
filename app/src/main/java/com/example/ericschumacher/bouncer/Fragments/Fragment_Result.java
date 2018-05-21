package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

/**
 * Created by Eric Schumacher on 21.05.2018.
 */

public class Fragment_Result extends Fragment {

    // Layout
    View layout;
    TextView tvResult;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_result, container, false);

        // Data
        tvResult.setText(getArguments().getString(Constants_Intern.SELECTION_RESULT));
        return layout;
    }

    // Layout
    private void setLayout() {
       tvResult = (TextView)layout.findViewById(R.id.tv_result);
    }
}
