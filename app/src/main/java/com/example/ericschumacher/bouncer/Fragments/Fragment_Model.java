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
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Choice;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Name;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Battery;
import com.example.ericschumacher.bouncer.Interfaces.Interface_DeviceManager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Model;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Additive;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Additive;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Model extends Fragment implements View.OnClickListener, Interface_Model {

    // Layout
    View Layout;

    TableRow trName;
    TableRow trManufacturer;
    TableRow trCharger;
    TableRow trBatteryRemovable;
    TableRow trBattery;
    TableRow trDefaultExploitation;
    TableRow trDps;

    TextView tvName;
    TextView tvManufacturer;
    TextView tvCharger;
    TextView tvBatteryRemovable;
    TextView tvBattery;
    TextView tvDefaultExploitation;
    TextView tvDps;

    // Interface
    Interface_DeviceManager iManager;

    // Ohters
    FragmentManager fManager;

    // Connection
    Volley_Connection cVolley;
    Utility_Network uNetwork;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Layout
        setLayout(inflater, container);

        // Interface
        iManager = (Interface_DeviceManager) getActivity();

        // Connection
        cVolley = new Volley_Connection(getActivity());
        uNetwork = new Utility_Network(getActivity());

        // Others
        fManager = getActivity().getSupportFragmentManager();


        return Layout;
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        Layout = inflater.inflate(R.layout.fragment_model, container, false);

        trName = Layout.findViewById(R.id.trName);
        trManufacturer = Layout.findViewById(R.id.trManufacturer);
        trCharger = Layout.findViewById(R.id.trCharger);
        trBatteryRemovable = Layout.findViewById(R.id.trBatteryRemovable);
        trBattery = Layout.findViewById(R.id.trBattery);
        trDefaultExploitation = Layout.findViewById(R.id.trDefaultExploitation);
        trDps = Layout.findViewById(R.id.trDps);

        tvName = Layout.findViewById(R.id.tvName);
        tvManufacturer = Layout.findViewById(R.id.tvManufacturer);
        tvCharger = Layout.findViewById(R.id.tvCharger);
        tvBatteryRemovable = Layout.findViewById(R.id.tvBatteryRemovable);
        tvBattery = Layout.findViewById(R.id.tvBattery);
        tvDefaultExploitation = Layout.findViewById(R.id.tvDefaultExploitation);
        tvDps = Layout.findViewById(R.id.tvDps);

        trName.setOnClickListener(this);
        trManufacturer.setOnClickListener(this);
        trCharger.setOnClickListener(this);
        trBattery.setOnClickListener(this);
        trBatteryRemovable.setOnClickListener(this);
        trDefaultExploitation.setOnClickListener(this);
        trDps.setOnClickListener(this);
    }

    public void updateUI(Model model) {
        if (model != null) {
            if (model.getName() != null) {
                tvName.setText(model.getName());
            } else {
                tvName.setText(Constants_Intern.UNKOWN);
            }
            if (model.getoManufacturer() != null) {
                tvManufacturer.setText(model.getoManufacturer().getName());
            } else {
                tvManufacturer.setText(Constants_Intern.UNKOWN);
            }
            if (model.getoCharger() != null) {
                tvCharger.setText(model.getoCharger().getName());
            } else {
                tvCharger.setText(Constants_Intern.UNKOWN);
            }
            if (model.isBatteryRemovable() != null) {
                tvBatteryRemovable.setText((model.isBatteryRemovable()) ? getString(R.string.yes) : getString(R.string.no));
            } else {
                tvBatteryRemovable.setText(Constants_Intern.UNKOWN);
            }
            if (model.getoBattery() != null) {
                tvBattery.setText(model.getoBattery().getName());
            } else {
                tvBattery.setText(Constants_Intern.UNKOWN);
            }
            tvDps.setText(Integer.toString(model.getnDps()));
            tvDefaultExploitation.setText(model.getExploitationForScreen(getActivity()));
        } else {
            tvName.setText(Constants_Intern.UNKOWN);
            tvManufacturer.setText(Constants_Intern.UNKOWN);
            tvCharger.setText(Constants_Intern.UNKOWN);
            tvBattery.setText(Constants_Intern.UNKOWN);
            tvDps.setText(Constants_Intern.UNKOWN);

        }

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trName:
                requestName();
                break;
            case R.id.trManufacturer:
                requestManufacturer();
                break;
            case R.id.trCharger:
                requestCharger(iManager.getModel());
                break;
            case R.id.trBatteryRemovable:
                requestBatteryRemovable(iManager.getModel());
                break;
            case R.id.trBattery:
                requestBattery(iManager.getModel());
                break;
            case R.id.trDefaultExploitation:
                requestDefaultExploitation(iManager.getModel());
                break;
            case R.id.trDps:
                //requestDefaultExploitation(iManager.getModel());
                break;
        }
    }

    @Override
    public void requestName() {
        Fragment_Request_Name f = new Fragment_Request_Name();
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, f, Constants_Intern.FRAGMENT_REQUEST_NAME).commit();
    }

    public void requestManufacturer() {
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MANUFACTURER_ALL, null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) {
                try {
                    ArrayList<Manufacturer> lManufacturer = new ArrayList<>();
                    JSONArray jsonManufacturers = oJson.getJSONArray(Constants_Extern.LIST_MANUFACTURER);
                    for (int i = 0; i < jsonManufacturers.length(); i++) {
                        Manufacturer oManufacturer = new Manufacturer(jsonManufacturers.getJSONObject(i));
                        lManufacturer.add(oManufacturer);
                    }

                    // Show
                    Bundle b = new Bundle();
                    b.putParcelableArrayList(Constants_Intern.LIST_ADDITIVE, lManufacturer);
                    startFragmentChoice(b, Constants_Intern.FRAGMENT_REQUEST_MANUFACTURER);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        /*
        uNetwork.getManufactures(new Interface_VolleyCallback_ArrayList_Additive() {
            @Override
            public void onSuccess(ArrayList<Additive> list) {
                Bundle b = new Bundle();
                b.putParcelableArrayList(Constants_Intern.LIST_ADDITIVE, list);
                startFragmentChoice(b, Constants_Intern.FRAGMENT_REQUEST_MANUFACTURER);
            }
        });
        */
    }

    @Override
    public void requestCharger(Model model) {
        uNetwork.getChargers(model, new Interface_VolleyCallback_ArrayList_Additive() {
            @Override
            public void onSuccess(ArrayList<Additive> list) {
                Bundle b = new Bundle();
                b.putParcelableArrayList(Constants_Intern.LIST_ADDITIVE, list);
                startFragmentChoice(b, Constants_Intern.FRAGMENT_REQUEST_CHARGER);
            }
        });
    }

    @Override
    public void requestBatteryRemovable(Model model) {
        Fragment_Interaction fRequestButtons = new Fragment_Interaction();
        Bundle bundle = new Bundle();
        bundle.putString(Constants_Intern.TITLE_MAIN, getString(R.string.request));
        String[] lTitle = new String[1];
        lTitle[0] = getString(R.string.request_battery_removable_title);
        bundle.putStringArray(Constants_Intern.LIST_TITLE, lTitle);
        String[] lButtons = new String[2];
        lButtons[0] = getString(R.string.yes);
        lButtons[1] = getString(R.string.no);
        bundle.putStringArray(Constants_Intern.LIST_BUTTONS, lButtons);
        int[] lColorIds = {R.color.color_choice_positive, R.color.color_choice_negative};
        bundle.putIntArray(Constants_Intern.LIST_COLOR_IDS, lColorIds);
        fRequestButtons.setArguments(bundle);
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, fRequestButtons, Constants_Intern.FRAGMENT_REQUEST_BATTERY_REMOVABLE).commit();
    }

    @Override
    public void requestBattery(Model model) {
        Fragment_Request_Battery fragment = new Fragment_Request_Battery();
        Bundle b = new Bundle();
        b.putInt(Constants_Intern.SELECTION_ID_MODEL, model.getkModel());
        fragment.setArguments(b);
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, fragment, Constants_Intern.FRAGMENT_REQUEST_BATTERY).commit();
    }

    @Override
    public void requestDefaultExploitation(Model model) {
        Fragment_Request_Exploitation f = new Fragment_Request_Exploitation();
        Bundle b = new Bundle();
        b.putInt(Constants_Intern.SELECTION_ID_MODEL, model.getkModel());
        f.setArguments(b);
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, f, Constants_Intern.FRAGMENT_REQUEST_DEFAULT_EXPLOITATION).commit();
    }

    @Override
    public void requestDps() {
    }

    private void startFragmentChoice(Bundle bundle, String fragmentTag) {
        Fragment_Request_Choice f = new Fragment_Request_Choice();
        f.setArguments(bundle);
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, f, fragmentTag).commit();
    }
}
