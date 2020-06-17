package com.example.ericschumacher.bouncer.Adapter.List.Choice;

import android.util.Log;
import android.view.View;

import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_Choice_Image;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_Choice_New;
import com.example.ericschumacher.bouncer.Fragments.Choice.Image.Fragment_Choice_Image;
import com.example.ericschumacher.bouncer.Fragments.Choice.Image.Fragment_Choice_Image_Color;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Layout;

public class Adapter_List_Choice_Image_Color extends Adapter_List_Choice_Image{

    public Adapter_List_Choice_Image_Color(android.content.Context context, Interface_Adapter_List_Choice_Image_Color iFragmentChoice) {
        super(context, iFragmentChoice);
    }

    @Override
    public void onBindViewHolder(ViewHolder_Choice_New vhChoice, final int position) {


        final ViewHolder_Choice_Image vhChoiceImage = (ViewHolder_Choice_Image)vhChoice;
        final Interface_Adapter_List_Choice_Image_Color iFragmentChoiceImageColor = (Interface_Adapter_List_Choice_Image_Color)iFragmentChoice;

        // Text
        vhChoiceImage.tvName.setText(iFragmentChoiceImageColor.getName(position));

        // Image
        if (getItemViewType(position) == Fragment_Choice_Image_Color.TYPE_WITHOUT_IMAGE) {
            // Visibility
            vhChoiceImage.ivIconOne.setVisibility(View.VISIBLE);
            vhChoiceImage.ivIconOne.setImageBitmap(null);
            vhChoiceImage.ivIconOne.setImageDrawable(null);
            vhChoiceImage.ivIconTwo.setVisibility(View.GONE);

            // Color
            try {
                vhChoiceImage.ivIconOne.setBackgroundColor(iFragmentChoiceImageColor.getHexColor(position));
                vhChoiceImage.ivIconTwo.setBackgroundColor(iFragmentChoiceImageColor.getHexColor(position));
            } catch (IllegalArgumentException e) {
                Log.i("Error Color", iFragmentChoiceImageColor.getName(position));
            }
        }
        if (getItemViewType(position) == Fragment_Choice_Image_Color.TYPE_WITH_IMAGE) {
            // Visibility
            vhChoiceImage.ivIconOne.setVisibility(View.VISIBLE);
            vhChoiceImage.ivIconTwo.setVisibility(View.GONE);
            //vhChoiceImage.ivIconTwo.setVisibility(View.VISIBLE);
            Utility_Layout.setBackground(Context, vhChoiceImage.ivIconOne, R.color.color_transparent);
            iFragmentChoiceImageColor.setImage(vhChoiceImage.ivIconOne, vhChoiceImage.ivIconTwo, position);
            /*
            ImageRequest request1 = new ImageRequest(iFragmentChoiceImageColor.getUrlIconOne(position),
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            vhChoiceImage.ivIconOne.setImageBitmap(Utility_Camera.resizeToGivenWidth(Context, bitmap, 300));
                        }
                    }, 0, 0, ImageView.ScaleType.FIT_CENTER, null,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
            ImageRequest request2 = new ImageRequest(iFragmentChoiceImageColor.getUrlIconTwo(position),
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            Log.i("Width..", ""+bitmap.getWidth());
                            vhChoiceImage.ivIconTwo.setImageBitmap(Utility_Camera.resizeToGivenWidth(Context, bitmap, 300));
                            //h.ivIcon.setImageBitmap(bitmap);

                        }
                    }, 0, 0, ImageView.ScaleType.MATRIX, Bitmap.Config.RGB_565,
                    new Response.ErrorListener() {
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });
            rQueue.add(request1);
            rQueue.add(request2);
            rQueue.getCache().clear();
             */
        }

    }

    public interface Interface_Adapter_List_Choice_Image_Color extends Fragment_Choice_Image.Interface_Adapter_Choice_Image {
        public int getHexColor(int position);
    }
}
