package com.example.ericschumacher.bouncer.Activities.Menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.ericschumacher.bouncer.Activities.Activity_Article;
import com.example.ericschumacher.bouncer.Activities.Activity_Battery;
import com.example.ericschumacher.bouncer.Activities.Activity_Bouncer;
import com.example.ericschumacher.bouncer.Activities.Activity_Bouncer_New;
import com.example.ericschumacher.bouncer.Activities.Activity_Device_New;
import com.example.ericschumacher.bouncer.Activities.Activity_Juicer;
import com.example.ericschumacher.bouncer.Activities.Activity_Juicer_New;
import com.example.ericschumacher.bouncer.Activities.Activity_LKU_Booker;
import com.example.ericschumacher.bouncer.Activities.Activity_Lifter_LkuStock;
import com.example.ericschumacher.bouncer.Activities.Activity_Manager;
import com.example.ericschumacher.bouncer.Activities.Activity_Model;
import com.example.ericschumacher.bouncer.Activities.Activity_Turing;
import com.example.ericschumacher.bouncer.Activities.Activity_Article_Verify;
import com.example.ericschumacher.bouncer.Activities.Activity_Zwegat;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Menu;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Object_Menu;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Zebra.ManagerPrinter;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.printer.ZebraPrinter;

import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 08.07.2018.
 */

public class Activity_Menu extends AppCompatActivity implements View.OnClickListener  {

    RecyclerView rvMenu;
    ImageView ivPrinter;
    ImageView ivCamera;
    RadioGroup rgPrinter;
    RadioButton rbPrinterOne;
    RadioButton rbPrinterTwo;
    RadioButton rbPrinterThree;
    RadioButton rbPrinterFour;
    RadioButton rbPrinterFive;
    RadioButton rbPrinterSix;

    ArrayList<Object_Menu> lMenu = new ArrayList<>();

    // Printer
    private ZebraPrinter printer;
    private Connection connection;

