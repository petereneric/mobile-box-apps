package com.example.ericschumacher.bouncer.Activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request_Exploitation;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request_Name.Fragment_Request_Name_Battery;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request_Name.Fragment_Request_Name_Charger;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request_Name.Fragment_Request_Name_Manufacturer;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request_Name.Fragment_Request_Name_Model;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Result;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
import com.example.ericschumacher.bouncer.Objects.Object_Model;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

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
        oModel = null;

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
            @Override public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override public void afterTextChanged(Editable editable) {
                if (editable.toString()!="") {
                    String tac = editable.toString().substring(0,9);
                    int idModel = uNetwork.getIdModel_Tac(tac);
                    if (idModel > 0) {
                        oModel.setId(idModel);
                        checkModel();
                    } else {
                        startFragmentName();
                    }
                }
            }
        });
    }

    // Class Methods

    private void checkModel() {
        if (uNetwork.checkBlackList(oModel.getId())) {
            startFragmentResult(getString(R.string.recycling));
        } else {
            if (uNetwork.checkLku(oModel.getId())) {
                startFragmentResult(getString(R.string.reuse));
            } else {
                if (uNetwork.checkGreenList(oModel.getId())) {
                    startFragmentResult(getString(R.string.reuse));
                } else {
                    startFragmentExploitation();
                }
            }
        }
    }

    // Fragments
    private void startFragmentResult (String result) {
        Fragment_Result f = new Fragment_Result();
        Bundle b = new Bundle();
        b.putString(Constants_Intern.SELECTION_RESULT, result);
        f.setArguments(b);
        fManager.beginTransaction().replace(R.id.fl_input_output, f, "fragment_result");
    }

    private void startFragmentExploitation () {
        Fragment_Request_Exploitation f = new Fragment_Request_Exploitation();
        Bundle b = new Bundle();
        b.putInt(Constants_Intern.SELECTION_ID_MODEL, oModel.getId());
        f.setArguments(b);
        fManager.beginTransaction().replace(R.id.fl_input_output, f, "fragment_exploitation");
    }

    private void startFragmentName() {
        Fragment_Request_Name_Model f = new Fragment_Request_Name_Model();
        fManager.beginTransaction().replace(R.id.fl_input_output, f, "fragment_name_model");
    }

    private void startFragmentName(Fragment f) {
        Bundle b = new Bundle();
        b.putInt(Constants_Intern.SELECTION_ID_MODEL, oModel.getId());
        f.setArguments(b);
        fManager.beginTransaction().replace(R.id.fl_input_output, f, "fragment_name");
    }

    // Interface - Network
    @Override
    public void addGreenList(int idModel) {
        uNetwork.addGreenList(idModel);
    }

    @Override
    public void addBlackList(int idModel) {
        uNetwork.addBlackList(idModel);
    }

    @Override
    public void checkName(String name) {
        int idModel = uNetwork.getIdModel_Name(name);
        if (idModel > 0) {
            oModel.setId(idModel);
            checkModel();
        } else {
            oModel.setId(uNetwork.addModel(name, etScan.getText().toString()));
            startFragmentExploitation();
        }
    }

    @Override
    public void checkManufacturer(String name) {
        int idManufacturer = uNetwork.getIdManufacturer(name);
        if (idManufacturer > 0) {
            oModel.setIdManufacturer(idManufacturer);


        } else {
            idManufacturer = uNetwork.addManufacturer(name);
            oModel.setIdManufacturer(idManufacturer);
        }
        uNetwork.addManufacturerToModel(oModel.getId(), oModel.getIdManufacturer());
        checkDetails();
    }

    @Override
    public void checkCharger(String name) {
        int idModel = uNetwork.getIdModel_Name(name);
        if (idModel > 0) {
            oModel.setId(idModel);
            checkModel();
        } else {
            oModel.setId(uNetwork.addModel(name, etScan.getText().toString()));
            startFragmentExploitation();
        }
    }

    @Override
    public void checkBattery(String name) {
        int idModel = uNetwork.getIdModel_Name(name);
        if (idModel > 0) {
            oModel.setId(idModel);
            checkModel();
        } else {
            oModel.setId(uNetwork.addModel(name, etScan.getText().toString()));
            startFragmentExploitation();
        }
    }

    @Override
    public void checkDetails() {
        if (uNetwork.checkManufacturer(oModel.getId())) {
            if (uNetwork.checkCharger(oModel.getId())) {
                if (uNetwork.checkBattery(oModel.getId())) {
                    reset();
                } else {
                    startFragmentName(new Fragment_Request_Name_Battery());
                }
            } else {
                startFragmentName(new Fragment_Request_Name_Charger());
            }
        } else {
            startFragmentName(new Fragment_Request_Name_Manufacturer());
        }
    }

    @Override
    public void reset() {
        oModel = new Object_Model();
        etScan.setText("");
    }
}
