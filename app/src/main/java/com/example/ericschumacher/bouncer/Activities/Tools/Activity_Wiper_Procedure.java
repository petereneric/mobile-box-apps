package com.example.ericschumacher.bouncer.Activities.Tools;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Activities.Activity_Authentication;
import com.example.ericschumacher.bouncer.Constants.Constants_Intern;
import com.example.ericschumacher.bouncer.Fragments.Choice.Fragment_Choice;
import com.example.ericschumacher.bouncer.Fragments.Choice.Image.Fragment_Choice_Image_Manufacturer;
import com.example.ericschumacher.bouncer.Fragments.List.Fragment_List_Wipe_Procedure;
import com.example.ericschumacher.bouncer.Fragments.List.Fragment_List_Wipeprocedure;
import com.example.ericschumacher.bouncer.Interfaces.Interface_Wipeprocedure;
import com.example.ericschumacher.bouncer.Objects.Additive.Manufacturer;
import com.example.ericschumacher.bouncer.Objects.Wipeprocedure;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Image;
import com.example.ericschumacher.bouncer.Utilities.Utility_Keyboard;
import com.example.ericschumacher.bouncer.Utilities.Utility_Layout;
import com.example.ericschumacher.bouncer.Utilities.Utility_Toast;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

import java.util.ArrayList;

public class Activity_Wiper_Procedure extends Activity_Authentication implements View.OnClickListener, TextWatcher, Interface_Wipeprocedure, Fragment_Choice.Interface_Choice, TextView.OnEditorActionListener, View.OnFocusChangeListener {

    // VALUES & VARIABLES

    // Constants
    private static final int WIPEPROCEDURE_NONE = 0;
    private static final int WIPEPROCEDURE_LIST = 1;
    private static final int WIPEPROCEDURE_LIST_EXIST = 2;
    private static final int WIPEPROCEDURE_SELECTED = 3;

    // Data
    ArrayList<Wipeprocedure> lWipeprocedure = new ArrayList<>();
    Wipeprocedure oWipeprocedure = null;

    // Layout
    Toolbar vToolbar;
    TextView tvSearchType;
    ImageView ivSearchAction;
    View vDividerRight;
    EditText etSearch;
    FrameLayout flInteraction;
    FloatingActionButton vFab;

    // Fragment
    FragmentManager mFragmentManager;

    // Connection
    Volley_Connection cVolley;


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LIFE-CYCLE

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Connection
        cVolley = new Volley_Connection(this);

        // Fragment
        mFragmentManager = getSupportFragmentManager();

