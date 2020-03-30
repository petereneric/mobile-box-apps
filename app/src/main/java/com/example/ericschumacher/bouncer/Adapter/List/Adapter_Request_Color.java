package com.example.ericschumacher.bouncer.Adapter.List;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Request_Choice;
import com.example.ericschumacher.bouncer.Objects.Additive.Additive;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Camera;

import java.util.ArrayList;

public class Adapter_Request_Color extends Adapter_Request_Choice {

    private final static int TYPE_ADD = 1;
    private final static int TYPE_COLOR = 2;

    public Adapter_Request_Color(android.content.Context context, ArrayList<Additive> list, Interface_Request_Choice i) {
        super(context, list, i);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder_Request_Choice vh;
        if (viewType == TYPE_COLOR) {
            View layout = LayoutInflater.from(Context).inflate(R.layout.cardview_choice, parent, false);
            vh = new ViewHolder_Request_Choice(layout, new Interface_Click() {
                @Override
                public void onClick(int position) {
                    iRequestChoice.onChoice(position);
                }
            });
        } else {
            View layout = LayoutInflater.from(Context).inflate(R.layout.cardview_choice, parent, false);
            vh = new ViewHolder_Request_Choice(layout, new Interface_Click() {
                @Override
                public void onClick(int position) {
                    iRequestChoice.onAdd();
                }
            });
        }
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        holder.setIsRecyclable(false);
        final ViewHolder_Request_Choice h = (ViewHolder_Request_Choice) holder;
        if (position < lAdditive.size()) {
            Additive additive = lAdditive.get(position);
            h.tvName.setText(additive.getName());
            Log.i("onBind", additive.getName());
            if (((Color)additive).getkModelColor() > 0) {
                h.ivIconTwo.setVisibility(View.VISIBLE);
                ImageRequest request1 = new ImageRequest("http://svp-server.com/svp-gmbh/erp/files/images_model_color/" + Integer.toString(((Color)additive).getkModelColor()) + "_B.jpg",
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                h.ivIconTwo.setImageBitmap(Utility_Camera.resizeToGivenWidth(Context, bitmap, 300));
                            }
                        }, 0, 0, ImageView.ScaleType.FIT_CENTER, null,
                        new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        });
                ImageRequest request2 = new ImageRequest("http://svp-server.com/svp-gmbh/erp/files/images_model_color/" + Integer.toString(((Color)additive).getkModelColor()) + "_F.jpg",
                        new Response.Listener<Bitmap>() {
                            @Override
                            public void onResponse(Bitmap bitmap) {
                                Log.i("Width..", ""+bitmap.getWidth());
                                h.ivIcon.setImageBitmap(Utility_Camera.resizeToGivenWidth(Context, bitmap, 300));
                                //h.ivIcon.setImageBitmap(bitmap);

                            }
                        }, 0, 0, ImageView.ScaleType.MATRIX, Bitmap.Config.RGB_565,
                        new Response.ErrorListener() {
                            public void onErrorResponse(VolleyError error) {
                                error.printStackTrace();
                            }
                        });

                Log.i("Width..", ""+h.llCardview.getWidth());



                queue.add(request1);
                queue.add(request2);
                queue.getCache().clear();
            } else {
                try {
                    h.ivIconTwo.setVisibility(View.GONE);
                    h.ivIcon.setBackgroundColor(android.graphics.Color.parseColor(((Color) additive).getHexCode()));
                } catch (IllegalArgumentException e) {
                    Log.i("Error Color", additive.getName());
                }
            }


        } else {
            h.tvName.setVisibility(View.GONE);
            h.ivIcon.setBackgroundResource(R.drawable.ic_add_24dp);
        }
    }

    @Override
    public int getItemCount() {
        return lAdditive.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == lAdditive.size()) {
            return TYPE_ADD;
        } else {
            return TYPE_COLOR;
        }
    }
}
