package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.List.Adapter_BillPayee;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyCallback_ArrayList_BillPayee;
import com.example.ericschumacher.bouncer.Objects.Collection.BillPayee;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Network;

import java.util.ArrayList;

public class Fragment_Zwegat extends Fragment {

    // vLayout
    View Layout;
    RecyclerView rvBillPayee;

    // Utility
    Utility_Network uNetwork;

    // Data
    ArrayList<BillPayee> lBillPayee = new ArrayList<>();
    Adapter_BillPayee aBillPayee;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // vLayout
        setLayout(container);

        // Utility
        uNetwork = new Utility_Network(getActivity());

        // Data
        aBillPayee = new Adapter_BillPayee(getActivity(), lBillPayee);
        rvBillPayee.setAdapter(aBillPayee);
        uNetwork.getUnpaidBillsPayee(new Interface_VolleyCallback_ArrayList_BillPayee() {
            @Override
            public void onSuccess(ArrayList<BillPayee> billPayees) {
                lBillPayee = billPayees;
                aBillPayee = new Adapter_BillPayee(getActivity(), lBillPayee);
                rvBillPayee.setAdapter(aBillPayee);
                aBillPayee.notifyDataSetChanged();
            }

            @Override
            public void onFailure() {

            }
        });

        return Layout;
    }

    private void setLayout(ViewGroup container) {
        Layout = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_zwegat, container, false);
        rvBillPayee = Layout.findViewById(R.id.rvBillPayee);
        rvBillPayee.setLayoutManager(new LinearLayoutManager(getActivity()));

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(final RecyclerView.ViewHolder viewHolder, int direction) {
                BillPayee oBillPayee = lBillPayee.get(viewHolder.getAdapterPosition());
                uNetwork.setBillPayeePaid(oBillPayee.getId(), new Interface_VolleyCallback() {
                    @Override
                    public void onSuccess() {
                        lBillPayee.remove(viewHolder.getAdapterPosition());
                        aBillPayee.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure() {

                    }
                });

            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvBillPayee);
    }
}
