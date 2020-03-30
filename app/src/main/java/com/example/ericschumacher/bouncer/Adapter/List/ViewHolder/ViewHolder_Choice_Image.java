package com.example.ericschumacher.bouncer.Adapter.List.ViewHolder;

import android.view.View;
import android.widget.ImageView;

import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Long_Click;
import com.example.ericschumacher.bouncer.R;

public class ViewHolder_Choice_Image extends ViewHolder_Choice_New {

    // Layout
    public ImageView ivIconOne;
    public ImageView ivIconTwo;

    public ViewHolder_Choice_Image(View itemView, Interface_Click iClick, Interface_Long_Click iLongClick) {
        super(itemView, iClick, iLongClick);

        // Layout
        ivIconOne = itemView.findViewById(R.id.ivOne);
        ivIconTwo = itemView.findViewById(R.id.ivTwo);
    }
}
