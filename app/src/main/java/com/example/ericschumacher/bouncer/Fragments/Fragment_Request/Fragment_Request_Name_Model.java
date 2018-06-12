package com.example.ericschumacher.bouncer.Fragments.Fragment_Request;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.ericschumacher.bouncer.Adapter.Adapter_SearchResults;
import com.example.ericschumacher.bouncer.Interfaces.Interface_SearchResults;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Input;
import com.example.ericschumacher.bouncer.Objects.Object_SearchResult;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 23.05.2018.
 */

public class Fragment_Request_Name_Model extends Fragment_Request_Input {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

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
                iSelection.checkName(etInput.getText().toString());
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(etInput.getWindowToken(), 0);
                break;
            case R.id.et_name:
                Log.i("Wenigstens", "das");
                etInput.setFocusableInTouchMode(true);
                etInput.requestFocus();
        }
    }

}
