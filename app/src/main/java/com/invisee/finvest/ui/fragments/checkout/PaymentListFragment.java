package com.invisee.finvest.ui.fragments.checkout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.CartList;
import com.invisee.finvest.data.api.responses.ConfirmCheckPinResponse;
import com.invisee.finvest.data.api.responses.PaymentMethodResponse;
import com.invisee.finvest.data.api.responses.TransactionTransferResponse;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.ui.fragments.wallet.TopUpFragment;
import com.mobsandgeeks.saripaar.annotation.Length;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;

import butterknife.Bind;
import butterknife.BindString;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by fajarfatur on 1/22/16.
 */
public class PaymentListFragment extends BaseFragment {

    public static final String TAG = PaymentListFragment.class.getSimpleName();
    private final static String CART_LIST = "cartList";

    @Bind(R.id.txvTotal)
    TextView txvTotal;
    @Length(min = 2)
    @Bind(R.id.etSecurityPin)
    EditText etSecurityPin;
    @Bind(R.id.labelTerm)
    TextView labelterm;
    @Bind(R.id.rbTransfer)
    RadioButton rbTransfer;
    @Bind(R.id.rbWallet)
    RadioButton rbWallet;
    @Bind(R.id.txvNoVpAccount)
    TextView txvNoVpAccount;
    @Bind(R.id.txvNoVpBalance)
    TextView txvNoVpBalance;
    @Bind(R.id.txvWarningIcon)
    TextView txvWarningIcon;
    @Bind(R.id.llGone)
    LinearLayout llGone;
    @Bind(R.id.etPaypro)
    EditText etPaypro;

    @Bind(R.id.lineHide)
    LinearLayout lineSetHide;

    @Bind(R.id.lineHideTrans)
    LinearLayout lineSetHideTrans;

    @Bind(R.id.lineHidePaypro)
    LinearLayout lineSetHidePaypro;

    @Bind(R.id.llReminder)
    ScrollView llReminder;
    @Bind(R.id.llNoReminder)
    LinearLayout llNoReminder;

    @Bind(R.id.lnProgressBar)
    LinearLayout lnProgressBar;
    @Bind(R.id.lnDismissBar)
    RelativeLayout lnDismissBar;
    @Bind(R.id.pbLoading)
    ProgressBar pbLoading;
    @Bind(R.id.lnConnectionError)
    LinearLayout lnConnectionError;

    @BindString(R.string.redemption_please_input)
    String inputDigit;
    @BindString(R.string.paypro_loading_info)
    String payproLoadingInfo;

    private PaymentListPresenter presenter;

    public String type = "";
    public static String securityPin = "";
    public boolean dialogPaypro = false;

    @State
    public CartList cartList;
    @State
    public TransactionTransferResponse transferResponse;
    @State
    public ConfirmCheckPinResponse confirmCheckPinResponse;

    public PaymentMethodResponse paymentMethodResponse;

    public static void showFragment(BaseActivity sourceActivity, CartList cartList) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);

            PaymentListFragment fragment = new PaymentListFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(CART_LIST, cartList);
            fragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.container, fragment, TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_payment_method;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PaymentListPresenter(this);

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getPaymentList();

        presenter.generateRandomIndexSecurityPin();

        rbWallet.setEnabled(false);
        rbTransfer.setEnabled(false);


        txvNoVpBalance.setVisibility(View.GONE);
        txvNoVpAccount.setVisibility(View.GONE);
        txvWarningIcon.setVisibility(View.GONE);

        cartList = (CartList) getArguments().getSerializable(CART_LIST);
        securityPin = etSecurityPin.getText().toString();
        txvTotal.setText(getTotal());
        paymentList();

        rbWallet.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rbWallet.setChecked(true);
                rbTransfer.setChecked(false);
            }
        });

        rbTransfer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                rbWallet.setChecked(false);
                rbTransfer.setChecked(true);
            }
        });

    }


    @Override
    public void onValidationSucceeded() {
        super.onValidationSucceeded();

            if (rbTransfer.isChecked()) {
                type = "TRAN";
                presenter.checkPin();
            } else if (rbWallet.isChecked()) {
                type = "WALL";
                presenter.checkPin();
            }
//            } else if (rbPayPro.isChecked()) {
//                dialogPaypro = true;
//                type = "DOMP";
//                presenter.getPaymentPayPro();
//            }

    }

    @OnClick(R.id.tvVpAddSaldo)
    void addVp()
    {
        TopUpFragment.showFragment((BaseActivity) getActivity());
    }
    public String getTotal() {
        Double total = 0d;

        for (int i = 0; i < cartList.getCartList().size(); i++) {

            Double t = Double.parseDouble(cartList.getCartList().get(i).getTotal());
            total += t;

        }

        NumberFormat nf = NumberFormat.getCurrencyInstance();
        DecimalFormatSymbols decimalFormatSymbols = ((DecimalFormat) nf).getDecimalFormatSymbols();
        decimalFormatSymbols.setCurrencySymbol("");
        ((DecimalFormat) nf).setDecimalFormatSymbols(decimalFormatSymbols);

        return "Rp " + nf.format(total);
    }

    void paymentList() {

        final LinearLayout llHide;
        llHide = lineSetHide;

        rbWallet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (llHide.getVisibility() == View.GONE) {
                    llHide.setVisibility(View.VISIBLE);
                } else {
                    llHide.setVisibility(View.GONE);
                }
            }
        });

        final LinearLayout llHideTrans;
        llHideTrans = lineSetHideTrans;

        rbTransfer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (llHideTrans.getVisibility() == View.GONE) {
                    llHideTrans.setVisibility(View.VISIBLE);
                } else {
                    llHideTrans.setVisibility(View.GONE);
                }
            }
        });

        final LinearLayout llHidePaypro;
        llHidePaypro = lineSetHidePaypro;

//        rbPayPro.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
//                if (lineSetHidePaypro.getVisibility() == View.GONE) {
//                    lineSetHidePaypro.setVisibility(View.VISIBLE);
//                } else {
//                    lineSetHidePaypro.setVisibility(View.GONE);
//                }
//            }
//        });
    }

     void payment() {
        int searchListLength = paymentMethodResponse.getData().size();
        for (int i = 0; i < searchListLength; i++) {
            if (paymentMethodResponse.getData().get(i).getCode().contains("WALL")){
                rbWallet.setEnabled(true);
            } else if(paymentMethodResponse.getData().get(i).getCode().contains("TRAN")) {
                rbTransfer.setEnabled(true);
            } else if (paymentMethodResponse.getData().get(i).getCode().contains("DOMP")) {
//                rbPayPro.setEnabled(true);
            }

        }
    }

    void noReminder(boolean b) {
        llNoReminder.setVisibility(b ? View.VISIBLE : View.GONE);
        llReminder.setVisibility(b ? View.GONE : View.VISIBLE);
    }


    @OnClick(R.id.bProceed)
    void onClickProceed() {
        validator.validate();

    }

    @OnClick(R.id.bSendPin)
    public void generateNewPin() {
        presenter.sendPIN();
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
        presenter.getPaymentList();
    }
}
