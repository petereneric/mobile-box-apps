package com.example.ericschumacher.bouncer.Fragments.Fragment_Request;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Request_Name_Model extends Fragment_Request_Name_New {

    private ArrayList<Model> lModels = new ArrayList<>();

    @Override
    public void loadData(String sName) {
        lModels.clear();
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODELS_BY_NAMEPART + sName, null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (oJson.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                    JSONArray jsonArray = oJson.getJSONArray(Constants_Extern.LIST_MODELS);
                    for (int i = 0; i<jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        lModels.add(new Model(getActivity(), jsonObject));
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
        lModels.clear();
        update();
    }

    @Override
    public int getCount() {
        return lModels.size();
    }

    @Override
    public void onClick(int position) {
        iRequestName.returnRequestNameResult(getTag(), lModels.get(position));
    }

    @Override
    public void onCommit(final String cName) {
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_BY_NAME + cName, null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (oJson.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                    iRequestName.returnRequestNameResult(getTag(), new Model(getActivity(), oJson.getJSONObject(Constants_Extern.OBJECT_MODEL)));
                    //cVolley.execute(Request.Method.PUT, Urls.URL_PUT_MODEL_TAC + oDevice.getoModel().getkModel() + "/" + oDevice.getTAC(), null);
                } else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle(getString(R.string.add_model)+" "+cName);
                    builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, final int i) {
                            cVolley.getResponse(Request.Method.PUT, Urls.URL_PUT_MODEL_ADD + cName, null, new Interface_VolleyResult() {
                                @Override
                                public void onResult(JSONObject oJson) throws JSONException {
                                    iRequestName.returnRequestNameResult(getTag(), new Model(getActivity(), oJson.getJSONObject(Constants_Extern.OBJECT_MODEL)));
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

    public void onUnknown() {
        iRequestName.returnRequestNameResult(getTag(), null);
    }

    @Override
    public String getName(int position) {
        return lModels.get(position).getName();
    }

}
