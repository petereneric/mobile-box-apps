package com.example.ericschumacher.bouncer.Fragments.Print;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Print extends Fragment implements View.OnClickListener {

    // vLayout
    View vLayout;
    TextView tvTitle;
    TextView tvPrintDevice;
    TextView tvPrintArticle;
    TextView tvPrintBattery;
    TextView tvPrintBackcover;

    // Activity
    Interface_Print iPrint;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Layout
        setLayout(inflater, container);

        // Interface
        iPrint = (Interface_Print)getActivity();

        return vLayout;
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        // vLayout
        vLayout = inflater.inflate(getIdLayout(), container, false);
        tvTitle = vLayout.findViewById(R.id.tvTitle);
        tvPrintDevice = vLayout.findViewById(R.id.tvPrintDevice);
        tvPrintArticle = vLayout.findViewById(R.id.tvPrintArticle);
        tvPrintBattery = vLayout.findViewById(R.id.tvPrintBattery);
        tvPrintBackcover = vLayout.findViewById(R.id.tvPrintBackcover);

        // Data
        tvTitle.setText(getString(R.string.print));

        // OnClickListener
        tvPrintDevice.setOnClickListener(this);
        tvPrintArticle.setOnClickListener(this);
        tvPrintBattery.setOnClickListener(this);
        tvPrintBackcover.setOnClickListener(this);
    }

    public int getIdLayout() {
        return R.layout.fragment_print;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvPrintDevice:
                iPrint.returnPrint(getTag(), Constants_Intern.TYPE_PRINT_DEVICE);
                break;
            case R.id.tvPrintArticle:
                iPrint.returnPrint(getTag(), Constants_Intern.TYPE_PRINT_ARTICLE);
                break;
            case R.id.tvPrintBattery:
                iPrint.returnPrint(getTag(), Constants_Intern.TYPE_PRINT_BATTERY);
                break;
            case R.id.tvPrintBackcover:
                iPrint.returnPrint(getTag(), Constants_Intern.TYPE_PRINT_BACKCOVER);
                break;
        }
    }

    public interface Interface_Print {
        void returnPrint(String cTag, int tPrint);
    }
}
