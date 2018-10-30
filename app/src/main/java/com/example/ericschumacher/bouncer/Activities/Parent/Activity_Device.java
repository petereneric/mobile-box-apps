package com.example.ericschumacher.bouncer.Activities.Parent;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
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

import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Device;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog.Fragment_Dialog_Simple;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Dialog;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Manager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Int;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_JSON;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Additive.Station;
import com.example.ericschumacher.bouncer.Objects.Additive.Variation_Color;
import com.example.ericschumacher.bouncer.Objects.Additive.Variation_Shape;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;
import com.example.ericschumacher.bouncer.Zebra.ManagerPrinter;

import org.json.JSONException;
import org.json.JSONObject;

public class Activity_Device extends AppCompatActivity implements Interface_Manager, View.OnClickListener, Interface_Dialog {

    // Interfaces
    public Interface_Device iDevice;

    // Instances
    public Device oDevice;
    public Utility_Network uNetwork;

    // Printer
    public boolean usePrinter = true;
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Instances
        uNetwork = new Utility_Network(this);
        fManager = getSupportFragmentManager();
        if (usePrinter) mPrinter = new ManagerPrinter(this);

        // Layout
        setLayout();
        handleTextInput();

        // Fragments
        Fragment fDevice = new Fragment_Device();
        fManager.beginTransaction().replace(R.id.flFragmentDevice, fDevice, Constants_Intern.FRAGMENT_DEVICE).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        iDevice = (Interface_Device) fManager.findFragmentByTag(Constants_Intern.FRAGMENT_DEVICE);
        if (usePrinter) mPrinter.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (usePrinter) mPrinter.disconnect();
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
                if (!charSequence.toString().equals("")) onScan(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
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
        return oDevice;
    }

