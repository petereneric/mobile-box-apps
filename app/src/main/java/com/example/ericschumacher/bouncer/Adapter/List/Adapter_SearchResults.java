package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Interfaces.Interface_SearchResults;
import com.example.ericschumacher.bouncer.Objects.Object_SearchResult;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

/**
 * Created by Eric Schumacher on 07.06.2018.
 */

public class Adapter_SearchResults extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Data
    Context Context;
    ArrayList<Object_SearchResult> lSearchResults;
    Interface_SearchResults iSearchResults;

    public Adapter_SearchResults(Context context, ArrayList<Object_SearchResult> list, Interface_SearchResults i) {
        Context = context;
        lSearchResults = list;
        iSearchResults = i;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder_SearchResult h = new ViewHolder_SearchResult(LayoutInflater.from(Context).inflate(R.layout.item_searchresult, parent, false), new Interface_Click() {
            @Override
            public void onClick(int position) {
                iSearchResults.onResultClick(lSearchResults.get(position));
            }
        });
        return h;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder_SearchResult h = (ViewHolder_SearchResult)holder;
        h.tvResult.setText(lSearchResults.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return lSearchResults.size();
    }

    public void update(ArrayList<Object_SearchResult> list) {
        lSearchResults = list;
        notifyDataSetChanged();
    }

    private class ViewHolder_SearchResult extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvResult;
        Interface_Click iClick;

        public ViewHolder_SearchResult(View itemView, Interface_Click i) {
            super(itemView);

            iClick = i;
            tvResult = itemView.findViewById(R.id.tvResult);
            tvResult.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            iClick.onClick(getAdapterPosition());
        }
    }

    private interface Interface_Click {
        public void onClick(int position);
    }


}
