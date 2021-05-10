package com.example.ericschumacher.bouncer.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class SqlCommands {

    public static final String WHERE_CLAUSE = "WHERE_CLAUSE";
    public static final String WHERE_CLAUSE_CONSTANT = "WHERE_CLAUSE_CONSTANT";
    public static final String WHERE_CLAUSE_CONSTANT_MARKETING_UNSHIPPED_RECORDS = "WHERE_CLAUSE_CONSTANT_MARKETING_UNSHIPPED_RECORDS";
    public static final String WHERE_CLAUSE_CONSTANT_RECENT_ORDERS = "WHERE_CLAUSE_CONSTANT_RECENT_ORDERS";

    public static JSONObject getWhereClauseConstant(String cWhereClauseConstant) {
        JSONObject oJson = new JSONObject();
        try {
            oJson.put(WHERE_CLAUSE_CONSTANT, cWhereClauseConstant);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oJson;
    }
}
