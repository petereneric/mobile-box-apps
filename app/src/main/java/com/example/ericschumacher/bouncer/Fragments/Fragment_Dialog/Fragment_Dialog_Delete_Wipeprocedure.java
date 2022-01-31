package com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.R;

public class Fragment_Dialog_Delete_Wipeprocedure extends Fragment_Dialog_New {

    @Override
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);
        tvTitle.setText(getString(R.string.delete));
        tvSubtitle.setText(getString(R.string.wipeprocedure));
        tvContent.setText(getString(R.string.really_delete_content));

        // Visibility
        clButtons.setVisibility(View.GONE);
        tvNeutral.setVisibility(View.GONE);
    }
}
