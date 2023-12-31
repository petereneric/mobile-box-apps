package com.example.ericschumacher.bouncer.Activities.Manager;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Display.Fragment_Display;
import com.example.ericschumacher.bouncer.Fragments.Loading.Fragment_Loading;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Article;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Device;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Model;
import com.example.ericschumacher.bouncer.Fragments.Others.Fragment_Verify_Article;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Article;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

public class Activity_Article extends Activity_Device implements Fragment_Verify_Article.Interface_Verify_Article {

    // Data
    public Article oArticle;

    // Fragments
    public Fragment_Article fArticle;


    //---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    // Fragments

    public void initiateFragments() {
        fModel = (Fragment_Model) fManager.findFragmentById(R.id.fModel);
        fDevice = (Fragment_Device) fManager.findFragmentById(R.id.fDevice);
        fArticle = (Fragment_Article) fManager.findFragmentById(R.id.fArticle);
        fDevice.lMenu.setVisibility(View.GONE);
    }

    public void showFragment(Fragment fragment, Bundle bData, String cTag, Boolean bKeyboard) {
        super.showFragment(fragment, bData, cTag, bKeyboard);
    }

    public void showFragmentLoadingArticle() {
        Log.i("jo", "jooo");
        showFragment(new Fragment_Loading(), null, Constants_Intern.FRAGMENT_LOADING, null);
        getSupportFragmentManager().beginTransaction().hide(fArticle).commit();
    }

    public void removeFragments() {
        getSupportFragmentManager().beginTransaction().hide(fModel).commit();
        getSupportFragmentManager().beginTransaction().hide(fDevice).commit();
        getSupportFragmentManager().beginTransaction().hide(fArticle).commit();
        if (fManager.findFragmentByTag(Constants_Intern.FRAGMENT_DISPLAY_ARTICLE_NOT_FOUND) != null) {
            removeFragment(Constants_Intern.FRAGMENT_DISPLAY_ARTICLE_NOT_FOUND);
        }
        if (fManager.findFragmentByTag(Constants_Intern.FRAGMENT_LOADING) != null) {
            removeFragment(Constants_Intern.FRAGMENT_LOADING);
        }
    }


    // Layout

    public void setLayout() {
        super.setLayout();

        etSearch.setOnEditorActionListener(this);

        // Toolbar
        getSupportActionBar().setTitle(getString(R.string.article_manager));
    }

    public int getIdLayout() {
        return R.layout.activity_article;
    }


    // Update

