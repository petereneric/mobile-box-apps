package com.example.ericschumacher.bouncer.Adapter.List;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Adapter_List extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_LIST = 1;


    Context Context;
    JSONArray jListHeader;
    JSONArray jListData;
    JSONArray jListWeight;
    Interface_Click iClick;

    public Adapter_List(Context context, Interface_Click iClick, JSONObject jObject) throws JSONException {
        jListHeader = jObject.getJSONArray(Constants_Intern.JSON_LIST_HEADER);
        jListData = jObject.getJSONArray(Constants_Intern.JSON_LIST);
        jListWeight = jObject.getJSONArray(Constants_Intern.LAYOUT_WEIGHT);
        this.iClick = iClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LinearLayout linearLayout = new LinearLayout(Context);
        LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT
        );
        linearLayout.setLayoutParams(llp);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        ArrayList<TextView> lTextView = new ArrayList<>();
        for (int i=0; i<jListWeight.length(); i++) {
            try {
                JSONObject jWeight = jListWeight.getJSONObject(i);
                TextView textView = new TextView(Context);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        0, LinearLayout.LayoutParams.WRAP_CONTENT, jWeight.getInt(Constants_Intern.LAYOUT_WEIGHT));
                textView.setLayoutParams(lp);
                textView.setTextAppearance(Context, R.style.tv_itemRow);
                linearLayout.addView(textView);

                lTextView.add(textView);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return new ViewHolder_List(linearLayout, lTextView, new Interface_Click() {
            @Override
            public void onClick(int position) {

            }
        });
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder_List vhList = (ViewHolder_List)holder;
        if (holder.getItemViewType() == TYPE_LIST) {
            JSONArray jArray = null;
            try {
                jArray = jListData.getJSONArray(position-1);
                for (int i = 0; i<jArray.length(); i++) {
                    JSONObject jObject = jArray.getJSONObject(i);
                    TextView tv = vhList.lTextView.get(i);
                    tv.setText(jObject.getString(Constants_Intern.TEXT_VALUE));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            for (int i = 0; i<jListHeader.length(); i++) {
                try {
                    JSONObject jObject = jListHeader.getJSONObject(i);
                    TextView tv = vhList.lTextView.get(i);
                    tv.setText(jObject.getString(Constants_Intern.TEXT_VALUE));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_LIST;
        }
    }

    @Override
    public int getItemCount() {
        return jListData.length()+1;
    }

    private class ViewHolder_List extends RecyclerView.ViewHolder {

        Interface_Click iClick;
        ArrayList<TextView> lTextView = new ArrayList<>();

        public ViewHolder_List(View itemView, ArrayList<TextView> lTextView, Interface_Click iClick) {
            super(itemView);

            this.lTextView = lTextView;
            this.iClick = iClick;
        }
    }
}
