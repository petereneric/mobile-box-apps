package com.example.ericschumacher.bouncer.Fragments.Fragment_Record;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Records;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Adapter_List;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Records;
import com.example.ericschumacher.bouncer.Objects.Collection.Record;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

import java.util.ArrayList;

public class Fragment_Record_Existing extends Fragment implements View.OnClickListener, Interface_Adapter_List {

    // Layout
    View Layout;
    Button bStart;
    Button bBack;
    RecyclerView rvExisting;

    // Data
    ArrayList<Record> lRecords;
    Adapter_Records aRecords;


    // Interface
    Interface_Selection iBouncer;

    // Utility
    Utility_Network uNetwork;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Layout = inflater.inflate(R.layout.fragment_record_existing, container, false);
        setLayout();

        // Interface
        iBouncer = (Interface_Selection)getActivity();

        // Data
        uNetwork = new Utility_Network(getActivity());
        uNetwork.getUnselectedRecords(new Interface_VolleyCallback_ArrayList_Records() {
            @Override
            public void onSuccess(ArrayList<Record> records) {
                lRecords = records;
                aRecords = new Adapter_Records(getActivity(), records, Fragment_Record_Existing.this);
                rvExisting.setAdapter(aRecords);
                rvExisting.setLayoutManager(new LinearLayoutManager(getActivity()));
            }

            @Override
            public void onFailure() {
                // no unselected Records
            }
        });

        return Layout;
    }

    private void setLayout() {
        bStart = Layout.findViewById(R.id.bStart);
        bBack = Layout.findViewById(R.id.bBack);
        bBack.setVisibility(View.GONE);
        rvExisting = Layout.findViewById(R.id.rvExisting);

        bStart.setOnClickListener(this);
        bBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bStart:
                // to be deleted
                break;
            case R.id.bBack:
                //iBouncer.showFragmentRecordMenu();
                break;
        }
    }

    @Override
    public void onItemSelected(int position) {
        iBouncer.setRecord(lRecords.get(position));
    }
}
