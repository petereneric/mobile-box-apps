package com.example.ericschumacher.bouncer.Fragments.Edit;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Edit_Model_Color;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_Edit_Model_Damages;
import com.example.ericschumacher.bouncer.Adapter.List.Adapter_List;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_List;
import com.example.ericschumacher.bouncer.Adapter.List.ViewHolder.ViewHolder_ModelColor;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Fragment_Dialog.Fragment_Dialog_Simple;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Click;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Model_New_New;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Additive.Color;
import com.example.ericschumacher.bouncer.Objects.ModelColor;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Volley.Urls;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Fragment_Edit_Model_Color extends Fragment_Edit implements Interface_Click {

    // Data
    ArrayList<Color> lColor = null;

    // Interfaces
    public Interface_Model_New_New iModel;

    // Adapter
    public Adapter_Edit_Model_Color mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Reset Measure
        getActivity().getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0).edit().putInt(Constants_Intern.WIDTH_LIST_MODEL_COLOR, 0).commit();
        getActivity().getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0).edit().putInt(Constants_Intern.WIDTH_LIST_MODEL_COLOR_BACKGROUND, 0).commit();
        getActivity().getSharedPreferences(Constants_Intern.SHARED_PREFERENCES, 0).edit().putInt(Constants_Intern.HEIGHT_LIST_MODEL_COLOR_BACKGROUND, 0).commit();


        // Interfaces
        iModel = (Interface_Model_New_New) getActivity();

        // Super
        super.onCreateView(inflater, container, savedInstanceState);

        // Data
        load();

        return vLayout;
    }

    @Override
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);
        // RecyclerView
        rvList.setLayoutManager(new GridLayoutManager(getActivity(), 5));

        // Adapter
        mAdapter = new Adapter_Edit_Model_Color(getActivity(), iModel.getModel().getlModelColor(), lColor, this);
        rvList.setAdapter(mAdapter);
        setSwipe();
    }

    private void setSwipe() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Item
                if (mAdapter.getItemViewType(viewHolder.getAdapterPosition()) == Constants_Intern.ITEM && !iModel.getModel().getlModelColor().get(viewHolder.getAdapterPosition()).isbMatch()) {
                    switch (direction) {
                        case ItemTouchHelper.RIGHT:
                            ModelColor oModelColor = iModel.getModel().getlModelColor().get(viewHolder.getAdapterPosition());
                            cVolley.execute(Request.Method.DELETE, Urls.URL_DELETE_MODEL_COLOR+oModelColor.getId(), null);
                            iModel.getModel().getlModelColor().remove(oModelColor);
                            mAdapter.update(iModel.getModel().getlModelColor(), lColor);
                            break;
                    }
                }
            }

            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (mAdapter.getItemViewType(viewHolder.getAdapterPosition()) == Constants_Intern.ITEM && !iModel.getModel().getlModelColor().get(viewHolder.getAdapterPosition()).isbMatch()) {
                    final View clForeground = ((ViewHolder_ModelColor) viewHolder).vMain;
                    final View clBackground = ((ViewHolder_ModelColor) viewHolder).rlBackground;
                    clBackground.setVisibility(View.VISIBLE);
                    getDefaultUIUtil().onDraw(c, recyclerView, clForeground, dX, dY, actionState, isCurrentlyActive);
                }
            }

            @Override
            public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                if (mAdapter.getItemViewType(viewHolder.getAdapterPosition()) == Constants_Intern.ITEM && !iModel.getModel().getlModelColor().get(viewHolder.getAdapterPosition()).isbMatch()) {
                    final View foregroundView = ((ViewHolder_ModelColor) viewHolder).vMain;
                    getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
                }
            }

            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (mAdapter.getItemViewType(viewHolder.getAdapterPosition()) == Constants_Intern.ITEM) {
                    final View foregroundView = ((ViewHolder_ModelColor) viewHolder).vMain;
                    getDefaultUIUtil().clearView(foregroundView);
                    final View backgroundView = ((ViewHolder_ModelColor) viewHolder).rlBackground;
                    backgroundView.setVisibility(View.INVISIBLE);
                }
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rvList);
    }


    @Override
    public void onClick(int position) {
        // Item
        if (mAdapter.getItemViewType(position) == Constants_Intern.ITEM) {
            ModelColor oModelColor = iModel.getModel().getlModelColor().get(position);

            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(getString(R.string.default_exploitation));
            String[] items = {getString(R.string.automatic), getString(R.string.recycling), getString(R.string.reuse), getString(R.string.reset)};
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    switch (i) {
                        case 0: // Automatic
                            oModelColor.setbAutoExploitation(true);
                            oModelColor.settExploitation(Constants_Intern.EXPLOITATION_NULL);
                            break;
                        case 1: // Recycling
                            oModelColor.setbAutoExploitation(false);
                            oModelColor.settExploitation(Constants_Intern.EXPLOITATION_RECYCLING);
                            break;
                        case 2: // Reuse
                            oModelColor.setbAutoExploitation(false);
                            oModelColor.settExploitation(Constants_Intern.EXPLOITATION_REUSE);
                            break;
                        case 3: // Reuse
                            oModelColor.setbAutoExploitation(false);
                            oModelColor.settExploitation(Constants_Intern.EXPLOITATION_NULL);
                            break;
                    }
                    mAdapter.update(iModel.getModel().getlModelColor(), lColor);
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }

        // Add
        if (mAdapter.getItemViewType(position) == Constants_Intern.ADD) {
            lColor = new ArrayList<>();
            cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_COLOR_LIST_OTHERS + iModel.getModel().getkModel(), null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    JSONArray jsonArray = oJson.getJSONArray("lColor");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonColor = jsonArray.getJSONObject(i);
                        Color oColor = new Color(getActivity(), jsonColor);
                        lColor.add(oColor);
                    }
                    mAdapter.update(iModel.getModel().getlModelColor(), lColor);
                }
            });
        }

        // Add Color
        if (mAdapter.getItemViewType(position) == Constants_Intern.ADD_COLOR) {
            Color oColor = lColor.get(position-iModel.getModel().getlModelColor().size());
            cVolley.getResponse(Request.Method.PUT, Urls.URL_ADD_MODEL_COLOR + iModel.getModel().getkModel() + "/" + oColor.getId(), null, new Interface_VolleyResult() {
                @Override
                public void onResult(JSONObject oJson) throws JSONException {
                    iModel.getModel().getlModelColor().add(new ModelColor(getActivity(), oJson));
                    lColor = null;
                    mAdapter.update(iModel.getModel().getlModelColor(), lColor);
                }
            });
        }

    }

    private void load() {
        iModel.getModel().getlModelColor().clear();
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_MODEL_COLOR_LIST + iModel.getModel().getkModel(), null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) throws JSONException {
                JSONArray lJson = oJson.getJSONArray("lModelColor");
                for (int i = 0; i < lJson.length(); i++) {
                    JSONObject json = lJson.getJSONObject(i);
                    ModelColor oModelColor = new ModelColor(getActivity(), json);
                    iModel.getModel().getlModelColor().add(oModelColor);
                }
                mAdapter.update(iModel.getModel().getlModelColor(), lColor);
            }
        });
    }
}
