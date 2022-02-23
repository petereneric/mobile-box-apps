package com.example.ericschumacher.bouncer.Activities.Activity_Wiper;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Fragments.Fragment_Wiper_Multi;
import com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Fragments.Fragment_Wiper_Single;
import com.example.ericschumacher.bouncer.Activities.Manager.Activity_Device;
import com.example.ericschumacher.bouncer.Activities.Menu.Activity_Menu;
import com.example.ericschumacher.bouncer.Activities.Tools.Activity_Wiper_Procedure;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Block.Fragment_Block_Devices;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Activity_Wiper;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Callback;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Devices;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.Objects.ModelWipe;
import com.example.ericschumacher.bouncer.Objects.Protocol;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Activity_Wiper extends Activity_Device implements Interface_Devices, Interface_Activity_Wiper {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // VALUES & VARIABLES

    // Constants
    public static final int MODE_WIPER_NONE = 0;
    public static final int MODE_WIPER_SINGLE = 1;
    public static final int MODE_WIPER_MULTI = 2;



    // Data
    private ArrayList<Device> lDevices = new ArrayList<>();
    private ArrayList<Model> lModels = new ArrayList<>();

    // Menu
    private boolean menuVisibility = false;

    // Fragments
    private Fragment_Block_Devices fDevices;
    private Fragment_Wiper_Single fWiperSingle = new Fragment_Wiper_Single();
    private Fragment_Wiper_Multi fWiperMulti = new Fragment_Wiper_Multi();
    private String fSelected = null;


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LIFE-CYCLE

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        base(true);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // MENU

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_wiper, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mSettings:
                startActivityForResult(new Intent(this, Activity_Wiper_Procedure.class).putExtra(Constants_Intern.TOKEN_AUTHENTICATION, getTokenAuthentication()), Constants_Intern.REQUEST_CODE_TOKEN_AUTHENTICATION);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // FRAGMENTS

    @Override
    public void showFragment(Fragment fragment, Bundle bData, String cTag, Boolean bKeyboard) {
        setKeyboard(bKeyboard);
        Fragment f = getSupportFragmentManager().findFragmentByTag(cTag);
        if (f == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.flInteraction, fragment, cTag).commit();
        }
        if (!cTag.equals("FRAGMENT_WIPER_SINGLE") && !cTag.equals("FRAGMENT_WIPER_MULTI")) {
            if (getSupportFragmentManager().findFragmentByTag("FRAGMENT_WIPER_SINGLE") != null) {
                removePossibleFragments();
                getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("FRAGMENT_WIPER_SINGLE")).commit();
            }
            if (getSupportFragmentManager().findFragmentByTag("FRAGMENT_WIPER_MULTI") != null) {
                removePossibleFragments();
                getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("FRAGMENT_WIPER_MULTI")).commit();
            }
            fSelected = cTag;
        }
    }

    public void removePossibleFragments() {
        if (fSelected != null) {
            if (getSupportFragmentManager().findFragmentByTag(fSelected) != null) {
                getSupportFragmentManager().beginTransaction().remove(getSupportFragmentManager().findFragmentByTag(fSelected)).commit();
            }
        }
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PRIVATE

    @Override
    public void setLayout() {
        super.setLayout();
    }

    public int getIdLayout() {
        return R.layout.activity_wiper;
    }

    @Override
    public void initiateFragments() {
        super.initiateFragments();
        fDevices = (Fragment_Block_Devices)fManager.findFragmentById(R.id.fBlockDevices);
    }

    @Override
    public void updateLayout() {
        super.updateLayout();
        etSearch.setInputType(InputType.TYPE_CLASS_TEXT);

        switch (getMode()) {
            case MODE_WIPER_NONE:

                break;
            case MODE_WIPER_SINGLE:

                break;
            case MODE_WIPER_MULTI:

                break;
        }

        if (lDevices.size() > 0) {
            fDevices.update();
        } else {

        }
    }

    @Override
    public void base(Boolean bKeyboard) {
        baseObjects();
        switch (getMode()) {
            case MODE_WIPER_SINGLE:
                // Show Single
                showFragment(fWiperSingle, null, "FRAGMENT_WIPER_SINGLE", false);
                if (getSupportFragmentManager().findFragmentByTag("FRAGMENT_WIPER_SINGLE") != null) {
                    getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag("FRAGMENT_WIPER_SINGLE"));
                }
                // Hide Multi
                if (getSupportFragmentManager().findFragmentByTag("FRAGMENT_WIPER_MULTI") != null) {
                    getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("FRAGMENT_WIPER_MULTI")).commit();
                }
                break;
            case MODE_WIPER_MULTI:
                // Show Multi
                showFragment(fWiperMulti, null, "FRAGMENT_WIPER_MULTI", false);
                if (getSupportFragmentManager().findFragmentByTag("FRAGMENT_WIPER_MULTI") != null) {
                    getSupportFragmentManager().beginTransaction().show(getSupportFragmentManager().findFragmentByTag("FRAGMENT_WIPER_MULTI"));
                }
                // Hide Single
                if (getSupportFragmentManager().findFragmentByTag("FRAGMENT_WIPER_SINGLE") != null) {
                    getSupportFragmentManager().beginTransaction().hide(getSupportFragmentManager().findFragmentByTag("FRAGMENT_WIPER_SINGLE")).commit();
                }
                    break;
            case MODE_WIPER_NONE:

                break;
        }
        super.base(bKeyboard);
    }

    private void baseObjects() {
        switch (getMode()) {
            case MODE_WIPER_SINGLE:
            case MODE_WIPER_MULTI:
                if (menuVisibility) {
                    getSupportFragmentManager().beginTransaction().show(fDevice).commit();
                } else {
                    getSupportFragmentManager().beginTransaction().hide(fDevice).commit();
                }
                getSupportFragmentManager().beginTransaction().show(fDevices).commit();
                break;
            case MODE_WIPER_NONE:
                getSupportFragmentManager().beginTransaction().hide(fDevices).commit();
                break;
        }
    }

    @Override
    public void returnFromSearch() {
        if (deviceContained(oDevice)) {

        } else {
            Log.i("joooo", "jooo");
            etSearch.setText("");
            lDevices.add(oDevice);
            Model model = oDevice.getoModel();
            oDevice = null;
            if (!modelContained(model)) {
                lModels.add(model);
                model.loadModelWipes(new Interface_Callback() {
                    @Override
                    public void callback() {

                    }
                });
            }
            if (getModel() == null) setModel(lModels.get(0));
        }
        super.returnFromSearch();
    }

    // Search

    public void onSearch() {
        final String cSearchSaved = etSearch.getText().toString();
        switch (SharedPreferences.getInt(Constants_Intern.SEARCH_DEVICE_TYPE, Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_ID_DEVICE)) {
            case Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_ID_DEVICE:
                if (cSearchSaved.length() < 15) {
                    cVolley.getResponse(Request.Method.GET, Urls.URL_GET_DEVICE_BY_ID + getSearchId(), null, new Interface_VolleyResult() {
                        @Override
                        public void onResult(JSONObject oJson) {
                            if (cSearchSaved.equals(etSearch.getText().toString())) {
                                if (Volley_Connection.successfulResponse(oJson)) {
                                    try {
                                        oDevice = new Device(oJson.getJSONObject(Constants_Extern.OBJECT_DEVICE), Activity_Wiper.this);
                                        oModel = oDevice.getoModel();
                                        oDevice.loadProtocols(new Interface_Callback() {
                                            @Override
                                            public void callback() {
                                                returnFromSearch();
                                            }
                                        });
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    Utility_Toast.show(Activity_Wiper.this, R.string.id_unknown);
                                    removeFragments();
                                }
                            }
                        }
                    });
                    return;
                }
        }
        super.onSearch();
    }

    @Override
    public void setModel(Model oModel) {
        if (oDevice != null) {
            oDevice.setoModel(oModel);
        } else {
            this.oModel = oModel;
        }
    }

    @Override
    public Model getModel() {
        if (oDevice != null) {
            return oDevice.getoModel();
        } else {
            return oModel;
        }
    }

    private boolean deviceContained(Device device) {
        for (Device device_ :lDevices) {
            if (device_.getIdDevice() == device.getIdDevice()) return true;
        }
        return false;
    }

    private boolean modelContained(Model model) {
        for (Model model_ :lModels) {
            if (model_.getkModel() == model.getkModel()) return true;
        }
        return false;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PUBLIC

    public int getMode() {
        if (oDevice != null) {
            return MODE_WIPER_SINGLE;
        }
        if (lDevices.size() > 0) {
            return MODE_WIPER_MULTI;
        }
        return MODE_WIPER_NONE;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LISTENER

    @Override
    public void afterTextChanged(Editable editable) {
        if (!editable.toString().equals("")) {
            switch (SharedPreferences.getInt(Constants_Intern.SEARCH_DEVICE_TYPE, Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_ID_DEVICE)) {
                case Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_ID_DEVICE:
                    if (editable.toString().length() < 15) {
                        if (getSearchId() != null) {
                            onSearch();
                        }
                        break;
                    }
                case Constants_Intern.MAIN_SEARCH_DEVICE_TYPE_IMEI:
                    if (editable.toString().length() == 15) {
                        onSearch();
                    }
                    break;
            }
        }
    }



    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // INTERFACES

    // Interface_Devices
    @Override
    public ArrayList<Model> getModels() {
        return lModels;
    }

    @Override
    public void clickModel(Model model) {
        oDevice = null;
        setModel(model);
        base(null);
    }

    @Override
    public int numberDevices(Model model) {
        int nCount = 0;
        for (Device device : lDevices) {
            if (device.getoModel().getkModel() == model.getkModel()) nCount++;
        }
        return nCount;
    }

    @Override
    public boolean isSelected(Model model) {
        return (getModel().getkModel() == model.getkModel());
    }

    @Override
    public ArrayList<ModelWipe> getModelWipes() {
        return getSelectedModel().getlModelWipes();
    }

    @Override
    public void finishAll() {
        int size = getSelectedDevices().size();
        for (int i = 0; i < size; i++) {
            int finalI = i;
            Protocol.create(cVolley, getSelectedDevices().get(i).getIdDevice(), getJWT().getkUser(), true, new Protocol.Interface_Create() {
                @Override
                public void created(Protocol protocol) {
                    getSelectedDevices().get(finalI).getlProtocols().add(protocol);
                    if (finalI +1 == size) {
                        base(false);
                    }
                }
            });
        }
    }

    @Override
    public Model getSelectedModel() {
        return getModel();
    }

    @Override
    public ArrayList<Device> getSelectedDevices () {
        ArrayList<Device> lDevices = new ArrayList<>();
        for (Device device : this.lDevices) {
            if (device.getoModel().getkModel() == oModel.getkModel()) {
                lDevices.add(device);
            }
        }
        return lDevices;
    }
}
