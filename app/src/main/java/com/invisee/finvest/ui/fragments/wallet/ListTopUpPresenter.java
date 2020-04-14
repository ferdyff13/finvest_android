package com.invisee.finvest.ui.fragments.wallet;

import com.google.android.gms.common.api.Status;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Bank;
import com.invisee.finvest.data.api.beans.TopUpTransaction;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by pandu.abbiyuarsyah on 20/10/2017.
 */

public class ListTopUpPresenter {

    private ListTopUpFragment fragment;

    public ListTopUpPresenter(ListTopUpFragment fragment) {
        this.fragment = fragment;
    }

    void getListTopUp(String bank, String status) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().getListTopUp(PrefHelper.getString(PrefKey.TOKEN), bank, status)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<TopUpTransaction>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(List<TopUpTransaction> response) {
                        if (response.size() > 0) {
                            for (int i = 0; i < response.size(); i++) {
                              fragment.topUpTransaction = response;
                            }
                            fragment.noData(false);
                            fragment.loadtopuphistory();
                            fragment.dismissProgressDialog();

                        } else {
                            fragment.noData(true);
                            fragment.dismissProgressDialog();
                        }

                    }
                });
    }
}
