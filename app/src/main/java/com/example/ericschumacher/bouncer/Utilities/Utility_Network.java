package com.example.ericschumacher.bouncer.Utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Selection;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Additive;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Choice;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_Input;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Int;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_JSON;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Object_Choice;
import com.example.ericschumacher.bouncer.Objects.Object_Choice_Charger;
import com.example.ericschumacher.bouncer.Objects.Object_Choice_Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Object_SearchResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Additive;
import com.example.ericschumacher.bouncer.Objects.Additive.Battery;
import com.example.ericschumacher.bouncer.Objects.Additive.Charger;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Additive.Variation_Color;
import com.example.ericschumacher.bouncer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        Log.i("Checking", "Yes");
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response: ", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS)) {
                            device.setId(jsonObject.getInt(Constants_Extern.ID_MODEL));
                            device.setName(jsonObject.getString(Constants_Extern.NAME_MODEL));
                            if (jsonObject.getInt(Constants_Extern.ID_BATTERY) != Constants_Intern.ID_UNKNOWN) {
                                device.setBattery(new Battery(jsonObject.getInt(Constants_Extern.ID_BATTERY), jsonObject.getString(Constants_Extern.NAME_BATTERY)));
                            }
                            if (jsonObject.getInt(Constants_Extern.ID_CHARGER) != Constants_Intern.ID_UNKNOWN) {
                                device.setCharger(new Charger(jsonObject.getInt(Constants_Extern.ID_CHARGER), jsonObject.getString(Constants_Extern.NAME_CHARGER)));
                            }
                            if (jsonObject.getInt(Constants_Extern.ID_MANUFACTURER) != Constants_Intern.ID_UNKNOWN) {
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
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/lku/" + Integer.toString(o.getId());
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
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/lku/connect/" + Integer.toString(o.getId());
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
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/model/check/exploitation/" + Integer.toString(o.getId());
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
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/model/exploitation/reuse/" + Integer.toString(o.getId());
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
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/model/exploitation/recycling/" + Integer.toString(o.getId());
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
                            device.setId(jsonObject.getInt(Constants_Extern.ID_MODEL));
                            device.setName(jsonObject.getString(Constants_Extern.NAME_MODEL));
                            if (jsonObject.getInt(Constants_Extern.ID_BATTERY) != Constants_Intern.ID_UNKNOWN) {
                                device.setBattery(new Battery(jsonObject.getInt(Constants_Extern.ID_BATTERY), jsonObject.getString(Constants_Extern.NAME_BATTERY)));
                            }
                            if (jsonObject.getInt(Constants_Extern.ID_CHARGER) != Constants_Intern.ID_UNKNOWN) {
                                device.setCharger(new Charger(jsonObject.getInt(Constants_Extern.ID_CHARGER), jsonObject.getString(Constants_Extern.NAME_CHARGER)));
                            }
                            if (jsonObject.getInt(Constants_Extern.ID_MANUFACTURER) != Constants_Intern.ID_UNKNOWN) {
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

    public void addModel(final Device device, final Interface_VolleyCallback_Int iCallback) {
        if (device.testMode()) {
            device.setId(0);
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
    public void addBattery(Device o, final Interface_VolleyCallback_Int iCallback) {
        if (o.testMode()) {
            iCallback.onSuccess(0);
        } else {
            final String url = "http://svp-server.com/svp-gmbh/erp/bouncer/src/api.php/battery/add/" + o.getBattery().getName() + "/" + Integer.toString(o.getManufacturer().getId());
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
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/battery/" + Integer.toString(o.getId());
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
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/battery/connect/" + Integer.toString(o.getBattery().getId()) + "/" + Integer.toString(o.getId());
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
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/battery/id/" + Integer.toString(o.getId()) + "/" + name;
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

    public void getMatchingBatteries(Device o, String namePart, final Interface_VolleyCallback_ArrayList_Input iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/battery/all/" + Integer.toString(o.getId()) + "/" + namePart;
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
    public void getCharger(Device o, final Interface_VolleyCallback_JSON iCallback) {
        if (o.testMode()) {
            JSONObject json = new JSONObject();
            try {
                json.put(Constants_Extern.ID_CHARGER, 0);
                json.put(Constants_Extern.NAME_CHARGER, "test");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            iCallback.onSuccess(json);
        } else {
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/charger/" + Integer.toString(o.getId());
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
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/charger/connect/" + Integer.toString(o.getId()) + "/" + Integer.toString(o.getCharger().getId());
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

    public void getChargers(Device o, final Interface_VolleyCallback_ArrayList_Choice iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/charger/all/" + Integer.toString(o.getId());
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response, Charger: ", response);
                    try {
                        ArrayList<Object_Choice> chargers = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.i("Looping", "Array");
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            chargers.add(new Object_Choice_Charger(jsonObject.getInt(Constants_Extern.ID), jsonObject.getString(Constants_Extern.NAME)));
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
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/model/add/manufacturer/" + Integer.toString(o.getId()) + "/" + Integer.toString(o.getManufacturer().getId());
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


    public ArrayList<Object_Choice> getManufactures(final Interface_VolleyCallback_ArrayList_Choice iCallback) {
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/manufacturers";
        try {
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response_Manus: ", response);
                    try {
                        ArrayList<Object_Choice> manufacturers = new ArrayList<>();
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            Log.i("Looping", "Array");
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            manufacturers.add(new Object_Choice_Manufacturer(jsonObject.getInt(Constants_Extern.ID), jsonObject.getString(Constants_Extern.NAME)));
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
        final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/modelColor/all/" + Integer.toString(o.getId());
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
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/modelColor/" + Integer.toString(o.getId()) + "/" + Integer.toString(o.getVariationColor().getIdColor());
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
            final String url = "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/modelColor/" + Integer.toString(o.getId()) + "/" + Integer.toString(o.getVariationColor().getIdColor());
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

    /*
    public void inventoryDevice(Device o, final Interface_VolleyCallback iCallback) {
        RequestQueue queue;
        queue = Volley.newRequestQueue(Context);
        final String url = "http://www.svp-server.com/svp-gmbh/dagobert/src/routes/api.php/device";
        final JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put(Constants_Extern.IMEI_UNKNOWN, o.getIMEI());
            jsonBody.put(Constants_Extern.ID_MODEL_COLOR, o.getIdModelColor());
            jsonBody.put(, etType.getText().toString());
            jsonBody.put("detail_one", etDetailOne.getText().toString());
            jsonBody.put("detail_two", etDetailTwo.getText().toString());
            jsonBody.put("id_svp_subaccount", mSubaccountId);
            jsonBody.put("ust_value", tvValueAddedTax.getText().toString());
            final String requestBody = jsonBody.toString();
            StringRequest stringRequest = new StringRequest(Request.Method.PUT, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Log.i("Response - Model-Add", response);
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        if (jsonObject.getString(Constants_Network.RESPONSE).equals(Constants_Network.SUCCESS) && jsonObject.getString(Constants_Network.DETAILS).equals(Constants_Network.MODEL_ADDED)) {
                            Toast.makeText(mContext, mContext.getString(R.string.model_added), Toast.LENGTH_SHORT).show();
                        }
                        if (!jsonObject.getString(Constants_Network.RESPONSE).equals(Constants_Network.SUCCESS) && jsonObject.getString(Constants_Network.DETAILS).equals(Constants_Network.MODEL_EXISTS)) {
                            Toast.makeText(getActivity(), getString(R.string.model_exists), Toast.LENGTH_SHORT).show();
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
    */
}
