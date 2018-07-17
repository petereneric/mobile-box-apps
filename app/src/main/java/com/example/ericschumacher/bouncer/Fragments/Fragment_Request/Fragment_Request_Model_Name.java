package com.example.ericschumacher.bouncer.Fragments.Fragment_Request;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Model;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Input;
import com.example.ericschumacher.bouncer.Objects.Object_SearchResult;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 23.05.2018.
 */

public class Fragment_Request_Model_Name extends Fragment_Request_Input {

    // Interface
    Interface_Model iModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        iModel = (Interface_Model)getActivity();

        etInput.setFocusableInTouchMode(true);
        etInput.requestFocus();

        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                uNetwork.getMatchingModels(editable.toString(), new Interface_VolleyCallback_ArrayList_Input() {
                    @Override
                    public void onSuccess(ArrayList<Object_SearchResult> list) {
                        aSearchResults.update(list);
                    }
                });
            }
        });

        return layout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_commit:
                iModel.returnName(etInput.getText().toString());
                closeKeyboard();
                break;
            case R.id.et_name:
                Log.i("Wenigstens", "das");
                etInput.setFocusableInTouchMode(true);
                etInput.requestFocus();
        }
    }

}
