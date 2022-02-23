package com.example.ericschumacher.bouncer.Objects;

import android.view.View;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Utilities.Utility_DateTime;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class ModelWipe {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // VALUES & VARIABLES

    // Data
    private int id;
    private int kModel;
    private Wipe oWipe;
    private Wipe_Procedure oWipeProcedure;
    private String cDescription;
    private int nPosition;

    // Connection
    private Volley_Connection cVolley;


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR

    public ModelWipe (Volley_Connection cVolley, JSONObject oJson) {
        this.cVolley = cVolley;

        try {
            id = oJson.getInt("id");
            kModel = oJson.getInt("kModel");
            cDescription = oJson.getString("cDescription");
            nPosition = oJson.getInt("nPosition");

            oWipe = !oJson.isNull("oWipe") ? new Wipe(cVolley, oJson.getJSONObject("oWipe")) : null;
            oWipeProcedure = !oJson.isNull("oWipeProcedure") ? new Wipe_Procedure(cVolley, oJson.getJSONObject("oWipe")) : null;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getcDescription() {
        return cDescription;
    }

    public String getCompleteDescription() {
        String dWipe = getoWipeProcedure().getoWipe().getcDescription();
        String dWipeProcedure = getoWipeProcedure().getcDescription();
        String dModelWipe = getcDescription();
        if (!dModelWipe.equals("") || !dWipe.equals("") || !dWipeProcedure.equals("")) {
            return dWipe + (!dWipeProcedure.equals("") && !dWipe.equals("") ? ("|" + dWipeProcedure) : dWipeProcedure) + ((!dWipe.equals("") || !dWipeProcedure.equals("")) && !dModelWipe.equals("") ? "|" + dModelWipe : dModelWipe);
        } else {
            return null;
        }
    }

    public String get_cWipe() {
        if (oWipe != null) {
            return oWipe.getcName();
        }
        if (oWipeProcedure != null) {
            return oWipeProcedure.getoWipe().getcName();
        }
        return "";
    }

    public void setcDescription(String cDescription) {
        this.cDescription = cDescription;
    }

    public int getnPosition() {
        return nPosition;
    }

    public void setnPosition(int nPosition) {
        this.nPosition = nPosition;
    }

    public Wipe getoWipe() {
        return oWipe;
    }

    public Wipe_Procedure getoWipeProcedure() {
        return oWipeProcedure;
    }

    public static ArrayList<ModelWipe> sort(ArrayList<ModelWipe> modelWipes) {
        ArrayList<ModelWipe> wipes = new ArrayList<>();
        ArrayList<ModelWipe> wipeProcedures = new ArrayList<>();
        for (ModelWipe modelWipe : modelWipes) {
            if (modelWipe.getoWipe() != null) {
                wipes.add(modelWipe);
            }
            if (modelWipe.getoWipeProcedure() != null) {
                wipeProcedures.add(modelWipe);
            }
        }
        Collections.sort(wipes, (o1, o2) -> o1.getnPosition() - o2.getnPosition());
        Collections.sort(wipeProcedures, (o1, o2) -> o1.getnPosition() - o2.getnPosition());
        wipeProcedures.addAll(wipes);

        return wipeProcedures;
    }

    public static ArrayList<Wipe_Procedure> getWipeProcedures(ArrayList<ModelWipe> lModelWipes) {
        ArrayList<Wipe_Procedure> lWipeProcedures = new ArrayList<>();
        for (ModelWipe modelWipe : lModelWipes) {
            if (modelWipe.getoWipeProcedure() != null) lWipeProcedures.add(modelWipe.getoWipeProcedure());
        }
        return lWipeProcedures;
    }

    public static void updatePosition(ArrayList<ModelWipe> lModelWipes) {
        for (ModelWipe modelWipe : lModelWipes) {
            if (modelWipe.getoWipe() != null && modelWipe.getnPosition() != lModelWipes.indexOf(modelWipe)) {
                modelWipe.setnPosition(lModelWipes.indexOf(modelWipe));
                modelWipe.update();
            }
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // CRUD

    private void update() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            json.put("cDescription", cDescription);
            json.put("nPosition", nPosition);
            cVolley.execute(Request.Method.POST, Urls.URL_UPDATE_MODEL_WIPE, json);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
