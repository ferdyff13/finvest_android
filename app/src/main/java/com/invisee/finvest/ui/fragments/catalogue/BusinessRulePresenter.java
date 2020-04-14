package com.invisee.finvest.ui.fragments.catalogue;

import com.invisee.finvest.data.api.beans.Fee;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/2/16.
 */
public class BusinessRulePresenter {

    private BusinessRuleFragment fragment;

    public BusinessRulePresenter(BusinessRuleFragment fragment) {
        this.fragment = fragment;
    }

    void subscriptionFee(final Packages packages) {
        fragment.showSubscriptionLoading();
        fragment.getApi().subscriptionFee(packages.getId(), 1l, PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Fee>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.hideSubscriptionLoading();
                    }

                    @Override
                    public void onNext(List<Fee> list) {
                        fragment.hideSubscriptionLoading();

                        if (list.size() > 0) {
                            fragment.subscriptionFees = list;
                            fragment.setupSubscriptionView();
                        }
                    }
                });

    }

    void redemptionFee(final Packages packages) {
        fragment.showRedemptionLoading();
        fragment.getApi().redemptionFee(packages.getId(), 2l, PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Fee>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.hideRedemptionLoading();
                    }

                    @Override
                    public void onNext(List<Fee> list) {
                        fragment.hideRedemptionLoading();

                        if (list.size() > 0) {
                            fragment.redemptionFees = list;
                            fragment.setupRedemptionView();
                        }
                    }
                });

    }

}
