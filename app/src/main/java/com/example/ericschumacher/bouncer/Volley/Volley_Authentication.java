package com.example.ericschumacher.bouncer.Volley;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.StringRequest;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyAuthentication;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Status;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class Volley_Authentication {

    private Context Context;

    public Volley_Authentication(Context context) {
        Context = context;
    }

    public void login(String cCode, Interface_VolleyCallback_Status iStatus, Interface_VolleyAuthentication iAuthentication) {

        int statusCode;

        JSONObject json = new JSONObject();
        try {
            json.put("role", 10);
            json.put("email", "unknown");
            json.put("password", cCode);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        final JSONObject finalJsonObject = jsonObject;
        StringRequest strreq = new StringRequest(Request.Method.POST, Urls.URL_POST_LOGIN, new Response.Listener<String>() {


            @Override
            public void onResponse(String Response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError e) {
                e.printStackTrace();
                iStatus.returnStatus(401);
                Log.i("Habe dich", "ja");
                //Toast.makeText(Context, e + "error", Toast.LENGTH_LONG).show();
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
                Log.i("DerBoss", response.headers.get("authorization"));
                iAuthentication.returnToken(response.headers.get("authorization"));
                iStatus.returnStatus(response.statusCode);

                String responseString = "";
                if (response != null && response.statusCode == 200) {
                    largeLog("DerBoxx", new String(response.data));

                    responseString = new String(response.data);
                }
                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
            }
        };
        strreq.setRetryPolicy(new DefaultRetryPolicy(200000, 5, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Singleton_Request.getInstance(Context).addToRequestQueue(strreq);

    }

    public static void largeLog(String tag, String content) {
        if (content.length() > 4000) {
            //Log.d(tag, content.substring(0, 4000));
            //largeLog(tag, content.substring(4000));
        } else {
            Log.d(tag, content);
        }
    }
}
