package com.invisee.finvest.ui.fragments.portfolio;

import android.graphics.Color;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Fee;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.beans.PortfolioInvestment;
import com.invisee.finvest.data.api.responses.CartListResponse;
import com.invisee.finvest.data.api.responses.FundAllocationResponse;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.CartActivity;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/2/16.
 */
public class PortfolioConfirmationPresenter {

    private PortfolioConfirmationFragment fragment;

    public PortfolioConfirmationPresenter(PortfolioConfirmationFragment fragment) {
        this.fragment = fragment;
    }

    void topupFee(final Packages packages) {
        fragment.showTopupLoading();
        fragment.getApi().subscriptionFee(packages.getId(), 1l, PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<Fee>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                        Timber.e(e.getLocalizedMessage());
                        fragment.hideTopupLoading();
                    }

                    @Override
                    public void onNext(List<Fee> list) {
                        fragment.hideTopupLoading();
                        if (list.size() > 0) {
                            fragment.topupFees = list;
                            fragment.setupTopupView();
                        }
                    }
                });

    }

    void topUpToCart(PortfolioInvestment investment, Packages packages) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().createTrxCart(Double.toString(investment.getInvestmentAmount()), investment.getInvestmentAccountNumber(), packages.getPackageCode(), "TOPUP", PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.dismissProgressDialog();

                        new MaterialDialog.Builder(fragment.getActivity())
                                .iconRes(R.mipmap.ic_launcher_finvest)
                                .backgroundColor(fragment.cDanger)
                                .title(fragment.getString(R.string.failed).toUpperCase())
                                .titleColor(Color.WHITE)
                                .content(fragment.connectionError)
                                .contentColor(Color.WHITE)
                                .positiveText(R.string.ok)
                                .positiveColor(Color.WHITE)
                                .cancelable(false)
                                .show();
                    }

                    @Override
                    public void onNext(GenericResponse response) {
                        fragment.dismissProgressDialog();

                        if (response.getCode() == 1) {
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.failed).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content(response.getInfo())
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.ok)
                                    .positiveColor(Color.WHITE)
                                    .cancelable(false)
                                    .show();
                        } else {

                            ((BaseActivity) fragment.getActivity()).addNotifCount();

                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.success).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content(R.string.catalogue_confirm_subscription)
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.yes)
                                    .positiveColor(Color.WHITE)
                                    .negativeText(R.string.catalogue_view_packages)
                                    .negativeColor(fragment.cGray)
                                    .onPositive(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog dialog, DialogAction which) {
                                            CartActivity.startActivity((BaseActivity) fragment.getActivity());
                                        }
                                    })
                                    .onNegative(new MaterialDialog.SingleButtonCallback() {
                                        @Override
                                        public void onClick(MaterialDialog dialog, DialogAction which) {
                                            fragment.getActivity().finish();
                                        }
                                    })
                                    .cancelable(false)
                                    .show();
                        }

                    }
                });

    }

    public void cartList() {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().getCartList(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<CartListResponse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(List<CartListResponse> response) {
                        fragment.dismissProgressDialog();
                        ((BaseActivity) fragment.getActivity()).setNotifCount(0);

                        if (response != null && response.size() > 0) {
                            ((BaseActivity) fragment.getActivity()).setNotifCount(response.size());
                            fragment.cartList = response;

                            for (int i = 0; i < response.size(); i++) {
                                fundAllocation(response.get(i).getFundPackages().getId().longValue());
                            }

                            /*fragment.loadRule();*/
                        }
                    }
                });

    }

    void fundAllocation(Long id) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().fundAllocationInfo(id , PrefHelper.getString(PrefKey.TOKEN))
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
                        fragment.response = response;
                    }
                });

    }


}
