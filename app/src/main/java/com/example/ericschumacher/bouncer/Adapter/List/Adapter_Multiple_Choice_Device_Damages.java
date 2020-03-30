package com.example.ericschumacher.bouncer.Adapter.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
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
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_Choice;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Interaction_Multiple_Choice_Device_Damages;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Long_Click;
import com.example.ericschumacher.bouncer.Objects.Object_Device_Damage;
import com.example.ericschumacher.bouncer.Objects.Object_Model_Damage;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Camera;

import java.util.ArrayList;

public class Adapter_Multiple_Choice_Device_Damages extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    // Types
    private final static int TYPE_DEVICE_DAMAGE = 0;
    private final static int TYPE_MODEL_DAMAGE = 1;
    private final static int TYPE_OTHER_DAMAGE = 2;
    private final static int TYPE_OVERBROKEN = 3;

    // Data
    ArrayList<Object_Model_Damage> lModelDamages;
    ArrayList<Object_Device_Damage> lDeviceDamages;
    int kDevice;

    // Else
    Context Context;
    RequestQueue queue;

    // Connection
    RequestQueue rQueue;

    // Interface
    Fragment_Interaction_Multiple_Choice_Device_Damages iFragment;

    public Adapter_Multiple_Choice_Device_Damages(android.content.Context context, ArrayList<Object_Device_Damage> lDeviceDamages, ArrayList<Object_Model_Damage> lModelDamages, int kDevice, Fragment_Interaction_Multiple_Choice_Device_Damages iFragment) {
        this.Context = context;
        queue = Volley.newRequestQueue(Context);
        this.iFragment = iFragment;
        this.lModelDamages = lModelDamages;
        for (Object_Model_Damage oModelDamage: lModelDamages) {
            Log.i("Model Damage: ", oModelDamage.getoDamage().getcName());
        }
        this.lDeviceDamages = lDeviceDamages;
        Log.i("Size Device Damages: ", ""+lDeviceDamages.size());
        for (Object_Device_Damage oDamage: lDeviceDamages) {
            //Log.i("Device Damage: ", oDamage.getoModelDamage().getoDamage().getcName());
            Log.i("Device Damage: ", ""+oDamage.getId());
        }
        this.kDevice = kDevice;
        rQueue = Volley.newRequestQueue(Context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, final int viewType) {
        View lViewHolder = LayoutInflater.from(Context).inflate(R.layout.item_choice_device_damages, parent, false);
        ViewHolder_Choice vhChoice = new ViewHolder_Choice(lViewHolder, new ViewHolder_Choice.Interface_Click() {
            @Override
            public void onClick(int position, ImageView ivIconOne) {
                if (viewType == TYPE_DEVICE_DAMAGE) {
                    Object_Device_Damage oDeviceDamage = lDeviceDamages.get(position);
                    switch (oDeviceDamage.gettStatus()) {
                        case Constants_Intern.STATUS_DAMAGE_REPAIRABLE:
                            oDeviceDamage.settStatus(Constants_Intern.STATUS_DAMAGE_REPAIRED);
                            break;
                        case Constants_Intern.STATUS_DAMAGE_REPAIRED:
                            oDeviceDamage.settStatus(Constants_Intern.STATUS_DAMAGE_REPAIRABLE);
                        case Constants_Intern.STATUS_DAMAGE_NOT_REPAIRABLE:
                            oDeviceDamage.settStatus(Constants_Intern.STATUS_DAMAGE_REPAIRABLE);
                            break;
                    }
                }
                if (viewType == TYPE_MODEL_DAMAGE) {
                    Object_Model_Damage oModelDamage = lModelDamages.get(position - lDeviceDamages.size());
                    Object_Device_Damage oDeviceDamage = new Object_Device_Damage(Context, kDevice, oModelDamage, Constants_Intern.STATUS_DAMAGE_REPAIRABLE);
                    lDeviceDamages.add(oDeviceDamage);
                    lModelDamages.remove(oModelDamage);
                }

                if (viewType == TYPE_OTHER_DAMAGE) {
                    ivIconOne.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.icon_damage_not_listed_highlighted));
                    lDeviceDamages.clear();
                    iFragment.clickOtherDamages();

                }
                if (viewType == TYPE_OVERBROKEN) {
                    ivIconOne.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.icon_damage_overbroken_highlighted));
                    lDeviceDamages.clear();
                    iFragment.clickOtherDamages();
                }
                notifyDataSetChanged();
            }
        }, new Interface_Long_Click() {
            @Override
            public boolean onLongClick(int position) {
                if (viewType == TYPE_DEVICE_DAMAGE) {
                    final Object_Device_Damage oDeviceDamage = lDeviceDamages.get(position);
                    switch (oDeviceDamage.gettStatus()) {
                        case Constants_Intern.STATUS_DAMAGE_REPAIRABLE:
                            AlertDialog.Builder builder = new AlertDialog.Builder(Context);
                            String[] lChoice = {Context.getString(R.string.not_repairable), Context.getString(R.string.delete)};
                            builder.setItems(lChoice, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            oDeviceDamage.settStatus(Constants_Intern.STATUS_DAMAGE_NOT_REPAIRABLE);
                                            notifyDataSetChanged();
                                            break;
                                        case 1:
                                            Object_Model_Damage oModelDamage = oDeviceDamage.getoModelDamage();
                                            lModelDamages.add(oModelDamage);
                                            lDeviceDamages.remove(oDeviceDamage);
                                            notifyDataSetChanged();
                                    }
                                }
                            });
                            builder.create().show();
                            return true;
                        case Constants_Intern.STATUS_DAMAGE_REPAIRED:
                            AlertDialog.Builder builder2 = new AlertDialog.Builder(Context);
                            String[] lChoice2 = {Context.getString(R.string.delete)};
                            builder2.setItems(lChoice2, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            Object_Model_Damage oModelDamage = oDeviceDamage.getoModelDamage();
                                            lModelDamages.add(oModelDamage);
                                            lDeviceDamages.remove(oDeviceDamage);
                                            notifyDataSetChanged();
                                    }
                                }
                            });
                            builder2.create().show();
                            return true;
                        case Constants_Intern.STATUS_DAMAGE_NOT_REPAIRABLE:
                            AlertDialog.Builder builder3 = new AlertDialog.Builder(Context);
                            String[] lChoice3 = {Context.getString(R.string.delete)};
                            builder3.setItems(lChoice3, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    switch (which) {
                                        case 0:
                                            Object_Model_Damage oModelDamage = oDeviceDamage.getoModelDamage();
                                            lModelDamages.add(oModelDamage);
                                            lDeviceDamages.remove(oDeviceDamage);
                                            notifyDataSetChanged();
                                        case 7:
                                    }
                                }
                            });
                            builder3.create().show();
                            return true;
                        default:
                            return false;
                    }
                } else {
                    return false;
                }
            }
        });
        return vhChoice;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final ViewHolder_Choice vhChoice = (ViewHolder_Choice) holder;

        // Data
        if (getItemViewType(position) == TYPE_DEVICE_DAMAGE) {
            Log.i("YEAAAH", "dddd");
            Object_Device_Damage oDeviceDamage = lDeviceDamages.get(position);

            // Text
            vhChoice.tvName.setText(oDeviceDamage.getoModelDamage().getoDamage().getcName()+" | "+oDeviceDamage.getStatusText(Context));
            vhChoice.tvName.setTextColor(ResourcesCompat.getColor(Context.getResources(), oDeviceDamage.getColor(), null));

            // Image

           ImageRequest rImage = new ImageRequest(oDeviceDamage.getoModelDamage().getoDamage().getlIcon(oDeviceDamage.gettStatus()), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    vhChoice.ivIconOne.setImageBitmap(bitmap);
                }
            }, 0, 0, ImageView.ScaleType.FIT_CENTER, null, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            queue.add(rImage);
            queue.add(rImage);
            queue.getCache().clear();

            // Frame
            vhChoice.llChoice.setBackgroundResource(oDeviceDamage.getFrame());

        }
        if (getItemViewType(position) == TYPE_MODEL_DAMAGE) {
            Object_Model_Damage oModelDamage = lModelDamages.get(position - lDeviceDamages.size());

            // Text
            vhChoice.tvName.setText(oModelDamage.getoDamage().getcName()+" | "+Context.getString(R.string.not_selected));

            // Image
            Log.i("JOOOO: ", oModelDamage.getoDamage().getlIcon(0));
            ImageRequest rImage = new ImageRequest(oModelDamage.getoDamage().getlIcon(0), new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    vhChoice.ivIconOne.setImageBitmap(Utility_Camera.resizeToGivenWidth(Context, bitmap, 500));
                }
            }, 0, 0, ImageView.ScaleType.FIT_CENTER, null, new Response.ErrorListener() {
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                }
            });

            queue.add(rImage);
            queue.add(rImage);
            queue.getCache().clear();

            // Frame
            vhChoice.llChoice.setBackgroundResource(R.drawable.background_rounded_corners_grey);
        }
        if (getItemViewType(position) == TYPE_OTHER_DAMAGE) {
            vhChoice.tvName.setText(Context.getString(R.string.damage_not_listed));
            vhChoice.llChoice.setBackgroundResource(R.drawable.background_rounded_corners_grey);
            vhChoice.ivIconOne.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.icon_damage_not_listed_not_highlighted));
        }
        if (getItemViewType(position) == TYPE_OVERBROKEN) {
            vhChoice.tvName.setText(Context.getString(R.string.damage_overbroken));
            vhChoice.llChoice.setBackgroundResource(R.drawable.background_rounded_corners_grey);
            vhChoice.ivIconOne.setImageDrawable(ContextCompat.getDrawable(Context, R.drawable.icon_damage_overbroken_not_highlighted));
        }


    }

    @Override
    public int getItemCount() {
        return lDeviceDamages.size() + lModelDamages.size() + 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (position < lDeviceDamages.size()) {
            return TYPE_DEVICE_DAMAGE;
        }
        if (position >= lDeviceDamages.size() && position < (lModelDamages.size() + lDeviceDamages.size())) {
            return TYPE_MODEL_DAMAGE;
        }
        if (position == (lModelDamages.size() + lDeviceDamages.size())) {
            return TYPE_OTHER_DAMAGE;
        }
        if (position == (lModelDamages.size() + lDeviceDamages.size() + 1)) {
            return TYPE_OVERBROKEN;
        }
        return 0;
    }

    public ArrayList<Object_Device_Damage> getlDeviceDamages() {
        return lDeviceDamages;
    }
}
