package com.example.ericschumacher.bouncer.Adapter.List.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Long_Click;
import com.example.ericschumacher.bouncer.R;

public class ViewHolder_Choice_Simple extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    // Layout
    public LinearLayout llChoiceSimple;
    public TextView tvName;

    // Interface
    com.example.ericschumacher.bouncer.Interfaces.Interface_Click iClick;
    Interface_Long_Click iLongClick;

    public ViewHolder_Choice_Simple(View itemView, com.example.ericschumacher.bouncer.Interfaces.Interface_Click iClick, Interface_Long_Click iLongClick) {
        super(itemView);

        // Layout
        llChoiceSimple = itemView.findViewById(R.id.llChoice);
        tvName = itemView.findViewById(R.id.tvName);

        // OnClickListener
        llChoiceSimple.setOnClickListener(this);
        llChoiceSimple.setOnLongClickListener(this);

        // Interface
        this.iClick = iClick;
        this.iLongClick = iLongClick;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llChoice:
                iClick.onClick(getAdapterPosition());
        }
    }

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()) {
            case R.id.llChoice:
                return iLongClick.onLongClick(getAdapterPosition());
            default:
                return false;
        }
    }
}
