package com.example.ericschumacher.bouncer.Fragments.Object;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Activities.Manager.Activity_Model;
import com.example.ericschumacher.bouncer.Objects.Model;
import com.example.ericschumacher.bouncer.R;
import com.example.ericschumacher.bouncer.Utilities.Utility_Layout;
import com.example.ericschumacher.bouncer.Volley.Volley_Connection;

public class Fragment_Model extends Fragment implements View.OnClickListener {

    View vLayout;

    // Header
    TextView tvSubtitle;
    ImageView ivHeaderLeft;
    ImageView ivHeaderRight;

    TableRow trName;
    TableRow trManufacturer;
    TableRow trCharger;
    TableRow trBatteryRemovable;
    TableRow trBackcoverRemovable;
    TableRow trBattery;
    TableRow trBatteryLku;
    TableRow trDefaultExploitation;
    TableRow trDps;
    TableRow trPhoneType;
    TableRow trModelDamages;

    TextView tvTitle;
    TextView tvName;
    TextView tvManufacturer;
    TextView tvCharger;
    TextView tvBatteryRemovable;
    TextView tvBackcoverRemovable;
    TextView tvBattery;
    TextView tvBatteryLku;
    TextView tvDefaultExploitation;
    TextView tvDps;
    TextView tvPhoneType;
    TextView tvModelDamages;

    // Visibility
    boolean bShowAll;

    // Interface
    Activity_Model activityModel;

    // Ohters
    FragmentManager fManager;

    // Connection
    Volley_Connection cVolley;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // Interface
        activityModel = (Activity_Model) getActivity();

        // Layout
        setLayout(inflater, container);

        return vLayout;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        //super.onSaveInstanceState(outState);
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        // Initiate
        vLayout = inflater.inflate(getIdLayout(), container, false);
        tvTitle = vLayout.findViewById(R.id.tvTitle);
        tvSubtitle = vLayout.findViewById(R.id.tvSubtitle);
        ivHeaderLeft = vLayout.findViewById(R.id.ivHeaderLeft);
        ivHeaderRight = vLayout.findViewById(R.id.ivHeaderRight);
        trName = vLayout.findViewById(R.id.trName);
        trManufacturer = vLayout.findViewById(R.id.trManufacturer);
        trCharger = vLayout.findViewById(R.id.trCharger);
        trBatteryRemovable = vLayout.findViewById(R.id.trBatteryRemovable);
        trBackcoverRemovable = vLayout.findViewById(R.id.trBackcoverRemovable);
        trBattery = vLayout.findViewById(R.id.trBattery);
        trBatteryLku = vLayout.findViewById(R.id.trBatteryLku);
        trDefaultExploitation = vLayout.findViewById(R.id.trDefaultExploitation);
        trDps = vLayout.findViewById(R.id.trDps);
        trPhoneType = vLayout.findViewById(R.id.trPhone);
        trModelDamages = vLayout.findViewById(R.id.trModelDamages);

        tvName = vLayout.findViewById(R.id.tvName);
        tvManufacturer = vLayout.findViewById(R.id.tvManufacturer);
        tvCharger = vLayout.findViewById(R.id.tvCharger);
        tvBatteryRemovable = vLayout.findViewById(R.id.tvBatteryRemovable);
        tvBackcoverRemovable = vLayout.findViewById(R.id.tvBackcoverRemovable);
        tvBattery = vLayout.findViewById(R.id.tvBattery);
        tvBatteryLku = vLayout.findViewById(R.id.tvBatteryLku);
        tvDefaultExploitation = vLayout.findViewById(R.id.tvDefaultExploitation);
        tvDps = vLayout.findViewById(R.id.tvDps);
        tvPhoneType = vLayout.findViewById(R.id.tvPhone);
        tvModelDamages = vLayout.findViewById(R.id.tvModelDamages);

        // Data
        tvTitle.setText(getString(R.string.model));

        // OnClickListener
        trName.setOnClickListener(this);
        trManufacturer.setOnClickListener(this);
        trCharger.setOnClickListener(this);
        trBattery.setOnClickListener(this);
        trBatteryRemovable.setOnClickListener(this);
        trBackcoverRemovable.setOnClickListener(this);
        trDefaultExploitation.setOnClickListener(this);
        trDps.setOnClickListener(this);
        trPhoneType.setOnClickListener(this);
        trModelDamages.setOnClickListener(this);
        ivHeaderLeft.setOnClickListener(this);
        ivHeaderRight.setOnClickListener(this);

