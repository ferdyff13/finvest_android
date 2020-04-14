package com.invisee.finvest.ui.fragments.portfolio;

import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.responses.FundAllocationResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 3/2/16.
 */
public class PortfolioFundAlocationPresenter {

    private PortfolioFundAlocationFragment fragment;

    public PortfolioFundAlocationPresenter(PortfolioFundAlocationFragment fragment) {
        this.fragment = fragment;
    }

    public void fundAllocation(Packages packages) {
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
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(FundAllocationResponse response) {
                        fragment.dismissProgressDialog();
                        if (response.getCode() == 1) {
                            fragment.fundAlloc = response;
                            fragment.loadList();

                        }

                    }
                });

    }
}
