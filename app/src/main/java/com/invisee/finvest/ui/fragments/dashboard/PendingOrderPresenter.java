package com.invisee.finvest.ui.fragments.dashboard;

import com.invisee.finvest.data.api.beans.TransactionHistory;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by fajarfatur on 3/6/16.
 */

public class PendingOrderPresenter {

    private static final String MAX = "100";
    private static final String PENDING_ORDER = "PENDINGORDER";

    private PendingOrderFragment fragment;

    public PendingOrderPresenter(PendingOrderFragment fragment) {
        this.fragment = fragment;
    }

    void getPendingTransaction() {
        fragment.showProgressBar();
        fragment.getApi().getPendingTransaction(PrefHelper.getString(PrefKey.TOKEN), PENDING_ORDER, MAX)
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
                        if (transactionHistories != null && transactionHistories.size() > 0) {
                            fragment.llNoTranscation(true);
                            fragment.transactionHistories = (ArrayList<TransactionHistory>) transactionHistories;
                            fragment.loadList();
                            fragment.dismissProgressBar();
                        } else  {
                            fragment.llNoTranscation(false);
                            fragment.dismissProgressBar();
                        }

                    }
                });
    }
}
