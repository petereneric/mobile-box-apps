package com.example.ericschumacher.bouncer.Fragments.Choice.Image;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Adapter.List.Choice.Adapter_List_Choice_Image_Color;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Choice_Image_Color extends Fragment_Choice_Image implements Adapter_List_Choice_Image_Color.Interface_Adapter_List_Choice_Image_Color {

    // Data
    ArrayList<Color> lColor = new ArrayList<>();
    int kModel;

    // ViewTypes
    public static final int TYPE_WITH_IMAGE = 0;
    public static final int TYPE_WITHOUT_IMAGE = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Data
        kModel = bData.getInt(Constants_Intern.ID_MODEL);

        // Set Adapter
        aChoice = new Adapter_List_Choice_Image_Color(getActivity(), this);
        rvData.setAdapter(aChoice);

        // Load Data
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_COLORS_MODEL+kModel, null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                JSONArray lJson = oJson.getJSONArray(Constants_Extern.LIST_COLORS);
                for (int i = 0; i<lJson.length(); i++) {
                    Color oColor = new Color(getActivity(), lJson.getJSONObject(i));
                    lColor.add(oColor);
                }
                aChoice.notifyDataSetChanged();
            }
        });

        return vLayout;
    }

    @Override
    public int getHexColor(int position) {
        return android.graphics.Color.parseColor(lColor.get(position).getHexCode());
    }

    @Override
    public String getUrlIconOne(int position) {
        return "http://svp-server.com/svp-gmbh/erp/files/images_model_color/" + lColor.get(position).getkModelColor() + "_F.jpg";
    }

    @Override
    public String getUrlIconTwo(int position) {
        return "http://svp-server.com/svp-gmbh/erp/files/images_model_color/" + lColor.get(position).getkModelColor() + "_B.jpg";
    }

    @Override
    public int getItemViewType(int position) {
        if (lColor.get(position).getkModelColor() > 0) {
            return TYPE_WITH_IMAGE;
        } else {
            return TYPE_WITHOUT_IMAGE;
        }
    }

    @Override
    public void onAdapterClick(int position) {
        iChoice.returnChoice(getTag(), lColor.get(position));
    }

    @Override
    public Integer getViewHolderLayout() {
        return R.layout.item_choice_image;
    }

    @Override
    public String getName(int position) {
        return lColor.get(position).getcNameDE();
    }

    @Override
    public Integer getItemCount() {
        return lColor.size();
    }


}
