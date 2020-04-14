package com.invisee.finvest.ui.fragments.reminder;

import com.invisee.finvest.R;
import com.invisee.finvest.data.api.requests.InvesmentNumberRequest;
import com.invisee.finvest.data.api.requests.ReminderEditRequest;
import com.invisee.finvest.data.api.requests.SaveReminderRequest;
import com.invisee.finvest.data.api.responses.GenericResponse;
import com.invisee.finvest.data.api.responses.InvestmentAccountResponse;
import com.invisee.finvest.data.api.responses.PackageByTokenResponse;
import com.invisee.finvest.data.api.responses.ReminderDetailResponse;
import com.invisee.finvest.data.prefs.PrefHelper;
import com.invisee.finvest.data.prefs.PrefKey;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import timber.log.Timber;

/**
 * Created by pandu.abbiyuarsyah on 08/06/2017.
 */

public class ReminderEditPresenter {

    private ReminderEditFragment fragment;

    public ReminderEditPresenter (ReminderEditFragment fragment) {
        this.fragment = fragment;
    }

    ReminderEditRequest constructreminderedit() {
        ReminderEditRequest reminderEditRequest = new ReminderEditRequest();
        reminderEditRequest.setId(String.valueOf(fragment.reminder.getReminderId()));
        reminderEditRequest.setToken(PrefHelper.getString(PrefKey.TOKEN));

        return reminderEditRequest;
    }

    void getReminderDetail(final ReminderEditRequest request) {
        fragment.getApi().getReimderDetail(request.getId(), request.getToken())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<ReminderDetailResponse>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        fragment.dismissProgressDialog();
                    }

                    @Override
                    public void onNext(ReminderDetailResponse response) {
                        if (response.getCode() == 1) {
                            fragment.reminderDetail = response.getReminder();
                            getInvestmentNumberByPackage(constructInvesmentNumbber());
                            fragment.setSpinnerPackage();
                            fragment.loadDetail();
                        }
                    }
                });
    }

    void getPackageNameByToken() {
        fragment.showProgressDialog(fragment.loading);
        fragment.getApi().viewPackageName(PrefHelper.getString(PrefKey.TOKEN))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<PackageByTokenResponse>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        fragment.dismissProgressDialog();
                        fragment.showFailedDialog(fragment.connectionError);
                    }

                    @Override
                    public void onNext(PackageByTokenResponse response) {
                        if (response.getCode() == 1) {
                            fragment.packageList = response.getData();
                            getReminderDetail(constructreminderedit());
                        } else {
                            fragment.showFailedDialog(response.getInfo());
                        }
                    }
                });
    }

    InvesmentNumberRequest constructInvesmentNumbber() {
        InvesmentNumberRequest invesmentNumberRequest = new InvesmentNumberRequest();
        invesmentNumberRequest.setFundPackage(String.valueOf(fragment.reminderDetail.getFundPackageRefId()));
        invesmentNumberRequest.setToken(PrefHelper.getString(PrefKey.TOKEN));

        return invesmentNumberRequest;
    }

    void getInvestmentNumberByPackage(final InvesmentNumberRequest request) {
        fragment.getApi().viewInvestementAccount(request.getFundPackage(), request.getToken())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<InvestmentAccountResponse>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        fragment.dismissProgressDialog();
                        fragment.showFailedDialog(fragment.connectionError);
                    }

                    @Override
                    public void onNext(InvestmentAccountResponse response) {
                        if (response.getCode() == 1) {
                            fragment.investmentAccountList = response.getData();
                        /*    for (int i = 0; i < response.getData().size(); i++) {*/
                                /*fragment.setSpinnerInvestmentNo(response.getData(), fragment.reminderDetail.getFundPackageRefId().intValue());*/
                                /*fragment.setSpinnerInvestmentNo();*/



                         /*   getReminderDetail(constructreminderedit());*/
                        } else {
                            fragment.showFailedDialog(response.getInfo());
                        }
                        fragment.dismissProgressDialog();
                    }
                });
    }

    void getInvestmentNumberByPackage(String fundPackageId) {
        fragment.getApi().viewInvestementAccountrEeminder(PrefHelper.getString(PrefKey.TOKEN), fundPackageId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<InvestmentAccountResponse>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        fragment.dismissProgressDialog();
                        fragment.showFailedDialog(fragment.connectionError);
                    }

                    @Override
                    public void onNext(InvestmentAccountResponse response) {

                        if (response.getCode() == 1) {
                            fragment.investmentAccountList = response.getData();
                            fragment.setSpinnerInvestmentNo();
                        } else {
                            fragment.showFailedDialog(response.getInfo());
                        }
                    }
                });
    }

    void saveOrUpdate(SaveReminderRequest request) {
        fragment.showProgressDialog(fragment.getString(R.string.please_wait));
        fragment.getApi().saveOrUpdateReminder(PrefHelper.getString(PrefKey.TOKEN), request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<GenericResponse>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e.getMessage());
                        fragment.dismissProgressDialog();
                        fragment.showFailedDialog(fragment.connectionError);
                    }

                    @Override
                    public void onNext(GenericResponse response) {
                        fragment.dismissProgressDialog();

                        if (response.getCode() == 1) {
                            fragment.showSuccessDialog(response.getInfo(), true);
                        } else {
                            fragment.showFailedDialog(response.getInfo());
                        }
                    }
                });
    }




}
