package com.example.ericschumacher.bouncer.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
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
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Choice;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Name_Battery;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Model_Name;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request_Condition;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request_Exploitation;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request_Shape;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Result;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Model;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Choice;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Int;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Object_Choice;
import com.example.ericschumacher.bouncer.Objects.Supplement.Variation_Color;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

import java.util.ArrayList;

public class Activity_Bouncer extends AppCompatActivity implements Interface_Selection, View.OnClickListener, Interface_Model {

    // Utilities
    Utility_Network uNetwork;

    // Objects
    Device oDevice;

    // Layout - Device
    TableRow trLKU;
    TableRow trDestination;
    TableRow trStation;
    TableRow trShape;
    TableRow trColor;

    // Layout -
    EditText etScan;
    ImageView ivClearScan;
    TextView tvCollector;
    TextView tvCounterReuse;
    TextView tvCounterRecycling;
    TextView tvCounterTotal;
    TextView tvName;
    TextView tvManufacturer;
    TextView tvCharger;
    TextView tvBattery;
    TextView tvLKU;
    TextView tvColor;
    FloatingActionButton fab;

    // Fragments
    FragmentManager fManager;

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

        // Utilities
        uNetwork = new Utility_Network(this);


        // Objects
        oDevice = new Device();

        // Fragments
        fManager = getSupportFragmentManager();

        // Layout
        setLayout();
        handleInteraction();
        updateUI();

