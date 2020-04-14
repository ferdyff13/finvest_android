package com.invisee.finvest.ui.fragments.portfolio;

import com.invisee.finvest.data.api.responses.CartListResponse;
import com.invisee.finvest.data.api.responses.PortfolioInvestmentListResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;
import com.invisee.finvest.ui.activities.BaseActivity;

import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by fajarfatur on 3/1/16.
 */
public class ListPortfolioPresenter {

    private ListPortfolioFragment fragment;

    public ListPortfolioPresenter(ListPortfolioFragment fragment) {
        this.fragment = fragment;
    }

    void getInvestmentList(){
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

                        if(portfolioInvestmentListResponse.getCode() == 1) {
                            if (portfolioInvestmentListResponse.getData().size() > 0) {
                                fragment.noPortfolio(false);
                                fragment.investmentList = portfolioInvestmentListResponse;
                                fragment.loadInvestmentList();
                                fragment.dismissProgressBar();
                            } else {
                                fragment.noPortfolio(true);
                                fragment.dismissProgressBar();
                            }
                        }

                    }
                });
    }

    public void cartList() {
        fragment.getApi().getCartList(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<List<CartListResponse>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.connectionError();
                    }

                    @Override
                    public void onNext(List<CartListResponse> response) {
                        ((BaseActivity) fragment.getActivity()).setNotifCount(0);

                        if (response != null && response.size() > 0) {
                            ((BaseActivity) fragment.getActivity()).setNotifCount(response.size());
                            fragment.cartList = response;
                            for (int i = 0; i < response.size(); i++) {

                            }
                        } else {

                        }
                    }
                });
    }
}
