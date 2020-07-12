package com.example.ericschumacher.bouncer.Fragments.Object;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.ericschumacher.bouncer.Objects.Order;
import com.example.ericschumacher.bouncer.R;

public class Fragment_Order extends Fragment_Object {

    // Layout
    TableRow trCollector;
    TableRow trMarketingPackage;
    TableRow trAmountBoxes;
    TableRow trAmountBricolages;
    TableRow trAmountFlyer;
    TableRow trAmountPoster;
    TextView tvCollector;
    TextView tvMarketingPackage;
    TextView tvAmountBoxes;
    TextView tvAmountBricolage;
    TextView tvAmountFlyer;
    TextView tvAmountPoster;
    public View lMenu;

    // Object
    Order oOrder;

    // Interface
    Interface_Fragment_Order iOrder;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        // Interface
        iOrder = (Interface_Fragment_Order)getActivity();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setLayout(LayoutInflater inflater, ViewGroup container) {
        super.setLayout(inflater, container);

        trCollector = vLayout.findViewById(R.id.trCollector);
        trMarketingPackage = vLayout.findViewById(R.id.trMarketingPackage);
        trAmountBoxes = vLayout.findViewById(R.id.trAmountBox);
        trAmountBricolages = vLayout.findViewById(R.id.trAmountBricolage);
        trAmountFlyer = vLayout.findViewById(R.id.trAmountFlyer);
        trAmountPoster = vLayout.findViewById(R.id.trAmountPoster);

        tvCollector = vLayout.findViewById(R.id.tvCollector);
        tvMarketingPackage = vLayout.findViewById(R.id.tvMarketingPackage);
        tvAmountBoxes = vLayout.findViewById(R.id.tvAmountBox);
        tvAmountBricolage = vLayout.findViewById(R.id.tvAmountBricolage);
        tvAmountFlyer = vLayout.findViewById(R.id.tvAmountFlyer);
        tvAmountPoster = vLayout.findViewById(R.id.tvAmountPoster);

        lMenu = vLayout.findViewById(R.id.lMenu);

        // Data
        tvTitle.setText(getString(R.string.order));

        // Visibility
        ivPrint.setVisibility(View.GONE);
        ivDone.setVisibility(View.GONE);
        ivPause.setVisibility(View.GONE);
        ivClear.setVisibility(View.GONE);
        ivDelete.setVisibility(View.GONE);
    }

    public int getIdLayout() {
        return R.layout.fragment_box;
    }

    public void updateLayout() {
        oOrder = iOrder.getOrder();

        if (oOrder != null) {
            tvCollector.setText(oOrder.getoCollector() != null ? oOrder.getoCollector().getName() : getString(R.string.unknown));
            tvMarketingPackage.setText(oOrder.getoMarketingPackage() != null ? oOrder.getoMarketingPackage().getcName() : getString(R.string.unknown));
            if (oOrder.getnAmountMobileBox() > 0) {
                tvAmountBoxes.setText(oOrder.getnAmountMobileBox());
            } else {
                trAmountBoxes.setVisibility(View.GONE);
            }
            if (oOrder.getnAmountBricolage() > 0) {
                tvAmountBricolage.setText(oOrder.getnAmountBricolage());
            } else {
                trAmountBricolages.setVisibility(View.GONE);
            }
            if (oOrder.getnAmountFlyer() > 0) {
                tvAmountFlyer.setText(oOrder.getnAmountFlyer());
            } else {
                trAmountFlyer.setVisibility(View.GONE);
            }
            if (oOrder.getnAmountPoster() > 0) {
                tvAmountPoster.setText(oOrder.getnAmountPoster());
            } else {
                trAmountPoster.setVisibility(View.GONE);
            }
        }
    }

    public interface Interface_Fragment_Order {
        Order getOrder();
    }

}
