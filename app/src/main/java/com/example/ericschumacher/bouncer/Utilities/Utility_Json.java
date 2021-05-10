package com.example.ericschumacher.bouncer.Utilities;

import android.util.Log;

import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Objects.Collection.Record;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Utility_Json {

    public JSONObject transformRecordToJson(Record record) {
        return new JSONObject();
    }

    public static Integer indexOf(JSONArray aJson, JSONObject oJson) {
        for (int i = 0; i < aJson.length(); i++) {
            try {
                if (aJson.getJSONObject(i).getInt(Constants_Extern.ID) == oJson.getInt(Constants_Extern.ID)) {
                    Log.i("Treffer", oJson.getInt(Constants_Extern.ID)+"");
                    return i;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
