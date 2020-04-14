package com.invisee.finvest.ui.fragments.new_dashboard;

import com.invisee.finvest.data.api.responses.CompletenessPercentageResponse;
import com.invisee.finvest.data.api.responses.PortfolioInvestmentListResponse;
import com.invisee.finvest.data.api.responses.PortfolioInvestmentSummaryResponse;
import com.invisee.finvest.data.api.responses.PromoListResponse;
import com.invisee.finvest.data.api.responses.SliderResponse;
import com.invisee.finvest.data.api.responses.WalletBalanceResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by pandu.abbiyuarsyah on 07/03/2017.
 */

public class NewDashboardPresenter {

    private NewDashboardFragment fragment;
    public NewDashboardPresenter(NewDashboardFragment fragment) {
        this.fragment = fragment;
    }

    void getInvestmentList() {
        fragment.getApi().getInvestmentList(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PortfolioInvestmentListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.connectionError();
                    }

                    @Override
                    public void onNext(PortfolioInvestmentListResponse portfolioInvestmentListResponse) {
                        if (portfolioInvestmentListResponse.getCode() == 1) {
                            getInvestmentSummary();
                        }
                    }

                });
    }

    void getInvestmentSummary() {
        fragment.loadInvestmentAmmount(0);
        fragment.getApi().getInvestmentSummary(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PortfolioInvestmentSummaryResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.connectionError();
                    }

                    @Override
                    public void onNext(PortfolioInvestmentSummaryResponse portfolioInvestmentSummaryResponse) {
                        fragment.investmentSummary = portfolioInvestmentSummaryResponse;
                        fragment.loadInvestmentAmmount(portfolioInvestmentSummaryResponse.getTotalMarketValue());
                        getCompleteness();
                    }
                });
    }

    void getWalletBalance() {
        fragment.setBalanceView(0);
        fragment.getApi().requestWalletBalance(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<WalletBalanceResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.setBalanceView(0);
                        fragment.dismissProgressBar();
                    }

                    @Override
                    public void onNext(WalletBalanceResponse response) {
                        if (response.getCode() == 0) {
                            fragment.balance = response.getData();
                            fragment.setBalanceView(response.getData().getBalance());
                            fragment.dismissProgressBar();
                        } else {
                            fragment.dismissProgressBar();
                            // fragment.showFailedDialog(response.getInfo());
                        }


                    }
                });
    }

    public void getCompleteness() {
        fragment.getApi().getCompletenessPercentage(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CompletenessPercentageResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.connectionError();
                    }

                    @Override
                    public void onNext(CompletenessPercentageResponse completenessPercentageResponse) {
                        if(completenessPercentageResponse.getData().getKyc()==100&&completenessPercentageResponse.getData().getFatca()==100&&completenessPercentageResponse.getData().getRiskProfile()==100)
                        {
                            fragment.createGridView(true);
                            getWalletBalance();
                        }
                        else
                        {
                            fragment.createGridView(false);
                            getWalletBalance();
                        }

                    }
                });
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
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(PromoListResponse promoListResponse) {
                        fragment.listPromo = promoListResponse.getData();
//                        fragment.slider(promoListResponse.getData());
                        getInvestmentList();
                    }
                });
    }

    void getSlider() {
        fragment.showProgressBar();
        fragment.getApi().getSlider()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<SliderResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.connectionError();
                    }

                    @Override
                    public void onNext(SliderResponse response) {
                        fragment.listlider = response.getData();
                        fragment.slider(response.getData());
                        fragment.loadBanner();
                        getInvestmentList();
                    }
                });
    }

}
