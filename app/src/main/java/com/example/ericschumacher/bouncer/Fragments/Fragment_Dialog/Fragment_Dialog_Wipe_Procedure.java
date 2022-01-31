package com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Fragments.List.Fragment_List_Wipe_Procedure;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Wipeprocedure;
import com.example.ericschumacher.bouncer.Objects.Wipe_Procedure;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Keyboard;

public class Fragment_Dialog_Wipe_Procedure extends DialogFragment implements View.OnClickListener {

    // Layout
    View Layout;
    TextView tvTitle;
    TextView tvWipeprocedure;
    TextView tvWipe;
    EditText etDescription;
    ImageButton ibBack;
    ImageButton ibSave;
    ImageButton ibDelete;

    // Interfaces
    Interface_Wipeprocedure iWipeprocedure;

    // Data
    Wipe_Procedure oWipeProcedure;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Interfaces
        iWipeprocedure = (Interface_Wipeprocedure) getTargetFragment();

        // Data
        oWipeProcedure = iWipeprocedure.getWipeprocedure().getlWipeProcedure().get(getArguments().getInt("position"));

        // Layout
        Layout = inflater.inflate(R.layout.fragment_dialog_wipe_procedure, container, false);
        setLayout(Layout);

        // Keyboard
        tvWipe.requestFocus();
        Utility_Keyboard.hideKeyboardFrom(getActivity(), etDescription);

        return Layout;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    private void setLayout(View layout) {
        tvTitle = layout.findViewById(R.id.tvTitle);
        tvWipeprocedure = layout.findViewById(R.id.tvWipeprocedure);
        tvWipe = layout.findViewById(R.id.tvWipe);
        etDescription = layout.findViewById(R.id.etDescription);
        ibBack = layout.findViewById(R.id.ibBack);
        ibSave = layout.findViewById(R.id.ibSave);
        ibDelete = layout.findViewById(R.id.ibDelete);
        ibDelete.setVisibility(View.GONE);


        // ClickListener
        ibBack.setOnClickListener(this);
        ibSave.setOnClickListener(this);
        etDescription.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    tvWipe.requestFocus();
                    Utility_Keyboard.hideKeyboardFrom(getActivity(), etDescription);
                }
                return false;
            }
        });

        // Data
        tvTitle.setText(getString(R.string.edit_wipe_procedure));
        tvWipe.setText(oWipeProcedure.getoWipe().getcName());
        tvWipeprocedure.setText(iWipeprocedure.getWipeprocedure().getcName());
        etDescription.setText(oWipeProcedure.getcDescription());

        InputMethodManager imm = (InputMethodManager) etDescription.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm.isActive())
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ibBack:
                Utility_Keyboard.hideKeyboardFrom(getActivity(), etDescription);
                this.dismiss();
                break;
            case R.id.ibSave:
                Utility_Keyboard.hideKeyboardFrom(getActivity(), etDescription);
                oWipeProcedure.setcDescription(etDescription.getText().toString());
                ((Fragment_List_Wipe_Procedure)getTargetFragment()).update();
                dismiss();
                break;
        }
    }
}
