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
import com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Activity_Wiper;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Menu;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog.Fragment_Dialog_Authentication;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Authentication_Dialog;
import com.example.ericschumacher.bouncer.Objects.Object_Menu;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Volley_Authentication;
import com.example.ericschumacher.bouncer.Zebra.ManagerPrinter;

import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 08.07.2018.
 */

public class Activity_Menu extends AppCompatActivity implements Interface_Authentication_Dialog {

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
    AppCompatRadioButton rbPrinterNine;
    AppCompatRadioButton rbPrinterTen;
    AppCompatRadioButton rbPrinterEleven;
    AppCompatRadioButton rbPrinterTwelve;
    AppCompatRadioButton rbPrinterThirteen;
    AppCompatRadioButton rbPrinterFourteen;
    AppCompatRadioButton rbPrinterFifteen;
    RadioGroup rgOne;
    RadioGroup rgTwo;
    RadioGroup rgThree;
    RadioGroup rgFour;

    Toolbar vToolbar;

    ArrayList<Object_Menu> lMenu = new ArrayList<>();

    // SharedPreferences
    SharedPreferences SharedPreferences;

    // Volley
    Volley_Authentication vAuthentication;

    // Log
    private static String logTitle = "ACTIVITY_MENU";

    // Token
    private String tAuthentication = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Log
        Log.i(logTitle, "onCreate");

        // Volley
        vAuthentication = new Volley_Authentication(this);

