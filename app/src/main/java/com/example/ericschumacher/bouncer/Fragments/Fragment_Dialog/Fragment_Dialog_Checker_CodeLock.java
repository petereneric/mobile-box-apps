package com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.R;

public class Fragment_Dialog_Checker_CodeLock extends Fragment_Dialog {

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return vLayout;
    }

    @Override
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        // Data
        tvHeader.setText(getString(R.string.code_lock));
        tvContent.setText(getText(R.string.dialog_code_lock_content));
        tvButtonOne.setText(getString(R.string.reset));
        tvButtonTwo.setText(getString(R.string.failed));

        // Height
        setHeightContent(1600);
    }
}
