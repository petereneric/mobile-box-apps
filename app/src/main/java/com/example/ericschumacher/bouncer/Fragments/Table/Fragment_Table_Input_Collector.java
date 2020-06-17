package com.example.ericschumacher.bouncer.Fragments.Table;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Ann;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Table_Input_Collector extends Fragment_Table_Input {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ann
        lAnn.add(new Ann(null, null, Constants_Intern.NAME, getString(R.string.name), 1));
        lAnn.add(new Ann(null , null, Constants_Intern.PRENAME_PERSON, getString(R.string.prename_person), 4));
        lAnn.add(new Ann(null, null, Constants_Intern.SURNAME_PERSON, getString(R.string.surname_person), 4));
        lAnn.add(new Ann(null, null, Constants_Intern.CITY, getString(R.string.city), 4));
    }

    @Override
    public void onSearch() {
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_COLLECTORS_BY_INPUT+etSearch.getText().toString(), null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (Volley_Connection.successfulResponse(oJson)) {
                    lData = oJson.getJSONArray(Constants_Extern.LIST_COLLECTOR);
                    update();
                } else {
                    reset();
                }
            }
        });
    }

    @Override
    public ArrayList<Ann> getAnn() {
        lAnn.add(new Ann(null, Constants_Intern.OBJECT_MODEL, Constants_Intern.ID_MODEL, getString(R.string.id), 1));
        lAnn.add(new Ann(Constants_Intern.OBJECT_MODEL,Constants_Intern.OBJECT_MANUFACTURER, Constants_Intern.NAME_MANUFACTURER, getString(R.string.manufacturer), 4));
        lAnn.add(new Ann(null, Constants_Intern.OBJECT_MODEL, Constants_Intern.NAME_MODEL, getString(R.string.model), 4));
        return lAnn;
    }
}
