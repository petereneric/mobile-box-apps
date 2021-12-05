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

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Edit_Device_Damages;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Edit;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Manager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Object_Device_Damage;
import com.example.ericschumacher.bouncer.Objects.Object_Model_Damage;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Layout;
import com.example.ericschumacher.bouncer.Volley.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Edit_Device_Damages_New extends Fragment_Edit implements Adapter_Edit_Device_Damages.Interface_Adapter_List_Edit_Device_Damages {

    // Adapter
    Adapter_Edit_Device_Damages mAdapter;

    // Interfaces
    Fragment_Device.Interface_Device iDevice;
    Interface_Edit iEdit;
    Interface_Manager iManager;
    Fragment_Edit_Device_Damages.Interface_Edit_Device_Damages iEditDeviceDamages;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        // Interfaces
        iEdit = (Interface_Edit) getActivity();
        iEditDeviceDamages = (Fragment_Edit_Device_Damages.Interface_Edit_Device_Damages) getActivity();
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
                if (getItemViewType(viewHolder.getAdapterPosition()) == Constants_Intern.ITEM) {
                    // General
                    Object_Device_Damage deviceDamage = getDeviceDamage(viewHolder.getAdapterPosition());
                    if (deviceDamage != null) {
                        switch (direction) {
                            case ItemTouchHelper.LEFT:
                                // Unlink
                                //cVolley.execute(Request.Method.DELETE, Urls.DELETE_);
                                cVolley.execute(Request.Method.DELETE, Urls.URL_DELETE_DEVICE_DAMAGE + deviceDamage.getId(), null);
                                iDevice.getDevice().getlDeviceDamages().remove(deviceDamage);
                                break;
                            case ItemTouchHelper.RIGHT:
                                if (deviceDamage != null && deviceDamage.gettStatus() < 2) {
                                    deviceDamage.settStatus(3);
                                }
                                break;
                        }
                    }
                }
                mAdapter.update(iDevice.getDevice().getoModel().getlModelDamages(), iDevice.getDevice().getlDeviceDamages());
            }

            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (getItemViewType(viewHolder.getAdapterPosition()) == Constants_Intern.ITEM) {
                    // General
                    Object_Device_Damage deviceDamage = getDeviceDamage(viewHolder.getAdapterPosition());

                    if ((dX < 0 && deviceDamage != null) || (dX > 0 && deviceDamage != null && deviceDamage.gettStatus() < 2)) {
                        Log.i("Check1", "" + dX);
                        final View clForeground = ((ViewHolder_List) viewHolder).clForeground;
                        final View clBackground = ((ViewHolder_List) viewHolder).clBackground;
                        clBackground.setVisibility(View.VISIBLE);
                        // Left
                        if (dX < 0) {
                            Utility_Layout.setBackgroundDrawable(getActivity(), clBackground, R.drawable.background_rounded_corners_solid_orange);
                            ((ViewHolder_List) viewHolder).ivSwipeLeft.setVisibility(View.INVISIBLE);
                            ((ViewHolder_List) viewHolder).ivSwipeRight.setVisibility(View.VISIBLE);
                        }
                        // Right
                        if (dX > 0) {
                            Utility_Layout.setBackgroundDrawable(getActivity(), clBackground, R.drawable.background_rounded_corners_solid_negative);
                            ((ViewHolder_List) viewHolder).ivSwipeLeft.setVisibility(View.VISIBLE);
                            ((ViewHolder_List) viewHolder).ivSwipeRight.setVisibility(View.INVISIBLE);
                        }
                        getDefaultUIUtil().onDraw(c, recyclerView, clForeground, dX, dY, actionState, isCurrentlyActive);
                    }
                }

            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                // General
                if (getItemViewType(viewHolder.getAdapterPosition()) == Constants_Intern.ITEM) {
                    Object_Device_Damage deviceDamage = getDeviceDamage(viewHolder.getAdapterPosition());

                    if ((dX < 0 && deviceDamage != null) || (dX > 0 && deviceDamage != null && deviceDamage.gettStatus() < 2)) {
                        Log.i("Check2", "" + dX);
                        final View foregroundView = ((ViewHolder_List) viewHolder).clForeground;
                        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
                    }
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

    public Object_Device_Damage getDeviceDamage(int position) {
        if (iDevice.getDevice() != null) {
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
        } else {
            return null;
        }
    }

    @Override
    public int getItemCount() {
        if (iDevice.getDevice() != null) {
            return iDevice.getDevice().getoModel().getlModelDamages().size() + 2;
        } else {
            return 0;
        }
    }

    @Override
    public void clickList(int position) {
        if (getItemViewType(position) == Constants_Intern.ITEM) {
            if (getDeviceDamage(position) != null) {
                switch (getDeviceDamage(position).gettStatus()) {
                    case 1:
                        if (getDeviceDamage(position).gettStatus() == 1)
                            getDeviceDamage(position).settStatus(2);
                        break;
                    case 2:
                        if (getDeviceDamage(position).gettStatus() == 2)
                            getDeviceDamage(position).settStatus(1);
                        break;
                    case 3:
                        if (getDeviceDamage(position).gettStatus() == 3)
                            getDeviceDamage(position).settStatus(1);
                        break;
                }
                update();
            } else {
                // new DeviceDamage
                if (iDevice.getDevice().getIdDevice() == 0) {
                    Object_Device_Damage oDeviceDamage = new Object_Device_Damage(getActivity(), iDevice.getDevice().getIdDevice(), iDevice.getDevice().getoModel().getlModelDamages().get(position), Constants_Intern.STATUS_DAMAGE_REPAIRABLE);
                    iDevice.getDevice().getlDeviceDamages().add(oDeviceDamage);
                    update();
                } else {
                    JSONObject oJson = new JSONObject();
                    try {
                        oJson.put("kDevice", iDevice.getDevice().getIdDevice());
                        oJson.put("kModelDamage", iDevice.getDevice().getoModel().getlModelDamages().get(position).getId());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    cVolley.getResponse(Request.Method.PUT, Urls.URL_CREATE_DEVICE_DAMAGE, oJson, new Interface_VolleyResult() {
                        @Override
                        public void onResult(JSONObject oJson) throws JSONException {
                            if (oJson != null) {
                                Object_Device_Damage oDeviceDamage = new Object_Device_Damage(getActivity(), oJson);
                                Log.i("Size before: ", iDevice.getDevice().getlDeviceDamages().size() + "");
                                iDevice.getDevice().getlDeviceDamages().add(oDeviceDamage);
                                Log.i("Size after: ", iDevice.getDevice().getlDeviceDamages().size() + "");
                                update();
                            }
                        }
                    });
                }
            }
        }
        if (Constants_Intern.TYPE_DAMAGE_OVERBROKEN == getItemViewType(position)) {
            iEditDeviceDamages.returnEditDeviceDamages(Constants_Intern.TYPE_ACTION_DEVICE_DAMAGES_OVERBROKEN, getTag());
        }
        if (Constants_Intern.TYPE_DAMAGE_OTHER == getItemViewType(position)) {
            iEditDeviceDamages.returnEditDeviceDamages(Constants_Intern.TYPE_ACTION_DEVICE_DAMAGES_OTHER_DAMAGES, getTag());
        }

    }

    @Override
    public boolean longClickList(int position) {
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < iDevice.getDevice().getoModel().getlModelDamages().size()) {
            return Constants_Intern.ITEM;
        } else {
            if (position == iDevice.getDevice().getoModel().getlModelDamages().size()) {
                return Constants_Intern.TYPE_DAMAGE_OTHER;
            }
            if (position == iDevice.getDevice().getoModel().getlModelDamages().size() + 1) {
                return Constants_Intern.TYPE_DAMAGE_OVERBROKEN;
            }
        }
        return 0;
    }

    @Override
    public void onCommit() {
        iEditDeviceDamages.returnEditDeviceDamages(Constants_Intern.TYPE_ACTION_DEVICE_DAMAGES_COMMIT, getTag());
    }

    public void update() {
        mAdapter.update(iDevice.getDevice().getoModel().getlModelDamages(), iDevice.getDevice().getlDeviceDamages());
    }
}
