package com.invisee.finvest.ui.fragments.portfolio;

import android.graphics.Color;
import android.view.View;
import android.widget.Toast;

import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.invisee.finvest.R;
import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.beans.PortfolioInvestment;
import com.invisee.finvest.data.api.responses.CartListResponse;
import com.invisee.finvest.data.api.responses.CheckRedemptionResponse;
import com.invisee.finvest.data.api.responses.FundAllocationResponse;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.api.responses.PackageResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 3/1/16.
 */
public class DetailPortfolioPresenter {

    private DetailPortfolioFragment fragment;

    public DetailPortfolioPresenter(DetailPortfolioFragment fragment) {
        this.fragment = fragment;
    }


    void packageDetail(final PortfolioInvestment investment) {
        fragment.showProgressBar();
        fragment.getApi().packageDetail(investment.getPackageId(), PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PackageResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.connectionError();

                    }

                    @Override
                    public void onNext(PackageResponse response) {
                        if (response.getCode() == 1) {
                            fragment.packages = response.getData();
                            fragment.packages.setId(investment.getPackageId());
                            fragment.setupViewPager();
                            fragment.setupTabLayout();
                            fundAllocation(fragment.packages);
                        } else {
                            fragment.dismissProgressBar();
                        }

                    }
                });

    }


    void fundAllocation(Packages packages) {
        fragment.getApi().fundAllocationInfo(packages.getId(), PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<FundAllocationResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                      fragment.connectionError();
                    }

                    @Override
                    public void onNext(FundAllocationResponse response) {

                        if (response.getCode() == 1) {
                            fragment.fundAlloc = response;
                            for (int i= 0; i < response.getData().size(); i++) {
                                if (response.getData().get(i).getProductType().equals("Reksa Dana Terproteksi")) {
                                    fragment.lnTopUp.setVisibility(View.GONE);
                                } else {
                                    fragment.lnTopUp.setVisibility(View.VISIBLE);
                                }
                            }
                            fragment.dismissProgressBar();
                        } else {
                            fragment.dismissProgressBar();
                        }

                    }
                });

    }

    void checkRedemptionTransaction(String investmentAccountNo) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().checkRedemptionTransaction(investmentAccountNo, PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CheckRedemptionResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(CheckRedemptionResponse checkRedemptionResponse) {
                        fragment.dismissProgressDialog();
                        if (checkRedemptionResponse.getCode() == 1) {
                            fragment.redemptionGranted();
                        } else if (checkRedemptionResponse.getCode() == 0 && checkRedemptionResponse.isStatus()){
                            fragment.showConfirmation(checkRedemptionResponse.getInfo());
                        } else {
                            fragment.showConfirmation(checkRedemptionResponse.getInfo());
                        }
                    }
                });
    }

    void topUpValidation(String investmentAccountNo) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().topUpValidation(investmentAccountNo, PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(GenericResponse response) {
                        fragment.dismissProgressDialog();
                        if (response.getCode() == 0) {
                            checkTrxCartByInvestmentNumber(fragment.investment.getInvestmentAccountNumber());
                        } else {
                            Toast.makeText(fragment.getContext(), response.getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void checkTrxCartByInvestmentNumber(String investmentAccountNo) {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().checkTrxCartByInvestmentNumber(investmentAccountNo, PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(GenericResponse response) {
                        fragment.dismissProgressDialog();
                        if (response.getCode() == 0) {
                            fragment.portfolioConfirmation();
                        } else if (response.getCode() == 1) {
                            new MaterialDialog.Builder(fragment.getActivity())
                                    .iconRes(R.mipmap.ic_launcher_finvest)
                                    .backgroundColor(fragment.cDanger)
                                    .title(fragment.getString(R.string.failed).toUpperCase())
                                    .titleColor(Color.WHITE)
                                    .content("Investasi ini sudah dimasukkan ke keranjang belanja")
                                    .contentColor(Color.WHITE)
                                    .positiveText(R.string.ok)
                                    .positiveColor(Color.WHITE)
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
                    public void onNext(List<CartListResponse> response){
                        fragment.dismissProgressDialog();

                        try{
                            ((BaseActivity) fragment.getActivity()).setNotifCount(0);
                        }catch (Exception e){
                            e.printStackTrace();
                        }

                        if(response != null && response.size() > 0){
                            try{
                                ((BaseActivity) fragment.getActivity()).setNotifCount(response.size());
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }
                    }
                });

    }


}
