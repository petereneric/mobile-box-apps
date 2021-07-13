package com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Fragments.Edit.Fragment_Edit_Model_Checks;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Model_New_New;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.Objects.ModelCheck;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Keyboard;

public class Fragment_Dialog_Model_Check extends DialogFragment implements View.OnClickListener {

    // Layout
    View Layout;
    TextView tvTitle;
    TextView tvModel;
    TextView tvCheck;
    EditText etDescription;
    Switch sPositionFixed;
    ImageButton ibBack;
    ImageButton ibSave;
    ImageButton ibDelete;

    // Interfaces
    Interface_Model_New_New iModel;
    Fragment_Edit_Model_Checks iFragmentModelChecks;

    // Data
    ModelCheck oModelCheck;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        // Interfaces
        iModel = (Interface_Model_New_New)getActivity();
        iFragmentModelChecks = (Fragment_Edit_Model_Checks)getTargetFragment();

        // Data
        oModelCheck = iFragmentModelChecks.getModelCheck(getArguments().getInt("position"));

        // Layout
        Layout = inflater.inflate(R.layout.fragment_dialog_model_check, container, false);
        setLayout(Layout);

        return Layout;
    }

    @Override
    public void onStart() {
        super.onStart();
        // Keyboard
        tvCheck.requestFocus();
        Utility_Keyboard.hideKeyboardFrom(getActivity(), etDescription);

        //getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        //InputMethodManager imm = (InputMethodManager)etDescription.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        //if (imm.isActive()) imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
    }

    private void setLayout(View layout) {
        tvTitle = layout.findViewById(R.id.tvTitle);
        tvModel = layout.findViewById(R.id.tvModel);
        tvCheck = layout.findViewById(R.id.tvCheck);
        etDescription = layout.findViewById(R.id.etDescription);
        sPositionFixed = layout.findViewById(R.id.sPositionFixed);
        ibBack = layout.findViewById(R.id.ibBack);
        ibSave = layout.findViewById(R.id.ibSave);
        ibDelete = layout.findViewById(R.id.ibDelete);

        // ClickListener
        ibBack.setOnClickListener(this);
        ibSave.setOnClickListener(this);
        ibDelete.setOnClickListener(this);
        etDescription.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    tvCheck.requestFocus();
                    Utility_Keyboard.hideKeyboardFrom(getActivity(), etDescription);
                }
                return false;
            }
        });

        // Data
        tvTitle.setText(getString(R.string.edit_model_check));
        tvModel.setText(iModel.getModel().getName());
        tvCheck.setText(oModelCheck.getoCheck().getcName());
        etDescription.setText(oModelCheck.getcDescription());
        sPositionFixed.setChecked(oModelCheck.isbPositionFixed());

        InputMethodManager imm =
                (InputMethodManager)etDescription.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
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
                oModelCheck.setcDescription(etDescription.getText().toString());
                boolean bSort = !(oModelCheck.isbPositionFixed() == sPositionFixed.isChecked());
                oModelCheck.setbPositionFixed(sPositionFixed.isChecked());
                oModelCheck.update();
                iFragmentModelChecks.refresh(bSort);
                dismiss();
                break;
            case R.id.ibDelete:
                Utility_Keyboard.hideKeyboardFrom(getActivity(), etDescription);
                oModelCheck.delete();
                dismiss();
                iFragmentModelChecks.update();
                break;

        }
    }

}
