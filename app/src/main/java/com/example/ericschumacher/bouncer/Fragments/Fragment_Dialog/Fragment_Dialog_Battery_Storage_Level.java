package com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Others.Fragment_Batteries;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Dialog_Battery_Storage_Level extends DialogFragment implements View.OnClickListener {

    // Layout
    View Layout;
    RadioGroup rgStorageLevel;
    RadioButton rbMainStorage;
    RadioButton rbReserveStorage;
    RadioButton rbNoMoreStorage;
    RadioButton rbNoStorage;
    Button bCancel;
    Button bApply;

    // Data
    int nPosition;
    int lStorage;

    // Interface
    Fragment_Batteries iFragmentBatteries;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Data
        nPosition = getArguments().getInt(Constants_Intern.POSITION_BATTERY);
        lStorage = getArguments().getInt(Constants_Intern.STORAGE_LEVEL);

        // Interface
        iFragmentBatteries = (Fragment_Batteries)getTargetFragment();

        // Layout
        Layout = inflater.inflate(R.layout.fragment_dialog_battery_storage_level, container, false);
        rgStorageLevel = Layout.findViewById(R.id.rgStorageLevel);
        rbMainStorage = Layout.findViewById(R.id.rbMainStorage);
        rbReserveStorage = Layout.findViewById(R.id.rbReserveStorage);
        rbNoMoreStorage = Layout.findViewById(R.id.rbNoMoreStorage);
        rbNoStorage = Layout.findViewById(R.id.rbNoStorage);
        bCancel = Layout.findViewById(R.id.bCancel);
        bApply = Layout.findViewById(R.id.bApply);

        bApply.setOnClickListener(this);
        bCancel.setOnClickListener(this);


        rbMainStorage.setOnClickListener(this);
        rbReserveStorage.setOnClickListener(this);
        rbNoMoreStorage.setOnClickListener(this);
        rbNoStorage.setOnClickListener(this);

        switch (lStorage) {
            case Constants_Intern.MAIN_STORAGE:
                rbMainStorage.setChecked(true);
                break;
            case Constants_Intern.RESERVE_STORAGE:
                rbReserveStorage.setChecked(true);
                break;
            case Constants_Intern.NO_MORE_STORAGE:
                rbNoMoreStorage.setChecked(true);
                break;
            case Constants_Intern.NO_STORAGE:
                rbNoStorage.setChecked(true);
                break;
        }

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);

        return Layout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rbMainStorage:
                lStorage = Constants_Intern.MAIN_STORAGE;
                break;
            case R.id.rbReserveStorage:
                lStorage = Constants_Intern.RESERVE_STORAGE;
                break;
            case R.id.rbNoMoreStorage:
                lStorage = Constants_Intern.NO_MORE_STORAGE;
                break;
            case R.id.rbNoStorage:
                lStorage = Constants_Intern.NO_STORAGE;
                break;
            case R.id.bCancel:
                this.dismiss();
                break;
            case R.id.bApply:
                iFragmentBatteries.updateStorageLevel(nPosition, lStorage);
                this.dismiss();
                break;
        }
    }
}
