package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Collection.Record;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Record_New_New extends Fragment implements View.OnClickListener {

    // Layout
    View vLayout;

    TextView tvTitle;
    TextView tvNameCollector;
    TextView tvDateCreation;
    TextView tvDateLastUpdate;
    TextView tvRecycling;
    TextView tvReuse;
    TextView tvRepair;
    TextView tvTotal;
    Button bPause;
    Button bFinish;

    // Interface
    Interface_Fragment_Record iFragmentRecord;

    // Object
    Record oRecord;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Interface
        iFragmentRecord = (Interface_Fragment_Record)getActivity();

        // Object
        oRecord = iFragmentRecord.getRecord();

        // Layout
        setLayout(inflater, container);

        return vLayout;
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        // Inflate
        vLayout = inflater.inflate(R.layout.fragment_record_new_new, container, false);

        tvTitle = vLayout.findViewById(R.id.tvTitle);
        tvNameCollector = vLayout.findViewById(R.id.tvNameCollector);
        tvDateCreation = vLayout.findViewById(R.id.tvDateCreation);
        tvDateLastUpdate = vLayout.findViewById(R.id.tvDateLastUpdate);
        tvRecycling = vLayout.findViewById(R.id.tvRecycling);
        tvReuse = vLayout.findViewById(R.id.tvReuse);
        tvRepair = vLayout.findViewById(R.id.tvRepair);
        tvTotal = vLayout.findViewById(R.id.tvTotal);
        bPause = vLayout.findViewById(R.id.bPause);
        bFinish = vLayout.findViewById(R.id.bFinish);

        // Data
        tvTitle.setText(getString(R.string.record));

        // OnClickListener
        bPause.setOnClickListener(this);
        bFinish.setOnClickListener(this);
    }

    public void updateLayout() {
        oRecord = iFragmentRecord.getRecord();

        if (oRecord != null) {
            if (oRecord.getoCollector().getName() != null) {
                tvNameCollector.setText(oRecord.getoCollector().getName());
            } else {
                tvNameCollector.setText(getString(R.string.unknown));
            }
            if (oRecord.getDateCreation() != null) {
                tvDateCreation.setText(oRecord.getDateCreation());
            } else {
                tvDateCreation.setText(getString(R.string.unknown));
            }
            if (oRecord.getDateLastUpdate() != null) {
                tvDateLastUpdate.setText(oRecord.getDateLastUpdate());
            } else {
                tvDateLastUpdate.setText(getString(R.string.unknown));
            }
            tvRecycling.setText(Integer.toString(oRecord.getnRecycling()));
            tvReuse.setText(Integer.toString(oRecord.getnReuse()));
            tvTotal.setText(Integer.toString(oRecord.getnRecycling()+oRecord.getnReuse()));
        } else {
            tvNameCollector.setText(getString(R.string.unknown));
            tvDateLastUpdate.setText(getString(R.string.unknown));
            tvRecycling.setText(getString(R.string.unknown));
            tvReuse.setText(getString(R.string.unknown));
            tvTotal.setText(getString(R.string.unknown));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bPause:
                iFragmentRecord.returnRecord(getTag(), Constants_Intern.ACTION_FRAGMENT_RECORD_PAUSE);
                break;
            case R.id.bFinish:
                iFragmentRecord.returnRecord(getTag(), Constants_Intern.ACTION_FRAGMENT_RECORD_FINISH);
                break;
        }
    }

    public interface Interface_Fragment_Record {
        Record getRecord();
        void returnRecord(String cTag, String cAction);
    }
}
