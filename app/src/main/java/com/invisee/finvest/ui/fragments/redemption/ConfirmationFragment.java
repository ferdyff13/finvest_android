package com.invisee.finvest.ui.fragments.redemption;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.beans.PortfolioInvestment;
import com.invisee.finvest.data.api.responses.PartialRedemtionResponse;
import com.invisee.finvest.data.api.responses.RedeemFeeResponse;
import com.invisee.finvest.data.api.responses.RedemptionOrderDetailResponse;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.RedemptionActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.AmountFormatter;
import com.mobsandgeeks.saripaar.annotation.Checked;
import com.invisee.finvest.util.ui.RangeSeekBar;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by fajarfatur on 3/14/16.
 */
public class ConfirmationFragment extends BaseFragment {

    public static final String TAG = ConfirmationFragment.class.getSimpleName();
    private static final String INVESTMENT = "investment";
    private static final String PACKAGES = "packages";

    @Bind(R.id.bConfirm)
    Button bConfirm;
    @Bind(R.id.tvPackageName)
    TextView tvPackageName;
    @Bind(R.id.tvMarketValue)
    TextView tvMarketValue;
    @Bind(R.id.tvInvestment)
    TextView tvInvestment;
    @Bind(R.id.etAmount)
    EditText etAmount;
    @Bind(R.id.llvalidasi)
    LinearLayout llvalidasi;
    @Bind(R.id.tvValidasi)
    TextView tvValidasi;
    @Bind(R.id.tvFee)
    TextView tvFee;
    @Checked
    @Bind(R.id.cbAgree)
    CheckBox cbAgree;
    @Bind(R.id.seekbar_placeholder)
    LinearLayout llSeekBar;
    @Bind(R.id.togglebutton_redeemtion)
    Switch toggleButtonRedeemtion;
    @Bind(R.id.rangeSeekBar)
    RangeSeekBar rsb;
    @State
    PortfolioInvestment investment;
    @State
    Packages packages;
    @State
    RedemptionOrderDetailResponse redemptionOrderDetailResponse;
    @State
    RedeemFeeResponse redemptionFees;
    @State
    PartialRedemtionResponse partialRedemtionResponse;
    @Bind(R.id.lnProgressBar)
    LinearLayout lnProgressBar;
    @Bind(R.id.lnDismissBar)
    RelativeLayout lnDismissBar;
    @Bind(R.id.pbLoading)
    ProgressBar pbLoading;
    @Bind(R.id.lnConnectionError)
    LinearLayout lnConnectionError;


    public String answer = "full";
    private double partialRedeem;
    private ConfirmationPresenter presenter;
    private double amount, amountMin;
    public int max, min;
    private int amountRedeem = 0;
    private DecimalFormat df;
    private DecimalFormat dfnd;
    private boolean hasFractionalPart;

    public static void showFragment(BaseActivity sourceActivity, PortfolioInvestment investment, Packages packages) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            ConfirmationFragment fragment = new ConfirmationFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(INVESTMENT, investment);
            bundle.putSerializable(PACKAGES, packages);
            fragment.setArguments(bundle);
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.slide_in_right, R.anim.slide_out_left, android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            fragmentTransaction.replace(R.id.container, fragment, TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_redemption_confirmation;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new ConfirmationPresenter(this);
        if (investment == null)
            investment = (PortfolioInvestment) getArguments().getSerializable(INVESTMENT);
        if (packages == null)
            packages = (Packages) getArguments().getSerializable(PACKAGES);

        answer = "full";
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((RedemptionActivity) getActivity()).setStep(1);
        toggleButtonRedeemtion.setChecked(true);
        etAmount.setEnabled(false);
        presenter.getRangeOfPartialRedemtion(investment.getInvestmentAccountNumber());

//        if (redemptionFees != null) {
//            bindInvestmentData();
//        } else {
//            presenter.getRedemptionFee(investment.getInvestmentAccountNumber());
//        }

    }


//
//    @Override
//    public void onResume() {
//        super.onResume();
////        toggleButtonRedeemtion.setChecked(true);
//
//    }

