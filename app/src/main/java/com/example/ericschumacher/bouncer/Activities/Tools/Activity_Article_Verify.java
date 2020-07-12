package com.example.ericschumacher.bouncer.Activities.Tools;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Activities.Manager.Activity_Article;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Display.Fragment_Display;
import com.example.ericschumacher.bouncer.Fragments.Others.Fragment_Verify_Article;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Article;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

public class Activity_Article_Verify extends Activity_Article {

    // Fragments

    public void initiateFragments() {
        super.initiateFragments();
        fArticle.lMenu.setVisibility(View.GONE);
    }

    public void removeFragments() {
        super.removeFragments();
        if (getSupportFragmentManager().findFragmentByTag(Constants_Intern.FRAGMENT_VERIFY_ARTICLE) != null) {
            removeFragment(Constants_Intern.FRAGMENT_VERIFY_ARTICLE);
        }
    }


    // Layout

    public void setLayout() {
        super.setLayout();


        // Toolbar
        getSupportActionBar().setTitle(getString(R.string.activity_verify_article));
    }




    // Update

    public void updateLayout() {
        super.updateLayout();

        if (oArticle != null) {
            showFragment(new Fragment_Verify_Article(), null, Constants_Intern.FRAGMENT_VERIFY_ARTICLE, null);
        }
    }

    public void updateArticle() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                cVolley.getResponse(Request.Method.GET, Urls.URL_GET_ARTICLE + oDevice.getIdDevice(), null, new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) throws JSONException {
                        if (Volley_Connection.successfulResponse(oJson)) {
                            if (!etSearch.getText().toString().equals("") && oJson.getInt(Constants_Extern.ID_DEVICE) == Integer.parseInt(etSearch.getText().toString())) {
                                oArticle = new Article(Activity_Article_Verify.this, oJson.getJSONObject(Constants_Extern.OBJECT_ARTICLE));
                                getSupportFragmentManager().beginTransaction().show(fArticle).commit();
                                fArticle.updateLayout();
                                Log.i("jOOOO", "jOOO!");
                            }
                        } else {
                            oArticle = null;
                            if (oDevice != null) {
                                Bundle bundle = new Bundle();
                                bundle.putString(Constants_Intern.TEXT, getString(R.string.article_not_found));
                                showFragment(new Fragment_Display(), bundle, Constants_Intern.FRAGMENT_DISPLAY_ARTICLE_NOT_FOUND, null);
                            }
                        }
                    }
                });
            }
        }, 2000);

    }



    // Base & Reset

    public void base(Boolean bKeyboard) {
        setKeyboard(bKeyboard);
        updateLayout();
    }


}
