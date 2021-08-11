package com.example.ericschumacher.bouncer.Fragments.Checker;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List_Diagnose;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.List.Fragment_List;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Checker;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Diagnose;
import com.example.ericschumacher.bouncer.Objects.ModelCheck;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_DateTime;
import com.example.ericschumacher.bouncer.Volley.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_List_Diagnose extends Fragment_List implements Adapter_List.Interface_Adapter_List, View.OnClickListener, Interface_Update {

    // Layout
    TextView tvTitle;
    TextView tvSubtitle;
    ImageView ivHeaderLeft;
    ImageView ivHeaderRight;

    // Interface
    Fragment_Device.Interface_Device iDevice;
    Interface_Fragment_Checker iChecker;

    // Data
    Adapter_List_Diagnose aDiagnose;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Interface
        iChecker = (Interface_Fragment_Checker) getParentFragment();

        // Data
        loadChecks();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        return vLayout;
    }

    @Override
    public void onDestroy() {
        /*
        if (iChecker.getDiagnose().getlDiagnoseChecks().size() == 0) {
            iChecker.deleteDiagnose();
        }
         */
        super.onDestroy();
    }

    @Override
    public void setInterface() {
        iDevice = (Fragment_Device.Interface_Device) getActivity();
    }

    @Override
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        // Layout
        tvTitle = vLayout.findViewById(R.id.tvTitle);
        tvTitle.setText(getActivity().getString(R.string.checking));
        tvSubtitle = vLayout.findViewById(R.id.tvSubtitle);
        ivHeaderLeft = vLayout.findViewById(R.id.ivHeaderLeft);
        ivHeaderRight = vLayout.findViewById(R.id.ivHeaderRight);

        // Header
        tvSubtitle.setVisibility(View.VISIBLE);

        ivHeaderLeft.setVisibility(View.VISIBLE);
        ivHeaderRight.setVisibility(View.VISIBLE);
        ivHeaderLeft.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_pause_white_24dp));
        ivHeaderLeft.setColorFilter(getActivity().getResources().getColor(R.color.color_grey));
        ivHeaderRight.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.ic_clear_24dp));
        ivHeaderRight.setColorFilter(getActivity().getResources().getColor(R.color.color_grey));

        // ClickListener
        ivHeaderLeft.setOnClickListener(this);
        ivHeaderRight.setOnClickListener(this);


        // Data
        aDiagnose = new Adapter_List_Diagnose(getActivity(), this, iChecker != null && iChecker.getModelChecks() != null ? iChecker.getModelChecks() : new ArrayList<>(), iChecker != null && iChecker.getDiagnose() != null ? iChecker.getDiagnose().getlDiagnoseChecks() : new ArrayList<>(), iChecker);
        rvList.setAdapter(aDiagnose);

        // Swipe
        setSwipe();

        // Update
        updateLayout();
    }

    public void updateLayout() {
        if (iChecker != null) {
            aDiagnose.updateData(iChecker.getModelChecks(), iChecker.getDiagnose() != null ? iChecker.getDiagnose().getlDiagnoseChecks(): new ArrayList<>());
            if (iChecker.getDiagnose() != null) {
                tvSubtitle.setVisibility(View.VISIBLE);
                tvSubtitle.setText(Utility_DateTime.dateToString(iChecker.getDiagnose().getdLastUpdate()) + " | " + iChecker.getDiagnose().getcUser() + " | " + (iChecker.getDiagnose().isbFinished() ? getString(R.string.finished) : getString(R.string.not_finished)));

            } else {
                tvSubtitle.setVisibility(View.GONE);
            }
        }
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
                            if (iChecker.getDiagnose() != null && iChecker.getDiagnose().hasDiagnoseCheck(iChecker.getModelChecks().get(viewHolder.getAdapterPosition()).getoCheck().getId())) {
                                // Delete DiagnoseCheck
                                iChecker.getDiagnose().deleteDiagnoseCheck(iChecker.getModelChecks().get(viewHolder.getAdapterPosition()).getoCheck().getId());
                                iChecker.getDiagnose().updateState(iDevice.getDevice().getoModel(), iChecker.getModelChecks());
                                aDiagnose.updateData(iChecker.getModelChecks(), iChecker.getDiagnose().getlDiagnoseChecks());
                                break;
                            }
                    }
                }

            }

            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (iChecker.getDiagnose() != null && getItemViewType(viewHolder.getAdapterPosition()) == Constants_Intern.ITEM && iChecker.getDiagnose().hasDiagnoseCheck(iChecker.getModelChecks().get(viewHolder.getAdapterPosition()).getoCheck().getId())) {
                    final View clForeground = ((ViewHolder_List) viewHolder).clForeground;
                    final View clBackground = ((ViewHolder_List) viewHolder).clBackground;
                    clBackground.setVisibility(View.VISIBLE);
                    getDefaultUIUtil().onDraw(c, recyclerView, clForeground, dX, dY, actionState, isCurrentlyActive);
                }
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (iChecker.getDiagnose() != null && getItemViewType(viewHolder.getAdapterPosition()) == Constants_Intern.ITEM && iChecker.getDiagnose().hasDiagnoseCheck(iChecker.getModelChecks().get(viewHolder.getAdapterPosition()).getoCheck().getId())) {
                    final View foregroundView = ((ViewHolder_List) viewHolder).clForeground;
                    getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (iChecker.getDiagnose() != null && getItemViewType(viewHolder.getAdapterPosition()) == Constants_Intern.ITEM) {
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

    private void loadChecks() {

        // Load Diagnose_Checks
        if (iChecker.getDiagnose() != null) {
            Log.i("laadde", "nochmal!!");
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_DIAGNOSE_CHECKS + iChecker.getDiagnose().getId(), null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    if (oJson != null) {
                        if (oJson.getJSONArray("lDiagnoseChecks").length() > 0) {
                            iChecker.getDiagnose().addDiagnoseChecks(oJson.getJSONArray("lDiagnoseChecks"));
                        }

                        aDiagnose.updateData(iChecker.getModelChecks(), iChecker.getDiagnose().getlDiagnoseChecks());
                    } else {
                        // There is no Diagnose
                    }
                }// Check if null means that there are no modelChecks --> Show in UI
            });
        } else {
            if (aDiagnose != null) aDiagnose.updateData(iChecker.getModelChecks(), new ArrayList<>());
        }
    }

    @Override
    public int getItemCount() {
        return iChecker.getModelChecks().size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < iChecker.getModelChecks().size()) {
            return Constants_Intern.ITEM;
        } else {
            return Constants_Intern.SPECIAL;
        }
    }

    @Override
    public void clickList(int position) {
        if (iChecker.getDiagnose() != null) {
            handleClick(position);
        } else {
            JSONObject oJson = new JSONObject();
            try {
                oJson.put("kDevice", iDevice.getDevice().getIdDevice());
                oJson.put("kUser", 1);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            cVolley.getResponse(Request.Method.PUT, Urls.URL_CREATE_DIAGNOSE, oJson, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    if (oJson != null) {
                        Diagnose oDiagnose = new Diagnose(getActivity(), oJson);
                        iChecker.getDiagnoses().add(oDiagnose);
                        iChecker.setDiagnose(oDiagnose);
                        handleClick(position);

                    }
                }
            });
        }

    }

    private void handleClick(int position) {
        boolean bFinishedBefore = iChecker.getDiagnose().isbFinished();

        // Special
        if (getItemViewType(position) == Constants_Intern.SPECIAL) {
            if (iChecker.getDiagnose().isbFinished()) {
                iChecker.showHandler();
            } else {
                iChecker.getDiagnose().finishSuccessfully(iChecker.getModelChecks());
            }
        }

        // Item
        if (getItemViewType(position) == Constants_Intern.ITEM) {
            iChecker.getDiagnose().click(iChecker.getModelChecks(), iChecker.getModelChecks().get(position).getoCheck().getId());
        }

        // State
        iChecker.getDiagnose().updateState(iDevice.getDevice().getoModel(), iChecker.getModelChecks());
        iChecker.diagnoseChange();

        if (!bFinishedBefore && iChecker.getDiagnose().isbFinished()) {
            // Show Handler
            iChecker.showHandler();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivHeaderLeft:
                // Pause
                iChecker.pauseDiagnose();
                break;
            case R.id.ivHeaderRight:
                // Delete
                iChecker.deleteDiagnose(iChecker.getDiagnose());
                break;
        }
    }

    @Override
    public void update() {
        Log.i("jOOOO", "neee");
        if (aDiagnose != null) {
            loadChecks();
        }
    }
}