        ivHeaderRight.setVisibility(View.VISIBLE);
        ivHeaderRight.setColorFilter(getActivity().getResources().getColor(R.color.color_divider));
        bShowAll = false;
    }

    public int getIdLayout() {
        return R.layout.fragment_model;
    }

    public void updateLayout() {
        Model model = activityModel.getModel();

        setClickable(model != null);

        if (model != null) {

            if (model.getName() != null) {
                tvName.setText(model.getName());
            } else {
                tvName.setText(getString(R.string.unknown));
            }
            if (model.getoManufacturer() != null) {
                tvManufacturer.setText(model.getoManufacturer().getName());
            } else {
                tvManufacturer.setText(getString(R.string.unknown));
            }
            if (model.getoCharger() != null) {
                tvCharger.setText(model.getoCharger().getName());
            } else {
                tvCharger.setText(getString(R.string.unknown));
            }
            if (model.getoBattery() != null) {
                tvBattery.setText(model.getoBattery().getName());
            } else {
                tvBattery.setText(getString(R.string.unknown));
            }
            if (model.gettPhone() != null) {
                tvPhoneType.setText(model.gettPhoneName(getActivity()));
            } else {
                tvPhoneType.setText(getString(R.string.unknown));
            }
            if (model.gettDefaultExploitation() != null) {
                tvDefaultExploitation.setText(model.getExploitationName(getActivity()));
            } else {
                tvDefaultExploitation.setText(getString(R.string.unknown));
            }
            tvDps.setText(Integer.toString(model.getnDps()));
            if (model.isBatteryRemovable() != null) {
                tvBatteryRemovable.setText((model.isBatteryRemovable()) ? getString(R.string.yes) : getString(R.string.no));
            } else {
                tvBatteryRemovable.setText(getString(R.string.unknown));
            }
            if (model.isBackcoverRemovable() != null) {
                tvBackcoverRemovable.setText((model.isBackcoverRemovable()) ? getString(R.string.yes) : getString(R.string.no));
            } else {
                tvBackcoverRemovable.setText(getString(R.string.unknown));
            }
            if (model.getoBattery() != null && model.getoBattery().getLku() != null) {
                tvBatteryLku.setText(model.getoBattery().getoManufacturer().getcShortcut() + " - " + Integer.toString(model.getoBattery().getLku()));
            } else {
                tvBatteryLku.setText(getString(R.string.unknown));
            }
            tvModelDamages.setText(Integer.toString(model.getlModelDamages().size()));

        } else {
            tvName.setText(getString(R.string.unknown));
            tvManufacturer.setText(getString(R.string.unknown));
            tvCharger.setText(getString(R.string.unknown));
            tvBattery.setText(getString(R.string.unknown));
            tvPhoneType.setText(getString(R.string.unknown));
            tvDefaultExploitation.setText(getString(R.string.unknown));
            tvDps.setText(getString(R.string.unknown));
            tvBatteryRemovable.setText(getString(R.string.unknown));
            tvBackcoverRemovable.setText(getString(R.string.unknown));
            tvBatteryLku.setText(getString(R.string.unknown));
            tvModelDamages.setText(getString(R.string.unknown));
        }

        // Clap-In & -Out
        updateVisibility();

    }

    public void updateVisibility() {
        int bVisibility;
        if (bShowAll) {
            bVisibility = View.VISIBLE;
            ivHeaderRight.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_expand_more));

        } else {
            bVisibility = View.GONE;
            ivHeaderRight.setImageDrawable(ContextCompat.getDrawable(getContext(), R.drawable.ic_expand_less));
        }
        setVisibility(bVisibility);

    }

    public void setVisibility(int bVisibility) {
        trPhoneType.setVisibility(bVisibility);
        trManufacturer.setVisibility(bVisibility);
        trDefaultExploitation.setVisibility(bVisibility);
        trDps.setVisibility(bVisibility);
        trBattery.setVisibility(bVisibility);
        trBatteryRemovable.setVisibility(bVisibility);
        trBackcoverRemovable.setVisibility(bVisibility);
        trModelDamages.setVisibility(bVisibility);
    }

    public void showAll(boolean bShowAll) {
        this.bShowAll = bShowAll;
        updateVisibility();
    }

    private void setClickable(boolean clickable) {
        trName.setClickable(clickable);
        trManufacturer.setClickable(clickable);
        trCharger.setClickable(clickable);
        trBattery.setClickable(clickable);
        trPhoneType.setClickable(clickable);
        trDefaultExploitation.setClickable(clickable);
        trDps.setClickable(clickable);
        trBatteryRemovable.setClickable(clickable);
        trBackcoverRemovable.setClickable(clickable);
        trBatteryLku.setClickable(clickable);
        trModelDamages.setClickable(clickable);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.trName:
                activityModel.requestModelName();
                break;
            case R.id.trManufacturer:
                activityModel.requestManufacturer();
                break;
            case R.id.trCharger:
                activityModel.requestCharger();
                break;
            case R.id.trBattery:
                activityModel.requestBattery();
                break;
            case R.id.trPhone:
                activityModel.requestPhoneType();
                break;
            case R.id.trDefaultExploitation:
                activityModel.requestDefaultExploitation();
                break;
            case R.id.trBatteryRemovable:
                activityModel.requestBatteryRemovable();
                break;
            case R.id.trBackcoverRemovable:
                activityModel.requestBackcoverRemovable();
                break;
            case R.id.trDps:
                activityModel.requestDps();
                break;
            case R.id.trModelDamages:
                activityModel.requestModelDamages();
                break;
            case R.id.ivHeaderRight:
                bShowAll = !bShowAll;
                updateLayout();
                break;
        }
    }
}
