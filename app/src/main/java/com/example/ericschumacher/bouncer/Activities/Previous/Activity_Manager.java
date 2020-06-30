package com.example.ericschumacher.bouncer.Activities.Previous;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List_Navigation;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_List_New;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Activity_List;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_List;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Ann;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Marketing_Shipping;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Activity_Manager extends AppCompatActivity implements View.OnClickListener, Interface_Activity_List, Interface_Click {

    // Layout
    DrawerLayout vDrawer;
    Toolbar vToolbar;
    TextView tvDoubleModels;
    TextView tvDevicesNoModelLku;
    ActionBarDrawerToggle mDrawerToggle;
    FrameLayout flContainer;
    RecyclerView rvNavigation;

    // Connection
    Volley_Connection cVolley;

    // Data
    JSONArray jsonArrayData;
    ArrayList<Ann> lAnn;

    // Else
    android.support.v4.app.FragmentManager fManager;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connection
        cVolley = new Volley_Connection(this);

        // Layout
        setLayoutSpecials();

        // Else
        fManager = getSupportFragmentManager();

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Layout
    private void setLayoutSpecials() {
        setContentView(R.layout.activity_manager);

        // Initiate
        vToolbar = findViewById(R.id.Toolbar);
        vDrawer = findViewById(R.id.drawer_layout);
        flContainer = findViewById(R.id.flFragment);
        tvDoubleModels = findViewById(R.id.tvDoubleModel);
        tvDevicesNoModelLku = findViewById(R.id.tvDevicesWithoutModelInLku);
        rvNavigation = findViewById(R.id.rvNavigation);

        // OnClickListener
        tvDoubleModels.setOnClickListener(this);
        tvDevicesNoModelLku.setOnClickListener(this);

        // Toolbar
        setSupportActionBar(vToolbar);

        // Drawer
        mDrawerToggle = new ActionBarDrawerToggle(this, vDrawer, 0, 0) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        vDrawer.addDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // List Navigation
        rvNavigation.setLayoutManager(new LinearLayoutManager(this));
        Adapter_List_Navigation aListNavigation = new Adapter_List_Navigation(this, getListNavigation(), this);
        rvNavigation.setAdapter(aListNavigation);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public void getData(String cTag, final Interface_Fragment_List iFragmentList) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_MULTIPLE_MODEL:
                try {
                    cVolley.getResponse(Request.Method.POST, Urls.URL_GET_MODELS, new JSONObject("{\"WHERE_CLAUSE_CONSTANT\" : \"" + Constants_Intern.WHERE_CLAUSE_MODELS_DUPLICATE + "\"}"), new Interface_VolleyResult() {
                        @Override
                        public void onResult(JSONObject oJson) throws JSONException {
                            iFragmentList.setData(oJson.getJSONArray(Constants_Extern.LIST_MODELS));
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_COLOR_SHAPE_LKU:
                try {
                    cVolley.getResponse(Request.Method.POST, Urls.URL_GET_DEVICES, new JSONObject("{\"WHERE_CLAUSE_CONSTANT\" : \"" + Constants_Intern.WHERE_CLAUSE_DEVICES_NO_MODEL_COLOR_SHAPE_PRIME_STOCK + "\"}"), new Interface_VolleyResult() {
                        @Override
                        public void onResult(JSONObject oJson) throws JSONException {
                            iFragmentList.setData(oJson.getJSONArray(Constants_Extern.LIST_DEVICES));
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constants_Intern.FRAGMENT_MARKETING_UNSHIPPED_RECORDS:
                try {
                    cVolley.getResponse(Request.Method.POST, Urls.URL_GET_MARKETING_SHIPPING_RECORDS, new JSONObject("{\"WHERE_CLAUSE_CONSTANT\" : \"" + Constants_Intern.WHERE_CLAUSE_MARKETING_UNSHIPPED_RECORDS + "\"}"), new Interface_VolleyResult() {
                        @Override
                        public void onResult(JSONObject oJson) throws JSONException {
                            iFragmentList.setData(oJson.getJSONArray(Constants_Extern.LIST_SHIPPING_RECORDS));
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constants_Intern.FRAGMENT_DEVICES_NO_COLOR_LKU:
                try {
                    cVolley.getResponse(Request.Method.POST, Urls.URL_GET_DEVICES, new JSONObject("{\"WHERE_CLAUSE_CONSTANT\" : \"" + Constants_Intern.WHERE_CLAUSE_DEVICES_NO_COLOR_PRIME_STOCK + "\"}"), new Interface_VolleyResult() {
                        @Override
                        public void onResult(JSONObject oJson) throws JSONException {
                            iFragmentList.setData(oJson.getJSONArray(Constants_Extern.LIST_DEVICES));
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_LKU:
                try {
                    cVolley.getResponse(Request.Method.POST, Urls.URL_GET_DEVICES, new JSONObject("{\"WHERE_CLAUSE_CONSTANT\" : \"" + Constants_Intern.WHERE_CLAUSE_DEVICES_NO_MODEL_PRIME_STOCK + "\"}"), new Interface_VolleyResult() {
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
            case Constants_Intern.FRAGMENT_MULTIPLE_MODEL:
                ArrayList<Ann> lAnn = new ArrayList<>();
                lAnn.add(new Ann(null, null, Constants_Extern.ID_MODEL, getString(R.string.id_model), 1));
                lAnn.add(new Ann(null, null, Constants_Extern.NAME_MODEL, getString(R.string.name_model), 1));
                return lAnn;
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_COLOR_SHAPE_LKU:
                ArrayList<Ann> lAnnTwo = new ArrayList<>();
                lAnnTwo.add(new Ann(null, null, Constants_Extern.ID_DEVICE, getString(R.string.id_device), 1));
                lAnnTwo.add(new Ann(null, Constants_Extern.OBJECT_STORAGEPLACE, Constants_Extern.NAME_STOCK, getString(R.string.name_stock), 1));
                lAnnTwo.add(new Ann(null, Constants_Extern.OBJECT_STORAGEPLACE, Constants_Extern.ID_LKU, getString(R.string.id_lku), 1));
                lAnnTwo.add(new Ann(null, Constants_Extern.OBJECT_STORAGEPLACE, Constants_Extern.POSITION, getString(R.string.position), 1));
                return lAnnTwo;
            case Constants_Intern.FRAGMENT_MARKETING_UNSHIPPED_RECORDS:
                ArrayList<Ann> lAnnThree = new ArrayList<>();
                lAnnThree.add(new Ann(null, Constants_Extern.OBJECT_COLLECTOR, Constants_Extern.NAME, getString(R.string.name_collector), 3));
                lAnnThree.add(new Ann(null, Constants_Extern.OBJECT_COLLECTOR, Constants_Extern.ID, getString(R.string.id_collector), 1));
                lAnnThree.add(new Ann(null, null, Constants_Extern.ID, getString(R.string.id_shipping_record), 2));
                lAnnThree.add(new Ann(null, Constants_Extern.OBJECT_BOX, Constants_Extern.ID, getString(R.string.id_box), 1));
                lAnnThree.add(new Ann(null, null, Constants_Extern.NUMBER_BOX, getString(R.string.number_box), 1));
                lAnnThree.add(new Ann(null, Constants_Extern.OBJECT_BRICOLAGE, Constants_Extern.ID, getString(R.string.id_bricolage), 1));
                lAnnThree.add(new Ann(null, null, Constants_Extern.NUMBER_BRICOLAGE, getString(R.string.number_bricolage), 1));
                lAnnThree.add(new Ann(null, Constants_Extern.OBJECT_FLYER, Constants_Extern.ID, getString(R.string.id_flyer), 1));
                lAnnThree.add(new Ann(null, null, Constants_Extern.NUMBER_FLYER, getString(R.string.number_flyer), 1));
                lAnnThree.add(new Ann(null, Constants_Extern.OBJECT_POSTER, Constants_Extern.ID, getString(R.string.id_poster), 1));
                lAnnThree.add(new Ann(null, null, Constants_Extern.NUMBER_POSTER, getString(R.string.number_poster), 1));
                lAnnThree.add(new Ann(null, null, Constants_Extern.ID_TRACKING, getString(R.string.id_tracking), 1));
                lAnnThree.add(new Ann(null, null, Constants_Extern.IS_SENT, getString(R.string.shipping_status), 1));
                return lAnnThree;
            case Constants_Intern.FRAGMENT_DEVICES_NO_COLOR_LKU:
                ArrayList<Ann> lAnnFour = new ArrayList<>();
                lAnnFour.add(new Ann(null, null, Constants_Extern.ID_DEVICE, getString(R.string.id_device), 1));
                lAnnFour.add(new Ann(null, Constants_Extern.OBJECT_STORAGEPLACE, Constants_Extern.NAME_STOCK, getString(R.string.name_stock), 1));
                lAnnFour.add(new Ann(null, Constants_Extern.OBJECT_STORAGEPLACE, Constants_Extern.ID_LKU, getString(R.string.id_lku), 1));
                lAnnFour.add(new Ann(null, Constants_Extern.OBJECT_STORAGEPLACE, Constants_Extern.POSITION, getString(R.string.position), 1));
                return lAnnFour;
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_LKU:
                ArrayList<Ann> lAnnFive = new ArrayList<>();
                lAnnFive.add(new Ann(null, null, Constants_Extern.ID_DEVICE, getString(R.string.id_device), 1));
                lAnnFive.add(new Ann(null, Constants_Extern.OBJECT_STORAGEPLACE, Constants_Extern.NAME_STOCK, getString(R.string.name_stock), 1));
                lAnnFive.add(new Ann(null, Constants_Extern.OBJECT_STORAGEPLACE, Constants_Extern.ID_LKU, getString(R.string.id_lku), 1));
                lAnnFive.add(new Ann(null, Constants_Extern.OBJECT_STORAGEPLACE, Constants_Extern.POSITION, getString(R.string.position), 1));
                return lAnnFive;
        }
        return null;
    }

    @Override
    public void onSwipeLeft(final int nPosition, String cTag, JSONObject jsonObject, final Interface_Fragment_List iFragmentList) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_MULTIPLE_MODEL:
                // merge
                try {
                    cVolley.execute(Request.Method.POST, Urls.URL_MODELS_MERGE + jsonObject.getString(Constants_Intern.NAME_MODEL), null);
                    iFragmentList.remove(nPosition);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_COLOR_SHAPE_LKU:
                Device oDeviceThee = new Device(jsonObject, this);
                oDeviceThee.setoStoragePlace(null);
                break;
            case Constants_Intern.FRAGMENT_MARKETING_UNSHIPPED_RECORDS:
                Marketing_Shipping oMarketingShipping = new Marketing_Shipping(this, jsonObject);
                //oMarketingShipping.setbSent(true);
                //iFragmentList.updateLayout();
                break;
            case Constants_Intern.FRAGMENT_DEVICES_NO_COLOR_LKU:
                Device oDevice = new Device(jsonObject, this);
                oDevice.setoStoragePlace(null);
                iFragmentList.remove(nPosition);
                break;
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_LKU:
                Device oDeviceTwo = new Device(jsonObject, this);
                oDeviceTwo.setoStoragePlace(null);
                break;
        }
    }

    @Override
    public void onSwipeRight(int nPosition, String cTag, JSONObject jsonObject, Interface_Fragment_List iFragmentList) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_MULTIPLE_MODEL:

                break;
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_COLOR_SHAPE_LKU:

                break;
            case Constants_Intern.FRAGMENT_MARKETING_UNSHIPPED_RECORDS:

                break;
            case Constants_Intern.FRAGMENT_DEVICES_NO_COLOR_LKU:

                break;
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_LKU:

                break;
        }
    }

    @Override
    public void setViewSwipeRight(String cTag, TextView tvBackground) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_MULTIPLE_MODEL:

                break;
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_COLOR_SHAPE_LKU:

                break;
            case Constants_Intern.FRAGMENT_DEVICES_NO_COLOR_LKU:

                break;
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_LKU:

                break;
        }
    }

    @Override
    public void setViewSwipeLeft(String cTag, TextView tvBackground) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_MULTIPLE_MODEL:
                setSwipeViewLeftBackground(tvBackground, getString(R.string.merge), R.color.color_secondary);
                break;
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_COLOR_SHAPE_LKU:
                setSwipeViewLeftBackground(tvBackground, getString(R.string.book_out), R.color.color_secondary);
                break;
            case Constants_Intern.FRAGMENT_MARKETING_UNSHIPPED_RECORDS:
                setSwipeViewLeftBackground(tvBackground, getString(R.string.sent), R.color.color_secondary);
                break;
            case Constants_Intern.FRAGMENT_DEVICES_NO_COLOR_LKU:
                setSwipeViewLeftBackground(tvBackground, getString(R.string.book_out), R.color.color_secondary);
                break;
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_LKU:
                setSwipeViewLeftBackground(tvBackground, getString(R.string.book_out), R.color.color_secondary);
                break;
        }
    }

    @Override
    public void onClick(int nPosition, String cTag, JSONObject jsonObject) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_MULTIPLE_MODEL:

                break;
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_COLOR_SHAPE_LKU:

                break;
            case Constants_Intern.FRAGMENT_MARKETING_UNSHIPPED_RECORDS:

                break;
            case Constants_Intern.FRAGMENT_DEVICES_NO_COLOR_LKU:

                break;
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_LKU:

                break;
        }
    }

    @Override
    public boolean bHeader(String cTag) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_MULTIPLE_MODEL:
                return true;
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_COLOR_SHAPE_LKU:
                return true;
            case Constants_Intern.FRAGMENT_MARKETING_UNSHIPPED_RECORDS:
                return true;
            case Constants_Intern.FRAGMENT_DEVICES_NO_COLOR_LKU:
                return true;
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_LKU:
                return true;
        }
        return false;
    }

    @Override
    public void setToolbar(String cTag) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_MULTIPLE_MODEL:
                getSupportActionBar().setTitle(getString(R.string.multiple_model));
                break;
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_COLOR_SHAPE_LKU:
                getSupportActionBar().setTitle(getString(R.string.devices_no_model_color_shape_lku));
                break;
            case Constants_Intern.FRAGMENT_MARKETING_UNSHIPPED_RECORDS:
                getSupportActionBar().setTitle(getString(R.string.marketing_shipping_records_not_sent));
                break;
            case Constants_Intern.FRAGMENT_DEVICES_NO_COLOR_LKU:
                getSupportActionBar().setTitle(getString(R.string.devices_no_color_lku));
                break;
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_LKU:
                getSupportActionBar().setTitle(getString(R.string.devices_no_model_lku));
                break;
        }
    }

    @Override
    public JSONObject getJsonObject(int position, String cTag) {
        JSONObject jsonObject = null;
        try {
            if (bHeader(cTag)) {
                jsonObject = jsonArrayData.getJSONObject(position - 1);
            } else {
                jsonObject = jsonArrayData.getJSONObject(position);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    @Override
    public int getSwipeBehaviour(String cTag) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_MULTIPLE_MODEL:
                return ItemTouchHelper.LEFT;
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_COLOR_SHAPE_LKU:
                return ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
            case Constants_Intern.FRAGMENT_MARKETING_UNSHIPPED_RECORDS:
                return ItemTouchHelper.LEFT;
            case Constants_Intern.FRAGMENT_DEVICES_NO_COLOR_LKU:
                return ItemTouchHelper.LEFT;
            case Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_LKU:
                return ItemTouchHelper.LEFT;
            default:
                return ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT;
        }
    }

    private ArrayList<String> getListNavigation() {
        ArrayList<String> lNavigation = new ArrayList<>();
        lNavigation.add(getString(R.string.multiple_model));
        lNavigation.add(getString(R.string.devices_no_model_color_shape_lku));
        lNavigation.add(getString(R.string.marketing_shipping_records_not_sent));
        lNavigation.add(getString(R.string.devices_no_color_lku));
        lNavigation.add(getString(R.string.devices_no_model_lku));
        return lNavigation;
    }

    // Navigation
    @Override
    public void onClick(int position) {
        switch (position) {
            case 0: // Multiple Model
                replaceFragment(new Fragment_List_New(), Constants_Intern.FRAGMENT_MULTIPLE_MODEL);
                break;
            case 1: // Devices without model in Lku
                replaceFragment(new Fragment_List_New(), Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_COLOR_SHAPE_LKU);
                break;
            case 2: // Marketing Shipping Records not sent
                replaceFragment(new Fragment_List_New(), Constants_Intern.FRAGMENT_MARKETING_UNSHIPPED_RECORDS);
                break;
            case 3: // Devices without color in Lku
                replaceFragment(new Fragment_List_New(), Constants_Intern.FRAGMENT_DEVICES_NO_COLOR_LKU);
                break;
            case 4: // Devices without color in Lku
                replaceFragment(new Fragment_List_New(), Constants_Intern.FRAGMENT_DEVICES_NO_MODEL_LKU);
                break;
        }
        vDrawer.closeDrawer(Gravity.LEFT);
    }

    public void replaceFragment(Fragment fragment, String tag) {
        fManager.beginTransaction().replace(R.id.flFragment, fragment, tag).commit();
    }

    private void setSwipeViewLeftBackground(TextView tvBackground, String cText, int kColor) {
        tvBackground.setText(cText);
        tvBackground.setBackgroundColor(ResourcesCompat.getColor(getResources(), kColor, null));
        tvBackground.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
    }
}
