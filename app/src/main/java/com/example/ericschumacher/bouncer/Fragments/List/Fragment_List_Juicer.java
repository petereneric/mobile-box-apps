package com.example.ericschumacher.bouncer.Fragments.List;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Activities.Activity_Juicer_New;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List_Juicer;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_Juicer;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Keyboard;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_List_Juicer extends Fragment implements View.OnClickListener, TextWatcher {

    // Layout
    View vLayout;
    EditText etSearch;
    RecyclerView vRecyclerView;
    ImageView ivClear;
    View vFocus;
    View lSearch;

    // Variables
    boolean bSearchSelected;

    // Activity
    Activity_Juicer_New activityJuicer;

    // Data
    ArrayList<Device> lDevices;
    ArrayList<Device> lDevicesExtra;

    // Connection
    Volley_Connection cVolley;

    // SharedPreferences
    android.content.SharedPreferences SharedPreferences;

    // Adapter
    Adapter_List_Juicer aListJuicer;
    Integer kModelColorShape;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Layout
        setLayout(inflater, container);

        // Data
        lDevices = new ArrayList<>();
        lDevicesExtra = new ArrayList<>();

        // Variables
        bSearchSelected = false;

        // Adapter
        aListJuicer = new Adapter_List_Juicer(getActivity(), lDevicesExtra, this);
        vRecyclerView.setAdapter(aListJuicer);

        // Activity
        activityJuicer = (Activity_Juicer_New) getActivity();

        // Connection
        cVolley = new Volley_Connection(getActivity());

        // SharedPreferences
        SharedPreferences = getActivity().getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0);

        return vLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        loadDevices();
    }


    private void setLayout(LayoutInflater inflater, @Nullable ViewGroup container) {
        // Initiate
        vLayout = inflater.inflate(R.layout.fragment_list_juicer, container, false);
        etSearch = vLayout.findViewById(R.id.etSearch);
        vRecyclerView = vLayout.findViewById(R.id.vRecyclerView);
        vFocus = vLayout.findViewById(R.id.vFocus);
        lSearch = vLayout.findViewById(R.id.lSearch);
        ivClear = vLayout.findViewById(R.id.ivAction);

        // RecyclerView
        vRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setSwipe();

        // OnClickListener & TextWatcher
        etSearch.setOnClickListener(this);
        ivClear.setOnClickListener(this);
        etSearch.addTextChangedListener(this);
    }

    private void setSwipe() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        Device device = lDevicesExtra.get(viewHolder.getAdapterPosition());
                        lDevicesExtra.get(viewHolder.getAdapterPosition()).setoStoragePlace(null);
                        lDevicesExtra.remove(lDevicesExtra.get(viewHolder.getAdapterPosition()));
                        if (!isExtraDevice(device)) {
                            for (Device device1 : lDevices) {
                                if (device.getIdDevice() == device1.getIdDevice()) {
                                    lDevices.remove(device1);
                                    base();
                                    break;
                                }
                            }
                        }
                        base();
                        break;
                }
            }

            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final View rlForeground = ((ViewHolder_Juicer) viewHolder).llForeground;
                final View rlBackground = ((ViewHolder_Juicer) viewHolder).rlBackground;
                rlBackground.setVisibility(View.VISIBLE);
                getDefaultUIUtil().onDraw(c, recyclerView, rlForeground, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final View foregroundView = ((ViewHolder_Juicer) viewHolder).llForeground;
                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                final View foregroundView = ((ViewHolder_Juicer) viewHolder).llForeground;
                getDefaultUIUtil().clearView(foregroundView);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(vRecyclerView);
    }

    public void loadDevices() {
        lSearch.setVisibility(View.GONE);
        if (activityJuicer.getIdModelColorShape() != null) {
            kModelColorShape = activityJuicer.getIdModelColorShape();
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_DEVICES_BY_ID_MODEL_COLOR_SHAPE_FOR_JUICER + activityJuicer.getStock() + "/" + kModelColorShape, null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    oJson.toString();
                    if (Volley_Connection.successfulResponse(oJson)) {
                        lDevices.clear();
                        lDevicesExtra.clear();
                        JSONArray jsonArray = oJson.getJSONArray(Constants_Extern.DEVICES);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            lDevices.add(new Device(jsonObject, getActivity()));
                        }
                        lDevicesExtra.addAll(lDevices);
                    }
                    lSearch.setVisibility(View.VISIBLE);
                    base();
                }
            });
        }
    }

    public void loadDevicesExtra(final Device device) {
        lDevicesExtra.clear();
        updateLayout();
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_DEVICES_IN_STOCK + activityJuicer.getStock() + "/" + device.getoStoragePlace().getkLku(), null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (Volley_Connection.successfulResponse(oJson)) {
                    ArrayList<Device> devices = Device.getDevices(getActivity(), oJson);
                    for (Device d: devices) {
                        if (d.getIdDevice() == device.getIdDevice()) {
                            devices.remove(d);
                            break;
                        }
                    }
                    lDevicesExtra.addAll(lDevices);
                    lDevicesExtra.addAll(devices);
                    lDevicesExtra = Device.sortByLku(lDevicesExtra);
                    updateLayout();
                }
            }
        });
    }

    public boolean isExtraDevice(Device device) {
        for (Device d : lDevices) {
            if (d.getIdDevice() == device.getIdDevice()) {
                return false;
            }
        }
        return true;
    }

    public void onDeviceSelected(Device device) {
        if (device.getoStoragePlace().getkStock() == Constants_Intern.STATION_PRIME_STOCK) {
            loadDevicesExtra(device);
        } else {
            Device.showFragmentDialogImage(getActivity(), device, Fragment_List_Juicer.this, getActivity().getSupportFragmentManager());
        }
    }

    public void onDeviceExtraSelected(Device device) {
        if (device.getoStoragePlace().getkStock() == Constants_Intern.STATION_PRIME_STOCK) {
            lDevicesExtra.clear();
            lDevicesExtra.addAll(lDevices);
            aListJuicer.notifyDataSetChanged();
        }
    }

    public void updateLayout() {
        // Adapter
        aListJuicer.notifyDataSetChanged();
    }


    // Keyboard

    public void setKeyboard(Boolean bKeyboard) {
        if (bKeyboard != null) {
            if (bKeyboard == Constants_Intern.SHOW_KEYBOARD) {
                Utility_Keyboard.openKeyboard(getActivity(), etSearch);
                etSearch.requestFocus();
            } else {
                Utility_Keyboard.hideKeyboardFrom(getActivity(), etSearch);
                vFocus.requestFocus();
            }
        }
    }

    // Base

    private void base() {
        updateLayout();
        etSearch.setText("");
        setKeyboard(Constants_Intern.SHOW_KEYBOARD);
        if (lDevices.size() == 0) {
            lDevicesExtra.clear();
            updateLayout();
            activityJuicer.removeIdModelColorShape(kModelColorShape);
            loadDevices();
        }
    }

    // Search


    private void onSearch() {
        String cInput = etSearch.getText().toString();
        for (Device device : lDevicesExtra) {
            if (device.getIdDevice() == Integer.parseInt(cInput)) {
                removeDevice(device);
                break;
            }
        }

        /*
        for (Device device : lDevicesExtra) {
            if (Integer.parseInt(cInput) == device.getIdDevice()) {
                device.setoStoragePlace(null);
                lDevicesExtra.remove(device);
                if (!isExtraDevice(device)) {
                    for (Device device1 : lDevices) {
                        if (Integer.parseInt(cInput) == device1.getIdDevice()) {
                            lDevices.remove(device1);
                            lDevicesExtra.clear();
                            lDevicesExtra.addAll(lDevices);
                            base();
                            break;
                        }
                    }
                }
                base();
                break;
            }
        }
         */

    }

    private void removeDevice(Device oDevice) {
        oDevice.setoStoragePlace(null);
        if (!isExtraDevice(oDevice)) {
            for (Device device : lDevices) {
                if (device.getIdDevice() == oDevice.getIdDevice()) {
                    lDevices.remove(device);
                    lDevicesExtra.clear();
                    lDevicesExtra.addAll(lDevices);
                    break;
                }
            }
        } else {
            for (Device device : lDevicesExtra) {
                if (device.getIdDevice() == oDevice.getIdDevice()) {
                    lDevicesExtra.remove(device);
                    break;
                }
            }
            for (Device device : lDevices) {
                if (device.getIdDevice() == oDevice.getIdDevice()) {
                    lDevices.remove(device);
                    break;
                }
            }

        }
        base();
    }


    // OnClickListener & TextWatcher

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivAction:
                etSearch.setText("");
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!editable.toString().equals("")) {
            onSearch();
        }
    }
}
