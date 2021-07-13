package com.example.ericschumacher.bouncer.Fragments.Edit;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List_ModelChecks;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog.Fragment_Dialog_Model_Check;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Model_New_New;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Check;
import com.example.ericschumacher.bouncer.Objects.ModelCheck;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Volley.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

public class Fragment_Edit_Model_Checks extends Fragment_Edit implements Interface_Update, Adapter_List.Interface_Adapter_List {

    // Interface
    Interface_Model_New_New iModel;

    // Data
    ArrayList<ModelCheck> lModelChecks = new ArrayList<>();

    // Adapter
    Adapter_List_ModelChecks aList;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        // Interface
        iModel = (Interface_Model_New_New) getActivity();

        super.onCreateView(inflater, container, savedInstanceState);
        return vLayout;
    }

    @Override
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        // RecyclerView
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        aList = new Adapter_List_ModelChecks(getActivity(), this, lModelChecks);
        rvList.setAdapter(aList);

        // drag & drop
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END, 0) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                int positionSelect = viewHolder.getAdapterPosition();
                int postionTarget = target.getAdapterPosition();
                if (positionSelect < lModelChecks.size() && postionTarget < lModelChecks.size()) {
                    Collections.swap(lModelChecks, viewHolder.getAdapterPosition(),target.getAdapterPosition());
                    ModelCheck.updatePosition(lModelChecks);
                    aList.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
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
                ModelCheck.sortByLogic(lModelChecks);
                ModelCheck.updatePosition(lModelChecks);
                aList.notifyDataSetChanged();
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvList);
    }

    private void loadData() {
        // Reset

        if (aList != null) {
            lModelChecks = new ArrayList<>();
            aList.update(lModelChecks);
            aList.notifyDataSetChanged();
        }

        // Load
        if (iModel != null && iModel.getModel() != null) {
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_CHECKS + iModel.getModel().getkModel(), null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    if (oJson != null) {
                        JSONArray aJson = oJson.getJSONArray("lModelChecks");
                        for (int i = 0; i < aJson.length(); i++) {
                            JSONObject jsonModelCheck = aJson.getJSONObject(i);
                            ModelCheck oModelCheck = new ModelCheck(jsonModelCheck, getActivity());
                            lModelChecks.add(oModelCheck);
                        }
                    }
                    ModelCheck.sortByPosition(lModelChecks);
                    ModelCheck.sortByLogic(lModelChecks);
                    ModelCheck.updatePosition(lModelChecks);

                    aList.notifyDataSetChanged();
                }
            });
        }
    }

    @Override
    public void update() {
        loadData();
    }

    public void refresh(boolean bSort) {
        aList.update(lModelChecks);
        if (bSort) {
            ModelCheck.sortByLogic(lModelChecks);
            ModelCheck.updatePosition(lModelChecks);
        }
        aList.notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return lModelChecks.size() + 1;
    }

    @Override
    public void clickList(int position) {
        if (getItemViewType(position) == Constants_Intern.ITEM) {
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            Fragment_Dialog_Model_Check dModelCheck = new Fragment_Dialog_Model_Check();
            Bundle bundle = new Bundle();
            bundle.putInt("position", position);
            dModelCheck.setArguments(bundle);
            dModelCheck.setTargetFragment(Fragment_Edit_Model_Checks.this, 0);
            dModelCheck.show(ft, Constants_Intern.FRAGMENT_DIALOG_EDIT_MODEL_CHECKS);
        } else {
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_CHECKS_AVAILABLE + iModel.getModel().getkModel(), null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    if (oJson != null) {
                        Log.i("JSON: ", "not null");
                        ArrayList<Check> lChecks = new ArrayList<>();
                        JSONArray aJson = oJson.getJSONArray("lChecks");
                        for (int i = 0; i < aJson.length(); i++) {
                            JSONObject jsonCheck = aJson.getJSONObject(i);
                            Check oCheck = new Check(getActivity(), jsonCheck);
                            lChecks.add(oCheck);
                        }
                        if (lChecks.size() > 0) {
                            String[] lCheckNames = new String[lChecks.size()];
                            for (Check check : lChecks) {
                                lCheckNames[lChecks.indexOf(check)] = check.getcName();
                            }
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                            dialog.setTitle(R.string.add_check);
                            dialog.setItems(lCheckNames, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int position) {
                                    Check check = lChecks.get(position);
                                    cVolley.getResponse(Request.Method.PUT, Urls.URL_CREATE_MODEL_CHECK + iModel.getModel().getkModel() + "/" + check.getId(), null, new Interface_VolleyResult() {
                                        @Override
                                        public void onResult(JSONObject oJson) throws JSONException {
                                            loadData();
                                        }
                                    });
                                }
                            });
                            AlertDialog alert = dialog.create();
                            alert.show();
                        } else {
                            Utility_Toast.show(getActivity(), R.string.no_checks_available);
                        }
                        Log.i("Checks Size: ", "" + lChecks.size());
                    }
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position < lModelChecks.size() ? Constants_Intern.ITEM : Constants_Intern.ADD;
    }

    public ModelCheck getModelCheck(int position) {
        if (lModelChecks.size() > 0) {
            return lModelChecks.get(position);
        }
        return null;

    }
}
