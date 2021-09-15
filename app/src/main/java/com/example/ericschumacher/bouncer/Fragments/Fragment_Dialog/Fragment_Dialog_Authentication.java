package com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.res.ResourcesCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Authentication_Dialog;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyAuthentication;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_Status;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Volley_Authentication;

public class Fragment_Dialog_Authentication extends DialogFragment implements View.OnClickListener {

    // Layout
    View vLayout;
    TextView tvInfo;
    TextView tvDelete;
    ImageView ivCodeOne;
    ImageView ivCodeTwo;
    ImageView ivCodeThree;
    ImageView ivCodeFour;
    ImageView ivCodeFive;
    ImageView ivCodeSix;
    Button bKeyboardZero;
    Button bKeyboardOne;
    Button bKeyboardTwo;
    Button bKeyboardThree;
    Button bKeyboardFour;
    Button bKeyboardFive;
    Button bKeyboardSix;
    Button bKeyboardSeven;
    Button bKeyboardEight;
    Button bKeyboardNine;

    // Interface
    Interface_Authentication_Dialog iAuthentication;

    // Data
    String cCode = "";

    // Volley
    Volley_Authentication vAuthentication;

    // Vibrator
    Vibrator mVibrator;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {

        // Interface
        iAuthentication = (Interface_Authentication_Dialog)getActivity();

        // Vibrator
        mVibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);

        // Volley
        vAuthentication = new Volley_Authentication(getActivity());

        // Layout
        setLayout(inflater, container);
        base();

