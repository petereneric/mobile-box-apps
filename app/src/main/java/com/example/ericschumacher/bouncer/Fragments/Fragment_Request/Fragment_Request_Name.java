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

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_DeviceManager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Object_SearchResult;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Keyboard;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 23.05.2018.
 */

public class Fragment_Request_Name extends Fragment_Request_Input {

    // Interface
    Interface_DeviceManager iManager;

    // Connection
    Volley_Connection cVolley;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = super.onCreateView(inflater, container, savedInstanceState);

        iManager = (Interface_DeviceManager)getActivity();

        // Connection
        cVolley = new Volley_Connection(getContext());

        Utility_Keyboard.openKeyboard(getActivity(), etInput);
        InputMethodManager imgr = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imgr.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);


        ivHelp.setVisibility(View.GONE);

        etInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODELS_BY_NAMEPART + editable.toString(), null, new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) throws JSONException {
                        if (oJson.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                            ArrayList<Object_SearchResult> list = new ArrayList<>();
                            JSONArray jsonArray = oJson.getJSONArray(Constants_Extern.LIST_MODELS);
                            for (int i = 0; i<jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                if (!jsonObject.isNull(Constants_Extern.NAME_MODEL)) {
                                    list.add(new Object_SearchResult(jsonObject.getInt(Constants_Extern.ID_MODEL), jsonObject.getString(Constants_Extern.NAME_MODEL)));
                                } else {
                                    Log.i("SWWWWH", "kj");
                                }
                            }
                            aSearchResults.update(list);
                        }
                    }
                });
                /*
                uNetwork.getMatchingModels(editable.toString(), new Interface_VolleyCallback_ArrayList_Input() {
                    @Override
                    public void onSuccess(ArrayList<Object_SearchResult> list) {
                        aSearchResults.update(list);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
                */
            }
        });

        return layout;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.b_commit:
                iManager.returnName(etInput.getText().toString(), 0);
                closeKeyboard();
                break;
            case R.id.et_name:
                Log.i("Wenigstens", "das");
                etInput.setFocusableInTouchMode(true);
                etInput.requestFocus();
        }
    }

    @Override
    public void itemSelected(Object_SearchResult oSearchResult) {
        super.itemSelected(oSearchResult);
        iManager.returnName(etInput.getText().toString(), oSearchResult.getId());
        closeKeyboard();
    }
}
