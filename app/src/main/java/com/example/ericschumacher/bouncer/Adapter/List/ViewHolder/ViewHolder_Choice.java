package com.example.ericschumacher.bouncer.Adapter.List.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Long_Click;
import com.example.ericschumacher.bouncer.R;

public class ViewHolder_Choice extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

    // Layout
    public LinearLayout llChoice;
    public ImageView ivIconOne;
    public ImageView ivIconTwo;
    public TextView tvName;

    // Interface
    Interface_Click iClick;
    Interface_Long_Click iLongClick;

    public ViewHolder_Choice(View itemView, Interface_Click iClick, Interface_Long_Click iLongClick) {
        super(itemView);

        // Layout
        llChoice = itemView.findViewById(R.id.llChoice);
        ivIconOne = itemView.findViewById(R.id.ivOne);
        ivIconTwo = itemView.findViewById(R.id.ivTwo);
        tvName = itemView.findViewById(R.id.tv_name);

        // OnClickListener
        llChoice.setOnClickListener(this);
        llChoice.setOnLongClickListener(this);

        // Interface
        this.iClick = iClick;
        this.iLongClick = iLongClick;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.llChoice:
                iClick.onClick(getAdapterPosition(), ivIconOne);
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

    public interface Interface_Click {
        void onClick(int position, ImageView ivIconOne);
    }
}
