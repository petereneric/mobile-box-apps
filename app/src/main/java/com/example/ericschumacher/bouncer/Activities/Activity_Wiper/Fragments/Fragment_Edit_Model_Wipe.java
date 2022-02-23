package com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Adapter.List.Adapter_List_ModelWipe;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Edit.Fragment_Edit;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Activity_Wiper;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Wiper;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Model_New_New;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;
import com.example.ericschumacher.bouncer.Objects.ModelWipe;

import java.util.Collections;

public class Fragment_Edit_Model_Wipe extends Fragment_Edit implements Interface_Update, Adapter_List.Interface_Adapter_List {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // VALUES & VARIABLES

    // Interface
    Interface_Model_New_New iModel;
    Interface_Fragment_Wiper iWiperSub;
    Interface_Activity_Wiper iMain;

    // Adapter
    Adapter_List_ModelWipe aList;


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LIFE-CYCLE

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        // Interface
        iModel = (Interface_Model_New_New) getActivity();
        iWiperSub = (Interface_Fragment_Wiper) getTargetFragment();
        iMain = (Interface_Activity_Wiper)getActivity();

        super.onCreateView(inflater, container, savedInstanceState);

        update();
        return vLayout;
    }

    @Override
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        // RecyclerView
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        aList = new Adapter_List_ModelWipe(getActivity(), this, iMain.getModelWipes());
        rvList.setAdapter(aList);

        // drag & drop
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int positionSelect = viewHolder.getAdapterPosition();
                int postionTarget = target.getAdapterPosition();

                if (positionSelect >= ModelWipe.getWipeProcedures(iWiperSub.getModelWipes()).size() && positionSelect < iWiperSub.getModelWipes().size() && postionTarget <= ModelWipe.getWipeProcedures(iWiperSub.getModelWipes()).size() && postionTarget < iWiperSub.getModelWipes().size()) {
                    Collections.swap(iWiperSub.getModelWipes(), viewHolder.getAdapterPosition(),target.getAdapterPosition());
                    ModelWipe.updatePosition(iWiperSub.getModelWipes());
                    aList.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                    iWiperSub.changeModelWipes(false);
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                super.clearView(recyclerView, viewHolder);
                ModelWipe.updatePosition(iWiperSub.getModelWipes());
                aList.notifyDataSetChanged();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvList);
    }

    @Override
    public int getItemCount() {
        return iMain.getModelWipes().size()+1;
    }

    @Override
    public void clickList(int position) {

    }

    @Override
    public boolean longClickList(int position) {
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < iMain.getModelWipes().size()) {
            return Constants_Intern.ITEM;
        } else {
            return Constants_Intern.ADD;
        }
    }

    @Override
    public void update() {
        aList.update(iMain.getModelWipes());
    }

    public void updateLayout() {
        aList.update(iWiperSub.getModelWipes());
    }
}
