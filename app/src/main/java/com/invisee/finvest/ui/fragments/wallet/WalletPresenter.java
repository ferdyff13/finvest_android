package com.invisee.finvest.ui.fragments.wallet;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.TopUpTransaction;
import com.invisee.finvest.data.api.requests.WalletRequest;
import com.invisee.finvest.data.api.responses.WalletBalanceResponse;
import com.invisee.finvest.data.api.responses.WalletHistoryResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.MainActivity;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 3/21/16.
 */
public class WalletPresenter {

    private WalletFragment fragment;

    public WalletPresenter(WalletFragment fragment) {
        this.fragment = fragment;
    }

    void getWalletBalance() {
        fragment.showProgressBar();
        fragment.getApi().requestWalletBalance(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<WalletBalanceResponse>() {
                    @Override
                    public void onCompleted(){

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.connectionError();
                    }

                    @Override
                    public void onNext(WalletBalanceResponse response){
                        if (response.getCode() == 0) {
                            fragment.balance = response.getData();
                            fragment.setBalanceView();
                            fragment.dismissProgressBar();
                        } else {
                            fragment.dismissProgressBar();
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.failed).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content(R.string.no_finpay_account)
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.ok)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                            MainActivity.startActivity((BaseActivity) fragment.getActivity());
                                        }
                                    })
                                    .positiveColor(Color.WHITE)
                                    .cancelable(false)
                                    .show();
                        }


                    }
                });
    }


    void getWalletHistory(String month) {
        fragment.showLoadingHistory();
        fragment.getApi().requestWalletHistory(PrefHelper.getString(PrefKey.TOKEN), new WalletRequest(PrefHelper.getString(PrefKey.TOKEN), month))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<WalletHistoryResponse>() {
                    @Override
                    public void onCompleted(){

                    }

                    @Override
                    public void onError(Throwable e){
                        Timber.e(e.getMessage());
                        fragment.hideLoadingHistory();
                        fragment.showErrorBalanceHistory(fragment.connectionError);
                    }

                    @Override
                    public void onNext(WalletHistoryResponse response) {
                        fragment.hideLoadingHistory();
                        if (response.getCode() == 1) {
                            fragment.historyMap = response.getData();
                            fragment.loadList();
                        } else {
                            fragment.showErrorBalanceHistory(response.getInfo());
                        }
                    }
                });
    }


    void getListTopUp() {
        fragment.getApi().getListTopUp(PrefHelper.getString(PrefKey.TOKEN), null, null)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<TopUpTransaction>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<TopUpTransaction> response) {
                        if (response.size() > 0) {
                            for (int i = 0; i < response.size(); i++) {
                                if (response.get(i).getTrxStatus().equals("WAIT")){
                                    fragment.lnPendingTrx.setVisibility(View.VISIBLE);
                                    fragment.lnNoPendingTrx.setVisibility(View.GONE);
                                    fragment.topUpTransaction = response.get(i);
                                    break;
                                } else {
                                    fragment.lnNoPendingTrx.setVisibility(View.VISIBLE);
                                    fragment.lnPendingTrx.setVisibility(View.GONE);
                                }
                            }
                            fragment.pendingTransaction();
                        }

                    }
                });
    }

}
