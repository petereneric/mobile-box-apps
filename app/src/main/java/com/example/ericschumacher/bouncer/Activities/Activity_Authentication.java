package com.example.ericschumacher.bouncer.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog.Fragment_Dialog_Authentication;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Authentication_Dialog;
import com.example.ericschumacher.bouncer.Interfaces.Interface_JWT;
import com.example.ericschumacher.bouncer.Volley.JWT;

public class Activity_Authentication extends AppCompatActivity implements Interface_JWT, Interface_Authentication_Dialog {

    // Token
    public String tAuthentication = null;
    public JWT jwt = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Token
        tAuthentication = getIntent().getStringExtra(Constants_Intern.TOKEN_AUTHENTICATION);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Token
        tAuthentication = null;
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Token
        if (tAuthentication == null) {
            Fragment_Dialog_Authentication dAuthentication = new Fragment_Dialog_Authentication();
            dAuthentication.show(getSupportFragmentManager(), "FRAGMENT_DIALOG_AUTHENTICATION");
        }
    }

    @Override
    public void onBackPressed() {
        // Token
        getIntent().putExtra(Constants_Intern.TOKEN_AUTHENTICATION, tAuthentication);
        setResult(RESULT_OK, getIntent());
        super.onBackPressed();
    }

    @Override
    public JWT getJWT() {
        if (tAuthentication != null) {
            if (jwt == null) {
                jwt = new JWT(tAuthentication);
            }
            return jwt;
        } else {
            return null;
        }
    }

    @Override
    public void returnTokenAuthentication(String token) {
        tAuthentication = token;
    }
}