    void bindInvestmentData() {
        tvPackageName.setText(investment.getPackageName());
        tvInvestment.setText(investment.getInvestmentAccountNumber());
        tvMarketValue.setText(AmountFormatter.format(investment.getTotalInvestmentMarketValue()));
        amount = (1 - redemptionFees.getData().getRedeemFee()) * investment.getTotalInvestmentMarketValue();
        amountMin = amount*min/100;
        etAmount.setText(AmountFormatter.formatNonCurrency((int)Math.ceil(amount)));
        tvFee.setText(String.format("%.2f", Double.parseDouble(redemptionFees.getData().getRedeemFee() + "")) + " %");
    }

    @OnClick(R.id.bConfirm)
    void confirm() {
        validator.validate();
    }

    @Override
    public void onValidationSucceeded() {
        super.onValidationSucceeded();
        ((RedemptionActivity) getActivity()).setStep(2);
        Log.d(TAG, "onValidationSucceeded: " + answer);
        if (toggleButtonRedeemtion.isChecked() == true) {
            answer = "full";
        }
        AccountFragment.showFragment((BaseActivity) getActivity(), investment, packages, answer);
    }

    void viewSeekBar() {
        int maxRsb = max;
        rsb.setRangeValues(min, maxRsb);
        rsb.setSelectedMinValue(min);

        llSeekBar.setVisibility(View.VISIBLE);

        rsb.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener<Integer>() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar<?> bar, Integer minValue, Integer maxValue) {
                Log.d(TAG, "onRangeSeekBarValuesChanged: " + rsb.getAbsoluteMaxValue() + " " + rsb.getAbsoluteMinValue() + " " + rsb.getSelectedMinValue() + " " + rsb.getSelectedMaxValue() + " ");
                partialRedeem = rsb.getSelectedMaxValue().doubleValue() / 100;
                etAmount.setText(AmountFormatter.formatNonCurrency((int)Math.ceil(partialRedeem * amount)));
//                answer = String.valueOf(partialRedeem);
            }
        });
    }

    @OnClick(R.id.togglebutton_redeemtion)
    public void onToggleChanged() {
        if (toggleButtonRedeemtion.isChecked() == false) {
            etAmount.setEnabled(true);
            viewSeekBar();
            rsb.setSelectedMaxValue(min);
            partialRedeem = rsb.getSelectedMaxValue().doubleValue() / 100;
//            answer = String.valueOf(partialRedeem);
            minRedeemtinPartial();
        } else {
            etAmount.setText(AmountFormatter.formatNonCurrency((int)Math.ceil(amount)));
            etAmount.setEnabled(false);
            llSeekBar.setVisibility(View.INVISIBLE);
            answer = "full";
        }
    }


    void init() {
        df = new DecimalFormat("#,###.##", new DecimalFormatSymbols(Locale.GERMAN));
        df.setDecimalSeparatorAlwaysShown(true);
        dfnd = new DecimalFormat("#,###.##", new DecimalFormatSymbols(Locale.GERMAN));
        hasFractionalPart = false;

        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!s.toString().equalsIgnoreCase("")) {
//                    calculate(s);
/*                    String a = s.toString().substring(0,1);
                    if (!a.equals("0")){
                        calculate(s);
                    } else {
                        rsb.setSelectedMaxValue(max);
                        partialRedeem = rsb.getSelectedMaxValue().doubleValue() / 100;
                        etAmount.setText(AmountFormatter.formatNonCurrency((int)Math.ceil(partialRedeem * amount)));
                    }*/
                } else {
//                    rsb.setSelectedMaxValue(min);
//                    partialRedeem = rsb.getSelectedMaxValue().doubleValue() / 100;
//                    etAmount.setText(AmountFormatter.formatNonCurrency((int)Math.ceil(partialRedeem * amount)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                etAmount.removeTextChangedListener(this);
                try {
                    int inilen, endlen;
                    inilen = etAmount.getText().length();

                    String v = s.toString().replace(String.valueOf(df.getDecimalFormatSymbols().getGroupingSeparator()), "");
                    rsb.setSelectedMaxValue(min);
                    partialRedeem = rsb.getSelectedMaxValue().doubleValue() / 100;
                    int minRedeem = (int)Math.ceil(partialRedeem * amount);

                    amountRedeem = Integer.valueOf(s.toString().replace(".", ""));
                    amount = (1 - redemptionFees.getData().getRedeemFee()) * investment.getTotalInvestmentMarketValue();
                    double percent = (amountRedeem / amount);
                    answer = String.valueOf(percent);
                    Double temp = amount * max / 100;
                    int maxRedeem = (int)Math.ceil(temp);

                    if(amountRedeem >=temp){
//                        v = String.valueOf(maxRedeem);
                    } else if (v.equals("0")) {
                        v = String.valueOf(minRedeem);
                    } else if (v == "") {
                        v = String.valueOf(minRedeem);
                    }


               /*     v = v.isEmpty() ? String.valueOf(minRedeem) : v;*/ // jika kosong, kasih default angka 0
                    Number n = df.parse(v);
                    int cp = etAmount.getSelectionStart();
                    if (hasFractionalPart) {
                        etAmount.setText(df.format(n));
                    } else {
                        etAmount.setText(dfnd.format(n));
                    }
                    endlen = etAmount.getText().length();
                    int sel = (cp + (endlen - inilen));
                    if (sel > 0 && sel <= etAmount.getText().length()) {
                        etAmount.setSelection(sel);
                    } else {
                        // place cursor at the end?
                        etAmount.setSelection(etAmount.getText().length() - 1);
                    }
                    calculate(s);
                } catch (Exception e) {
                    Log.e(TAG, "afterTextChanged: exception catched...", e);
                }
                etAmount.addTextChangedListener(this);
            }
        });
    }

    void minRedeemtinPartial() {
        amount = (1 - redemptionFees.getData().getRedeemFee()) * investment.getTotalInvestmentMarketValue();
        Double temp = amount * min / 100;
        etAmount.setText(AmountFormatter.formatNonCurrency((int)Math.ceil(temp)));
    }

    void maxRedeemtinPartial() {
        amount = (1 - redemptionFees.getData().getRedeemFee()) * investment.getTotalInvestmentMarketValue();
        Double temp = amount * max / 100;
        etAmount.setText(AmountFormatter.formatNonCurrency((int)Math.ceil(temp)));
    }

    void calculate(CharSequence s) {
        amountRedeem = Integer.valueOf(s.toString().replace(".", ""));
//                        Double amountValue = Double.parseDouble(s.toString());
        double percent = (amountRedeem / amount) * 100;
        if (percent <= min) {
            rsb.setSelectedMaxValue(min);
        } else if (percent >= max) {
            rsb.setSelectedMaxValue(max);
        } else {
            rsb.setSelectedMaxValue(percent);
        }

        if (amountRedeem > amount) {
            rsb.setSelectedMaxValue(max);
//            partialRedeem = rsb.getSelectedMaxValue().doubleValue() / 100;
//            etAmount.setText(AmountFormatter.formatNonCurrency((int)Math.ceil(partialRedeem * amount)));
        }
        partialRedeem = rsb.getSelectedMaxValue().doubleValue() / 100;
//        answer = String.valueOf(partialRedeem);
    }


    public void showProgressBar(){
        pbLoading.setVisibility(View.VISIBLE);
        lnConnectionError.setVisibility(View.GONE);
        lnProgressBar.setVisibility(View.VISIBLE);
        lnDismissBar.setVisibility(View.GONE);
    }

    public void dismissProgressBar(){
        lnProgressBar.setVisibility(View.GONE);
        lnDismissBar.setVisibility(View.VISIBLE);
    }

    public void connectionError() {
        lnProgressBar.setVisibility(View.VISIBLE);
        lnDismissBar.setVisibility(View.GONE);
        pbLoading.setVisibility(View.GONE);
        lnConnectionError.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.tvTryAgain)
    void retryConnection() {
        presenter.getRedemptionFee(investment.getInvestmentAccountNumber());
    }

}
