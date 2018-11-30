package com.example.ericschumacher.bouncer.Fragments.Fragment_Turing;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.R;

public class Fragment_List extends Fragment {

    // Layout
    View Layout;
    RecyclerView RecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        setLayout(inflater, container);


        return Layout;
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        Layout = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView = Layout.findViewById(R.id.RecyclerView);

        RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
