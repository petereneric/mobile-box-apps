package com.example.ericschumacher.bouncer.Adapter.List.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Long_Click;
import com.example.ericschumacher.bouncer.R;

public class ViewHolder_Choice_Simple_Add extends RecyclerView.ViewHolder implements View.OnClickListener {

    // Layout
    public LinearLayout llChoiceSimpleAdd;

    // Interface
    com.example.ericschumacher.bouncer.Interfaces.Interface_Click iClick;
    Interface_Long_Click iLongClick;

    public ViewHolder_Choice_Simple_Add(View itemView, com.example.ericschumacher.bouncer.Interfaces.Interface_Click iClick) {
        super(itemView);

        // Layout
        llChoiceSimpleAdd = itemView.findViewById(R.id.llChoiceSimpleAdd);

        // OnClickListener
        llChoiceSimpleAdd.setOnClickListener(this);

        // Interface
        this.iClick = iClick;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llChoiceSimpleAdd:
                iClick.onClick(getAdapterPosition());
        }
    }
}
