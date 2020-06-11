package com.example.ericschumacher.bouncer.Fragments.Loading;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.R;

public class Fragment_Loading extends Fragment {

    // Layout
    View vLayout;
    ContentLoadingProgressBar vProgressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Layout
        vLayout = inflater.inflate(R.layout.fragment_loading, container, false);
        vProgressBar = vLayout.findViewById(R.id.vProgressBar);
        vProgressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(getActivity(), R.color.color_primary), PorterDuff.Mode.SRC_IN );

        return vLayout;
    }
}
