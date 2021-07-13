package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

public class ModelCheck implements Serializable {

    // Connection
    Context Context;
    Volley_Connection cVolley;

    // Data
    int id;
    int kModel;
    Check oCheck;
    String cDescription;
    int nCount;
    int nPosition;
    boolean bPositionFixed;

    public ModelCheck(JSONObject json, Context context) {
        Context = context;
        cVolley = new Volley_Connection(Context);

        try {
            id = json.getInt("id");
            kModel = json.getInt("kModel");
            cDescription = json.getString("cDescription");
            nCount = json.getInt("nCount");
            nPosition = json.getInt("nPosition");
            bPositionFixed = json.getInt("bPositionFixed") == 1;
            oCheck = new Check(Context, json.getJSONObject("oCheck"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONObject getJson() {
        JSONObject oJson = new JSONObject();
        try {
            oJson.put("id", id);
            oJson.put("cDescription", cDescription);
            oJson.put("bPositionFixed", bPositionFixed ? 1 : 0);
            oJson.put("nCount", nCount);
            oJson.put("nPosition", nPosition);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return oJson;
    }

    public int getId() {
        return id;
    }

    public String getcDescription() {
        return cDescription;
    }

    public void setcDescription(String cDescription) {
        this.cDescription = cDescription;
    }

    public int getnCount() {
        return nCount;
    }

    public void setnCount(int nCount) {
        this.nCount = nCount;
    }

    public int getnPosition() {
        return nPosition;
    }

    public void setnPosition(int nPosition) {
        this.nPosition = nPosition;
    }

    public boolean isbPositionFixed() {
        return bPositionFixed;
    }

    public void setbPositionFixed(boolean bPositionFixed) {
        this.bPositionFixed = bPositionFixed;
    }

    public Check getoCheck() {
        return oCheck;
    }

    public void update() {
        cVolley.execute(Request.Method.POST, Urls.URL_UPDATE_MODEL_CHECK, getJson());
    }

    public void delete() {
        cVolley.execute(Request.Method.DELETE, Urls.URL_DELETE_MODEL_CHECK+id, null);
    }

    public static void updatePosition(ArrayList<ModelCheck> lModelChecks) {
        for (ModelCheck oModelCheck : lModelChecks) {
            // check if something has changed
            if (oModelCheck.getnPosition() != lModelChecks.indexOf(oModelCheck)) {
                oModelCheck.setnPosition(lModelChecks.indexOf(oModelCheck));
                oModelCheck.update();
            }
        }
    }

    public static void updatePositionLocally(ArrayList<ModelCheck> lModelChecks) {
        for (ModelCheck oModelCheck : lModelChecks) {
            // check if something has changed
            oModelCheck.setnPosition(lModelChecks.indexOf(oModelCheck));
        }
    }

    public static void sortByPosition(ArrayList<ModelCheck> lModelChecks) {
        Collections.sort(lModelChecks, (o1, o2) -> o1.getnPosition() - o2.getnPosition());
    }

    public static void sortByLogic(ArrayList<ModelCheck> lModelChecks) {
        for (ModelCheck o : lModelChecks) {
            Log.i("TEST", o.getoCheck().getcName() + " | " + o.getnPosition());
        }
        for (int i = 0; i < lModelChecks.size(); i++) {
            ModelCheck oModelCheck = lModelChecks.get(i);
            if (!oModelCheck.isbPositionFixed() && (lModelChecks.indexOf(oModelCheck)-1 < 0 || lModelChecks.get(lModelChecks.indexOf(oModelCheck)-1).isbPositionFixed())) {
                Log.i("Aktuell", oModelCheck.getoCheck().getcName());
                ArrayList<ModelCheck> lModelChecksPartial = new ArrayList<>();
                for (int i2 = lModelChecks.indexOf(oModelCheck); i2 < lModelChecks.size(); i2++) {
                    if (!lModelChecks.get(i2).isbPositionFixed()) {
                        lModelChecksPartial.add(lModelChecks.get(i2));
                    } else {
                        break;
                    }
                }

                for (ModelCheck o : lModelChecksPartial) {
                    Log.i("Partials", o.getoCheck().getcName() + " | " + o.getnPosition());
                }

                Collections.sort(lModelChecksPartial, (o1, o2) -> o2.getnCount() - o1.getnCount());

                for (int i3 = 0; i3 < lModelChecksPartial.size(); i3++) {
                    ModelCheck oModelCheckPartial = lModelChecksPartial.get(i3);
                    oModelCheckPartial.setnPosition(i+i3);
                }

            }
        }
        sortByPosition(lModelChecks); // needs to be done so that partial changes are respected
        //updatePosition(lModelChecks);
    }
}
