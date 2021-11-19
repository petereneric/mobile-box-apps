package com.example.ericschumacher.bouncer.Adapter.List.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.R;

public class ViewHolder_ModelColor extends RecyclerView.ViewHolder implements View.OnClickListener {

    // Layout
    private TextView tvTitle;
    private ImageView ivMatch;
    private ImageView ivAuto;
    private ImageView ivExploitation;
    private ImageView ivContent;

    // Interface
    Interface_Click iClick;


    public ViewHolder_ModelColor(View itemView, final Interface_Click iClick) {
        super(itemView);

        // Interface
        this.iClick = iClick;

        // Initiate
        tvTitle = itemView.findViewById(R.id.tvTitle);
        ivMatch = itemView.findViewById(R.id.ivMatch);
        ivAuto = itemView.findViewById(R.id.ivAuto);
        ivExploitation = itemView.findViewById(R.id.ivExploitation);
        ivContent = itemView.findViewById(R.id.ivContent);

        //Click
        ivMatch.setOnClickListener(this);
        ivAuto.setOnClickListener(this);
        ivExploitation.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivMatch:
                iClick.onClick(Constants_Intern.EDIT_MODEL_COLOR_MATCH);
                break;
            case R.id.ivAuto:
                iClick.onClick(Constants_Intern.EDIT_MODEL_COLOR_AUTO);
                break;
            case R.id.ivExploitation:
                iClick.onClick(Constants_Intern.EDIT_MODEL_COLOR_EXPLOITATION);
                break;
        }
    }
}
