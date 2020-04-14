package com.invisee.finvest.ui.fragments.catalogue;

import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.responses.FundAllocationResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/2/16.
 */
public class FundAllocationPresenter {

    private FundAllocationFragment fragment;

    public FundAllocationPresenter(FundAllocationFragment fragment) {
        this.fragment = fragment;
    }


    void fundAllocation(final Packages packages) {
        fragment.showLoading();
        fragment.getApi().fundAllocationInfo(packages.getId(), PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<FundAllocationResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.hideLoading();
                    }

                    @Override
                    public void onNext(FundAllocationResponse response) {

                        if (response.getCode() == 1) {
                            fragment.hideLoading();
                            fragment.response = response;
                            fragment.loadList();
                        }

                    }
                });

    }

}
