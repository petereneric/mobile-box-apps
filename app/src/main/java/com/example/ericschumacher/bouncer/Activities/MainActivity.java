package com.example.ericschumacher.bouncer.Activities;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request_Exploitation;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Result;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Network;
import com.example.ericschumacher.bouncer.Objects.Object_Model;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

public class MainActivity extends AppCompatActivity implements Interface_Network {

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
                String tac = editable.toString().substring(0,9);
                int idModel = uNetwork.getIdModel(tac);
                if (idModel > 0) {
                    if (uNetwork.checkBlackList(idModel)) {
                        startFragmentResult(getString(R.string.recycling));
                    } else {
                        if (uNetwork.checkLku(idModel)) {
                            startFragmentResult(getString(R.string.reuse));
                        } else {
                            if (uNetwork.checkGreenList(idModel)) {
                                startFragmentResult(getString(R.string.reuse));
                            } else {

                            }
                        }
                    }
                } else {
                    // Tac unknown
                }
            }
        });
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

    // Interface - Network
    @Override
    public void addGreenList(int idModel) {
        uNetwork.addGreenList(idModel);
    }

    @Override
    public void addBlackList(int idModel) {
        uNetwork.addBlackList(idModel);
    }
}
