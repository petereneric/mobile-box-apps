package com.example.ericschumacher.bouncer.Fragments.Choice.Image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Image;
import com.example.ericschumacher.bouncer.Volley.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Choice_Image_Manufacturer extends Fragment_Choice_Image implements Fragment_Choice_Image.Interface_Adapter_Choice_Image {

    // Data
    ArrayList<Manufacturer> lManufacturer = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        // Set Adapter
        aChoice = new Adapter_List_Choice_Image(getActivity(), this);
        rvData.setAdapter(aChoice);

        // Load Data
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MANUFACTURER_ALL, null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                JSONArray jsonArray = oJson.getJSONArray(Constants_Extern.LIST_MANUFACTURER);
                for (int i = 0; i<jsonArray.length(); i++) {
                    lManufacturer.add(new Manufacturer(jsonArray.getJSONObject(i)));
                }
                aChoice.notifyDataSetChanged();
            }
        });

        return vLayout;
    }

    // Interface Methods
    @Override
    public String getUrlIconOne(int position) {
        return lManufacturer.get(position).getUrlIcon();
    }

    @Override
    public String getUrlIconTwo(int position) {
        return null;
    }

    @Override
    public void setImage(ImageView ivOne, ImageView ivTwo, int position) {
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public void onAdapterClick(int position) {
        iChoice.returnChoice(getTag(), lManufacturer.get(position));
    }

    @Override
    public boolean onAdapterLongClick(int position) {
        return false;
    }

    @Override
    public Integer getViewHolderLayout() {
        return R.layout.item_choice_image;
    }

    @Override
    public String getName(int position) {
        return lManufacturer.get(position).getName();
    }

    @Override
    public Integer getItemCount() {
        return lManufacturer.size();
    }
}
