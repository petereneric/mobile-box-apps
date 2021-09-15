package com.example.ericschumacher.bouncer.Objects;

import android.content.Context;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Model_New_New;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Utilities.Utility_DateTime;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

public class Diagnose implements Comparable<Diagnose> {

    // Data
    int id;
    int kDevice;
    int kUser;
    String cUser;
    Date dCreation;
    Date dLastUpdate;
    boolean bFinished;
    Boolean bPassed;
    ArrayList<DiagnoseCheck> lDiagnoseChecks = new ArrayList<>();

    // Environment
    Context eContext;

    // Connection
    Volley_Connection cVolley;

    public Diagnose(Context eContext, JSONObject oJson) {
        this.eContext = eContext;
        cVolley = new Volley_Connection(eContext);

        try {
            id = oJson.getInt("id");
            kDevice = oJson.getInt("kDevice");
            kUser = oJson.getInt("kUser");
            cUser = oJson.getString("cUser");
            dCreation = Utility_DateTime.stringToDateTime(oJson.getString("dCreation"));
            dLastUpdate = Utility_DateTime.stringToDateTime(oJson.getString("dLastUpdate"));
            bFinished = oJson.getInt("bFinished") == 1;
            bPassed = oJson.isNull("bPassed") ? null : oJson.getInt("bPassed") == 1;
            if (!oJson.isNull("lDiagnoseChecks")) {
                JSONArray aJson = oJson.getJSONArray("lDiagnoseChecks");
                for (int i = 0; i < aJson.length(); i++) {
                    JSONObject jsonDiagnoseCheck = aJson.getJSONObject(i);
                    DiagnoseCheck oDiagnoseCheck = new DiagnoseCheck(eContext, jsonDiagnoseCheck);
                    lDiagnoseChecks.add(oDiagnoseCheck);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addDiagnoseChecks(JSONArray jsonArray) {
        lDiagnoseChecks.clear();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                DiagnoseCheck diagnoseCheck = new DiagnoseCheck(eContext, jsonArray.getJSONObject(i));
                lDiagnoseChecks.add(diagnoseCheck);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteDiagnoseCheck(ArrayList<ModelCheck> lModelChecks, int kCheck) {
        for (DiagnoseCheck diagnoseCheck : lDiagnoseChecks) {
            if (diagnoseCheck.getkCheck() == kCheck) {
                if (diagnoseCheck.getId() != null) {
                    diagnoseCheck.delete();
                } else {
                    diagnoseCheck.deleteWithoutId(getId(), kCheck);
                }
                lDiagnoseChecks.remove(diagnoseCheck);
                break;
            }
        }
        for (ModelCheck modelCheck : lModelChecks) {
            if (modelCheck.getoCheck().getId() == kCheck) {
                // Lower count for amount of fails
                modelCheck.setnCount(modelCheck.getnCount() > 0 ? modelCheck.getnCount() - 1 : 0);
                break;
            }
        }
    }

    public void finishSuccessfully(ArrayList<ModelCheck> lModelChecks) {
        for (ModelCheck modelCheck : lModelChecks) {
            boolean bMatch = false;
            for (DiagnoseCheck diagnoseCheck : lDiagnoseChecks) {
                if (diagnoseCheck.getkCheck() == modelCheck.getoCheck().getId()) {
                    bMatch = true;
                }
            }
            if (!bMatch) {
                DiagnoseCheck diagnoseCheck = new DiagnoseCheck(eContext, id, modelCheck.getoCheck().getId(), 1);
                lDiagnoseChecks.add(diagnoseCheck);
            }
        }
    }

/*
    public void changeCheck(DiagnoseCheck diagnoseCheck) {
        diagnoseCheck.settStatus(diagnoseCheck.gettStatus() == 1 ? 2 : 1);
    }


    public void failedCheck(ArrayList<ModelCheck> modelChecks, int kCheck) {
        for (ModelCheck modelCheck : modelChecks) {
            if (modelCheck.getoCheck().getId() == kCheck) {
                boolean bDiagnoseCheck = false;
                for (DiagnoseCheck diagnoseCheck : lDiagnoseChecks) {
                    if (diagnoseCheck.getkCheck() == kCheck) {
                        diagnoseCheck.settStatus(2);
                        bDiagnoseCheck = true;
                    }
                }
                if (!bDiagnoseCheck) {
                    DiagnoseCheck diagnoseCheck = new DiagnoseCheck(eContext, id, kCheck, 2);
                    lDiagnoseChecks.add(diagnoseCheck);
                }
                break;
            } else {
                boolean bDiagnoseCheck = false;
                for (DiagnoseCheck diagnoseCheck : lDiagnoseChecks) {
                    if (diagnoseCheck.getkCheck() == modelCheck.getoCheck().getId()) {
                        bDiagnoseCheck = true;
                    }
                }
                if (!bDiagnoseCheck) {
                    DiagnoseCheck diagnoseCheck = new DiagnoseCheck(eContext, id, modelCheck.getoCheck().getId(), 1);
                    lDiagnoseChecks.add(diagnoseCheck);
                }
            }
        }
    }

 */

    public void click(ArrayList<ModelCheck> lModelChecks, int kCheck, boolean bFailed) {
        // Check if an existing DiagnoseCheck was clicked or not
        boolean bDiagnoseCheckExists = false;
        for (DiagnoseCheck diagnoseCheck : lDiagnoseChecks) {

            if (diagnoseCheck.getkCheck() == kCheck) {
                // An existing DiagnoseCheck was clicked
                // 1.1. Just change the status
                diagnoseCheck.settStatus(diagnoseCheck.gettStatus() == 1 ? 2 : 1);
                for (ModelCheck modelCheck : lModelChecks) {
                    if (modelCheck.getoCheck().getId() == kCheck) {
                        // Lower count for amount of fails
                        if (diagnoseCheck.gettStatus() == 1) {
                            modelCheck.setnCount(modelCheck.getnCount() > 0 ? modelCheck.getnCount() - 1 : 0);
                        }
                        // Rise count for amount of fails
                        if (diagnoseCheck.gettStatus() == 2) {
                            modelCheck.setnCount(modelCheck.getnCount() + 1);
                        }
                    }
                }

                bDiagnoseCheckExists = true;
                break;
            }
        }

        if (!bDiagnoseCheckExists) {
            // A not existing DiagnoseCheck was clicked
            // 2.1. Create a new failed DiagnoseCheck
            //DiagnoseCheck _diagnoseCheck = new DiagnoseCheck(eContext, id, kCheck, 2);
            DiagnoseCheck _diagnoseCheck = new DiagnoseCheck(eContext, id, kCheck, bFailed ? 2 : 1);
            lDiagnoseChecks.add(_diagnoseCheck);
            // 2.2. Set all not set ModelChecks to passed, new created DiagnoseChecks
            for (ModelCheck modelCheck : lModelChecks) {
                if (modelCheck.getoCheck().getId() == kCheck) {

                    if (bFailed) modelCheck.setnCount(modelCheck.getnCount() + 1);
                    break;
                } else {
                    // 3. Check if there is already a DiagnoseCheck
                    boolean bDiagnoseCheck = false;
                    for (DiagnoseCheck diagnoseCheck : lDiagnoseChecks) {
                        if (diagnoseCheck.getkCheck() == modelCheck.getoCheck().getId()) {
                            bDiagnoseCheck = true;
                        }
                    }
                    // 3.1. There is no DiagnoseCheck
                    if (!bDiagnoseCheck) {
                        DiagnoseCheck diagnoseCheck = new DiagnoseCheck(eContext, id, modelCheck.getoCheck().getId(), 1);
                        lDiagnoseChecks.add(diagnoseCheck);
                    }
                }
            }
        }
    }

    // function for checking if passed and failed and finished, can run after every update
    public void updateState(Model model, ArrayList<ModelCheck> lModelChecks) {
        Boolean passed = true;
        boolean finished = true;
        for (ModelCheck modelCheck : lModelChecks) {
            boolean match = false;
            for (DiagnoseCheck diagnoseCheck : lDiagnoseChecks) {
                if (diagnoseCheck.getkCheck() == modelCheck.getoCheck().getId()) {
                    match = true;
                    if (diagnoseCheck.gettStatus() == 2) {
                        passed = false;
                    }
                }
            }
            if (!match && passed != null && passed == true) {
                passed = null;
            }
            if (finished && !match) {
                finished = false;
            }
        }
        boolean bRepair = model.getlModelDamages().size() > 1;
        bRepair = false; // Leave when we want to fully check RepairDevices
        if (!bRepair && (passed != null && !passed) && !finished) {
            finished = true;
        }

        setbPassed(passed);
        setbFinished(finished);

        // Update date
        dLastUpdate = Calendar.getInstance().getTime();

        upload();
    }

    public boolean hasDiagnoseCheck(int kCheck) {
        for (DiagnoseCheck diagnoseCheck : lDiagnoseChecks) {
            if (diagnoseCheck.getkCheck() == kCheck) {
                return true;
            }
        }
        return false;
    }

    private void upload() {
        JSONObject jsonDiagnose = new JSONObject();
        try {
            jsonDiagnose.put("kDiagnose", id);
            jsonDiagnose.put("bFinished", bFinished ? 1 : 0);
            jsonDiagnose.put("bPassed", bPassed != null ? bPassed ? 1 : 0 : null);
            jsonDiagnose.put("dLastUpdate", Utility_DateTime.dateTimeToStringDatabase(dLastUpdate));
        } catch (JSONException e) {
            e.printStackTrace();
        }

        cVolley.execute(Request.Method.POST, Urls.URL_UPDATE_DIAGNOSE, jsonDiagnose);
    }

    public int getId() {
        return id;
    }

    public int getkDevice() {
        return kDevice;
    }

    public void setkDevice(int kDevice) {
        this.kDevice = kDevice;
    }

    public int getkUser() {
        return kUser;
    }

    public String getcUser() {
        return cUser;
    }

    public void setcUser(String cUser) {
        this.cUser = cUser;
    }

    public ArrayList<DiagnoseCheck> getlDiagnoseChecks() {
        return lDiagnoseChecks;
    }

    public Date getdCreation() {
        return dCreation;
    }

    public void setdCreation(Date dCreation) {
        this.dCreation = dCreation;
    }

    public Date getdLastUpdate() {
        return dLastUpdate;
    }

    public void setdLastUpdate(Date dLastUpdate) {
        this.dLastUpdate = dLastUpdate;
    }

    public boolean isbFinished() {
        return bFinished;
    }

    public void setbFinished(boolean bFinished) {
        this.bFinished = bFinished;
    }

    public Boolean isbPassed() {
        return bPassed;
    }

    public void setbPassed(Boolean bPassed) {
        this.bPassed = bPassed;
    }

    public void delete(ArrayList<ModelCheck> lModelChecks) {
        for (DiagnoseCheck diagnoseCheck : lDiagnoseChecks) {
            diagnoseCheck.delete();
            if (diagnoseCheck.gettStatus() == 2) {
                for (ModelCheck modelCheck : lModelChecks) {
                    if (modelCheck.getoCheck().getId() == diagnoseCheck.getkCheck()) {
                        // Lower count for amount of fails
                        modelCheck.setnCount(modelCheck.getnCount() > 0 ? modelCheck.getnCount() - 1 : 0);
                    }
                }
            }
        }
        cVolley.execute(Request.Method.DELETE, Urls.URL_DELETE_DIAGNOSE + id, null);
    }

    @Override
    public int compareTo(Diagnose o) {
        if (getdLastUpdate() == null || o.getdLastUpdate() == null) {
            return 0;
        }
        return o.getdLastUpdate().compareTo(getdLastUpdate());
    }
}
