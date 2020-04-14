package com.invisee.finvest.ui.fragments.wallet;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.TopUpTransaction;
import com.invisee.finvest.data.api.beans.WalletBalance;
import com.invisee.finvest.data.api.beans.WalletHistory;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.ListTopUpActivity;
import com.invisee.finvest.ui.activities.SignUpActivity;
import com.invisee.finvest.ui.activities.TopUpActivity;
import com.invisee.finvest.ui.activities.WalletActivity;
import com.invisee.finvest.ui.adapters.rv.WalletHistoryAdapter;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.ui.fragments.termsandcondition.TermsAndConditionViseePayFragment;
import com.invisee.finvest.util.AmountFormatter;
import com.invisee.finvest.util.DateUtil;
import com.invisee.finvest.util.ui.MonthYearPickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import butterknife.Bind;
import butterknife.OnClick;
import icepick.State;


/**
 * Created by fajarfatur on 3/6/16.
 */

public class WalletFragment extends BaseFragment {

    public static final String TAG = WalletFragment.class.getSimpleName();

    @Bind(R.id.txvAccountNumber)
    TextView txvAccountNumber;
    @Bind(R.id.txvBalance)
    TextView txvBalance;
    @Bind(R.id.txvMonth)
    TextView txvMonth;
    @Bind(R.id.txvError)
    TextView txvError;

    @Bind(R.id.tvTotalAmount)
    TextView tvTotalAmount;
    @Bind(R.id.tvBank)
    TextView tvBank;
    @Bind(R.id.tvTransactionNumber)
    TextView tvTransactionNumber;
    @Bind(R.id.tvDueDate)
    TextView tvDueDate;
    @Bind(R.id.tvStatus)
    TextView tvStatus;
    @Bind(R.id.lnPendingTrx)
    LinearLayout lnPendingTrx;
    @Bind(R.id.lnNoPendingTrx)
    LinearLayout lnNoPendingTrx;
    @Bind(R.id.progressbar)
    ProgressBar progressbar;
    @Bind(R.id.rv)
    RecyclerView rv;
    @Bind(R.id.lnProgressBar)
    LinearLayout lnProgressBar;
    @Bind(R.id.lnDismissBar)
    RelativeLayout lnDismissBar;
    @Bind(R.id.pbLoading)
    ProgressBar pbLoading;
    @Bind(R.id.lnConnectionError)
    LinearLayout lnConnectionError;


    private WalletPresenter presenter;

    @State
    public WalletBalance balance;

    public TopUpTransaction topUpTransaction;

    Calendar calendar;

    public Map<String, WalletHistory> historyMap;
    private WalletHistoryAdapter adapter;


    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            fragmentTransaction.replace(R.id.container, new WalletFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_wallet;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new WalletPresenter(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        presenter.getWalletBalance();
        presenter.getListTopUp();

        calendar = Calendar.getInstance();
        rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        getTransactionHistory();
    }

    @Override
    public void onResume() {
        super.onResume();
//        getActivity().setTitle("ViseePay");
        ((WalletActivity) getActivity()).setActionBarTitle("FinPay");
    }

    public void setBalanceView() {
        txvAccountNumber.setText(balance.getPocket());
        txvBalance.setText(AmountFormatter.format(balance.getBalance()));
    }

    public void loadList() {
        adapter = new WalletHistoryAdapter(historyMap);
        rv.setAdapter(adapter);
    }

    public void getTransactionHistory() {
        txvMonth.setText(DateUtil.format(calendar.getTime(), DateUtil.MMMM));
        presenter.getWalletHistory(DateUtil.format(calendar.getTime(), DateUtil.YYYY_MM));
    }

    public void showLoadingHistory() {
        progressbar.setVisibility(View.VISIBLE);
        txvError.setVisibility(View.GONE);
        rv.setVisibility(View.GONE);
    }

    public void hideLoadingHistory() {
        progressbar.setVisibility(View.GONE);
        rv.setVisibility(View.VISIBLE);

    }

    public void showErrorBalanceHistory(String message) {
        rv.setVisibility(View.GONE);
        txvError.setVisibility(View.VISIBLE);
        txvError.setText(message);
    }

    @OnClick(R.id.lnrTopUp)
    void onClickTopUp() {
        TopUpActivity.startActivity((BaseActivity) getActivity());
    }

    @OnClick(R.id.lnrMonth)
    void onClickMonth() {
        monthYearPickerDialog().show(getChildFragmentManager(), "MonthYearPickerDialog");
    }

    @OnClick(R.id.tvDetail)
    void onClickToTopUp(){
        ListTopUpActivity.startActivity((BaseActivity) getActivity());
    }

    private MonthYearPickerDialog monthYearPickerDialog() {
        MonthYearPickerDialog pd = new MonthYearPickerDialog();
        pd.setListener(dateListener);
        return pd;
    }

    DatePickerDialog.OnDateSetListener dateListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int dayOfMonth, int monthOfYear, int year) {
            calendar = Calendar.getInstance();
            calendar.set(year, monthOfYear, dayOfMonth);
            getTransactionHistory();
        }
    };

    @OnClick(R.id.btn_copy)
    void copyToClip(){
        copyToClipboard();
    }

    void copyToClipboard() {
        int sdk = android.os.Build.VERSION.SDK_INT;
        if(sdk < android.os.Build.VERSION_CODES.HONEYCOMB) {
            android.text.ClipboardManager clipboard = (android.text.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setText(txvAccountNumber.getText().toString());
                Toast.makeText(getContext(), R.string.info_copy_to_clipboard,Toast.LENGTH_LONG).show();

        } else {
            android.content.ClipboardManager clipboard = (android.content.ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            android.content.ClipData clip = android.content.ClipData.newPlainText("No. akun FinPay", txvAccountNumber.getText().toString());
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getContext(), R.string.info_copy_to_clipboard, Toast.LENGTH_LONG).show();
        }
    }

    void pendingTransaction(){
        tvTotalAmount.setText(AmountFormatter.format(topUpTransaction.getTotalAmount()));
        tvBank.setText(topUpTransaction.getBankName());
        tvTransactionNumber.setText(topUpTransaction.getTrxNo());

        if (topUpTransaction.getTrxStatus().contains("WAIT")) {
            tvStatus.setText("Menunggu Pembayaran");
        } else if (topUpTransaction.getTrxStatus().contains("PAID")){
            tvStatus.setText("Telah Dibayar");
        } else if (topUpTransaction.getTrxStatus().contains("DONE")) {
            tvStatus.setText("Selesai");
        } else if (topUpTransaction.getTrxStatus().contains("CANC")) {
            tvStatus.setText("Batal");
        } else {
            tvStatus.setText(topUpTransaction.getTrxStatus());
        }


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ",    Locale.getDefault());
        //  Date date3 = sdf.parse("2017-02-03T11:44:52.6152Z");
        Date date3 = null;
        try {
            date3 = sdf.parse(topUpTransaction.getDueDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.DD__MMM__YYYY_HH_MM, Locale.getDefault());
        String convertedDate= formatter.format(date3);
        tvDueDate.setText(convertedDate);
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
        presenter.getWalletBalance();
    }

//    @OnClick(R.id.tvTermsViseePay)
//    void termsViseepay(){
//        TermsAndConditionViseePayFragment.showFragment((BaseActivity) getActivity() );
//    }


}
