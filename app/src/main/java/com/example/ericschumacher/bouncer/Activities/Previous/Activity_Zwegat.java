package com.example.ericschumacher.bouncer.Activities.Previous;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.example.ericschumacher.bouncer.Activities.Activity_Authentication;
import com.example.ericschumacher.bouncer.Fragments.Old.Fragment_Zwegat;
import com.example.ericschumacher.bouncer.R;

public class  Activity_Zwegat extends Activity_Authentication {

    android.support.v4.app.FragmentManager FragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Layout
        setLayout();

        // Fragment
        Fragment_Zwegat fZwegat = new Fragment_Zwegat();
        FragmentManager = getSupportFragmentManager();
        FragmentManager.beginTransaction().replace(R.id.flContainer, fZwegat, "FRAGMENT_ZWEGAT").commit();
    }

    private void setLayout() {
        setContentView(R.layout.activity_zwegat);
    }
}
