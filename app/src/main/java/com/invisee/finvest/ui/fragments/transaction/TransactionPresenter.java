package com.invisee.finvest.ui.fragments.transaction;

import android.view.View;

import com.invisee.finvest.data.api.beans.TransactionHistory;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 3/21/16.
 */
public class TransactionPresenter {

    private static final String MAX = "100";

    private TransactionFragment fragment;

    public TransactionPresenter(TransactionFragment fragment) {
        this.fragment = fragment;
    }

    void getTransactionHistory(String name) {
        fragment.showProgressBar();
        fragment.getApi().getTransactionHistory(PrefHelper.getString(PrefKey.TOKEN), MAX, name)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<TransactionHistory>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.connectionError();

                    }

                    @Override
                    public void onNext(List<TransactionHistory> transactionHistories) {
                        fragment.transactionHistories = (ArrayList<TransactionHistory>) transactionHistories;
                        if (transactionHistories.size() > 0) {
                            fragment.tvMessage.setVisibility(View.GONE);
                            fragment.rv.setVisibility(View.VISIBLE);
                            fragment.line.setVisibility(View.VISIBLE);
                            fragment.sTrxName.setVisibility(View.VISIBLE);
                            fragment.loadTrxHistoryList();
                            fragment.dismissProgressBar();
                        } else {
                            fragment.tvMessage.setVisibility(View.VISIBLE);
                            fragment.rv.setVisibility(View.GONE);
                            fragment.line.setVisibility(View.GONE);
                            fragment.dismissProgressBar();
                        }

                    }
                });
    }
}
