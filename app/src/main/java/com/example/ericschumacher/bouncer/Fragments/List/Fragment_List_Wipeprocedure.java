package com.example.ericschumacher.bouncer.Fragments.List;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Activities.Tools.Activity_Wiper_Procedure;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List_Wipeprocedure;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog.Fragment_Dialog_Delete_Wipeprocedure;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog.Fragment_Dialog_New;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Wipeprocedure;
import com.example.ericschumacher.bouncer.Objects.Wipeprocedure;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Image;
import com.example.ericschumacher.bouncer.Utilities.Utility_Layout;

import java.util.ArrayList;

public class Fragment_List_Wipeprocedure extends Fragment implements Fragment_Dialog_New.Interface_Dialog_New {

    // VALUES & VARIABLES

    // Layout
    View vLayout;
    TextView tvTitle;
    RecyclerView rvData;

    // Data
    ArrayList<Wipeprocedure> lWipeprocedure = new ArrayList<>();

    // Interface
    Interface_Wipeprocedure iWipeprocedure;

    // Adapter
    Adapter_List_Wipeprocedure mAdapter;

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LIFE-CYCLE


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Interface
        iWipeprocedure = (Interface_Wipeprocedure)getActivity();

        // Layout
        setLayout(inflater, container);

        // Data
        update(iWipeprocedure.getlWipeprocedures());

        return vLayout;
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PRIVATE

    // Layout
    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(R.layout.fragment_list_new, container, false);

        tvTitle = vLayout.findViewById(R.id.tvTitle);
        rvData = vLayout.findViewById(R.id.rvData);

        mAdapter = new Adapter_List_Wipeprocedure(getActivity(), lWipeprocedure, new Interface_Click() {
            @Override
            public void onClick(int position) {
                ((Activity_Wiper_Procedure)getActivity()).setWipeprocedure(lWipeprocedure.get(position));
            }
        });
        rvData.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvData.setAdapter(mAdapter);
        setSwipe();
    }

    // Swipe ++ Drag & Drop
    private void setSwipe() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                    switch (direction) {
                        case ItemTouchHelper.RIGHT:
                            Fragment_Dialog_Delete_Wipeprocedure fDialog = new Fragment_Dialog_Delete_Wipeprocedure();
                            Bundle data = new Bundle();
                            data.putInt("nPosition", viewHolder.getAdapterPosition());
                            fDialog.setArguments(data);
                            fDialog.setTargetFragment(Fragment_List_Wipeprocedure.this, 0);
                            fDialog.show(getActivity().getSupportFragmentManager(), Constants_Intern.DIALOG_DELETE_WIPEPROCEDURE);
                            break;
                    }
            }

            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                    // Swipe
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        final View clForeground = ((ViewHolder_List) viewHolder).clForeground;
                        final View clBackground = ((ViewHolder_List) viewHolder).clBackground;
                        clBackground.setVisibility(View.VISIBLE);
                        Utility_Layout.setBackgroundDrawable(getActivity(), ((ViewHolder_List) viewHolder).clBackground, R.drawable.background_rounded_corners_solid_negative);
                        ((ViewHolder_List) viewHolder).ivSwipeRight.setVisibility(View.GONE);
                        Utility_Image.setImageResource(getActivity(), ((ViewHolder_List) viewHolder).ivSwipeLeft, R.drawable.ic_delete_white_24dp, R.color.color_white);
                        getDefaultUIUtil().onDraw(c, recyclerView, clForeground, dX, dY, actionState, isCurrentlyActive);
                    }
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                    // Swipe
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                        final View foregroundView = ((ViewHolder_List) viewHolder).clForeground;
                        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
                    }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                    super.clearView(recyclerView, viewHolder);
                    final View foregroundView = ((ViewHolder_List) viewHolder).clForeground;
                    getDefaultUIUtil().clearView(foregroundView);
                    final View backgroundView = ((ViewHolder_List) viewHolder).clBackground;
                    backgroundView.setVisibility(View.INVISIBLE);
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvData);
    }

    // Update
    private void updateLayout() {
        if (lWipeprocedure.size() > 0) {
            tvTitle.setText(getString(R.string.wipeprocedures));
        } else {
            tvTitle.setText(getString(R.string.no_matching_results));
        }
    }

    public void update(ArrayList<Wipeprocedure> lWipeprocedure) {
        this.lWipeprocedure = lWipeprocedure;
        mAdapter.update(lWipeprocedure);
        updateLayout();
    }

    @Override
    public void returnDialog(String fTag, int tAction, Integer nPosition) {
        switch (fTag) {
            case Constants_Intern.DIALOG_DELETE_WIPEPROCEDURE:
                switch(tAction) {
                    case Constants_Intern.TYPE_ACTION_POSITIVE:
                        if (nPosition != null) {
                            iWipeprocedure.deleteWipeprocedure(nPosition);
                        }
                }
                break;
        }
        mAdapter.update(iWipeprocedure.getlWipeprocedures());
    }
}
