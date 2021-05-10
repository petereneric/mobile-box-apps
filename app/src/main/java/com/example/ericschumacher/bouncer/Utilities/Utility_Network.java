package com.example.ericschumacher.bouncer.Utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
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
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Additive;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_BillPayee;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Charger;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Devices;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_IdModelColorShape;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Input;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_ModelColorShapeIds;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Records;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Int;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_JSON;
import com.example.ericschumacher.bouncer.Objects.Additive.Additive;
import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Additive.Shape;
import com.example.ericschumacher.bouncer.Objects.Additive.Station;
import com.example.ericschumacher.bouncer.Objects.Collection.BillPayee;
import com.example.ericschumacher.bouncer.Objects.Collection.Record;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.Objects.Object_Choice;
import com.example.ericschumacher.bouncer.Objects.Object_Id_Model_Color_Shape;
import com.example.ericschumacher.bouncer.Objects.Object_SearchResult;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 19.05.2018.
 */

public class Utility_Network {

    // Variables
    Context Context;
    RequestQueue queue;


    public Utility_Network(Context context) {
        Context = context;
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

    public void getMatchingManufacturers(String namePart, final Interface_VolleyCallback_ArrayList_Input iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/manufacturer/all/" + namePart;
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

    public void assignLku(Device o, final Interface_VolleyCallback_Int iCallback) {
        if (o.testMode()) {
            iCallback.onSuccess(0);
        } else {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/lku/assign/" + Integer.toString(o.getIdDevice());
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response, assignLKU: ", response);
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

    public void bookDeviceOutOfLKUStock(Device o, final Interface_VolleyCallback iCallback) {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/lku/bookout/" + Integer.toString(o.getIdDevice());
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



    public void updateRpd(final Interface_VolleyCallback_ArrayList_IdModelColorShape iCallback) {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/rpd";
            Log.i("urlCheck", url);
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response: ", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                                ArrayList<Object_Id_Model_Color_Shape> list = new ArrayList<>();
                                JSONArray jsonArray = jsonObject.getJSONArray(Constants_Extern.IDS_NOT_CONNECTED);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject model = jsonArray.getJSONObject(i);
                                    list.add(new Object_Id_Model_Color_Shape(model.getInt(Constants_Extern.ID_MODEL_COLOR_SHAPE), model.getInt(Constants_Extern.ID_COLOR), model.getInt(Constants_Extern.ID_SHAPE),model.getString(Constants_Extern.NAME)));
                                }
                                iCallback.onSuccess(list);
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
                stringRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                queue.add(stringRequest);
                queue.getCache().clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

    public void exploitReuse(Device o) {
        if (o.testMode()) {

        } else {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/model/exploitation/reuse/" + Integer.toString(o.getoModel().getkModel());
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
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/model/exploitation/recycling/" + Integer.toString(o.getoModel().getkModel());
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

    public void getDeviceByIMEI(final String IMEI, final Interface_VolleyCallback_JSON iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/device/imei" + IMEI;
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        iCallback.onResponse(jsonObject);
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

    public void getDeviceById(final int id, final Interface_VolleyCallback_JSON iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/device/id/" + id;
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        iCallback.onResponse(jsonObject);
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


    public void getIdModel_Name(final Device device, final Interface_VolleyCallback iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/model/id/name/" + device.getoModel().getName() + "/" + device.getTAC();
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                            device.getoModel().setkModel(jsonObject.getInt(Constants_Extern.ID_MODEL));
                            device.getoModel().setName(jsonObject.getString(Constants_Extern.NAME_MODEL));
                            if (!jsonObject.isNull(Constants_Extern.ID_BATTERY)) {
                                //device.getoModel().setoBattery(new Battery(jsonObject.getInt(Constants_Extern.ID_BATTERY), jsonObject.getString(Constants_Extern.NAME_BATTERY), 2));
                            }
                            if (!jsonObject.isNull(Constants_Extern.ID_CHARGER)) {
                                device.getoModel().setoCharger(new Charger(jsonObject.getInt(Constants_Extern.ID_CHARGER), jsonObject.getString(Constants_Extern.NAME_CHARGER)));
                            }
                            if (!jsonObject.isNull(Constants_Extern.ID_MANUFACTURER)) {
                                device.getoModel().setoManufacturer(new Manufacturer(jsonObject.getInt(Constants_Extern.ID_MANUFACTURER), jsonObject.getString(Constants_Extern.NAME_MANUFACTURER)));
                            }
                            if (!jsonObject.isNull(Constants_Extern.EXPLOITATION)) {
                                device.getoModel().settDefaultExploitation(jsonObject.getInt(Constants_Extern.EXPLOITATION));
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
            device.getoModel().setkModel(0);
        } else {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/model/add/" + device.getoModel().getName() + "/" + device.getTAC();
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
            final String url = "http://svp-server.com/svp-gmbh/erp/bouncer/src/api.php/battery/add/" + name + "/" + Integer.toString(o.getoModel().getoManufacturer().getId());
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
            iCallback.onResponse(json);
        } else {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/battery/" + Integer.toString(o.getoModel().getkModel());
            try {
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response: ", response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                                iCallback.onResponse(jsonObject);
                            } else {
                                //iCallback.onResponse(jsonObject);
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
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/battery/connect/" + Integer.toString(o.getoModel().getoBattery().getId()) + "/" + Integer.toString(o.getoModel().getkModel());
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
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/battery/id/" + Integer.toString(o.getoModel().getkModel()) + "/" + name;
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
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/battery/all/" + Integer.toString(model.getkModel()) + "/" + namePart;
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

    // Color
    public void addColor(Color color, final Interface_VolleyCallback_Int iCallback) {
        RequestQueue queue;
        queue = Volley.newRequestQueue(Context);
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/color/add";
        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put(Constants_Extern.NAME_COLOR, color.getName());
            jsonBody.put(Constants_Extern.HEX_CODE, color.getHexCode());
            jsonBody.put(Constants_Extern.ID_MANUFACTURER, (color.getManufacturer() == null) ? null : color.getManufacturer().getId());
            jsonBody.put(Constants_Extern.ID_MODEL, (color.getModel() == null) ? null : color.getModel().getkModel());
            final String requestBody = jsonBody.toString();
            Log.i("Show me", requestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                            iCallback.onSuccess(jsonObject.getInt(Constants_Extern.ID_COLOR));
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

    public void updateColor(Color color, final Interface_VolleyCallback iCallback) {
        RequestQueue queue;
        queue = Volley.newRequestQueue(Context);
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/color/add";
        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put(Constants_Extern.ID_COLOR, color.getId());
            jsonBody.put(Constants_Extern.NAME_COLOR, color.getName());
            jsonBody.put(Constants_Extern.HEX_CODE, color.getHexCode());
            jsonBody.put(Constants_Extern.ID_MANUFACTURER, color.getManufacturer().getId());
            jsonBody.put(Constants_Extern.ID_MODEL, color.getModel().getkModel());
            final String requestBody = jsonBody.toString();
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

    public void deleteColor(Color color, final Interface_VolleyCallback iCallback) {
        RequestQueue queue;
        queue = Volley.newRequestQueue(Context);
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/color/delete";
        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put(Constants_Extern.ID_COLOR, color.getId());
            final String requestBody = jsonBody.toString();
            Log.i("Show me", requestBody);
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

    // Charger


    public void connectChargerWithModel(Device o) {
        if (o.testMode()) {

        } else {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/charger/connect/" + Integer.toString(o.getoModel().getkModel()) + "/" + Integer.toString(o.getoModel().getoCharger().getId());
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
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/charger/all/" + Integer.toString(model.getkModel());
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

    public void getChargers(final Interface_VolleyCallback_ArrayList_Charger iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/charger/all";
        Log.i("JOOOOOOOO", "jo");
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response, Charger: ", response);
                    try {
                        ArrayList<Charger> chargers = new ArrayList<>();
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
    public void getoManufacturer(final Device device, final Interface_VolleyCallback iCallback) {
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
                                device.setoManufacturer(new Manufacturer(jsonObject.getInt(Constants_Extern.ID_MANUFACTURER), jsonObject.getString(Constants_Extern.NAME_MANUFACTURER)));
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
                queue.getCache().clearSearch();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    */


    public void addManufacturerToModel(Device o) {
        if (!o.testMode()) {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/model/add/manufacturer/" + Integer.toString(o.getoModel().getkModel()) + "/" + Integer.toString(o.getoModel().getoManufacturer().getId());
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
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/modelColor/all/" + Integer.toString(o.getoModel().getkModel());
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
                            list.add(new Color(jsonObject.getInt(Constants_Extern.ID_COLOR), jsonObject.getString(Constants_Extern.NAME_COLOR), jsonObject.getString(Constants_Extern.HEX_CODE)));
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
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/modelColor/" + Integer.toString(o.getoModel().getkModel()) + "/" + Integer.toString(o.getoColor().getId());
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
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/modelColor/" + Integer.toString(o.getoModel().getkModel()) + "/" + Integer.toString(o.getoColor().getId());
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

    public void addDevice(Device o, Record oRecord, final Interface_VolleyCallback_Int iCallback) {
        RequestQueue queue;
        queue = Volley.newRequestQueue(Context);
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/device/add";
        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put(Constants_Extern.IMEI, o.getIMEI());
            jsonBody.put(Constants_Extern.ID_MODEL, o.getoModel().getkModel());
            jsonBody.put(Constants_Extern.ID_COLOR, o.getoColor().getId());
            jsonBody.put(Constants_Extern.ID_SHAPE, o.getoShape().getId());
            jsonBody.put(Constants_Extern.ID_STATION, o.getoStation().getId());
            jsonBody.put(Constants_Extern.ID_RECORD, oRecord.getId());
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
            jsonBody.put(Constants_Extern.ID_DEVICE, o.getIdDevice());
            jsonBody.put(Constants_Extern.ID_MODEL, o.getoModel().getkModel());
            jsonBody.put(Constants_Extern.ID_COLOR, o.getoColor().getId());
            jsonBody.put(Constants_Extern.ID_SHAPE, o.getoShape().getId());
            jsonBody.put(Constants_Extern.ID_STATION, o.getoStation().getId());
            final String requestBody = jsonBody.toString();
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

    public void getDevicesForJuicer(ArrayList<Charger> chargerNotSeledted, final Interface_VolleyCallback_ArrayList_ModelColorShapeIds iCallback) {


        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/ids/juicer";
        try {
            final JSONObject jsonBody = new JSONObject();
            for (int i = 0; i<chargerNotSeledted.size(); i++) {
                Charger charger = chargerNotSeledted.get(i);
                jsonBody.put(Integer.toString(i), charger.getId());
            }
            final String requestBody = jsonBody.toString();
            Log.i("Request Juicer: ", requestBody);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response Juicer: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                            JSONArray jsonArray = jsonObject.getJSONArray(Constants_Extern.IDS);
                            ArrayList<Integer> modelColorShapeIds = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.i("Looping", "Array");
                                JSONObject json = jsonArray.getJSONObject(i);
                                modelColorShapeIds.add(json.getInt(Constants_Extern.ID));
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
                    } finally {

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
            stringRequest.setRetryPolicy(new DefaultRetryPolicy( 50000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            queue.add(stringRequest);
            queue.getCache().clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getDevicesByIdModelColorShape(int idModelColorShape, int idStation, final Interface_VolleyCallback_ArrayList_Devices iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/devices/" + Integer.toString(idModelColorShape);
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response Devices: ", response);
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
        Device device = new Device(Context);
        try {
            if (!json.isNull(Constants_Extern.ID_MODEL))
                device.getoModel().setkModel(json.getInt(Constants_Extern.ID_MODEL));
            if (!json.isNull(Constants_Extern.NAME_MODEL))
                device.getoModel().setName(json.getString(Constants_Extern.NAME_MODEL));
            if (!json.isNull(Constants_Extern.EXPLOITATION))
                device.getoModel().settDefaultExploitation(json.getInt(Constants_Extern.EXPLOITATION));
            if (!json.isNull(Constants_Extern.ID_MANUFACTURER) && !json.isNull(Constants_Extern.NAME_MANUFACTURER))
                device.getoModel().setoManufacturer(new Manufacturer(json.getInt(Constants_Extern.ID_MANUFACTURER), json.getString(Constants_Extern.NAME_MANUFACTURER)));
            if (!json.isNull(Constants_Extern.ID_BATTERY) && !json.isNull(Constants_Extern.NAME_BATTERY))
                //device.getoModel().setoBattery(new Battery(json.getInt(Constants_Extern.ID_BATTERY), json.getString(Constants_Extern.NAME_BATTERY), 0));
            if (!json.isNull(Constants_Extern.ID_CHARGER) && !json.isNull(Constants_Extern.NAME_CHARGER))
                device.getoModel().setoCharger(new Charger(json.getInt(Constants_Extern.ID_CHARGER), json.getString(Constants_Extern.NAME_CHARGER)));
            if (!json.isNull(Constants_Extern.ID_DEVICE))
                device.setIdDevice(json.getInt(Constants_Extern.ID_DEVICE));
            if (!json.isNull(Constants_Extern.IMEI))
                device.setIMEI(json.getString(Constants_Extern.IMEI));
            if (!json.isNull(Constants_Extern.CONDITION))
                device.setCondition(json.getInt(Constants_Extern.CONDITION));
            if (!json.isNull(Constants_Extern.ID_STATION))
                device.setoStation(new Station(json.getInt(Constants_Extern.ID_STATION)));
            if (!json.isNull(Constants_Extern.ID_COLOR) && !json.isNull(Constants_Extern.NAME_COLOR) && !json.isNull(Constants_Extern.HEX_CODE))
                device.setoColor
                        (new Color(json.getInt(Constants_Extern.ID_COLOR), json.getString(Constants_Extern.NAME_COLOR), json.getString(Constants_Extern.HEX_CODE)));
            if (!json.isNull(Constants_Extern.ID_SHAPE) && !json.isNull(Constants_Extern.NAME_SHAPE))
                device.setoShape(new Shape(json.getInt(Constants_Extern.ID_SHAPE), json.getString(Constants_Extern.NAME_SHAPE)));
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
            if (device.getCondition() != 0) json.put(Constants_Extern.CONDITION, device.getCondition());
            if (device.getoStation() != null && device.getoStation().getId() != 0) json.put(Constants_Extern.ID_STATION, device.getoStation().getId());
            if (device.getoColor() != null && device.getoColor().getId() != 0) json.put(Constants_Extern.ID_COLOR, device.getoColor().getId());
            if (device.getoShape() != null && device.getoShape().getId() != 0) json.put(Constants_Extern.ID_SHAPE, device.getoShape().getId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    // Collection

    public void getCollectorsByName(String namePart, final Interface_VolleyCallback_ArrayList_Input iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/collectors/" + namePart;
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response Devices: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                            JSONArray jsonArray = jsonObject.getJSONArray(Constants_Extern.COLLECTORS);
                            ArrayList<Object_SearchResult> records = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.i("Looping", "Array");
                                JSONObject json = jsonArray.getJSONObject(i);
                                records.add(new Object_SearchResult(json.getInt(Constants_Extern.ID), json.getString(Constants_Extern.NAME)));
                            }
                            iCallback.onSuccess(records);
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

    public void getUnselectedRecords(final Interface_VolleyCallback_ArrayList_Records iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/records/unselected";
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response Devices: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                            JSONArray jsonArray = jsonObject.getJSONArray(Constants_Extern.RECORDS);
                            ArrayList<Record> records = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.i("Looping", "Array");
                                JSONObject json = jsonArray.getJSONObject(i);
                                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                Log.i("DAATE", json.getString(Constants_Extern.LAST_UPDATE));
                                //records.add(new Record(json.getInt(Constants_Extern.ID), Utility_DateTime.stringToDateTime(json.getString(Constants_Extern.LAST_UPDATE)), json.getInt(Constants_Extern.COUNT_RECYCLING), json.getInt(Constants_Extern.COUNT_REUSE), json.getInt(Constants_Extern.DEVICES), json.getString(Constants_Extern.NAME)));
                            }
                            iCallback.onSuccess(records);
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

    public void createCollector(Device device, final Interface_VolleyCallback iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/device/" + Integer.toString(device.getIdDevice());
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
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

    public void setBillPayeePaid(int idBill, final Interface_VolleyCallback iCallback) {
        RequestQueue queue;
        queue = Volley.newRequestQueue(Context);
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/bill/payee/paid/"+Integer.toString(idBill);
        final JSONObject jsonBody = new JSONObject();
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
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

    public void createRecord(int idCollector, final Interface_VolleyCallback_Int iCallback) {
        RequestQueue queue;
        queue = Volley.newRequestQueue(Context);
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/record/create/"+Integer.toString(idCollector);
        final JSONObject jsonBody = new JSONObject();
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                            iCallback.onSuccess(jsonObject.getInt(Constants_Extern.ID_RECORD));
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

    public void recordReuse(int idRecord, final Interface_VolleyCallback iCallback) {
        RequestQueue queue;
        queue = Volley.newRequestQueue(Context);
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/record/reuse/"+Integer.toString(idRecord);
        final JSONObject jsonBody = new JSONObject();
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
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

    public void recordRecycling(int idRecord, final Interface_VolleyCallback iCallback) {
        RequestQueue queue;
        queue = Volley.newRequestQueue(Context);
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/record/recycling/"+Integer.toString(idRecord);
        final JSONObject jsonBody = new JSONObject();
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
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

    public void recordSelected(int idRecord, final Interface_VolleyCallback iCallback) {
        RequestQueue queue;
        queue = Volley.newRequestQueue(Context);
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/record/selected/"+Integer.toString(idRecord);
        final JSONObject jsonBody = new JSONObject();
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
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



    public void getUnpaidBillsPayee(final Interface_VolleyCallback_ArrayList_BillPayee iCallback) {
        final String url = Urls.URL_REST_API+"bill/payee/unpaid";
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response Devices: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                            JSONArray jsonArray = jsonObject.getJSONArray(Constants_Extern.BILLS);
                            ArrayList<BillPayee> billPayees = new ArrayList<>();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                Log.i("Looping", "Array");
                                JSONObject json = jsonArray.getJSONObject(i);
                                billPayees.add(new BillPayee(json.getInt(Constants_Extern.ID), Utility_DateTime.stringToDateTime(json.getString(Constants_Extern.DATE_CREATION)), json.getString(Constants_Extern.NAME_PAYEE), json.getString(Constants_Extern.ACCOUNTHOLDER), json.getString(Constants_Extern.IBAN), json.getDouble(Constants_Extern.PAYMENT)));
                            }
                            iCallback.onSuccess(billPayees);
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
