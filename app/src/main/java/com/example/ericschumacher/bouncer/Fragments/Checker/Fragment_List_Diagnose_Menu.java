package com.example.ericschumacher.bouncer.Fragments.Checker;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.text.Layout;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List_Diagnose_Menu;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_Juicer;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_List;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Fragment_Checker;
import com.example.ericschumacher.bouncer.Interfaces.Interface_JWT;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Update;
import com.example.ericschumacher.bouncer.Objects.Device;
import com.example.ericschumacher.bouncer.Objects.Diagnose;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

public class Fragment_List_Diagnose_Menu extends Fragment implements Adapter_List.Interface_Adapter_List, Interface_Update {

    // Layout
    View vLayout;
    RecyclerView rvList;
    TextView tvTitle;

    // Adapter
    Adapter_List_Diagnose_Menu aDiagnoseMenu;

    // Interfaces
    Interface_Fragment_Checker iChecker;
    Interface_JWT iJWT;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Interfaces
        iChecker = (Interface_Fragment_Checker)getParentFragment();
        iJWT = (Interface_JWT)getActivity();

        // Adapter
        aDiagnoseMenu = new Adapter_List_Diagnose_Menu(getActivity(), this, iChecker.getDiagnoses());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        // Layout
        setLayout(inflater, container);

        return vLayout;
    }

    // Layout

    private void setLayout(LayoutInflater inflater, ViewGroup container) {
        vLayout = inflater.inflate(R.layout.fragment_list_new, container, false);
        rvList = vLayout.findViewById(R.id.rvData);
        tvTitle = vLayout.findViewById(R.id.tvTitle);
        tvTitle.setText(getActivity().getString(R.string.diagnose_menu));

        // RecyclerView
        rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvList.setAdapter(aDiagnoseMenu);
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
                            if (iJWT.getJWT().isAdmin() || DateUtils.isToday(iChecker.getDiagnoses().get(viewHolder.getAdapterPosition()).getdCreation().getTime())) {
                                if (iJWT.getJWT().isAdmin() || iJWT.getJWT().getkUser() == iChecker.getDiagnoses().get(viewHolder.getAdapterPosition()).getkUser()) {
                                    iChecker.deleteDiagnose(iChecker.getDiagnoses().get(viewHolder.getAdapterPosition()));
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
    public int getItemCount() {
        return iChecker.getDiagnoses().size() + 1;
    }

    @Override
    public void clickList(int position) {
        switch (getItemViewType(position)) {
            case Constants_Intern.ITEM:
                iChecker.editDiagnose(position);
                break;
            case Constants_Intern.ADD:
                iChecker.addDiagnose();
                break;
        }
    }

    @Override
    public boolean longClickList(int position) {
        return false;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < iChecker.getDiagnoses().size()) {
            return Constants_Intern.ITEM;
        } else {
            return Constants_Intern.ADD;
        }
    }

    @Override
    public void update() {
        if (iChecker != null) {
            aDiagnoseMenu.update(iChecker.getDiagnoses());
            Log.i("Sagan", iChecker.getDiagnoses().size()+"");
            aDiagnoseMenu.notifyDataSetChanged();
        }
    }
}
