package com.example.ericschumacher.bouncer.Fragments.List;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Activities.Activity_Juicer_New;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Keyboard;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

public class Fragment_List_Juicer extends Fragment implements View.OnClickListener, TextWatcher {

    // Layout
    View vLayout;
    TextView tvSearchType;
    EditText etSearch;
    ImageView ivCommit;
    View vDividerLeft;

    // Activity
    Activity_Juicer_New activityJuicer;

    // Connection
    Volley_Connection cVolley;

    // SharedPreferences
    android.content.SharedPreferences SharedPreferences;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Layout
        setLayout(inflater, container);

        // Activity
        activityJuicer = (Activity_Juicer_New)getActivity();

        // Connection
        cVolley = new Volley_Connection(getActivity());

        // SharedPreferences
        SharedPreferences = getActivity().getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0);

        return vLayout;
    }

    private void setLayout(LayoutInflater inflater, @Nullable ViewGroup container) {
        // Initiate
        vLayout = inflater.inflate(R.layout.fragment_list_juicer, container, false);
        tvSearchType = vLayout.findViewById(R.id.tvSearchType);
        etSearch = vLayout.findViewById(R.id.etSearch);
        ivCommit = vLayout.findViewById(R.id.ivCommit);
        vDividerLeft = vLayout.findViewById(R.id.vDivicerLeft);

        // OnClickListener & TextWatcher
        tvSearchType.setOnClickListener(this);
        ivCommit.setOnClickListener(this);
        etSearch.setOnClickListener(this);
        etSearch.addTextChangedListener(this);
    }

    public void update() {
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_DEVICES_BY_ID_MODEL_COLOR_SHAPE_FOR_JUICER + activityJuicer.getStock()+"/"+activityJuicer.getIdModelColorShape(), null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                oJson.toString();
                if (Volley_Connection.successfulResponse(oJson)) {

                }
            }
        });
    }

    public void updateLayout() {
        // SearchType
        switch (SharedPreferences.getInt(Constants_Intern.SEARCH_JUICER_TYPE, 0)) {
            case Constants_Intern.SEARCH_JUICER_TYPE_ID_DEVICE:
                tvSearchType.setText(getString(R.string.id_device));
                etSearch.setHint(getString(R.string.enter_scan_id_device));
                etSearch.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                etSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                break;
            case Constants_Intern.SEARCH_JUICER_TYPE_IMEI_DEVICE:
                tvSearchType.setText(getString(R.string.imei));
                etSearch.setHint(getString(R.string.enter_scan_imei));
                etSearch.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                etSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
                break;
        }
    }


    // Keyboard

    public void setKeyboard(Boolean bKeyboard) {
        if (bKeyboard != null) {
            if (bKeyboard == Constants_Intern.SHOW_KEYBOARD) {
                Utility_Keyboard.openKeyboard(getActivity(), etSearch);
                etSearch.requestFocus();
            } else {
                Utility_Keyboard.hideKeyboardFrom(getActivity(), etSearch);
                vDividerLeft.requestFocus();
            }
        }
    }

    // Search

    /*
    private void onSearch() {
        switch (SharedPreferences.getInt(Constants_Intern.SEARCH_MODEL_TYPE, 2)) {
            case Constants_Intern.SEARCH_JUICER_TYPE_ID_DEVICE:
                if ()
                cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_BY_ID + etSearch.getText().toString(), null, new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) {
                        if (cSearchSaved.equals(etSearch.getText().toString())) {
                            if (Volley_Connection.successfulResponse(oJson)) {
                                try {
                                    oModel = new Model(Activity_Model.this, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL));
                                    returnFromSearch();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Utility_Toast.show(Activity_Model.this, R.string.id_unknown);
                                removeFragments();
                            }
                        }
                    }
                });
                break;
        }
    }
    */



    // OnClickListener & TextWatcher

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSearchType:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                String[] lChoice = {getString(R.string.id_device), getString(R.string.imei)};
                builder.setItems(lChoice, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                SharedPreferences.edit().putInt(Constants_Intern.SEARCH_JUICER_TYPE, Constants_Intern.SEARCH_JUICER_TYPE_ID_DEVICE).commit();
                                break;
                            case 1:
                                SharedPreferences.edit().putInt(Constants_Intern.SEARCH_JUICER_TYPE, Constants_Intern.SEARCH_JUICER_TYPE_IMEI_DEVICE).commit();
                                break;
                        }
                    }
                });
                builder.create().show();
                updateLayout();
                break;
            case R.id.ivCommit:

                break;
            case R.id.etSearch:

                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!editable.toString().equals("")) {
            switch (SharedPreferences.getInt(Constants_Intern.SEARCH_JUICER_TYPE, 1)) {
                case Constants_Intern.SEARCH_JUICER_TYPE_ID_DEVICE:
                    //onSearch();
                case Constants_Intern.SEARCH_JUICER_TYPE_IMEI_DEVICE:
                    if (editable.toString().length() == 15) {
                        setKeyboard(Constants_Intern.CLOSE_KEYBOARD);
                        //onSearch();
                    }
                    break;
            }
        }
    }
}
