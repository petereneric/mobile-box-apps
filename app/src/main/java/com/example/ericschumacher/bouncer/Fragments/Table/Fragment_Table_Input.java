package com.example.ericschumacher.bouncer.Fragments.Table;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.R;

import org.json.JSONArray;

public class Fragment_Table_Input extends Fragment_Table implements View.OnClickListener, TextWatcher {

    // Layout
    TextView tvSearchType;
    ImageView ivAction;
    EditText etSearch;
    View vDividerRight;


    @Override
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        tvSearchType = vLayout.findViewById(R.id.tvSearchType);
        ivAction = vLayout.findViewById(R.id.ivAction);
        vDividerRight = vLayout.findViewById(R.id.vDivicerRight);
        etSearch = vLayout.findViewById(R.id.etSearch);
    }

    public int getIdLayout() {
        return R.layout.fragment_table_new_input;
    }

    // Reset

    public void reset() {
        if (!etSearch.getText().toString().equals("")) {
            etSearch.setText("");
            return;
        }
        lData = new JSONArray();
        lAnn.clear();
        bHeader = false;
        update();
    }

    // Update

    public void update() {
        aTable.notifyDataSetChanged();
        updateLayout();
    }

    public void updateLayout() {
        if (etSearch.getText().length() > 0) {
            ivAction.setImageResource(R.drawable.ic_clear_24dp);
        } else {
            ivAction.setVisibility(View.GONE);
            vDividerRight.setVisibility(View.GONE);
            //ivAction.setImageResource(R.drawable.ic_beach_access_white_24dp);
        }
    }

    // Search

    public void onSearch() {

    }


    // OnClickListener & TextWatcher

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivAction:
                if (etSearch.getText().length() > 0) {
                    etSearch.setText("");
                }
        }
    }

    @Override
    public void afterTextChanged(Editable editable) {
        updateLayout();
        if (!editable.toString().equals("")) {
            onSearch();
        } else {
            reset();
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }


}
