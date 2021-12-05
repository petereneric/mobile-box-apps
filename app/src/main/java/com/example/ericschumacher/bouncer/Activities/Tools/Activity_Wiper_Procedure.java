package com.example.ericschumacher.bouncer.Activities.Tools;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Activities.Activity_Authentication;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

public class Activity_Wiper_Procedure extends Activity_Authentication implements View.OnClickListener, TextWatcher {

    // VALUES & VARIABLES

    // Layout
    Toolbar vToolbar;
    TextView tvSearchType;
    ImageView ivSearchAction;
    EditText etSearch;
    FrameLayout flInteraction;
    FloatingActionButton vFab;

    // Connection
    Volley_Connection cVolley;


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LIFE-CYCLE

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Layout
        setLayout();
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PRIVATE

    private void setLayout() {
        setContentView(R.layout.activity_wiper_procedure);

        vToolbar = findViewById(R.id.vToolbar);
        tvSearchType = findViewById(R.id.tvSearchType);
        ivSearchAction = findViewById(R.id.ivAction);
        etSearch = findViewById(R.id.etSearch);
        flInteraction = findViewById(R.id.flInteraction);
        vFab = findViewById(R.id.vFab);

        // Listener
        ivSearchAction.setOnClickListener(this);
        vFab.setOnClickListener(this);
    }

    private void onClick_SearchAction() {

    }

    private void onClick_Fab() {

    }

    private void afterTextChanged(String text) {

    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LISTENER

    // Click
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivAction:
                onClick_SearchAction();
                break;
            case R.id.vFab:
                onClick_Fab();
                break;
        }
    }

    // Text
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        afterTextChanged(editable.toString());
    }
}
