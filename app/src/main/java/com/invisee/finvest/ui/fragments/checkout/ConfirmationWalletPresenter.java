package com.invisee.finvest.ui.fragments.checkout;

import com.invisee.finvest.data.api.requests.AmountSummaryRequest;
import com.invisee.finvest.data.api.requests.PayWalletRequest;
import com.invisee.finvest.data.api.responses.AmountSummaryResponse;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.CheckoutActivity;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by pandu.abbiyuarsyah on 24/03/2017.
 */

public class ConfirmationWalletPresenter {

    private ConfirmationWalletFragment fragment;

    public ConfirmationWalletPresenter(ConfirmationWalletFragment fragment) {
        this.fragment = fragment;
    }

    void payWallet(){
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().payWallet(PrefHelper.getString(PrefKey.TOKEN), constructPayWalletRequest()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.dismissProgressDialog();
                        fragment.showDialogErrorApi();

                    }

                    @Override
                    public void onNext(GenericResponse response) {
                        fragment.dismissProgressDialog();
                        amountSummary();
                    }
                });
    }

    void amountSummary() {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().amountSummaryWallet(PrefHelper.getString(PrefKey.TOKEN), constructamountsummaryrequest()).
                observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<AmountSummaryResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.dismissProgressDialog();

                    }

                    @Override
                    public void onNext(AmountSummaryResponse response) {
                        fragment.dismissProgressDialog();
                        fragment.ammountresponse = response;
                        ((BaseActivity) fragment.getActivity()).hideKeyboard();
                        ((CheckoutActivity) fragment.getActivity()).setStep(3);
                        WalletSummaryFragment.showFragment((BaseActivity) fragment.getActivity(), response);
                    }
                });
    }

    AmountSummaryRequest constructamountsummaryrequest() {
        AmountSummaryRequest amountSummaryRequest = new AmountSummaryRequest();

        amountSummaryRequest.setToken(PrefHelper.getString(PrefKey.TOKEN));
        amountSummaryRequest.setOrderNo(fragment.response.getData().getOrderNo());

        return amountSummaryRequest;

    }

    PayWalletRequest constructPayWalletRequest() {
        PayWalletRequest payWalletRequest = new PayWalletRequest();

        payWalletRequest.setToken(PrefHelper.getString(PrefKey.TOKEN));
        payWalletRequest.setPin(fragment.pin);
        payWalletRequest.setIndex(PrefHelper.getString(PrefKey.INDEX));
        payWalletRequest.setAmount(fragment.response.getData().getAmount());
        payWalletRequest.setOrderNo(fragment.response.getData().getOrderNo());

        return payWalletRequest;

    }
}
