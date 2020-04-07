package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_SimpleText;
import com.example.ericschumacher.bouncer.R;

public class Fragment_SimpleText extends Fragment implements View.OnClickListener, View.OnLongClickListener {

    // vLayout
    View vLayout;
    TextView tvText;

    // Data
    String cText;

    // Interface
    Interface_Fragment_SimpleText iFragmentSimpleText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Data
        cText = getArguments().getString(Constants_Intern.TEXT_VALUE);

        // vLayout
        setLayout(inflater, container);

        // Interface
        iFragmentSimpleText = (Interface_Fragment_SimpleText)getActivity();

        return vLayout;
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(R.layout.fragment_simple_text, container, false);
        tvText = vLayout.findViewById(R.id.tvText);

        tvText.setText(cText);

        tvText.setOnClickListener(this);
        tvText.setOnLongClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvText:
                iFragmentSimpleText.onClick(getTag());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.tvText:
                iFragmentSimpleText.onLongClick(getTag());
                return true;
        }
        return false;
    }
}
