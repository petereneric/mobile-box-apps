package com.example.ericschumacher.bouncer.Adapter.List;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Request_Choice;
import com.example.ericschumacher.bouncer.Objects.Additive.Additive;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;
import com.example.ericschumacher.bouncer.R;

import java.util.ArrayList;

public class Adapter_Request_Color extends Adapter_Request_Choice {

    private final static int TYPE_ADD = 1;
    private final static int TYPE_COLOR = 2;

    public Adapter_Request_Color(android.content.Context context, ArrayList<Additive> list, Interface_Request_Choice i) {
        super(context, list, i);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder_Request_Choice vh;
        if (viewType == TYPE_COLOR) {
            View layout = LayoutInflater.from(Context).inflate(R.layout.cardview_choice, parent, false);
            vh = new ViewHolder_Request_Choice(layout, new Interface_Click() {
                @Override
                public void onClick(int position) {
                    iRequestChoice.onChoice(position);
                }
            });
        } else {
            View layout = LayoutInflater.from(Context).inflate(R.layout.cardview_choice, parent, false);
            vh = new ViewHolder_Request_Choice(layout, new Interface_Click() {
                @Override
                public void onClick(int position) {
                    iRequestChoice.onAdd();
                }
            });
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder_Request_Choice h = (ViewHolder_Request_Choice) holder;
        if (position < lAdditive.size()) {
            Additive additive = lAdditive.get(position);
            h.tvName.setText(additive.getName());
            Log.i("onBind", additive.getName());
            h.ivIcon.setBackgroundColor(android.graphics.Color.parseColor(((Color) additive).getHexCode()));
        } else {
            h.tvName.setVisibility(View.GONE);
            h.ivIcon.setBackgroundResource(R.drawable.ic_add_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return lAdditive.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == lAdditive.size()) {
            return TYPE_ADD;
        } else {
            return TYPE_COLOR;
        }
    }
}
