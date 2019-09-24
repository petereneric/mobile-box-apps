package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.R;

public class Fragment_Table extends Fragment {

    // Layout
    View Layout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Layout
        setLayout(inflater, container);


        return Layout;
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        Layout = inflater.inflate(R.layout.fragment_table, container, false);
    }
}
