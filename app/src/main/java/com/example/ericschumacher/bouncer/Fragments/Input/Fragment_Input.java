package com.example.ericschumacher.bouncer.Fragments.Input;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Adapter.List.Choice.Adapter_List_Choice;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Keyboard;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import java.util.ArrayList;

public class Fragment_Input extends Fragment implements View.OnClickListener, TextWatcher, Adapter_List_Choice.Interface_Adapter_Choice {

    // Layout
    View vLayout;
    TextView tvTitle;
    ImageView ivUnknown;
    ImageView ivCommit;
    EditText etSearch;
    RecyclerView rvSearch;

    // Data
    public Bundle bData;
    String cTitle;
    ArrayList<String> lSearch = new ArrayList<>();

    // Connection
    Volley_Connection cVolley;

    // Adapter
    Adapter_List_Choice aSearch;

    // Interface
    public Interface_Input iInput;

    // Constants
    public static final int SPAN_COUNT = 4;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Arguments
        bData = getArguments();
        cTitle = bData.getString(Constants_Intern.TITLE);

        // Connection
        cVolley = new Volley_Connection(getActivity());

        // Adapter
        aSearch = new Adapter_List_Choice(getActivity(), this);

        // Interface
        iInput = (Interface_Input)getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        // Layout
        setLayout(inflater, container);

        // RecyclerView
        rvSearch.setLayoutManager(new GridLayoutManager(getActivity(), SPAN_COUNT));
        rvSearch.setAdapter(aSearch);

        return vLayout;
    }

    @Override
    public void onResume() {
        super.onResume();
        Utility_Keyboard.openKeyboard(getActivity(), etSearch);
    }

    @Override
    public void onPause() {
        super.onPause();
        Utility_Keyboard.hideKeyboardFrom(getActivity(), etSearch);
    }

    // Layout
    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        // Initiate
        vLayout = inflater.inflate(R.layout.fragment_input, container, false);
        tvTitle = vLayout.findViewById(R.id.tvTitle);
        ivUnknown = vLayout.findViewById(R.id.ivUnknown);
        ivCommit = vLayout.findViewById(R.id.ivCommit);
        etSearch = vLayout.findViewById(R.id.etSearch);
        rvSearch = vLayout.findViewById(R.id.rvSearch);

        // OnClickListener & TextWatcher
        ivUnknown.setOnClickListener(this);
        ivCommit.setOnClickListener(this);
        etSearch.addTextChangedListener(this);

        // Fill with arguments
        tvTitle.setText(cTitle);
    }

    // Class methods
    public void onSearchChanged(String cSearch) {
        if (cSearch.equals("")) {
            clearSearch();
            return;
        }
    }

    public void clearSearch() {
        lSearch.clear();
        aSearch.notifyDataSetChanged();
    }

    // Interface
    public void onUnknown() {
        iInput.unknownInput(getTag());
    }

    public void onCommit() {
        if (!etSearch.getText().toString().equals("")) {
            iInput.returnInput(getTag(), etSearch.getText().toString());
        } else {
            Utility_Toast.show(getActivity(), R.string.invalid_input);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivUnknown:
                onUnknown();
                break;
            case R.id.ivCommit:
                onCommit();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        onSearchChanged(charSequence.toString());
    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    // Interface FragmentChoice
    @Override
    public void onAdapterClick(int position) {
        etSearch.setText(lSearch.get(position));
    }

    @Override
    public Integer getViewHolderLayout() {
        return R.layout.item_choice;
    }

    @Override
    public String getName(int position) {
        return lSearch.get(position);
    }

    @Override
    public Integer getItemCount() {
        return lSearch.size();
    }

    public interface Interface_Input {
        public void returnInput(String cTag, String cInput);
        public void unknownInput(String cTag);
    }
}