        return vLayout;
    }

    // Layout
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        // Initiate
        vLayout = inflater.inflate(R.layout.fragment_dialog_authentication, container, false);
        tvInfo = vLayout.findViewById(R.id.tvInfo);
        tvDelete = vLayout.findViewById(R.id.tvDelete);
        ivCodeOne = vLayout.findViewById(R.id.ivCodeOne);
        ivCodeTwo = vLayout.findViewById(R.id.ivCodeTwo);
        ivCodeThree = vLayout.findViewById(R.id.ivCodeThree);
        ivCodeFour = vLayout.findViewById(R.id.ivCodeFour);
        ivCodeFive = vLayout.findViewById(R.id.ivCodeFive);
        ivCodeSix = vLayout.findViewById(R.id.ivCodeSix);
        bKeyboardZero = vLayout.findViewById(R.id.bKeyboardZero);
        bKeyboardOne = vLayout.findViewById(R.id.bKeyboardOne);
        bKeyboardTwo = vLayout.findViewById(R.id.bKeyboardTwo);
        bKeyboardThree = vLayout.findViewById(R.id.bKeyboardThree);
        bKeyboardFour = vLayout.findViewById(R.id.bKeyboardFour);
        bKeyboardFive = vLayout.findViewById(R.id.bKeyboardFive);
        bKeyboardSix = vLayout.findViewById(R.id.bKeyboardSix);
        bKeyboardSeven = vLayout.findViewById(R.id.bKeyboardSeven);
        bKeyboardEight = vLayout.findViewById(R.id.bKeyboardEight);
        bKeyboardNine = vLayout.findViewById(R.id.bKeyboardNine);

        // ClickListener
        bKeyboardZero.setOnClickListener(this);
        bKeyboardOne.setOnClickListener(this);
        bKeyboardTwo.setOnClickListener(this);
        bKeyboardThree.setOnClickListener(this);
        bKeyboardFour.setOnClickListener(this);
        bKeyboardFive.setOnClickListener(this);
        bKeyboardSix.setOnClickListener(this);
        bKeyboardSeven.setOnClickListener(this);
        bKeyboardEight.setOnClickListener(this);
        bKeyboardNine.setOnClickListener(this);
        tvDelete.setOnClickListener(this);

        // Dialog
        Window window = getDialog().getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bKeyboardZero:
                cCode = cCode + "0";
                base();
                break;
            case R.id.bKeyboardOne:
                cCode = cCode + "1";
                base();
                break;
            case R.id.bKeyboardTwo:
                cCode = cCode + "2";
                base();
                break;
            case R.id.bKeyboardThree:
                cCode = cCode + "3";
                base();
                break;
            case R.id.bKeyboardFour:
                cCode = cCode + "4";
                base();
                break;
            case R.id.bKeyboardFive:
                cCode = cCode + "5";
                base();
                break;
            case R.id.bKeyboardSix:
                cCode = cCode + "6";
                base();
                break;
            case R.id.bKeyboardSeven:
                cCode = cCode + "7";
                base();
                break;
            case R.id.bKeyboardEight:
                cCode = cCode + "8";
                base();
                break;
            case R.id.bKeyboardNine:
                cCode = cCode + "9";
                base();
                break;
            case R.id.tvDelete:
                cCode = cCode.substring(0, cCode.length()-1);
                base();
                break;
        }
    }

    private void base() {
        tvInfo.setVisibility(View.GONE);
        updateLayout();
            if (cCode.length() == 6) {
                vAuthentication.login(cCode, new Interface_VolleyCallback_Status() {
                    @Override
                    public void returnStatus(int status) {
                        Log.i("Status", "" + status);
                        if (status == 200) {
                            Log.i("Login", "Success");
                            dismiss();
                            return;
                        }

                        if (status == 401) {
                            vibrate(1000);
                            Log.i("Login", "Failure");
                            cCode = "";
                            tvInfo.setText(getString(R.string.code_does_not_exist));
                            tvInfo.setVisibility(View.VISIBLE);
                            updateLayout();
                        }
                    }
                }, new Interface_VolleyAuthentication() {
                    @Override
                    public void returnToken(String token) {
                        iAuthentication.returnTokenAuthentication(token);
                    }
                });
            }
    }

    private void updateLayout() {
        if (cCode.length() > 0) {
            tvDelete.setVisibility(View.VISIBLE);
        } else {
            tvDelete.setVisibility(View.INVISIBLE);
        }
        switch (cCode.length()) {
            case 0:
                ivCodeOne.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_stroke_primary_authentication_code, null));
                ivCodeTwo.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_stroke_primary_authentication_code, null));
                ivCodeThree.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_stroke_primary_authentication_code, null));
                ivCodeFour.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_stroke_primary_authentication_code, null));
                ivCodeFive.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_stroke_primary_authentication_code, null));
                ivCodeSix.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_stroke_primary_authentication_code, null));
                break;
            case 1:
                ivCodeOne.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_solid_primary_authentication_code, null));
                ivCodeTwo.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_stroke_primary_authentication_code, null));
                ivCodeThree.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_stroke_primary_authentication_code, null));
                ivCodeFour.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_stroke_primary_authentication_code, null));
                ivCodeFive.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_stroke_primary_authentication_code, null));
                ivCodeSix.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_stroke_primary_authentication_code, null));
                break;
            case 2:
                ivCodeOne.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_solid_primary_authentication_code, null));
                ivCodeTwo.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_solid_primary_authentication_code, null));
                ivCodeThree.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_stroke_primary_authentication_code, null));
                ivCodeFour.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_stroke_primary_authentication_code, null));
                ivCodeFive.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_stroke_primary_authentication_code, null));
                ivCodeSix.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_stroke_primary_authentication_code, null));
                break;
            case 3:
                ivCodeOne.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_solid_primary_authentication_code, null));
                ivCodeTwo.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_solid_primary_authentication_code, null));
                ivCodeThree.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_solid_primary_authentication_code, null));
                ivCodeFour.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_stroke_primary_authentication_code, null));
                ivCodeFive.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_stroke_primary_authentication_code, null));
                ivCodeSix.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_stroke_primary_authentication_code, null));
                break;
            case 4:
                ivCodeOne.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_solid_primary_authentication_code, null));
                ivCodeTwo.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_solid_primary_authentication_code, null));
                ivCodeThree.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_solid_primary_authentication_code, null));
                ivCodeFour.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_solid_primary_authentication_code, null));
                ivCodeFive.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_stroke_primary_authentication_code, null));
                ivCodeSix.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_stroke_primary_authentication_code, null));
                break;
            case 5:
                ivCodeOne.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_solid_primary_authentication_code, null));
                ivCodeTwo.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_solid_primary_authentication_code, null));
                ivCodeThree.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_solid_primary_authentication_code, null));
                ivCodeFour.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_solid_primary_authentication_code, null));
                ivCodeFive.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_solid_primary_authentication_code, null));
                ivCodeSix.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_stroke_primary_authentication_code, null));
                break;
            case 6:
                ivCodeOne.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_solid_primary_authentication_code, null));
                ivCodeTwo.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_solid_primary_authentication_code, null));
                ivCodeThree.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_solid_primary_authentication_code, null));
                ivCodeFour.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_solid_primary_authentication_code, null));
                ivCodeFive.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_solid_primary_authentication_code, null));
                ivCodeSix.setBackground(ResourcesCompat.getDrawable(getResources(), R.drawable.background_round_solid_primary_authentication_code, null));
                break;
        }
    }

    private void vibrate(int duration) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mVibrator.vibrate(VibrationEffect.createOneShot(duration, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            //deprecated in API 26
            mVibrator.vibrate(duration);
        }
    }
}
