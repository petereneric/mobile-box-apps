package com.example.ericschumacher.bouncer.Utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Additive;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Devices;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Input;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_ModelColorShapeIds;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Int;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_JSON;
import com.example.ericschumacher.bouncer.Objects.Additive.Additive;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Additive.Station;
import com.example.ericschumacher.bouncer.Objects.Additive.Variation_Color;
import com.example.ericschumacher.bouncer.Objects.Additive.Variation_Shape;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.Objects.Object_Choice;
import com.example.ericschumacher.bouncer.Objects.Object_SearchResult;
import com.example.ericschumacher.bouncer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 19.05.2018.
 */

public class Utility_Network {

    // Variables
    Context Context;
    RequestQueue queue;

    // Interfaces
    Interface_Selection iSelection;

    public Utility_Network(Context context) {
        Context = context;
        iSelection = (Interface_Selection) context;
        queue = Volley.newRequestQueue(Context);
    }

    public void getMatchingModels(String namePart, final Interface_VolleyCallback_ArrayList_Input iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/model/all/" + namePart;
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response: ", response);
                    try {
                        ArrayList<Object_SearchResult> list = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            list.add(new Object_SearchResult(jsonObject.getInt(Constants_Extern.ID), jsonObject.getString(Constants_Extern.NAME)));
                        }
                        iCallback.onSuccess(list);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null && response.statusCode == 200) {
                        responseString = new String(response.data);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            queue.add(stringRequest);
            queue.getCache().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getModelByTac(final Device device, final Interface_VolleyCallback iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/model/tac/" + device.getTAC();
        Log.i("Checking", url);
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                            device.setIdModel(jsonObject.getInt(Constants_Extern.ID_MODEL));
                            device.setName(jsonObject.getString(Constants_Extern.NAME_MODEL));
                            if (!jsonObject.isNull(Constants_Extern.ID_BATTERY)) {
                                device.setBattery(new Battery(jsonObject.getInt(Constants_Extern.ID_BATTERY), jsonObject.getString(Constants_Extern.NAME_BATTERY)));
                            }
                            if (!jsonObject.isNull(Constants_Extern.ID_CHARGER)) {
                                device.setCharger(new Charger(jsonObject.getInt(Constants_Extern.ID_CHARGER), jsonObject.getString(Constants_Extern.NAME_CHARGER)));
                            }
                            if (!jsonObject.isNull(Constants_Extern.ID_MANUFACTURER)) {
                                device.setManufacturer(new Manufacturer(jsonObject.getInt(Constants_Extern.ID_MANUFACTURER), jsonObject.getString(Constants_Extern.NAME_MANUFACTURER)));
                            }
                            device.setExploitation(jsonObject.getInt(Constants_Extern.EXPLOITATION));
                            iCallback.onSuccess();
                        } else {
                            iCallback.onFailure();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null && response.statusCode == 200) {
                        responseString = new String(response.data);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            queue.add(stringRequest);
            queue.getCache().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getLKU(Device o, final Interface_VolleyCallback_Int iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/lku/" + Integer.toString(o.getIdModel());
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                            iCallback.onSuccess(jsonObject.getInt(Constants_Extern.ID_LKU));
                        } else {
                            iCallback.onFailure();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null && response.statusCode == 200) {
                        responseString = new String(response.data);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            queue.add(stringRequest);
            queue.getCache().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connectWithLku(Device o, final Interface_VolleyCallback_Int iCallback) {
        if (o.testMode()) {
            iCallback.onSuccess(0);
        } else {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/lku/connect/" + Integer.toString(o.getIdModel());
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response: ", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                                iCallback.onSuccess(jsonObject.getInt(Constants_Extern.ID_LKU));
                            } else {
                                iCallback.onFailure();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null && response.statusCode == 200) {
                            responseString = new String(response.data);
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                queue.add(stringRequest);
                queue.getCache().clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void assignLku(Device o, final Interface_VolleyCallback_Int iCallback) {
        if (o.testMode()) {
            iCallback.onSuccess(0);
        } else {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/lku/assign/" + Integer.toString(o.getIdDevice());
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response: ", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                                iCallback.onSuccess(jsonObject.getInt(Constants_Extern.ID_LKU));
                            } else {
                                iCallback.onFailure();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null && response.statusCode == 200) {
                            responseString = new String(response.data);
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                queue.add(stringRequest);
                queue.getCache().clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void reassignLKU(Device o, final Interface_VolleyCallback_Int iCallback) {
        if (o.testMode()) {
            iCallback.onSuccess(0);
        } else {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/lku/reassign/" + Integer.toString(o.getIdDevice());
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response: ", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                                iCallback.onSuccess(jsonObject.getInt(Constants_Extern.ID_LKU));
                            } else {
                                iCallback.onFailure();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null && response.statusCode == 200) {
                            responseString = new String(response.data);
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                queue.add(stringRequest);
                queue.getCache().clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void checkExploitation(Device o, final Interface_VolleyCallback_Int iCallback) {
        if (o.testMode()) {
            iCallback.onSuccess(2);
        } else {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/model/check/exploitation/" + Integer.toString(o.getIdModel());
            Log.i("urlCheck", url);
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response: ", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                                iCallback.onSuccess(jsonObject.getInt(Constants_Extern.EXPLOITATION));
                            } else {
                                iCallback.onFailure();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null && response.statusCode == 200) {
                            responseString = new String(response.data);
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                queue.add(stringRequest);
                queue.getCache().clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void exploitReuse(Device o) {
        if (o.testMode()) {

        } else {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/model/exploitation/reuse/" + Integer.toString(o.getIdModel());
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                                Toast.makeText(Context, Context.getString(R.string.added_to) + " " + Context.getString(R.string.reuse), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null && response.statusCode == 200) {
                            responseString = new String(response.data);
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                queue.add(stringRequest);
                queue.getCache().clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void exploitRecycling(Device o) {
        if (o.testMode()) {

        } else {
            Log.i("exploitRecycling", "started");
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/model/exploitation/recycling/" + Integer.toString(o.getIdModel());
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response: ", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                                Toast.makeText(Context, Context.getString(R.string.added_to) + " " + Context.getString(R.string.recycling), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null && response.statusCode == 200) {
                            responseString = new String(response.data);
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                queue.add(stringRequest);
                queue.getCache().clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void getIdModel_Name(final Device device, final Interface_VolleyCallback iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/model/id/name/" + device.getName() + "/" + device.getTAC();
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                            device.setIdModel(jsonObject.getInt(Constants_Extern.ID_MODEL));
                            device.setName(jsonObject.getString(Constants_Extern.NAME_MODEL));
                            if (!jsonObject.isNull(Constants_Extern.ID_BATTERY)) {
                                device.setBattery(new Battery(jsonObject.getInt(Constants_Extern.ID_BATTERY), jsonObject.getString(Constants_Extern.NAME_BATTERY)));
                            }
                            if (!jsonObject.isNull(Constants_Extern.ID_CHARGER)) {
                                device.setCharger(new Charger(jsonObject.getInt(Constants_Extern.ID_CHARGER), jsonObject.getString(Constants_Extern.NAME_CHARGER)));
                            }
                            if (!jsonObject.isNull(Constants_Extern.ID_MANUFACTURER)) {
                                device.setManufacturer(new Manufacturer(jsonObject.getInt(Constants_Extern.ID_MANUFACTURER), jsonObject.getString(Constants_Extern.NAME_MANUFACTURER)));
                            }
                            if (!jsonObject.isNull(Constants_Extern.EXPLOITATION)) {
                                device.setExploitation(jsonObject.getInt(Constants_Extern.EXPLOITATION));
                            }
                            iCallback.onSuccess();
                        } else {
                            iCallback.onFailure();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null && response.statusCode == 200) {
                        responseString = new String(response.data);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            queue.add(stringRequest);
            queue.getCache().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addModel(final Device device, final Interface_VolleyCallback_Int iCallback) {
        if (device.testMode()) {
            device.setIdModel(0);
        } else {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/model/add/" + device.getName() + "/" + device.getTAC();
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response: ", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            iCallback.onSuccess(jsonObject.getInt(Constants_Extern.ID_MODEL));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null && response.statusCode == 200) {
                            responseString = new String(response.data);
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                queue.add(stringRequest);
                queue.getCache().clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    // Battery
    public void addBattery(String name, Device o, final Interface_VolleyCallback_Int iCallback) {
        if (o.testMode()) {
            iCallback.onSuccess(0);
        } else {
            final String url = "http://svp-server.com/svp-gmbh/erp/bouncer/src/api.php/battery/add/" + name + "/" + Integer.toString(o.getManufacturer().getId());
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response: ", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                                iCallback.onSuccess(jsonObject.getInt(Constants_Extern.ID_BATTERY));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null && response.statusCode == 200) {
                            responseString = new String(response.data);
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                queue.add(stringRequest);
                queue.getCache().clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void getBattery(Device o, final Interface_VolleyCallback_JSON iCallback) {
        if (o.testMode()) {
            JSONObject json = new JSONObject();
            try {
                json.put(Constants_Extern.ID_BATTERY, 0);
                json.put(Constants_Extern.NAME_BATTERY, "test");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            iCallback.onSuccess(json);
        } else {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/battery/" + Integer.toString(o.getIdModel());
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response: ", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                                iCallback.onSuccess(jsonObject);
                            } else {
                                iCallback.onFailure(jsonObject);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null && response.statusCode == 200) {
                            responseString = new String(response.data);
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                queue.add(stringRequest);
                queue.getCache().clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void connectBatteryWithModel(Device o) {
        if (o.testMode()) {

        } else {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/battery/connect/" + Integer.toString(o.getBattery().getId()) + "/" + Integer.toString(o.getIdModel());
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response: ", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null && response.statusCode == 200) {
                            responseString = new String(response.data);
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                queue.add(stringRequest);
                queue.getCache().clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void getIdBattery(Device o, String name, final Interface_VolleyCallback_Int iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/battery/id/" + Integer.toString(o.getIdModel()) + "/" + name;
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                            iCallback.onSuccess(jsonObject.getInt(Constants_Extern.ID_BATTERY));
                        } else {
                            iCallback.onFailure();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null && response.statusCode == 200) {
                        responseString = new String(response.data);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            queue.add(stringRequest);
            queue.getCache().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMatchingBatteries(Model model, String namePart, final Interface_VolleyCallback_ArrayList_Input iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/battery/all/" + Integer.toString(model.getIdModel()) + "/" + namePart;
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response: ", response);
                    try {
                        ArrayList<Object_SearchResult> list = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            list.add(new Object_SearchResult(jsonObject.getInt(Constants_Extern.ID), jsonObject.getString(Constants_Extern.NAME)));
                        }
                        iCallback.onSuccess(list);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null && response.statusCode == 200) {
                        responseString = new String(response.data);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            queue.add(stringRequest);
            queue.getCache().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Charger
    public void getCharger(Model model, final Interface_VolleyCallback_JSON iCallback) {
        if (model instanceof Device && ((Device) model).testMode()) {
            JSONObject json = new JSONObject();
            try {
                json.put(Constants_Extern.ID_CHARGER, 0);
                json.put(Constants_Extern.NAME_CHARGER, "test");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            iCallback.onSuccess(json);
        } else {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/charger/" + Integer.toString(model.getIdModel());
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response: ", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                                iCallback.onSuccess(jsonObject);
                            } else {
                                iCallback.onFailure(jsonObject);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null && response.statusCode == 200) {
                            responseString = new String(response.data);
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                queue.add(stringRequest);
                queue.getCache().clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void connectChargerWithModel(Device o) {
        if (o.testMode()) {

        } else {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/charger/connect/" + Integer.toString(o.getIdModel()) + "/" + Integer.toString(o.getCharger().getId());
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response: ", response);

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null && response.statusCode == 200) {
                            responseString = new String(response.data);
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                queue.add(stringRequest);
                queue.getCache().clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public void getChargers(Model model, final Interface_VolleyCallback_ArrayList_Additive iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/charger/all/" + Integer.toString(model.getIdModel());
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response, Charger: ", response);
                    try {
                        ArrayList<Additive> chargers = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.i("Looping", "Array");
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            chargers.add(new Charger(jsonObject.getInt(Constants_Extern.ID), jsonObject.getString(Constants_Extern.NAME)));
                        }
                        iCallback.onSuccess(chargers);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null && response.statusCode == 200) {
                        responseString = new String(response.data);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            queue.add(stringRequest);
            queue.getCache().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getChargers(final Interface_VolleyCallback_ArrayList_Additive iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/charger/all/";
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response, Charger: ", response);
                    try {
                        ArrayList<Additive> chargers = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.i("Looping", "Array");
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            chargers.add(new Charger(jsonObject.getInt(Constants_Extern.ID), jsonObject.getString(Constants_Extern.NAME)));
                        }
                        iCallback.onSuccess(chargers);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null && response.statusCode == 200) {
                        responseString = new String(response.data);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            queue.add(stringRequest);
            queue.getCache().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    /*
    public void getManufacturer(final Device device, final Interface_VolleyCallback iCallback) {
        if (device.testMode()) {
            JSONObject json = new JSONObject();
            try {
                json.put(Constants_Extern.ID_MANUFACTURER, 0);
                json.put(Constants_Extern.NAME_MANUFACTURER, "test");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            iCallback.onSuccess(json);
        } else {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/model/manufacturer/" + Integer.toString(device.getId());
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response_Manufacturer: ", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                                device.setManufacturer(new Manufacturer(jsonObject.getInt(Constants_Extern.ID_MANUFACTURER), jsonObject.getString(Constants_Extern.NAME_MANUFACTURER)));
                            } else {
                                iCallback.onFailure();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null && response.statusCode == 200) {
                            responseString = new String(response.data);
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                queue.add(stringRequest);
                queue.getCache().clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    */


    public void addManufacturerToModel(Device o) {
        if (!o.testMode()) {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/model/add/manufacturer/" + Integer.toString(o.getIdModel()) + "/" + Integer.toString(o.getManufacturer().getId());
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response: ", response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null && response.statusCode == 200) {
                            responseString = new String(response.data);
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                queue.add(stringRequest);
                queue.getCache().clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public ArrayList<Object_Choice> getManufactures(final Interface_VolleyCallback_ArrayList_Additive iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/manufacturers";
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response_Manus: ", response);
                    try {
                        ArrayList<Additive> manufacturers = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.i("Looping", "Array");
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            manufacturers.add(new Manufacturer(jsonObject.getInt(Constants_Extern.ID), jsonObject.getString(Constants_Extern.NAME)));
                        }
                        iCallback.onSuccess(manufacturers);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null && response.statusCode == 200) {
                        responseString = new String(response.data);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            queue.add(stringRequest);
            queue.getCache().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getColors(Device o, final Interface_VolleyCallback_ArrayList_Additive iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/modelColor/all/" + Integer.toString(o.getIdModel());
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response: ", response);
                    try {
                        ArrayList<Additive> list = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            list.add(new Variation_Color(jsonObject.getInt(Constants_Extern.ID_COLOR), jsonObject.getString(Constants_Extern.NAME_COLOR), jsonObject.getString(Constants_Extern.HEX_CODE)));
                        }
                        iCallback.onSuccess(list);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null && response.statusCode == 200) {
                        responseString = new String(response.data);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            queue.add(stringRequest);
            queue.getCache().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getIdModelColor(Device o, final Interface_VolleyCallback_Int iCallback) {
        if (o.testMode()) {
            iCallback.onSuccess(0);
        } else {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/modelColor/" + Integer.toString(o.getIdModel()) + "/" + Integer.toString(o.getVariationColor().getId());
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response: ", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                                iCallback.onSuccess(jsonObject.getInt(Constants_Extern.ID_MODEL_COLOR));
                            } else {
                                iCallback.onFailure();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null && response.statusCode == 200) {
                            responseString = new String(response.data);
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                queue.add(stringRequest);
                queue.getCache().clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addModelColor(Device o, final Interface_VolleyCallback_Int iCallback) {
        if (o.testMode()) {
            iCallback.onSuccess(0);
        } else {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/modelColor/" + Integer.toString(o.getIdModel()) + "/" + Integer.toString(o.getVariationColor().getId());
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response: ", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                                iCallback.onSuccess(jsonObject.getInt(Constants_Extern.ID_MODEL_COLOR));
                            } else {
                                iCallback.onFailure();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VOLLEY", error.toString());
                    }
                }) {
                    @Override
                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                        String responseString = "";
                        if (response != null && response.statusCode == 200) {
                            responseString = new String(response.data);
                        }
                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                    }
                };
                queue.add(stringRequest);
                queue.getCache().clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void addDevice(Device o, final Interface_VolleyCallback_Int iCallback) {
        RequestQueue queue;
        queue = Volley.newRequestQueue(Context);
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/device";
        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put(Constants_Extern.IMEI, o.getIMEI());
            jsonBody.put(Constants_Extern.ID_MODEL, o.getIdModel());
            jsonBody.put(Constants_Extern.ID_COLOR, o.getVariationColor().getId());
            jsonBody.put(Constants_Extern.ID_SHAPE, o.getVariationShape().getId());
            jsonBody.put(Constants_Extern.ID_STATION, o.getStation().getId());
            jsonBody.put(Constants_Extern.DESTINATION, o.getDestination());
            final String requestBody = jsonBody.toString();
            Log.i("Show me", requestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                            iCallback.onSuccess(jsonObject.getInt(Constants_Extern.ID_DEVICE));
                        } else {
                            iCallback.onFailure();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null && response.statusCode == 200) {
                        responseString = new String(response.data);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            queue.add(stringRequest);
            queue.getCache().clear();

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void updateDevice(Device o, final Interface_VolleyCallback iCallback) {
        RequestQueue queue;
        queue = Volley.newRequestQueue(Context);
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/device/update";
        final JSONObject jsonBody = new JSONObject();
        try {
            final String requestBody = convertJsonToDevice(o).toString();
            Log.i("Show me", requestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                            iCallback.onSuccess();
                        } else {
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }

                @Override
                public byte[] getBody() throws AuthFailureError {
                    try {
                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                    } catch (UnsupportedEncodingException uee) {
                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                        return null;
                    }
                }

                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null && response.statusCode == 200) {
                        responseString = new String(response.data);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            queue.add(stringRequest);
            queue.getCache().clear();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDevicesForJuicer(Charger charger, final Interface_VolleyCallback_ArrayList_ModelColorShapeIds iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/ids/juicer" + "/" + Integer.toString(charger.getId());
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                            JSONArray jsonArray = jsonObject.getJSONArray(Constants_Extern.DEVICES);
                            ArrayList<Integer> modelColorShapeIds = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.i("Looping", "Array");
                                JSONObject json = jsonArray.getJSONObject(i);
                                modelColorShapeIds.add(json.getInt(Constants_Extern.ID_MODEL_COLOR_SHAPE));
                            }
                            iCallback.onSuccess(modelColorShapeIds);
                        } else {
                            iCallback.onFailure();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null && response.statusCode == 200) {
                        responseString = new String(response.data);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            queue.add(stringRequest);
            queue.getCache().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDevicesByIdModelColorShape(int idModelColorShape, int idStation, final Interface_VolleyCallback_ArrayList_Devices iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/devices/juicer" + "/" + Integer.toString(idModelColorShape);
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                            JSONArray jsonArray = jsonObject.getJSONArray(Constants_Extern.DEVICES);
                            ArrayList<Device> devices = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.i("Looping", "Array");
                                JSONObject json = jsonArray.getJSONObject(i);
                                devices.add(convertJsonToDevice(json));
                            }
                            iCallback.onSuccess(devices);
                        } else {
                            iCallback.onFailure();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null && response.statusCode == 200) {
                        responseString = new String(response.data);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            queue.add(stringRequest);
            queue.getCache().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDevice(int idDevice, final Interface_VolleyCallback_Device iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/device/" + Integer.toString(idDevice);
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                            JSONObject json = jsonObject.getJSONObject(Constants_Extern.DEVICE);
                            iCallback.onSuccess(convertJsonToDevice(json));
                        } else {
                            iCallback.onFailure();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null && response.statusCode == 200) {
                        responseString = new String(response.data);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            queue.add(stringRequest);
            queue.getCache().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteDevice(Device device, final Interface_VolleyCallback iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/device/" + Integer.toString(device.getIdDevice());
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                            iCallback.onSuccess();
                        } else {
                            iCallback.onFailure();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null && response.statusCode == 200) {
                        responseString = new String(response.data);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            queue.add(stringRequest);
            queue.getCache().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Device convertJsonToDevice(JSONObject json) {
        Device device = new Device();
        try {
            if (!json.isNull(Constants_Extern.ID_MODEL))
                device.setIdModel(json.getInt(Constants_Extern.ID_MODEL));
            if (!json.isNull(Constants_Extern.NAME_MODEL))
                device.setName(json.getString(Constants_Extern.NAME_MODEL));
            if (!json.isNull(Constants_Extern.EXPLOITATION_DEFAULT))
                device.setDefaultExploitation(json.getInt(Constants_Extern.EXPLOITATION_DEFAULT));
            if (!json.isNull(Constants_Extern.ID_MANUFACTURER) && !json.isNull(Constants_Extern.NAME_MANUFACTURER))
                device.setManufacturer(new Manufacturer(json.getInt(Constants_Extern.ID_MANUFACTURER), json.getString(Constants_Extern.NAME_MANUFACTURER)));
            if (!json.isNull(Constants_Extern.ID_BATTERY) && !json.isNull(Constants_Extern.NAME_BATTERY))
                device.setBattery(new Battery(json.getInt(Constants_Extern.ID_BATTERY), json.getString(Constants_Extern.NAME_BATTERY)));
            if (!json.isNull(Constants_Extern.ID_CHARGER) && !json.isNull(Constants_Extern.NAME_CHARGER))
                device.setCharger(new Charger(json.getInt(Constants_Extern.ID_CHARGER), json.getString(Constants_Extern.NAME_CHARGER)));
            if (!json.isNull(Constants_Extern.ID_DEVICE))
                device.setIdDevice(json.getInt(Constants_Extern.ID_DEVICE));
            if (!json.isNull(Constants_Extern.IMEI))
                device.setIMEI(json.getString(Constants_Extern.IMEI));
            if (!json.isNull(Constants_Extern.LKU))
                device.setLKU((json.getInt(Constants_Extern.LKU)));
            if (!json.isNull(Constants_Extern.CONDITION))
                device.setCondition(json.getInt(Constants_Extern.CONDITION));
            if (!json.isNull(Constants_Extern.DESTINATION))
                device.setDestination(json.getInt(Constants_Extern.DESTINATION));
            if (!json.isNull(Constants_Extern.ID_STATION))
                device.setStation(new Station(json.getInt(Constants_Extern.ID_STATION)));
            if (!json.isNull(Constants_Extern.ID_COLOR) && !json.isNull(Constants_Extern.NAME_COLOR) && !json.isNull(Constants_Extern.HEX_CODE))
                device.setVariationColor
                        (new Variation_Color(json.getInt(Constants_Extern.ID_COLOR), json.getString(Constants_Extern.NAME_COLOR), json.getString(Constants_Extern.HEX_CODE)));
            if (!json.isNull(Constants_Extern.ID_SHAPE) && !json.isNull(Constants_Extern.NAME_SHAPE))
                device.setVariationShape(new Variation_Shape(json.getInt(Constants_Extern.ID_SHAPE), json.getString(Constants_Extern.NAME_SHAPE)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return device;
    }

    private JSONObject convertJsonToDevice(Device device) {
        JSONObject json = new JSONObject();

        try {
            if (device.getIdDevice() != 0) json.put(Constants_Extern.ID_DEVICE, device.getIdDevice());
            if (device.getIMEI() != null) json.put(Constants_Extern.IMEI, device.getIMEI());
            if (device.getLKU() != 0) json.put(Constants_Extern.LKU, device.getLKU());
            if (device.getCondition() != 0) json.put(Constants_Extern.CONDITION, device.getCondition());
            if (device.getDestination() != 0) json.put(Constants_Extern.DESTINATION, device.getDestination());
            if (device.getStation() != null && device.getStation().getId() != 0) json.put(Constants_Extern.ID_STATION, device.getStation().getId());
            if (device.getVariationColor() != null && device.getVariationColor().getId() != 0) json.put(Constants_Extern.ID_COLOR, device.getVariationColor().getId());
            if (device.getVariationShape() != null && device.getVariationShape().getId() != 0) json.put(Constants_Extern.ID_SHAPE, device.getVariationShape().getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    // Collection Partner
    public void createCollector(Device device, final Interface_VolleyCallback iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/device/" + Integer.toString(device.getIdDevice());
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                            iCallback.onSuccess();
                        } else {
                            iCallback.onFailure();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VOLLEY", error.toString());
                }
            }) {
                @Override
                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                    String responseString = "";
                    if (response != null && response.statusCode == 200) {
                        responseString = new String(response.data);
                    }
                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                }
            };
            queue.add(stringRequest);
            queue.getCache().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
