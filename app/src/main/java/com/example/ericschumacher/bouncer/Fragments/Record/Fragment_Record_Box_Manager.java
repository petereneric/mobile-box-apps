package com.example.ericschumacher.bouncer.Fragments.Record;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_Record_Box_Manager extends Fragment_Record {
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        // Visibility
        ivPrint.setVisibility(View.GONE);
        ivAdd.setVisibility(View.GONE);
        ivPause.setVisibility(View.GONE);
        ivDelete.setVisibility(View.GONE);
    }
}
