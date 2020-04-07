package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Edit.Fragment_Edit_Model_Battery;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Choice;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Name_Battery;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Request.Fragment_Request_Name_Model;
import com.example.ericschumacher.bouncer.Interfaces.Interface_DeviceManager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Model;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
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

    // vLayout
    View Layout;

    TableRow trName;
    TableRow trManufacturer;
    TableRow trCharger;
    TableRow trBatteryRemovable;
    TableRow trBackcoverRemovable;
    TableRow trBattery;
    TableRow trBatteryLku;
    TableRow trDefaultExploitation;
    TableRow trDps;
    TableRow trPhone;

    TextView tvName;
    TextView tvManufacturer;
    TextView tvCharger;
    TextView tvBatteryRemovable;
    TextView tvBackcoverRemovable;
    TextView tvBattery;
    TextView tvBatteryLku;
    TextView tvDefaultExploitation;
    TextView tvDps;
    TextView tvPhone;

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

        // vLayout
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
        trBackcoverRemovable = Layout.findViewById(R.id.trBackcoverRemovable);
        trBattery = Layout.findViewById(R.id.trBattery);
        trBatteryLku = Layout.findViewById(R.id.trBatteryLku);
        trDefaultExploitation = Layout.findViewById(R.id.trDefaultExploitation);
        trDps = Layout.findViewById(R.id.trDps);
        trPhone = Layout.findViewById(R.id.trPhone);

        tvName = Layout.findViewById(R.id.tvName);
        tvManufacturer = Layout.findViewById(R.id.tvManufacturer);
        tvCharger = Layout.findViewById(R.id.tvCharger);
        tvBatteryRemovable = Layout.findViewById(R.id.tvBatteryRemovable);
        tvBackcoverRemovable = Layout.findViewById(R.id.tvBackcoverRemovable);
        tvBattery = Layout.findViewById(R.id.tvBattery);
        tvBatteryLku = Layout.findViewById(R.id.tvBatteryLku);
        tvDefaultExploitation = Layout.findViewById(R.id.tvDefaultExploitation);
        tvDps = Layout.findViewById(R.id.tvDps);
        tvPhone = Layout.findViewById(R.id.tvPhone);

        trName.setOnClickListener(this);
        trManufacturer.setOnClickListener(this);
        trCharger.setOnClickListener(this);
        trBattery.setOnClickListener(this);
        trBatteryRemovable.setOnClickListener(this);
        trBackcoverRemovable.setOnClickListener(this);
        trDefaultExploitation.setOnClickListener(this);
        trDps.setOnClickListener(this);
        trPhone.setOnClickListener(this);
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
            tvPhone.setText(model.gettPhoneName(getActivity()));
            if (model.isBatteryRemovable() != null) {
                tvBatteryRemovable.setText((model.isBatteryRemovable()) ? getString(R.string.yes) : getString(R.string.no));
            } else {
                tvBatteryRemovable.setText(Constants_Intern.UNKOWN);
            }
            if (model.isBackcoverRemovable() != null) {
                tvBackcoverRemovable.setText((model.isBackcoverRemovable()) ? getString(R.string.yes) : getString(R.string.no));
            } else {
                tvBackcoverRemovable.setText(Constants_Intern.UNKOWN);
            }
            if (model.getoBattery() != null) {
                tvBattery.setText(model.getoBattery().getName());
            } else {
                tvBattery.setText(Constants_Intern.UNKOWN);
            }
            if (model.getoBattery() != null && model.getoBattery().getLku() != null) {
                Log.i("BBBAT: ", Integer.toString(model.getoBattery().getLku()));
                tvBatteryLku.setText(model.getoBattery().getoManufacturer().getcShortcut()+"-"+Integer.toString(model.getoBattery().getLku()));
            } else {
                tvBattery.setText("-");
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
            case R.id.trPhone:
                requestTypePhone();
                break;
            case R.id.trBatteryRemovable:
                requestBatteryRemovable(iManager.getModel());
                break;
            case R.id.trBackcoverRemovable:
                requestBackcoverRemovable(iManager.getModel());
                break;
            case R.id.trBattery:
                // make a choice here - If not Battery connected (null) or one - Just show editText - Else if more than one option show list
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
        Fragment_Request_Name_Model fragment = new Fragment_Request_Name_Model();
        Bundle bData = new Bundle();
        bData.putString(Constants_Intern.INTERACTION_TITLE, getString(R.string.interaction_title_request_name_model));
        fragment.setArguments(bData);
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, fragment, Constants_Intern.FRAGMENT_REQUEST_NAME_MODEL).commit();
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
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_CHARGER_BY_MANUFACTURER+Integer.toString(model.getoManufacturer().getId()), null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (oJson.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                    JSONArray jsonArray = oJson.getJSONArray(Constants_Extern.LIST_CHARGER);
                    ArrayList<Charger> lCharger = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        Charger oCharger  = new Charger(jsonObject);
                        lCharger.add(oCharger);
                    }
                    Bundle b = new Bundle();
                    b.putParcelableArrayList(Constants_Intern.LIST_ADDITIVE, lCharger);
                    startFragmentChoice(b, Constants_Intern.FRAGMENT_REQUEST_CHARGER);
                }
            }
        });
        /*
        uNetwork.getChargers(model, new Interface_VolleyCallback_ArrayList_Additive() {
            @Override Error here
            public void onSuccess(ArrayList<Additive> list) {
                Bundle b = new Bundle();
                b.putParcelableArrayList(Constants_Intern.LIST_ADDITIVE, list);
                startFragmentChoice(b, Constants_Intern.FRAGMENT_REQUEST_CHARGER);
            }
        });
        */
    }

    @Override
    public void requestTypePhone() {
        Fragment_Interaction_Simple_Choice fRequestButtons = new Fragment_Interaction_Simple_Choice();
        Bundle bundle = new Bundle();
        bundle.putString(Constants_Intern.INTERACTION_TITLE, getString(R.string.typePhone));
        int[] lDrawableIds = new int[2];
        lDrawableIds[0] = R.drawable.button_interaction_shape_very_good;
        lDrawableIds[1] = R.drawable.button_interaction_shape_cherry;
        bundle.putIntArray(Constants_Intern.LIST_DRAWABLE_IDS, lDrawableIds);
        int[] lColorIds = new int[2];
        lColorIds[0] = R.color.color_shape_very_good;
        lColorIds[1] = R.color.color_shape_cherry;
        bundle.putIntArray(Constants_Intern.LIST_COLOR_IDS, lColorIds);
        String[] lButtons = new String[2];
        lButtons[0] = getString(R.string.handy);
        lButtons[1] = getString(R.string.smartphone);
        bundle.putStringArray(Constants_Intern.LIST_BUTTONS, lButtons);
        fRequestButtons.setArguments(bundle);
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, fRequestButtons, Constants_Intern.FRAGMENT_REQUEST_PHONE_TYPE).commit();
    }

    @Override
    public void requestBatteryRemovable(Model model) {
        Fragment_Interaction_Simple_Choice fRequestButtons = new Fragment_Interaction_Simple_Choice();
        Bundle bundle = new Bundle();
        bundle.putString(Constants_Intern.INTERACTION_TYPE, getString(R.string.interaction_simple_choice));
        bundle.putString(Constants_Intern.INTERACTION_TITLE, getString(R.string.battery_removable));
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
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, fRequestButtons, Constants_Intern.FRAGMENT_REQUEST_BATTERY_REMOVABLE).commit();
    }

    @Override
    public void requestBackcoverRemovable(Model model) {
        Fragment_Interaction_Simple_Choice fRequestButtons = new Fragment_Interaction_Simple_Choice();
        Bundle bundle = new Bundle();
        bundle.putString(Constants_Intern.INTERACTION_TYPE, getString(R.string.interaction_simple_choice));
        bundle.putString(Constants_Intern.INTERACTION_TITLE, getString(R.string.backcover_removable));
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
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, fRequestButtons, Constants_Intern.FRAGMENT_REQUEST_BACKCOVER_REMOVABLE).commit();
    }

    @Override
    public void requestBattery(Model model) {

        if (model.getlModelBatteries().size() < 1) {
            Fragment_Request_Name_Battery fragment = new Fragment_Request_Name_Battery();
            Bundle b = new Bundle();
            b.putInt(Constants_Intern.TYPE_INTERFACE, Constants_Intern.TYPE_INTERFACE_FROM_ACTIVITY);
            b.putString(Constants_Intern.INTERACTION_TITLE, getString(R.string.request_name_battery));
            fragment.setArguments(b);
            fManager.beginTransaction().replace(R.id.flFragmentInteraction, fragment, Constants_Intern.FRAGMENT_REQUEST_MODEL_BATTERY).commit();
            Log.i("HHHHHE", "jo");
        } else {
            Fragment_Edit_Model_Battery fragment = new Fragment_Edit_Model_Battery();
            Bundle bData = new Bundle();
            bData.putString(Constants_Intern.INTERACTION_TITLE, getString(R.string.interaction_title_edit_model_battery));
            bData.putInt(Constants_Intern.ID_MODEL, model.getkModel());
            fragment.setArguments(bData);
            fManager.beginTransaction().replace(R.id.flFragmentInteraction, fragment, Constants_Intern.FRAGMENT_EDIT_MODEL_BATTERY).commit();
        }

        /*
        Fragment_Request_Battery fragment = new Fragment_Request_Battery();
        Bundle b = new Bundle();
        b.putInt(Constants_Intern.SELECTION_ID_MODEL, model.getkModel());
        fragment.setArguments(b);
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, fragment, Constants_Intern.FRAGMENT_REQUEST_BATTERY).commit();
        */

    }

    @Override
    public void requestDefaultExploitation(Model model) {
        //old
        /*
        Fragment_Request_Exploitation f = new Fragment_Request_Exploitation();
        Bundle b = new Bundle();
        b.putInt(Constants_Intern.SELECTION_ID_MODEL, model.getkModel());
        f.setArguments(b);
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, f, Constants_Intern.FRAGMENT_REQUEST_DEFAULT_EXPLOITATION).commit();
        */

        // new

        Fragment_Interaction_Simple_Choice fRequestButtons = new Fragment_Interaction_Simple_Choice();
        Bundle bundle = new Bundle();
        bundle.putString(Constants_Intern.INTERACTION_TYPE, getString(R.string.interaction_simple_choice));
        bundle.putString(Constants_Intern.INTERACTION_TITLE, getString(R.string.default_exploitation));
        int[] lDrawableIds = new int[3];
        lDrawableIds[0] = R.drawable.button_interaction_default_exploitation_recycling;
        lDrawableIds[1] = R.drawable.button_interaction_default_exploitation_reuse;
        lDrawableIds[2] = R.drawable.button_interaction_default_exploitation_defect_reuse;
        bundle.putIntArray(Constants_Intern.LIST_DRAWABLE_IDS, lDrawableIds);
        int[] lColorIds = new int[3];
        lColorIds[0] = R.color.color_recycling;
        lColorIds[1] = R.color.color_intact_reuse;
        lColorIds[2] = R.color.color_defect_reuse;
        bundle.putIntArray(Constants_Intern.LIST_COLOR_IDS, lColorIds);
        String[] lButtons = new String[3];
        lButtons[0] = getString(R.string.recycling);
        lButtons[1] = getString(R.string.reuse);
        lButtons[2] = getString(R.string.defect_reuse);
        bundle.putStringArray(Constants_Intern.LIST_BUTTONS, lButtons);
        fRequestButtons.setArguments(bundle);
        fManager.beginTransaction().replace(R.id.flFragmentInteraction, fRequestButtons, Constants_Intern.FRAGMENT_REQUEST_DEFAULT_EXPLOITATION).commit();
    }

    @Override
    public void requestModelDamages(Model model) {

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