    @Override
    public Device getDevice() {
        return oDevice;
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
            uNetwork.getDeviceById(Integer.parseInt(text), new Interface_VolleyCallback_JSON() {
                @Override
                public void onResponse(JSONObject json) {
                    convertJsonToDevice(json);
                    updateUI();
                    onScanned();
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



        /*
        uNetwork.getDevice(Integer.parseInt(text), new Interface_VolleyCallback_Device() {
            @Override
            public void onSuccess(Device device) {
                oDevice = device;
                updateUI();
                if (oDevice.getStation().getId() == Constants_Intern.STATION_LKU_STOCKING_INT) {
                    oDevice.getStation().setId(Constants_Intern.STATION_UNKNOWN_INT);
                    uNetwork.updateDevice(oDevice, new Interface_VolleyCallback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onFailure() {

                        }
                    });
                    Toast.makeText(Activity_Device.this, getString(R.string.device_written_off), Toast.LENGTH_LONG).show();
                    mPrinter.printDevice(oDevice);
                } else {
                    uNetwork.assignLku(oDevice, new Interface_VolleyCallback_Int() {
                        @Override
                        public void onSuccess(int i) {
                            oDevice.setLKU(i);
                            oDevice.getStation().setId(Constants_Intern.STATION_LKU_STOCKING_INT);
                            updateUI();
                            uNetwork.updateDevice(oDevice, new Interface_VolleyCallback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onFailure() {

                                }
                            });
                            Toast.makeText(Activity_Device.this, getString(R.string.device_stored_lku), Toast.LENGTH_LONG).show();
                            mPrinter.printDevice(oDevice);
                        }

                        @Override
                        public void onFailure() {
                            // Platz scheint voll
                            oDevice.getStation().setId(Constants_Intern.STATION_EXCESS_STOCKING_INT);
                            updateUI();
                            uNetwork.updateDevice(oDevice, new Interface_VolleyCallback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onFailure() {

                                }
                            });
                            Toast.makeText(Activity_Device.this, getString(R.string.device_stored_excess_stock), Toast.LENGTH_LONG).show();
                            mPrinter.printDevice(oDevice);
                        }
                    });

                }
            }

            @Override
            public void onFailure() {

            }
        });
        */
    }

    @Override
    public void bookDeviceIntoLKUStock() {
        uNetwork.assignLku(oDevice, new Interface_VolleyCallback_Int() {
            @Override
            public void onSuccess(int i) {
                oDevice.setLKU(i);
                oDevice.getStation().setId(Constants_Intern.STATION_LKU_STOCKING_INT);
                updateUI();
                uNetwork.updateDevice(oDevice, new Interface_VolleyCallback() {
                    @Override
                    public void onSuccess() {
                        updateUI();
                        Log.i("tagg", "bookDeviceIntoOfLKUSTock - Success");
                        if (usePrinter) mPrinter.printDevice(oDevice);
                        resetDevice();
                    }

                    @Override
                    public void onFailure() {

                    }
                });
                Toast.makeText(Activity_Device.this, getString(R.string.device_stored_lku), Toast.LENGTH_LONG).show();
                if (usePrinter) mPrinter.printDevice(oDevice);
            }

            @Override
            public void onFailure() {
                // Device is already in LKU Stock
                oDevice.getStation().setId(Constants_Intern.STATION_LKU_STOCKING_INT);
                uNetwork.updateDevice(oDevice, new Interface_VolleyCallback() {
                    @Override
                    public void onSuccess() {
                        updateUI();
                        Log.i("tagg", "bookDeviceIntoOfLKUSTock - Failure");
                        if (usePrinter) mPrinter.printDevice(oDevice);
                    }

                    @Override
                    public void onFailure() {

                    }
                });
                // Platz scheint voll
                /*
                oDevice.getStation().setId(Constants_Intern.STATION_EXCESS_STOCKING_INT);
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
                if (usePrinter) mPrinter.printDevice(oDevice);
                */
            }
        });
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_LKU_BOOKING)).commit();
    }

    @Override
    public void bookDeviceOutOfLKUStock() {
        uNetwork.bookDeviceOutOfLKUStock(oDevice, new Interface_VolleyCallback() {
                    @Override
                    public void onSuccess() {
                        oDevice.getStation().setId(Constants_Intern.STATION_UNKNOWN_INT);
                        oDevice.setLKU(Constants_Intern.ID_UNKNOWN);
                        updateUI();
                        Log.i("tagg", "bookDeviceOutOfLKUSTock");
                        if (usePrinter) mPrinter.printDevice(oDevice);
                        resetDevice();
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

    // Returns
    @Override
    public void returnDefaultExploitation(int exploitation) {
        oDevice.setExploitation(exploitation);
        if (oDevice.getExploitation() == Constants_Intern.EXPLOITATION_RECYCLING) {
            uNetwork.exploitRecycling(oDevice);
        } else {
            uNetwork.exploitReuse(oDevice);
        }
        handledReturnDefaultExploitation();
    }

    @Override
    public void returnName(String name) {
        oDevice.setName(name);
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
                        oDevice.setIdModel(i);
                        handledReturnModel();
                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }
        });
    }

    public void returnCondition(int condition) {
        oDevice.setCondition(condition);
        if (oDevice.getCondition() == Constants_Intern.CONDITION_BROKEN) oDevice.setExploitation(Constants_Intern.EXPLOITATION_RECYCLING);
        handledReturnCondition();
    }



    @Override
    public void returnStation(Station station) {
    }

    @Override
    public void returnManufacturer(Manufacturer manufacturer) {
        oDevice.setManufacturer(manufacturer);
        uNetwork.addManufacturerToModel(oDevice);
        updateUI();
        handledReturnManufacturer();
    }

    @Override
    public void returnCharger(Charger charger) {
        oDevice.setCharger(charger);
        uNetwork.connectChargerWithModel(oDevice);
        updateUI();
        handledReturnCharger();
    }

    @Override
    public void returnShape(Variation_Shape shape) {
        oDevice.setVariationShape(shape);
        updateUI();
        handledReturnShape();
    }

    @Override
    public void returnColor(Variation_Color color) {
        oDevice.setVariationColor(color);
        updateUI();
        handledReturnColor();
    }

    @Override
    public void returnBattery(final String name) {
        uNetwork.getIdBattery(oDevice, name, new Interface_VolleyCallback_Int() {
            @Override
            public void onSuccess(int i) {
                oDevice.setBattery(new Battery(i, name));
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
                    public void onClick(DialogInterface dialogInterface, int i) {
                        uNetwork.addBattery(name, oDevice, new Interface_VolleyCallback_Int() {
                            @Override
                            public void onSuccess(int i) {
                                oDevice.setBattery(new Battery(i, name));
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
                    public void onClick(DialogInterface dialogInterface, int i) {
                        handledReturnBattery();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public void unknownBattery() {

    }

    // Handled Returns
    public void handledReturnModel() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST_NAME)).commit();
    }

    public void handledReturnBattery() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST_BATTERY)).commit();
    }

    public void handledReturnManufacturer() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST_MANUFACTURER)).commit();
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

    public void handledReturnShape() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST_SHAPE)).commit();
    }

