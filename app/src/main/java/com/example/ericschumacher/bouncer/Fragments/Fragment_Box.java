package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Fragments.Parent.Fragment_Object;
import com.example.ericschumacher.bouncer.Objects.Collection.Box;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_DateTime;

public class Fragment_Box extends Fragment_Object implements View.OnClickListener {

    // Layout

    TableRow trIdBox;
    TableRow trDateCreation;
    TableRow trDateLastUpdate;
    TextView tvIdBox;
    TextView tvDateCreation;
    TextView tvDateLastUpdate;

    // Object
    Box oBox;

    // Interface
    Interface_Fragment_Box iFragmentBox;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Interface
        iFragmentBox = (Interface_Fragment_Box)getActivity();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        trDateCreation = vLayout.findViewById(R.id.trDateCreation);
        trDateLastUpdate = vLayout.findViewById(R.id.trDateLastUpdate);
        trIdBox = vLayout.findViewById(R.id.trIdBox);

        tvIdBox = vLayout.findViewById(R.id.tvIdBox);
        tvDateCreation = vLayout.findViewById(R.id.tvDateCreation);
        tvDateLastUpdate = vLayout.findViewById(R.id.tvDateLastUpdate);

        // Data
        tvTitle.setText(getString(R.string.box));

        // Visibility
        ivPrint.setVisibility(View.GONE);
        ivDone.setVisibility(View.GONE);
        ivPause.setVisibility(View.GONE);
        ivClear.setVisibility(View.GONE);
    }

    public int getIdLayout() {
        return R.layout.fragment_box;
    }

    public void updateLayout() {
        oBox = iFragmentBox.getBox();

        if (oBox != null) {
            tvIdBox.setText(Integer.toString(oBox.getkId()));
            tvDateCreation.setText(Utility_DateTime.dateToString(oBox.getdCreation()));
            tvDateLastUpdate.setText(Utility_DateTime.dateToString(oBox.getdLastUpdate()));

            if (oBox.getoRecord() == null) {
                ivAdd.setVisibility(View.VISIBLE);
            } else {
                ivAdd.setVisibility(View.GONE);
            }
        }
    }



    public interface Interface_Fragment_Box{
        Box getBox();
    }
}
