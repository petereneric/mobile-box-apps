package com.example.ericschumacher.bouncer.Activities.Tools;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Activities.Activity_Authentication;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Button.Fragment_Button;
import com.example.ericschumacher.bouncer.Fragments.Button.Fragment_Button_Columba_Send;
import com.example.ericschumacher.bouncer.Fragments.Loading.Fragment_Loading;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Object;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Order;
import com.example.ericschumacher.bouncer.Fragments.Table.Fragment_Table;
import com.example.ericschumacher.bouncer.Fragments.Table.Fragment_Table_Order;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Order;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Json;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Volley.SqlCommands;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;
import com.example.ericschumacher.bouncer.Zebra.ManagerPrinter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Activity_Columba extends Activity_Authentication implements Fragment_Order.Interface_Fragment_Order, Fragment_Button.Interface_Fragment_Button, Fragment_Object.Interface_Fragment_Object_Menu, Fragment_Table.Interface_Fragment_Table_Select_DataImport {

    // Layout
    Toolbar vToolbar;

    // Print
    public ManagerPrinter mPrinter;

    // Data
    ArrayList<Order> lOpenOrders = new ArrayList<>();
    JSONArray lOpenOrdersJson = new JSONArray();
    ArrayList<Order> lRecentOrders = new ArrayList<>();
    JSONArray lRecentOrdersJson = new JSONArray();
    Order oOrderSelected;

    // Fragments
    public FragmentManager fManager;
    Fragment_Order fOrder;
    Fragment_Table_Order fOpenOrders;
    Fragment_Table_Order fRecentOrders;

    // Connection
    Volley_Connection cVolley;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Fragments
        fManager = getSupportFragmentManager();

        // Print
        //mPrinter = new ManagerPrinter(this);

        // Connection
        cVolley = new Volley_Connection(this);

        // Layout
        setLayout();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initiateFragments();
    }

    @Override
    protected void onResume() {
        super.onResume();
        base();
        mPrinter = new ManagerPrinter(this);
        mPrinter.connect();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mPrinter != null) mPrinter.disconnect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //if (mPrinter != null) mPrinter.disconnect();
    }


    // Fragments

    public void initiateFragments() {
        fOrder = (Fragment_Order) fManager.findFragmentById(R.id.fOrder);
        fOpenOrders = (Fragment_Table_Order) fManager.findFragmentById(R.id.fMoreOrders);
        fRecentOrders = (Fragment_Table_Order) fManager.findFragmentById(R.id.fRecentOrders);
    }

    public void showFragment(Fragment fragment, Bundle bData, String cTag) {
        fragment.setArguments(bData);
        fManager.beginTransaction().replace(R.id.flInteraction, fragment, cTag).commit();
    }

    public void removeFragment(String cTag) {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(cTag)).commit();
    }

    public void removeFragmentInteraction() {
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (Fragment fragment : fragments) {
            if (fragment == null || fragment.getTag().equals(Constants_Intern.FRAGMENT_TABLE_OPEN_ORDERS) || fragment.getTag().equals(Constants_Intern.FRAGMENT_TABLE_RECENT_ORDERS) || fragment.getTag().equals(Constants_Intern.FRAGMENT_ORDER)) {
                continue;
            }
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
    }


    // Layout

    private void setLayout() {
        setContentView(R.layout.activity_columba);

        // Initiate
        vToolbar = findViewById(R.id.vToolbar);

        // Toolbar
        setSupportActionBar(vToolbar);
        getSupportActionBar().setTitle(getString(R.string.activity_name_columba));
        vToolbar.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.color_primary, null));
        vToolbar.setTitleTextColor(ResourcesCompat.getColor(getResources(), R.color.color_white, null));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

    }

    private void updateLayout() {
        if (lOpenOrders.size() > 0) {
            fManager.beginTransaction().show(fOpenOrders).commit();
            fOpenOrders.update();
        } else {
            fManager.beginTransaction().hide(fOpenOrders).commit();
            removeFragmentInteraction();
        }
        if (lRecentOrders.size() > 0) {
            fManager.beginTransaction().show(fRecentOrders).commit();
            fRecentOrders.update();
        } else {
            fManager.beginTransaction().hide(fRecentOrders).commit();
        }
        if (getOrder() != null) {
            fManager.beginTransaction().show(fOrder).commit();
            fOrder.updateLayout();
            if (getOrder().isbSent()) {
                fOrder.lMenu.setVisibility(View.VISIBLE);
            } else {
                fOrder.lMenu.setVisibility(View.GONE);
                showFragment(new Fragment_Button_Columba_Send(), null, Constants_Intern.FRAGMENT_BUTTON_COLUMBA_SEND);
            }
        } else {
            fManager.beginTransaction().hide(fOrder).commit();
        }
    }

    // Base & Reset

    private void base() {
        if (lOpenOrders.size() == 0) {
            cVolley.getResponse(Request.Method.POST, Urls.URL_GET_MARKETING_SHIPPING_RECORDS, SqlCommands.getWhereClauseConstant(SqlCommands.WHERE_CLAUSE_CONSTANT_MARKETING_UNSHIPPED_RECORDS), new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    if (Volley_Connection.successfulResponse(oJson)) {
                        lOpenOrdersJson = oJson.getJSONArray(Constants_Extern.LIST_ORDER);
                        for (int i = 0; i < lOpenOrdersJson.length(); i++) {
                            lOpenOrders.add(new Order(Activity_Columba.this, lOpenOrdersJson.getJSONObject(i)));
                        }
                        updateLayout();
                    } else {
                        Utility_Toast.show(Activity_Columba.this, R.string.no_orders);
                    }
                }
            });
        }
        if (lRecentOrders.size() == 0) {
            lRecentOrders.clear();
            lRecentOrdersJson = new JSONArray();
            cVolley.getResponse(Request.Method.POST, Urls.URL_GET_MARKETING_SHIPPING_RECORDS, SqlCommands.getWhereClauseConstant(SqlCommands.WHERE_CLAUSE_CONSTANT_RECENT_ORDERS), new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    if (Volley_Connection.successfulResponse(oJson)) {
                        lRecentOrdersJson = oJson.getJSONArray(Constants_Extern.LIST_ORDER);
                        for (int i = 0; i < lRecentOrdersJson.length(); i++) {
                            lRecentOrders.add(new Order(Activity_Columba.this, lRecentOrdersJson.getJSONObject(i)));
                        }
                        updateLayout();
                    }
                }
            });
        }
        updateLayout();
    }

    private void reset() {
        lOpenOrders.clear();
        base();
    }

    // Get

    @Override
    public Order getOrder() {
        if (oOrderSelected == null && lOpenOrders.size() > 0) {
            oOrderSelected = lOpenOrders.get(0);
        }
        return oOrderSelected;
    }


    @Override
    public void returnButton(String cTag, int tAction) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_BUTTON_COLUMBA_SEND:
                switch (tAction) {
                    case Constants_Intern.TYPE_ACTION_FRAGMENT_BUTTON_PRIMARY_ONE:
                        showFragment(new Fragment_Loading(), null, Constants_Intern.FRAGMENT_LOADING);
                        // create label
                        // check print connection
                        if (mPrinter != null) {
                            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_DHL_INTERN_CREATE_SHIPMENT_ORDER + getOrder().getId(), null, new Interface_VolleyResult() {
                                @Override
                                public void onResult(JSONObject oJson) throws JSONException {
                                    removeFragment(Constants_Intern.FRAGMENT_LOADING);
                                    if (Volley_Connection.successfulResponse(oJson)) {
                                        mPrinter.printDhlLabel(oJson.getString(Constants_Extern.ZPL2));
                                        getOrder().setZplLabel(oJson.getString(Constants_Extern.ZPL2));
                                        Log.i("ZPL2: ", oJson.getString(Constants_Extern.ZPL2));
                                        getOrder().setkTracking(oJson.getString(Constants_Extern.SHIPMENT_NUMBER));
                                        getOrder().setbSent(true);
                                        getOrder().mail();

                                        Log.i("JsonArray Open before: ", lOpenOrdersJson.toString());
                                        Log.i("Array Open before: ", lOpenOrders.toString());

                                        lRecentOrdersJson.put(lOpenOrdersJson.getJSONObject(lOpenOrders.indexOf(getOrder())));
                                        lRecentOrders.add(getOrder());
                                        lOpenOrdersJson.remove(lOpenOrders.indexOf(getOrder()));
                                        lOpenOrders.remove(getOrder());

                                        Log.i("JsonArray Open after: ", lOpenOrdersJson.toString());
                                        Log.i("Array Open after: ", lOpenOrders.toString());

                                        oOrderSelected = null;
                                        base();
                                    } else {
                                        // Stop loading
                                        Utility_Toast.showString(Activity_Columba.this, oJson.getString(Constants_Extern.DETAILS));
                                    }
                                }
                            });
                        } else {
                            Utility_Toast.show(this, R.string.printer_not_connected);
                        }

                        return;
                    case Constants_Intern.TYPE_ACTION_FRAGMENT_BUTTON_SECONDARY_ONE:
                        AlertDialog.Builder builder = new AlertDialog.Builder(Activity_Columba.this);
                        builder.setTitle(getString(R.string.report_missing_stock));
                        String[] items = {getString(R.string.mobile_box), getString(R.string.bricolage), getString(R.string.flyer), getString(R.string.poster)};
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://trello.com/b/NWNwkXmI/bestellungen"));
                                startActivity(browserIntent);
                                switch (which) {
                                    // setStock for specific MarketingPackage item to no stock
                                    // send mail to info@
                                    case 0:
                                        break;
                                    case 1:
                                        break;
                                    case 2:
                                        break;
                                    case 3:
                                        break;

                                }
                                reset();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                        return;
                }
                break;
            case Constants_Intern.FRAGMENT_BUTTON_COLUMBA_FINISH:
                switch (tAction) {
                    case Constants_Intern.TYPE_ACTION_FRAGMENT_BUTTON_PRIMARY_ONE:

                        break;
                }
                break;
        }
        removeFragment(cTag);
    }

    @Override
    public void returnMenu(int tAction, String cTag) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_ORDER:
                switch (tAction) {
                    case Constants_Intern.TYPE_ACTION_MENU_PRINT:
                        Log.i("Orrrder", getOrder().getId() + "");
                        if (getOrder().getZplLabel() != null) {
                            mPrinter.printDhlLabel(getOrder().getZplLabel());
                        } else {
                            Utility_Toast.show(this, R.string.cant_find_label);
                            Log.i("ee", "22");
                        }
                        break;
                    case Constants_Intern.TYPE_ACTION_MENU_MAIL:
                        getOrder().mail();
                        Utility_Toast.show(this, R.string.mail_send_again);
                        break;
                }
                break;
        }
    }

    @Override
    public void returnTable(String cTag, JSONObject oJson) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_TABLE_OPEN_ORDERS:
                Integer index = Utility_Json.indexOf(lOpenOrdersJson, oJson);
                if (index != null) oOrderSelected = lOpenOrders.get(index);
                break;
            case Constants_Intern.FRAGMENT_TABLE_RECENT_ORDERS:
                Integer index_ = Utility_Json.indexOf(lRecentOrdersJson, oJson);
                if (index_ != null) {
                    oOrderSelected = lRecentOrders.get(index_);
                    removeFragmentInteraction();
                }
                break;
        }
        updateLayout();
    }

    @Override
    public JSONArray getData(String cTag) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_TABLE_OPEN_ORDERS:
                return lOpenOrdersJson;
            case Constants_Intern.FRAGMENT_TABLE_RECENT_ORDERS:
                return lRecentOrdersJson;
        }
        return null;
    }

    @Override
    public boolean isSelected(String cTag, int position) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_TABLE_OPEN_ORDERS:
                if (getOrder() != null && !getOrder().isbSent())
                    return lOpenOrders.indexOf(getOrder()) == position;
                break;
            case Constants_Intern.FRAGMENT_TABLE_RECENT_ORDERS:
                if (getOrder() != null && getOrder().isbSent())
                    return lRecentOrders.indexOf(getOrder()) == position;
                break;
        }
        return false;
    }

    // Menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