    public void handledReturnCondition() {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST_CONDITION)).commit();
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
                oDevice.setIdModel(json.getInt(Constants_Extern.ID_MODEL));
            if (!json.isNull(Constants_Extern.NAME_MODEL))
                oDevice.setName(json.getString(Constants_Extern.NAME_MODEL));
            if (!json.isNull(Constants_Extern.EXPLOITATION_DEFAULT))
                oDevice.setDefaultExploitation(json.getInt(Constants_Extern.EXPLOITATION_DEFAULT));
            if (!json.isNull(Constants_Extern.ID_MANUFACTURER) && !json.isNull(Constants_Extern.NAME_MANUFACTURER))
                oDevice.setManufacturer(new Manufacturer(json.getInt(Constants_Extern.ID_MANUFACTURER), json.getString(Constants_Extern.NAME_MANUFACTURER)));
            if (!json.isNull(Constants_Extern.ID_BATTERY) && !json.isNull(Constants_Extern.NAME_BATTERY))
                oDevice.setBattery(new Battery(json.getInt(Constants_Extern.ID_BATTERY), json.getString(Constants_Extern.NAME_BATTERY)));
            if (!json.isNull(Constants_Extern.ID_CHARGER) && !json.isNull(Constants_Extern.NAME_CHARGER))
                oDevice.setCharger(new Charger(json.getInt(Constants_Extern.ID_CHARGER), json.getString(Constants_Extern.NAME_CHARGER)));
            if (!json.isNull(Constants_Extern.ID_DEVICE))
                oDevice.setIdDevice(json.getInt(Constants_Extern.ID_DEVICE));
            if (!json.isNull(Constants_Extern.IMEI))
                oDevice.setIMEI(json.getString(Constants_Extern.IMEI));
            if (!json.isNull(Constants_Extern.CONDITION))
                oDevice.setCondition(json.getInt(Constants_Extern.CONDITION));
            if (!json.isNull(Constants_Extern.DESTINATION))
                oDevice.setDestination(json.getInt(Constants_Extern.DESTINATION));
            if (!json.isNull(Constants_Extern.ID_STATION))
                oDevice.setStation(new Station(json.getInt(Constants_Extern.ID_STATION)));
            if (!json.isNull(Constants_Extern.LKU))
                oDevice.setLKU((json.getInt(Constants_Extern.LKU)));
            if (oDevice.getLKU() == Constants_Intern.STATION_LKU_STOCKING_INT) oDevice.getStation().setId(Constants_Intern.STATION_LKU_STOCKING_INT);
            if (!json.isNull(Constants_Extern.ID_COLOR) && !json.isNull(Constants_Extern.NAME_COLOR) && !json.isNull(Constants_Extern.HEX_CODE))
                oDevice.setVariationColor
                        (new Variation_Color(json.getInt(Constants_Extern.ID_COLOR), json.getString(Constants_Extern.NAME_COLOR), json.getString(Constants_Extern.HEX_CODE)));
            if (!json.isNull(Constants_Extern.ID_SHAPE) && !json.isNull(Constants_Extern.NAME_SHAPE))
                oDevice.setVariationShape(new Variation_Shape(json.getInt(Constants_Extern.ID_SHAPE), json.getString(Constants_Extern.NAME_SHAPE)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
