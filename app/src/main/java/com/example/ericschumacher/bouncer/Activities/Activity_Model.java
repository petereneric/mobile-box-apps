package com.example.ericschumacher.bouncer.Activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Choice.Fragment_Choice;
import com.example.ericschumacher.bouncer.Fragments.Choice.Image.Fragment_Choice_Image_Charger;
import com.example.ericschumacher.bouncer.Fragments.Choice.Image.Fragment_Choice_Image_Manufacturer;
import com.example.ericschumacher.bouncer.Fragments.Edit.Fragment_Edit_Model_Battery;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Model_New;
import com.example.ericschumacher.bouncer.Fragments.Input.Fragment_Input;
import com.example.ericschumacher.bouncer.Fragments.Input.Fragment_Input_Battery;
import com.example.ericschumacher.bouncer.Fragments.Input.Fragment_Input_Dps;
import com.example.ericschumacher.bouncer.Fragments.Input.Fragment_Input_Model;
import com.example.ericschumacher.bouncer.Fragments.Select.Fragment_Select;
import com.example.ericschumacher.bouncer.Fragments.Select.Fragment_Select_Exploitation;
import com.example.ericschumacher.bouncer.Fragments.Select.Fragment_Select_PhoneType;
import com.example.ericschumacher.bouncer.Fragments.Select.Fragment_Select_YesNo;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Model_New_New;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.Objects.Model_Battery;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

public class Activity_Model extends AppCompatActivity implements View.OnClickListener, TextWatcher, Fragment_Choice.Interface_Choice, Fragment_Input.Interface_Input, Fragment_Select.Interface_Select, Fragment_Edit_Model_Battery.Interface_Edit_Model_Battery, Interface_Model_New_New {

    // Layout
    Toolbar vToolbar;
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

        // Interaction
        fManager = getSupportFragmentManager();

        // Layout
        setLayout();

        // Connection
        cVolley = new Volley_Connection(this);

