package com.invisee.finvest.ui.fragments.wallet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.TopUpTransaction;
import com.invisee.finvest.data.api.beans.TopUpViseePay;
import com.invisee.finvest.data.api.responses.TopUpViseePayResponse;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.AmountFormatter;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by pandu.abbiyuarsyah on 25/10/2017.
 */

public class DetailOfTopUpTransactionFragment extends BaseFragment {

    private final static String TAG = DetailOfTopUpTransactionFragment.class.getSimpleName();

    private final static String TopUp = "TopUp";

    @Bind(R.id.tvAmount)
    TextView tvAmount;
    @Bind(R.id.tvUniqCode)
    TextView tvUniqCode;
    @Bind(R.id.tvTotal)
    TextView tvTotal;
    @Bind(R.id.txvExpirationTime)
    TextView txvExpirationTime;
    @Bind(R.id.tvRekNo)
    TextView tvRekNo;
    @Bind(R.id.tvRekName)
    TextView tvRekName;
    @Bind(R.id.tvAddRek)
    TextView tvAddRek;
    @Bind(R.id.tvBank)
    TextView tvBank;

    @State
    TopUpTransaction topUpTransaction;

    private DetailOfTopUpTransactionPresenter presenter;
    public TopUpViseePayResponse topUpViseePayResponse;

    public static void showFragment(BaseActivity sourceActivity, TopUpTransaction topUpTransaction) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
            Fragment fragment = new DetailOfTopUpTransactionFragment();

            if (topUpTransaction != null) {
                Bundle bundle = new Bundle();
                bundle.putSerializable(TopUp, topUpTransaction);
                fragment.setArguments(bundle);
            }

            fragmentTransaction.replace(R.id.container, fragment, TAG);
            fragmentTransaction.commit();
        }
    }


    @Override
    protected int getLayout() {
        return R.layout.f_top_up_summary;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(TopUp))
            topUpTransaction = (TopUpTransaction) getArguments().getSerializable(TopUp);
        else
            topUpTransaction = null;

        presenter = new DetailOfTopUpTransactionPresenter(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getDetailTopUpWaiting();
    }

    void view() {
        tvAmount.setText(AmountFormatter.format(topUpViseePayResponse.getData().getTrxAmount()));
        tvUniqCode.setText(topUpViseePayResponse.getData().getUniqueId());
        tvTotal.setText(AmountFormatter.format(topUpViseePayResponse.getData().getTotalAmount()));
        txvExpirationTime.setText(String.format(getString(R.string.summary_expired_time_5), topUpViseePayResponse.getData().getDueDate()));
        tvRekNo.setText(topUpViseePayResponse.getData().getNoRek());
        tvRekName.setText(topUpViseePayResponse.getData().getNameRek());
        tvAddRek.setText(topUpViseePayResponse.getData().getAddrRek());
        tvBank.setText("Bank " + topUpViseePayResponse.getData().getBank());
    }


    @OnClick(R.id.btn_copy)
    void copyToClip(){
        copyToClipboard();
    }

    void copyToClipboard() {

        int sdk = android.os.Build.VERSION.SDK_INT;
        String totAmount_ = "";
        if(topUpViseePayResponse.getData().getTotalAmount() != null && topUpViseePayResponse.getData().getTotalAmount() != 0.0){
            double tAmount = topUpViseePayResponse.getData().getTotalAmount();
            int totAmount = (int) tAmount;
            totAmount_ = String.valueOf(totAmount);
        }

        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB){
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(totAmount_);
            Toast.makeText(getContext(), R.string.info_copy_to_clipboard_jumlah,Toast.LENGTH_LONG).show();
        }else{
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Jumlah", totAmount_);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), R.string.info_copy_to_clipboard_jumlah, Toast.LENGTH_LONG).show();
        }
    }


}

