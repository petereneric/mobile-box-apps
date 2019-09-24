package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

public class Adapter_List_Navigation extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Interface
    Interface_Click iClick;

    // Data
    ArrayList<String> lNavigation;

    // Else
    Context Context;

    public Adapter_List_Navigation(Context context, ArrayList<String> lNavigation, Interface_Click iClick) {
        Context = context;
        this.lNavigation = lNavigation;
        this.iClick = iClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View Layout = LayoutInflater.from(Context).inflate(R.layout.item_list_navigation, parent, false);
        ViewHolder_List_Navigation vhListNavigation = new ViewHolder_List_Navigation(Layout, new Interface_Click() {
            @Override
            public void onClick(int position) {
                iClick.onClick(position);
            }
        });
        return vhListNavigation;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder_List_Navigation vhListNavigation = (ViewHolder_List_Navigation)holder;
        vhListNavigation.tvTitle.setText(lNavigation.get(position));
    }

    @Override
    public int getItemCount() {
        return lNavigation.size();
    }

    public class ViewHolder_List_Navigation extends RecyclerView.ViewHolder {

        Interface_Click iClick;
        public TextView tvTitle;

        public ViewHolder_List_Navigation(View itemView, final Interface_Click iClick) {
            super(itemView);

            this.iClick = iClick;

            tvTitle = itemView.findViewById(R.id.tvTitle);

            this.tvTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    iClick.onClick(getAdapterPosition());
                }
            });

        }
    }
}
