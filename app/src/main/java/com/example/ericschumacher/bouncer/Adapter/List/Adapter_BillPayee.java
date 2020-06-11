package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Objects.Collection.BillPayee;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_DateTime;

import java.util.ArrayList;

public class Adapter_BillPayee extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context Context;
    ArrayList<BillPayee> lBillPayee;

    public Adapter_BillPayee(android.content.Context context, ArrayList<BillPayee> lBillPayee) {
        Context = context;
        this.lBillPayee = lBillPayee;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder_BillPayee(LayoutInflater.from(Context).inflate(R.layout.item_bill_payee, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder_BillPayee vhBillPayee = (ViewHolder_BillPayee)holder;
        BillPayee oBillPayee = lBillPayee.get(position);
        vhBillPayee.tvDateCreation.setText(Utility_DateTime.dateToString(oBillPayee.getdCreation()));
        vhBillPayee.tvName.setText(oBillPayee.getcName());
        vhBillPayee.tvAccountHolder.setText(oBillPayee.getcAccountHolder());
        vhBillPayee.tvIban.setText(oBillPayee.getcIban());
        vhBillPayee.tvPayment.setText(Double.toString(oBillPayee.getsPayment()));

    }

    @Override
    public int getItemCount() {
        return lBillPayee.size();
    }

    private class ViewHolder_BillPayee extends RecyclerView.ViewHolder {

        TextView tvDateCreation;
        TextView tvName;
        TextView tvAccountHolder;
        TextView tvIban;
        TextView tvPayment;

        public ViewHolder_BillPayee(View itemView) {
            super(itemView);

            tvDateCreation = itemView.findViewById(R.id.tvDateCreation);
            tvName = itemView.findViewById(R.id.tvName);
            tvAccountHolder = itemView.findViewById(R.id.tvAccountHolder);
            tvIban = itemView.findViewById(R.id.tvIban);
            tvPayment = itemView.findViewById(R.id.tvPayment);
        }
    }
}
