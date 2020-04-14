package com.invisee.finvest.ui.fragments.wallet;

import com.invisee.finvest.data.api.requests.DetailWaitingTopUpRequest;
import com.invisee.finvest.data.api.responses.TopUpFinPayResponse;
import com.invisee.finvest.data.api.responses.TopUpViseePayResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class DetailOfTopUpFinPayTransactionPresenter {

    private  DetailOfTopUpFinPayTransactionFragment fragment;

    public DetailOfTopUpFinPayTransactionPresenter(DetailOfTopUpFinPayTransactionFragment fragment) {
        this.fragment = fragment;
    }

    public DetailWaitingTopUpRequest constrcutdetailwaitingtopup(){
        DetailWaitingTopUpRequest detailWaitingTopUpRequest = new DetailWaitingTopUpRequest();
        detailWaitingTopUpRequest.setTrxNumber(fragment.topUpTransaction.getTrxNo());

        return detailWaitingTopUpRequest;
    }

    void getDetailTopUpFinPayWaiting() {
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
                        fragment.topUpFinPayResponse = response;
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
