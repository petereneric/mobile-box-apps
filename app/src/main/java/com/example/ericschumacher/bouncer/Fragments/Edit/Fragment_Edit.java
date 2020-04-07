package com.example.ericschumacher.bouncer.Fragments.Edit;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

public class Fragment_Edit extends Fragment implements View.OnClickListener {

    // Data
    Bundle bData;
    String cTitle;

    // vLayout
    View vLayout;
    TextView tvTitle;
    public Button bCommit;
    RecyclerView rvList;

    // Connection
    Volley_Connection cVolley;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        // Connection
        cVolley = new Volley_Connection(getActivity());

        // Data
        bData = getArguments();
        cTitle = bData.getString(Constants_Intern.TITLE);

        // vLayout
        setLayout(inflater, container);

        return vLayout;
    }


    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        // vLayout
        vLayout = inflater.inflate(R.layout.fragment_edit, container, false);
        tvTitle = vLayout.findViewById(R.id.tvTitle);
        bCommit = vLayout.findViewById(R.id.bCommit);
        rvList = vLayout.findViewById(R.id.rvData);

        // RecyclerView
        rvList.setLayoutManager(new GridLayoutManager(getActivity(), 4));

        // Data
        tvTitle.setText(cTitle);

        // ClickListener
        bCommit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bCommit:
                onCommit();
                break;
        }
    }

    public void onCommit() {
    }
}
