package com.example.ericschumacher.bouncer.Fragments.Old;

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

import com.example.ericschumacher.bouncer.Adapter.Table.Adapter_Table;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Activity_List;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_List;
import com.example.ericschumacher.bouncer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Fragment_List_Two extends Fragment implements Interface_Fragment_List, Interface_Click {

    // vLayout
    View vLayout;
    RecyclerView rvList;

    // Data
    JSONArray jsonArrayData = new JSONArray();

    // Adapter
    Adapter_Table aList;

    // Interface
    Interface_Activity_List iActivityList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Interface
        iActivityList = (Interface_Activity_List)getActivity();

        // vLayout
        setLayout(inflater, container);

        // Adapter
        Log.i("TAGG", getTag());

        return vLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Data
        iActivityList.getData(getTag(), this);
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(R.layout.fragment_table_new, container, false);

        rvList = vLayout.findViewById(R.id.rvList);
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));

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

                            iActivityList.onSwipeLeft(viewHolder.getAdapterPosition(), getTag(), getJsonObject(viewHolder.getAdapterPosition()), (com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_List) Fragment_List_Two.this);

                            break;
                        case ItemTouchHelper.RIGHT:
                            iActivityList.onSwipeRight(viewHolder.getAdapterPosition(), getTag(), getJsonObject(viewHolder.getAdapterPosition()), (com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_List) Fragment_List_Two.this);
                            break;
                    }
                }
            }

            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final View llForeground = ((Adapter_Table.ViewHolder_List) viewHolder).llForeground;
                final TextView tvBackground = ((Adapter_Table.ViewHolder_List) viewHolder).tvBackground;
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
                final View foregroundView = ((Adapter_Table.ViewHolder_List) viewHolder).llForeground;
                getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                final View foregroundView = ((Adapter_Table.ViewHolder_List) viewHolder).llForeground;
                getDefaultUIUtil().clearView(foregroundView);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvList);

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
        //aList = new Adapter_Table(getActivity(), iActivityList.bHeader(getTag()), this, this.jsonArrayData, iActivityList.getAnn(getTag()));
        rvList.setAdapter(aList);
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

