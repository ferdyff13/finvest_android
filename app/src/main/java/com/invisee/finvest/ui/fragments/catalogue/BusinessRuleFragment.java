package com.invisee.finvest.ui.fragments.catalogue;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Fee;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.DateUtil;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import butterknife.Bind;
import icepick.State;

/**
 * Created by fajarfatur on 1/22/16.
 */
public class BusinessRuleFragment extends BaseFragment {

    public static final String TAG = BusinessRuleFragment.class.getSimpleName();
    private static final String PACKAGES = "packages";

    @Bind(R.id.txvMinSubscription)
    TextView txvMinSubscription;
    @Bind(R.id.txvMinTopup)
    TextView txvMinTopup;
    @Bind(R.id.txvTransactionCutOff)
    TextView txvTransactionCutOff;
    @Bind(R.id.txvSettlementCutOff)
    TextView txvSettlementCutOff;
    @Bind(R.id.txvSettlementPeriod)
    TextView txvSettlementPeriod;
    @Bind(R.id.lnrSubscriptionData)
    LinearLayout lnrSubscriptionData;
    @Bind(R.id.lnrRedemptionData)
    LinearLayout lnrRedemptionData;
    @Bind(R.id.prbSubscription)
    ProgressBar prbSubscription;
    @Bind(R.id.prbRedemption)
    ProgressBar prbRedemption;
    private BusinessRulePresenter presenter;

    @State
    Packages packages;
    public List<Fee> subscriptionFees;
    public List<Fee> redemptionFees;

    NumberFormat nf;


    public static Fragment getFragment(Packages packages) {
        Fragment f = new BusinessRuleFragment();

        Bundle extras = new Bundle();
        extras.putSerializable(PACKAGES, packages);

        f.setArguments(extras);
        return f;
    }

    @Override
    protected int getLayout() {
        return R.layout.f_business_rules;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if (packages == null) {
            Bundle extras = getArguments();
            if (extras != null && extras.containsKey(PACKAGES)) {
                packages = (Packages) extras.getSerializable(PACKAGES);
            }
        }

        if (packages != null) {
            presenter = new BusinessRulePresenter(this);
            presenter.redemptionFee(packages);
            presenter.subscriptionFee(packages);
            setDataToView();
        }

    }


    public void setDataToView() {
        nf = NumberFormat.getCurrencyInstance(new Locale("ID", "id"));
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);


        Calendar transCutOff = Calendar.getInstance();
        transCutOff.setTime(DateUtil.format(packages.getTransactionCutOff(), "yyyy-MM-dd'T'HH:mm:ssXXX"));
        Calendar settlementCutOff = Calendar.getInstance();
        settlementCutOff.setTime(DateUtil.format(packages.getSettlementCutOff(), "yyyy-MM-dd'T'HH:mm:ssXXX"));

        txvMinSubscription.setText(nf.format(packages.getMinSubscriptionAmount()).trim());
        txvMinTopup.setText(nf.format(packages.getMinTopupAmount()).trim());
        txvTransactionCutOff.setText(DateUtil.format(transCutOff.getTime(), DateUtil.HH_MM));
        txvSettlementCutOff.setText(DateUtil.format(settlementCutOff.getTime(), DateUtil.HH_MM));
        txvSettlementPeriod.setText(packages.getSettlementPeriod() + " " + "Hari" + " " + "Bursa");
    }


    public void showSubscriptionLoading() {
        prbSubscription.setVisibility(View.VISIBLE);
    }

    public void hideSubscriptionLoading() {
        prbSubscription.setVisibility(View.GONE);
    }

    public void showRedemptionLoading() {
        prbRedemption.setVisibility(View.VISIBLE);
    }

    public void hideRedemptionLoading() {
        prbRedemption.setVisibility(View.GONE);
    }

    public void setupSubscriptionView() {
        for (int i = 0; i < subscriptionFees.size(); i++) {

            Fee fee = subscriptionFees.get(i);

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.row_fee, null);

            String actualResult = MessageFormat.format("{0,number,#.##%}", fee.getFeeAmount());

            TextView txvItem = (TextView) view.findViewById(R.id.txvItem);
            TextView txvRate = (TextView) view.findViewById(R.id.txvRate);

            if (fee.getAmountMax() == 0) {
                txvItem.setText(" lebih dari " + nf.format(fee.getAmountMin()));

            } else {
                txvItem.setText(nf.format(fee.getAmountMin()) + " - " + nf.format(fee.getAmountMax()));
            }

            txvRate.setText(actualResult);

            lnrSubscriptionData.addView(view);
        }
    }

    public void setupRedemptionView() {
        for (int i = 0; i < redemptionFees.size(); i++) {

            Fee fee = redemptionFees.get(i);

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.row_fee, null);

            String actualResult = MessageFormat.format("{0,number,#.##%}", fee.getFeeAmount());

            TextView txvItem = (TextView) view.findViewById(R.id.txvItem);
            TextView txvRate = (TextView) view.findViewById(R.id.txvRate);

            if (fee.getAmountMax() == 0) {
                txvItem.setText(" lebih dari " + fee.getAmountMin());

            } else {
                txvItem.setText(fee.getAmountMin() + " - " + fee.getAmountMax());
            }
            txvRate.setText(actualResult);

            lnrRedemptionData.addView(view);
        }
    }

}
