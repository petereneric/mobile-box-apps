package com.example.ericschumacher.bouncer.Volley;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Volley_Connection {

    private Context Context;

    public Volley_Connection(Context context) {
        Context = context;
    }

    public void getResponseWithJSON(int method, String url, JSONObject jsonObject) {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(method, url, jsonObject, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.i("RESS", response.toString());
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                // TODO: Handle error

            }
        });
        jsonObjectRequest.setRetryPolicy(new DefaultRetryPolicy(200000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Singleton_Request.getInstance(Context).addToRequestQueue(jsonObjectRequest);
    }

    public void getResponseWithString(final int method, final String url, final JSONObject jsonValue, final Interface_VolleyResult iCallback) {

        final String test = "test";

        StringRequest strreq = new StringRequest(method, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String Response) {
                Log.i("RESSSPONSE", Response);
                JSONObject oJson = null;
                try {
                    oJson = new JSONObject(Response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("jetzt aber", Response);
                if (oJson != null) {
                    try {
                        iCallback.onResult(oJson);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i("ERROR", Response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                Toast.makeText(Context, e + "error", Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            public int getMethod() {
                return method;
            }

            // set headers
            @Override
            public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization: Basic", "QUESTIONMARK");
                //params.put("api-version", "1");
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {

                String requestBody = null;
                Log.i("tessst", test);
                try {
                    if (jsonValue != null) {
                        requestBody = jsonValue.toString();
                    }
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", jsonValue, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null && response.statusCode == 200) {
                    largeLog("DerBoxx", new String(response.data));
                    Log.i("DerBoss", new String(response.data));
                    responseString = new String(response.data);
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        strreq.setRetryPolicy(new DefaultRetryPolicy(200000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Singleton_Request.getInstance(Context).addToRequestQueue(strreq);
    }

    public void getResponse(int method, final String url, final JSONObject jsonValue, final Interface_VolleyResult iCallback) {

        final String fake = "test";
        if (jsonValue != null) Log.i("Hier", jsonValue.toString());

        JSONObject jsonObject = null;
        try {
            if (jsonValue != null) jsonObject = new JSONObject(jsonValue.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JSONObject finalJsonObject = jsonObject;
        StringRequest strreq = new StringRequest(method, url, new Response.Listener<String>() {



            @Override
            public void onResponse(String Response) {
                JSONObject oJson = null;
                try {
                    oJson = new JSONObject(Response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("jetzt aber", Response);
                if (oJson != null) {
                    try {
                        iCallback.onResult(oJson);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
                    Log.i("ERROR", Response);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                Toast.makeText(Context, e + "error", Toast.LENGTH_LONG).show();
            }
        }) {
            // set headers
            @Override
            public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization: Basic", "QUESTIONMARK");
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String requestBody = null;
                try {
                    if (finalJsonObject != null) {
                        Log.i("Hier2", finalJsonObject.toString());
                        requestBody = finalJsonObject.toString();
                    }
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", finalJsonObject, "utf-8");
                    return null;
                }
            }

            @Override
            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                String responseString = "";
                if (response != null && response.statusCode == 200) {
                    largeLog("DerBoxx", new String(response.data));
                    Log.i("DerBoss", new String(response.data));
                    responseString = new String(response.data);
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        strreq.setRetryPolicy(new DefaultRetryPolicy(200000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Singleton_Request.getInstance(Context).addToRequestQueue(strreq);
    }

    public void execute(int method, final String url, final JSONObject jsonValue) {

        StringRequest strreq = new StringRequest(method, url, new Response.Listener<String>() {

            @Override
            public void onResponse(String Response) {
                Log.i("Response", url + Response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                Toast.makeText(Context, e + "error", Toast.LENGTH_LONG).show();
            }
        }) {
            // set headers
            @Override
            public Map<String, String> getHeaders() throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization: Basic", "QUESTIONMARK");
                return params;
            }

            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }

            @Override
            public byte[] getBody() throws AuthFailureError {
                String requestBody = null;
                try {
                    if (jsonValue != null) {
                        requestBody = jsonValue.toString();
                    }
                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", jsonValue, "utf-8");
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
        strreq.setRetryPolicy(new DefaultRetryPolicy(100000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Singleton_Request.getInstance(Context).addToRequestQueue(strreq);
    }

    public void uploadImage(String url, Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        final String imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {
                if (s.equals("true")) {
                    Toast.makeText(Context, "Uploaded Successful", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Context, "Some error occurred!", Toast.LENGTH_LONG).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Toast.makeText(Context, "Some error occurred -> " + volleyError, Toast.LENGTH_LONG).show();
                ;
            }
        }) {
            //adding parameters to send
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("image", imageString);
                return parameters;
            }
        };

        Singleton_Request.getInstance(Context).addToRequestQueue(request);
    }

    public static void largeLog(String tag, String content) {
        if (content.length() > 4000) {
            Log.d(tag, content.substring(0, 4000));
            largeLog(tag, content.substring(4000));
        } else {
            Log.d(tag, content);
        }
    }

    public static boolean successfulResponse(JSONObject oJson) {
        try {
            return (oJson.getString(Constants_Extern.RESULT).equals(Constants_Extern.SUCCESS));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
