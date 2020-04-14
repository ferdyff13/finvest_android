package com.invisee.finvest.ui.fragments.portfolio;

import android.widget.Toast;

import com.invisee.finvest.data.api.requests.InvestmentPerformanceRequest;
import com.invisee.finvest.data.api.responses.InvestmentPerformanceResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 3/2/16.
 */
public class PortfolioInvesmentPerformancePresenter {

    private PortfolioInvesmentPerformanceFragment fragment;

    public PortfolioInvesmentPerformancePresenter(PortfolioInvesmentPerformanceFragment fragment) {
        this.fragment = fragment;
    }

    void getInvestmentPerformance(String investmentAccountNo){
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().getInvestmentPerformance(PrefHelper.getString(PrefKey.TOKEN),
                new InvestmentPerformanceRequest(investmentAccountNo))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<InvestmentPerformanceResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e){
                        Timber.e(e.getMessage());
                        fragment.dismissProgressDialog();
                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(InvestmentPerformanceResponse investmentPerformanceResponse) {
                        fragment.dismissProgressDialog();


                        List<String> datelist = new ArrayList<>();

                        Integer index;
                        for (index = 0; index < investmentPerformanceResponse.getData().size() ; index++)
                        {
                            String a = investmentPerformanceResponse.getData().get(index).getDate();
                            datelist.add(a);
                        }

                        fragment.date = datelist.toArray(new String[investmentPerformanceResponse.getData().size()]);


//                        fragment.drawChart(investmentPerformanceResponse.getData());
                        fragment.chart(investmentPerformanceResponse.getData());
                    }
                });
    }
}
