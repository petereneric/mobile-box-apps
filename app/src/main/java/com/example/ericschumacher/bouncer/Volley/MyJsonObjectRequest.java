package com.example.ericschumacher.bouncer.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

/**
 * Created by Eric Schumacher on 19.05.2018.
 */

public class MyJsonObjectRequest extends JsonObjectRequest {

    public MyJsonObjectRequest(String url, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
    }

    @Override
    protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
        try {
            String jsonString = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
            // here's the new code, if jsonString.length() == 0 don't parse
            if (jsonString.length() == 0) {
                return Response.success(null, HttpHeaderParser.parseCacheHeaders(response));
            }
            // end of patch
            return Response.success(new JSONObject(jsonString), HttpHeaderParser.parseCacheHeaders(response));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException je) {
            return Response.error(new ParseError(je));
        }
    }
}