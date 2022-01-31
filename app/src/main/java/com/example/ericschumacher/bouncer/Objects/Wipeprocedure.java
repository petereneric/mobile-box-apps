package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Callback;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Wipeprocedure {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // VALUES & VARIABLES

    // Object
    int id;
    Manufacturer oManufacturer;
    String cName;
    ArrayList<Wipe_Procedure> lWipeProcedure = new ArrayList<>();

    // Context
    Context mContext;

    // Connection
    Volley_Connection cVolley;


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // CONSTRUCTOR

    public Wipeprocedure(Context context, JSONObject json) {
        mContext = context;
        cVolley = new Volley_Connection(mContext);

        try {
            id = json.getInt("id");
            oManufacturer = new Manufacturer(json.getJSONObject("oManufacturer"));
            cName = json.getString("cName");
            if (!json.isNull("lWipeProcedures")) {
                JSONArray aJson = json.getJSONArray("lWipeProcedures");
                for (int i = 0; i<aJson.length(); i++) {
                    Wipe_Procedure wipeProcedure = new Wipe_Procedure(mContext, aJson.getJSONObject(i));
                    lWipeProcedure.add(wipeProcedure);
                }
                Wipe_Procedure.sortByPosition(lWipeProcedure);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PRIVATE



    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // GETTER & SETTER

    public int getId() {
        return id;
    }

    public Manufacturer getoManufacturer() {
        return oManufacturer;
    }

    public void setoManufacturer(Manufacturer oManufacturer) {
        this.oManufacturer = oManufacturer;
        update();
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
        update();
    }

    public ArrayList<Wipe_Procedure> getlWipeProcedure() {
        return lWipeProcedure;
    }

    public void addWipeProcedure(int kWipe, Interface_Callback iCallback) {
        Wipe_Procedure.create(mContext, cVolley, kWipe, getId(), new Wipe_Procedure.Interface_Create() {
            @Override
            public void created(Wipe_Procedure wipeProcedure) {
                getlWipeProcedure().add(wipeProcedure);
                iCallback.callback();
            }
        });
    }

    public void unlinkWipeProcedure(int position) {
        Wipe_Procedure wipeProcedure = getlWipeProcedure().get(position);
        wipeProcedure.delete();
        getlWipeProcedure().remove(wipeProcedure);
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // CRUD

    public static void create(Context context, Volley_Connection cVolley, int kManufacturer, String cName, Interface_Create iCreate) {
        JSONObject json = new JSONObject();
        try {
            json.put("kManufacturer", kManufacturer);
            json.put("cName", cName);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cVolley.getResponse(Request.Method.PUT, Urls.URL_ADD_WIPEPROCEDURE, json, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                Wipeprocedure.read(context, cVolley, oJson.getInt("id"), new Interface_Read() {
                    @Override
                    public void read(Wipeprocedure wipeprocedure) {
                        iCreate.created(wipeprocedure);
                    }
                });
            }
        });
    }

    public static void read(Context context, Volley_Connection cVolley, int id, Interface_Read iRead) {
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_WIPEPROCEDURE + id, null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                iRead.read(new Wipeprocedure(context, oJson));
            }
        });
    }

    public static void readByName(Context context, Volley_Connection cVolley, String cName, Interface_Read_byName iRead) {
        JSONObject json = new JSONObject();
        try {
            json.put("cNamePart", cName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cVolley.getResponse(Request.Method.POST, Urls.URL_GET_WIPEPROCEDURES_BY_NAME, json, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                ArrayList<Wipeprocedure> lWipeprocedures = new ArrayList<>();
                JSONArray aJson = oJson.getJSONArray("lWipeprocedures");
                for (int i = 0; i<aJson.length(); i++) {
                    lWipeprocedures.add(new Wipeprocedure(context, aJson.getJSONObject(i)));
                }
                iRead.read(lWipeprocedures);
            }
        });
    }

    private void update() {
        JSONObject json = new JSONObject();
        try {
            json.put("id", id);
            json.put("kManufacturer", oManufacturer.getId());
            json.put("cName", cName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        cVolley.execute(Request.Method.POST, Urls.URL_UPDATE_WIPEPROCEDURE, json);
    }

    public void delete() {
        cVolley.execute(Request.Method.DELETE, Urls.URL_DELETE_WIPEPROCEDURE+id, null);
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // Interfaces

    public interface Interface_Create {
        void created(Wipeprocedure wipeprocedure);
    }

    public interface Interface_Read {
        void read(Wipeprocedure wipeprocedure);
    }

    public interface Interface_Read_byName {
        void read(ArrayList<Wipeprocedure> lWipeprocedure);
    }
}
