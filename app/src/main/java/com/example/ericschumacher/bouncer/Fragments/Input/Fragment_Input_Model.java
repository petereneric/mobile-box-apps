package com.example.ericschumacher.bouncer.Fragments.Input;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Fragment_Input_Model extends Fragment_Input {

    // Class methods
    public void onSearchChanged(String cSearch) {
        super.onSearchChanged(cSearch);
        final String cSearchSaved = cSearch;
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODELS_BY_NAMEPART+cSearch, null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (etSearch.getText().toString().equals(cSearchSaved)) {
                    if (Volley_Connection.successfulResponse(oJson)) {
                        lSearch.clear();
                        // check if getEditText equals saved edittext
                        JSONArray jsonArray = oJson.getJSONArray(Constants_Extern.LIST_MODELS);
                        for (int i = 0; i<jsonArray.length(); i++) {
                            lSearch.add(jsonArray.getJSONObject(i).getString(Constants_Extern.NAME_MODEL));
                        }
                        aSearch.notifyDataSetChanged();
                    } else {
                        clearSearch();
                    }
                }
            }
        });
    }
}
