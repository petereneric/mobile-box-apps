package com.example.ericschumacher.bouncer.Fragments.Choice.Image;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Adapter.List.Choice.Adapter_List_Choice_Image;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Article;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Image;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Choice_Image_Model extends Fragment_Choice_Image implements Fragment_Choice_Image.Interface_Adapter_Choice_Image {

    // Data
    ArrayList<Model> lModel = new ArrayList<>();
    ArrayList<Article> lArticle = new ArrayList<>();

    // Arguments
    int kManufacturer;
    int tPhone;
    int kColor;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Arguments
        kManufacturer = getArguments().getInt(Constants_Intern.ID_MANUFACTURER);
        kColor = getArguments().getInt(Constants_Intern.ID_COLOR);

        // Set Adapter
        aChoice = new Adapter_List_Choice_Image(getActivity(), this);
        rvData.setAdapter(aChoice);

        // Load Data

        cVolley.getResponse(Request.Method.POST, Urls.URL_POST_MODELS_UNKNOWN, getJson(), new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                if (Volley_Connection.successfulResponse(oJson)) {
                    JSONArray jsonArrayModels = oJson.getJSONArray(Constants_Extern.LIST_MODELS);
                    for (int i = 0; i<jsonArrayModels.length(); i++) {
                        lModel.add(new Model(getActivity(), jsonArrayModels.getJSONObject(i)));
                    }
                    JSONArray jsonArrayArticles = oJson.getJSONArray(Constants_Extern.LIST_ARTICLES);
                    for (int i = 0; i<jsonArrayArticles.length(); i++) {
                        lArticle.add(new Article(getActivity(), jsonArrayArticles.getJSONObject(i)));
                    }
                    aChoice.notifyDataSetChanged();
                } else {
                    iChoice.unknownChoice(getTag());
                }

            }
        });

        return vLayout;
    }

    // Interface Methods
    @Override
    public String getUrlIconOne(int position) {
        return null;
    }

    @Override
    public String getUrlIconTwo(int position) {
        return null;
    }

    @Override
    public void setImage(ImageView ivOne, ImageView ivTwo, int position) {
        if (getItemViewType(position) == Constants_Intern.TYPE_ITEM) {
            ivOne.setVisibility(View.VISIBLE);
            ivTwo.setVisibility(View.GONE);
            ivOne.setImageBitmap(lArticle.get(position).getBitmapOne());
        } else {
            ivOne.setVisibility(View.VISIBLE);
            ivTwo.setVisibility(View.GONE);
            Utility_Image.setImageResource(getActivity(), ivOne, R.drawable.ic_unknown_white_192dp, R.color.color_grey);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position < lModel.size()) {
            return Constants_Intern.TYPE_ITEM;
        } else {
            return Constants_Intern.TYPE_UNKNOWN;
        }
    }

    @Override
    public void onAdapterClick(int position) {
        if (getItemViewType(position) == Constants_Intern.TYPE_ITEM) {
            iChoice.returnChoice(getTag(), lModel.get(position));
        } else {
            iChoice.unknownChoice(getTag());
        }

    }

    @Override
    public boolean onAdapterLongClick(int position) {
        if (getItemViewType(position) == Constants_Intern.TYPE_ITEM) {
            Article.showFragmentDialogImage(getActivity(), lArticle.get(position), Fragment_Choice_Image_Model.this, getActivity().getSupportFragmentManager());
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Integer getViewHolderLayout() {
        return R.layout.item_choice_image;
    }

    @Override
    public String getName(int position) {
        if (getItemViewType(position) == Constants_Intern.TYPE_ITEM) {
            return lModel.get(position).getName();
        } else {
            return getString(R.string.model_unknown);
        }
    }

    @Override
    public Integer getItemCount() {
        return lModel.size()+1;
    }

    private JSONObject getJson() {
        JSONObject oJson = new JSONObject();

        try {
            oJson.put(Constants_Extern.ID_MANUFACTURER, kManufacturer);
            oJson.put(Constants_Extern.ID_COLOR, kColor);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return oJson;
    }
}