    // SharedPreferences
    SharedPreferences SharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SharedPreferences
        SharedPreferences = getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0);

        // RecyclerView
        setLayout();
        createMenuData();
        rvMenu.setAdapter(new Adapter_Menu(this, lMenu));
        rvMenu.setLayoutManager(new GridLayoutManager(this, 2));

        // Test

        // Printer

        //doConnectionTest();
    }

    public static void largeLog(String tag, String content) {
        if (content.length() > 4000) {
            Log.d(tag, content.substring(0, 4000));
            largeLog(tag, content.substring(4000));
        } else {
            Log.d(tag, content);
        }
    }

    private void setLayout() {
        setContentView(R.layout.activity_menu);
        rvMenu = findViewById(R.id.rvMenu);
        ivPrinter = findViewById(R.id.ivPrinter);
        ivCamera = findViewById(R.id.ivCamera);

        ivPrinter.setOnClickListener(this);
        ivCamera.setOnClickListener(this);
        rbPrinterOne = findViewById(R.id.rbPrinterOne);
        rbPrinterTwo = findViewById(R.id.rbPrinterTwo);
        rbPrinterThree = findViewById(R.id.rbPrinterThree);
        rbPrinterFour = findViewById(R.id.rbPrinterFour);
        rbPrinterFive = findViewById(R.id.rbPrinterFive);
        rbPrinterSix = findViewById(R.id.rbPrinterSix);

        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER, Constants_Intern.ID_PRINTER_ONE).equals(Constants_Intern.ID_PRINTER_ONE)) {
            rbPrinterOne.setChecked(true);
        }
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER, Constants_Intern.ID_PRINTER_ONE).equals(Constants_Intern.ID_PRINTER_TWO)) {
            rbPrinterTwo.setChecked(true);
        }
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER, Constants_Intern.ID_PRINTER_ONE).equals(Constants_Intern.ID_PRINTER_THREE)) {
            rbPrinterThree.setChecked(true);
        }
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER, Constants_Intern.ID_PRINTER_ONE).equals(Constants_Intern.ID_PRINTER_FOUR)) {
            rbPrinterFour.setChecked(true);
        }
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER, Constants_Intern.ID_PRINTER_ONE).equals(Constants_Intern.ID_PRINTER_FIVE)) {
            rbPrinterFive.setChecked(true);
        }
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER, Constants_Intern.ID_PRINTER_ONE).equals(Constants_Intern.ID_PRINTER_SIX)) {
            rbPrinterSix.setChecked(true);
        }

        updateUI();
    }

    private void createMenuData() {
        lMenu.add(new Object_Menu(getString(R.string.model_manager), new Intent(this, Activity_Model.class), R.color.color_defect_reuse));
        lMenu.add(new Object_Menu(getString(R.string.device_manager), new Intent(this, Activity_Device_New.class), R.color.color_intact_reuse));
        lMenu.add(new Object_Menu(getString(R.string.activity_lifter_stock_lku), new Intent(this, Activity_Lifter_LkuStock.class), R.color.color_orange));
        lMenu.add(new Object_Menu(getString(R.string.activity_verify_article), new Intent(this, Activity_Article_Verify.class), R.color.color_defect_reuse_light));
        lMenu.add(new Object_Menu(getString(R.string.article_manager), new Intent(this, Activity_Article.class), R.color.color_intact_reuse_dark));
        lMenu.add(new Object_Menu(getString(R.string.bouncer_new), new Intent(this, Activity_Bouncer_New.class), R.color.color_primary));
        lMenu.add(new Object_Menu(getString(R.string.juicer), new Intent(this, Activity_Juicer_New.class), R.color.color_grey));
        lMenu.add(new Object_Menu(getString(R.string.activity_name_bouncer), new Intent(this, Activity_Bouncer.class), R.color.color_primary));
        lMenu.add(new Object_Menu(getString(R.string.activity_name_juicer), new Intent(this, Activity_Juicer.class), R.color.color_primary_dark));
        lMenu.add(new Object_Menu(getString(R.string.activity_name_lku_booker), new Intent(this, Activity_LKU_Booker.class), R.color.color_secondary));
        lMenu.add(new Object_Menu(getString(R.string.activity_name_turing), new Intent(this, Activity_Turing.class), R.color.color_primary_turing));
        lMenu.add(new Object_Menu(getString(R.string.activity_name_zwegat), new Intent(this, Activity_Zwegat.class), R.color.color_divider));
        lMenu.add(new Object_Menu(getString(R.string.activity_battery), new Intent(this, Activity_Battery.class), R.color.color_primary));
        lMenu.add(new Object_Menu(getString(R.string.activity_name_manager), new Intent(this, Activity_Manager.class), R.color.color_primary));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivPrinter:
                ManagerPrinter.usePrinter(this, !ManagerPrinter.usePrinter(this));
                updateUI();
                break;
            case R.id.ivCamera:
                SharedPreferences.edit().putBoolean(Constants_Intern.USE_CAMERA_MODEL_COLOR, (!SharedPreferences.getBoolean(Constants_Intern.USE_CAMERA_MODEL_COLOR, false))).commit();
                updateUI();
                break;
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rbPrinterOne:
                if (checked)
                    SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER, Constants_Intern.ID_PRINTER_ONE).commit();
                    break;
            case R.id.rbPrinterTwo:
                if (checked)
                    SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER, Constants_Intern.ID_PRINTER_TWO).commit();
                    break;
            case R.id.rbPrinterThree:
                if (checked)
                    SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER, Constants_Intern.ID_PRINTER_THREE).commit();
                break;
            case R.id.rbPrinterFour:
                if (checked)
                    SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER, Constants_Intern.ID_PRINTER_FOUR).commit();
                break;
            case R.id.rbPrinterFive:
                if (checked)
                    SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER, Constants_Intern.ID_PRINTER_FIVE).commit();
                break;
            case R.id.rbPrinterSix:
                if (checked)
                    SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER, Constants_Intern.ID_PRINTER_SIX).commit();
                break;
        }
    }

    private void updateUI() {
        int kColor = (ManagerPrinter.usePrinter(this)) ? R.color.color_activated : R.color.color_deactivated;
        ivPrinter.setColorFilter(ContextCompat.getColor(this, kColor), android.graphics.PorterDuff.Mode.SRC_IN);
        int kColorCamera = SharedPreferences.getBoolean(Constants_Intern.USE_CAMERA_MODEL_COLOR, false) ? R.color.color_activated : R.color.color_deactivated;
        Log.i("BBB", SharedPreferences.getBoolean(Constants_Intern.USE_CAMERA_MODEL_COLOR, false) ? "true" : "false");
        ivCamera.setColorFilter(ContextCompat.getColor(this, kColorCamera), android.graphics.PorterDuff.Mode.SRC_IN);
    }
}
