package com.example.ericschumacher.bouncer.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ericschumacher.bouncer.Activities.Parent.Activity_Device;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Device;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Choice;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request_Condition;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Result;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Manager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Int;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Additive.Station;
import com.example.ericschumacher.bouncer.Objects.Additive.Variation_Color;
import com.example.ericschumacher.bouncer.Objects.Additive.Variation_Shape;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.R;

public class Activity_Bouncer extends Activity_Device implements Interface_Selection, View.OnClickListener, Interface_Manager {

    // Objects

    // Layout -
    EditText etScan;
    ImageView ivClearScan;
    TextView tvCollector;
    TextView tvCounterReuse;
    TextView tvCounterRecycling;
    TextView tvCounterTotal;
    FloatingActionButton fab;

    // Fragments
    FragmentManager fManager;

    // Interfaces
    Interface_Device iDevice;

    // Counter
    private int cRecycling = 0;
    private int cReuse = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Counter
        if (savedInstanceState != null) {
            cRecycling = savedInstanceState.getInt(Constants_Intern.COUNTER_RECYCLING, 0);
            cReuse = savedInstanceState.getInt(Constants_Intern.COUNTER_REUSE, 0);
        }

        // Objects
        oDevice = new Device();

        // Fragments
        fManager = getSupportFragmentManager();


