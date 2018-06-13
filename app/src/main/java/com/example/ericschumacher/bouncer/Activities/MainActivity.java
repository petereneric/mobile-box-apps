package com.example.ericschumacher.bouncer.Activities;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
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

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Choice;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Name_Battery;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Name_Model;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request_Exploitation;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Result;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Choice;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Int;
import com.example.ericschumacher.bouncer.Objects.Object_Choice;
import com.example.ericschumacher.bouncer.Objects.Object_Model;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Interface_Selection {

    // Utilities
    Utility_Network uNetwork;

    // Objects
    Object_Model oModel;

    // Layout
    EditText etScan;

    // Fragments
    FragmentManager fManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Utilities
        uNetwork = new Utility_Network(this);

        // Objects
        oModel = new Object_Model();

        // Fragments
        fManager = getSupportFragmentManager();

        // Layout
        setLayout();
        handleInteraction();

        etScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("Hier", "läuft");
            }
        });
    }

    // Layout
    private void setLayout() {
        setContentView(R.layout.activity_selection);
        etScan = findViewById(R.id.et_scan);
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
                if (editable.toString() != "" && editable.toString().length() == 15) {
                    Log.i("Working", "Yes");
                    // Hide keyboard
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etScan.getWindowToken(), 0);

                    // Get Tac and work with it
                    String tac = editable.toString().substring(0, 8);
                    uNetwork.getIdModel_Tac(tac, new Interface_VolleyCallback_Int() {
                        @Override
                        public void onSuccess(int i) {
                            int idModel = i;
                            oModel.setId(idModel);
                            checkModel();
                        }

                        @Override
                        public void onFailure() {
                            startFragmentRequest();
                        }
                    });
                }
            }
        });
    }

    // Class Methods

    private void checkModel() {
        uNetwork.checkLku(oModel.getId(), new Interface_VolleyCallback() {
            @Override
            public void onSuccess() {
                Log.i("checkModel()", "Found LKU");
                oModel.setExploitation(Constants_Intern.EXPLOITATION_REUSE);
                checkDetails();
            }

            @Override
            public void onFailure() {
                uNetwork.checkExploitation(oModel.getId(), new Interface_VolleyCallback_Int() {
                    @Override
                    public void onSuccess(int i) {
                        oModel.setExploitation(i);
                        if (oModel.getExploitation() == Constants_Intern.EXPLOITATION_RECYCLING) {
                            startFragmentResult(getString(R.string.recycling));
                        } else {
                            checkDetails();
                        }
                        Log.i("Expo check", Integer.toString(i));

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
    public void startFragmentResult(String result) {
        Fragment_Result f = new Fragment_Result();
        Bundle b = new Bundle();
        b.putString(Constants_Intern.SELECTION_RESULT, result);
        f.setArguments(b);
        fManager.beginTransaction().replace(R.id.fl_input_output, f, "fragment_result").commit();
        reset();
    }



    private void startFragmentExploitation() {
        Fragment_Request_Exploitation f = new Fragment_Request_Exploitation();
        Bundle b = new Bundle();
        b.putInt(Constants_Intern.SELECTION_ID_MODEL, oModel.getId());
        f.setArguments(b);
        fManager.beginTransaction().replace(R.id.fl_input_output, f, "fragment_exploitation").commit();
    }

    private void startFragmentRequest() {
        Fragment_Request_Name_Model f = new Fragment_Request_Name_Model();
        fManager.beginTransaction().replace(R.id.fl_input_output, f, "fragment_name_model").commit();
    }

    private void startFragmentRequest(Fragment f) {
        Bundle b = new Bundle();
        b.putInt(Constants_Intern.SELECTION_ID_MODEL, oModel.getId());
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
        oModel.setExploitation(Constants_Intern.EXPLOITATION_REUSE);
        uNetwork.exploitReuse(idModel);
    }

    @Override
    public void exploitRecycling(int idModel) {
        oModel.setExploitation(Constants_Intern.EXPLOITATION_RECYCLING);
        uNetwork.exploitRecycling(idModel);
    }

    @Override
    public void checkName(final String name) {
        uNetwork.getIdModel_Name(name, etScan.getText().toString().substring(0, 8), new Interface_VolleyCallback_Int() {
            @Override
            public void onSuccess(int i) {
                oModel.setId(i);
                checkModel();
            }

            @Override
            public void onFailure() {
                uNetwork.addModel(name, etScan.getText().toString().substring(0, 8), new Interface_VolleyCallback_Int() {
                    @Override
                    public void onSuccess(int i) {
                        oModel.setId(i);
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
    public void callbackManufacturer(int id) {
        oModel.setIdManufacturer(id);
        uNetwork.addManufacturerToModel(oModel.getId(), oModel.getIdManufacturer());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                checkDetails();
            }
        }, 500);

    }

    @Override
    public void callbackCharger(int id) {
        oModel.setIdCharger(id);
        uNetwork.connectChargerWithModel(oModel.getId(), oModel.getIdCharger());
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                checkDetails();
            }
        }, 500);
    }

    @Override
    public void checkBattery(final String name) {
        uNetwork.getIdBattery(oModel.getId(), name, new Interface_VolleyCallback_Int() {
            @Override
            public void onSuccess(int i) {
                oModel.setIdBattery(i);
                uNetwork.connectBatteryWithModel(oModel.getId(), oModel.getIdBattery());
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        checkDetails();
                    }
                }, 500);
            }

            @Override
            public void onFailure() {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.add_battery, name));
                builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        uNetwork.addBattery(name, oModel.getIdManufacturer(), new Interface_VolleyCallback_Int() {
                            @Override
                            public void onSuccess(int i) {
                                oModel.setIdBattery(i);
                                uNetwork.connectBatteryWithModel(oModel.getId(), oModel.getIdBattery());
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
                        startFragmentResult(oModel.getExploitationForScreen(MainActivity.this));
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });
    }

    @Override
    public void checkDetails() {
        uNetwork.checkManufacturer(oModel.getId(), new Interface_VolleyCallback() {
            @Override
            public void onSuccess() {
                uNetwork.checkCharger(oModel.getId(), new Interface_VolleyCallback() {
                    @Override
                    public void onSuccess() {
                        uNetwork.checkBattery(oModel.getId(), new Interface_VolleyCallback() {
                            @Override
                            public void onSuccess() {
                                Log.i("Result check", oModel.getExploitationForScreen(MainActivity.this));
                                startFragmentResult(oModel.getExploitationForScreen(MainActivity.this));
                            }

                            @Override
                            public void onFailure() {
                                startFragmentRequest(new Fragment_Request_Name_Battery());
                            }
                        });
                    }

                    @Override
                    public void onFailure() {
                        uNetwork.getChargers(oModel.getId(), new Interface_VolleyCallback_ArrayList_Choice() {
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
            public void onFailure() {
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
    }

    @Override
    public void reset() {
        oModel = new Object_Model();
        etScan.setText("");
        /*for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }*/
    }

    @Override
    public Object_Model getModel() {
        return oModel;
    }
}
