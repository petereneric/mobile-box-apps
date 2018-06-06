package com.example.ericschumacher.bouncer.Activities;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Choice;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request_Exploitation;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Name_Battery;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Name_Model;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Result;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Choice;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Int;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_String;
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
                if (editable.toString() != "" && editable.toString().length() > 8) {
                    Log.i("Working", "Yes");
                    // Hide keyboard
                    InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(etScan.getWindowToken(), 0);

                    // Get Tac and work with it
                    String tac = editable.toString().substring(0, 9);
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
                startFragmentResult(getString(R.string.reuse));
            }

            @Override
            public void onFailure() {
                uNetwork.checkExploitation(oModel.getId(), new Interface_VolleyCallback_String() {
                    @Override
                    public void onSuccess(String s) {
                            startFragmentResult(s);
                    }
                    @Override
                    public void onFailure() {
                        startFragmentExploitation();
                    }
                });
            }
        /*
        uNetwork.checkBlackList(oModel.getId(), new Interface_VolleyCallback() {
            @Override
            public void onSuccess() {
                startFragmentResult(getString(R.string.recycling));
            }

            @Override
            public void onFailure() {
                uNetwork.checkLku(oModel.getId(), new Interface_VolleyCallback() {
                    @Override
                    public void onSuccess() {
                        startFragmentResult(getString(R.string.reuse));
                    }

                    @Override
                    public void onFailure() {
                        uNetwork.checkGreenList(oModel.getId(), new Interface_VolleyCallback() {
                            @Override
                            public void onSuccess() {
                                startFragmentResult(getString(R.string.reuse));
                            }

                            @Override
                            public void onFailure() {
                                startFragmentExploitation();
                            }
                        });
                    }
                });
            }
        });
        */
        });
    }

    // Fragments
    private void startFragmentResult(String result) {
        Fragment_Result f = new Fragment_Result();
        Bundle b = new Bundle();
        b.putString(Constants_Intern.SELECTION_RESULT, result);
        f.setArguments(b);
        fManager.beginTransaction().replace(R.id.fl_input_output, f, "fragment_result").commit();
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
        uNetwork.exploitReuse(idModel);
    }

    @Override
    public void exploitRecycling(int idModel) {
        uNetwork.exploitRecycling(idModel);
    }

    @Override
    public void checkName(final String name) {
        uNetwork.getIdModel_Name(name, etScan.getText().toString(), new Interface_VolleyCallback_Int() {
            @Override
            public void onSuccess(int i) {
                oModel.setId(i);
                checkModel();
            }

            @Override
            public void onFailure() {
                uNetwork.addModel(name, etScan.getText().toString(), new Interface_VolleyCallback_Int() {
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
        checkDetails();
    }

    @Override
    public void checkBattery(final String name) {
        uNetwork.getIdModel_Name(name, etScan.getText().toString(), new Interface_VolleyCallback_Int() {
            @Override
            public void onSuccess(int i) {
                oModel.setId(i);
                checkModel();
            }

            @Override
            public void onFailure() {
                uNetwork.addModel(name, etScan.getText().toString(), new Interface_VolleyCallback_Int() {
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
    public void checkDetails() {
        uNetwork.checkManufacturer(oModel.getId(), new Interface_VolleyCallback() {
            @Override
            public void onSuccess() {
                if (uNetwork.checkCharger(oModel.getId()) || true) {
                    if (uNetwork.checkBattery(oModel.getId()) || true) {
                        reset();
                    } else {
                        startFragmentRequest(new Fragment_Request_Name_Battery());
                    }
                } else {
                    //startFragmentRequest(new Fragment_Request_Name_Charger());
                }
            }

            @Override
            public void onFailure() {
                uNetwork.getManufactures(new Interface_VolleyCallback_ArrayList_Choice() {
                    @Override
                    public void onSuccess(ArrayList<Object_Choice> list) {
                        Bundle b = new Bundle();
                        b.putParcelableArrayList(Constants_Intern.LIST_CHOICE_MANUFACTURES, list);
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
        for (Fragment fragment:getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }
}
