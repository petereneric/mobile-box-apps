package com.example.ericschumacher.bouncer.Adapter.List.Choice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_Choice_New;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Long_Click;

public class Adapter_List_Choice extends RecyclerView.Adapter<ViewHolder_Choice_New> implements Interface_Click, Interface_Long_Click {

    // Else
    Context Context;

    // Interface
    public Interface_Adapter_Choice iFragmentChoice;


    public Adapter_List_Choice(Context context, Interface_Adapter_Choice iFragmentChoice) {
        Context = context;
        this.iFragmentChoice = iFragmentChoice;
    }

    @Override
    public ViewHolder_Choice_New onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder_Choice_New vhChoice = new ViewHolder_Choice_New(LayoutInflater.from(Context).inflate(iFragmentChoice.getViewHolderLayout(), parent, false),this, this);
        return vhChoice;
    }

    @Override
    public void onBindViewHolder(ViewHolder_Choice_New vhChoice, int position) {
        vhChoice.tvName.setText(iFragmentChoice.getName(position));
    }

    @Override
    public int getItemCount() {
        return iFragmentChoice.getItemCount();
    }

    @Override
    public void onClick(int position) {
        iFragmentChoice.onAdapterClick(position);
    }

    @Override
    public boolean onLongClick(int position) {
        return iFragmentChoice.onAdapterLongClick(position);
    }

    public interface Interface_Adapter_Choice {

        public void onAdapterClick(int position);

        public boolean onAdapterLongClick(int position);

        public Integer getViewHolderLayout();

        public String getName(int position);

        public Integer getItemCount();
    }
}
