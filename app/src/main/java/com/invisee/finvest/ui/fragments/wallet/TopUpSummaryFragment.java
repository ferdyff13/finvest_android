package com.invisee.finvest.ui.fragments.wallet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Reminder;
import com.invisee.finvest.data.api.beans.TopUpViseePay;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.AmountFormatter;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by pandu.abbiyuarsyah on 19/10/2017.
 */

public class TopUpSummaryFragment extends BaseFragment {

    private static final String TAG = TopUpSummaryFragment.class.getSimpleName();

    private static final String TopUp = "TopUp";

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
    TopUpViseePay topUpViseePay;

    public static void showFragment(BaseActivity sourceActivity, TopUpViseePay topUpViseePay) {
        FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
        Fragment fragment = new TopUpSummaryFragment();

        if (topUpViseePay != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(TopUp, topUpViseePay);
            fragment.setArguments(bundle);
        }

        fragmentTransaction.replace(R.id.container, fragment, TAG);
        fragmentTransaction.commit();
    }


    @Override
    protected int getLayout() {
        return R.layout.f_top_up_summary;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(TopUp))
            topUpViseePay = (TopUpViseePay) getArguments().getSerializable(TopUp);
        else
            topUpViseePay = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewSummary();

    }

    void viewSummary() {
        tvAmount.setText(AmountFormatter.format(topUpViseePay.getTrxAmount()));
        tvUniqCode.setText(topUpViseePay.getUniqueId());
        tvTotal.setText(AmountFormatter.format(topUpViseePay.getTotalAmount()));
        txvExpirationTime.setText(String.format(getString(R.string.summary_expired_time_5), topUpViseePay.getDueDate()));
        tvBank.setText("Bank " + topUpViseePay.getBank());
        tvRekNo.setText(topUpViseePay.getNoRek());
        tvRekName.setText(topUpViseePay.getNameRek());
        tvAddRek.setText(topUpViseePay.getAddrRek());
    }


    @OnClick(R.id.btn_copy)
    void copyToClip(){
        copyToClipboard();
    }


    void copyToClipboard(){

        String totAmount_ = "";
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(topUpViseePay.getTotalAmount() != null && topUpViseePay.getTotalAmount() != 0.0){
             double tAmount = topUpViseePay.getTotalAmount();
             int totAmount = (int) tAmount;
             totAmount_ = String.valueOf(totAmount);
        }

        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB){
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(totAmount_);
            Toast.makeText(getContext(), R.string.info_copy_to_clipboard_jumlah, Toast.LENGTH_LONG).show();
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Jumlah tranfer", totAmount_);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), R.string.info_copy_to_clipboard_jumlah, Toast.LENGTH_LONG).show();
        }
    }
}
