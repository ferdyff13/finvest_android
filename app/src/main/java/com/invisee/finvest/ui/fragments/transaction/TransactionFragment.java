package com.invisee.finvest.ui.fragments.transaction;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.TransactionHistory;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.adapters.rv.TransactionHistoryAdapter;
import com.invisee.finvest.ui.fragments.BaseFragment;
import com.invisee.finvest.util.AmountFormatter;
import com.invisee.finvest.util.DateUtil;
import com.invisee.finvest.util.ui.RecyclerItemClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import icepick.State;

/**
 * Created by fajarfatur on 3/6/16.
 */
public class TransactionFragment extends BaseFragment {

    private static final String TAG = "TransactionFragment";

    @Bind(R.id.tvMessage)
    TextView tvMessage;
    @Bind(R.id.sTrxName)
    Spinner sTrxName;
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
    @Bind(R.id.line)
    LinearLayout line;

    @State
    ArrayList<TransactionHistory> transactionHistories;



    public String trx_name = "";
    private String selectedItem;

    public String convertedDate;

    private String subscription = "SUBSCRIPTION";
    private String redemption = "REDEMPTION";
    private String topUp = "TOPUP";
    private String topUpAuto = "TOPUP AUTO";


    private TransactionPresenter presenter;

    public static void showFragment(BaseActivity sourceActivity) {
        if (!sourceActivity.isFragmentNotNull(TAG)) {
            FragmentTransaction fragmentTransaction = sourceActivity.getSupportFragmentManager().beginTransaction();
            fragmentTransaction.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit);
            fragmentTransaction.replace(R.id.container, new TransactionFragment(), TAG);
            fragmentTransaction.commit();
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.f_transaction;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new TransactionPresenter(this);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        sTrxName.setVisibility(View.INVISIBLE);
        sTrxName.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int spinner_pos = sTrxName.getSelectedItemPosition();
                String[] size_values = getResources().getStringArray(R.array.trx_history);
                selectedItem = size_values[spinner_pos];


                if (selectedItem.contains("Subscription")) {
                    trx_name = subscription;
                } else if (selectedItem.contains("Penjualan Kembali")) {
                    trx_name = redemption;
                } else if (selectedItem.contains("Top Up")) {
                    trx_name = topUp;
                } else if (selectedItem.contains("TopUp Auto")) {
                    trx_name = topUpAuto;
                } else if (selectedItem.contains("Semua")) {
                    trx_name = null;
                }

                presenter.getTransactionHistory(trx_name);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        initRV();
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        getActivity().setTitle("Transaksi");
//        initRV();
////        initSRL();
////        if (transactionHistories != null) {
////            loadTrxHistoryList();
////        } else {;
////            getSelectedItemSpinner();
////        }
//    }


    private void initRV() {
        LinearLayoutManager llManager = new LinearLayoutManager(getActivity());
        llManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv.setLayoutManager(llManager);


        rv.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.SimpleOnItemClickListener() {
            @Override
            public void onItemClick(View childView, int position){
                super.onItemClick(childView, position);

                TransactionHistory transactionHistory = (TransactionHistory) childView.getTag();
                showDetailTrx(transactionHistory);

            }
        }));

    }

    void getSelectedItemSpinner() {

        selectedItem = (String) sTrxName.getSelectedItem();

        if (selectedItem.contains("Subscription")) {
            trx_name = subscription;
        } else if (selectedItem.contains("Penjualan Kembali")) {
            trx_name = redemption;
        } else if (selectedItem.contains("Top Up")) {
            trx_name = topUp;
        } else if (selectedItem.contains("TopUp Auto")) {
            trx_name = topUpAuto;
        } else if (selectedItem.contains("Semua")) {
            trx_name = null;
        }

        presenter.getTransactionHistory(trx_name);

    }

    public void showDetailTrx(TransactionHistory transactionHistory) {
        MaterialDialog dialog = new MaterialDialog.Builder(getActivity())
                .title(R.string.detail_trans)
                .customView(R.layout.dialog_trx_history_detail, true)
                .positiveText(R.string.close)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        dialog.dismiss();
                    }
                }).build();

        View view = dialog.getCustomView();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssXXX", Locale.getDefault());
        Date dateParse = null;
        try {
            dateParse = sdf.parse(transactionHistory.getTransactionDate());
            SimpleDateFormat formatter = new SimpleDateFormat(DateUtil.DD_MMM_YYYY, Locale.getDefault());
            convertedDate = formatter.format(dateParse);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        ((TextView) ButterKnife.findById(view, R.id.tvTrxDate)).setText(convertedDate);
        ((TextView) ButterKnife.findById(view, R.id.tvOrderNumber)).setText(transactionHistory.getOrderNumber());
        ((TextView) ButterKnife.findById(view, R.id.tvTrxTime)).setText(transactionHistory.getTransactionTime());
        ((TextView) ButterKnife.findById(view, R.id.tvInvestmentNumber)).setText(transactionHistory.getInvestmentNumber());
        ((TextView) ButterKnife.findById(view, R.id.tvPackageName)).setText(transactionHistory.getPackageName());
        ((TextView) ButterKnife.findById(view, R.id.tvAmount)).setText(AmountFormatter.format(transactionHistory.getAmount()));
        ((TextView) ButterKnife.findById(view, R.id.tvPaymentType)).setText(transactionHistory.getPaymentType());
        ((TextView) ButterKnife.findById(view, R.id.tvTrxType)).setText(transactionHistory.getTransactionType());
        ((TextView) ButterKnife.findById(view, R.id.tvTrxStatus)).setText(transactionHistory.getTransactionStatus());
        dialog.show();
    }

    public void loadTrxHistoryList() {
        rv.setAdapter(new TransactionHistoryAdapter(getActivity(), transactionHistories));
    }


//    public void showPb() {
//        hideMessage();
//        pb.setVisibility(View.VISIBLE);
//    }

//    public void hidePb() {
//        pb.setVisibility(View.INVISIBLE);
//    }
//
//    public void showMessage(String message, Drawable icon) {
//        tvMessage.setText(message);
//        ivMessageIcon.setImageDrawable(icon);
//        tvMessage.setVisibility(View.VISIBLE);
//        ivMessageIcon.setVisibility(View.VISIBLE);
//    }
//
//    public void hideMessage() {
//        tvMessage.setVisibility(View.INVISIBLE);
//        ivMessageIcon.setVisibility(View.INVISIBLE);
//    }

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
        presenter.getTransactionHistory(trx_name);
    }

}
