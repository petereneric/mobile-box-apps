package com.example.ericschumacher.bouncer.Fragments.Fragment_Turing;

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
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_List;
import com.example.ericschumacher.bouncer.Interfaces.Interface_List;
import com.example.ericschumacher.bouncer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Fragment_List extends Fragment implements Interface_Click, Interface_Fragment_List {

    // Layout
    View Layout;
    RecyclerView RecyclerView;
 
    // Data
    JSONArray lJson;

    // Adapter
    Adapter_Table aList;

    // Interface
    Interface_List iList;

    // cTag
    String Tag;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Layout
        setLayout(inflater, container);

        // Interface
        iList = (Interface_List) getActivity();

        // Data
        Tag = getArguments().getString(Constants_Intern.TAG);
        lJson = iList.getData(Tag);
        if (lJson != null) {
            Log.i("AllGood", "jo");
        } else {
            Log.i("nogood", "ne");
        }

        // Adapter
        //aList = new Adapter_Table(getActivity(), true, this, lJson, iList.getAnn(Tag));

        RecyclerView.setAdapter(aList);


        return Layout;
    }

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        Layout = inflater.inflate(R.layout.fragment_list, container, false);
        RecyclerView = Layout.findViewById(R.id.RecyclerView);

        RecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT | ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (viewHolder.getAdapterPosition() > 0) {
                    try {
                        switch (direction) {
                            case ItemTouchHelper.LEFT:

                                iList.onSwipeLeft(viewHolder.getAdapterPosition(), getObjectJson(viewHolder.getAdapterPosition()), Tag, Fragment_List.this);

                                break;
                            case ItemTouchHelper.RIGHT:
                                iList.onSwipeRight(viewHolder.getAdapterPosition(), getObjectJson(viewHolder.getAdapterPosition()), Tag, Fragment_List.this);
                                break;
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                final View llForeground = ((Adapter_Table.ViewHolder_List) viewHolder).llForeground;
                final TextView tvBackground = ((Adapter_Table.ViewHolder_List) viewHolder).tvBackground;
                if (dX > 0) {
                    iList.setSwipeViewRight(tvBackground);
                } else {
                    iList.setSwipeViewLeft(tvBackground);
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
        itemTouchHelper.attachToRecyclerView(RecyclerView);

    }


    @Override
    public void onClick(int position) {
        iList.onClick(getObjectJson(position), Tag);
    }

    private JSONObject getObjectJson(int position) {
        try {
            return lJson.getJSONObject(position - 1);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void update() {
        aList.notifyDataSetChanged();
    }

    @Override
    public void remove(int position) {
        lJson.remove(position - 1);
        update();
    }

    @Override
    public void setData(JSONArray jsonArrayData) {

    }
}
