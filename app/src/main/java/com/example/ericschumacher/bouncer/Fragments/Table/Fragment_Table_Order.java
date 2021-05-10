package com.example.ericschumacher.bouncer.Fragments.Table;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Ann;
import com.example.ericschumacher.bouncer.R;

import org.json.JSONArray;

import java.util.ArrayList;

public class Fragment_Table_Order extends Fragment_Table {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Ann
        lAnn.add(new Ann(null, Constants_Intern.OBJECT_COLLECTOR, Constants_Intern.NAME, getString(R.string.receiver), 3));
        lAnn.add(new Ann(null , Constants_Intern.OBJECT_MARKETING_PACKAGE, Constants_Intern.NAME, getString(R.string.marketing_package), 1));
        lAnn.add(new Ann(null, null, Constants_Intern.NUMBER_BRICOLAGE, getString(R.string.bricolages), 1));
        lAnn.add(new Ann(null, null, Constants_Intern.NUMBER_BOX, getString(R.string.mobile_boxes), 1));
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        switch (getTag()) {
            case Constants_Intern.FRAGMENT_TABLE_OPEN_ORDERS:
                tvTitle.setText(getString(R.string.open_orders));
                break;
            case Constants_Intern.FRAGMENT_TABLE_RECENT_ORDERS:
                tvTitle.setText(getString(R.string.recent_orders));
                break;
        }

    }

    /*
    @Override
    public void onSearch() {
        Log.i("jo", "jo");
        lData = null;
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_COLLECTORS_BY_INPUT+etSearch.getText().toString(), null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (Volley_Connection.successfulResponse(oJson)) {
                    lData = oJson.getJSONArray(Constants_Extern.LIST_COLLECTOR);
                    aTable = new Adapter_Table(getActivity(), Fragment_Table_Input_Collector.this);
                    rvList.setAdapter(aTable);
                }
            }
        });
    }
    */

    @Override
    public ArrayList<Ann> getAnn() {
        return lAnn;
    }

    @Override
    public JSONArray getJsonArray() {
        return ((Interface_Fragment_Table_Select_DataImport)iFragmentTable).getData(getTag());
    }

    @Override
    public boolean hasHeader() {
        return true;
    }

    @Override
    public boolean isSelected(int position) {
        Log.i("catch", "here");
        return ((Interface_Fragment_Table_Select_DataImport)iFragmentTable).isSelected(getTag(), position);
    }
}
