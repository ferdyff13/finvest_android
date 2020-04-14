package com.invisee.finvest.ui.fragments.promo;

import android.graphics.Color;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.requests.JoinPromoRequest;
import com.invisee.finvest.data.api.requests.PromoDetailRequest;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.api.responses.PromoDetailResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.ListPortfolioActivity;
import com.invisee.finvest.ui.activities.PromoActivity;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pandu.abbiyuarsyah on 18/05/2017.
 */

public class DetailPromoPresenter {

    private DetailPromoFragment fragment;

    public DetailPromoPresenter(DetailPromoFragment fragment) {
        this.fragment = fragment;
    }

    PromoDetailRequest constructPromoDetailRequest() {
        PromoDetailRequest promoDetailRequest = new PromoDetailRequest();

        promoDetailRequest.setToken(PrefHelper.getString(PrefKey.TOKEN));
        promoDetailRequest.setCode(fragment.response.getCode());

        return promoDetailRequest;
    }


    JoinPromoRequest construcJoinPromoRequest() {
        JoinPromoRequest joinPromoRequest = new JoinPromoRequest();

        joinPromoRequest.setToken(PrefHelper.getString(PrefKey.TOKEN));
        joinPromoRequest.setPromo(fragment.response.getCode());

        return joinPromoRequest;
    }

    void getDetailPromo() {
        fragment.showProgressBar();
        fragment.getApi().getPromoDetail(constructPromoDetailRequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PromoDetailResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressBar();
                    }

                    @Override
                    public void onNext(PromoDetailResponse promoListResponse) {
                        fragment.promoDetail = promoListResponse.getData();
                        fragment.loadDetail();
                        fragment.dismissProgressBar();
                    }
                });
    }

    void joinPromo() {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().joinPromo(construcJoinPromoRequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(GenericResponse response) {
                        fragment.dismissProgressDialog();
                        if (response.getCode() == 0 ) {
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.success).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content(response.getInfo())
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.ok)
                                    .positiveColor(Color.WHITE)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog dialog, DialogAction which) {
                                            PromoActivity.startActivity((BaseActivity) fragment.getActivity());
                                        }
                                    })
                                    .cancelable(false)
                                    .show();

                        } else if (response.getCode() == 1) {
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.infortmation).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content(response.getInfo())
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.ok)
                                    .positiveColor(Color.WHITE)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog dialog, DialogAction which) {
                                            PromoActivity.startActivity((BaseActivity) fragment.getActivity());
                                        }
                                    })
                                    .cancelable(false)
                                    .show();
                        } else {
                            fragment.showFailedDialog(response.getInfo());
                        }

                    }
                });
    }


}
