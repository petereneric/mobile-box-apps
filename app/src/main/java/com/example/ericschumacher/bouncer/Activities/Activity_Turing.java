package com.example.ericschumacher.bouncer.Activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Turing;
import com.example.ericschumacher.bouncer.Interfaces.Interface_List;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_IdModelColorShape;
import com.example.ericschumacher.bouncer.Objects.Object_Id_Model_Color_Shape;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

import java.util.ArrayList;

public class Activity_Turing extends AppCompatActivity implements Interface_List {

    // Layout
    RecyclerView rvIdsNotConnected;

    // Values
    Utility_Network uNetwork;
    Adapter_Turing aTuring;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Values
        uNetwork = new Utility_Network(this);

        // Layout
        setContentView(R.layout.activity_turing);
        rvIdsNotConnected = findViewById(R.id.rvIdsNotConnected);
        rvIdsNotConnected.setLayoutManager(new LinearLayoutManager(this));
        uNetwork.updateRpd(new Interface_VolleyCallback_ArrayList_IdModelColorShape() {
            @Override
            public void onSuccess(ArrayList<Object_Id_Model_Color_Shape> lIdModelColorShape) {
                aTuring = new Adapter_Turing(Activity_Turing.this, lIdModelColorShape);
                rvIdsNotConnected.setAdapter(aTuring);
            }

            @Override
            public void onFailure() {

            }
        });
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        aTuring.removeItem(viewHolder);
                    }
                };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvIdsNotConnected);

    }

    @Override
    public void onClick(int position, String tagFragment) {

    }

    @Override
    public void onSwipeLeft(int position, String tagFragment) {

    }

    @Override
    public void onSwipeRight(int position, String tagFragment) {

    }
}
