package com.example.ericschumacher.bouncer.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.ericschumacher.bouncer.Adapter.Adapter_Menu;
import com.example.ericschumacher.bouncer.Objects.Object_Menu;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 08.07.2018.
 */

public class Activity_Menu extends AppCompatActivity {

    RecyclerView rvMenu;

    ArrayList<Object_Menu> lMenu = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        // RecyclerView
        setLayout();
        createMenuData();
        rvMenu.setAdapter(new Adapter_Menu(this, lMenu));
        rvMenu.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void setLayout() {
        setContentView(R.layout.activity_menu);
        rvMenu = findViewById(R.id.rvMenu);
    }

    private void createMenuData() {
        lMenu.add(new Object_Menu(getString(R.string.activity_name_bouncer), new Intent(this, Activity_Bouncer.class), R.color.color_primary));
        lMenu.add(new Object_Menu(getString(R.string.activity_name_manager), new Intent(this, Activity_Manager.class), R.color.color_primary_light));
        lMenu.add(new Object_Menu(getString(R.string.activity_name_juicer), new Intent(this, Activity_Juicer.class), R.color.color_primary_dark));
    }
}