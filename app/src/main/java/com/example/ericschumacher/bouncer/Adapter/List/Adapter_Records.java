package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Adapter_List;
import com.example.ericschumacher.bouncer.Objects.Collection.Record;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

public class Adapter_Records extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Interface
    Interface_Adapter_List iAdapterList;

    // Data
    ArrayList<Record> lRecords;

    // Else
    Context Context;

    public Adapter_Records(android.content.Context context, ArrayList<Record> lRecords, Interface_Adapter_List iAdapterList) {
        Context = context;
        this.lRecords = lRecords;
        this.iAdapterList = iAdapterList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder_Record vhRecord = new ViewHolder_Record(LayoutInflater.from(Context).inflate(R.layout.item_record, parent, false), new Adapter_Menu.Interface_Click() {
            @Override
            public void onClick(int i) {
                Log.i("jo", "jo");
                iAdapterList.onItemSelected(i);
            }
        });
        return vhRecord;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder_Record vhRecord = (ViewHolder_Record)holder;
        Record oRecord = lRecords.get(position);

        vhRecord.tvId.setText(Integer.toString(oRecord.getId()));
        //vhRecord.tvLastUpdate.setText(Utility_DateTime.dateToString(oRecord.getdLastUpdate()));
        //vhRecord.tvCollector.setText(oRecord.getcCollectorName());
        vhRecord.tvTotal.setText(Integer.toString(oRecord.getnDevices()));
    }

    @Override
    public int getItemCount() {
        return lRecords.size();
    }

    private class ViewHolder_Record extends RecyclerView.ViewHolder implements View.OnClickListener {

        Adapter_Menu.Interface_Click iClick;

        // Layout
        RelativeLayout rvRecord;
        TextView tvId;
        TextView tvLastUpdate;
        TextView tvTotal;
        TextView tvCollector;


        public ViewHolder_Record(View itemView, Adapter_Menu.Interface_Click iClick) {
            super(itemView);

            this.iClick = iClick;

            rvRecord = itemView.findViewById(R.id.rvRecord);
            tvId = itemView.findViewById(R.id.tvId);
            tvLastUpdate = itemView.findViewById(R.id.tvLastUpdate);
            tvTotal = itemView.findViewById(R.id.tvTotal);
            tvCollector = itemView.findViewById(R.id.tvCollector);

            rvRecord.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.rvRecord:
                    iClick.onClick(getAdapterPosition());
                    break;
            }
        }
    }

}
