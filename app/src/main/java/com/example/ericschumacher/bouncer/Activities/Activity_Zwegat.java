package com.example.ericschumacher.bouncer.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.ericschumacher.bouncer.Fragments.Fragment_Zwegat;
import com.example.ericschumacher.bouncer.R;

public class Activity_Zwegat extends AppCompatActivity {

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
