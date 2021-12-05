package com.example.ericschumacher.bouncer.Fragments.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.R;

public class Fragment_List_Wipe_Procedure extends Fragment {

    // VALUES & VARIABLES

    // Layout
    View vLayout;
    TextView tvTitle;
    RecyclerView rvData;

    // Adapter


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LIFE-CYCLE


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Layout
        setLayout(inflater, container);

        return vLayout;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PRIVATE

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(R.layout.fragment_list_new, container, false);

        tvTitle = vLayout.findViewById(R.id.tvTitle);
        rvData = vLayout.findViewById(R.id.rvData);
    }
}
