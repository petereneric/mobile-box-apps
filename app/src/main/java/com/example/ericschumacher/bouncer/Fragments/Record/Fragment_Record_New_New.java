package com.example.ericschumacher.bouncer.Fragments.Record;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Parent.Fragment_Object;
import com.example.ericschumacher.bouncer.Objects.Collection.Record;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Record_New_New extends Fragment_Object implements View.OnClickListener {

    TextView tvIdCollector;
    TextView tvNameCollector;
    TextView tvDateCreation;
    TextView tvDateLastUpdate;
    TextView tvRecycling;
    TextView tvReuse;
    TextView tvRepair;
    TextView tvTotal;

    // Interface
    Interface_Fragment_Record iFragmentRecord;

    // Object
    Record oRecord;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Interface
        iFragmentRecord = (Interface_Fragment_Record) getActivity();

        // Object
        oRecord = iFragmentRecord.getRecord();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        tvIdCollector = vLayout.findViewById(R.id.tvIdCollector);
        tvNameCollector = vLayout.findViewById(R.id.tvNameCollector);
        tvDateCreation = vLayout.findViewById(R.id.tvDateCreation);
        tvDateLastUpdate = vLayout.findViewById(R.id.tvDateLastUpdate);
        tvRecycling = vLayout.findViewById(R.id.tvRecycling);
        tvReuse = vLayout.findViewById(R.id.tvReuse);
        tvRepair = vLayout.findViewById(R.id.tvRepair);
        tvTotal = vLayout.findViewById(R.id.tvTotal);

        // Data
        tvTitle.setText(getString(R.string.record));

        // Visibility
        ivPrint.setVisibility(View.GONE);
        ivAdd.setVisibility(View.GONE);
        ivPause.setVisibility(View.GONE);
        ivClear.setVisibility(View.GONE);
    }

    public int getIdLayout() {
        return R.layout.fragment_record_new_new;
    }

    public void updateLayout() {
        oRecord = iFragmentRecord.getRecord();

        if (oRecord != null) {
            tvIdCollector.setText(Integer.toString(oRecord.getId()));
            if (oRecord.getoCollector() != null && oRecord.getoCollector().getName() != null) {
                tvNameCollector.setText(oRecord.getoCollector().getName());
            } else {
                tvNameCollector.setText(getString(R.string.free_record));
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
            tvTotal.setText(Integer.toString(oRecord.getnRecycling() + oRecord.getnReuse()));
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
            case R.id.ivDone:
                AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
                alertDialog.setTitle(getString(R.string.commit));
                alertDialog.setMessage(getString(R.string.message_commit_done_record));
                alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.yes),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                iFragmentObjectMenu.returnMenu(Constants_Intern.TYPE_ACTION_MENU_DONE, getTag());
                            }
                        });
                alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.no),
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();
                return;
        }
        super.onClick(view);
    }


    public interface Interface_Fragment_Record extends Interface_Fragment_Object_Menu {
        Record getRecord();
    }
}