        // SharedPreferences
        SharedPreferences = getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // FragmentModel
        fModel = (Fragment_Model_New)fManager.findFragmentById(R.id.fModel);
        updateLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Update

    }

    // Layout
    public void setLayout() {
        setContentView(R.layout.activity_model);
        vToolbar = findViewById(R.id.vToolbar);
        flInteraction = findViewById(R.id.flInteraction);
        tvSearchType = findViewById(R.id.tvSearchType);
        ivSearch = findViewById(R.id.ivSearch);
        etSearch = findViewById(R.id.etSearch);

        // Toolbar
        setSupportActionBar(vToolbar);
        vToolbar.setTitle(getString(R.string.model_manager));
        vToolbar.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.color_primary, null));
        vToolbar.setTitleTextColor(ResourcesCompat.getColor(getResources(), R.color.color_white, null));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // OnClickListener & TextWatcher
        tvSearchType.setOnClickListener(this);
        ivSearch.setOnClickListener(this);
        etSearch.addTextChangedListener(this);
    }

    public void updateLayout() {
        // Fragment model
        fModel.updateLayout();

        // TextViewSearch & EditTextSearch
        switch (SharedPreferences.getInt(Constants_Intern.SEARCH_TYPE_MODEL, 2)) {
            case Constants_Intern.MAIN_SEARCH_TYPE_ID_MODEL:
                tvSearchType.setText(getString(R.string.id_model));
                etSearch.setHint(getString(R.string.enter_scan_id_model));
                etSearch.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                etSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
                break;
            case Constants_Intern.MAIN_SEARCH_TYPE_NAME_MODEL:
                tvSearchType.setText(getString(R.string.name_model));
                etSearch.setHint(getString(R.string.enter_scan_name_model));
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
    public void showFragment(Fragment fragment, Bundle bData, String cTag) {
        fragment.setArguments(bData);
        fManager.beginTransaction().replace(R.id.flInteraction, fragment, cTag).commit();
    }

    public void removeFragment(String cTag) {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(cTag)).commit();
    }

    // Get
    public Model getModel() {
        return oModel;
    }

    // Edit
    public void onClickModelName() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.interaction_title_request_name_model));
        showFragment(new Fragment_Input_Model(), bData, Constants_Intern.FRAGMENT_INPUT_MODEL_NAME);
    }

    public void onClickManufacturer() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.manufacturer));
        showFragment(new Fragment_Choice_Image_Manufacturer(), bData, Constants_Intern.FRAGMENT_CHOICE_IMAGE_MANUFACTURER);
    }

    public void onClickCharger() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.charger));
        bData.putInt(Constants_Intern.ID_MANUFACTURER, oModel.getoManufacturer() != null ? oModel.getoManufacturer().getId() : Constants_Intern.ID_UNKNOWN);
        showFragment(new Fragment_Choice_Image_Charger(), bData, Constants_Intern.FRAGMENT_CHOICE_IMAGE_CHARGER);
    }

    public void onClickBattery() {
        if (oModel.getlModelBatteries().size() == 0) {
            Bundle bData = new Bundle();
            bData.putString(Constants_Intern.TITLE, getString(R.string.battery));
            showFragment(new Fragment_Input_Battery(), bData, Constants_Intern.FRAGMENT_INPUT_BATTERY);
        } else {
            Bundle bData = new Bundle();
            bData.putString(Constants_Intern.TITLE, getString(R.string.interaction_title_edit_model_battery));
            showFragment(new Fragment_Edit_Model_Battery(), bData, Constants_Intern.FRAGMENT_EDIT_MODEL_BATTERY);
        }
    }

    public void onClickPhoneType() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.phone_type));
        showFragment(new Fragment_Select_PhoneType(), bData, Constants_Intern.FRAGMENT_SELECT_PHONE_TYPE);
    }

    public void onClickDefaultExploitation() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.exploitation));
        showFragment(new Fragment_Select_Exploitation(), bData, Constants_Intern.FRAGMENT_SELECT_EXPLOITATION);
    }

    public void onClickDps() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.dps));
        showFragment(new Fragment_Input_Dps(), bData, Constants_Intern.FRAGMENT_INPUT_DPS);
    }

    public void onClickBatteryRemovable() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.battery_removable));
        showFragment(new Fragment_Select_YesNo(), bData, Constants_Intern.FRAGMENT_SELECT_BATTERY_REMOVABLE);
    }

    public void onClickBackcoverRemovable() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.backcover_removable));
        showFragment(new Fragment_Select_YesNo(), bData, Constants_Intern.FRAGMENT_SELECT_BACKCOVER_REMOVABLE);
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
        updateLayout();
        removeFragment(cTag);
    }

    @Override
    public void returnInput(final String cTag, final String cInput) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_INPUT_MODEL_NAME:
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
                                    oModel = new Model(Activity_Model.this, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL));
                                    updateLayout();
                                    oModel.connectTac(tac);
                                } else {
                                    cVolley.getResponse(Request.Method.PUT, Urls.URL_PUT_MODEL_ADD + cInput, null, new Interface_VolleyResult() {
                                        @Override
                                        public void onResult(JSONObject oJson) throws JSONException {
                                            oModel = new Model(Activity_Model.this, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL));
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
            case Constants_Intern.FRAGMENT_INPUT_DPS:
                oModel.setnDps(Integer.parseInt(cInput));
                break;
            case Constants_Intern.FRAGMENT_INPUT_BATTERY:
                cVolley.getResponse(Request.Method.GET, Urls.URL_GET_BATTERY_BY_NAME + cInput, null, new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) throws JSONException {
                        if (Volley_Connection.successfulResponse(oJson)) {
                            Battery oBattery = new Battery(oJson.getJSONObject(Constants_Extern.OBJECT_BATTERY), Activity_Model.this);
                            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_BATTERY_CONNECT_MODEL + oBattery.getId() + "/" + oModel.getkModel(), null, new Interface_VolleyResult() {
                                @Override
                                public void onResult(JSONObject oJson) throws JSONException {
                                    oModel.addModelBattery(new Model_Battery(Activity_Model.this, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL_BATTERY)));
                                    removeFragment(cTag);
                                    onClickBattery();
                                }
                            });
                        } else {
                            cVolley.getResponse(Request.Method.PUT, Urls.URL_PUT_BATTERY_ADD + cInput + oModel.getoManufacturer().getId(), null, new Interface_VolleyResult() {
                                @Override
                                public void onResult(JSONObject oJson) throws JSONException {
                                    if (Volley_Connection.successfulResponse(oJson)) {
                                        Battery oBattery = new Battery(oJson.getJSONObject(Constants_Extern.OBJECT_BATTERY), Activity_Model.this);
                                        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_BATTERY_CONNECT_MODEL + oBattery.getId() + "/" + oModel.getkModel(), null, new Interface_VolleyResult() {
                                            @Override
                                            public void onResult(JSONObject oJson) throws JSONException {
                                                oModel.addModelBattery(new Model_Battery(Activity_Model.this, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL_BATTERY)));
                                                removeFragment(cTag);
                                                onClickBattery();
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void returnEditModelBattery(String cTag) {
        removeFragment(cTag);
    }



    @Override
    public void returnSelect(String cTag, int tSelect) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_SELECT_PHONE_TYPE:
                oModel.settPhone(tSelect);
                break;
            case Constants_Intern.FRAGMENT_SELECT_EXPLOITATION:
                oModel.settDefaultExploitation(tSelect);
                break;
            case Constants_Intern.FRAGMENT_SELECT_BATTERY_REMOVABLE:
                oModel.setBatteryRemovable(tSelect == 1);
                break;
            case Constants_Intern.FRAGMENT_SELECT_BACKCOVER_REMOVABLE:
                oModel.setBackcoverRemovable(tSelect == 1);
                break;
        }
        updateLayout();
        removeFragment(cTag);
    }

    @Override
    public void unknownInput(String cTag) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_INPUT_MODEL_NAME:
                switch (SharedPreferences.getInt(Constants_Intern.SEARCH_TYPE_MODEL, 2)) {
                    case Constants_Intern.MAIN_SEARCH_TYPE_ID_MODEL:
                    case Constants_Intern.MAIN_SEARCH_TYPE_NAME_MODEL:
                    case Constants_Intern.MAIN_SEARCH_TYPE_IMEI:
                    case Constants_Intern.MAIN_SEARCH_TYPE_TAC:
                        break;
                }
                break;
            case Constants_Intern.FRAGMENT_INPUT_DPS:
                break;
            case Constants_Intern.FRAGMENT_INPUT_BATTERY:
        }
        updateLayout();
        removeFragment(cTag);
    }

    @Override
    public void addModelBattery() {
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.TITLE, getString(R.string.battery));
        showFragment(new Fragment_Input_Battery(), bData, Constants_Intern.FRAGMENT_INPUT_BATTERY);
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
                                SharedPreferences.edit().putInt(Constants_Intern.SEARCH_TYPE_MODEL, Constants_Intern.MAIN_SEARCH_TYPE_NAME_MODEL).commit();
                                break;
                            case 1:
                                SharedPreferences.edit().putInt(Constants_Intern.SEARCH_TYPE_MODEL, Constants_Intern.MAIN_SEARCH_TYPE_ID_MODEL).commit();
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
                final String cSearchSaved = etSearch.getText().toString();
                switch (SharedPreferences.getInt(Constants_Intern.SEARCH_TYPE_MODEL, 2)) {
                    case Constants_Intern.MAIN_SEARCH_TYPE_ID_MODEL:
                        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_BY_ID + etSearch.getText().toString(), null, new Interface_VolleyResult() {
                            @Override
                            public void onResult(JSONObject oJson) {
                                if (cSearchSaved.equals(etSearch.getText().toString())) {
                                    if (Volley_Connection.successfulResponse(oJson)) {
                                        try {
                                            oModel = new Model(Activity_Model.this, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL));
                                            updateLayout();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        Utility_Toast.show(Activity_Model.this, R.string.id_unknown);
                                    }
                                }
                            }
                        });
                        break;
                    case Constants_Intern.MAIN_SEARCH_TYPE_NAME_MODEL:
                        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_BY_NAME + etSearch.getText().toString(), null, new Interface_VolleyResult() {
                            @Override
                            public void onResult(JSONObject oJson) {
                                if (cSearchSaved.equals(etSearch.getText().toString())) {
                                    if (Volley_Connection.successfulResponse(oJson)) {
                                        try {
                                            oModel = new Model(Activity_Model.this, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL));
                                            updateLayout();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        Utility_Toast.show(Activity_Model.this, R.string.name_unknown);
                                    }
                                }

                            }
                        });
                        break;
                    case Constants_Intern.MAIN_SEARCH_TYPE_TAC:
                        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_BY_TAC + etSearch.getText().toString(), null, new Interface_VolleyResult() {
                            @Override
                            public void onResult(JSONObject oJson) {
                                if (cSearchSaved.equals(etSearch.getText().toString())) {
                                    if (Volley_Connection.successfulResponse(oJson)) {
                                        try {
                                            oModel = new Model(Activity_Model.this, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL));
                                            updateLayout();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        Utility_Toast.show(Activity_Model.this, R.string.tac_unknown);
                                    }
                                }
                            }
                        });
                        break;
                    case Constants_Intern.MAIN_SEARCH_TYPE_IMEI:
                        String tac = etSearch.getText().toString().substring(0, 8);
                        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_BY_TAC + tac, null, new Interface_VolleyResult() {
                            @Override
                            public void onResult(JSONObject oJson) {
                                if (cSearchSaved.equals(etSearch.getText().toString())) {
                                    if (Volley_Connection.successfulResponse(oJson)) {
                                        try {
                                            oModel = new Model(Activity_Model.this, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL));
                                            updateLayout();
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        Utility_Toast.show(Activity_Model.this, R.string.tac_unknown);
                                    }
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
        if (!editable.toString().equals("")) {
            switch (SharedPreferences.getInt(Constants_Intern.SEARCH_TYPE_MODEL, 2)) {
                case Constants_Intern.MAIN_SEARCH_TYPE_ID_MODEL:
                case Constants_Intern.MAIN_SEARCH_TYPE_NAME_MODEL:
                    ivSearch.callOnClick();
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
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
