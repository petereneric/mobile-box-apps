package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Color;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_DeviceManager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Model;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Additive;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Device extends Fragment implements View.OnClickListener, Interface_Device {

    // Layout
    View Layout;
    TableRow trLKU;
    TableRow trStation;
    TableRow trColor;
    TableRow trShape;
    TableRow trBatteryContained;
    TableRow trBackcoverContained;
    TableRow trDamages;

    TextView tvLKU;
    TextView tvStation;
    TextView tvColor;
    TextView tvShape;
    TextView tvBatteryContained;
    TextView tvBackcoverContained;
    TextView tvDamages;

    // Fragment
    FragmentManager fManager;
    private final static String FRAGMENT_MODEL = "FRAGMENT_MODEL";

    // Interface
    Interface_Model iModel;
    Interface_DeviceManager iManager;

    // Connection
    Utility_Network uNetwork;
    Volley_Connection cVolley;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Layout = inflater.inflate(R.layout.fragment_device, container, false);
        setLayout();

        fManager = getActivity().getSupportFragmentManager();
        Fragment_Model fModel = new Fragment_Model();
        fManager.beginTransaction().replace(R.id.flModel, fModel, FRAGMENT_MODEL).commit();
        iManager = (Interface_DeviceManager)getActivity();

        // Connection
        uNetwork = new Utility_Network(getActivity());
        cVolley = new Volley_Connection(getActivity());

        return Layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        iModel = (Interface_Model)fManager.findFragmentByTag(FRAGMENT_MODEL);
    }

    private void setLayout() {
        trLKU = Layout.findViewById(R.id.trLKU);
        trStation = Layout.findViewById(R.id.trStation);
        trColor = Layout.findViewById(R.id.trColor);
        trShape = Layout.findViewById(R.id.trShape);
        trBatteryContained = Layout.findViewById(R.id.trBattery);
        trBackcoverContained = Layout.findViewById(R.id.trBackcoverContained);
        trDamages = Layout.findViewById(R.id.trDamages);

        tvLKU = Layout.findViewById(R.id.tvLKU);
        tvStation = Layout.findViewById(R.id.tvStation);
        tvColor = Layout.findViewById(R.id.tvColor);
        tvShape = Layout.findViewById(R.id.tvShape);
        tvBatteryContained = Layout.findViewById(R.id.tvBattery);
        tvBackcoverContained = Layout.findViewById(R.id.tvBackcoverContained);
        tvDamages = Layout.findViewById(R.id.tvDamages);

        trLKU.setOnClickListener(this);
        trStation.setOnClickListener(this);
        trColor.setOnClickListener(this);
        trShape.setOnClickListener(this);
        trBatteryContained.setOnClickListener(this);
        trBackcoverContained.setOnClickListener(this);
        trDamages.setOnClickListener(this);
    }

    public void updateUI(Device device) {

        if (device.getoColor() != null) {
            tvColor.setText(device.getoColor().getName());
        } else {
            tvColor.setText(Constants_Intern.UNKOWN);
        }
        if (device.getoStoragePlace() != null) {
            trLKU.setVisibility(View.VISIBLE);
            tvLKU.setText(Integer.toString(device.getoStoragePlace().getkLku()));
            if (device.getoStoragePlace().getkStock() == Constants_Intern.STATION_PRIME_STOCK) {
                tvLKU.setText(tvLKU.getText() + " ("+device.getoStoragePlace().getnPosition()+")");
            }
        } else {
            trLKU.setVisibility(View.GONE);
        }
        if (device.getoShape() != null) {
            tvShape.setText(device.getoShape().getName());
        } else {
            tvShape.setText(Constants_Intern.UNKOWN);
        }
        if (device.getoStation().getId() > Constants_Intern.INT_UNKNOWN) {
            tvStation.setText(device.getoStation().getName());
        } else {
            tvStation.setText(Constants_Intern.UNKOWN);
        }
        if (device.isBatteryContained() != null) {
            if (device.isBatteryContained()) {
                tvBatteryContained.setText(getString(R.string.yes));
            } else {
                tvBatteryContained.setText(getString(R.string.no));
            }
        } else {
            tvBatteryContained.setText(getString(R.string.unknown));
        }
        if (device.isBackcoverContained() != null) {
            if (device.isBackcoverContained()) {
                tvBackcoverContained.setText(getString(R.string.yes));
            } else {
                tvBackcoverContained.setText(getString(R.string.no));
            }
        } else {
            tvBackcoverContained.setText(getString(R.string.unknown));
        }
        tvDamages.setText(Integer.toString(device.getlDeviceDamages().size()));
        ((Fragment_Model)fManager.findFragmentByTag(FRAGMENT_MODEL)).updateUI(device.getoModel());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trLKU:
                iManager.onClickLKU();
                //requestLKU();
                break;
            case R.id.trStation:
                requestStation();
                break;
            case R.id.trColor:
                requestColor(iManager.getDevice());
                break;
            case R.id.trShape:
                requestShape();
                break;
            case R.id.trBattery:
                requestBatteryContained();
                break;
            case R.id.trBackcoverContained:
                requestBackcoverContained();
                break;
            case R.id.trDamages:
                iManager.requestDamages();
                break;
        }
    }

    @Override
    public void requestLKU() {
        //Toast.makeText(getActivity(), "Noch checken", Toast.LENGTH_LONG).show();
        /*
        if (iManager.getDevice().getkModel() > 0) {
            uNetwork.connectWithLku(iManager.getDevice(), new Interface_VolleyCallback_Int() {
                @Override
                public void onSuccess(int i) {
                    iManager.getDevice().setLKU(i);
                    iManager.updateUI();
                }

                @Override
                public void onFailure() {
                    Toast.makeText(getActivity(), getString(R.string.toast_model_has_lku), Toast.LENGTH_LONG).show();
                }
            });
        }
        */
    }

    @Override
    public void requestStation() {

    }

    @Override
    public void requestShape() {
        Fragment_Interaction_Simple_Choice fRequestButtons = new Fragment_Interaction_Simple_Choice();
        Bundle bundle = new Bundle();
        bundle.putString(Constants_Intern.INTERACTION_TYPE, getString(R.string.interaction_simple_choice));
        bundle.putString(Constants_Intern.INTERACTION_TITLE, getString(R.string.shape));
        int[] lDrawableIds = new int[7];
        lDrawableIds[0] = R.drawable.button_interaction_shape_cherry;
        lDrawableIds[1] = R.drawable.button_interaction_shape_very_good;
        lDrawableIds[2] = Constants_Intern.GONE_INT;
        lDrawableIds[3] = R.drawable.button_interaction_shape_good;
        lDrawableIds[4] = R.drawable.button_interaction_shape_acceptable;
        lDrawableIds[5] = Constants_Intern.GONE_INT;
        lDrawableIds[6] = R.drawable.button_interaction_shape_broken;
        bundle.putIntArray(Constants_Intern.LIST_DRAWABLE_IDS, lDrawableIds);
        int[] lColorIds = new int[7];
        lColorIds[0] = R.color.color_shape_cherry;
        lColorIds[1] = R.color.color_shape_very_good;
        lColorIds[2] = Constants_Intern.GONE_INT;
        lColorIds[3] = R.color.color_shape_good;
        lColorIds[4] = R.color.color_shape_acceptable;
        lColorIds[5] = Constants_Intern.GONE_INT;
        lColorIds[6] = R.color.color_shape_broken;
        bundle.putIntArray(Constants_Intern.LIST_COLOR_IDS, lColorIds);
        String[] lButtons = new String[7];
        lButtons[0] = getString(R.string.shape_cherry);
        lButtons[1] = getString(R.string.shape_very_good);
        lButtons[2] = Constants_Intern.GONE;
        lButtons[3] = getString(R.string.shape_good);
        lButtons[4] = getString(R.string.shape_acceptable);
        lButtons[5] = Constants_Intern.GONE;
        lButtons[6] = getString(R.string.broken);
        bundle.putStringArray(Constants_Intern.LIST_BUTTONS, lButtons);
        //int[] lColorIds = {R.color.color_choice_positive, R.color.color_choice_negative};
        //bundle.putIntArray(Constants_Intern.LIST_COLOR_IDS, lColorIds);
        fRequestButtons.setArguments(bundle);
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, fRequestButtons, Constants_Intern.FRAGMENT_INTERACTION_SHAPE).commit();

        //Fragment_Request_Shape f = new Fragment_Request_Shape();
        //fManager.beginTransaction().replace(R.id.flFragmentInteraction, f, Constants_Intern.FRAGMENT_REQUEST_SHAPE).commit();


    }

    @Override
    public void requestCondition() {
        Fragment_Request_Condition f = new Fragment_Request_Condition();
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, f, Constants_Intern.FRAGMENT_REQUEST_CONDITION).commit();
    }

    @Override
    public void requestColor(Device device) {
        // Request new Colors
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_COLORS_MODEL + device.getoModel().getkModel(), null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                JSONArray lJson = oJson.getJSONArray(Constants_Extern.LIST_COLORS);
                ArrayList<Additive> list = new ArrayList<>();
                for (int i = 0; i<lJson.length(); i++) {
                    JSONObject json = lJson.getJSONObject(i);
                    Color oColor = new Color(getActivity(), json);
                    list.add(oColor);
                }
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constants_Intern.LIST_ADDITIVE, list);
                Fragment_Request_Color f = new Fragment_Request_Color();
                f.setArguments(bundle);
                fManager.beginTransaction().replace(R.id.flFragmentInteraction, f, Constants_Intern.FRAGMENT_REQUEST_COLOR).commit();
            }
        });

        /*
        uNetwork.getColors(device, new Interface_VolleyCallback_ArrayList_Additive() {
            @Override
            public void onSuccess(ArrayList<Additive> list) {
                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList(Constants_Intern.LIST_ADDITIVE, list);
                Fragment_Request_Color f = new Fragment_Request_Color();
                f.setArguments(bundle);
                fManager.beginTransaction().replace(R.id.flFragmentInteraction, f, Constants_Intern.FRAGMENT_REQUEST_COLOR).commit();
            }
        });
        */
    }

    @Override
    public void requestBatteryContained() {
        Fragment_Interaction_Simple_Choice fRequestButtons = new Fragment_Interaction_Simple_Choice();
        Bundle bundle = new Bundle();
        bundle.putString(Constants_Intern.INTERACTION_TYPE, getString(R.string.interaction_simple_choice));
        bundle.putString(Constants_Intern.INTERACTION_TITLE, getString(R.string.battery_intact_and_contained));
        int[] lDrawableIds = new int[2];
        lDrawableIds[0] = R.drawable.button_interaction_yes;
        lDrawableIds[1] = R.drawable.button_interaction_no;
        bundle.putIntArray(Constants_Intern.LIST_DRAWABLE_IDS, lDrawableIds);
        int[] lColorIds = new int[2];
        lColorIds[0] = R.color.color_yes;
        lColorIds[1] = R.color.color_no;
        bundle.putIntArray(Constants_Intern.LIST_COLOR_IDS, lColorIds);
        String[] lButtons = new String[2];
        lButtons[0] = getString(R.string.yes);
        lButtons[1] = getString(R.string.no);
        bundle.putStringArray(Constants_Intern.LIST_BUTTONS, lButtons);
        fRequestButtons.setArguments(bundle);
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, fRequestButtons, Constants_Intern.FRAGMENT_REQUEST_BATTERY_CONTAINED).commit();
    }

    @Override
    public void requestBackcoverContained() {
        Fragment_Interaction_Simple_Choice fInteraction = new Fragment_Interaction_Simple_Choice();
        Bundle bundle = new Bundle();
        bundle.putString(Constants_Intern.INTERACTION_TYPE, getString(R.string.interaction_simple_choice));
        bundle.putString(Constants_Intern.INTERACTION_TITLE, getString(R.string.backcover_intact_and_contained));
        int[] lDrawableIds = new int[2];
        lDrawableIds[0] = R.drawable.button_interaction_yes;
        lDrawableIds[1] = R.drawable.button_interaction_no;
        bundle.putIntArray(Constants_Intern.LIST_DRAWABLE_IDS, lDrawableIds);
        int[] lColorIds = new int[2];
        lColorIds[0] = R.color.color_yes;
        lColorIds[1] = R.color.color_no;
        bundle.putIntArray(Constants_Intern.LIST_COLOR_IDS, lColorIds);
        String[] lButtons = new String[2];
        lButtons[0] = getString(R.string.yes);
        lButtons[1] = getString(R.string.no);
        bundle.putStringArray(Constants_Intern.LIST_BUTTONS, lButtons);
        fInteraction.setArguments(bundle);
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, fInteraction, Constants_Intern.FRAGMENT_INTERACTION_BACKCOVER_CONTAINED).commit();
    }

    @Override
    public void requestName() {
        iModel.requestName();
    }

    @Override
    public void requestManufacturer() {
        iModel.requestManufacturer();
    }

    @Override
    public void requestCharger(Device device) {
        iModel.requestCharger(device.getoModel());
    }

    @Override
    public void requestBatteryRemovable(Device device) {
        iModel.requestBatteryRemovable(device.getoModel());
    }

    @Override
    public void requestBackcoverRemovable(Device device) {
        iModel.requestBackcoverRemovable(device.getoModel());
    }

    @Override
    public void requestBattery(Device device) {
        iModel.requestBattery(device.getoModel());
    }

    @Override
    public void requestDefaultExploitation(Model model) {
        iModel.requestDefaultExploitation(model);
    }

    @Override
    public void requestTypePhone() {
        iModel.requestTypePhone();
    }

    @Override
    public void requestModelDamages(Model model) {
        iModel.requestModelDamages(model);
    }
}
