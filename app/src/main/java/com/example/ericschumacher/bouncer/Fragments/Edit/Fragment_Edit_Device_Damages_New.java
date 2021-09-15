package com.example.ericschumacher.bouncer.Fragments.Edit;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Edit_Device_Damages;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Edit;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Manager;
import com.example.ericschumacher.bouncer.Objects.Object_Device_Damage;
import com.example.ericschumacher.bouncer.Objects.Object_Model_Damage;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Layout;

import java.util.ArrayList;

public class Fragment_Edit_Device_Damages_New extends Fragment_Edit implements Adapter_Edit_Device_Damages.Interface_Adapter_List_Edit_Device_Damages {

    // Adapter
    Adapter_Edit_Device_Damages mAdapter;

    // Interfaces
    Fragment_Device.Interface_Device iDevice;
    Interface_Edit iEdit;
    Interface_Manager iManager;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        // Interfaces
        iEdit = (Interface_Edit) getActivity();
        iDevice = (Fragment_Device.Interface_Device) getActivity();
        iManager = (Interface_Manager) getActivity();

        // Super
        super.onCreateView(inflater, container, savedInstanceState);

        // Visibility
        bCommit.setVisibility(View.VISIBLE);

        return vLayout;
    }

    @Override
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);
        // RecyclerView
        rvList.setLayoutManager(new GridLayoutManager(getActivity(), 2));

        // Adapter
        mAdapter = new Adapter_Edit_Device_Damages(getActivity(), this, iDevice.getDevice() != null ? iDevice.getDevice().getoModel().getlModelDamages() : new ArrayList<>(), iDevice.getDevice() != null ? iDevice.getDevice().getlDeviceDamages() : new ArrayList<>());
        rvList.setAdapter(mAdapter);
        setSwipe();
    }

    private void setSwipe() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // General
                Object_Device_Damage deviceDamage = getDeviceDamage(viewHolder.getAdapterPosition());

                switch (direction) {
                    case ItemTouchHelper.LEFT:
                        // Unlink
                        //cVolley.execute(Request.Method.DELETE, Urls.DELETE_);
                        iDevice.getDevice().getlDeviceDamages().remove(deviceDamage);
                        break;
                    case ItemTouchHelper.RIGHT:
                        deviceDamage.settStatus(3);
                        break;
                }
                mAdapter.update(iDevice.getDevice().getoModel().getlModelDamages(), iDevice.getDevice().getlDeviceDamages());
            }

            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // General
                Object_Device_Damage deviceDamage = getDeviceDamage(viewHolder.getAdapterPosition());

                if ((dX < 0 && deviceDamage != null) || (dX > 0 && deviceDamage != null && deviceDamage.gettStatus() < 2)) {
                    Log.i("Check1", ""+dX);
                    final View clForeground = ((ViewHolder_List) viewHolder).clForeground;
                    final View clBackground = ((ViewHolder_List) viewHolder).clBackground;
                    clBackground.setVisibility(View.VISIBLE);
                    // Left
                    if (dX < 0) {
                        Utility_Layout.setBackgroundDrawable(getActivity(), clBackground, R.drawable.background_rounded_corners_solid_orange);
                        ((ViewHolder_List)viewHolder).ivSwipeLeft.setVisibility(View.INVISIBLE);
                        ((ViewHolder_List)viewHolder).ivSwipeRight.setVisibility(View.VISIBLE);
                    }
                    // Right
                    if (dX > 0) {
                        Utility_Layout.setBackgroundDrawable(getActivity(), clBackground, R.drawable.background_rounded_corners_solid_negative);
                        ((ViewHolder_List)viewHolder).ivSwipeLeft.setVisibility(View.VISIBLE);
                        ((ViewHolder_List)viewHolder).ivSwipeRight.setVisibility(View.INVISIBLE);
                    }
                    getDefaultUIUtil().onDraw(c, recyclerView, clForeground, dX, dY, actionState, isCurrentlyActive);
                }
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // General
                Object_Device_Damage deviceDamage = getDeviceDamage(viewHolder.getAdapterPosition());

                if ((dX < 0 && deviceDamage != null )|| (dX > 0 && deviceDamage != null && deviceDamage.gettStatus() < 2)) {
                    Log.i("Check2", ""+dX);
                    final View foregroundView = ((ViewHolder_List) viewHolder).clForeground;
                    getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                final View foregroundView = ((ViewHolder_List) viewHolder).clForeground;
                getDefaultUIUtil().clearView(foregroundView);
                final View backgroundView = ((ViewHolder_List) viewHolder).clBackground;
                backgroundView.setVisibility(View.INVISIBLE);

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvList);
    }

    public Object_Device_Damage getDeviceDamage(int position) {
        Object_Model_Damage modelDamage = iDevice.getDevice().getoModel().getlModelDamages().get(position);
        Object_Device_Damage oDeviceDamage = null;
        if (iDevice.getDevice().getlDeviceDamages().size() > 0) {
            for (Object_Device_Damage deviceDamage : iDevice.getDevice().getlDeviceDamages()) {
                if (modelDamage.getId() == deviceDamage.getoModelDamage().getId()) {
                    oDeviceDamage = deviceDamage;
                }
            }
        }
        return oDeviceDamage;
    }

    @Override
    public int getItemCount() {
        return iDevice.getDevice().getoModel().getlModelDamages().size();
    }

    @Override
    public void clickList(int position) {
        if (getDeviceDamage(position) != null) {
            switch (getDeviceDamage(position).gettStatus()) {
                case 1:
                    if (getDeviceDamage(position).gettStatus() == 1)getDeviceDamage(position).settStatus(2);
                    break;
                case 2:
                    if (getDeviceDamage(position).gettStatus() == 2)getDeviceDamage(position).settStatus(1);
                    break;
                case 3:
                    if (getDeviceDamage(position).gettStatus() == 3)getDeviceDamage(position).settStatus(1);
                    break;
            }
        } else {
            // new DeviceDamage
        }
        update();
    }

    @Override
    public boolean longClickList(int position) {
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        return Constants_Intern.ITEM;
    }

    public void update() {
        mAdapter.update(iDevice.getDevice().getoModel().getlModelDamages(), iDevice.getDevice().getlDeviceDamages());
    }
}