        // Layout
        setLayout();
    }

    @Override
    protected void onResume() {
        super.onResume();
        switch (getActionType()) {
            case WIPEPROCEDURE_NONE:
            case WIPEPROCEDURE_LIST:
            case WIPEPROCEDURE_LIST_EXIST:
                loadWipeprocedures();
                break;
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PRIVATE

    // Layout
    private void setLayout() {
        setContentView(R.layout.activity_wiper_procedure);

        vToolbar = findViewById(R.id.vToolbar);
        tvSearchType = findViewById(R.id.tvSearchType);
        ivSearchAction = findViewById(R.id.ivAction);
        vDividerRight = findViewById(R.id.vDividerRight);
        etSearch = findViewById(R.id.etSearch);
        flInteraction = findViewById(R.id.flInteraction);
        vFab = findViewById(R.id.vFab);

        // EditText
        etSearch.setHint(getString(R.string.enter_wipeprocedure));
        etSearch.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_WORDS);
        etSearch.setFilters(new InputFilter[]{new InputFilter.LengthFilter(100)});
        etSearch.setOnFocusChangeListener(this);

        // Listener
        ivSearchAction.setOnClickListener(this);
        vFab.setOnClickListener(this);
        etSearch.addTextChangedListener(this);
        etSearch.setOnEditorActionListener(this);

        // SupportActionBar
        setSupportActionBar(vToolbar);
        getSupportActionBar().setTitle(getString(R.string.wiper));
        getSupportActionBar().setSubtitle(getString(R.string.wipeprocedures));
        vToolbar.setTitleTextColor(ResourcesCompat.getColor(getResources(), R.color.color_white, null));
        vToolbar.setSubtitleTextColor(ResourcesCompat.getColor(getResources(), R.color.color_white, null));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void updateLayout() {
        ivSearchAction.setVisibility(View.VISIBLE);
        vDividerRight.setVisibility(View.VISIBLE);
        vFab.setVisibility(View.VISIBLE);
        tvSearchType.setText(getString(R.string.name));
        switch (getActionType()) {
            case WIPEPROCEDURE_NONE:
                removeFragment(Constants_Intern.FRAGMENT_LIST_WIPE_PROCEDURE);
                ivSearchAction.setVisibility(View.GONE);
                vDividerRight.setVisibility(View.GONE);
                vFab.setVisibility(View.GONE);
            case WIPEPROCEDURE_LIST:
                Utility_Image.setImageResource(this, ivSearchAction, R.drawable.ic_add_24dp, R.color.color_primary);
                updateFragmentWipeprocedure();
                break;
            case WIPEPROCEDURE_LIST_EXIST:
                removeFragment(Constants_Intern.FRAGMENT_LIST_WIPE_PROCEDURE);
                Utility_Image.setImageResource(this, ivSearchAction, R.drawable.ic_add_24dp, R.color.color_divider);
                updateFragmentWipeprocedure();
                break;
            case WIPEPROCEDURE_SELECTED:
                removeFragment(Constants_Intern.FRAGMENT_LIST_WIPEPROCEDURE);
                Utility_Image.setImageResource(this, ivSearchAction, R.drawable.ic_change, R.color.color_primary);
                tvSearchType.setText(oWipeprocedure.getoManufacturer().getName());
                etSearch.setText(oWipeprocedure.getcName());
                updateFragmentWipeProcedure();
                break;
        }
    }

    // Data
    private void loadWipeprocedures() {
        Wipeprocedure.readByName(this, cVolley, etSearch.getText().toString(), new Wipeprocedure.Interface_Read_byName() {
            @Override
            public void read(ArrayList<Wipeprocedure> lWipeprocedure_) {
                lWipeprocedure = lWipeprocedure_;
                updateLayout();
            }
        });
    }

    // Fragment
    private void updateFragmentWipeprocedure() {
        if (mFragmentManager.findFragmentByTag(Constants_Intern.FRAGMENT_LIST_WIPEPROCEDURE) != null) {
            ((Fragment_List_Wipeprocedure)mFragmentManager.findFragmentByTag(Constants_Intern.FRAGMENT_LIST_WIPEPROCEDURE)).update(lWipeprocedure);
        } else {
            Fragment_List_Wipeprocedure fWipeprocedure = new Fragment_List_Wipeprocedure();
            mFragmentManager.beginTransaction().add(R.id.flInteraction, fWipeprocedure, Constants_Intern.FRAGMENT_LIST_WIPEPROCEDURE).commit();
        }
    }

    private void updateFragmentWipeProcedure() {
        if (mFragmentManager.findFragmentByTag(Constants_Intern.FRAGMENT_LIST_WIPE_PROCEDURE) != null) {
            ((Fragment_List_Wipe_Procedure)mFragmentManager.findFragmentByTag(Constants_Intern.FRAGMENT_LIST_WIPE_PROCEDURE)).update();
        } else {
            Fragment_List_Wipe_Procedure fWipeProcedure = new Fragment_List_Wipe_Procedure();
            mFragmentManager.beginTransaction().add(R.id.flInteraction, fWipeProcedure, Constants_Intern.FRAGMENT_LIST_WIPE_PROCEDURE).commit();
        }
    }

    private void removeFragment(String tag) {
        if (mFragmentManager.findFragmentByTag(tag) != null) {
            mFragmentManager.beginTransaction().remove(mFragmentManager.findFragmentByTag(tag)).commit();
        }
    }

    private void removeFragments() {
        if (mFragmentManager.findFragmentByTag(Constants_Intern.FRAGMENT_LIST_WIPEPROCEDURE) != null) {
            mFragmentManager.beginTransaction().remove(mFragmentManager.findFragmentByTag(Constants_Intern.FRAGMENT_LIST_WIPEPROCEDURE)).commit();
        }
        if (mFragmentManager.findFragmentByTag(Constants_Intern.FRAGMENT_LIST_WIPE_PROCEDURE) != null) {
            mFragmentManager.beginTransaction().remove(mFragmentManager.findFragmentByTag(Constants_Intern.FRAGMENT_LIST_WIPE_PROCEDURE)).commit();
        }
    }

    private void showFragment(Fragment fragment, String tag) {
        removeFragments();
        mFragmentManager.beginTransaction().add(R.id.flInteraction, fragment, tag).commit();
    }

    // Reset
    private void resetData() {
        oWipeprocedure = null;
        lWipeprocedure = new ArrayList<>();
    }

    private void hardReset() {
        removeFragments();
        resetData();
        etSearch.setText("");
    }

    // ActionType
    private int getActionType() {
        if (oWipeprocedure == null && etSearch.getText().length() == 0) {
            return WIPEPROCEDURE_NONE;
        }
        if (oWipeprocedure == null && etSearch.getText().length() > 0) {
            for (Wipeprocedure wipeprocedure : lWipeprocedure) {
                if (wipeprocedure.getcName().equals(etSearch.getText().toString())) {
                    return WIPEPROCEDURE_LIST_EXIST;
                }
            }
            return WIPEPROCEDURE_LIST;
        }
        if (oWipeprocedure != null) {
            return WIPEPROCEDURE_SELECTED;
        }
        return -1;
    }

    // Click & TextChange
    private void onClick_SearchAction() {
        switch (getActionType()) {
            case WIPEPROCEDURE_LIST:
                Fragment_Choice_Image_Manufacturer fManufacturer = new Fragment_Choice_Image_Manufacturer();
                Bundle data = new Bundle();
                data.putString(Constants_Intern.TITLE, getString(R.string.manufacturer));
                fManufacturer.setArguments(data);
                showFragment(fManufacturer, Constants_Intern.FRAGMENT_CHOICE_IMAGE_MANUFACTURER);
                break;
            case WIPEPROCEDURE_SELECTED:
                Utility_Toast.show(this, R.string.model_list_not_implemented);
                break;
        }
    }

    private void onClick_Fab() {
        hardReset();
    }

    private void afterTextChanged(String text) {
        // Data
        switch (getActionType()) {
            case WIPEPROCEDURE_NONE:
            case WIPEPROCEDURE_LIST_EXIST:
            case WIPEPROCEDURE_LIST:
                loadWipeprocedures();
                break;
            case WIPEPROCEDURE_SELECTED:
                oWipeprocedure.setcName(text);
                break;
        }
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // PUBLIC

    public Wipeprocedure getWipeprocedure() {
        return oWipeprocedure;
    }

    public void setWipeprocedure(Wipeprocedure wipeprocedure) {
        resetData();
        removeFragments();
        oWipeprocedure = wipeprocedure;
        updateLayout();
    }

    @Override
    public void deleteWipeprocedure(int nPosition) {
        Wipeprocedure wipeprocedure = lWipeprocedure.get(nPosition);
        if (oWipeprocedure != null && wipeprocedure.getId() == oWipeprocedure.getId()) oWipeprocedure = null;

        lWipeprocedure.remove(wipeprocedure);
        wipeprocedure.delete();
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LISTENER

    // Click
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivAction:
                onClick_SearchAction();
                break;
            case R.id.vFab:
                onClick_Fab();
                break;
        }
    }

    // Text
    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        afterTextChanged(editable.toString());
    }

    @Override
    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        switch (textView.getId()) {
            case R.id.etSearch:
                if ((keyEvent != null && (keyEvent.getKeyCode() == KeyEvent.KEYCODE_ENTER)) || (i == EditorInfo.IME_ACTION_DONE)) {
                    Utility_Keyboard.hideKeyboardFrom(this, etSearch);
                }
                return true;
        }
        return false;
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // LISTENER

    @Override
    public ArrayList<Wipeprocedure> getlWipeprocedures() {
        return lWipeprocedure;
    }


    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // MENU

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
    // INTERFACE

    @Override
    public void returnChoice(String cTag, Object object) {
        switch (cTag) {
            case Constants_Intern.FRAGMENT_CHOICE_IMAGE_MANUFACTURER:
                Wipeprocedure.create(this, cVolley, ((Manufacturer) object).getId(), etSearch.getText().toString(), new Wipeprocedure.Interface_Create() {
                    @Override
                    public void created(Wipeprocedure wipeprocedure) {
                        removeFragment(cTag);
                        setWipeprocedure(wipeprocedure);
                        updateLayout();
                        Utility_Keyboard.hideKeyboardFrom(Activity_Wiper_Procedure.this, etSearch);
                    }
                });
        }
    }

    @Override
    public void unknownChoice(String cTag) {

    }

    @Override
    public void onFocusChange(View view, boolean b) {
        ((EditText)view).setSelection(((EditText)view).getText().length());
    }
}
