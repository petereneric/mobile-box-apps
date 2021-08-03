package com.example.ericschumacher.bouncer.Fragments.Table;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Adapter.Table.Adapter_Table;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Ann;
import com.example.ericschumacher.bouncer.Objects.Collection.Record;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Table_Input_Collector extends Fragment_Table_Input {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ann
        lAnn.add(new Ann(null, null, Constants_Intern.NAME, getString(R.string.name), 2));
        lAnn.add(new Ann(null , null, Constants_Intern.PRENAME_PERSON, getString(R.string.prename_person), 1));
        lAnn.add(new Ann(null, null, Constants_Intern.SURNAME_PERSON, getString(R.string.surname_person), 1));
        lAnn.add(new Ann(null, null, Constants_Intern.SHIPPING_CITY, getString(R.string.city), 1));
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        tvTitle.setText(getString(R.string.collector));
        tvSearchType.setText(getString(R.string.name));
    }

    public void setInterface() {
        iFragmentTable = (Interface_Fragment_Table_Input_Collector) getActivity();
    }

    @Override
    public void onSearch() {
        Log.i("jo", "jo");
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_COLLECTORS_BY_INPUT+etSearch.getText().toString(), null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (Volley_Connection.successfulResponse(oJson)) {
                    lData = oJson.getJSONArray(Constants_Extern.LIST_COLLECTOR);
                } else {
                    lData = new JSONArray();
                }
                aTable = new Adapter_Table(getActivity(), Fragment_Table_Input_Collector.this);
                rvList.setAdapter(aTable);
            }
        });
        if (etSearch.getText().toString().length() >= 10 && etSearch.getText().toString().matches(".*\\d.*")) {
            Log.i("Search for ShippingId", "Check");
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_RECORD_BY_SHIPPINGNUMBER + etSearch.getText().toString(), null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    if (oJson.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                        Record record = new Record(getActivity(), oJson.getJSONObject(Constants_Extern.OBJECT_RECORD));
                        ((Interface_Fragment_Table_Input_Collector)iFragmentTable).returnTableRecord(getTag(), record);
                    } else {
                        if (!oJson.isNull(Constants_Extern.DETAILS)) {
                            Utility_Toast.showString(getActivity(), oJson.getString(Constants_Extern.DETAILS));
                        }
                    }
                }
            });
        }
    }

    @Override
    public ArrayList<Ann> getAnn() {
        return lAnn;
    }

    @Override
    public boolean hasHeader() {
        return true;
    }

    public interface Interface_Fragment_Table_Input_Collector extends Interface_Fragment_Table{
        public void returnTableRecord(final String cTag, Record record);
    }
}
