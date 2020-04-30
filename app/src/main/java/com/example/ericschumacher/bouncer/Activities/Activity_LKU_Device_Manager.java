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
import android.widget.Toast;

import com.example.ericschumacher.bouncer.Activities.Parent.Activity_Device;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Int;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

public class Activity_LKU_Device_Manager extends Activity_Device {

    // Layout
    EditText etScan;
    ImageView ivClearScan;
    android.support.v4.view.ViewPager ViewPager;

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
                        if (oDevice.getoStation().getId() == Constants_Intern.STATION_PRIME_STOCK) {
                            oDevice.getoStation().setId(Constants_Intern.STATION_UNKNOWN);
                            uNetwork.updateDevice(oDevice, new Interface_VolleyCallback() {
                                @Override
                                public void onSuccess() {

                                }

                                @Override
                                public void onFailure() {

                                }
                            });
                            Toast.makeText(Activity_LKU_Device_Manager.this, getString(R.string.device_written_off), Toast.LENGTH_LONG).show();
                            mPrinter.printDeviceId(oDevice);
                        } else {
                            uNetwork.assignLku(oDevice, new Interface_VolleyCallback_Int() {
                                @Override
                                public void onSuccess(int i) {
                                    oDevice.getoStation().setId(Constants_Intern.STATION_PRIME_STOCK);
                                    updateUI();
                                    uNetwork.updateDevice(oDevice, new Interface_VolleyCallback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onFailure() {

                                        }
                                    });
                                    Toast.makeText(Activity_LKU_Device_Manager.this, getString(R.string.device_stored_lku), Toast.LENGTH_LONG).show();
                                    mPrinter.printDeviceId(oDevice);
                                }

                                @Override
                                public void onFailure() {
                                    // Platz scheint voll
                                    oDevice.getoStation().setId(Constants_Intern.STATION_EXCESS_STOCK);
                                    updateUI();
                                    uNetwork.updateDevice(oDevice, new Interface_VolleyCallback() {
                                        @Override
                                        public void onSuccess() {

                                        }

                                        @Override
                                        public void onFailure() {

                                        }
                                    });
                                    Toast.makeText(Activity_LKU_Device_Manager.this, getString(R.string.device_stored_excess_stock), Toast.LENGTH_LONG).show();
                                    mPrinter.printDeviceId(oDevice);
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

    @Override
    public void updateUI() {
        ((Fragment_Device) fManager.findFragmentByTag(Constants_Intern.FRAGMENT_DEVICE)).updateUI(oDevice);
    }

    @Override
    public void onClickLKU() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_LKU_Device_Manager.this);
        builder.setTitle(getString(R.string.is_lku_filled));
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                uNetwork.reassignLKU(oDevice, new Interface_VolleyCallback_Int() {
                    @Override
                    public void onSuccess(int i) {
                        updateUI();
                        mPrinter.printDeviceId(oDevice);
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
