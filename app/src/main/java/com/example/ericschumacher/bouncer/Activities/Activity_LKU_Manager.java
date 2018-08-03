package com.example.ericschumacher.bouncer.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Manager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Int;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

public class Activity_LKU_Manager extends Activity_Manager_Device implements Interface_Manager {

    // Layout
    EditText etScan;
    ImageView ivClearScan;

    // Utilities
    Utility_Network uNetwork;

    // Else
    FragmentManager fManager;
    Device oDevice;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lku_manager);
        setLayout();

        uNetwork = new Utility_Network(this);

        // EditText
        etScan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                uNetwork.getDevice(Integer.parseInt(charSequence.toString()), new Interface_VolleyCallback_Device() {
                    @Override
                    public void onSuccess(Device device) {
                        oDevice = device;
                        updateUI();
                        if (oDevice.getStation().getId() == Constants_Intern.STATION_LKU_STOCKING_INT) {
                            oDevice.getStation().setId(Constants_Intern.STATION_UNKNOWN_INT);
                            uNetwork.updateDevice(oDevice);
                        } else {
                            uNetwork.assignLku(oDevice, new Interface_VolleyCallback_Int() {
                                @Override
                                public void onSuccess(int i) {
                                    oDevice.setLKU(i);
                                    updateUI();
                                    uNetwork.updateDevice(oDevice);
                                }

                                @Override
                                public void onFailure() {

                                }
                            });

                        }
                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        // Fragment Device
        oDevice = new Device();
        fManager = getSupportFragmentManager();
        Fragment fDevice = new Fragment_Device();
        fManager.beginTransaction().replace(R.id.flFragmentDevice, fDevice, Constants_Intern.FRAGMENT_DEVICE).commit();
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();
        updateUI();

    }

    private void setLayout() {
        etScan = findViewById(R.id.etScan);
        ivClearScan = findViewById(R.id.ivClearScan);
    }

    @Override
    public void updateUI() {
        ((Fragment_Device) fManager.findFragmentByTag(Constants_Intern.FRAGMENT_DEVICE)).updateUI(oDevice);
    }

    @Override
    public void onClickLKU() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_LKU_Manager.this);
        builder.setTitle(getString(R.string.is_lku_filled));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                uNetwork.reassignLKU(oDevice, new Interface_VolleyCallback_Int() {
                    @Override
                    public void onSuccess(int i) {
                        oDevice.setLKU(i);
                        updateUI();
                        mPrinter.printDevice(oDevice);
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

            }
        });
    }
}
