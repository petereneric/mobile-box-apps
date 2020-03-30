package com.example.ericschumacher.bouncer.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Choice.Fragment_Choice;
import com.example.ericschumacher.bouncer.Fragments.Choice.Image.Adapter_Choice_Image_Manufacturer;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Model_New;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Name_Model;
import com.example.ericschumacher.bouncer.Fragments.Input.Fragment_Input;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

public class Activity_Model extends AppCompatActivity implements View.OnClickListener, TextWatcher, Fragment_Choice.Interface_Choice, Fragment_Input.Interface_Input {

    // Layout
    FrameLayout flInteraction;
    TextView tvSearchType;
    ImageView ivSearch;
    EditText etSearch;

    // Objects
    Model oModel;

    // Interaction
    FragmentManager fManager;
    Fragment_Model_New fModel;

    // Connection
    Volley_Connection cVolley;

    // SharedPreferences
    SharedPreferences SharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Layout
        setLayout();

        // Interaction
        fManager = getSupportFragmentManager();
        fModel = (Fragment_Model_New)fManager.findFragmentById(R.id.fModel);

        // Connection
        cVolley = new Volley_Connection(this);

        // SharedPreferences
        SharedPreferences = getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0);
    }

    // Layout
    public void setLayout() {
        flInteraction = findViewById(R.id.flInteraction);
        tvSearchType = findViewById(R.id.tvSearchType);
        ivSearch = findViewById(R.id.ivSearch);
        etSearch = findViewById(R.id.etSearch);

        // OnClickListener & TextWatcher
        tvSearchType.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        etSearch.addTextChangedListener(this);

        updateLayout();
    }

    public void updateLayout() {
        // Fragment model
        fModel.updateLayout();

        // TextViewSearch & EditTextSearch
        switch (SharedPreferences.getInt(Constants_Intern.SEARCH_TYPE_MODEL, 2)) {
            case Constants_Intern.MAIN_SEARCH_TYPE_ID_MODEL:
                tvSearchType.setText(getString(R.string.name_model));
                etSearch.setHint(getString(R.string.enter_scan_name_model));
                etSearch.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                etSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
                break;
            case Constants_Intern.MAIN_SEARCH_TYPE_NAME_MODEL:
                tvSearchType.setText(getString(R.string.id_model));
                etSearch.setHint(getString(R.string.enter_scan_id_model));
                etSearch.setRawInputType(InputType.TYPE_CLASS_TEXT);
                etSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
                break;
            case Constants_Intern.MAIN_SEARCH_TYPE_TAC:
                tvSearchType.setText(getString(R.string.tac));
                etSearch.setHint(getString(R.string.enter_scan_tac));
                etSearch.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                etSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(8)});
                break;
            case Constants_Intern.MAIN_SEARCH_TYPE_IMEI:
                tvSearchType.setText(getString(R.string.imei));
                etSearch.setHint(getString(R.string.enter_scan_imei));
                etSearch.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                etSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
                break;
        }
    }

    // Interaction
    public void showFragment(Fragment fragment, Bundle data, String tag) {
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, fragment, tag).commit();
    }

    // Private methods
    private void startFragment(Fragment fragment, Bundle bData, String cTag) {
        fragment.setArguments(bData);
        fManager.beginTransaction().replace(R.id.flInteraction, fragment, cTag).commit();
    }

    public void removeFragment(String cTag) {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(cTag)).commit();
    }


    // Public methods
    // Get
    public Model getModel() {
        return oModel;
    }

    // Edit
    public void editName() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.INTERACTION_TITLE, getString(R.string.interaction_title_request_name_model));
        showFragment(new Fragment_Request_Name_Model(), bData, Constants_Intern.FRAGMENT_REQUEST_NAME_MODEL);
    }

    public void editManufacturer() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.manufacturer));
        showFragment(new Adapter_Choice_Image_Manufacturer(), bData, Constants_Intern.FRAGMENT_CHOICE_IMAGE_MANUFACTURER);
    }

    public void onChoiceCallback(String cTag, Object object) {

    }

    public void editCharger() {

    }

    public void editBattery() {

    }

    public void editPhoneType() {

    }

    public void editDefaultExploitation() {

    }

    public void editDps() {

    }

    public void editBatteryRemovable() {

    }

    public void editBackcoverRemovable() {

    }

    // Return
    @Override
    public void returnChoice(String cTag, Object object) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_CHOICE_IMAGE_MANUFACTURER:
                oModel.setoManufacturer((Manufacturer)object);
                break;
            case Constants_Intern.FRAGMENT_CHOICE_IMAGE_CHARGER:
                oModel.setoCharger((Charger)object);
                break;
        }
    }

    // ClickListener
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSearchType:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                String[] lChoice = {getString(R.string.name_model), getString(R.string.id_model), getString(R.string.tac), getString(R.string.imei)};
                builder.setItems(lChoice, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                SharedPreferences.edit().putInt(Constants_Intern.SEARCH_TYPE_MODEL, Constants_Intern.MAIN_SEARCH_TYPE_ID_MODEL).commit();
                                break;
                            case 1:
                                SharedPreferences.edit().putInt(Constants_Intern.SEARCH_TYPE_MODEL, Constants_Intern.MAIN_SEARCH_TYPE_NAME_MODEL).commit();
                                break;
                            case 2:
                                SharedPreferences.edit().putInt(Constants_Intern.SEARCH_TYPE_MODEL, Constants_Intern.MAIN_SEARCH_TYPE_TAC).commit();
                                break;
                            case 3:
                                SharedPreferences.edit().putInt(Constants_Intern.SEARCH_TYPE_MODEL, Constants_Intern.MAIN_SEARCH_TYPE_IMEI).commit();
                                break;
                        }
                        etSearch.setText("");
                        updateLayout();
                    }
                });
                builder.create().show();
                break;
            case R.id.ivSearch:
                switch (SharedPreferences.getInt(Constants_Intern.SEARCH_TYPE_MODEL, 2)) {
                    case Constants_Intern.MAIN_SEARCH_TYPE_ID_MODEL:
                        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_BY_ID + Integer.parseInt(etSearch.getText().toString()), null, new Interface_VolleyResult() {
                            @Override
                            public void onResult(JSONObject oJson) {
                                if (Volley_Connection.successfulResponse(oJson)) {
                                    oModel = new Model(Activity_Model.this, oJson);
                                    updateLayout();
                                } else {
                                    Utility_Toast.show(Activity_Model.this, R.string.id_unknown);
                                }
                            }
                        });
                        break;
                    case Constants_Intern.MAIN_SEARCH_TYPE_NAME_MODEL:
                        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_BY_NAME + Integer.parseInt(etSearch.getText().toString()), null, new Interface_VolleyResult() {
                            @Override
                            public void onResult(JSONObject oJson) {
                                if (Volley_Connection.successfulResponse(oJson)) {
                                    oModel = new Model(Activity_Model.this, oJson);
                                    updateLayout();
                                } else {
                                    Utility_Toast.show(Activity_Model.this, R.string.name_unknown);
                                }
                            }
                        });
                        break;
                    case Constants_Intern.MAIN_SEARCH_TYPE_TAC:
                        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_BY_TAC + etSearch.getText().toString(), null, new Interface_VolleyResult() {
                            @Override
                            public void onResult(JSONObject oJson) {
                                if (Volley_Connection.successfulResponse(oJson)) {
                                    oModel = new Model(Activity_Model.this, oJson);
                                    updateLayout();
                                } else {
                                    Utility_Toast.show(Activity_Model.this, R.string.tac_unknown);
                                }
                            }
                        });
                        break;
                    case Constants_Intern.MAIN_SEARCH_TYPE_IMEI:
                        String tac = etSearch.getText().toString().substring(0, 8);
                        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_BY_TAC + tac, null, new Interface_VolleyResult() {
                            @Override
                            public void onResult(JSONObject oJson) {
                                if (Volley_Connection.successfulResponse(oJson)) {
                                    oModel = new Model(Activity_Model.this, oJson);
                                    updateLayout();
                                } else {
                                    Utility_Toast.show(Activity_Model.this, R.string.tac_unknown);
                                }
                            }
                        });
                        break;
                }
                break;
        }
    }

    // TextWatcher
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void afterTextChanged(Editable editable) {
        switch (SharedPreferences.getInt(Constants_Intern.SEARCH_TYPE_MODEL, 2)) {
            case Constants_Intern.MAIN_SEARCH_TYPE_ID_MODEL:
                break;
            case Constants_Intern.MAIN_SEARCH_TYPE_NAME_MODEL:
                break;
            case Constants_Intern.MAIN_SEARCH_TYPE_TAC:
                if (editable.toString().length()==8) {
                    ivSearch.callOnClick();
                }
                break;
            case Constants_Intern.MAIN_SEARCH_TYPE_IMEI:
                if (editable.toString().length()==15) {
                    ivSearch.callOnClick();
                }
                break;
        }
    }

    @Override
    public void returnInput(String cTag, final String cInput) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_INPUT_MODEL:
                switch (SharedPreferences.getInt(Constants_Intern.SEARCH_TYPE_MODEL, 2)) {
                    case Constants_Intern.MAIN_SEARCH_TYPE_ID_MODEL:
                    case Constants_Intern.MAIN_SEARCH_TYPE_NAME_MODEL:
                        if (oModel != null) {
                            oModel.setName(cInput);
                            updateLayout();
                        }
                        break;
                    case Constants_Intern.MAIN_SEARCH_TYPE_IMEI:
                    case Constants_Intern.MAIN_SEARCH_TYPE_TAC:
                        final String tac = etSearch.getText().toString().substring(0, 8);
                        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_BY_NAME+cInput, null, new Interface_VolleyResult() {
                            @Override
                            public void onResult(JSONObject oJson) throws JSONException {
                                if (Volley_Connection.successfulResponse(oJson)) {
                                    oModel = new Model(Activity_Model.this, oJson);
                                    updateLayout();
                                    oModel.connectTac(tac);
                                } else {
                                    cVolley.getResponse(Request.Method.PUT, Urls.URL_PUT_MODEL_ADD + cInput, null, new Interface_VolleyResult() {
                                        @Override
                                        public void onResult(JSONObject oJson) throws JSONException {
                                            oModel = new Model(Activity_Model.this, oJson);
                                            updateLayout();
                                            oModel.connectTac(tac);
                                        }
                                    });
                                }
                            }
                        });
                        break;
                }
                break;
        }
        removeFragment(cTag);
    }

    @Override
    public void unknownInput(String cTag) {

    }
}
