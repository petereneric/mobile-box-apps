package com.example.ericschumacher.bouncer.Fragments.Print;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.ericschumacher.bouncer.Activities.Activity_Article;

public class Fragment_Print_Article extends Fragment_Print {

    // Activity
    Activity_Article activityArticle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Layout
        vLayout = super.onCreateView(inflater, container, savedInstanceState);

        // Activity
        activityArticle = (Activity_Article)getActivity();

        return vLayout;
    }



    @Override
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);
        // Visibility
        tvPrintArticle.setVisibility(View.VISIBLE);
    }
}
