package com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Fragments;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Adapter.List.Adapter_List_Protocol_Menu;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List_Diagnose_Menu;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Adapter_List;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Checker;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Wiper_Single;
import com.example.ericschumacher.bouncer.Interfaces.Interface_JWT;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;

public class Fragment_Wiper_Protocol_Menu extends Fragment implements Interface_Update, Adapter_List.Interface_Adapter_List {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // VALUES & VARIABLES

    // Layout
    View vLayout;
    RecyclerView rvList;
    TextView tvTitle;

    // Adapter
    Adapter_List_Protocol_Menu aProtocolMenu;

    // Interfaces
    Interface_Fragment_Wiper_Single iWiperSingle;
    Interface_JWT iJWT;


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LIFECYCLE

    @Override
    public void onCreate(@Nullable @org .jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Interfaces
        iWiperSingle = (Interface_Fragment_Wiper_Single)getParentFragment();
        iJWT = (Interface_JWT)getActivity();

        // Adapter
        aProtocolMenu = new Adapter_List_Protocol_Menu(getActivity(), this, iWiperSingle.getProtocols());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        // Layout
        setLayout(inflater, container);

        return vLayout;
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PRIVATE

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(R.layout.fragment_list_new, container, false);
        rvList = vLayout.findViewById(R.id.rvData);
        tvTitle = vLayout.findViewById(R.id.tvTitle);
        tvTitle.setText(getActivity().getString(R.string.protocol_menu));

        // RecyclerView
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setAdapter(aProtocolMenu);
        setSwipe();
    }

    private void setSwipe() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (getItemViewType(viewHolder.getAdapterPosition()) == Constants_Intern.ITEM) {
                    switch (direction) {
                        case ItemTouchHelper.LEFT:
                            // Delete
                            if (iJWT.getJWT().isAdmin() || DateUtils.isToday(iWiperSingle.getProtocols().get(viewHolder.getAdapterPosition()).getdCreation().getTime())) {
                                if (iJWT.getJWT().isAdmin() || iJWT.getJWT().getkUser() == iWiperSingle.getProtocols().get(viewHolder.getAdapterPosition()).getkUser()) {
                                    iWiperSingle.getProtocols().get(viewHolder.getAdapterPosition()).delete();
                                } else {
                                    Utility_Toast.show(getActivity(), R.string.cant_be_edited_due_wrong_user);
                                    update();
                                }
                            } else {
                                Utility_Toast.show(getActivity(), R.string.cant_be_edited_anymore_due_time);
                                update();
                            }

                            break;
                    }
                }
            }

            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (getItemViewType(viewHolder.getAdapterPosition()) == Constants_Intern.ITEM) {
                    final View clForeground = ((ViewHolder_List) viewHolder).clForeground;
                    final View clBackground = ((ViewHolder_List) viewHolder).clBackground;
                    clBackground.setVisibility(View.VISIBLE);
                    getDefaultUIUtil().onDraw(c, recyclerView, clForeground, dX, dY, actionState, isCurrentlyActive);
                }
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (getItemViewType(viewHolder.getAdapterPosition()) == Constants_Intern.ITEM) {
                    final View foregroundView = ((ViewHolder_List) viewHolder).clForeground;
                    getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (getItemViewType(viewHolder.getAdapterPosition()) == Constants_Intern.ITEM) {
                    final View foregroundView = ((ViewHolder_List) viewHolder).clForeground;
                    getDefaultUIUtil().clearView(foregroundView);
                    final View backgroundView = ((ViewHolder_List) viewHolder).clBackground;
                    backgroundView.setVisibility(View.INVISIBLE);
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvList);
    }

    @Override
    public void update() {
    }


    @Override
    public int getItemCount() {
        return iWiperSingle.getProtocols().size()+1;
    }

    @Override
    public void clickList(int position) {
        switch (getItemViewType(position)) {
            case Constants_Intern.ITEM:
                iWiperSingle.editProtocol(position);
                break;
            case Constants_Intern.ADD:
                iWiperSingle.addProtocol();
                break;
        }
    }

    @Override
    public boolean longClickList(int position) {
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < iWiperSingle.getProtocols().size()) {
            return Constants_Intern.ITEM;
        } else {
            return Constants_Intern.ADD;
        }
    }
}
