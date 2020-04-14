package com.invisee.finvest.ui.fragments.promo;

import com.invisee.finvest.data.api.beans.PromoResponse;
import com.invisee.finvest.data.api.beans.Reminder;
import com.invisee.finvest.data.api.responses.PromoListResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.fragments.userProfile.KycDataFragment;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by pandu.abbiyuarsyah on 18/05/2017.
 */

public class PromoPresenter {

    private PromoFragment fragment;

    public PromoPresenter(PromoFragment fragment) {
        this.fragment = fragment;
    }

    void getPromoList() {
        fragment.showProgressBar();
        fragment.getApi().getPromoList(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PromoListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.connectionError();
                    }

                    @Override
                    public void onNext(PromoListResponse promoListResponse) {
                        fragment.listPromo = promoListResponse.getData();
                        fragment.getListPromo();
                        fragment.dismissProgressBar();
                    }
                });
    }


}
