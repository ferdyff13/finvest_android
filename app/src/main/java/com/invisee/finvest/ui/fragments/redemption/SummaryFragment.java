package com.invisee.finvest.ui.fragments.redemption;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.beans.RedemptionData;
import com.invisee.finvest.data.api.beans.RedemptionProductComposition;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.MainActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.AmountFormatter;
import com.invisee.finvest.util.DateUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by fajarfatur on 3/14/16.
 */
public class SummaryFragment extends BaseFragment {

    public static final String TAG = SummaryFragment.class.getSimpleName();
    private static final String REDEMPTION_DATA = "redemptionData";
    private static final String PACKAGES = "packages";
    private static final String PARTIALPERCENTAGE = "PartialPercentage";
    String percentage;

    @Bind(R.id.txvOrderNumber)
    TextView txvOrderNumber;
    @Bind(R.id.txvInvestment)
    TextView txvInvestment;
    @Bind(R.id.txvPackageName)
    TextView txvPackageName;
    @Bind(R.id.txvMarketValue)
    TextView txvMarketValue;
    @Bind(R.id.txvFee)
    TextView txvFee;
    @Bind(R.id.txvRedeemAmount)
    TextView txvRedeemAmount;
    @Bind(R.id.txvAccountNumber)
    TextView txvAccountNumber;
    @Bind(R.id.txvAccountName)
    TextView txvAccountName;
    @Bind(R.id.txvBankName)
    TextView txvBankName;
    @Bind(R.id.txvExpirationTime)
    TextView txvExpirationTime;
    @Bind(R.id.txvDisclaimer1)
    TextView txvDisclaimer1;
    @Bind(R.id.txvDisclaimer2)
    TextView txvDisclaimer2;
    @Bind(R.id.lnrData)
    LinearLayout lnrData;
    @Bind(R.id.llPartialPercentage)
    LinearLayout llPartialPercentage;
    @Bind(R.id.tvPartialPercenetage)
    TextView txvPartialPercenetage;


    @State
    RedemptionData redemptionData;
    @State
    Packages packages;

    Double tempPercentage;

    public static void showFragment(BaseActivity sourceActivity, RedemptionData redemptionData, Packages packages, String partialPercentage) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            SummaryFragment fragment = new SummaryFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(REDEMPTION_DATA, redemptionData);
            bundle.putSerializable(PACKAGES, packages);
            bundle.putString(PARTIALPERCENTAGE, partialPercentage);
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.replace(R.id.container, fragment, TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_redemption_summary;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        redemptionData = (RedemptionData) getArguments().getSerializable(REDEMPTION_DATA);
        packages = (Packages) getArguments().getSerializable(PACKAGES);
        percentage = getArguments().getString(PARTIALPERCENTAGE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tempPercentage = Double.parseDouble(percentage)*100;
        int valuePercentage =  tempPercentage.intValue();
        txvPartialPercenetage.setText(valuePercentage+"%");
    }

    @Override
    public void onResume() {
        super.onResume();
        if (redemptionData != null)
            bindingRedemptionData();
        if (!percentage.equals("0")){
            llPartialPercentage.setVisibility(View.VISIBLE);
        } else {
            llPartialPercentage.setVisibility(View.GONE);
        }
    }

    void bindingRedemptionData() {

        txvOrderNumber.setText(redemptionData.getOrderNumber());
        txvInvestment.setText(redemptionData.getInvestment());
        txvPackageName.setText(redemptionData.getPackageName());
        txvMarketValue.setText(AmountFormatter.format(redemptionData.getMarketValue()));
        txvFee.setText(Double.toString(redemptionData.getFee()) + " %");
        txvRedeemAmount.setText(AmountFormatter.format(redemptionData.getRedeemAmount()));
        txvAccountNumber.setText(redemptionData.getAccountNumber());
        txvAccountName.setText(redemptionData.getAccountName());
        txvBankName.setText(redemptionData.getBankName());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        //  Date date3 = sdf.parse("2017-02-03T11:44:52.6152Z");
        Date date3 = null;
        try {
            date3 = sdf.parse(redemptionData.getPaidDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        String convertedDate= formatter.format(date3);

        txvExpirationTime.setText(String.format(getString(R.string.redemption_expired_time), convertedDate));

        Calendar transCutOff = Calendar.getInstance();
        transCutOff.setTime(DateUtil.format(packages.getTransactionCutOff(), DateUtil.INVISEE_RETURN_FORMAT2));

        txvDisclaimer1.setText(String.format(getString(R.string.redemption_disclaimer_1), DateUtil.format(transCutOff.getTime(), DateUtil.HH_MM)));
        txvDisclaimer2.setText(String.format(getString(R.string.redemption_disclaimer_2), DateUtil.format(transCutOff.getTime(), DateUtil.HH_MM)));

        loadList();
    }


    public void loadList() {

        for (int i = 0; i < redemptionData.getProduct().size(); i++) {

            RedemptionProductComposition item = redemptionData.getProduct().get(i);

            LayoutInflater inflater = getActivity().getLayoutInflater();
            View v = inflater.inflate(R.layout.row_redemption_detail, null);

            TextView txvFundName = (TextView) v.findViewById(R.id.txvFundName);
            TextView txvFundAllocation = (TextView) v.findViewById(R.id.txvFundAllocation);

            txvFundName.setText(item.getUtProductName());
            txvFundAllocation.setText(Double.toString(item.getComposition()) + " %");

            lnrData.addView(v);

        }

    }

    @OnClick(R.id.bOk)
    void close() {
        MainActivity.startActivity((BaseActivity) getActivity());
        getActivity().finish();
    }
}
