package com.example.ericschumacher.bouncer.Fragments.Checker;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.format.DateUtils;
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
import com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog.Fragment_Dialog;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog.Fragment_Dialog_Checker_CodeLock;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog.Fragment_Dialog_Checker_Software;
import com.example.ericschumacher.bouncer.Fragments.List.Fragment_List;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Device;
import com.example.ericschumacher.bouncer.Interfaces.Interface_JWT;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Checker;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Manager;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Diagnose;
import com.example.ericschumacher.bouncer.Objects.DiagnoseCheck;
import com.example.ericschumacher.bouncer.Objects.Object_Model_Damage;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_DateTime;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Volley.JWT;
import com.example.ericschumacher.bouncer.Volley.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_List_Diagnose extends Fragment_List implements Adapter_List.Interface_Adapter_List, View.OnClickListener, Interface_Update, Fragment_Dialog.Interface_Fragment_Dialog {

    // Layout
    TextView tvTitle;
    TextView tvSubtitle;
    ImageView ivHeaderLeft;
    ImageView ivHeaderRight;

    // Interface
    Fragment_Device.Interface_Device iDevice;
    Interface_Fragment_Checker iChecker;
    Interface_Manager iManager;
    Interface_JWT iJWT;

    // Data
    Adapter_List_Diagnose aDiagnose;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Interface
        iChecker = (Interface_Fragment_Checker) getParentFragment();
        iManager = (Interface_Manager) getActivity();
        iJWT = (Interface_JWT)getActivity();

        // Data
        //loadChecks();
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

        // RecyclerView
        //rvList.setLayoutManager(new GridLayoutManager(getActivity(), 2));

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
            aDiagnose.updateData(iChecker.getModelChecks(), iChecker.getDiagnose() != null ? iChecker.getDiagnose().getlDiagnoseChecks() : new ArrayList<>());
            if (iChecker.getDiagnose() != null) {
                tvSubtitle.setVisibility(View.VISIBLE);
                tvSubtitle.setText(Utility_DateTime.dateToString(iChecker.getDiagnose().getdCreation()) + " | " + iChecker.getDiagnose().getcUser() + " | " + (iChecker.getDiagnose().isbFinished() ? getString(R.string.finished) : getString(R.string.not_finished)));

            } else {
                tvSubtitle.setVisibility(View.GONE);
                ivHeaderRight.setVisibility(View.GONE);
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
                                iChecker.getDiagnose().deleteDiagnoseCheck(iChecker.getModelChecks(), iChecker.getModelChecks().get(viewHolder.getAdapterPosition()).getoCheck().getId());
                                iChecker.getDiagnose().updateState(iDevice.getDevice().getoModel(), iChecker.getModelChecks());
                                iChecker.diagnoseChange();
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
            boolean bHandlerClick = (getItemViewType(position) == Constants_Intern.SPECIAL && iChecker.getDiagnose().isbFinished());
            if (DateUtils.isToday(iChecker.getDiagnose().getdCreation().getTime()) || bHandlerClick) {
                if (iJWT.getJWT().isAdmin() || iJWT.getJWT().getkUser() == iChecker.getDiagnose().getkUser() || bHandlerClick) {
                    handleClick(position);
                } else {
                    Utility_Toast.show(getActivity(), R.string.cant_be_edited_due_wrong_user);
                }
            } else {
                Utility_Toast.show(getActivity(), R.string.cant_be_edited_anymore_due_time);
            }
        } else {
            JSONObject oJson = new JSONObject();
            try {
                oJson.put("kDevice", iDevice.getDevice().getIdDevice());
                Log.i("UserId: ", iJWT.getJWT().getkUser()+"");
                oJson.put("kUser", iJWT.getJWT().getkUser());
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

    @Override
    public boolean longClickList(int position) {
        return false;
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
            if (iChecker.getModelChecks().get(position).getoCheck().getId() == 13 || iChecker.getModelChecks().get(position).getoCheck().getId() == 10) {
                boolean bFailed = true;
                for (DiagnoseCheck diagnoseCheck : iChecker.getDiagnose().getlDiagnoseChecks()) {
                    if (diagnoseCheck.getkCheck() == iChecker.getModelChecks().get(position).getoCheck().getId()) {
                        if (diagnoseCheck.gettStatus() == 2) {
                            bFailed = false;
                        }
                        break;
                    }
                }

                if (bFailed) {
                    if (iChecker.getModelChecks().get(position).getoCheck().getId() == 13) {
                        // Code-Lock
                        for (Object_Model_Damage modelDamage : iDevice.getDevice().getoModel().getlModelDamages()) {
                            if (modelDamage.getoDamage().getkDamage() == iChecker.getModelChecks().get(position).getoCheck().getkDamage()) {
                                // show dialog
                                Fragment_Dialog_Checker_CodeLock fDialog = new Fragment_Dialog_Checker_CodeLock();
                                Bundle bundle = new Bundle();
                                bundle.putInt("nPosition", position);
                                fDialog.setArguments(bundle);
                                fDialog.setTargetFragment(this, 0);
                                fDialog.show(getFragmentManager(), "FRAGMENT_DIALOG_CHECKER_CODE_LOCK");
                                return;
                            }
                        }
                    }
                    if (iChecker.getModelChecks().get(position).getoCheck().getId() == 10) {
                        // Software
                        for (Object_Model_Damage modelDamage : iDevice.getDevice().getoModel().getlModelDamages()) {
                            if (modelDamage.getoDamage().getkDamage() == iChecker.getModelChecks().get(position).getoCheck().getkDamage()) {
                                // show dialog
                                Fragment_Dialog_Checker_Software fDialog = new Fragment_Dialog_Checker_Software();
                                Bundle bundle = new Bundle();
                                bundle.putInt("nPosition", position);
                                fDialog.setArguments(bundle);
                                fDialog.setTargetFragment(this, 0);
                                fDialog.show(getFragmentManager(), "FRAGMENT_DIALOG_CHECKER_SOFTWARE");
                                return;
                            }
                        }
                    }
                }
            }
            iChecker.getDiagnose().click(iChecker.getModelChecks(), iChecker.getModelChecks().get(position).getoCheck().getId(), true);
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
        /*
        if (aDiagnose != null) {
            loadChecks();
        }
         */
    }

    @Override
    public void click(String cTag, int typeButton, int nPosition) {
        if (cTag.equals("FRAGMENT_DIALOG_CHECKER_CODE_LOCK") || cTag.equals("FRAGMENT_DIALOG_CHECKER_SOFTWARE")) {
            switch (typeButton) {
                case Constants_Intern.BUTTON_ONE:
                    if (nPosition > 0) iChecker.getDiagnose().click(iChecker.getModelChecks(), iChecker.getModelChecks().get(nPosition - 1).getoCheck().getId(), false);
                    // Edit
                    iChecker.getDiagnose().updateState(iDevice.getDevice().getoModel(), iChecker.getModelChecks());
                    iChecker.diagnoseChange();
                    aDiagnose.updateData(iChecker.getModelChecks(), iChecker.getDiagnose().getlDiagnoseChecks());
                    // reset
                    iManager.reset();
                    break;
                case Constants_Intern.BUTTON_TWO:
                    boolean bFinishedBefore = iChecker.getDiagnose().isbFinished();

                    iChecker.getDiagnose().click(iChecker.getModelChecks(), iChecker.getModelChecks().get(nPosition).getoCheck().getId(), true);

                    // State
                    iChecker.getDiagnose().updateState(iDevice.getDevice().getoModel(), iChecker.getModelChecks());
                    iChecker.diagnoseChange();

                    if (!bFinishedBefore && iChecker.getDiagnose().isbFinished()) {
                        // Show Handler
                        iChecker.showHandler();
                    }
                    break;
            }
        }
    }
}
