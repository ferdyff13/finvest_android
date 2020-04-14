package com.invisee.finvest.ui.fragments.dashboard;

import com.invisee.finvest.data.api.requests.ListRequest;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/2/16.
 */
public class DashboardPresenter {

    private DashboardFragment fragment;

    public DashboardPresenter(DashboardFragment fragment) {
        this.fragment = fragment;
    }


    void investmentList() {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().investmentList(PrefHelper.getString(PrefKey.TOKEN), new ListRequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                        Timber.e(e.getLocalizedMessage());
                    }

                    @Override
                    public void onNext(GenericResponse genericResponse) {
                        Timber.i("Code %s", genericResponse.getCode());
                        Timber.i("Info %s", genericResponse.getInfo());
                        fragment.dismissProgressDialog();
                    }
                });

    }

}
