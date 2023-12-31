package com.example.ericschumacher.bouncer.Fragments.Others;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Activities.Manager.Activity_Device;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

public class Fragment_Verify_Article extends Fragment implements View.OnClickListener {

    // Layout
    View vLayout;
    TextView tvTitle;
    RelativeLayout rlMain;
    Button bCommit;
    Button bCommitAndPrint;
    Button bReclean;
    Button bDefect;

    // Variables
    boolean bFeatureChanged;

    // Interface
    Interface_Verify_Article iVerifyArticle;

    // Instances
    Activity_Device activityDevice;

    // Connection
    Volley_Connection cVolley;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Instances
        activityDevice = (Activity_Device)getActivity();

        // Connection
        cVolley = new Volley_Connection(getActivity());

        // Variables
        bFeatureChanged = getArguments().getBoolean(Constants_Intern.BOOLEAN_FEATURE_CHANGED);

        // vLayout
        setLayout(inflater, container);
        update();

        // Interface
        iVerifyArticle = (Interface_Verify_Article)getActivity();

        return vLayout;
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        // Layout
        vLayout = inflater.inflate(R.layout.fragment_verify_sku, container, false);
        tvTitle = vLayout.findViewById(R.id.tvTitle);
        rlMain = vLayout.findViewById(R.id.rlMain);
        bCommit = vLayout.findViewById(R.id.bCommit);
        bCommitAndPrint = vLayout.findViewById(R.id.bCommitAndPrint);
        bReclean = vLayout.findViewById(R.id.bReclean);
        bDefect = vLayout.findViewById(R.id.bDefect);

        // Data
        tvTitle.setText(getString(R.string.verify_article));

        // OnClickListener
        bCommit.setOnClickListener(this);
        bCommitAndPrint.setOnClickListener(this);
        bReclean.setOnClickListener(this);
        bDefect.setOnClickListener(this);
    }

    public void update() {
        if (bFeatureChanged) {
            bCommit.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bCommit:
                iVerifyArticle.returnVerifyArticle(getTag(), false);
                break;
            case R.id.bCommitAndPrint:
                iVerifyArticle.returnVerifyArticle(getTag(), true);
                break;
            case R.id.bReclean:
                iVerifyArticle.errorVerifyArticle(getTag(), Constants_Intern.TYPE_ERROR_RECLEAN);
                break;
            case R.id.bDefect:
                iVerifyArticle.errorVerifyArticle(getTag(), Constants_Intern.TYPE_ERROR_DEFECT);
                break;
        }
    }

    public interface Interface_Verify_Article {
        void returnVerifyArticle(String cTag, boolean bPrint);
        void errorVerifyArticle(String cTag, int tError);
    }
}
