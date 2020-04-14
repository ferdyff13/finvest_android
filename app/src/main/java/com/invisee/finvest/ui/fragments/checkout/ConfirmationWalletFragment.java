package com.invisee.finvest.ui.fragments.checkout;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.ConfirmCheckPin;
import com.invisee.finvest.data.api.responses.AmountSummaryResponse;
import com.invisee.finvest.data.api.responses.ConfirmCheckPinResponse;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.CartActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.AmountFormatter;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by pandu.abbiyuarsyah on 24/03/2017.
 */

public class ConfirmationWalletFragment extends BaseFragment {

    public static final String TAG = ConfirmationWalletFragment.class.getSimpleName();
    private final static String CONFIRM_CHECK = "confirmCheckPinResponse";
    private final static String SECURITY_PIN = "securityPin";

    @Bind(R.id.txvaccVp)
    TextView txvaccVp;

    @Bind(R.id.txvbalanceVp)
    TextView txvbalanceVp;

    @Bind(R.id.txvOrderNumberVp)
    TextView txvOrderNumberVp;

    @Bind(R.id.txvTotalVp)
    TextView txvTotalVp;

    @State
    public ConfirmCheckPinResponse response;
    @State
    public AmountSummaryResponse ammountresponse;

    private ConfirmationWalletPresenter presenter;

    public String pin;

    public static void showFragment(BaseActivity sourceActivity, ConfirmCheckPinResponse response, String securityPin) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);

            ConfirmationWalletFragment fragment = new ConfirmationWalletFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(CONFIRM_CHECK, response);
            bundle.putString(SECURITY_PIN, securityPin);
            fragment.setArguments(bundle);

            fragmentTransaction.replace(R.id.container, fragment, TAG);
            fragmentTransaction.commit();
        }
    }


    @Override
    protected int getLayout() {
        return R.layout.f_confirmation_wallet;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        response = (ConfirmCheckPinResponse) getArguments().getSerializable(CONFIRM_CHECK);
        pin = (String) getArguments().getSerializable(SECURITY_PIN);

        presenter = new ConfirmationWalletPresenter(this);

        loadClist();
    }

    public void loadClist() {
        ConfirmCheckPin item = response.getData();

        txvaccVp.setText(item.getAccount());
        txvbalanceVp.setText(AmountFormatter.format(item.getBalance().toString()));
        txvOrderNumberVp.setText(item.getOrderNo());
        txvTotalVp.setText(AmountFormatter.format(item.getAmount().toString()));
    }


    @OnClick(R.id.okConfirmatioVP)
    void goToSummary() {
        presenter.payWallet();
    }

    @OnClick(R.id.cancelConfirmatioVP)
    void CancelConfirmation() {
        if (getFragmentManager().getBackStackEntryCount() > 0) {
            getFragmentManager().popBackStack();
        } else {
            super.getActivity().onBackPressed();
        }
    }

    void showDialogErrorApi() {
        new MaterialDialog.Builder(getActivity())
                .iconRes(R.mipmap.ic_launcher_finvest)
                .backgroundColor(cDanger)
                .title(getString(R.string.infortmation).toUpperCase())
                .titleColor(Color.WHITE)
                .content(R.string.error_api)
                .contentColor(Color.WHITE)
                .positiveText(R.string.redeemtion_failed_tutup)
                .positiveColor(Color.WHITE)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(MaterialDialog dialog, DialogAction which) {
                        CartActivity.startActivity((BaseActivity) getActivity());
                        getActivity().finish();
                    }
                })
                .show();
    }
}