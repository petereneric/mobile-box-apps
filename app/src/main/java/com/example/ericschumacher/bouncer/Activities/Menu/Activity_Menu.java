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

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Activities.Activity_Bouncer;
import com.example.ericschumacher.bouncer.Activities.Activity_Juicer;
import com.example.ericschumacher.bouncer.Activities.Activity_LKU_Booker;
import com.example.ericschumacher.bouncer.Activities.Activity_LKU_Manager;
import com.example.ericschumacher.bouncer.Activities.Activity_Turing;
import com.example.ericschumacher.bouncer.Activities.Activity_Zwegat;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Menu;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Object_Menu;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;
import com.example.ericschumacher.bouncer.Zebra.ManagerPrinter;
import com.zebra.sdk.comm.Connection;
import com.zebra.sdk.printer.ZebraPrinter;

import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 08.07.2018.
 */

public class Activity_Menu extends AppCompatActivity implements View.OnClickListener {

    RecyclerView rvMenu;
    ImageView ivPrinter;

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

        // Test Connection
        Volley_Connection tConnection = new Volley_Connection(this);
        tConnection.getResponse(Request.Method.GET, "http://www.svp-server.com/svp-gmbh/erp/bouncer/src/api.php/model/all/33", null, new Interface_VolleyResult() {
            @Override
            public void onResult(String result) {
                Log.i("RESULT", result);
            }
        });

        // Printer

        //doConnectionTest();
    }

    private void setLayout() {
        setContentView(R.layout.activity_menu);
        rvMenu = findViewById(R.id.rvMenu);
        ivPrinter = findViewById(R.id.ivPrinter);

        ivPrinter.setOnClickListener(this);

        updateUI();
    }

    private void createMenuData() {
        lMenu.add(new Object_Menu(getString(R.string.activity_name_bouncer), new Intent(this, Activity_Bouncer.class), R.color.color_primary));
        lMenu.add(new Object_Menu(getString(R.string.activity_name_manager), new Intent(this, Activity_LKU_Manager.class), R.color.color_primary_light));
        lMenu.add(new Object_Menu(getString(R.string.activity_name_juicer), new Intent(this, Activity_Juicer.class), R.color.color_primary_dark));
        lMenu.add(new Object_Menu(getString(R.string.activity_name_lku_booker), new Intent(this, Activity_LKU_Booker.class), R.color.color_secondary));
        lMenu.add(new Object_Menu(getString(R.string.activity_name_turing), new Intent(this, Activity_Turing.class), R.color.color_secondary_dark));
        lMenu.add(new Object_Menu(getString(R.string.activity_name_zwegat), new Intent(this, Activity_Zwegat.class), R.color.color_divider));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivPrinter:
                ManagerPrinter.usePrinter(this, !ManagerPrinter.usePrinter(this));
                updateUI();
                break;
        }
    }

    private void updateUI() {
        int kColor = (ManagerPrinter.usePrinter(this)) ? R.color.color_activated : R.color.color_deactivated;
        ivPrinter.setColorFilter(ContextCompat.getColor(this, kColor), android.graphics.PorterDuff.Mode.SRC_IN);
    }
}
