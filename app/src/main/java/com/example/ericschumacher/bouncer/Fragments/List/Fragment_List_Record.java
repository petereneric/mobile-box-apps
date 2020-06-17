package com.example.ericschumacher.bouncer.Fragments.List;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Ann;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_List_Record extends Fragment_List {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set Adapter
        //aList = new Adapter_Table(getActivity(), true, this, lData, getListAnn());
        rvList.setAdapter(aList);

        // Load data
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_RECORDS_INPROGRESS, null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (Volley_Connection.successfulResponse(oJson)) {
                    lData = oJson.getJSONArray(Constants_Extern.LIST_RECORDS);
                }
                aList.notifyDataSetChanged();
            }
        });
    }

    private ArrayList<Ann> getListAnn() {
        ArrayList<Ann> lAnn = new ArrayList<>();
        lAnn.add(new Ann(null, null, Constants_Extern.ID_RECORD, getString(R.string.id_record), 1));
        lAnn.add(new Ann(null, Constants_Extern.OBJECT_COLLECTOR, Constants_Extern.NAME, getString(R.string.name_collector), 4));
        lAnn.add(new Ann(null, Constants_Extern.OBJECT_COLLECTOR, Constants_Extern.DATE_CREATION, getString(R.string.date_creation), 2));

        return lAnn;
    }
}
