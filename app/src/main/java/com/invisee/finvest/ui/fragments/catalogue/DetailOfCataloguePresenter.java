package com.invisee.finvest.ui.fragments.catalogue;

import android.graphics.Color;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.beans.ProductList;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.api.responses.MaxScoreResponse;
import com.invisee.finvest.data.api.responses.PackageResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;
import com.invisee.finvest.ui.activities.CartActivity;
import com.invisee.finvest.ui.activities.ListOfCatalogueActivity;
import com.invisee.finvest.ui.activities.UserProfileActivity;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/2/16.
 */
public class DetailOfCataloguePresenter {

    private DetailOfCatalogueFragment fragment;

    public DetailOfCataloguePresenter(DetailOfCatalogueFragment fragment) {
        this.fragment = fragment;
    }

    void packageDetail(final ProductList packages) {
        fragment.showProgressBar();
        fragment.getApi().packageDetail(packages.getId(), PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PackageResponse>() {
                    @Override
                    public void onCompleted() {
                        // Do nothing
                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.connectionError();
                    }

                    @Override
                    public void onNext(PackageResponse response) {
                        if (response.getCode() == 1) {
                            fragment.packages = response.getData();
                            fragment.packages.setId(packages.getId());
                            fragment.setPackagesDataToView();
                            fragment.setupViewPager();
                            fragment.dismissProgressBar();
                        } else {
                            fragment.showFailedDialog(response.getInfo());
                        }
                    }
                });
    }

    void getMaxScore(final ProductList packages) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().getMaxScore(packages.getId(), PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<MaxScoreResponse>() {
                    @Override
                    public void onCompleted() {
                        // Do nothing
                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(MaxScoreResponse response) {
                        fragment.dismissProgressDialog();
                        if (response.getCode() == 1) {
                            if (response.getData().getMaxScoreKyc().equals("")) {
                                if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("ACT")) {
                                    new MaterialDialog.Builder(fragment.getActivity())
                                            .iconRes(R.mipmap.ic_launcher_finvest)
                                            .backgroundColor(fragment.cDanger)
                                            .title(fragment.getString(R.string.success).toUpperCase())
                                            .titleColor(Color.WHITE)
                                            .content(R.string.catalogue_act_dialog)
                                            .contentColor(Color.WHITE)
                                            .positiveText(R.string.yes)
                                            .positiveColor(Color.WHITE)
                                            .negativeText(R.string.no)
                                            .negativeColor(fragment.cGray)
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(MaterialDialog dialog, DialogAction which) {
                                                    UserProfileActivity.startActivity((BaseActivity) fragment.getActivity());
                                                }
                                            })
                                            .cancelable(false)
                                            .show();
                                }
                            } else if(Integer.valueOf(response.getData().getMaxScoreKyc()) < response.getData().getMaxScoreFundPackage()) {
                                new MaterialDialog.Builder(fragment.getActivity())
                                        .iconRes(R.mipmap.ic_launcher_finvest)
                                        .backgroundColor(fragment.cDanger)
                                        .title(fragment.getString(R.string.success).toUpperCase())
                                        .titleColor(Color.WHITE)
                                        .content(R.string.catalogue_dialog_risk_profile)
                                        .contentColor(Color.WHITE)
                                        .positiveText(R.string.yes)
                                        .positiveColor(Color.WHITE)
                                        .negativeText(R.string.no)
                                        .negativeColor(fragment.cGray)
                                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                                            @Override
                                            public void onClick(MaterialDialog dialog, DialogAction which) {
                                                if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("ACT")) {
                                                    new MaterialDialog.Builder(fragment.getActivity())
                                                            .iconRes(R.mipmap.ic_launcher_finvest)
                                                            .backgroundColor(fragment.cDanger)
                                                            .title(fragment.getString(R.string.success).toUpperCase())
                                                            .titleColor(Color.WHITE)
                                                            .content(R.string.catalogue_act_dialog)
                                                            .contentColor(Color.WHITE)
                                                            .positiveText(R.string.yes)
                                                            .positiveColor(Color.WHITE)
                                                            .negativeText(R.string.no)
                                                            .negativeColor(fragment.cGray)
                                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                                @Override
                                                                public void onClick(MaterialDialog dialog, DialogAction which) {
                                                                    UserProfileActivity.startActivity((BaseActivity) fragment.getActivity());
                                                                }
                                                            })
                                                            .cancelable(false)
                                                            .show();
                                                } else {
                                                    subscribeToCart(fragment.packages);
                                                }
                                            }
                                        })
                                        .cancelable(false)
                                        .show();
                            } else {
                                if (PrefHelper.getString(PrefKey.CUSTOMER_STATUS).equals("ACT")) {
                                    new MaterialDialog.Builder(fragment.getActivity())
                                            .iconRes(R.mipmap.ic_launcher_finvest)
                                            .backgroundColor(fragment.cDanger)
                                            .title(fragment.getString(R.string.success).toUpperCase())
                                            .titleColor(Color.WHITE)
                                            .content(R.string.catalogue_act_dialog)
                                            .contentColor(Color.WHITE)
                                            .positiveText(R.string.yes)
                                            .positiveColor(Color.WHITE)
                                            .negativeText(R.string.no)
                                            .negativeColor(fragment.cGray)
                                            .onPositive(new MaterialDialog.SingleButtonCallback() {
                                                @Override
                                                public void onClick(MaterialDialog dialog, DialogAction which) {
                                                    UserProfileActivity.startActivity((BaseActivity) fragment.getActivity());
                                                }
                                            })
                                            .cancelable(false)
                                            .show();
                                } else {
                                        subscribeToCart(fragment.packages);
                                }
                            }
                        }
                    }
                });
    }

    void subscribeToCart(final Packages packages) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().createTrxCart(packages.getInvestmentAmount(), "", packages.getPackageCode(), "SUBCR", PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted() {
                        // Do nothing
                    }

                    @Override
                    public void onError(Throwable e) {
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
                        } if (response.getCode() == 13) {
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
                        } else if(response.getCode() == 50){
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.infortmation).toUpperCase())
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
                                            ListOfCatalogueActivity.startActivity((BaseActivity) fragment.getActivity());
                                        }
                                    })
                                    .cancelable(false)
                                    .show();
                        }
                    }
                });
    }
}