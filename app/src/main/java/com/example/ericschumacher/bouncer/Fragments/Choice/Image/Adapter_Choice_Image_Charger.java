package com.example.ericschumacher.bouncer.Fragments.Choice.Image;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Adapter.List.Choice.Adapter_List_Choice_Image;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Adapter_Choice_Image_Charger extends Fragment_Choice_Image implements Fragment_Choice_Image.Interface_Adapter_Choice_Image {

    // Data
    ArrayList<Charger> lCharger = new ArrayList<>();

    // Arguments
    Integer kManufacturer = null;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set Adapter
        aChoice = new Adapter_List_Choice_Image(getActivity(), this);

        // Arguments
        kManufacturer = bData.getInt(Constants_Intern.ID_MANUFACTURER);

        // Load Data
        if (kManufacturer != null) {
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_CHARGER_BY_MANUFACTURER+kManufacturer, null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    JSONArray jsonArray = oJson.getJSONArray(Constants_Extern.LIST_MANUFACTURER);
                    for (int i = 0; i<jsonArray.length(); i++) {
                        lCharger.add(new Charger(jsonArray.getJSONObject(i)));
                    }
                }
            });
        } else {
            // Get all chargers here
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    // Interface Methods
    @Override
    public String getUrlIconOne(int position) {
        return lCharger.get(position).getUrlIcon();
    }

    @Override
    public String getUrlIconTwo(int position) {
        return null;
    }

    @Override
    public void onAdapterClick(int position) {
        iChoice.returnChoice(getTag(), lCharger.get(position));
    }

    @Override
    public Integer getViewHolderLayout() {
        return R.layout.item_choice_image;
    }

    @Override
    public String getName(int position) {
        return lCharger.get(position).getName();
    }

    @Override
    public Integer getItemCount() {
        return lCharger.size();
    }
}
