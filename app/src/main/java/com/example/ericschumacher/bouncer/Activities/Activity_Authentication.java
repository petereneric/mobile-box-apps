package com.example.ericschumacher.bouncer.Activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog.Fragment_Dialog_Authentication;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Authentication_Dialog;
import com.example.ericschumacher.bouncer.Interfaces.Interface_JWT;
import com.example.ericschumacher.bouncer.Volley.JWT;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class Activity_Authentication extends AppCompatActivity implements Interface_JWT, Interface_Authentication_Dialog {

    // Token
    public String tAuthentication = null;
    public JWT jwt = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        handleSSLHandshake();
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Token
        if (requestCode == Constants_Intern.REQUEST_CODE_TOKEN_AUTHENTICATION && resultCode == RESULT_OK) {
            tAuthentication = data.getStringExtra(Constants_Intern.TOKEN_AUTHENTICATION);
        }
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

    public String getTokenAuthentication() {
        return tAuthentication;
    }

    /**
     * Enables https connections
     */
    @SuppressLint("TrulyRandom")
    public static void handleSSLHandshake() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] certs, String authType) {
                }
            }};

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String arg0, SSLSession arg1) {
                    return true;
                }
            });
        } catch (Exception ignored) {
        }
    }
}