    public void updateLayout() {

        removeFragments();
        // Fragments
        if (oModel != null) {
            getSupportFragmentManager().beginTransaction().show(fModel).commit();
            fModel.updateLayout();
        }
        if (oDevice != null) {
            getSupportFragmentManager().beginTransaction().show(fDevice).commit();
            fDevice.updateLayout();
        }
        if (oArticle != null) {
            Log.i("jjjjooo", "jjooo");
            getSupportFragmentManager().beginTransaction().show(fArticle).commit();
        } else {
            if (oDevice != null) {
                Log.i("joooo", "jo");
                Bundle bundle = new Bundle();
                bundle.putString(Constants_Intern.TEXT, getString(R.string.article_not_found));
                showFragment(new Fragment_Display(), bundle, Constants_Intern.FRAGMENT_DISPLAY_ARTICLE_NOT_FOUND, null);
            }
        }
        fArticle.updateLayout();

        // TextViewSearch & EditTextSearch
        switch (SharedPreferences.getInt(Constants_Intern.SEARCH_ARTICLE_TYPE, 0)) {
            case Constants_Intern.MAIN_SEARCH_ARTICLE_TYPE_ID_DEVICE:
                tvSearchType.setText(getString(R.string.id_device));
                etSearch.setHint(getString(R.string.enter_scan_id_device));
                etSearch.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                etSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(20)});
                break;
            case Constants_Intern.MAIN_SEARCH_ARTICLE_TYPE_IMEI:
                tvSearchType.setText(getString(R.string.imei));
                etSearch.setHint(getString(R.string.enter_scan_imei));
                etSearch.setRawInputType(InputType.TYPE_CLASS_NUMBER);
                etSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
                break;
        }
    }

    public void updateArticle() {
        Handler handler = new Handler();
        switch (SharedPreferences.getInt(Constants_Intern.SEARCH_ARTICLE_TYPE, 0)) {
            case Constants_Intern.MAIN_SEARCH_ARTICLE_TYPE_ID_DEVICE:
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_ARTICLE + oDevice.getIdDevice(), null, new Interface_VolleyResult() {
                            @Override
                            public void onResult(JSONObject oJson) throws JSONException {
                                if (Volley_Connection.successfulResponse(oJson)) {
                                    if (!etSearch.getText().toString().equals("") && oJson.getInt(Constants_Extern.ID_DEVICE) == Integer.parseInt(etSearch.getText().toString())) {
                                        oArticle = new Article(Activity_Article.this, oJson.getJSONObject(Constants_Extern.OBJECT_ARTICLE));
                                    } else {
                                        oArticle = null;
                                    }

                                } else {
                                    oArticle = null;
                                }
                                updateLayout();

                            }
                        });
                    }
                }, 1000);
                break;
            case Constants_Intern.MAIN_SEARCH_ARTICLE_TYPE_IMEI:
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_ARTICLE + oDevice.getIdDevice(), null, new Interface_VolleyResult() {
                            @Override
                            public void onResult(JSONObject oJson) throws JSONException {
                                if (Volley_Connection.successfulResponse(oJson)) {
                                        oArticle = new Article(Activity_Article.this, oJson.getJSONObject(Constants_Extern.OBJECT_ARTICLE));
                                } else {
                                    oArticle = null;
                                }
                                updateLayout();
                            }
                        });
                    }
                }, 1000);
                break;
        }

    }


    // Base & Reset

    @Override
    public void base(Boolean bKeyboard) {
        updateLayout();
        //Bundle bundle = new Bundle();
        //bundle.putString(Constants_Intern.TEXT, getString(R.string.edit_new_article));
        //showFragment(new Fragment_Display(), bundle, Constants_Intern.FRAGMENT_DISPLAY_EDIT_NEW_ARTICLE, bKeyboard);
        //getSupportFragmentManager().beginTransaction().show(fPrintArticle).commit();
    }

    public void reset() {
        fModel.showAll(false);
        fDevice.showAll(false);
        oArticle = null;
        super.reset();
    }


    // Search

    public void onSearch() {
        final String cSearchSaved = etSearch.getText().toString();
        switch (SharedPreferences.getInt(Constants_Intern.SEARCH_ARTICLE_TYPE, 0)) {
            case Constants_Intern.MAIN_SEARCH_ARTICLE_TYPE_ID_DEVICE:
                cVolley.getResponse(Request.Method.GET, Urls.URL_GET_DEVICE_BY_ID + etSearch.getText().toString(), null, new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) {
                        if (cSearchSaved.equals(etSearch.getText().toString())) {
                            if (Volley_Connection.successfulResponse(oJson)) {
                                try {
                                    oDevice = new Device(oJson.getJSONObject(Constants_Extern.OBJECT_DEVICE), Activity_Article.this);
                                    oModel = oDevice.getoModel();
                                    cVolley.getResponse(Request.Method.GET, Urls.URL_GET_ARTICLE + oDevice.getIdDevice(), null, new Interface_VolleyResult() {
                                        @Override
                                        public void onResult(JSONObject oJson) throws JSONException {
                                            if (Volley_Connection.successfulResponse(oJson)) {
                                                if (!etSearch.getText().toString().equals("") && oJson.getInt(Constants_Extern.ID_DEVICE) == Integer.parseInt(etSearch.getText().toString())) {
                                                    oArticle = new Article(Activity_Article.this, oJson.getJSONObject(Constants_Extern.OBJECT_ARTICLE));
                                                    returnFromSearch();
                                                }
                                            } else {
                                                oArticle = null;
                                                returnFromSearch();
                                            }
                                        }
                                    });

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Utility_Toast.show(Activity_Article.this, R.string.device_unknown);
                                removeFragments();
                            }
                        }
                    }
                });
                break;
            case Constants_Intern.MAIN_SEARCH_ARTICLE_TYPE_IMEI:
                cVolley.getResponse(Request.Method.GET, Urls.URL_GET_DEVICE_BY_IMEI + etSearch.getText().toString(), null, new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) {
                        if (cSearchSaved.equals(etSearch.getText().toString())) {
                            if (Volley_Connection.successfulResponse(oJson)) {
                                try {
                                    oDevice = new Device(oJson.getJSONObject(Constants_Extern.OBJECT_DEVICE), Activity_Article.this);
                                    oModel = oDevice.getoModel();
                                    cVolley.getResponse(Request.Method.GET, Urls.URL_GET_ARTICLE + oDevice.getIdDevice(), null, new Interface_VolleyResult() {
                                        @Override
                                        public void onResult(JSONObject oJson) throws JSONException {
                                            if (Volley_Connection.successfulResponse(oJson)) {
                                                oArticle = new Article(Activity_Article.this, oJson.getJSONObject(Constants_Extern.OBJECT_ARTICLE));
                                                returnFromSearch();
                                            } else {
                                                oArticle = null;
                                                returnFromSearch();
                                            }
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            } else {
                                Utility_Toast.show(Activity_Article.this, R.string.imei_unknown);
                                removeFragments();
                                hardReset();
                            }
                        }
                    }
                });
                break;
        }
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------


    // Get

    public Article getArticle() {
        return oArticle;
    }


    // Return

    @Override
    public void returnDisplay(String cTag) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_DISPLAY_EDIT_NEW_ARTICLE:
                hardReset();
            case Constants_Intern.FRAGMENT_DISPLAY_ARTICLE_NOT_FOUND:
                hardReset();
        }
    }

    @Override
    public void returnVerifyArticle(final String cTag, final boolean bPrint) {
        cVolley.getResponse(Request.Method.PUT, Urls.URL_PUT_ARTICLE_BOOKING_INTO+oArticle.getkArticle()+"/1/7", null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (Volley_Connection.successfulResponse(oJson)) {
                    if (bPrint) mPrinter.printDeviceSku(oArticle, oDevice);
                    hardReset();
                    removeFragment(cTag);
                    Utility_Toast.show(Activity_Article.this, R.string.booking_successful);
                } else {
                    Utility_Toast.show(Activity_Article.this, R.string.booking_failed);
                }
            }
        });
    }

    @Override
    public void returnMenu(int tAction, String cTag) {
        switch (tAction) {
            case Constants_Intern.TYPE_ACTION_MENU_PRINT:
                mPrinter.printDeviceSku(oArticle, oDevice);
                hardReset();
                break;
        }
    }


    // Error

    @Override
    public void errorVerifyArticle(String cTag, int tError) {
        switch (tError) {
            case Constants_Intern.TYPE_ERROR_RECLEAN:
            case Constants_Intern.TYPE_ERROR_DEFECT:
                hardReset();
                removeFragment(cTag);
                break;
        }
    }


    // FeatureChanged

    @Override
    public void onFeatureChanged() {
        showFragmentLoadingArticle();
        updateArticle();
    }


    // ClickListener & TextWatcher

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSearchType:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                String[] lChoice = {getString(R.string.id_device), getString(R.string.imei)};
                builder.setItems(lChoice, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                SharedPreferences.edit().putInt(Constants_Intern.SEARCH_ARTICLE_TYPE, Constants_Intern.MAIN_SEARCH_ARTICLE_TYPE_ID_DEVICE).commit();
                                break;
                            case 1:
                                SharedPreferences.edit().putInt(Constants_Intern.SEARCH_ARTICLE_TYPE, Constants_Intern.MAIN_SEARCH_ARTICLE_TYPE_IMEI).commit();
                                break;
                        }
                        hardReset();
                    }
                });
                builder.create().show();
                break;
            case R.id.etSearch:
                break;
            case R.id.ivAction:
                hardReset();
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!editable.toString().equals("")) {
            switch (SharedPreferences.getInt(Constants_Intern.SEARCH_ARTICLE_TYPE, 0)) {
                case Constants_Intern.MAIN_SEARCH_ARTICLE_TYPE_ID_DEVICE:
                    onSearch();
                    break;
                case Constants_Intern.MAIN_SEARCH_ARTICLE_TYPE_IMEI:
                    if (editable.toString().length() == 15) {
                        onSearch();
                    }
                    break;
            }

            // Visibility
            fModel.showAll(false);
            fDevice.showAll(false);
        } else {
            reset();
        }
    }
}
