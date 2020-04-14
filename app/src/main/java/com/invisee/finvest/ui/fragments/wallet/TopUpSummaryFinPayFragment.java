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
import com.invisee.finvest.data.api.beans.ResponseFinPay;
import com.invisee.finvest.data.api.beans.TopUpFinPay;
import com.invisee.finvest.data.api.beans.TopUpViseePay;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.AmountFormatter;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;

public class TopUpSummaryFinPayFragment extends BaseFragment {

    private static final String TAG = TopUpSummaryFinPayFragment.class.getSimpleName();

    private static final String TopUpFin = "TopUpFin";

    @Bind(R.id.textView1)
    TextView textView1;
    @Bind(R.id.textView2)
    TextView textView2;
    @Bind(R.id.textView3)
    TextView textView3;
    @Bind(R.id.textView4)
    TextView textView4;
    @Bind(R.id.textView5)
    TextView textView5;
    @Bind(R.id.txtNumber)
    TextView txtNumber;

    @State
    TopUpFinPay topUpFinPay;
    @State
    ResponseFinPay responseFinPay;

    public static void showFragment(BaseActivity sourceActivity, TopUpFinPay topUpFinPay) {
        FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
        Fragment fragment = new TopUpSummaryFinPayFragment();

        if (topUpFinPay != null) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(TopUpFin, topUpFinPay);
            fragment.setArguments(bundle);
        }

        fragmentTransaction.replace(R.id.container, fragment, TAG);
        fragmentTransaction.commit();
    }

    @Override
    protected int getLayout() {
        return R.layout.f_top_up_summary_finpay;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(TopUpFin))
            topUpFinPay = (TopUpFinPay) getArguments().getSerializable(TopUpFin);
        else
            topUpFinPay = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewSummary();

    }

    void viewSummary() {
        textView5.setText(R.string.text_summary_finpay_1);
        txtNumber.setText(topUpFinPay.getResponse().getPayment_code());
    }


    @OnClick(R.id.txtNumber)
    void copyToClip(){
        copyToClipboard();
    }


    void copyToClipboard(){

        String codeReserve = "";
        int sdk = android.os.Build.VERSION.SDK_INT;

        codeReserve = topUpFinPay.getResponse().getPayment_code();

        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB){
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(codeReserve);
            Toast.makeText(getContext(), R.string.info_copy_to_clipboard_code, Toast.LENGTH_LONG).show();
        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("Kode Tambah Saldo", codeReserve);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), R.string.info_copy_to_clipboard_code, Toast.LENGTH_LONG).show();
        }
    }

}
