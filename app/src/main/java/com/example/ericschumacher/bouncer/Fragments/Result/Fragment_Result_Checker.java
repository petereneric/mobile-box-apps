package com.example.ericschumacher.bouncer.Fragments.Result;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Checker;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Result_Checker extends Fragment_Result implements Interface_Update {

    // Interfaces
    Interface_Fragment_Checker iChecker;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void setLayout(LayoutInflater inflater, @Nullable ViewGroup container) {
        super.setLayout(inflater, container);

        tvInteractionTitle.setVisibility(View.VISIBLE);
        tvInteractionTitle.setText(getString(R.string.handler));
    }

    @Override
    public void setScreen() {
        // Check if there is diagnose that can be shown
        if (iChecker != null) {
            Log.i("hier lk", "kk");
            if (iChecker.getDiagnoses().size() == 0) {
                // No diagnoses
                Log.i("diagnose", "nlskdjf");
                llInformation.setVisibility(View.VISIBLE);
                tvInformation.setText(getString(R.string.no_diagnoses_created_yet));

            } else {
                if (iChecker.getDiagnoses().get(0).isbFinished()) {
                    // Last diagnose is finished
                    Log.i("diagnose finished", "nlskdjf");
                    llInformation.setVisibility(View.GONE);

                } else {
                    // Last diagnose not finished
                    Log.i("diagnose not finished", "nlskdjf");
                    llInformation.setVisibility(View.VISIBLE);
                    tvInformation.setText(getString(R.string.please_finish_diagnose));
                }
            }
        }
    }

    @Override
    public void update() {
        setScreen();
    }

    @Override
    public void setInterface() {
        super.setInterface();
        iChecker = (Interface_Fragment_Checker)getTargetFragment();
    }

    @Override
    public void click() {
        if (iChecker.getDiagnoses().size() > 0 && iChecker.getDiagnoses().get(0).isbFinished()) {
            iResult.returnResult(getTag());
        } else {
            iChecker.showTab(0);
        }
    }
}
