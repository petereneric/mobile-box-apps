package com.example.ericschumacher.bouncer.Activities.Manager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.example.ericschumacher.bouncer.Constants.Constants_Extern;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Box;
import com.example.ericschumacher.bouncer.Fragments.Object.Fragment_Object;
import com.example.ericschumacher.bouncer.Fragments.Record.Fragment_Record;
import com.example.ericschumacher.bouncer.Fragments.Table.Fragment_Table;
import com.example.ericschumacher.bouncer.Fragments.Table.Fragment_Table_Input_Collector;
import com.example.ericschumacher.bouncer.Interfaces.Interface_VolleyResult;
import com.example.ericschumacher.bouncer.Objects.Collection.Box;
import com.example.ericschumacher.bouncer.Objects.Collection.Collector;
import com.example.ericschumacher.bouncer.Objects.Collection.Record;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Keyboard;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Volley.Urls;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;
import com.example.ericschumacher.bouncer.Zebra.ManagerPrinter;

import org.json.JSONException;
import org.json.JSONObject;

public class Activity_Box extends AppCompatActivity implements View.OnClickListener, TextWatcher, TextView.OnEditorActionListener, Fragment_Object.Interface_Fragment_Object_Menu, Fragment_Box.Interface_Fragment_Box, Fragment_Record.Interface_Fragment_Record, Fragment_Table.Interface_Fragment_Table {

    // Layout
    Toolbar vToolbar;
    FrameLayout flInteraction;
    TextView tvSearchType;
    ImageView ivAction;
    EditText etSearch;
    View vDividerLeft;

    // Print
    public ManagerPrinter mPrinter;

    // Data
    Box oBox;

    // Fragments
    FragmentManager fManager;
    Fragment_Box fBox;
    Fragment_Record fRecord;

    // Connection
    Volley_Connection cVolley;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Print
        mPrinter = new ManagerPrinter(this);

        // Fragments
        fManager = getSupportFragmentManager();

        // Layout
        setLayout();

