package com.example.ericschumacher.bouncer.Fragments.List;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List_Wipe_Procedure;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_List;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_ModelColor;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog.Fragment_Dialog_Wipe_Procedure;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Callback;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click_List;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Wipeprocedure;
import com.example.ericschumacher.bouncer.Objects.ModelColor;
import com.example.ericschumacher.bouncer.Objects.Wipe;
import com.example.ericschumacher.bouncer.Objects.Wipe_Procedure;
import com.example.ericschumacher.bouncer.Objects.Wipeprocedure;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Image;
import com.example.ericschumacher.bouncer.Utilities.Utility_Layout;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;

public class Fragment_List_Wipe_Procedure extends Fragment implements Interface_Click_List, Interface_Wipeprocedure {

    // VALUES & VARIABLES

    // Layout
    View vLayout;
    TextView tvTitle;
    RecyclerView rvData;

    // Interfaces
    Interface_Wipeprocedure iWipeprocedure;

    // Adapter
    Adapter_List_Wipe_Procedure mAdapter;

    // Connection
    Volley_Connection cVolley;

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LIFE-CYCLE


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Interfaces
        iWipeprocedure = (Interface_Wipeprocedure) getActivity();

        // Connection
        cVolley = new Volley_Connection(getActivity());

        // Layout
        setLayout(inflater, container);

        return vLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        update();
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PRIVATE

    // Layout
    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(R.layout.fragment_list_new, container, false);

        // Initiate
        tvTitle = vLayout.findViewById(R.id.tvTitle);
        rvData = vLayout.findViewById(R.id.rvData);

        // Data
        tvTitle.setText(getString(R.string.wipe_procedures));

        // Adapter
        mAdapter = new Adapter_List_Wipe_Procedure(getActivity(), iWipeprocedure.getWipeprocedure().getlWipeProcedure(), this);

        // RecyclerView
        rvData.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvData.setAdapter(mAdapter);
        setSwipe();
    }

    // Swipe + Drag & Drop
    private void setSwipe() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int positionSelect = viewHolder.getAdapterPosition();
                int postionTarget = target.getAdapterPosition();
                if (positionSelect < iWipeprocedure.getWipeprocedure().getlWipeProcedure().size() && postionTarget < iWipeprocedure.getWipeprocedure().getlWipeProcedure().size()) {
                    Collections.swap(iWipeprocedure.getWipeprocedure().getlWipeProcedure(), viewHolder.getAdapterPosition(), target.getAdapterPosition());
                    Wipe_Procedure.updatePosition(iWipeprocedure.getWipeprocedure().getlWipeProcedure());
                    mAdapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Item
                if (mAdapter.getItemViewType(viewHolder.getAdapterPosition()) == Constants_Intern.ITEM) {
                    switch (direction) {
                        case ItemTouchHelper.RIGHT:
                            iWipeprocedure.getWipeprocedure().unlinkWipeProcedure(viewHolder.getAdapterPosition());
                            update();
                            break;
                    }
                }
            }

            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (mAdapter.getItemViewType(viewHolder.getAdapterPosition()) == Constants_Intern.ITEM) {
                    // Swipe
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        final View clForeground = ((ViewHolder_List) viewHolder).clForeground;
                        final View clBackground = ((ViewHolder_List) viewHolder).clBackground;
                        clBackground.setVisibility(View.VISIBLE);
                        Utility_Layout.setBackgroundDrawable(getActivity(), ((ViewHolder_List) viewHolder).clBackground, R.drawable.background_rounded_corners_solid_orange);
                        ((ViewHolder_List) viewHolder).ivSwipeRight.setVisibility(View.GONE);
                        Utility_Image.setImageResource(getActivity(), ((ViewHolder_List) viewHolder).ivSwipeLeft, R.drawable.ic_unlink_24, R.color.color_white);
                        getDefaultUIUtil().onDraw(c, recyclerView, clForeground, dX, dY, actionState, isCurrentlyActive);
                    }

                    // Drag
                    if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                }
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (mAdapter.getItemViewType(viewHolder.getAdapterPosition()) == Constants_Intern.ITEM) {
                    // Swipe
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        final View foregroundView = ((ViewHolder_List) viewHolder).clForeground;
                        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
                    }
                    // Drag
                    if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                    }
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (mAdapter.getItemViewType(viewHolder.getAdapterPosition()) == Constants_Intern.ITEM) {
                    super.clearView(recyclerView, viewHolder);
                    final View foregroundView = ((ViewHolder_List) viewHolder).clForeground;
                    getDefaultUIUtil().clearView(foregroundView);
                    final View backgroundView = ((ViewHolder_List) viewHolder).clBackground;
                    backgroundView.setVisibility(View.INVISIBLE);
                    try {
                        mAdapter.notifyDataSetChanged();
                        Wipe_Procedure.updatePosition(iWipeprocedure.getWipeprocedure().getlWipeProcedure());
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvData);
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PUBLIC

    public void update() {
        mAdapter.update(iWipeprocedure.getWipeprocedure().getlWipeProcedure());
    }

    @Override
    public void onClickList(int position, int type) {
        // Item
        if (type == Constants_Intern.ITEM) {
            // Show dialog
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment_Dialog_Wipe_Procedure dWipeProcedure = new Fragment_Dialog_Wipe_Procedure();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            dWipeProcedure.setArguments(bundle);
            dWipeProcedure.setTargetFragment(Fragment_List_Wipe_Procedure.this, 0);
            dWipeProcedure.show(ft, Constants_Intern.FRAGMENT_DIALOG_WIPE_PROCEDURE);
        }

        // Add
        if (type == Constants_Intern.ADD) {
            Wipe.readAvailable(getActivity(), cVolley, iWipeprocedure.getWipeprocedure().getId(), new Wipe.Interface_Read_List() {
                @Override
                public void read(ArrayList<Wipe> lWipe) {
                    if (lWipe.size() > 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setTitle(getString(R.string.add));
                        String[] items = new String[lWipe.size()];
                        for (int i = 0; i < lWipe.size(); i++) {
                            items[i] = lWipe.get(i).getcName();
                        }
                        builder.setItems(items, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                iWipeprocedure.getWipeprocedure().addWipeProcedure(lWipe.get(i).getId(), new Interface_Callback() {
                                    @Override
                                    public void callback() {
                                        update();
                                    }
                                });

                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    } else {
                        Utility_Toast.show(getActivity(), R.string.no_more_selection);
                    }
                }
            });

        }
    }

    @Override
    public ArrayList<Wipeprocedure> getlWipeprocedures() {
        return iWipeprocedure.getlWipeprocedures();
    }

    @Override
    public Wipeprocedure getWipeprocedure() {
        return iWipeprocedure.getWipeprocedure();
    }

    @Override
    public void deleteWipeprocedure(int nPosition) {
        iWipeprocedure.deleteWipeprocedure(nPosition);
    }
}
