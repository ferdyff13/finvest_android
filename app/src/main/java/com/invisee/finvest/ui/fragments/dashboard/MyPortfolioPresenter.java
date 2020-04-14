package com.invisee.finvest.ui.fragments.dashboard;

import android.widget.Toast;

import com.invisee.finvest.data.api.beans.PortfolioChartData;
import com.invisee.finvest.data.api.responses.PortfolioInvestmentListResponse;
import com.invisee.finvest.data.api.responses.PortfolioInvestmentSummaryResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 3/1/16.
 */
public class MyPortfolioPresenter {

    private MyPortfolioFragment fragment;

    public MyPortfolioPresenter(MyPortfolioFragment fragment) {
        this.fragment = fragment;
    }


    void getInvestmentList() {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().getInvestmentList(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PortfolioInvestmentListResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        fragment.dismissProgressDialog();
                        fragment.noPortfolio(true);
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(PortfolioInvestmentListResponse portfolioInvestmentListResponse) {
                        fragment.dismissProgressDialog();
                        if (portfolioInvestmentListResponse.getCode() == 1) {
                            if (portfolioInvestmentListResponse.getData().size() > 0) {
                                fragment.noPortfolio(false);
                                getInvestmentSummary();
                                getPieChart();
                            } else {
                                fragment.noPortfolio(true);
                            }
                        }
                    }
                });
    }

    void getInvestmentSummary() {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().getInvestmentSummary(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PortfolioInvestmentSummaryResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        fragment.dismissProgressDialog();
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(PortfolioInvestmentSummaryResponse portfolioInvestmentSummaryResponse) {
                        fragment.dismissProgressDialog();
                        fragment.investmentSummary = portfolioInvestmentSummaryResponse;
                        fragment.loadInvestmentSummary();
                    }
                });
    }

    void getPieChart() {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().getPieChart(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<PortfolioChartData>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        fragment.dismissProgressDialog();
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<PortfolioChartData> portfolioChartDatas) {
                        fragment.dismissProgressDialog();
                        /*fragment.renderPieChart(portfolioChartDatas);*/

                    }
                });
    }

}