        // Connection
        cVolley = new Volley_Connection(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initiateFragments();
        removeFragments();
        updateLayout();
        setKeyboard(Constants_Intern.SHOW_KEYBOARD);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Print
        if (mPrinter == null) {
            mPrinter = new ManagerPrinter(this);
        }

        mPrinter.connect();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPrinter != null) mPrinter.disconnect();
    }

    // Fragments

    public void initiateFragments() {
        fBox = (Fragment_Box) fManager.findFragmentById(R.id.fBox);
        //fBox.lMenu.setVisibility(View.GONE);
        fRecord = (Fragment_Record) fManager.findFragmentById(R.id.fRecord);
    }

    public void showFragment(Fragment fragment, Bundle bData, String cTag, Boolean bKeyboard) {
        setKeyboard(bKeyboard);
        fragment.setArguments(bData);
        fManager.beginTransaction().replace(R.id.flInteraction, fragment, cTag).commit();
    }

    public void removeFragment(String cTag) {
        fManager.beginTransaction().remove(fManager.findFragmentByTag(cTag)).commit();
    }

    public void removeFragments() {
        getSupportFragmentManager().beginTransaction().hide(fBox).commit();
        getSupportFragmentManager().beginTransaction().hide(fRecord).commit();
    }

    // Layout

    public void setLayout() {
        setContentView(getIdLayout());
        vToolbar = findViewById(R.id.vToolbar);
        flInteraction = findViewById(R.id.flInteraction);
        tvSearchType = findViewById(R.id.tvSearchType);
        ivAction = findViewById(R.id.ivAction);
        etSearch = findViewById(R.id.etSearch);
        vDividerLeft = findViewById(R.id.vDivicerLeft);

        etSearch.setSelectAllOnFocus(true);

        // Toolbar
        setSupportActionBar(vToolbar);
        getSupportActionBar().setTitle(getString(R.string.activity_box_manager));
        vToolbar.setBackgroundColor(ResourcesCompat.getColor(getResources(), R.color.color_primary, null));
        vToolbar.setTitleTextColor(ResourcesCompat.getColor(getResources(), R.color.color_white, null));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // EditText
        tvSearchType.setText(getString(R.string.id_box));
        etSearch.setHint(getString(R.string.enter_scan_id_box));
        etSearch.setInputType(InputType.TYPE_CLASS_TEXT);
        etSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});

        // OnClickListener, TextWatcher, OnEditorActionListener
        tvSearchType.setOnClickListener(this);
        ivAction.setOnClickListener(this);
        etSearch.setOnClickListener(this);
        etSearch.addTextChangedListener(this);
        etSearch.setOnEditorActionListener(this);
    }

    // Update

    public void updateLayout() {
        // Fragments
        if (oBox != null) {
            getSupportFragmentManager().beginTransaction().show(fBox).commit();
            fBox.updateLayout();
        } else {
            getSupportFragmentManager().beginTransaction().hide(fBox).commit();
        }
        if (oBox != null && oBox.getoRecord() != null) {
            getSupportFragmentManager().beginTransaction().show(fRecord).commit();
            fRecord.updateLayout();
        } else {
            getSupportFragmentManager().beginTransaction().hide(fRecord).commit();
        }
    }

    public int getIdLayout() {
        return R.layout.activity_box;
    }

    // Keyboard

    public void setKeyboard(Boolean bKeyboard) {
        if (bKeyboard != null) {
            if (bKeyboard == Constants_Intern.SHOW_KEYBOARD) {
                Utility_Keyboard.openKeyboard(this, etSearch);
                etSearch.requestFocus();
            } else {
                Utility_Keyboard.hideKeyboardFrom(this, etSearch);
                vDividerLeft.requestFocus();
            }
        }
    }


    // Base & Reset

    public void base(Boolean bKeyboard) {
        updateLayout();
    }

    public void reset() {
        if (!etSearch.getText().toString().equals("")) {
            etSearch.setText("");
            return;
        }
        oBox = null;
        setKeyboard(Constants_Intern.SHOW_KEYBOARD);
        updateLayout();
    }


    // Search

    public void onSearch() {
        final String cSearchSaved = etSearch.getText().toString();
        cVolley.getResponse(Request.Method.GET, Urls.URL_GET_BOX_BY_ID + etSearch.getText().toString().substring(0, etSearch.getText().toString().length()-1), null, new Interface_VolleyResult() {
            @Override
            public void onResult(JSONObject oJson) {
                if (cSearchSaved.equals(etSearch.getText().toString())) {
                    if (Volley_Connection.successfulResponse(oJson)) {
                        try {
                            oBox = new Box(Activity_Box.this, oJson.getJSONObject(Constants_Extern.OBJECT_BOX));
                            returnFromSearch();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Utility_Toast.show(Activity_Box.this, R.string.id_unknown);
                        reset();
                    }
                }
            }
        });
    }

    public void returnFromSearch() {
        base(null);
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (!editable.toString().equals("")) {
            if (editable.toString().length() > 1 && editable.toString().substring(editable.toString().length()-1).equals("e")) {
                onSearch();
                setKeyboard(Constants_Intern.CLOSE_KEYBOARD);
            }
        } else {
            reset();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvSearchType:
            case R.id.etSearch:
                break;
            case R.id.ivAction:
                reset();
        }
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        switch (textView.getId()) {
            case R.id.etSearch:
                if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                    setKeyboard(Constants_Intern.CLOSE_KEYBOARD);
                }
                return true;
        }
        return false;
    }

    @Override
    public Box getBox() {
        return oBox;
    }

    @Override
    public Record getRecord() {
        if (oBox != null) {
            return oBox.getoRecord();
        } else {
            return null;
        }
    }

    @Override
    public void returnMenu(int tAction, String cTag) {
        switch (tAction) {
            case Constants_Intern.TYPE_ACTION_MENU_PRINT:
                switch (cTag) {
                    case Constants_Intern.FRAGMENT_BOX:
                        mPrinter.printBox(oBox);
                        mPrinter.printBox(oBox);
                        break;
                }
                break;
            case Constants_Intern.TYPE_ACTION_MENU_ADD:
                switch (cTag) {
                    case Constants_Intern.FRAGMENT_BOX:
                        if (oBox.getoRecord() == null) {
                            showFragment(new Fragment_Table_Input_Collector(), null, Constants_Intern.FRAGMENT_TABLE_INPUT_COLLECTOR, null);
                        }
                        break;
                }
                break;
            case Constants_Intern.TYPE_ACTION_MENU_DONE:
                switch (cTag) {
                    case Constants_Intern.FRAGMENT_RECORD:
                        cVolley.execute(Request.Method.PUT, Urls.URL_RECORD_FINISH+oBox.getoRecord().getId(), null);
                        oBox.setoRecord(null);
                        updateLayout();
                        break;
                }
                break;
            case Constants_Intern.TYPE_ACTION_MENU_DELETE:
                switch (cTag) {
                    case Constants_Intern.FRAGMENT_BOX:
                        cVolley.execute(Request.Method.DELETE, Urls.URL_DELETE_BOX_DELETE+oBox.getkId(), null);
                        oBox.setoRecord(null);
                        reset();
                        break;
                    case Constants_Intern.FRAGMENT_RECORD:
                        cVolley.execute(Request.Method.DELETE, Urls.URL_RECORD_DELETE+oBox.getoRecord().getId(), null);
                        oBox.setoRecord(null);
                        updateLayout();
                        break;
                }
                break;
        }
    }

    // Menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_box, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.mAdd:
                reset();
                cVolley.getResponse(Request.Method.PUT, Urls.URL_PUT_BOX_ADD, null, new Interface_VolleyResult() {
                    @Override
                    public void onResult(JSONObject oJson) throws JSONException {
                        if (Volley_Connection.successfulResponse(oJson)) {
                            oBox = new Box(Activity_Box.this, oJson.getJSONObject(Constants_Extern.OBJECT_BOX));
                            updateLayout();
                            setKeyboard(Constants_Intern.CLOSE_KEYBOARD);
                        }
                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void returnTable(final String cTag, final JSONObject jsonObject) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_TABLE_INPUT_COLLECTOR:
                if (oBox.getoRecord() == null) {
                    cVolley.getResponse(Request.Method.PUT, Urls.URL_RECORD_ADD, null, new Interface_VolleyResult() {
                        @Override
                        public void onResult(JSONObject oJson) throws JSONException {
                            if (Volley_Connection.successfulResponse(oJson)) {
                                oBox.setoRecord(new Record(Activity_Box.this, oJson.getJSONObject(Constants_Extern.OBJECT_RECORD)));
                                oBox.getoRecord().setoCollector(new Collector(Activity_Box.this, jsonObject));
                            }
                            removeFragment(cTag);
                            updateLayout();
                        }
                    });
                }
                break;
        }
    }


}
