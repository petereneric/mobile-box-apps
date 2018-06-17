package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Objects.Object_Model;
import com.example.ericschumacher.bouncer.R;

/**
 * Created by Eric Schumacher on 21.05.2018.
 */

public class Fragment_Result extends Fragment implements View.OnClickListener {

    // Layout
    View Layout;
    TextView tvRecycling;
    TextView tvReuse;

    // Data
    Object_Model oModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Layout = inflater.inflate(R.layout.fragment_result, container, false);

        // Layout
        setLayout();

        // Data
        oModel = getArguments().getParcelable(Constants_Intern.OBJECT_MODEL);
        if (oModel.getExploitation() == Constants_Intern.EXPLOITATION_RECYCLING) {
            tvRecycling.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.color_primary, null));
        } else {
            tvRecycling.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.color_primary, null));
        }

        return Layout;
    }

    // Layout
    private void setLayout() {
       tvRecycling = Layout.findViewById(R.id.tvRecycling);
       tvReuse = Layout.findViewById(R.id.tvReuse);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvRecycling:

                break;
            case R.id.tvReuse:

                break;
        }
    }
}
