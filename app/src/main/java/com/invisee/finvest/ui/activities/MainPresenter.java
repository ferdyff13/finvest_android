package com.invisee.finvest.ui.activities;

import android.graphics.Color;

import com.afollestad.materialdialogs.MaterialDialog;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.responses.CartListResponse;
import com.invisee.finvest.data.api.responses.CompletenessPercentageResponse;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.api.responses.SettlementInfoResponse;
import com.invisee.finvest.data.api.responses.StatusCustomerResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/18/16.
 */
public class MainPresenter {

    private MainActivity activity;

    private double KYCpercentage, FATCApercentage, Riskpercentage = 0;
    private int totalCut = 100;

    public MainPresenter(MainActivity activity) {
        this.activity = activity;
    }

    void cartList() {
        activity.getInviseeService().getApi().getCartList(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<CartListResponse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                    }

                    @Override
                    public void onNext(List<CartListResponse> response) {
                        if (response != null)
                            activity.setNotifCount(response.size());
                            getCustomerStatus();
                    }
                });

    }


    void getCustomerStatus() {
        activity.getInviseeService().getApi().getStatusCustomer(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<StatusCustomerResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(StatusCustomerResponse statusCustomerResponse) {
                        if (statusCustomerResponse.getCode() == 0) {
                            activity.txvStatus.setText("Status : ");
                            activity.txvCustomerStatus.setText(statusCustomerResponse.getData());
                        }

                    }
                });
    }

    void logout() {
        activity.showProgressDialog(activity.loading);
        activity.getInviseeService().getApi().logout(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        activity.dismissProgressDialog();

                        new MaterialDialog.Builder(activity)
                                .iconRes(R.mipmap.ic_launcher_finvest)
                                .backgroundColor(activity.cDanger)
                                .title(activity.getString(R.string.logout).toUpperCase())
                                .titleColor(Color.WHITE)
                                .content(activity.connectionError)
                                .contentColor(Color.WHITE)
                                .neutralText(R.string.ok)
                                .show();
                    }

                    @Override
                    public void onNext(GenericResponse response) {
                        activity.dismissProgressDialog();
                        if (response.getCode() == 1) {
                            PrefHelper.clearAllPreferences();
                            SignInActivity.startActivity(activity);
                            activity.finish();
                        } else {
                            new MaterialDialog.Builder(activity)
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(activity.cDanger)
                                    .title(activity.getString(R.string.logout).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content(response.getInfo())
                                    .contentColor(Color.WHITE)
                                    .neutralText(R.string.ok)
                                    .show();
                        }

                    }
                });

    }

    public void getCompleteness() {
        activity.getInviseeService().getApi().getCompletenessPercentage(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CompletenessPercentageResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                    }

                    @Override
                    public void onNext(CompletenessPercentageResponse completenessPercentageResponse) {
                        KYCpercentage   = completenessPercentageResponse.getData().getKyc()*0.8;
                        FATCApercentage = completenessPercentageResponse.getData().getFatca()*0.1;
                        Riskpercentage  = completenessPercentageResponse.getData().getRiskProfile()*0.1;
                        int total = (int) ((KYCpercentage+FATCApercentage+Riskpercentage));
                        if (total > 100) {
                            activity.tvCompleteness.setText(totalCut +"%");
                        } else {
                            activity.tvCompleteness.setText(total + "%");
                        }
                        activity.pbCompleteness.setProgress(total);

                        //NumberFormat percentFormat = NumberFormat.getPercentInstance();
                        //percentFormat.setMaximumFractionDigits(2);

                    }
                });
    }

}
