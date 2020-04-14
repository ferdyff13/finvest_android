package com.invisee.finvest.ui.fragments.wallet;

import com.invisee.finvest.data.api.beans.TopUpTransaction;
import com.invisee.finvest.data.api.requests.DetailWaitingTopUpRequest;
import com.invisee.finvest.data.api.responses.TopUpViseePayResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by pandu.abbiyuarsyah on 25/10/2017.
 */

public class DetailOfTopUpTransactionPresenter {

    private  DetailOfTopUpTransactionFragment fragment;

    public DetailOfTopUpTransactionPresenter(DetailOfTopUpTransactionFragment fragment) {
        this.fragment = fragment;
    }

    public DetailWaitingTopUpRequest constrcutdetailwaitingtopup(){
        DetailWaitingTopUpRequest detailWaitingTopUpRequest = new DetailWaitingTopUpRequest();
        detailWaitingTopUpRequest.setTrxNumber(fragment.topUpTransaction.getTrxNo());

        return detailWaitingTopUpRequest;
    }

    void getDetailTopUpWaiting() {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().detailWaitingTopUp(PrefHelper.getString(PrefKey.TOKEN), constrcutdetailwaitingtopup())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<TopUpViseePayResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(TopUpViseePayResponse response) {
                        fragment.topUpViseePayResponse = response;
                        if (response.getCode() == 1) {
                            fragment.view();
                            fragment.dismissProgressDialog();
                        } else {
                            fragment.dismissProgressDialog();
                        }
                    }
                });
    }


}
