package com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Activities.Activity_Wiper.Adapter.List.Adapter_List_ModelWipe;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Edit.Fragment_Edit;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Activity_Wiper;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Callback;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Wiper;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Model_New_New;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.ModelWipe;
import com.example.ericschumacher.bouncer.Objects.Wipe;
import com.example.ericschumacher.bouncer.Objects.Wipeprocedure;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class Fragment_Edit_Model_Wipe extends Fragment_Edit implements Interface_Update, Adapter_List.Interface_Adapter_List {

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // VALUES & VARIABLES

    // Interface
    Interface_Model_New_New iModel;
    Interface_Fragment_Wiper iWiperSub;
    Interface_Activity_Wiper iWiperMain;

    // Adapter
    Adapter_List_ModelWipe aList;

    // Connection
    Volley_Connection cVolley;


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LIFE-CYCLE

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        // Interface
        iModel = (Interface_Model_New_New) getActivity();
        iWiperSub = (Interface_Fragment_Wiper) getTargetFragment();
        iWiperMain = (Interface_Activity_Wiper)getActivity();

        // Connection
        cVolley = new Volley_Connection(getActivity());

        super.onCreateView(inflater, container, savedInstanceState);

        update();
        return vLayout;
    }

    @Override
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        // RecyclerView
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        aList = new Adapter_List_ModelWipe(getActivity(), this, iWiperMain.getModelWipes());
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
        return iWiperMain.getModelWipes().size()+1;
    }

    @Override
    public void clickList(int position) {
        if (getItemViewType(position) == Constants_Intern.ADD) {
            ModelWipe.readWipesAvailable(cVolley, iWiperMain.getSelectedModel().getkModel(), new ModelWipe.Interface_Read_WipesAvailable() {
                @Override
                public void read(ArrayList<Wipe> lWipesAvailable) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle(getString(R.string.add_wipe));
                    String[] cWipes = new String[lWipesAvailable.size()];
                    for (int i = 0; i < lWipesAvailable.size(); i++) {
                        cWipes[i] = lWipesAvailable.get(i).getcName();
                    }
                    dialog.setItems(cWipes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ModelWipe.create(cVolley, iWiperMain.getSelectedModel().getkModel(), lWipesAvailable.get(which).getId(), new ModelWipe.Interface_Create() {
                                @Override
                                public void created(ModelWipe modelWipe) {
                                    iWiperMain.getSelectedModel().getlModelWipes().add(modelWipe);
                                    iWiperMain.updateLayout();
                                }
                            });
                        }
                    });
                    AlertDialog alert = dialog.create();
                    alert.show();
                }
            });
        }
    }

    @Override
    public boolean longClickList(int position) {
        if (getItemViewType(position) == Constants_Intern.ADD && iWiperMain.getModelWipes().size() == 0 ) {
            Wipeprocedure.readAll(getContext(), cVolley, iWiperMain.getSelectedModel().getoManufacturer().getId(), new Wipeprocedure.Interface_Read_All() {
                @Override
                public void read(ArrayList<Wipeprocedure> lWipeprocedure) {
                    AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                    dialog.setTitle(getString(R.string.add_wipeprocedure));
                    String[] cProcedures = new String[lWipeprocedure.size()];
                    for (int i = 0; i < lWipeprocedure.size(); i++) {
                        cProcedures[i] = lWipeprocedure.get(i).getcName();
                    }
                    dialog.setItems(cProcedures, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Log.i("urrrl", Urls.URL_ADD_WIPEPROCEDURE_MODEL + cProcedures[which] + "/" + iWiperMain.getSelectedModel().getkModel());
                            cVolley.getResponse(Request.Method.PUT, Urls.URL_ADD_WIPEPROCEDURE_MODEL + lWipeprocedure.get(which).getId() + "/" + iWiperMain.getSelectedModel().getkModel(), null, new Interface_VolleyResult() {
                                @Override
                                public void onResult(JSONObject oJson) throws JSONException {
                                    iWiperMain.getSelectedModel().loadModelWipes(new Interface_Callback() {
                                        @Override
                                        public void callback() {
                                            iWiperMain.updateLayout();
                                        }
                                    });
                                }
                            });
                        }
                    });
                    AlertDialog alert = dialog.create();
                    alert.show();
                }
            });
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < iWiperMain.getModelWipes().size()) {
            return Constants_Intern.ITEM;
        } else {
            return Constants_Intern.ADD;
        }
    }

    @Override
    public void update() {
        aList.update(iWiperMain.getModelWipes());
    }

    public void updateLayout() {
        aList.update(iWiperSub.getModelWipes());
    }
}
