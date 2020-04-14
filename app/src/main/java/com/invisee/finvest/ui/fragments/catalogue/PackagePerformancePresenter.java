package com.invisee.finvest.ui.fragments.catalogue;

import com.invisee.finvest.data.api.beans.Packages;
import com.invisee.finvest.data.api.requests.PackagePerformanceRequest;
import com.invisee.finvest.data.api.responses.PackagesPerformanceResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import java.util.ArrayList;
import java.util.List;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 2/2/16.
 */
public class PackagePerformancePresenter {

    public final static String DAILY = "DAILY";
    public final static String WEEKLY = "WEEKLY";
    public final static String MONTHLY = "MONTHLY";
    public final static String ANNUALLY = "ANNUALLY";

    public final static String ONE_MONTH = "1month";
    public final static String SIX_MONTH = "6month";
    public final static String ONE_YEAR = "1year";
    public final static String THREE_YEARS = "3year";
    public final static String FIVE_YEARS = "5year";

    private PackagePerformanceFragment fragment;

    public PackagePerformancePresenter(PackagePerformanceFragment fragment) {
        this.fragment = fragment;
    }

    void packagePerformanceInfo(final Packages packages, String mode) {
        PackagePerformanceRequest request = new PackagePerformanceRequest();
        request.setPackageId(packages.getId());
        request.setToken(PrefHelper.getString(PrefKey.TOKEN));
        request.setRange(mode);


        fragment.showLoading();
        fragment.getApi().packagePerformanceInfo(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PackagesPerformanceResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.hideLoading();
                    }

                    @Override
                    public void onNext(PackagesPerformanceResponse response) {
                        fragment.hideLoading();
                        if (response.getCode() == 0) {
                            fragment.response = response;
                            /*fragment.setupChart();*/
                            /*fragment.drawChart();*/
                            List<String> datelist = response.getData().getPerformanceDate();
                            fragment.date = datelist.toArray(new String[datelist.size()]);
                            fragment.cubicLineChart();
                        }
                    }
                });

    }
}
