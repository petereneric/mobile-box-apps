package com.example.ericschumacher.bouncer.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Activities.Activity_Article;
import com.example.ericschumacher.bouncer.Fragments.Parent.Fragment_Object;
import com.example.ericschumacher.bouncer.Objects.Article;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Article extends Fragment_Object {

    // Activity
    Activity_Article activityArticle;

    // Objects
    Article oArticle;

    // Layout
    RelativeLayout rlMain;
    ImageView ivOne;
    ImageView ivTwo;
    TextView tvSku;
    TextView tvArticle;
    TextView tvModel;
    TextView tvColor;
    TextView tvShape;
    TextView tvStockAmount;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Activity
        activityArticle = (Activity_Article) getActivity();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        // Layout
        rlMain = vLayout.findViewById(R.id.rlMain);
        tvArticle = vLayout.findViewById(R.id.tvArticle);
        tvSku = vLayout.findViewById(R.id.tvSku);
        tvModel = vLayout.findViewById(R.id.tvModel);
        tvColor = vLayout.findViewById(R.id.tvColor);
        tvShape = vLayout.findViewById(R.id.tvShape);
        tvStockAmount = vLayout.findViewById(R.id.tvStockAmount);
        ivOne = vLayout.findViewById(R.id.ivOne);
        ivTwo = vLayout.findViewById(R.id.ivTwo);

        // Data
        tvTitle.setText(getString(R.string.article));

        // Visibility
        ivAdd.setVisibility(View.GONE);
        ivPause.setVisibility(View.GONE);
        ivClear.setVisibility(View.GONE);
        ivDelete.setVisibility(View.GONE);
        ivDone.setVisibility(View.GONE);
        if (!(getActivity() instanceof Activity_Article)) {
            llLayout.setVisibility(View.GONE);
        }
        rlMain.setVisibility(View.GONE);
    }

    public int getIdLayout() {
        return R.layout.fragment_article;
    }

    public void updateLayout() {
        oArticle = activityArticle.getArticle();

        if (oArticle != null) {

            // Text
            tvArticle.setText(oArticle.getcArticle());
            tvSku.setText(oArticle.getkSku());
            tvModel.setText(oArticle.getcModel());
            tvColor.setText(oArticle.getcColor());
            tvShape.setText(oArticle.getcShape());
            tvStockAmount.setText(Integer.toString(oArticle.getnAmountStock()));

            // Images
            ivOne.setImageBitmap(oArticle.getBitmapOne());
            ivTwo.setImageBitmap(oArticle.getBitmapTwo());
        }
    }
}