        // Layout
        setLayout();
        handleInteraction();
        Fragment fDevice = new Fragment_Device();
        fManager.beginTransaction().replace(R.id.flFragmentDevice, fDevice, Constants_Intern.FRAGMENT_DEVICE).commit();

    }

    @Override
    protected void onResume() {
        super.onResume();
        iDevice = (Interface_Device) fManager.findFragmentByTag(Constants_Intern.FRAGMENT_DEVICE);
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateUI();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Bundle outState = new Bundle();
        outState.putInt(Constants_Intern.COUNTER_RECYCLING, cRecycling);
        outState.putInt(Constants_Intern.COUNTER_REUSE, cReuse);
        onSaveInstanceState(outState);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(Constants_Intern.COUNTER_RECYCLING, cRecycling);
        outState.putInt(Constants_Intern.COUNTER_REUSE, cReuse);
        super.onSaveInstanceState(outState);
    }

    // Layout
    private void setLayout() {
        setContentView(R.layout.activity_bouncer);
        etScan = findViewById(R.id.etScan);
        ivClearScan = findViewById(R.id.ivClearScan);
        tvCounterReuse = findViewById(R.id.tvCounterReuse);
        tvCounterRecycling = findViewById(R.id.tvCounterRecycling);
        tvCounterTotal = findViewById(R.id.tvCounterTotal);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(this);
        ivClearScan.setOnClickListener(this);
    }

    private void handleInteraction() {
        etScan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable editable) {
                if ((editable.toString() != "" && editable.toString().length() == 15) || editable.toString().equals("00000000")) {
                    if (editable.toString().equals("00000000")) {
                        oDevice.setIMEI("000000000000000");
                    } else {
                        oDevice.setIMEI(editable.toString());
                    }
                    // Hide keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etScan.getWindowToken(), 0);

                    // Get TAC
                    String tac = editable.toString().substring(0, 8);
                    uNetwork.getModelByTac(oDevice, new Interface_VolleyCallback() {
                        @Override
                        public void onSuccess() {
                            updateUI();
                            checkExploitation();
                        }

                        @Override
                        public void onFailure() {
                            iDevice.requestName();
                        }
                    });
                }
            }
        });
    }

    // Class Methods

    private void checkExploitation() {
        if (oDevice.getExploitation() == Constants_Intern.EXPLOITATION_RECYCLING) {
            oDevice.setDestination(Constants_Intern.EXPLOITATION_RECYCLING);
            showResult();
        }
        if (oDevice.getExploitation() == Constants_Intern.EXPLOITATION_REUSE){
            checkDetails();
        }
        if (oDevice.getExploitation() == Constants_Intern.EXPLOITATION_NULL) {
            iDevice.requestDefaultExploitation(oDevice);
        }
    }

    // Fragments
    @Override
    public void showResult() {

        Fragment_Result f = new Fragment_Result();
        Bundle b = new Bundle();
        b.putParcelable(Constants_Intern.OBJECT_MODEL, oDevice);
        f.setArguments(b);
        fManager.beginTransaction().replace(R.id.flFragmentRequest, f, Constants_Intern.FRAGMENT_REQUEST).commit();

        if (oDevice.getExploitation() == Constants_Intern.EXPLOITATION_RECYCLING) {
            cRecycling++;
        } else {
            cReuse++;
        }
        updateUI();
    }

    @Override
    public void setCondition(int condition) {
        oDevice.setCondition(condition);
        if (oDevice.getCondition() == Constants_Intern.CONDITION_BROKEN) {
            oDevice.setExploitation(Constants_Intern.EXPLOITATION_RECYCLING);
            oDevice.setDestination(Constants_Intern.EXPLOITATION_RECYCLING);
        } else {
            oDevice.setDestination(Constants_Intern.EXPLOITATION_REUSE);
        }
        checkConditionAndShape();
    }

    @Override
    public void finishDevice() {

        printLabel();
        reset();
    }

    @Override
    public void saveDevice() {
        if (oDevice.getDestination() == Constants_Intern.EXPLOITATION_RECYCLING || true) {

            reset();
        } else {
            oDevice.setDestination(2);
            oDevice.setStation(new Station(Constants_Intern.STATION_LKU_STOCKING));
            uNetwork.addDevice(oDevice, new Interface_VolleyCallback_Int() {
                @Override
                public void onSuccess(int i) {
                    oDevice.setIdDevice(i);
                    Log.i("Yeqh", "got that");
                    uNetwork.assignLku(oDevice, new Interface_VolleyCallback_Int() {
                        @Override
                        public void onSuccess(int i) {
                            oDevice.setLKU(i);
                            Toast.makeText(Activity_Bouncer.this, "LKU: "+Integer.toString(i), Toast.LENGTH_LONG).show();
                            if (usePrinter) mPrinter.printDevice(oDevice);
                            reset();
                        }

                        @Override
                        public void onFailure() {

                        }
                    });
                }

                @Override
                public void onFailure() {

                }
            });
        }
    }

    private void printLabel() {

    }

    private void startFragmentRequest(Fragment f) {
        Bundle b = new Bundle();
        b.putInt(Constants_Intern.SELECTION_ID_MODEL, oDevice.getIdModel());
        f.setArguments(b);
        fManager.beginTransaction().replace(R.id.flFragmentRequest, f, "fragment_name").commit();
    }

    private void startFragmentChoice(Bundle bundle) {
        Fragment_Request_Choice f = new Fragment_Request_Choice();
        f.setArguments(bundle);
        fManager.beginTransaction().replace(R.id.flFragmentRequest, f, "fragment_request_choice").commit();
    }

    // Interface - Network
    @Override
    public void exploitReuse(int idModel) {
        oDevice.setExploitation(Constants_Intern.EXPLOITATION_REUSE);
        uNetwork.exploitReuse(oDevice);
    }

    @Override
    public void exploitRecycling(int idModel) {
        oDevice.setExploitation(Constants_Intern.EXPLOITATION_RECYCLING);
        uNetwork.exploitRecycling(oDevice);
    }

    @Override
    public void checkName(final String name) {
        oDevice.setName(name);
        uNetwork.getIdModel_Name(oDevice, new Interface_VolleyCallback() {
            @Override
            public void onSuccess() {
                checkExploitation();
                updateUI();
            }

            @Override
            public void onFailure() {
                uNetwork.addModel(oDevice, new Interface_VolleyCallback_Int() {
                    @Override
                    public void onSuccess(int i) {
                        oDevice.setIdModel(i);
                        updateUI();
                        iDevice.requestDefaultExploitation(oDevice);
                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }
        });
    }

    @Override
    public void returnManufacturer(Manufacturer manufacturer) {
        oDevice.setManufacturer(manufacturer);
        uNetwork.addManufacturerToModel(oDevice);
        updateUI();
        checkDetails();
    }

    @Override
    public void returnCharger(Charger charger) {
        oDevice.setCharger(charger);
        uNetwork.connectChargerWithModel(oDevice);
        updateUI();
                checkDetails();
    }

    @Override
    public void returnColor(Variation_Color color) {
        oDevice.setVariationColor(color);
        updateUI();
        showResult();
    }

    @Override
    public void returnBattery(final String name) {
        uNetwork.getIdBattery(oDevice, name, new Interface_VolleyCallback_Int() {
            @Override
            public void onSuccess(int i) {
                oDevice.setBattery(new Battery(i, name));
                uNetwork.connectBatteryWithModel(oDevice);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        checkDetails();
                    }
                }, 500);
            }

            @Override
            public void onFailure() {
                AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Bouncer.this);
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
                                        checkDetails();
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
                        showResult();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public void returnShape(Variation_Shape shape) {
        oDevice.setVariationShape(shape);
        updateUI();
        checkConditionAndShape();
    }

    @Override
    public void checkDetails() {
        if (oDevice.getManufacturer() != null) {
            if (oDevice.getCharger() != null) {
                if (oDevice.getBattery() != null) {
                    checkConditionAndShape();
                } else {
                    iDevice.requestBattery(oDevice);
                }
            } else {
                iDevice.requestCharger(oDevice);
            }
        } else {
            iDevice.requestManufacturer();
        }
    }

    void checkConditionAndShape() {
        if (oDevice.getCondition() == Constants_Intern.CONDITION_UNKNOWN && oDevice.getExploitation() == Constants_Intern.EXPLOITATION_REUSE) {
            Fragment_Request_Condition f = new Fragment_Request_Condition();
            fManager.beginTransaction().replace(R.id.flFragmentRequest, f, Constants_Intern.FRAGMENT_REQUEST).commit();
        } else {
            if (oDevice.getVariationShape() == null && oDevice.getExploitation() == Constants_Intern.EXPLOITATION_REUSE) {
                Log.i("RequestShape", "Yes");
                iDevice.requestShape();
            } else {
                if (oDevice.getVariationColor() == null && oDevice.getExploitation() == Constants_Intern.EXPLOITATION_REUSE) {
                    iDevice.requestColor(oDevice);
                } else {
                    showResult();
                }
            }
        }
    }

    @Override
    public void reset() {
        oDevice = new Device();
        etScan.setText("");
        etScan.requestFocus();
        if (fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST) != null) {
            fManager.beginTransaction().remove(fManager.findFragmentByTag(Constants_Intern.FRAGMENT_REQUEST)).commit();
        }
        updateUI();
    }

    private void totalReset() {
        cReuse = 0;
        cRecycling = 0;
        reset();
    }

    @Override
    public void updateUI() {
        ((Fragment_Device) fManager.findFragmentByTag(Constants_Intern.FRAGMENT_DEVICE)).updateUI(oDevice);
        tvCounterRecycling.setText(Integer.toString(cRecycling));
        tvCounterReuse.setText(Integer.toString(cReuse));
        tvCounterTotal.setText(Integer.toString(cRecycling + cReuse));
    }

    @Override
    public Device getDevice() {
        return oDevice;
    }

    @Override
    public void returnDefaultExploitation(int exploitation) {
        super.returnDefaultExploitation(exploitation);
        if (oDevice.getExploitation() == Constants_Intern.EXPLOITATION_RECYCLING) {
            showResult();
        } else {
            checkDetails();
        }
    }

    @Override
    public Model getModel() {
        return oDevice;
    }

    @Override
    public void returnName(String name) {
        oDevice.setName(name);
        uNetwork.getIdModel_Name(oDevice, new Interface_VolleyCallback() {
            @Override
            public void onSuccess() {
                updateUI();
                checkExploitation();
            }

            @Override
            public void onFailure() {
                uNetwork.addModel(oDevice, new Interface_VolleyCallback_Int() {
                    @Override
                    public void onSuccess(int i) {
                        oDevice.setIdModel(i);
                        updateUI();
                        iDevice.requestDefaultExploitation(oDevice);
                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:

                totalReset();
                break;
            case R.id.ivClearScan:
                reset();
                break;
        }
    }


}
