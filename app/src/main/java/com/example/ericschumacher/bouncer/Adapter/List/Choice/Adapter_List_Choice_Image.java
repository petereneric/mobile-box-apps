package com.example.ericschumacher.bouncer.Adapter.List.Choice;

import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_Choice_Image;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_Choice_New;
import com.example.ericschumacher.bouncer.Fragments.Choice.Image.Fragment_Choice_Image;

public class Adapter_List_Choice_Image extends Adapter_List_Choice {

    RequestQueue rQueue;

    public Adapter_List_Choice_Image(android.content.Context context, Fragment_Choice_Image.Interface_Adapter_Choice_Image iFragmentChoice) {
        super(context, iFragmentChoice);
        rQueue = Volley.newRequestQueue(Context);
    }

    @Override
    public ViewHolder_Choice_Image onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder_Choice_Image vhChoice = new ViewHolder_Choice_Image(LayoutInflater.from(Context).inflate(iFragmentChoice.getViewHolderLayout(), parent, false), this, this);
        return vhChoice;
    }

    @Override
    public void onBindViewHolder(ViewHolder_Choice_New vhChoice, final int position) {
        super.onBindViewHolder(vhChoice, position);

        // Image
        final ViewHolder_Choice_Image vhChoiceImage = (ViewHolder_Choice_Image)vhChoice;
        final Fragment_Choice_Image.Interface_Adapter_Choice_Image iFragmentChoiceImage = (Fragment_Choice_Image.Interface_Adapter_Choice_Image)iFragmentChoice;

        // Image one
        iFragmentChoiceImage.setImage(vhChoiceImage.ivIconOne, vhChoiceImage.ivIconTwo, position);

        // Url request one
        if (iFragmentChoiceImage.getUrlIconOne(position) != null) {
            vhChoiceImage.ivIconOne.setVisibility(View.VISIBLE);
            Log.i("IMMAGE: ", iFragmentChoiceImage.getUrlIconOne(position));
            ImageRequest request = new ImageRequest(iFragmentChoiceImage.getUrlIconOne(position),
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            vhChoiceImage.ivIconOne.setImageBitmap(bitmap);
                        }
                    }, 0, 0, ImageView.ScaleType.FIT_CENTER, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });


            rQueue.add(request);
            rQueue.getCache().clear();
        }

        // Url request two
        if (iFragmentChoiceImage.getUrlIconTwo(position) != null){
            vhChoiceImage.ivIconTwo.setVisibility(View.VISIBLE);
            ImageRequest request = new ImageRequest(iFragmentChoiceImage.getUrlIconTwo(position),
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            vhChoiceImage.ivIconTwo.setImageBitmap(bitmap);
                        }
                    }, 0, 0, ImageView.ScaleType.FIT_CENTER, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });


            rQueue.add(request);
            rQueue.getCache().clear();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return ((Fragment_Choice_Image.Interface_Adapter_Choice_Image)iFragmentChoice).getItemViewType(position);
    }
}
