package com.example.ericschumacher.bouncer.Fragments;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Activity_List;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_List;
import com.example.ericschumacher.bouncer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Fragment_List_New extends Fragment implements Interface_Fragment_List, Interface_Click {

    // Layout
    View Layout;
    RecyclerView RecyclerView;

    // Data
    JSONArray jsonArrayData = new JSONArray();

    // Adapter
    Adapter_List aList;

    // Interface
    Interface_Activity_List iActivityList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Interface
        iActivityList = (Interface_Activity_List)getActivity();

        // Layout
        setLayout(inflater, container);

        // Adapter
        Log.i("TAGG", getTag());

        return Layout;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Data
        iActivityList.getData(getTag(), this);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        Layout = inflater.inflate(R.layout.fragment_list_new, container, false);

        RecyclerView = Layout.findViewById(R.id.vRecyclerView);
        RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, iActivityList.getSwipeBehaviour(getTag())) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (viewHolder.getAdapterPosition() > 0) {
                    switch (direction) {
                        case ItemTouchHelper.LEFT:

                            iActivityList.onSwipeLeft(viewHolder.getAdapterPosition(), getTag(), getJsonObject(viewHolder.getAdapterPosition()), (com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_List)Fragment_List_New.this);

                            break;
                        case ItemTouchHelper.RIGHT:
                            iActivityList.onSwipeRight(viewHolder.getAdapterPosition(), getTag(), getJsonObject(viewHolder.getAdapterPosition()), (com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_List)Fragment_List_New.this);
                            break;
                    }
                }
            }

            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final View llForeground = ((Adapter_List.ViewHolder_List) viewHolder).llForeground;
                final TextView tvBackground = ((Adapter_List.ViewHolder_List) viewHolder).tvBackground;
                if (dX > 0) {
                    iActivityList.setViewSwipeRight(getTag(), tvBackground);
                } else {
                    iActivityList.setViewSwipeLeft(getTag(), tvBackground);
                }
                getDefaultUIUtil().onDraw(c, recyclerView, llForeground, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                        RecyclerView.ViewHolder viewHolder, float dX, float dY,
                                        int actionState, boolean isCurrentlyActive) {
                final View foregroundView = ((Adapter_List.ViewHolder_List) viewHolder).llForeground;
                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                final View foregroundView = ((Adapter_List.ViewHolder_List) viewHolder).llForeground;
                getDefaultUIUtil().clearView(foregroundView);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(RecyclerView);

    }


    @Override
    public void onClick(int position) {
        iActivityList.onClick(position, getTag(), getJsonObject(position));
    }

    public void update() {
        aList.notifyDataSetChanged();
    }

    @Override
    public void remove(int position) {
        jsonArrayData.remove(getPosition(position));
        Log.i("Joooafj", Integer.toString(position));
        aList.notifyDataSetChanged();
    }

    @Override
    public void setData(JSONArray jsonArrayData) {
        this.jsonArrayData = jsonArrayData;
        aList = new Adapter_List(getActivity(), iActivityList.bHeader(getTag()), this, this.jsonArrayData, iActivityList.getAnn(getTag()));
        RecyclerView.setAdapter(aList);
    }

    public JSONObject getJsonObject(int position) {
        JSONObject jsonObject = null;
        try {
            if (iActivityList.bHeader(getTag())) {
                jsonObject = jsonArrayData.getJSONObject(position - 1);
            } else {
                jsonObject = jsonArrayData.getJSONObject(position);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public int getPosition(int position) {
        if (iActivityList.bHeader(getTag())) {
            return position-1;
        } else {
            return position;
        }
    }


}

