package com.invisee.finvest.ui.fragments.dashboard;

import com.invisee.finvest.data.api.beans.PieChartDataDashboard;
import com.invisee.finvest.data.api.requests.PieChartRequest;
import com.invisee.finvest.data.api.responses.PieChartResponse;
import com.invisee.finvest.data.api.responses.PortfolioInvestmentListResponse;
import com.invisee.finvest.data.api.responses.PortfolioInvestmentSummaryResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import java.util.List;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by ivan.pradana on 3/8/2017.
 */

public class DetailDashboardPresenter {


    private DetailDashboardFragment fragment;

    public DetailDashboardPresenter(DetailDashboardFragment fragment) {
        this.fragment = fragment;
    }

    void investmentList() {
        fragment.showProgressBar();
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
                            if (portfolioInvestmentListResponse.getData().size() > 0) {
                                fragment.noPortfolio(false);
                                getInvestmentSummary();

                            } else {
                                fragment.noPortfolio(true);
                                fragment.dismissProgressBar();
                            }
                        }

                    }
                });
    }

    void getInvestmentSummary() {
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
                        fragment.loadInvestmentSummary();
                        getPieChartDashboard();
                    }
                });
    }

    PieChartRequest constructPieChartRequest() {
        PieChartRequest pieChartRequest = new PieChartRequest();
        pieChartRequest.setToken(PrefHelper.getString(PrefKey.TOKEN));
        return pieChartRequest;

    }

    void getPieChartDashboard() {
        fragment.getApi().getPieChartDashboard(PrefHelper.getString(PrefKey.TOKEN), constructPieChartRequest())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PieChartResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.connectionError();
                    }

                    @Override
                    public void onNext(PieChartResponse pieChartResponse) {
                        List<PieChartDataDashboard> pieChartDataDashboards = pieChartResponse.getData().getListDetail();
                        fragment.renderPieChartDashboard(pieChartDataDashboards);
                        fragment.initRV();
                        fragment.dismissProgressBar();

                    }
                });
    }


}
