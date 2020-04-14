package com.invisee.finvest.ui.fragments.redemption;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.invisee.finvest.data.api.responses.PartialRedemtionResponse;
import com.invisee.finvest.data.api.responses.RedeemFeeResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by fajarfatur on 3/16/16.
 */
public class ConfirmationPresenter {

    private ConfirmationFragment fragment;
    private String sysDate, sysDates,minimalDate;
    public int day, month, year, dayCurrent, monthCurrent, yearCurrent, dates, datenow;
    private boolean statusCheckDate = false;

    public ConfirmationPresenter(ConfirmationFragment fragment) {
        this.fragment = fragment;
    }

//    void getRedemptionOrderDetails(String investmentAccountNo) {
//        fragment.showProgressDialog(fragment.loading);
//        fragment.getApi().redemptionOrderDetails(investmentAccountNo, PrefHelper.getString(PrefKey.TOKEN))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Observer<RedemptionOrderResponse>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        fragment.dismissProgressDialog();
//                        Toast.makeText(fragment.getContext(), fragment.connectionError, Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onNext(RedemptionOrderResponse redemptionOrderDetailResponse) {
//                        fragment.dismissProgressDialog();
//                        if (redemptionOrderDetailResponse.getCode() == 1) {
//                            fragment.redemptionOrderDetailResponse = redemptionOrderDetailResponse.getData();
//                            fragment.bindInvestmentData();
//                        } else {
//                            Toast.makeText(fragment.getContext(), redemptionOrderDetailResponse.getInfo(), Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }

    void getRedemptionFee(String investmentAccountNo) {
        fragment.getApi().redemptionFee(investmentAccountNo, PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<RedeemFeeResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(RedeemFeeResponse redeemFeeResponse) {
                        fragment.redemptionFees = redeemFeeResponse;
                        fragment.bindInvestmentData();
                        fragment.init();
                        fragment.cbAgree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                                                               @Override
                                                               public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                                                                   if (isChecked) {
                                                                       fragment.bConfirm.setEnabled(true);
                                                                   } else {
                                                                       fragment.bConfirm.setEnabled(false);
                                                                   }
                                                               }
                                                           }
                        );

                        fragment.dismissProgressBar();
                    }
                });

    }

    void getRangeOfPartialRedemtion(String investmentAccountNo) {
        fragment.showProgressBar();
        fragment.getApi().rangeOfPartialRedemtion(investmentAccountNo, PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PartialRedemtionResponse>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getLocalizedMessage());
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(PartialRedemtionResponse partialRedemtionRsp) {
                        if (partialRedemtionRsp.getCode() == 1) {
                            fragment.partialRedemtionResponse = partialRedemtionRsp;
                            Double min = partialRedemtionRsp.getData().getMinPartialRedemption() * 100;
                            Double max = partialRedemtionRsp.getData().getMaxPartialRedemption() * 100;

//                            Calendar calendar = Calendar.getInstance();
//                            calendar.set(year, month-1, day);
//
//                            SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy");
//                            sysDates = formatDate.format(calendar.getTime());

                            checkStatusMinimalRedeem(partialRedemtionRsp.getData().getMinimalInvestmentRedeemDate());
                            Log.d("status", "onNext: " + partialRedemtionRsp.getData().getMinimalInvestmentAmount());

                            if (partialRedemtionRsp.getData().getMinimalInvestmentAmount() <= 0 && !statusCheckDate) {
                                if (min >= max) {
                                    fragment.llvalidasi.setVisibility(View.VISIBLE);
                                    fragment.toggleButtonRedeemtion.setEnabled(false);
                                    fragment.answer = "full";
                                } else {
                                    fragment.min = min.intValue();
                                    fragment.max = max.intValue();
                                }
                            } else {
                                fragment.min = min.intValue();
                                fragment.max = max.intValue();

                                fragment.etAmount.setEnabled(true);
                                fragment.tvValidasi.setText("Investasi ini hanya dapat dijual sebagian, penjualan secara keseluruhan hanya dapat dilakukan setelah " + minimalDate);
                                Log.d("Minim", "Date" + minimalDate);
//                                if (year > yearCurrent){
//                                    fragment.llvalidasi.setVisibility(View.GONE);
//                                } else if (month > monthCurrent){
//                                    fragment.llvalidasi.setVisibility(View.GONE);
//                                } else if (day > dayCurrent){
//                                    fragment.llvalidasi.setVisibility(View.GONE);
//                                } else {
//                                    fragment.llvalidasi.setVisibility(View.VISIBLE);
//                                }

                                if (year > yearCurrent){
                                    fragment.llvalidasi.setVisibility(View.VISIBLE);
                                } else {
                                    fragment.llvalidasi.setVisibility(View.GONE);
                                    if (month > monthCurrent){
                                        fragment.llvalidasi.setVisibility(View.VISIBLE);
                                    } else {
                                        fragment.llvalidasi.setVisibility(View.GONE);
                                        if (day > dayCurrent){
                                            fragment.llvalidasi.setVisibility(View.VISIBLE);
                                        } else {
                                            fragment.llvalidasi.setVisibility(View.GONE);
                                        }
                                    }
                                }

//                                if (partialRedemtionRsp.getData().getMinimalInvestmentRedeemDate() =< new Date())

                                fragment.toggleButtonRedeemtion.setChecked(true);
                                fragment.viewSeekBar();
//                                fragment.toggleButtonRedeemtion.setEnabled(false);
                            }

                            getRedemptionFee(fragment.investment.getInvestmentAccountNumber());

                        } else {
                            Toast.makeText(fragment.getContext(), partialRedemtionRsp.getInfo(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    void checkStatusMinimalRedeem(String minimalRedeemDate) {
        SimpleDateFormat dtf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ", Locale.getDefault());
        Date localDate = new Date();
        sysDate = dtf.format(localDate);


        if (minimalRedeemDate == null) {
            minimalRedeemDate = sysDate;
        }



        String stringCurrent = sysDate;
        String[] partsCurrent = stringCurrent.split("-");
        String tanggalCurrent = partsCurrent[2].substring(0, 2);
        dayCurrent = Integer.parseInt(tanggalCurrent);
        monthCurrent = Integer.parseInt(partsCurrent[1]);
        yearCurrent = Integer.parseInt(partsCurrent[0]);

        if (year > yearCurrent) {
            statusCheckDate = true;
        } else {
            if (month > monthCurrent) {
                statusCheckDate = true;

            } else {
                statusCheckDate = day > dayCurrent;
            }
        }
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month-1, day);

        SimpleDateFormat formatDate = new SimpleDateFormat("dd MMMM yyyy");
        minimalDate = formatDate.format(calendar.getTime());
        Log.d("tgl", "checkStatusMinimalRedeem: " + calendar.getTime() + " " + minimalRedeemDate);
    }
}
