package com.example.ericschumacher.bouncer.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Activity_List;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_List;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Ann;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Activity_List extends AppCompatActivity implements Interface_Activity_List {

    // Layout
    Toolbar vToolbar;
    FrameLayout flContainer;

    // Connection
    Volley_Connection cVolley;

    // Data
    JSONArray jsonArrayData;
    ArrayList<Ann> lAnn;

    // Else
    FragmentManager fManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connection
        cVolley = new Volley_Connection(this);

        // Else
        fManager = getSupportFragmentManager();

        // Layout
        setLayout();


    }

    // Layout
    private void setLayout() {
        setContentView(R.layout.activity_list);

        vToolbar = findViewById(R.id.Toolbar);
        flContainer = findViewById(R.id.flContainer);
    }

    @Override
    public void getData(String cTag, final Interface_Fragment_List iFragmentList) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_LKU:
                try {
                    cVolley.getResponse(Request.Method.POST, Urls.URL_GET_DEVICES, new JSONObject("{\"WHERE_CLAUSE_CONSTANT\" : \"WHERE_CLAUSE_DEVICES_NO_MODEL_COLOR_SHAPE_PRIME_STOCK\"}"), new Interface_VolleyResult() {
                        @Override
                        public void onResult(JSONObject oJson) throws JSONException {
                            iFragmentList.setData(oJson.getJSONArray(Constants_Extern.LIST_DEVICES));
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    @Override
    public ArrayList<Ann> getAnn(String cTag) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_LKU:
                ArrayList<Ann> lAnn = new ArrayList<>();
                lAnn.add(new Ann(null, Constants_Extern.OBJECT_STORAGEPLACE, Constants_Extern.LKU, getString(R.string.lku), 1));
                lAnn.add(new Ann(null, null, Constants_Extern.ID_DEVICE, getString(R.string.id_device), 1));
                lAnn.add(new Ann(null, null, Constants_Extern.POSITION, getString(R.string.position), 1));
                return lAnn;
        }
        return null;
    }

    @Override
    public void onSwipeLeft(int nPosition, String cTag, JSONObject jsonObject, Interface_Fragment_List iFragmentList) {

    }

    @Override
    public void onSwipeRight(int nPosition, String cTag, JSONObject jsonObject, Interface_Fragment_List iFragmentList) {

    }


    @Override
    public void setViewSwipeRight(String cTag, TextView tvBackground) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_LKU:

                break;

        }
    }

    @Override
    public void setViewSwipeLeft(String cTag, TextView tvBackground) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_LKU:

                break;

        }
    }

    @Override
    public void onClick(int nPosition, String cTag, JSONObject jsonObject) {

    }



    public void onClick(String cTag) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_LKU:

                break;

        }
    }

    @Override
    public boolean bHeader(String cTag) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_LKU:

                break;

        }
        return false;
    }

    @Override
    public void setToolbar(String cTag) {

    }

    @Override
    public JSONObject getJsonObject(int position, String cTag) {
        return null;
    }

    @Override
    public int getSwipeBehaviour(String cTag) {
        return 0;
    }
}

