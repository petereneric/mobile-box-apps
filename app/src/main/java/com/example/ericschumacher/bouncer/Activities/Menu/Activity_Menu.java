package com.example.ericschumacher.bouncer.Activities.Menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.RadioGroup;

import com.example.ericschumacher.bouncer.Activities.Manager.Activity_Article;
import com.example.ericschumacher.bouncer.Activities.Manager.Activity_Box;
import com.example.ericschumacher.bouncer.Activities.Manager.Activity_Device;
import com.example.ericschumacher.bouncer.Activities.Manager.Activity_Model;
import com.example.ericschumacher.bouncer.Activities.Previous.Activity_Manager;
import com.example.ericschumacher.bouncer.Activities.Previous.Activity_Turing;
import com.example.ericschumacher.bouncer.Activities.Previous.Activity_Zwegat;
import com.example.ericschumacher.bouncer.Activities.Tools.Activity_Article_Verify;
import com.example.ericschumacher.bouncer.Activities.Tools.Activity_Battery;
import com.example.ericschumacher.bouncer.Activities.Tools.Activity_Bouncer;
import com.example.ericschumacher.bouncer.Activities.Tools.Activity_Checker;
import com.example.ericschumacher.bouncer.Activities.Tools.Activity_Columba;
import com.example.ericschumacher.bouncer.Activities.Tools.Activity_Juicer;
import com.example.ericschumacher.bouncer.Activities.Tools.Activity_Lifter;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Menu;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Object_Menu;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Zebra.ManagerPrinter;

import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 08.07.2018.
 */

public class Activity_Menu extends AppCompatActivity {

    RecyclerView rvMenu;
    AppCompatRadioButton rbPrinterOne;
    AppCompatRadioButton rbPrinterTwo;
    AppCompatRadioButton rbPrinterThree;
    AppCompatRadioButton rbPrinterFour;
    AppCompatRadioButton rbPrinterFive;
    AppCompatRadioButton rbPrinterSix;
    AppCompatRadioButton rbPrinterSeven;
    AppCompatRadioButton rbPrinterEight;
    AppCompatRadioButton rbPrinterNone;
    RadioGroup rgOne;
    RadioGroup rgTwo;
    RadioGroup rgThree;

    Toolbar vToolbar;

    ArrayList<Object_Menu> lMenu = new ArrayList<>();

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
        rbPrinterOne = findViewById(R.id.rbPrinterOne);
        rbPrinterTwo = findViewById(R.id.rbPrinterTwo);
        rbPrinterThree = findViewById(R.id.rbPrinterThree);
        rbPrinterFour = findViewById(R.id.rbPrinterFour);
        rbPrinterFive = findViewById(R.id.rbPrinterFive);
        rbPrinterSix = findViewById(R.id.rbPrinterSix);
        rbPrinterSeven = findViewById(R.id.rbPrinterSeven);
        rbPrinterEight = findViewById(R.id.rbPrinterEight);
        rbPrinterNone = findViewById(R.id.rbPrinterNone);
        rgOne = findViewById(R.id.rgOne);
        rgTwo = findViewById(R.id.rgTwo);
        rgThree = findViewById(R.id.rgThree);
        vToolbar = findViewById(R.id.vToolbar);