        // SharedPreferences
        SharedPreferences = getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0);

        // RecyclerView
        setLayout();
        createMenuData();
        rvMenu.setAdapter(new Adapter_Menu(this, lMenu));
        rvMenu.setLayoutManager(new GridLayoutManager(this, 2));
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Log
        Log.i(logTitle, "onCreate");

        // Token
        if (tAuthentication == null) {
            Fragment_Dialog_Authentication dAuthentication = new Fragment_Dialog_Authentication();
            dAuthentication.show(getSupportFragmentManager(), "FRAGMENT_DIALOG_AUTHENTICATION");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Log
        Log.i(logTitle, "onPause");

        // Token
        tAuthentication = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Log
        Log.i(logTitle, "onActivityResult");

        // Token
        if (requestCode == Constants_Intern.REQUEST_CODE_TOKEN_AUTHENTICATION && resultCode == RESULT_OK) {
            tAuthentication = data.getStringExtra(Constants_Intern.TOKEN_AUTHENTICATION);
        }
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
        rbPrinterNine = findViewById(R.id.rbPrinterNine);
        rbPrinterTen = findViewById(R.id.rbPrinterTen);
        rbPrinterEleven = findViewById(R.id.rbPrinterEleven);
        rbPrinterTwelve = findViewById(R.id.rbPrinterTwelve);
        rbPrinterThirteen = findViewById(R.id.rbPrinterThirteen);
        rbPrinterFourteen = findViewById(R.id.rbPrinterFourteen);
        rbPrinterFifteen = findViewById(R.id.rbPrinterFifteen);
        rgOne = findViewById(R.id.rgOne);
        rgTwo = findViewById(R.id.rgTwo);
        rgThree = findViewById(R.id.rgThree);
        rgFour = findViewById(R.id.rgFour);
        vToolbar = findViewById(R.id.vToolbar);

        rgOne.clearCheck();
        rgTwo.clearCheck();
        rgThree.clearCheck();
        rgFour.clearCheck();
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
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_ONE_IP).equals(Constants_Intern.ID_PRINTER_NONE)) {
            rbPrinterNone.setChecked(true);
        }
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_ONE_IP).equals(Constants_Intern.ID_PRINTER_NINE_IP)) {
            rbPrinterNine.setChecked(true);
        }
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_ONE_IP).equals(Constants_Intern.ID_PRINTER_TEN_IP)) {
            rbPrinterTen.setChecked(true);
        }
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_ONE_IP).equals(Constants_Intern.ID_PRINTER_ELEVEN_IP)) {
            rbPrinterEleven.setChecked(true);
        }
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_ONE_IP).equals(Constants_Intern.ID_PRINTER_TWELVE_IP)) {
            rbPrinterTwelve.setChecked(true);
        }
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_ONE_IP).equals(Constants_Intern.ID_PRINTER_THIRTEEN_IP)) {
            rbPrinterThirteen.setChecked(true);
        }
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_ONE_IP).equals(Constants_Intern.ID_PRINTER_FOURTEEN_IP)) {
            rbPrinterFourteen.setChecked(true);
        }
        if (SharedPreferences.getString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_ONE_IP).equals(Constants_Intern.ID_PRINTER_FIFTEEN_IP)) {
            rbPrinterFifteen.setChecked(true);
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
                    case R.id.rbPrinterFour:
                        anyButtonChecked = rbPrinterFour.isChecked();
                        if (rbPrinterFour.isChecked()) {
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_FOUR_IP).commit();
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_BT, Constants_Intern.ID_PRINTER_FOUR_BT).commit();
                        }
                        break;
                }
                if (anyButtonChecked) {
                    rgTwo.clearCheck();
                    rgThree.clearCheck();
                    rgFour.clearCheck();
                }
            }
        });
        rgTwo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                ManagerPrinter.usePrinter(Activity_Menu.this, true);
                boolean anyButtonChecked = false;
                switch (i) {

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
                    case R.id.rbPrinterSeven:
                        anyButtonChecked = rbPrinterSeven.isChecked();
                        if (rbPrinterSeven.isChecked()) {
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_SEVEN_IP).commit();
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_BT, Constants_Intern.ID_PRINTER_SEVEN_BT).commit();
                        }
                        break;
                    case R.id.rbPrinterEight:
                        anyButtonChecked = rbPrinterEight.isChecked();
                        if (rbPrinterEight.isChecked()) {
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_EIGHT_IP).commit();
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_BT, Constants_Intern.ID_PRINTER_EIGHT_BT).commit();
                        }
                        break;
                }
                if (anyButtonChecked) {
                    rgOne.clearCheck();
                    rgThree.clearCheck();
                    rgFour.clearCheck();
                }
            }
        });
        rgThree.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                ManagerPrinter.usePrinter(Activity_Menu.this, true);
                boolean anyButtonChecked = false;
                switch (i) {
                    case R.id.rbPrinterNone:
                        anyButtonChecked = rbPrinterNone.isChecked();
                        if (rbPrinterNone.isChecked()) SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_NONE).commit();
                        ManagerPrinter.usePrinter(Activity_Menu.this, false);
                        break;
                    case R.id.rbPrinterNine:
                        anyButtonChecked = rbPrinterNine.isChecked();
                        if (rbPrinterNine.isChecked()) {
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_NINE_IP).commit();
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_BT, Constants_Intern.ID_PRINTER_NINE_BT).commit();
                        }
                        break;
                    case R.id.rbPrinterTen:
                        anyButtonChecked = rbPrinterTen.isChecked();
                        if (rbPrinterTen.isChecked()) {
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_TEN_IP).commit();
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_BT, Constants_Intern.ID_PRINTER_TEN_BT).commit();
                        }
                        break;
                    case R.id.rbPrinterEleven:
                        anyButtonChecked = rbPrinterEleven.isChecked();
                        if (rbPrinterEleven.isChecked()) {
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_ELEVEN_IP).commit();
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_BT, Constants_Intern.ID_PRINTER_ELEVEN_BT).commit();
                        }
                        break;
                }
                if (anyButtonChecked) {
                    rgOne.clearCheck();
                    rgTwo.clearCheck();
                    rgFour.clearCheck();
                }
            }
        });
        rgFour.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                ManagerPrinter.usePrinter(Activity_Menu.this, true);
                boolean anyButtonChecked = false;
                switch (i) {
                    case R.id.rbPrinterTwelve:
                        anyButtonChecked = rbPrinterTwelve.isChecked();
                        if (rbPrinterTwelve.isChecked()) {
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_TWELVE_IP).commit();
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_BT, Constants_Intern.ID_PRINTER_TWELVE_BT).commit();
                        }
                        break;
                    case R.id.rbPrinterThirteen:
                        anyButtonChecked = rbPrinterThirteen.isChecked();
                        if (rbPrinterThirteen.isChecked()) {
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_THIRTEEN_IP).commit();
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_BT, Constants_Intern.ID_PRINTER_THIRTEEN_BT).commit();
                        }
                        break;
                    case R.id.rbPrinterFourteen:
                        anyButtonChecked = rbPrinterFourteen.isChecked();
                        if (rbPrinterFourteen.isChecked()) {
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_FOURTEEN_IP).commit();
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_BT, Constants_Intern.ID_PRINTER_FOURTEEN_BT).commit();
                        }
                        break;
                    case R.id.rbPrinterFifteen:
                        anyButtonChecked = rbPrinterFifteen.isChecked();
                        if (rbPrinterFifteen.isChecked()) {
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_IP, Constants_Intern.ID_PRINTER_FIFTEEN_IP).commit();
                            SharedPreferences.edit().putString(Constants_Intern.SELECTED_PRINTER_BT, Constants_Intern.ID_PRINTER_FIFTEEN_BT).commit();
                        }
                        break;
                }
                if (anyButtonChecked) {
                    rgOne.clearCheck();
                    rgTwo.clearCheck();
                    rgThree.clearCheck();
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
        rbPrinterNine.setSupportButtonTintList(colorStateList);
        rbPrinterTen.setSupportButtonTintList(colorStateList);
        rbPrinterEleven.setSupportButtonTintList(colorStateList);
        rbPrinterTwelve.setSupportButtonTintList(colorStateList);
        rbPrinterThirteen.setSupportButtonTintList(colorStateList);
        rbPrinterFourteen.setSupportButtonTintList(colorStateList);
        rbPrinterFifteen.setSupportButtonTintList(colorStateList);

        // Toolbar
        setSupportActionBar(vToolbar);
        getSupportActionBar().setTitle(getString(R.string.mobile_box));
        vToolbar.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.color_primary, null));
        vToolbar.setTitleTextColor(ResourcesCompat.getColor(getResources(), R.color.color_white, null));
    }

    private void createMenuData() {
        lMenu.add(new Object_Menu(getString(R.string.checker), new Intent(this, Activity_Checker.class), R.color.color_choice_positive));
        lMenu.add(new Object_Menu(getString(R.string.wiper), new Intent(this, Activity_Wiper.class), R.color.color_flush));
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

    public String getTokenAuthentication() {
        return tAuthentication;
    }

    @Override
    public void returnTokenAuthentication(String token) {
        tAuthentication = token;
    }
}
