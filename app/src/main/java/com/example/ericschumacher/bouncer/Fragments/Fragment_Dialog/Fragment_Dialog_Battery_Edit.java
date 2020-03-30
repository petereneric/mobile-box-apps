package com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Batteries;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Dialog_Battery_Edit extends DialogFragment implements View.OnClickListener {

    // Layout
    View Layout;
    EditText etName;
    Button bCancel;
    Button bApply;

    // Data
    int nPosition;
    String cName;

    // Interface
    Fragment_Batteries iFragmentBatteries;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Data
        nPosition = getArguments().getInt(Constants_Intern.POSITION_BATTERY);
        cName = getArguments().getString(Constants_Intern.NAME_BATTERY);


        // Interface
        iFragmentBatteries = (Fragment_Batteries)getTargetFragment();

        // Layout
        Layout = inflater.inflate(R.layout.fragment_dialog_battery_edit, container, false);
        etName = Layout.findViewById(R.id.etName);
        bCancel = Layout.findViewById(R.id.bCancel);
        bApply = Layout.findViewById(R.id.bApply);

        bApply.setOnClickListener(this);
        bCancel.setOnClickListener(this);

        etName.setText(cName);

        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);


        return Layout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.bCancel:
                this.dismiss();
                break;
            case R.id.bApply:
                if (!etName.getText().toString().equals(cName)) {
                    iFragmentBatteries.editName(nPosition, etName.getText().toString());
                }
                this.dismiss();
                break;
        }
    }
}
