package com.example.ericschumacher.bouncer.Fragments.Fragment_Request;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Request_Name_Battery extends Fragment_Request_Name_New {

    private ArrayList<Battery> lBatteries = new ArrayList<>();

    @Override
    public void loadData(String sName) {
            lBatteries.clear();
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_BATTERY_BY_NAME_PART+sName, null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    if (Volley_Connection.successfulResponse(oJson)) {
                        JSONArray jsonArray = oJson.getJSONArray(Constants_Extern.LIST_BATTERIES);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Battery oBattery = new Battery(jsonArray.getJSONObject(i), getActivity());
                            lBatteries.add(oBattery);
                        }
                        update();
                    } else {
                        reset();
                    }
                }
            });

    }

    @Override
    public void reset() {
        lBatteries.clear();
        update();
    }

    @Override
    public void setLayout() {
        super.setLayout();
    }

    @Override
    public int getCount() {
        return lBatteries.size();
    }

    @Override
    public void onClick(int position) {
        iRequestName.returnRequestNameResult(getTag(), lBatteries.get(position));
    }

    @Override
    public String getName(int position) {
        return lBatteries.get(position).getName();
    }

    @Override
    public void onCommit(final String cName) {
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_BATTERY_BY_NAME + cName, null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (Volley_Connection.successfulResponse(oJson)) {
                    Battery oBattery = new Battery(oJson.getJSONObject(Constants_Extern.OBJECT_BATTERY), getActivity());
                    iRequestName.returnRequestNameResult(getTag(), oBattery);
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(getString(R.string.add_battery)+" "+cName);
                    builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, final int i) {
                            cVolley.getResponse(Request.Method.PUT, Urls.URL_PUT_BATTERY_ADD + cName + "/" + iRequestName.getModel().getoManufacturer().getId(), null, new Interface_VolleyResult() {
                                @Override
                                public void onResult(JSONObject oJson) throws JSONException {
                                    if (Volley_Connection.successfulResponse(oJson)) {
                                        Battery oBattery = new Battery(oJson.getJSONObject(Constants_Extern.OBJECT_BATTERY), getActivity());
                                        iRequestName.returnRequestNameResult(getTag(), oBattery);
                                    }
                                }
                            });
                        }
                    });
                    builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }

    @Override
    public void onUnknown() {
        iRequestName.returnRequestNameResult(getTag(), null);
    }
}
