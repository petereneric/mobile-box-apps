package com.example.ericschumacher.bouncer.Fragments;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Activities.Activity_Article;
import com.example.ericschumacher.bouncer.Objects.Article;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Article extends Fragment {

    // Activity
    Activity_Article activityArticle;

    // Objects
    Article oArticle;

    // Layout
    View vLayout;
    TextView tvTitle;
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

        // Object
        oArticle = activityArticle.getArticle();

        // Layout
        setLayout(inflater, container);

        return vLayout;
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        // Layout
        vLayout = inflater.inflate(R.layout.fragment_article, container, false);
        tvTitle = vLayout.findViewById(R.id.tvTitle);
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

        // Article
        rlMain.setVisibility(View.GONE);
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
            byte[] decodedString = Base64.decode(oArticle.getiOne(), Base64.DEFAULT);
            Bitmap bitmapOne = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ivOne.setImageBitmap(bitmapOne);

            byte[] decodedStringTwo = Base64.decode(oArticle.getiTwo(), Base64.DEFAULT);
            Bitmap bitmapTwo = BitmapFactory.decodeByteArray(decodedStringTwo, 0, decodedStringTwo.length);
            ivTwo.setImageBitmap(bitmapTwo);
        }
    }
}
