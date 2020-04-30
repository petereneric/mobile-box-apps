package com.example.ericschumacher.bouncer.Activities.Parent;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Color_Add;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Device;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog.Fragment_Dialog_Simple;
import com.example.ericschumacher.bouncer.Fragments.Edit.Fragment_Edit_Device_Damages;
import com.example.ericschumacher.bouncer.Fragments.Edit.Fragment_Edit_Model_Battery;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Edit_Model_Battery_Select;
import com.example.ericschumacher.bouncer.Fragments.Fragment_SimpleText;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_DeviceManager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Dialog;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_SimpleText;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Edit_Model_Battery;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Request_Name;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Search_Manufacturer;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Search_Model;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Input;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Int;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_JSON;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Additive;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Additive.Shape;
import com.example.ericschumacher.bouncer.Objects.Additive.Station;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.Objects.Model_Battery;
import com.example.ericschumacher.bouncer.Objects.Object_Device_Damage;
import com.example.ericschumacher.bouncer.Objects.Object_Model_Damage;
import com.example.ericschumacher.bouncer.Objects.Object_SearchResult;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Camera;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;
import com.example.ericschumacher.bouncer.Zebra.ManagerPrinter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

public class Activity_Device extends AppCompatActivity implements Interface_DeviceManager, View.OnClickListener, Interface_Dialog, Interface_Fragment_SimpleText, Interface_Edit_Model_Battery,
        Interface_Request_Name {

    // Interfaces
    public Interface_Device iDevice;

    // Instances
    public Device oDevice;
    public Utility_Network uNetwork;
    public Volley_Connection cVolley;

    // Printer
    public ManagerPrinter mPrinter;

    // Layout
    public EditText etScan;
    public ImageView ivHelp;
    public ImageView ivClearScan;
    public FloatingActionButton fabReset;

    // Fragments
    public FragmentManager fManager;
    public FrameLayout flInteraction;
    public FrameLayout flOverview;
    public FrameLayout flDevice;

    // Constants
    public static final int IMAGE_FRONT = 1;
    public static final int IMAGE_BACK = 2;
    public static final int IMAGE_SAVE = 3;

    // Variables
    private String fCurrentPicture;
    public SharedPreferences mSharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instances
        uNetwork = new Utility_Network(this);
        fManager = getSupportFragmentManager();
        mPrinter = new ManagerPrinter(this);
        cVolley = new Volley_Connection(this);

        // Layout
        setLayout();
        handleTextInput();

        // Fragments
        Fragment fDevice = new Fragment_Device();
        fManager.beginTransaction().replace(R.id.flFragmentDevice, fDevice, Constants_Intern.FRAGMENT_DEVICE).commit();

        // Variables
        mSharedPreferences = getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        iDevice = (Interface_Device) fManager.findFragmentByTag(Constants_Intern.FRAGMENT_DEVICE);
        if (mPrinter == null) {
            mPrinter = new ManagerPrinter(this);
        }
        mPrinter.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPrinter.disconnect();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_FRONT && resultCode == RESULT_OK) {
            Log.i("FilePath_", fCurrentPicture);
            takePictures(IMAGE_BACK);
        }
        if (requestCode == IMAGE_BACK && resultCode == RESULT_OK) {
            Log.i("FilePath_", fCurrentPicture);
            takePictures(IMAGE_SAVE);
        }
    }

    public void setLayout() {

        // Views
        setContentView(R.layout.activity_device);
        etScan = findViewById(R.id.etScan);
        ivHelp = findViewById(R.id.ivHelp);
        ivClearScan = findViewById(R.id.ivClearScan);
        fabReset = findViewById(R.id.fab);
        //flInteraction = findViewById(R.id.flFragmentInteraction);
        //flOverview = findViewById(R.id.flFragmentInteraction);
        //flDevice = findViewById(R.id.flFragmentDevice);

        ivHelp.setOnClickListener(this);
        fabReset.setOnClickListener(this);
        ivClearScan.setOnClickListener(this);
    }

    public void handleTextInput() {
        etScan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editable.toString().equals("")) onScan(editable.toString());
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                Fragment_Dialog_Simple f = new Fragment_Dialog_Simple();
                Bundle bundle = new Bundle();
                bundle.putString(Constants_Intern.TITLE, getString(R.string.dialog_fragment_total_reset_title));
                bundle.putInt(Constants_Intern.TYPE_FRAGMENT_DIALOG, Constants_Intern.TYPE_FRAGMENT_DIALOG_TOTAL_RESET);
                f.setArguments(bundle);
                f.show(getSupportFragmentManager(), "Fragment_Dialog_Total_Reset");
            case R.id.ivClearScan:
                resetDevice();
                break;
        }
    }

    // Getters
    @Override
    public Model getModel() {
        return oDevice.getoModel();
    }

    @Override
    public Device getDevice() {
        return oDevice;
    }

    @Override
    public Color getColor() {
        return oDevice.getoColor();
    }

    @Override
    public void searchMatchingModel(String namePart, final Interface_Search_Model iSearchModel) {
        uNetwork.getMatchingModels(namePart, new Interface_VolleyCallback_ArrayList_Input() {
            @Override
            public void onSuccess(ArrayList<Object_SearchResult> list) {
                iSearchModel.returnSearchResultsForModel(list);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void searchMatchingManufacturer(String namePart, final Interface_Search_Manufacturer iSearchManufacturer) {
        uNetwork.getMatchingManufacturers(namePart, new Interface_VolleyCallback_ArrayList_Input() {
            @Override
            public void onSuccess(ArrayList<Object_SearchResult> list) {
                iSearchManufacturer.returnSearchResultsForManufacturer(list);
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void onClickLKU() {

    }

    // Specific methods
    @Override
    public void onScan(String text) {
        int length = text.length();
        if (length < 15) {
            // Id_Device
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_DEVICE_BY_ID + text, null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    if (oJson.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                        oDevice = new Device(oJson.getJSONObject(Constants_Extern.OBJECT_DEVICE), Activity_Device.this);
                        updateUI();
                        onScanned();
                    }
                }
            });
        } else {
            if (length == 15) {
                uNetwork.getDeviceByIMEI(text, new Interface_VolleyCallback_JSON() {
                    @Override
                    public void onResponse(JSONObject json) {
                        convertJsonToDevice(json);
                        updateUI();
                        onScanned();
                    }
                });
            }
        }
    }


    @Override
    public void bookDeviceIntoLKUStock() {
        uNetwork.assignLku(oDevice, new Interface_VolleyCallback_Int() {
            @Override
            public void onSuccess(int i) {
                //oDevice.setLKU(i);
                oDevice.getoStation().setId(Constants_Intern.STATION_PRIME_STOCK);
                uNetwork.updateDevice(oDevice, new Interface_VolleyCallback() {
                    @Override
                    public void onSuccess() {
                        updateUI();
                        resetDevice();
                        Log.i("tagg", "bookDeviceIntoOfLKUStock - Success");
                        etScan.setText("");
                        openKeyboard(etScan);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
                Toast.makeText(Activity_Device.this, getString(R.string.device_stored_lku), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure() {
                // Device is already in LKU Stock
                oDevice.getoStation().setId(Constants_Intern.STATION_PRIME_STOCK);
                uNetwork.updateDevice(oDevice, new Interface_VolleyCallback() {
                    @Override
                    public void onSuccess() {
                        updateUI();
                        Log.i("tagg", "bookDeviceIntoOfLKUSTock - Failure");
                        mPrinter.printDeviceId(oDevice);
                        etScan.setText("");
                        openKeyboard(etScan);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
                // Platz scheint voll

                //oDevice.getoStation().setId(Constants_Intern.STATION_EXCESS_STOCKING_INT);
                updateUI();
                uNetwork.updateDevice(oDevice, new Interface_VolleyCallback() {
                    @Override
                    public void onSuccess() {
                        updateUI();
                    }

                    @Override
                    public void onFailure() {

                    }
                });
                Toast.makeText(Activity_Device.this, getString(R.string.device_stored_excess_stock), Toast.LENGTH_LONG).show();
                //if (usePrinter) mPrinter.printDeviceId(oDevice);

            }
        });
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_LKU_BOOKING)).commit();
    }

    @Override
    public void bookDeviceOutOfLKUStock() {
        uNetwork.bookDeviceOutOfLKUStock(oDevice, new Interface_VolleyCallback() {
            @Override
            public void onSuccess() {
                oDevice.getoStation().setId(Constants_Intern.STATION_UNKNOWN);
                //oDevice.setLKU(Constants_Intern.ID_UNKNOWN);
                updateUI();
                Log.i("tagg", "bookDeviceOutOfLKUSTock");
                mPrinter.printDeviceId(oDevice);
                etScan.setText("");
                openKeyboard(etScan);
            }

            @Override
            public void onFailure() {

            }
        });
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_LKU_BOOKING)).commit();
    }


    // Updates
    @Override
    public void updateUI() {
        ((Fragment_Device) fManager.findFragmentByTag(Constants_Intern.FRAGMENT_DEVICE)).updateUI(oDevice);
    }

    public void updateDevice() {
        uNetwork.updateDevice(oDevice, new Interface_VolleyCallback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailure() {

            }
        });
    }

    public void onScanned() {

    }

    public void requestDeviceBattery() {
        if (oDevice.getoModel().getlModelBatteries().size() > 1) {
            Fragment_Edit_Model_Battery_Select fragment = new Fragment_Edit_Model_Battery_Select();
            Bundle bData = new Bundle();
            bData.putString(Constants_Intern.INTERACTION_TITLE, getString(R.string.interaction_title_select_device_battery));
            bData.putInt(Constants_Intern.ID_MODEL, oDevice.getoModel().getkModel());
            bData.putInt(Constants_Intern.TYPE_MODE, Constants_Intern.TYPE_MODE_SELECT);
            fragment.setArguments(bData);
            fManager.beginTransaction().replace(R.id.flFragmentInteraction, fragment, Constants_Intern.FRAGMENT_MULTIPLE_CHOICE_MODEL_BATTERY_SELECT).commit();
        }
    }

    // Returns
    @Override
    public void returnDefaultExploitation(int exploitation) {
        oDevice.getoModel().settDefaultExploitation(exploitation);
        if (oDevice.getoModel().gettDefaultExploitation() == Constants_Intern.EXPLOITATION_RECYCLING) {
            uNetwork.exploitRecycling(oDevice);
        } else {
            uNetwork.exploitReuse(oDevice);
        }
        handledReturnDefaultExploitation();
    }

    @Override
    public void returnName(final String name, int id) {
        oDevice.getoModel().setName(name);

        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_BY_NAME + name, null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (oJson.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                    oDevice.setoModel(new Model(Activity_Device.this, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL)));
                    cVolley.execute(Request.Method.PUT, Urls.URL_PUT_MODEL_TAC + oDevice.getoModel().getkModel() + "/" + oDevice.getTAC(), null);
                    handledReturnModel();
                } else {
                    cVolley.getResponse(Request.Method.PUT, Urls.URL_PUT_MODEL_ADD + name, null, new Interface_VolleyResult() {
                        @Override
                        public void onResult(JSONObject oJson) throws JSONException {
                            oDevice.setoModel(new Model(Activity_Device.this, oJson.getJSONObject(Constants_Extern.OBJECT_MODEL)));
                            handledReturnModel();
                        }
                    });
                }
            }
        });

        /*
        uNetwork.getIdModel_Name(oDevice, new Interface_VolleyCallback() {
            @Override
            public void onSuccess() {
                handledReturnModel();
            }

            @Override
            public void onFailure() {
                Log.i("Ja", "was los");
                uNetwork.addModel(oDevice, new Interface_VolleyCallback_Int() {
                    @Override
                    public void onSuccess(int i) {
                        oDevice.getoModel().setkModel(i);
                        handledReturnModel();
                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }
        });
        */
    }

    public void returnCondition(int condition) {
        oDevice.setCondition(condition);
        if (oDevice.getCondition() == Constants_Intern.CONDITION_BROKEN)
            oDevice.getoModel().settDefaultExploitation(Constants_Intern.EXPLOITATION_RECYCLING);
        handledReturnCondition();
    }

    @Override
    public void onClickInteractionButton(String tFragment, int tButton) {
        switch (tFragment) {
            case Constants_Intern.FRAGMENT_REQUEST_BATTERY_CONTAINED:
                switch (tButton) {
                    case Constants_Intern.BUTTON_ONE:
                        oDevice.setBatteryContained(true);
                        if (oDevice.getoModel().getlModelBatteries() != null && oDevice.getoModel().getlModelBatteries().size() == 1) {
                            oDevice.setoBattery(oDevice.getoModel().getoBattery());
                        }
                        break;
                    case Constants_Intern.BUTTON_TWO:
                        oDevice.setBatteryContained(false);
                        break;
                }
                updateUI();
                handledReturnBatteryContained();
                break;
            case Constants_Intern.FRAGMENT_REQUEST_BATTERY_REMOVABLE:
                switch (tButton) {
                    case Constants_Intern.BUTTON_ONE:
                        oDevice.getoModel().setBatteryRemovable(true);
                        break;
                    case Constants_Intern.BUTTON_TWO:
                        oDevice.getoModel().setBatteryRemovable(false);
                        if (oDevice.isBatteryContained() == null) oDevice.setBatteryContained(true);
                        break;
                }
                updateUI();
                handledReturnBatteryRemovable();
                break;
            case Constants_Intern.FRAGMENT_REQUEST_BACKCOVER_REMOVABLE:
                switch (tButton) {
                    case Constants_Intern.BUTTON_ONE:
                        oDevice.getoModel().setBackcoverRemovable(true);
                        break;
                    case Constants_Intern.BUTTON_TWO:
                        oDevice.getoModel().setBackcoverRemovable(false);
                        if (oDevice.isBackcoverContained() == null)
                            oDevice.setbBackcoverContained(true);
                        break;
                }
                updateUI();
                handledReturnBackcoverRemovable();
                break;
            case Constants_Intern.FRAGMENT_INTERACTION_EXPLOITATION:
                switch (tButton) {
                    case Constants_Intern.BUTTON_ONE:
                        oDevice.getoModel().settDefaultExploitation(Constants_Intern.EXPLOITATION_RECYCLING);
                        break;
                    case Constants_Intern.BUTTON_TWO:
                        oDevice.getoModel().settDefaultExploitation(Constants_Intern.EXPLOITATION_INTACT_REUSE);
                        break;
                    case Constants_Intern.BUTTON_THREE:
                        oDevice.getoModel().settDefaultExploitation(Constants_Intern.EXPLOITATION_DEFECT_REUSE);
                        break;
                    case Constants_Intern.BUTTON_FOUR:

                        break;
                }
                updateUI();
                handledReturnDefaultExploitation();
                break;
            case Constants_Intern.FRAGMENT_INTERACTION_SHAPE:
                int tShape = 0;
                Log.i("Shappe:", Integer.toString(tShape));
                switch (tButton) {
                    case Constants_Intern.BUTTON_ONE:
                        tShape = 1;
                        break;
                    case Constants_Intern.BUTTON_TWO:
                        tShape = 2;
                        break;
                    case Constants_Intern.BUTTON_FOUR:
                        Log.i("button_four", "called");
                        tShape = 3;
                        break;
                    case Constants_Intern.BUTTON_FIVE:
                        tShape = 4;
                        break;
                    case Constants_Intern.BUTTON_SEVEN:
                        tShape = 5;
                        break;
                }
                oDevice.setoShape(new Shape(tShape));
                if (tShape == Constants_Intern.SHAPE_BROKEN) {
                    // Get Model Damage
                    cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_DAMAGE + oDevice.getoModel().getkModel(), null, new Interface_VolleyResult() {
                        @Override
                        public void onResult(JSONObject oJson) throws JSONException {
                            if (Volley_Connection.successfulResponse(oJson)) {
                                // Damages found
                                JSONArray aJson = oJson.getJSONArray(Constants_Extern.LIST_DAMAGES);
                                ArrayList<Object_Model_Damage> lModelDamages = new ArrayList<>();
                                for (int i = 0; i < aJson.length(); i++) {
                                    JSONObject oJsonModelDamage = aJson.getJSONObject(i);
                                    Object_Model_Damage oModelDamage = new Object_Model_Damage(Activity_Device.this, oJsonModelDamage);
                                    lModelDamages.add(oModelDamage);
                                }
                                for (Object_Device_Damage oDeviceDamage : oDevice.getlDeviceDamages()) {
                                    for (Object_Model_Damage oModelDamage : lModelDamages) {
                                        if (oDeviceDamage.getoModelDamage().equals(oModelDamage)) {
                                            lModelDamages.remove(oModelDamage);
                                        }
                                    }
                                }


                                JSONArray aJsonDeviceDamages = new JSONArray();
                                for (Object_Device_Damage oDeviceDamageDamage : oDevice.getlDeviceDamages()) {
                                    aJsonDeviceDamages.put(oDeviceDamageDamage.getJson());
                                }
                                Log.i("Pre Device Damages: ", aJsonDeviceDamages.toString());
                                JSONArray aJsonModelDamages = new JSONArray();
                                for (Object_Model_Damage oModelDamage : lModelDamages) {
                                    aJsonModelDamages.put(oModelDamage.getJson());
                                }
                                Log.i("Pre Model Damages: ", aJsonModelDamages.toString());

                                Bundle bData = new Bundle();
                                bData.putInt(Constants_Intern.ID_DEVICE, oDevice.getIdDevice());
                                bData.putString(Constants_Intern.STRING_MODEL_DAMAGES, aJsonModelDamages.toString());
                                bData.putString(Constants_Intern.STRING_DEVICE_DAMAGES, aJsonDeviceDamages.toString());
                                fragmentInteraction(new Fragment_Edit_Device_Damages(), bData, Constants_Intern.FRAGMENT_INTERACTION_DEVICE_DAMAGES);

                            } else {
                                // No damages found
                                switch (oDevice.getoModel().gettDefaultExploitation()) {
                                    case Constants_Intern.DEFAULT_EXPLOITATION_REUSE:
                                        oDevice.settState(Constants_Intern.STATE_RECYCLING);
                                        updateUI();
                                        handledReturnShape();
                                        break;
                                    case Constants_Intern.DEFAULT_EXPLOITATION_DEFECT_REUSE:
                                        oDevice.settState(Constants_Intern.STATE_DEFECT_REUSE);
                                        updateUI();
                                        handledReturnShape();
                                        break;
                                }
                            }
                        }
                    });
                }


                if (tShape < 5) {
                    Log.i("Shappeeee:", Integer.toString(tShape));
                    oDevice.settState(Constants_Intern.STATE_INTACT_REUSE);
                    updateUI();
                    handledReturnShape();
                }
                break;
            case Constants_Intern.FRAGMENT_INTERACTION_BACKCOVER_CONTAINED:
                switch (tButton) {
                    case Constants_Intern.BUTTON_ONE:
                        oDevice.setbBackcoverContained(true);
                        break;
                    case Constants_Intern.BUTTON_TWO:
                        oDevice.setbBackcoverContained(false);
                        break;
                }
                updateUI();
                handledReturnBackcoverContained();
                break;
            case Constants_Intern.FRAGMENT_REQUEST_DEFAULT_EXPLOITATION:
                int tDefaultExploitation = 0;
                switch (tButton) {
                    case Constants_Intern.BUTTON_ONE:
                        tDefaultExploitation = 1;
                        break;
                    case Constants_Intern.BUTTON_TWO:
                        tDefaultExploitation = 2;
                        break;
                    case Constants_Intern.BUTTON_THREE:
                        tDefaultExploitation = 3;
                        break;
                }
                oDevice.getoModel().settDefaultExploitation(tDefaultExploitation);
                oDevice.settState(Constants_Intern.STATE_UNKNOWN);
//                if (oDevice.getoModel().gettDefaultExploitation() == Constants_Intern.EXPLOITATION_RECYCLING) {
//                    uNetwork.exploitRecycling(oDevice);
//                } else {
//                    uNetwork.exploitReuse(oDevice);
//                }
                handledReturnDefaultExploitation();
                break;
            case Constants_Intern.FRAGMENT_MULTIPLE_CHOICE_DEVICE_DAMAGES:
                switch (tButton) {
                    case Constants_Intern.BUTTON_OVERBROKEN:
                        oDevice.settState(Constants_Intern.STATE_RECYCLING);
                        break;
                    case Constants_Intern.BUTTON_OTHER_DAMAGES:
                        oDevice.setlDeviceDamages(new ArrayList<Object_Device_Damage>());
                        switch (oDevice.getoModel().gettDefaultExploitation()) {
                            case Constants_Intern.DEFAULT_EXPLOITATION_REUSE:
                                oDevice.settState(Constants_Intern.STATE_RECYCLING);
                                break;
                            case Constants_Intern.DEFAULT_EXPLOITATION_DEFECT_REUSE:
                                oDevice.settState(Constants_Intern.STATE_DEFECT_REUSE);
                                break;
                        }
                        break;
                }
                handledReturnDeviceDamages();
                break;
            case Constants_Intern.FRAGMENT_REQUEST_PHONE_TYPE:
                switch (tButton) {
                    case Constants_Intern.BUTTON_TYPE_PHONE_HANDY:
                        oDevice.getoModel().settPhone(Constants_Intern.TYPE_PHONE_HANDY);
                        break;
                    case Constants_Intern.BUTTON_TYPE_PHONE_SMARTPHONE:
                        oDevice.getoModel().settPhone(Constants_Intern.TYPE_PHONE_SMARTPHONE);
                        break;
                }
                handledReturnTypePhone();
        }
    }


    @Override
    public void onClickInteractionMultipleChoiceCommit(String tFragment, ArrayList<Additive> lAdditive) {
        switch (tFragment) {
            case Constants_Intern.FRAGMENT_INTERACTION_MULTIPLE_CHOICE_DAMAGES:

                break;
        }
    }


    @Override
    public void returnStation(Station station) {
    }

    @Override
    public void addColor(final Color color) {
        uNetwork.addColor(color, new Interface_VolleyCallback_Int() {
            @Override
            public void onSuccess(int i) {
                color.setId(i);
                oDevice.setoColor(color);
                handledReturnAddColor();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void updateColor(Color color) {
        uNetwork.updateColor(color, new Interface_VolleyCallback() {
            @Override
            public void onSuccess() {
                handledReturnUpdateColor();
            }

            @Override
            public void onFailure() {

            }
        });
    }

    @Override
    public void fragmentColorUpdate() {
        fragmentInteraction(new Fragment_Color_Add(), null, Constants_Intern.FRAGMENT_COLOR_UPDATE);
    }

    @Override
    public void fragmentColorAdd() {
        Bundle bundle = new Bundle();
        bundle.putSerializable(Constants_Intern.OBJECT_DEVICE, oDevice);
        fragmentInteraction(new Fragment_Color_Add(), bundle, Constants_Intern.FRAGMENT_COLOR_ADD);
    }

    @Override
    public void requestDamages() {
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_DAMAGE + oDevice.getoModel().getkModel(), null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (Volley_Connection.successfulResponse(oJson)) {
                    // Damages found
                    JSONArray aJson = oJson.getJSONArray(Constants_Extern.LIST_DAMAGES);
                    ArrayList<Object_Model_Damage> lModelDamages = new ArrayList<>();
                    for (int i = 0; i < aJson.length(); i++) {
                        JSONObject oJsonModelDamage = aJson.getJSONObject(i);
                        Object_Model_Damage oModelDamage = new Object_Model_Damage(Activity_Device.this, oJsonModelDamage);
                        lModelDamages.add(oModelDamage);
                    }
                    for (Object_Device_Damage oDeviceDamage : oDevice.getlDeviceDamages()) {
                        Iterator<Object_Model_Damage> iModelDamage = lModelDamages.iterator();
                        while (iModelDamage.hasNext()) {
                            Object_Model_Damage oModelDamage = iModelDamage.next();
                            if (oDeviceDamage.getoModelDamage().equals(oModelDamage)) {
                                iModelDamage.remove();
                            }
                        }
                    }

                    JSONArray aJsonDeviceDamages = new JSONArray();
                    for (Object_Device_Damage oDeviceDamageDamage : oDevice.getlDeviceDamages()) {
                        aJsonDeviceDamages.put(oDeviceDamageDamage.getJson());
                    }
                    Log.i("Pre Device Damages: ", aJsonDeviceDamages.toString());
                    JSONArray aJsonModelDamages = new JSONArray();
                    for (Object_Model_Damage oModelDamage : lModelDamages) {
                        aJsonModelDamages.put(oModelDamage.getJson());
                    }
                    Log.i("Pre Model Damages: ", aJsonModelDamages.toString());

                    Bundle bData = new Bundle();
                    bData.putInt(Constants_Intern.ID_DEVICE, oDevice.getIdDevice());
                    bData.putString(Constants_Intern.STRING_MODEL_DAMAGES, aJsonModelDamages.toString());
                    bData.putString(Constants_Intern.STRING_DEVICE_DAMAGES, aJsonDeviceDamages.toString());
                    fragmentInteraction(new Fragment_Edit_Device_Damages(), bData, Constants_Intern.FRAGMENT_INTERACTION_DEVICE_DAMAGES);

                } else {
                    // No damages found
                    Toast.makeText(Activity_Device.this, getString(R.string.no_damages_available), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void returnDeviceBattery(Battery oBattery) {
        oDevice.setoBattery(oBattery);
        handledReturnDeviceBattery();
    }

    @Override
    public void returnModelBatteries(ArrayList<Model_Battery> lModelBatteries) {
        oDevice.getoModel().setlModelBatteries(lModelBatteries);
        handledReturnModelBatteries();
    }

    public void fragmentInteraction(Fragment f, Bundle b, String tag) {
        if (b != null) {
            f.setArguments(b);
        }
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, f, tag).commit();
    }

    public void fragmentOverview(Fragment f, Bundle b, String tag) {
        if (b != null) {
            f.setArguments(b);
        }
        fManager.beginTransaction().replace(R.id.flFragmentOverview, f, tag).commit();
    }

    @Override
    public void returnManufacturer(Manufacturer manufacturer) {
        oDevice.getoModel().setoManufacturer(manufacturer);
        uNetwork.addManufacturerToModel(oDevice);
        updateUI();
        handledReturnManufacturer();
    }

    @Override
    public void returnCharger(Charger charger) {
        oDevice.getoModel().setoCharger(charger);
        uNetwork.connectChargerWithModel(oDevice);
        updateUI();
        handledReturnCharger();
    }

    @Override
    public void returnShape(Shape shape) {
        oDevice.setoShape(shape);
        if (shape.getId() == 5) {
            oDevice.settState(Constants_Intern.STATE_RECYCLING);
        } else {
            oDevice.settState(Constants_Intern.STATE_INTACT_REUSE);
        }
        updateUI();
        handledReturnShape();
    }

    @Override
    public void returnColor(Color color) {
        oDevice.setoColor(color);
        updateUI();
        handledReturnColor();
    }

    @Override
    public void continueWithRoutine() {

    }

    @Override
    public void returnBattery(final String name) {

        /*
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_BATTERY_BY_NAME + name, null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (Volley_Connection.successfulResponse(oJson)) {

                    Battery oBattery = new Battery(oJson.getJSONObject(Constants_Extern.OBJECT_BATTERY), Activity_Device.this);
                    oDevice.getoModel().setoBattery(oBattery);
                    oDevice.getoModel().addlModelBatteries(new Model_Battery(Activity_Device.this, oDevice.getoModel().getkModel(), oBattery, 0));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        public void run() {
                            handledReturnBattery();
                        }
                    }, 500);
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Device.this);
                    builder.setTitle(getString(R.string.add_battery, name));
                    builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onAdapterClick(DialogInterface dialogInterface, final int i) {
                            cVolley.getResponse(Request.Method.PUT, Urls.URL_PUT_BATTERY_ADD + name + "/" + oDevice.getoModel().getoManufacturer().getId(), null, new Interface_VolleyResult() {
                                @Override
                                public void onResult(JSONObject oJson) throws JSONException {
                                    if (Volley_Connection.successfulResponse(oJson)) {
                                        Battery oBattery = new Battery(oJson.getJSONObject(Constants_Extern.OBJECT_BATTERY), Activity_Device.this);
                                        cVolley.execute(Request.Method.GET, Urls.URL_GET_BATTERY_CONNECT_MODEL + oBattery.getId() + "/" + oDevice.getoModel().getkModel(), null);
                                        oDevice.getoModel().setoBattery(oBattery);
                                        Handler handler = new Handler();
                                        handler.postDelayed(new Runnable() {
                                            public void run() {
                                                handledReturnBattery();
                                            }
                                        }, 500);
                                    }
                                }
                            });
                        }
                    });
                    builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onAdapterClick(DialogInterface dialogInterface, int i) {
                            handledReturnBattery();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
        */

                /*
        uNetwork.getIdBattery(oDevice, name, new Interface_VolleyCallback_Int() {
            @Override
            public void onSuccess(int i) {
                oDevice.getoModel().setoBattery(new Battery(i, name));
                uNetwork.connectBatteryWithModel(oDevice); // Check if old battery is overwritten
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        handledReturnBattery();
                    }
                }, 500);
            }

            @Override
            public void onFailure() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Device.this);
                builder.setTitle(getString(R.string.add_battery, name));
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onAdapterClick(DialogInterface dialogInterface, int i) {
                        uNetwork.addBattery(name, oDevice, new Interface_VolleyCallback_Int() {
                            @Override
                            public void onSuccess(int i) {
                                oDevice.getoModel().setoBattery(new Battery(i, name));
                                uNetwork.connectBatteryWithModel(oDevice);
                                Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    public void run() {
                                        handledReturnBattery();
                                    }
                                }, 500);
                            }

                            @Override
                            public void onFailure() {

                            }
                        });
                    }
                });
                builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onAdapterClick(DialogInterface dialogInterface, int i) {
                        handledReturnBattery();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
        */
    }

    @Override
    public void returnDamages(ArrayList<Object_Device_Damage> lDeviceDamages) {
        oDevice.setlDeviceDamages(lDeviceDamages);
        boolean bBigRefurbishment = false;
        for (Object_Device_Damage oDeviceDamage : lDeviceDamages) {
            if (oDeviceDamage.getoModelDamage().gettRepairPlace() == Constants_Intern.WORK_STATION_BIG_REFURBISHMENT) {
                bBigRefurbishment = true;
                break;
            }
        }
        if (bBigRefurbishment) {
            oDevice.settState(Constants_Intern.STATE_DEFECT_REPAIR);
        } else {
            oDevice.settState(Constants_Intern.STATE_DEFECT_REPAIR);
        }
        handledReturnDeviceDamages();
    }

    @Override
    public void unknownBattery() {
        oDevice.setBatteryContained(false);
        handledReturnBattery();
    }

    // Handled Returns
    public void handledReturnModel() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST_NAME)).commit();
    }

    public void handledReturnDeviceDamages() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_INTERACTION_DEVICE_DAMAGES)).commit();
    }

    public void handledReturnBattery() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST_BATTERY)).commit();
    }

    public void handledReturnDeviceBattery() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_MULTIPLE_CHOICE_MODEL_BATTERY_SELECT)).commit();
    }

    public void handledReturnModelBatteries() {
        //fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST_BATTERY)).commit();
    }

    public void handledReturnManufacturer() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST_MANUFACTURER)).commit();
    }

    public void handledReturnTypePhone() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST_PHONE_TYPE)).commit();
    }

    public void handledReturnCharger() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST_CHARGER)).commit();
    }

    public void handledReturnDefaultExploitation() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST_DEFAULT_EXPLOITATION)).commit();
    }

    public void handledReturnColor() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST_COLOR)).commit();
    }

    public void handledReturnAddColor() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST_COLOR)).commit();
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_COLOR_ADD)).commit();
        Log.i("Why", "the fuck");
    }

    public void handledReturnUpdateColor() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_COLOR_UPDATE)).commit();
    }

    public void handledReturnShape() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_INTERACTION_SHAPE)).commit();
    }

    public void handledReturnCondition() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST_CONDITION)).commit();
    }

    public void handledReturnBatteryContained() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST_BATTERY_CONTAINED)).commit();
        updateUI();
    }

    public void handledReturnBackcoverContained() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_INTERACTION_BACKCOVER_CONTAINED)).commit();
        updateUI();
    }

    public void handledReturnBatteryRemovable() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST_BATTERY_REMOVABLE)).commit();
        updateUI();
    }

    public void handledReturnBackcoverRemovable() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST_BACKCOVER_REMOVABLE)).commit();
        updateUI();
    }

    public void handledPicturesTaken() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_TAKE_BACK_PICTURE)).commit();
    }

    public void handledDeviceUpdate() {
        updateUI();
    }

    // Resets
    public void resetDevice() {
        oDevice = new Device();
        etScan.setText("");
        etScan.requestFocus();
        if (fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST) != null) {
            fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST)).commit();
        }
        if (fManager.findFragmentByTag(Constants_Intern.FRAGMENT_LKU_BOOKING) != null) {
            fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_LKU_BOOKING)).commit();
        }
        updateUI();
    }

    public void totalReset() {
        resetDevice();
    }

    public void closeKeyboard(View v) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void openKeyboard(View v) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInputFromWindow(v.getApplicationWindowToken(), InputMethodManager.SHOW_FORCED, 0);
        v.requestFocus();
    }

    public void takePictures(int tPicture) {
        switch (tPicture) {
            case IMAGE_FRONT:
                Fragment_SimpleText fSimpleText = new Fragment_SimpleText();
                Bundle bundle = new Bundle();
                bundle.putString(Constants_Intern.TEXT_VALUE, getString(R.string.click_for_front_picture));
                fSimpleText.setArguments(bundle);
                fManager.beginTransaction().replace(R.id.flFragmentInteraction, fSimpleText, Constants_Intern.FRAGMENT_TAKE_FRONT_PICTURE).commit();
                break;
            case IMAGE_BACK:
                Fragment_SimpleText fSimpleText2 = new Fragment_SimpleText();
                Bundle bundle2 = new Bundle();
                bundle2.putString(Constants_Intern.TEXT_VALUE, getString(R.string.click_for_back_picture));
                fSimpleText2.setArguments(bundle2);
                fManager.beginTransaction().replace(R.id.flFragmentInteraction, fSimpleText2, Constants_Intern.FRAGMENT_TAKE_BACK_PICTURE).commitAllowingStateLoss();
                break;
            case IMAGE_SAVE:
                JSONObject oJson = new JSONObject();
                try {
                    oJson.put(Constants_Extern.IMAGE_DEVICE_FRONT, Utility_Camera.getStringImage(Utility_Camera.getImageFileFront(this)));
                    oJson.put(Constants_Extern.IMAGE_DEVICE_BACK, Utility_Camera.getStringImage(Utility_Camera.getImageFileBack(this)));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                cVolley.getResponse(Request.Method.POST, Urls.URL_UPLOAD_IMAGE_MODEL_COLOR + oDevice.getoModel().getkModel() + "/" + oDevice.getoColor().getId(), oJson, new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) throws JSONException {
                        if (oJson.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                            oDevice.getoColor().setkModelColor(oJson.getInt(Constants_Extern.ID_MODEL_COLOR));
                            handledPicturesTaken();
                        }
                    }
                });
        }


    }


    // Dialogs
    @Override
    public void onYes(int type) {
        switch (type) {
            case Constants_Intern.TYPE_FRAGMENT_DIALOG_TOTAL_RESET:
                totalReset();
        }
    }

    @Override
    public void onNo() {

    }

    // Convert
    public void convertJsonToDevice(JSONObject json) {
        try {
            if (!json.isNull(Constants_Extern.ID_MODEL))
                oDevice.getoModel().setkModel(json.getInt(Constants_Extern.ID_MODEL));
            if (!json.isNull(Constants_Extern.NAME_MODEL))
                oDevice.getoModel().setName(json.getString(Constants_Extern.NAME_MODEL));
            if (!json.isNull(Constants_Extern.EXPLOITATION))
                oDevice.getoModel().settDefaultExploitation(json.getInt(Constants_Extern.EXPLOITATION));
            if (!json.isNull(Constants_Extern.ID_MANUFACTURER) && !json.isNull(Constants_Extern.NAME_MANUFACTURER))
                oDevice.getoModel().setoManufacturer(new Manufacturer(json.getInt(Constants_Extern.ID_MANUFACTURER), json.getString(Constants_Extern.NAME_MANUFACTURER)));
            if (!json.isNull(Constants_Extern.ID_BATTERY) && !json.isNull(Constants_Extern.NAME_BATTERY))
                //oDevice.getoModel().setoBattery(new Battery(json.getInt(Constants_Extern.ID_BATTERY), json.getString(Constants_Extern.NAME_BATTERY), json.getInt(Constants_Extern.EXPLOITATION)));
            if (!json.isNull(Constants_Extern.ID_CHARGER) && !json.isNull(Constants_Extern.NAME_CHARGER))
                oDevice.getoModel().setoCharger(new Charger(json.getInt(Constants_Extern.ID_CHARGER), json.getString(Constants_Extern.NAME_CHARGER)));
            if (!json.isNull(Constants_Extern.IMEI))
                oDevice.setIMEI(json.getString(Constants_Extern.IMEI));
            if (!json.isNull(Constants_Extern.CONDITION))
                oDevice.setCondition(json.getInt(Constants_Extern.CONDITION));
            if (!json.isNull(Constants_Extern.ID_STATION))
                oDevice.setoStation(new Station(json.getInt(Constants_Extern.ID_STATION)));
            if (!json.isNull(Constants_Extern.ID_COLOR) && !json.isNull(Constants_Extern.NAME_COLOR) && !json.isNull(Constants_Extern.HEX_CODE))
                oDevice.setoColor
                        (new Color(json.getInt(Constants_Extern.ID_COLOR), json.getString(Constants_Extern.NAME_COLOR), json.getString(Constants_Extern.HEX_CODE)));
            if (!json.isNull(Constants_Extern.ID_SHAPE) && !json.isNull(Constants_Extern.NAME_SHAPE))
                oDevice.setoShape(new Shape(json.getInt(Constants_Extern.ID_SHAPE), json.getString(Constants_Extern.NAME_SHAPE)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(String cTag) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_TAKE_FRONT_PICTURE:
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    if (Utility_Camera.getImageFileFront(this).isDirectory())
                        Utility_Camera.deleteImageFileFront(this);
                    File file = Utility_Camera.getImageFileFront(this);
                    if (file != null) {
                        Uri photoURI = FileProvider.getUriForFile(this, "com.example.ericschumacher.bouncer", file);
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        if (Utility_Camera.hasPersmission(this)) {
                            fCurrentPicture = file.getAbsolutePath();
                            startActivityForResult(takePictureIntent, IMAGE_FRONT);
                        } else {
                            Utility_Camera.requestPermission(this);
                        }

                    }
                }
                break;
            case Constants_Intern.FRAGMENT_TAKE_BACK_PICTURE:
                Intent takePictureIntent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent2.resolveActivity(getPackageManager()) != null) {
                    if (Utility_Camera.getImageFileBack(this).isDirectory())
                        Utility_Camera.deleteImageFileBack(this);
                    File file = Utility_Camera.getImageFileBack(this);
                    if (file != null) {
                        Uri photoURI = FileProvider.getUriForFile(this, "com.example.ericschumacher.bouncer", file);
                        takePictureIntent2.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        if (Utility_Camera.hasPersmission(this)) {
                            fCurrentPicture = file.getAbsolutePath();
                            startActivityForResult(takePictureIntent2, IMAGE_BACK);
                        } else {
                            Utility_Camera.requestPermission(this);
                        }

                    }
                }
                break;
        }


    }

    @Override
    public void onLongClick(String cTag) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_TAKE_BACK_PICTURE:
            case Constants_Intern.FRAGMENT_TAKE_FRONT_PICTURE:
                oDevice.getoColor().setkModelColor(Constants_Intern.TAKE_NO_PICTURE);
                handledPicturesTaken();
                break;
        }
    }

    @Override
    public void returnSelectedModelBattery(Battery oBattery) {

    }

    @Override
    public void connectModelBattery(int kModel, int kBattery, Fragment_Edit_Model_Battery fragment) {

    }

    @Override
    public void deleteModelBattery(int kModelBattery) {

    }

    @Override
    public void returnRequestNameResult(String tFragment, Object object) {

        if (tFragment.equals(Constants_Intern.FRAGMENT_REQUEST_NAME_MODEL)) {
            if (object != null) {
                Model oModel = (Model)object;
                oDevice.setoModel(oModel);

                if (!oDevice.getTAC().equals(Constants_Extern.TAC_UNKNOWN)) cVolley.execute(Request.Method.PUT, Urls.URL_PUT_MODEL_TAC + oDevice.getoModel().getkModel() + "/" + oDevice.getTAC(), null);
                handledReturnModel();
            } else {
                oDevice.settState(Constants_Intern.STATE_MODEL_UNKNOWN);
                handledReturnModel();
            }

        }

        if (tFragment.equals(Constants_Intern.FRAGMENT_REQUEST_MODEL_BATTERY)) {
            if (object != null) {
                oDevice.getoModel().getlModelBatteries().add(new Model_Battery(this, oDevice.getoModel().getkModel(), (Battery)object, 0));
                oDevice.setoBattery(oDevice.getoModel().getoBattery());
                oDevice.getoModel().update();
                handledReturnModelBatteries();
            } else {
                //oDevice.getoModel().setbBatteryKnown(false);
                handledReturnModelBatteries();
            }
        }
        if (tFragment.equals(Constants_Intern.FRAGMENT_REQUEST_NAME_BATTERY_IN_EDIT_MODE)) {
            oDevice.getoModel().getlModelBatteries().add(new Model_Battery(this, oDevice.getoModel().getkModel(), (Battery)object, 0));
            oDevice.getoModel().update();
            iDevice.requestBattery(oDevice);
        }
        if (tFragment.equals(Constants_Intern.FRAGMENT_MULTIPLE_CHOICE_MODEL_BATTERY_SELECT)) {
            oDevice.getoModel().getlModelBatteries().add(new Model_Battery(this, oDevice.getoModel().getkModel(), (Battery)object, 0));
            oDevice.getoModel().update();
            requestDeviceBattery();
        }
    }

    @Override
    public void returnRequestNameUnknown(int tObject) {

    }
}
