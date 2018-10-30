package com.example.ericschumacher.bouncer.Fragments.Fragment_Request;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.R;

public class Fragment_Input_Simple extends Fragment implements View.OnClickListener {

    // Layout
    public View Layout;
    public TextView tvSimple;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i("Fragment_Iput_Simple", "onCreateView");
        Layout = inflater.inflate(R.layout.fragment_input_simple, container, false);
        setLayout();


        return Layout;
    }

    private void setLayout() {
        tvSimple = Layout.findViewById(R.id.tvSimple);
        tvSimple.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

    }
}