        rgOne.clearCheck();
        rgTwo.clearCheck();
        rgThree.clearCheck();
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_ONE_IP).equals(Constants_Intern.ID_PRINTER_ONE_IP)) {
            rbPrinterOne.setChecked(true);
        }
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_ONE_IP).equals(Constants_Intern.ID_PRINTER_TWO_IP)) {
            rbPrinterTwo.setChecked(true);
        }
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_ONE_IP).equals(Constants_Intern.ID_PRINTER_THREE_IP)) {
            rbPrinterThree.setChecked(true);
        }
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_ONE_IP).equals(Constants_Intern.ID_PRINTER_FOUR_IP)) {
            rbPrinterFour.setChecked(true);
        }
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_ONE_IP).equals(Constants_Intern.ID_PRINTER_FIVE_IP)) {
            rbPrinterFive.setChecked(true);
        }
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_ONE_IP).equals(Constants_Intern.ID_PRINTER_SIX_IP)) {
            rbPrinterSix.setChecked(true);
        }
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_ONE_IP).equals(Constants_Intern.ID_PRINTER_SEVEN_IP)) {
            rbPrinterSeven.setChecked(true);
        }
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_ONE_IP).equals(Constants_Intern.ID_PRINTER_EIGHT_IP)) {
            rbPrinterEight.setChecked(true);
        }
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_ONE_IP).equals(Constants_Intern.ID_PRINTER_EIGHT_IP)) {
            rbPrinterEight.setChecked(true);
        }
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_ONE_IP).equals(Constants_Intern.ID_PRINTER_NONE)) {
            rbPrinterNone.setChecked(true);
        }


        rgOne.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                ManagerPrinter.usePrinter(Activity_Menu.this, true);
                boolean anyButtonChecked = false;
                switch (i) {
                    case R.id.rbPrinterOne:
                        anyButtonChecked = rbPrinterOne.isChecked();
                        if (rbPrinterOne.isChecked()) {
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_ONE_IP).commit();
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_BT, Constants_Intern.ID_PRINTER_ONE_BT).commit();
                        }
                        break;
                    case R.id.rbPrinterTwo:
                        anyButtonChecked = rbPrinterTwo.isChecked();
                        if (rbPrinterTwo.isChecked()) {
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_TWO_IP).commit();
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_BT, Constants_Intern.ID_PRINTER_TWO_BT).commit();
                        }
                        break;
                    case R.id.rbPrinterThree:
                        anyButtonChecked = rbPrinterThree.isChecked();
                        if (rbPrinterThree.isChecked()) {
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_THREE_IP).commit();
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_BT, Constants_Intern.ID_PRINTER_THREE_BT).commit();
                        }
                        break;
                }
                if (anyButtonChecked) {
                    rgTwo.clearCheck();
                    rgThree.clearCheck();
                }
            }
        });
        rgTwo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                ManagerPrinter.usePrinter(Activity_Menu.this, true);
                boolean anyButtonChecked = false;
                switch (i) {
                    case R.id.rbPrinterFour:
                        anyButtonChecked = rbPrinterFour.isChecked();
                        if (rbPrinterFour.isChecked()) {
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_FOUR_IP).commit();
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_BT, Constants_Intern.ID_PRINTER_FOUR_BT).commit();
                        }
                        break;
                    case R.id.rbPrinterFive:
                        anyButtonChecked = rbPrinterFive.isChecked();
                        if (rbPrinterFive.isChecked()) {
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_FIVE_IP).commit();
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_BT, Constants_Intern.ID_PRINTER_FIVE_BT).commit();
                        }
                        break;
                    case R.id.rbPrinterSix:
                        anyButtonChecked = rbPrinterSix.isChecked();
                        if (rbPrinterSix.isChecked()) {
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_SIX_IP).commit();
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_BT, Constants_Intern.ID_PRINTER_SIX_BT).commit();
                        }
                        break;
                }
                if (anyButtonChecked) {
                    rgOne.clearCheck();
                    rgThree.clearCheck();
                }
            }
        });
        rgThree.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                ManagerPrinter.usePrinter(Activity_Menu.this, true);
                boolean anyButtonChecked = false;
                switch (i) {
                    case R.id.rbPrinterSeven:
                        anyButtonChecked = rbPrinterSeven.isChecked();
                        if (rbPrinterSeven.isChecked()) {
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_SEVEN_IP).commit();
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_BT, Constants_Intern.ID_PRINTER_SEVEN_BT).commit();
                        }
                        break;
                    case R.id.rbPrinterEight:
                        anyButtonChecked = rbPrinterSeven.isChecked();
                        if (rbPrinterEight.isChecked()) {
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_EIGHT_IP).commit();
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_BT, Constants_Intern.ID_PRINTER_EIGHT_BT).commit();
                            Utility_Toast.showString(Activity_Menu.this, SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_ONE_IP));
                        }
                        break;
                    case R.id.rbPrinterNone:
                        anyButtonChecked = rbPrinterSeven.isChecked();
                        if (rbPrinterNone.isChecked())SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_NONE).commit();
                        ManagerPrinter.usePrinter(Activity_Menu.this, false);
                        break;
                }
                if (anyButtonChecked) {
                    rgOne.clearCheck();
                    rgTwo.clearCheck();
                }
            }
        });



        // Color

        ColorStateList colorStateList = new ColorStateList(new int[][]{new int[]{-android.R.attr.state_checked}, new int[]{android.R.attr.state_checked}}, new int[]{R.color.color_primary, R.color.color_primary,});
        rbPrinterOne.setSupportButtonTintList(colorStateList);
        rbPrinterTwo.setSupportButtonTintList(colorStateList);
        rbPrinterThree.setSupportButtonTintList(colorStateList);
        rbPrinterFour.setSupportButtonTintList(colorStateList);
        rbPrinterFive.setSupportButtonTintList(colorStateList);
        rbPrinterSix.setSupportButtonTintList(colorStateList);
        rbPrinterSeven.setSupportButtonTintList(colorStateList);
        rbPrinterEight.setSupportButtonTintList(colorStateList);
        rbPrinterNone.setSupportButtonTintList(colorStateList);

        // Toolbar
        setSupportActionBar(vToolbar);
        getSupportActionBar().setTitle(getString(R.string.mobile_box));
        vToolbar.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.color_primary, null));
        vToolbar.setTitleTextColor(ResourcesCompat.getColor(getResources(), R.color.color_white, null));
    }

    private void createMenuData() {
        lMenu.add(new Object_Menu(getString(R.string.checker), new Intent(this, Activity_Checker.class), R.color.color_choice_positive));
        lMenu.add(new Object_Menu(getString(R.string.activity_box_manager), new Intent(this, Activity_Box.class), R.color.color_orange));
        lMenu.add(new Object_Menu(getString(R.string.model_manager), new Intent(this, Activity_Model.class), R.color.color_defect_reuse));
        lMenu.add(new Object_Menu(getString(R.string.device_manager), new Intent(this, Activity_Device.class), R.color.color_intact_reuse));
        lMenu.add(new Object_Menu(getString(R.string.bouncer), new Intent(this, Activity_Bouncer.class), R.color.color_primary));
        lMenu.add(new Object_Menu(getString(R.string.juicer), new Intent(this, Activity_Juicer.class), R.color.color_grey));
        lMenu.add(new Object_Menu(getString(R.string.activity_lifter_stock_lku), new Intent(this, Activity_Lifter.class), R.color.color_orange));
        lMenu.add(new Object_Menu(getString(R.string.article_manager), new Intent(this, Activity_Article.class), R.color.color_intact_reuse_dark));
        lMenu.add(new Object_Menu(getString(R.string.activity_verify_article), new Intent(this, Activity_Article_Verify.class), R.color.color_defect_reuse_light));
        lMenu.add(new Object_Menu(getString(R.string.activity_name_turing), new Intent(this, Activity_Turing.class), R.color.color_primary_turing));
        lMenu.add(new Object_Menu(getString(R.string.activity_name_zwegat), new Intent(this, Activity_Zwegat.class), R.color.color_divider));
        lMenu.add(new Object_Menu(getString(R.string.activity_battery), new Intent(this, Activity_Battery.class), R.color.color_primary));
        lMenu.add(new Object_Menu(getString(R.string.activity_name_manager), new Intent(this, Activity_Manager.class), R.color.color_primary));
        lMenu.add(new Object_Menu(getString(R.string.activity_name_columba), new Intent(this, Activity_Columba.class), R.color.color_green));
    }
}
