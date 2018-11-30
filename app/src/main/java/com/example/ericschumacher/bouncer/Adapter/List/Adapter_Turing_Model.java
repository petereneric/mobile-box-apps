package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.R;

import org.json.JSONArray;

public class Adapter_Turing_Model extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context Context;
    JSONArray jsonArray;
    Adapter_Request_Choice.Interface_Click iClick;

    public Adapter_Turing_Model(Context context, JSONArray jsonArray, Adapter_Request_Choice.Interface_Click iClick) {
        iClick = iClick;
        Context = context;
        jsonArray = jsonArray;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return jsonArray.length();
    }

    private class ViewHolder_Turing_Model extends RecyclerView.ViewHolder implements View.OnClickListener {

        LinearLayout llItem;
        TextView tvManufacturer;
        TextView tvModelName;
        Interface_Click iClick;

        public ViewHolder_Turing_Model(View itemView, Interface_Click iClick) {
            super(itemView);

            iClick = iClick;

            tvManufacturer = itemView.findViewById(R.id.tvManufacturer);
            tvModelName = itemView.findViewById(R.id.tvModel);
            llItem = itemView.findViewById(R.id.llItem);

            llItem.setOnClickListener(this);


        }

        @Override
        public void onClick(View view) {

        }
    }
}
