package com.example.ericschumacher.bouncer.Fragments.Fragment_Record;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Record_Menu extends Fragment implements View.OnClickListener {

    // Layout
    View Layout;
    Button bNew;
    Button bExisting;
    Button bUnknown;

    // Interface
    Interface_Selection iBouncer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Layout = inflater.inflate(R.layout.fragment_menu_record, container, false);
        setLayout();

        // Interface
        iBouncer = (Interface_Selection)getActivity();


        return Layout;
    }

    private void setLayout() {
        bNew = Layout.findViewById(R.id.bNewRedord);
        bExisting = Layout.findViewById(R.id.bExistingRecord);
        bUnknown = Layout.findViewById(R.id.bUnknownSource);

        bNew.setOnClickListener(this);
        bExisting.setOnClickListener(this);
        bUnknown.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bNewRedord:
                iBouncer.showFragmentRecordNew();
                break;
            case R.id.bExistingRecord:
                iBouncer.showFragmentRecordExisting();
                break;
            case R.id.bUnknownSource:
                // needs to be deleted
                break;
        }
    }
}