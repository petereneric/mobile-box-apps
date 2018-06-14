package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.R;

/**
 * Created by Eric on 13.06.2018.
 */

public class Fragment_Request_Shape extends Fragment {

    View Layout;

    TextView tvBroken;
    TextView tvRepair;
    TextView tvAcceptable;
    TextView tvGood;
    TextView tvVeryGood;
    TextView tvCherry;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Layout = inflater.inflate(R.layout.fragment_request_shape, container, false);

        setLayout();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    void setLayout() {
        tvBroken = Layout.findViewById(R.id.tvBroken);
        tvRepair = Layout.findViewById(R.id.tvRepair);
        tvAcceptable = Layout.findViewById(R.id.tvAcceptable);
        tvGood = Layout.findViewById(R.id.tvGood);
        tvVeryGood = Layout.findViewById(R.id.tvVeryGood);
        tvCherry = Layout.findViewById(R.id.tvCherry);

    }
}
