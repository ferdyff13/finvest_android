package com.invisee.finvest.ui.fragments.support;

import android.widget.Toast;

import com.invisee.finvest.data.api.responses.FaqResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 3/6/16.
 */
public class SupportPresenter {

    private SupportFragment fragment;

    public SupportPresenter(SupportFragment fragment) {
        this.fragment = fragment;
    }

    public void getFaq() {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().getFaq(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<FaqResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                        Timber.e(e.getLocalizedMessage());
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(FaqResponse response) {
                        fragment.dismissProgressDialog();
                        if (response.getCode() == 1) {
                            fragment.faqList = response.getData();
                            fragment.loadList();
                        }
                    }
                });
    }

}