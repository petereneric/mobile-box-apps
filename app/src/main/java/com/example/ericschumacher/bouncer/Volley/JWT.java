package com.example.ericschumacher.bouncer.Volley;

import android.util.Base64;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class JWT {

    JSONObject jsonToken;

    public JWT(String jwtEncoded) {
        try {
            String[] split = jwtEncoded.split("\\.");
            jsonToken = new JSONObject(getJson(split[1]));

            Log.d("JWT_DECODED", "Header: " + getJson(split[0]));
            Log.d("JWT_DECODED", "Body: " + getJson(split[1]));
        } catch (JSONException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static void decoded(String JWTEncoded) throws Exception {

    }

    private static String getJson(String strEncoded) throws UnsupportedEncodingException{
        byte[] decodedBytes = Base64.decode(strEncoded, Base64.URL_SAFE);
        return new String(decodedBytes, "UTF-8");
    }

    public int getkUser() {
        try {
            return jsonToken.getInt("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public boolean isAdmin() {
        try {
            return jsonToken.getInt("bAdmin") == 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }
}
