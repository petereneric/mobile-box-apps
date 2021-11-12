package com.example.ericschumacher.bouncer.Fragments.Edit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Edit_Model_Damages;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog.Fragment_Dialog_Simple;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Dialog_Simple;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Edit;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Manager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Model_New_New;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Check;
import com.example.ericschumacher.bouncer.Objects.ModelCheck;
import com.example.ericschumacher.bouncer.Objects.Object_Damage;
import com.example.ericschumacher.bouncer.Objects.Object_Model_Damage;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Volley.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Edit_Model_Damages extends Fragment_Edit implements Adapter_List.Interface_Adapter_List, Interface_Dialog_Simple {

    // Adapter
    Adapter_Edit_Model_Damages mAdapter;

    // Interfaces
    Interface_Model_New_New iModel;
    Interface_Edit iEdit;
    Interface_Manager iManager;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        // Interfaces
        iEdit = (Interface_Edit)getActivity();
        iModel = (Interface_Model_New_New)getActivity();
        iManager = (Interface_Manager)getActivity();

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
        mAdapter = new Adapter_Edit_Model_Damages(getActivity(), this, iModel.getModel() != null ? iModel.getModel().getlModelDamages() : new ArrayList<>());
        rvList.setAdapter(mAdapter);
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
                            Fragment_Dialog_Simple fDialogSimple = new Fragment_Dialog_Simple();
                            Bundle data = new Bundle();
                            data.putString(Constants_Intern.TITLE, getString(R.string.really_delete_model_damage));
                            data.putInt(Constants_Intern.POSITION, viewHolder.getAdapterPosition());
                            fDialogSimple.setArguments(data);
                            fDialogSimple.setTargetFragment(Fragment_Edit_Model_Damages.this, 0);
                            fDialogSimple.show(getFragmentManager(), Constants_Intern.FRAGMENT_DIAlOG_SIMPLE_DELETE_MODEL_DAMAGE);
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
    public void onCommit() {
        iEdit.returnEdit(getTag());
    }

    @Override
    public int getItemCount() {
        if (iModel.getModel() != null) {
            return iModel.getModel().getlModelDamages().size()+1;
        } else {
            return 0;
        }
    }

    @Override
    public void clickList(int position) {
        if (getItemViewType(position) == Constants_Intern.ITEM) {

        } else {
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_DAMAGE_AVAILABLE + iModel.getModel().getkModel(), null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    if (oJson != null) {
                        ArrayList<Object_Damage> lDamages = new ArrayList<>();
                        JSONArray aJson = oJson.getJSONArray("lDamages");
                        for (int i = 0; i < aJson.length(); i++) {
                            JSONObject jsonDamage = aJson.getJSONObject(i);
                            Object_Damage oDamage = new Object_Damage(getActivity(), jsonDamage);
                            lDamages.add(oDamage);
                        }
                        if (lDamages.size() > 0) {
                            String[] lCheckNames = new String[lDamages.size()];
                            for (Object_Damage damage : lDamages) {
                                lCheckNames[lDamages.indexOf(damage)] = damage.getcName();
                            }
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                            dialog.setTitle(R.string.add_damage);
                            dialog.setItems(lCheckNames, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int position) {
                                    Object_Damage damage = lDamages.get(position);
                                    JSONObject oJson = new JSONObject();
                                    try {
                                        oJson.put("kModel", iModel.getModel().getkModel());
                                        oJson.put("kDamage", damage.getkDamage());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    cVolley.getResponse(Request.Method.PUT, Urls.URL_CREATE_MODEL_DAMAGE, oJson, new Interface_VolleyResult() {
                                        @Override
                                        public void onResult(JSONObject oJson) throws JSONException {
                                            if (oJson != null) {
                                                iModel.getModel().getlModelDamages().add(new Object_Model_Damage(getActivity(), oJson));
                                                //iManager.updateLayout();
                                                mAdapter.update(iModel.getModel().getlModelDamages());
                                            }
                                        }
                                    });
                                }
                            });
                            AlertDialog alert = dialog.create();
                            alert.show();
                        } else {
                            Utility_Toast.show(getActivity(), R.string.no_damages_available);
                        }
                    }
                }
            });
        }
    }

    @Override
    public boolean longClickList(int position) {
        if (getItemViewType(position) == Constants_Intern.ADD ) {
            if (iModel.getModel().getlModelDamages().size() == 0) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                dialog.setTitle(R.string.add_damage_sheme);
                String[] lProcedures = {"Tastenhandy", "Smartphone"};
                dialog.setItems(lProcedures, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int position) {
                        cVolley.getResponse(Request.Method.PUT, Urls.URL_CREATE_MODEL_DAMAGES + iModel.getModel().getkModel() + "/" + position, null, new Interface_VolleyResult() {
                            @Override
                            public void onResult(JSONObject oJson) throws JSONException {
                                if (oJson != null) {
                                    iModel.getModel().getlModelDamages().clear();
                                    JSONArray jsonArray = oJson.getJSONArray("lModelDamages");
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        Object_Model_Damage modelDamage = new Object_Model_Damage(getActivity(), jsonObject);
                                        iModel.getModel().getlModelDamages().add(modelDamage);
                                    }
                                    iManager.updateLayout();
                                    mAdapter.update(iModel.getModel().getlModelDamages());
                                }
                            }
                        });
                    }
                });
                AlertDialog alert = dialog.create();
                alert.show();
                return true;
            } else {
                Utility_Toast.show(getActivity(), R.string.delete_all_model_damages_first);
                return true;
            }
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < iModel.getModel().getlModelDamages().size()) {
            return Constants_Intern.ITEM;
        } else {
            return Constants_Intern.ADD;
        }
    }

    @Override
    public void onYes(String cTag, int position) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_DIAlOG_SIMPLE_DELETE_MODEL_DAMAGE:
                cVolley.execute(Request.Method.DELETE, Urls.URL_DELETE_MODEL_DAMAGE+iModel.getModel().getlModelDamages().get(position).getId(), null);
                iModel.getModel().getlModelDamages().remove(position);
                mAdapter.update(iModel.getModel().getlModelDamages());
                break;
        }
    }

    @Override
    public void onNo(String cTag) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_DIAlOG_SIMPLE_DELETE_MODEL_DAMAGE:
                mAdapter.update(iModel.getModel().getlModelDamages());
                break;
        }
    }
}