        // Printer

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(Constants_Intern.COUNTER_RECYCLING, cRecycling);
        outState.putInt(Constants_Intern.COUNTER_REUSE, cReuse);
    }

    // Layout
    private void setLayout() {
        setContentView(R.layout.activity_bouncer);
        etScan = findViewById(R.id.et_scan);
        ivClearScan = findViewById(R.id.ivClearScan);
        tvCollector = findViewById(R.id.tvCollector);
        tvCounterReuse = findViewById(R.id.tvCounterReuse);
        tvCounterRecycling = findViewById(R.id.tvCounterRecycling);
        tvCounterTotal = findViewById(R.id.tvCounterTotal);
        tvName = findViewById(R.id.tvName);
        tvManufacturer = findViewById(R.id.tvManufacturer);
        tvCharger = findViewById(R.id.tvCharger);
        tvBattery = findViewById(R.id.tvBattery);
        tvLKU = findViewById(R.id.tvLKU);
        tvColor = findViewById(R.id.tvColor);
        trColor = findViewById(R.id.trColor);
        trLKU = findViewById(R.id.trLKU);
        fab = findViewById(R.id.fab);

        fab.setOnClickListener(this);
        ivClearScan.setOnClickListener(this);
        trColor.setOnClickListener(this);
        trLKU.setOnClickListener(this);
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
                            startFragmentRequestName();
                        }
                    });
                }
            }
        });
    }

    // Class Methods

    private void checkExploitation() {
        uNetwork.getLKU(oDevice, new Interface_VolleyCallback_Int() {
            @Override
            public void onSuccess(int i) {
                Log.i("checkExploitation()", "Found LKU");
                oDevice.setLKU(i);
                oDevice.setExploitation(Constants_Intern.EXPLOITATION_REUSE);
                updateUI();
                checkDetails();
            }

            @Override
            public void onFailure() {
                uNetwork.checkExploitation(oDevice, new Interface_VolleyCallback_Int() {
                    @Override
                    public void onSuccess(int i) {
                        oDevice.setExploitation(i);
                        if (oDevice.getExploitation() == Constants_Intern.EXPLOITATION_RECYCLING) {
                            startFragmentResult();
                        } else {
                            checkDetails();
                        }
                    }

                    @Override
                    public void onFailure() {
                        startFragmentExploitation();
                    }
                });
            }
        });
    }

    // Fragments
    @Override
    public void startFragmentResult() {
        Fragment_Result f = new Fragment_Result();
        Bundle b = new Bundle();
        b.putParcelable(Constants_Intern.OBJECT_MODEL, oDevice);
        f.setArguments(b);
        fManager.beginTransaction().replace(R.id.fl_input_output, f, "fragment_result").commit();

        if (oDevice.getExploitation() == Constants_Intern.EXPLOITATION_RECYCLING) {
            cRecycling++;
        } else {
            cReuse++;
        }
        updateUI();
    }

    @Override
    public void setShape(int shape) {
        oDevice.setShape(shape);
        // Check mindest shape in DB
        checkConditionAndShape();
    }

    @Override
    public void setCondition(int condition) {
        oDevice.setCondition(condition);
        if (oDevice.getCondition() == Constants_Intern.CONDITION_BROKEN) {
            oDevice.setExploitation(Constants_Intern.EXPLOITATION_RECYCLING);
        }
        checkConditionAndShape();
    }

    @Override
    public void setModel(int id, int name) {

    }

    @Override
    public void finishDevice() {

        printLabel();
        reset();
    }

    private void printLabel() {

    }

    private void startFragmentExploitation() {
        Fragment_Request_Exploitation f = new Fragment_Request_Exploitation();
        Bundle b = new Bundle();
        b.putInt(Constants_Intern.SELECTION_ID_MODEL, oDevice.getId());
        f.setArguments(b);
        fManager.beginTransaction().replace(R.id.flFragmentChoiceAndSearch, f, "fragment_exploitation").commit();
    }

    private void startFragmentRequestName() {
        Fragment_Request_Model_Name f = new Fragment_Request_Model_Name();
        fManager.beginTransaction().replace(R.id.flFragmentChoiceAndSearch, f, "fragment_name_model").commit();
    }

    private void startFragmentRequest(Fragment f) {
        Bundle b = new Bundle();
        b.putInt(Constants_Intern.SELECTION_ID_MODEL, oDevice.getId());
        f.setArguments(b);
        fManager.beginTransaction().replace(R.id.fl_input_output, f, "fragment_name").commit();
    }

    private void startFragmentChoice(Bundle bundle) {
        Fragment_Request_Choice f = new Fragment_Request_Choice();
        f.setArguments(bundle);
        fManager.beginTransaction().replace(R.id.fl_input_output, f, "fragment_request_choice").commit();
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
        uNetwork.getIdModel_Name(oDevice, new Interface_VolleyCallback_Int() {
            @Override
            public void onSuccess(int i) {
                oDevice.setId(i);
                oDevice.setName(name);
                checkExploitation();
                updateUI();
            }

            @Override
            public void onFailure() {
                uNetwork.addModel(oDevice, new Interface_VolleyCallback_Int() {
                    @Override
                    public void onSuccess(int i) {
                        oDevice.setId(i);
                        oDevice.setName(name);
                        updateUI();
                        startFragmentExploitation();
                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }
        });
    }

    @Override
    public void callbackManufacturer(int id, String name) {
        oDevice.setIdManufacturer(id);
        oDevice.setNameManufacturer(name);
        uNetwork.addManufacturerToModel(oDevice);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                checkDetails();
            }
        }, 700);
    }

    @Override
    public void callbackCharger(int id, String name) {
        oDevice.setIdCharger(id);
        oDevice.setNameCharger(name);
            uNetwork.connectChargerWithModel(oDevice);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                checkDetails();
            }
        }, 700);
    }

    @Override
    public void callbackColor(int id, String name) {
        Variation_Color vColor = new Variation_Color(id, name);
        oDevice.setIdColor(id);
        oDevice.setNameColor(name);
        uNetwork.getIdModelColor(oDevice, new Interface_VolleyCallback_Int() {
            @Override
            public void onSuccess(int i) {
                oDevice.setIdModelColor(i);
                startFragmentResult();
            }

            @Override
            public void onFailure() {
                uNetwork.addModelColor(oDevice, new Interface_VolleyCallback_Int() {
                    @Override
                    public void onSuccess(int i) {
                        oDevice.setIdModelColor(i);

                        startFragmentResult();
                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
        });
    }

    @Override
    public void checkBattery(final String name) {
        uNetwork.getIdBattery(oDevice, name, new Interface_VolleyCallback_Int() {
            @Override
            public void onSuccess(int i) {
                oDevice.setIdBattery(i);
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
                        oDevice.setNameBattery(name);
                        uNetwork.addBattery(oDevice, new Interface_VolleyCallback_Int() {
                            @Override
                            public void onSuccess(int i) {
                                oDevice.setIdBattery(i);
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
                        startFragmentResult();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public void checkDetails() {
        if (oDevice.getManufacturer() != null) {
            if (oDevice.getCharger() != null) {
                if (oDevice.getBattery() != null) {
                    checkConditionAndShape();
                } else {
                    startFragmentRequest(new Fragment_Request_Name_Battery());
                }
            } else {
                uNetwork.getChargers(oDevice, new Interface_VolleyCallback_ArrayList_Choice() {
                    @Override
                    public void onSuccess(ArrayList<Object_Choice> list) {
                        Bundle b = new Bundle();
                        b.putParcelableArrayList(Constants_Intern.LIST_CHOICE, list);
                        startFragmentChoice(b);
                    }
                });
            }
        } else {
            uNetwork.getManufactures(new Interface_VolleyCallback_ArrayList_Choice() {
                @Override
                public void onSuccess(ArrayList<Object_Choice> list) {
                    Bundle b = new Bundle();
                    b.putParcelableArrayList(Constants_Intern.LIST_CHOICE, list);
                    startFragmentChoice(b);
                }
            });
        }


        /*
        uNetwork.getManufacturer(oDevice, new Interface_VolleyCallback_JSON() {
            @Override
            public void onSuccess(JSONObject json) {
                try {
                    oDevice.setIdManufacturer(json.getInt(Constants_Extern.ID_MANUFACTURER));
                    oDevice.setNameManufacturer(json.getString(Constants_Extern.NAME_MANUFACTURER));
                    updateUI();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                updateUI();
                uNetwork.getCharger(oDevice, new Interface_VolleyCallback_JSON() {
                    @Override
                    public void onSuccess(JSONObject json) {
                        try {
                            oDevice.setIdCharger(json.getInt(Constants_Extern.ID_CHARGER));
                            oDevice.setNameCharger(json.getString(Constants_Extern.NAME_CHARGER));
                            updateUI();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        uNetwork.getBattery(oDevice, new Interface_VolleyCallback_JSON() {
                            @Override
                            public void onSuccess(JSONObject json) {
                                try {
                                    oDevice.setIdBattery(json.getInt(Constants_Extern.ID_BATTERY));
                                    oDevice.setNameBattery(json.getString(Constants_Extern.NAME_BATTERY));
                                    updateUI();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                                checkConditionAndShape();
                            }

                            @Override
                            public void onFailure(JSONObject json) {
                                startFragmentRequest(new Fragment_Request_Name_Battery());
                            }
                        });
                    }

                    @Override
                    public void onFailure(JSONObject json) {
                        uNetwork.getChargers(oDevice, new Interface_VolleyCallback_ArrayList_Choice() {
                            @Override
                            public void onSuccess(ArrayList<Object_Choice> list) {
                                Bundle b = new Bundle();
                                b.putParcelableArrayList(Constants_Intern.LIST_CHOICE, list);
                                startFragmentChoice(b);
                            }
                        });
                    }
                });
            }

            @Override
            public void onFailure(JSONObject json) {
                uNetwork.getManufactures(new Interface_VolleyCallback_ArrayList_Choice() {
                    @Override
                    public void onSuccess(ArrayList<Object_Choice> list) {
                        Bundle b = new Bundle();
                        b.putParcelableArrayList(Constants_Intern.LIST_CHOICE, list);
                        startFragmentChoice(b);
                    }
                });
            }
        });
        */
    }

    void checkConditionAndShape() {
        if (oDevice.getCondition() == Constants_Intern.CONDITION_NOT_SET && oDevice.getExploitation() == Constants_Intern.EXPLOITATION_REUSE) {
            Fragment_Request_Condition f = new Fragment_Request_Condition();
            fManager.beginTransaction().replace(R.id.flFragmentChoiceAndSearch, f, "fragment_request_condition").commit();
        } else {
            if (oDevice.getVariationShape() == null && oDevice.getExploitation() == Constants_Intern.EXPLOITATION_REUSE) {
                Fragment_Request_Shape f = new Fragment_Request_Shape();
                fManager.beginTransaction().replace(R.id.flFragmentChoiceAndSearch, f, "fragment_request_shape").commit();
            } else {
                if (oDevice.getVariationColor() == null && oDevice.getExploitation() == Constants_Intern.EXPLOITATION_REUSE) {
                    requestColor();
                } else {
                    startFragmentResult();
                }
            }
        }
    }

    void requestColor() {
        Log.i("Hey", "Honey");
        uNetwork.getColors(oDevice, new Interface_VolleyCallback_ArrayList_Choice() {
            @Override
            public void onSuccess(ArrayList<Object_Choice> list) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constants_Intern.LIST_CHOICE, list);
                startFragmentChoice(bundle);
            }
        });
    }

    @Override
    public void reset() {
        oDevice = new Device();
        etScan.setText("");
        etScan.requestFocus();
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        updateUI();
    }

    private void totalReset() {
        cReuse = 0;
        cRecycling = 0;
        reset();
    }

    private void updateUI() {
        tvName.setText(oDevice.getName());
        tvManufacturer.setText(oDevice.getNameManufacturer());
        tvCharger.setText(oDevice.getNameCharger());
        tvBattery.setText(oDevice.getNameBattery());
        tvLKU.setText(Integer.toString(oDevice.getLKU()));

        tvCounterRecycling.setText(Integer.toString(cRecycling));
        tvCounterReuse.setText(Integer.toString(cReuse));
        tvCounterTotal.setText(Integer.toString(cRecycling + cReuse));
    }

    @Override
    public Device getModel() {
        return oDevice;
    }

    @Override
    public void requestName() {
        Fragment_Request_Model_Name f = new Fragment_Request_Model_Name();
        fManager.beginTransaction().replace(R.id.flFragmentRequest, f, "fragment_request_name").commit();
    }

    @Override
    public void returnName(String name) {
        oDevice.setName(name);
        uNetwork.getIdModel_Name(oDevice, new Interface_VolleyCallback() {
            @Override
            public void onSuccess() {
                checkExploitation();
                updateUI();
            }

            @Override
            public void onFailure() {
                uNetwork.addModel(oDevice, new Interface_VolleyCallback() {
                    @Override
                    public void onSuccess() {
                        updateUI();
                        startFragmentExploitation();
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
            case R.id.trLKU:
                if (oDevice.getId() > 0) {
                        uNetwork.connectWithLku(oDevice, new Interface_VolleyCallback_Int() {
                            @Override
                            public void onSuccess(int i) {
                                oDevice.setLKU(i);
                                updateUI();
                            }
                            @Override
                            public void onFailure() {
                                Toast.makeText(Activity_Bouncer.this, getString(R.string.toast_model_has_lku), Toast.LENGTH_LONG).show();
                            }
                        });
                }
                break;
            case R.id.trColor:
                requestColor();
        }
    }


}
