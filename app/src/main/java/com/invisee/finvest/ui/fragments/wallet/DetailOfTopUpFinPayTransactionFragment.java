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
import com.invisee.finvest.data.api.beans.TopUpTransaction;
import com.invisee.finvest.data.api.responses.TopUpFinPayResponse;
import com.invisee.finvest.data.api.responses.TopUpViseePayResponse;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.AmountFormatter;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;

public class DetailOfTopUpFinPayTransactionFragment extends BaseFragment {

    private final static String TAG = DetailOfTopUpFinPayTransactionFragment.class.getSimpleName();

    private final static String TopUp = "TopUp";

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
    TopUpTransaction topUpTransaction;

    private DetailOfTopUpFinPayTransactionPresenter presenter;
    public TopUpViseePayResponse topUpFinPayResponse;

    public static void showFragment(BaseActivity sourceActivity, TopUpTransaction topUpTransaction) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left, R.anim.slide_out_left, android.R.anim.slide_in_left, R.anim.slide_out_left);
            Fragment fragment = new DetailOfTopUpFinPayTransactionFragment();

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
        return R.layout.f_top_up_summary_finpay;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null && getArguments().containsKey(TopUp))
            topUpTransaction = (TopUpTransaction) getArguments().getSerializable(TopUp);
        else
            topUpTransaction = null;

        presenter = new DetailOfTopUpFinPayTransactionPresenter(this);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.getDetailTopUpFinPayWaiting();

    }

    void view() {
        textView5.setText(R.string.text_summary_finpay_1);
        txtNumber.setText(topUpFinPayResponse.getData().getPaymentCode());
    }


    @OnClick(R.id.txtNumber)
    void copyToClip(){
        copyToClipboard();
    }

    void copyToClipboard() {

        String codeReserve = "";
        int sdk = android.os.Build.VERSION.SDK_INT;

        codeReserve = topUpFinPayResponse.getData().getPaymentCode();

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
